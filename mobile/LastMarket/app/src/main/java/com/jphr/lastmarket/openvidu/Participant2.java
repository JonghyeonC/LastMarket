package com.jphr.lastmarket.openvidu;

import android.util.Log;

import org.webrtc.AudioTrack;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant2 {

    protected String connectionId;
    protected String participantName;
    protected Session2 session;
    protected List<IceCandidate> iceCandidateList = new ArrayList<>();
    protected PeerConnection peerConnection;
    protected AudioTrack audioTrack;
    protected VideoTrack videoTrack;
    protected MediaStream mediaStream;

    public Participant2(String participantName, Session2 session) {
        this.participantName = participantName;
        this.session = session;
    }

    public Participant2(String connectionId, String participantName, Session2 session) {
        this.connectionId = connectionId;
        this.participantName = participantName;
        this.session = session;
    }

    public String getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getParticipantName() {
        return this.participantName;
    }

    public List<IceCandidate> getIceCandidateList() {
        return this.iceCandidateList;
    }

    public PeerConnection getPeerConnection() {
        return peerConnection;
    }

    public void setPeerConnection(PeerConnection peerConnection) {
        this.peerConnection = peerConnection;
    }

    public AudioTrack getAudioTrack() {
        return this.audioTrack;
    }

    public void setAudioTrack(AudioTrack audioTrack) {
        this.audioTrack = audioTrack;
    }

    public VideoTrack getVideoTrack() {
        return this.videoTrack;
    }

    public void setVideoTrack(VideoTrack videoTrack) {
        this.videoTrack = videoTrack;
    }

    public MediaStream getMediaStream() {
        return this.mediaStream;
    }

    public void setMediaStream(MediaStream mediaStream) {
        this.mediaStream = mediaStream;
    }

    public void dispose() {
        if (this.peerConnection != null) {
            try {
                this.peerConnection.close();
            } catch (IllegalStateException e) {
                Log.e("Dispose PeerConnection", e.getMessage());
            }
        }
    }
}
