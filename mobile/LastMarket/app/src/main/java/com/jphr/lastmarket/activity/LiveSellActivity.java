package com.jphr.lastmarket.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.jphr.lastmarket.R;
import com.jphr.lastmarket.openvidu.CustomHttpClient;
import com.jphr.lastmarket.openvidu.CustomWebSocket;
import com.jphr.lastmarket.openvidu.CustomWebSocket2;
import com.jphr.lastmarket.openvidu.LocalParticipant;
import com.jphr.lastmarket.openvidu.PermissionsDialogFragment;
import com.jphr.lastmarket.openvidu.RemoteParticipant;
import com.jphr.lastmarket.openvidu.Session;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LiveSellActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101;
    private static final int MY_PERMISSIONS_REQUEST = 102;
    private final String TAG = "SessionActivity";
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

    private String APPLICATION_SERVER_URL;
    private Session session;
    private CustomHttpClient httpClient;
    private String token;
    private Long productId;
    String session_name = "SessionA";
    String participant_name = "participant_tmp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_live_sell);
        ButterKnife.bind(this);

        productId=getIntent().getLongExtra("productId",0);
        session_name=productId.toString();

        SharedPreferences pref= getSharedPreferences("user_info",MODE_PRIVATE);
        token= pref.getString("token","null");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        askForPermissions();

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
        LocalParticipant localParticipant = new LocalParticipant(participantName, session, this.getApplicationContext(), localVideoView);
        localParticipant.startCamera();
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
//    public void createRemoteParticipantVideo(final RemoteParticipant remoteParticipant) {
//        Handler mainHandler = new Handler(this.getMainLooper());
//        Runnable myRunnable = () -> {
//            View rowView = this.getLayoutInflater().inflate(R.layout.peer_video, null);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 0, 0, 20);
//            rowView.setLayoutParams(lp);
//            int rowId = View.generateViewId();
//            rowView.setId(rowId);
//            views_container.addView(rowView);
//            SurfaceViewRenderer videoView = (SurfaceViewRenderer) ((ViewGroup) rowView).getChildAt(0);
//            remoteParticipant.setVideoView(videoView);
//            videoView.setMirror(false);
//            EglBase rootEglBase = EglBase.create();
//            videoView.init(rootEglBase.getEglBaseContext(), null);
//            videoView.setZOrderMediaOverlay(true);
//            View textView = ((ViewGroup) rowView).getChildAt(1);
//            remoteParticipant.setParticipantNameText((TextView) textView);
//            remoteParticipant.setView(rowView);
//
//            remoteParticipant.getParticipantNameText().setText(remoteParticipant.getParticipantName());
//            remoteParticipant.getParticipantNameText().setPadding(20, 3, 20, 3);
//        };
//        mainHandler.post(myRunnable);
//    }

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