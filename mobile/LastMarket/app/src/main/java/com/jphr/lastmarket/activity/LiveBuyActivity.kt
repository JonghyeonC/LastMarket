package com.jphr.lastmarket.activity

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import butterknife.BindView
import butterknife.ButterKnife
import com.jphr.lastmarket.R
import com.jphr.lastmarket.openvidu.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.webrtc.EglBase
import org.webrtc.MediaStream
import org.webrtc.SurfaceViewRenderer
import java.io.IOException
import java.util.*

private const val TAG = "LiveBuyActivity"
class LiveBuyActivity : AppCompatActivity() {
    private var APPLICATION_SERVER_URL: String? = null
    private var session: Session? = null
    private var httpClient: CustomHttpClient? = null
    private val MY_PERMISSIONS_REQUEST_CAMERA = 100
    private val MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101
    private val MY_PERMISSIONS_REQUEST = 102

    @BindView(R.id.views_container)
    var views_container: LinearLayout? = null

//    @BindView(R.id.start_finish_call)
//    var start_finish_call: Button? = null

    var application_server_url = R.string.application_server_url

    @BindView(R.id.local_gl_surface_view)
    var localVideoView: SurfaceViewRenderer? = null

    @BindView(R.id.main_participant)
    var main_participant: TextView? = null

    @BindView(R.id.peer_container)
    var peer_container: FrameLayout? = null
    var session_name="SessionA"
    var participant_name="participant_tmp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_buy)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        askForPermissions()
        ButterKnife.bind(this)

        if (arePermissionGranted()) {
            initViews()
            APPLICATION_SERVER_URL = application_server_url.toString()
            httpClient = CustomHttpClient(APPLICATION_SERVER_URL)
            val sessionId: String = session_name
            getToken(sessionId)
        } else {
            val permissionsFragment: DialogFragment = PermissionsDialogFragment()
            permissionsFragment.show(supportFragmentManager, "Permissions Fragment")
        }

    }

    fun askForPermissions() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                MY_PERMISSIONS_REQUEST
            )
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECORD_AUDIO),
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO
            )
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST_CAMERA
            )
        }
    }

    fun buttonPressed(view: View?) {

    }

    private fun getToken(sessionId: String) {
        try {
            // Session Request
            val sessionBody = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{\"customSessionId\": \"$sessionId\"}"
            )
            this.httpClient?.httpCall(
                "/api/sessions",
                "POST",
                "application/json",
                sessionBody,
                object : Callback {
                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        Log.d(TAG, "responseString: " + response.body!!.string())

                        // Token Request
                        val tokenBody =
                            RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), "{}")
                        httpClient!!.httpCall(
                            "/api/sessions/$sessionId/connections",
                            "POST",
                            "application/json",
                            tokenBody,
                            object : Callback {
                                override fun onResponse(call: Call, response: Response) {
                                    var responseString: String? = null
                                    try {
                                        responseString = response.body!!.string()
                                    } catch (e: IOException) {
                                        Log.e(TAG, "Error getting body", e)
                                    }
                                    getTokenSuccess(responseString, sessionId)
                                }

                                override fun onFailure(call: Call, e: IOException) {
                                    Log.e(TAG, "Error POST /api/sessions/SESSION_ID/connections", e)
                                    APPLICATION_SERVER_URL?.let { connectionError(it) }
                                }
                            })
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        Log.e(TAG, "Error POST /api/sessions", e)
                        APPLICATION_SERVER_URL?.let { connectionError(it) }
                    }
                })
        } catch (e: IOException) {
            Log.e(TAG, "Error getting token", e)
            e.printStackTrace()
            APPLICATION_SERVER_URL?.let { connectionError(it) }
        }
    }

    private fun getTokenSuccess(token: String?, sessionId: String) {
        // Initialize our session
        session = Session(sessionId, token, views_container, this)

        // Initialize our local participant and start local camera
        val participantName: String = participant_name
        val localParticipant =
            LocalParticipant(participantName, session, this.applicationContext, localVideoView)
        localParticipant.startCamera()
        runOnUiThread {

            // Update local participant view
            main_participant?.setText(participant_name.toString())
            main_participant?.setPadding(20, 3, 20, 3)
        }

        // Initialize and connect the websocket to OpenVidu Server
        startWebSocket()
    }

    private fun startWebSocket() {
        val webSocket = CustomWebSocket(session, this)
        webSocket.execute()
        session?.setWebSocket(webSocket)
    }

    private fun connectionError(url: String) {
        val myRunnable = Runnable {
            val toast = Toast.makeText(this, "Error connecting to $url", Toast.LENGTH_LONG)
            toast.show()
            viewToDisconnectedState()
        }
        Handler(this.mainLooper).post(myRunnable)
    }

    private fun initViews() {
        val rootEglBase = EglBase.create()
        localVideoView?.init(rootEglBase.eglBaseContext, null)
        localVideoView?.setMirror(true)
        localVideoView?.setEnableHardwareScaler(true)
        localVideoView?.setZOrderMediaOverlay(true)
    }

    fun viewToDisconnectedState() {
        runOnUiThread {
            localVideoView?.clearImage()
            localVideoView?.release()

            main_participant?.text = null
            main_participant?.setPadding(0, 0, 0, 0)
        }
    }



//-----------------------------------------상대 영상 받기----------------------------------------
    fun createRemoteParticipantVideo(remoteParticipant: RemoteParticipant) {
        val mainHandler = Handler(this.mainLooper)
        val myRunnable = Runnable {
            val rowView: View =
                this.layoutInflater.inflate(R.layout.peer_video, null)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 0, 0, 20)
            rowView.layoutParams = lp
            val rowId = View.generateViewId()
            rowView.id = rowId
            views_container?.addView(rowView)
            val videoView =
                (rowView as ViewGroup).getChildAt(0) as SurfaceViewRenderer
            remoteParticipant.setVideoView(videoView)
            videoView.setMirror(false)
            val rootEglBase = EglBase.create()
            videoView.init(rootEglBase.eglBaseContext, null)
            videoView.setZOrderMediaOverlay(true)
            val textView = rowView.getChildAt(1)
            remoteParticipant.setParticipantNameText(textView as TextView)
            remoteParticipant.setView(rowView)
            remoteParticipant.getParticipantNameText()
                .setText(remoteParticipant.getParticipantName())
            remoteParticipant.getParticipantNameText().setPadding(20, 3, 20, 3)
        }
        mainHandler.post(myRunnable)
    }

    fun setRemoteMediaStream(stream: MediaStream, remoteParticipant: RemoteParticipant) {
        val videoTrack = stream.videoTracks[0]
        videoTrack.addSink(remoteParticipant.getVideoView())
        runOnUiThread {
            remoteParticipant.getVideoView().setVisibility(View.VISIBLE)
        }
    }

    fun leaveSession() {
        if (this.session != null) {
            this.session!!.leaveSession()
        }
        if (this.httpClient != null) {
            this.httpClient!!.dispose()
        }
        viewToDisconnectedState()
    }

    private fun arePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_DENIED
    }

    override fun onDestroy() {
        leaveSession()
        super.onDestroy()
    }

    override fun onBackPressed() {
        leaveSession()
        super.onBackPressed()
    }

    override fun onStop() {
        leaveSession()
        super.onStop()
    }
}