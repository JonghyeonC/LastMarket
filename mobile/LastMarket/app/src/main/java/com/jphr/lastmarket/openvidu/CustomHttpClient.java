package com.jphr.lastmarket.openvidu;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class CustomHttpClient {

    private OkHttpClient client;
    private String baseUrl;
    private String id="OPENVIDUAPP";
    private String pass="MY_SECRET";
    public CustomHttpClient(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            this.client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void httpCall(String url, String method, String contentType, RequestBody body,String token, Callback callback) throws IOException {
        url = url.startsWith("/") ? url.substring(1) : url;
        String credential = Credentials.basic(id, pass);


        Request request = new Request.Builder()
                .url(this.baseUrl + url)
                .header("Content-Type", contentType)
                .header("Authentication",token)
                .header("Authorization",credential)
                .method(method, body)
                .build();

        Log.d(TAG, "httpCall:"+request.url());
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void dispose() {
        this.client.dispatcher().executorService().shutdown();
    }

    private static final String TAG = "CustomHttpClient";
}
