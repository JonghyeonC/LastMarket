package com.jphr.lastmarket.openvidu;

import android.view.View;
import android.widget.TextView;

import org.webrtc.SurfaceViewRenderer;

public class RemoteParticipant2 extends Participant2 {

    private View view;
    private SurfaceViewRenderer videoView;
    private TextView participantNameText;

    public RemoteParticipant2(String connectionId, String participantName, Session2 session) {
        super(connectionId, participantName, session);
        this.session.addRemoteParticipant(this);
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public SurfaceViewRenderer getVideoView() {
        return this.videoView;
    }

    public void setVideoView(SurfaceViewRenderer videoView) {
        this.videoView = videoView;
    }

    public TextView getParticipantNameText() {
        return this.participantNameText;
    }

    public void setParticipantNameText(TextView participantNameText) {
        this.participantNameText = participantNameText;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
