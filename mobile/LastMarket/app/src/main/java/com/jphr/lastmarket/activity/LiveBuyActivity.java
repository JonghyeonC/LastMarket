package com.jphr.lastmarket.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphr.lastmarket.R;
import com.jphr.lastmarket.dto.ChatDTO;
import com.jphr.lastmarket.dto.ProductDetailDTO;
import com.jphr.lastmarket.openvidu.CustomHttpClient;
import com.jphr.lastmarket.openvidu.CustomWebSocket;
import com.jphr.lastmarket.openvidu.PermissionsDialogFragment;
import com.jphr.lastmarket.openvidu.RemoteParticipant;
import com.jphr.lastmarket.openvidu.Session;
import com.jphr.lastmarket.service.ProductService;
import com.jphr.lastmarket.viewmodel.LiveViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;


public class LiveBuyActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101;
    private static final int MY_PERMISSIONS_REQUEST = 102;
    private final String TAG = "SessionActivity";
    private LiveViewModel viewModel ;
    @Nullable
    @BindView(R.id.views_container)
    LinearLayout views_container;
    @BindView(R.id.local_gl_surface_view)
    SurfaceViewRenderer localVideoView;
    @BindView(R.id.main_participant)
    TextView main_participant;
    @BindView(R.id.peer_container)
    FrameLayout peer_container;


    private String application_server_url = "https://i8d206.p.ssafy.io/";
    private String wsServerUrl="ws://i8d206.p.ssafy.io/api/ws/websocket";
    private String APPLICATION_SERVER_URL;
    private Session session;
    private CustomHttpClient httpClient;
    private String token;
    private Long productId;
    private Long sellerId;
    private Long startPrice;
    private TextView topPriceTv;
    private TextView startPriceTv;
    private Long userId;
    private ProductDetailDTO detailDTO;
    private Double tick;
    String session_name = "SessionA";
    String participant_name = "participant_tmp";
    private StompClient stompClient;
    private List<StompHeader> headerList;
    private Long myTopPrice;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_live_buy);
        ButterKnife.bind(this);

        ImageView postPrice=findViewById(R.id.postPrice);
        startPriceTv=findViewById(R.id.startPriceTv);
        topPriceTv=findViewById(R.id.topPriceTv);

        productId=getIntent().getLongExtra("productId",0);
        Log.d(TAG, "onCreate: "+productId);
        sellerId=getIntent().getLongExtra("sellerId",0);
        startPrice=getIntent().getLongExtra("startPrice",0);
        tick=startPrice*0.1;
        startPriceTv.setText(startPrice.toString());

        detailDTO = (ProductDetailDTO) getIntent().getSerializableExtra("data");
        Log.d(TAG, "detailDTO: "+detailDTO);
        session_name=productId.toString();

        viewModel= new ViewModelProvider(this).get(LiveViewModel.class);
        viewModel.setNowPrice(startPrice);

        SharedPreferences pref= getSharedPreferences("user_info",MODE_PRIVATE);
        userId=pref.getLong("user_id",0);
        token= pref.getString("token","null");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        askForPermissions();
        initStomp();
        stompClient.topic("/exchange/chat.exchange/room."+productId).subscribe(topicMessage -> {
            String str=topicMessage.getPayload();
            JSONObject jsonObject=new JSONObject(str);
            String price=jsonObject.getString("message");
            String type=jsonObject.getString("chatType");
            if(type.equals("FINISH")){

                Long tmp2=Long.parseLong(price);
                ProductService productService=new ProductService();
                Log.d(TAG, "mytopprice"+myTopPrice+"tmp2"+tmp2);
                if(tmp2.equals(myTopPrice)){//내가격이 최고가일때 (낙찰)
                    //TODO : 낙찰되었을 때 EVENT 화려하게
                    Log.d(TAG, "onCreate: 낙찰");
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("isFromLive","true");
                    ChatDTO chatDTO=new ChatDTO(jsonObject.getString("chatType"),jsonObject.getString("buyer"),jsonObject.getString("seller"),jsonObject.getString("message"),jsonObject.getString("roomKey"),jsonObject.getString("sender"));
                    intent.putExtra("chatDTO",chatDTO);
                    productService.changeFinish(token,productId);
                    startActivity(intent);
                }else {//내가격이 최고가가 아닐때(미낙찰)
                    Log.d(TAG, "onCreate: 미낙찰");

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    productService.changeFinish(token,productId);
                    startActivity(intent);
                }

            }else if(type.equals("BID")){
                Log.d(TAG, "onCreate: "+price);
                Double tmp= Double.valueOf(price);
                Long tmp2= Long.valueOf(Math.round(tmp));
                try {
                    viewModel.setNowPrice(tmp2);
                }catch (Exception e){
                    Log.e(TAG, "error "+e );
                }
            }

        });



        if (arePermissionGranted()) {
            initViews();
            APPLICATION_SERVER_URL = application_server_url;
            httpClient = new CustomHttpClient(APPLICATION_SERVER_URL);

            String sessionId = session_name;
            getToken(sessionId);
        } else {
            DialogFragment permissionsFragment = new PermissionsDialogFragment();
            permissionsFragment.show(getSupportFragmentManager(), "Permissions Fragment");
        }

        //view
        postPrice.setOnClickListener(new ImageView.OnClickListener(){
            //TODO: 가격 제시했을 때 EVENT 화려하게
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                double price=viewModel.getNowPrice()+tick;
                String priceToString = Double.toString(price);
                ChatDTO dto= new ChatDTO("BID",userId.toString(),sellerId.toString(),priceToString,productId.toString(),userId.toString());

                ObjectMapper mapper=new ObjectMapper();
                try {
                    String jsonString=mapper.writeValueAsString(dto);
                    stompClient.send("/send/room."+productId, jsonString).subscribe();
                    Log.d(TAG, "onClick: send OK"+jsonString);
                    myTopPrice=Math.round(price);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        });
        viewModel.nowPrice.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                topPriceTv.setText(aLong.toString());
            }
        });
    }
    @SuppressLint("CheckResult")
    public void initStomp(){
        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

        //stomp client 생성
        stompClient= Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsServerUrl);

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;
                case ERROR:
                    Log.e(TAG, "initStomp:" );
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    if(lifecycleEvent.getException().getMessage().contains("EOF")){
                        isUnexpectedClosed.set(true);
                    }
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    if(isUnexpectedClosed.get()){
                        /**
                         * EOF Error
                         */
                        initStomp();
                        isUnexpectedClosed.set(false);
                    }
                    break;
            }
        });

        // add Header
        headerList=new ArrayList<>();
        headerList.add(new StompHeader("Authorization", token));
        stompClient.connect(headerList);
    }


    public void askForPermissions() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST);
        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }


    private void getToken(String sessionId) {
        try {
            Log.d(TAG, "getToken[sessionId]: " + sessionId);
            // Session Request
            RequestBody sessionBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"customSessionId\": \"" + sessionId + "\"}");
            Log.d(TAG, "getToken[sessionBody]: " + sessionBody);
            this.httpClient.httpCall("api/sessions", "POST", "application/json", sessionBody,token, new Callback() {

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.d(TAG, "responseString: " + response);

                    // Token Request
                    RequestBody tokenBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{}");
                    httpClient.httpCall("/api/sessions/" + sessionId + "/connections", "POST", "application/json", tokenBody,token, new Callback() {

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) {
                            String responseString = null;
                            try {
                                responseString = response.body().string();
                                Log.d(TAG, "onResponse[token]:" + responseString);
                            } catch (IOException e) {
                                Log.e(TAG, "Error getting body", e);
                            }
                            getTokenSuccess(responseString, sessionId);
                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e(TAG, "Error POST /api/sessions/SESSION_ID/connections", e);
                            connectionError(APPLICATION_SERVER_URL);
                        }
                    });
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(TAG, "Error POST /api/sessions", e);
                    connectionError(APPLICATION_SERVER_URL);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "Error getting token", e);
            e.printStackTrace();
            connectionError(APPLICATION_SERVER_URL);
        }
    }

    private void getTokenSuccess(String token, String sessionId) {
//        // Initialize our session
        session = new Session(sessionId, token, views_container, this);

        // Initialize our local participant and start local camera
        String participantName = participant_name.toString();
//        LocalParticipant localParticipant = new LocalParticipant(participantName, session, this.getApplicationContext(), localVideoView);
//        localParticipant.startCamera();
        runOnUiThread(() -> {
            // Update local participant view
            main_participant.setText(participant_name);
            main_participant.setPadding(20, 3, 20, 3);
        });

        // Initialize and connect the websocket to OpenVidu Server
        startWebSocket();
    }

    private void startWebSocket() {
        CustomWebSocket webSocket = new CustomWebSocket(session, this);
        webSocket.execute();
        session.setWebSocket(webSocket);
    }

    private void connectionError(String url) {
        Runnable myRunnable = () -> {
            Toast toast = Toast.makeText(this, "Error connecting to " + url, Toast.LENGTH_LONG);
            toast.show();
            viewToDisconnectedState();
        };
        new Handler(this.getMainLooper()).post(myRunnable);
    }

    private void initViews() {
        EglBase rootEglBase = EglBase.create();
        localVideoView.init(rootEglBase.getEglBaseContext(), null);
        localVideoView.setMirror(true);
        localVideoView.setEnableHardwareScaler(true);
        localVideoView.setZOrderMediaOverlay(true);
    }

    public void viewToDisconnectedState() {
        runOnUiThread(() -> {
            localVideoView.clearImage();
            localVideoView.release();
            main_participant.setText(null);
            main_participant.setPadding(0, 0, 0, 0);
        });
    }

    //---------------다른 사용자 비디오 레이아웃-----------------
    public void createRemoteParticipantVideo(final RemoteParticipant remoteParticipant) {
        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable myRunnable = () -> {
            View rowView = this.getLayoutInflater().inflate(R.layout.peer_video, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 20);
            rowView.setLayoutParams(lp);
            int rowId = View.generateViewId();
            rowView.setId(rowId);
            views_container.addView(rowView);
            SurfaceViewRenderer videoView = (SurfaceViewRenderer) ((ViewGroup) rowView).getChildAt(0);
            remoteParticipant.setVideoView(videoView);
            videoView.setMirror(false);
            EglBase rootEglBase = EglBase.create();
            videoView.init(rootEglBase.getEglBaseContext(), null);
            videoView.setZOrderMediaOverlay(true);
            View textView = ((ViewGroup) rowView).getChildAt(1);
            remoteParticipant.setParticipantNameText((TextView) textView);
            remoteParticipant.setView(rowView);

            remoteParticipant.getParticipantNameText().setText(remoteParticipant.getParticipantName());
            remoteParticipant.getParticipantNameText().setPadding(20, 3, 20, 3);
        };
        mainHandler.post(myRunnable);
    }

    public void setRemoteMediaStream(MediaStream stream, final RemoteParticipant remoteParticipant) {
        final VideoTrack videoTrack = stream.videoTracks.get(0);
        videoTrack.addSink(remoteParticipant.getVideoView());
        runOnUiThread(() -> {
            remoteParticipant.getVideoView().setVisibility(View.VISIBLE);
        });
    }

    public void leaveSession() {
        if (this.session != null) {
            this.session.leaveSession();
        }
        if (this.httpClient != null) {
            this.httpClient.dispose();
        }
        viewToDisconnectedState();
    }

    private boolean arePermissionGranted() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_DENIED);
    }

    @Override
    protected void onPause() {
        leaveSession();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        leaveSession();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        leaveSession();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        leaveSession();
        super.onStop();
    }

}