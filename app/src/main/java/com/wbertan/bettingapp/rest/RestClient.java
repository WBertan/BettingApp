package com.wbertan.bettingapp.rest;

import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.IRestCallback;
import com.wbertan.bettingapp.props.PropsRestRequestCode;
import com.wbertan.bettingapp.props.PropsRestRequestUrl;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class RestClient {
    private RestClient() {}

    public static RestClient getInstance() {
        return new RestClient();
    }

    private volatile boolean mFetchingUrl = true;
    private String mResponse = null;
    private String mError = null;

    public void doGet(@PropsRestRequestUrl final String aUrl, IRestCallback<String> aCallback, @PropsRestRequestCode int aRestRequestCode) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                BufferedReader bufferedReaderResponse=null;
                try {
                    URL url = new URL(aUrl);
                    URLConnection urlConnection = url.openConnection();

                    bufferedReaderResponse = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilderResponse = new StringBuilder();
                    String bufferLine;
                    while((bufferLine = bufferedReaderResponse.readLine()) != null) {
                        stringBuilderResponse.append(bufferLine).append('\n');
                    }
                    mResponse = stringBuilderResponse.toString();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mError = ex.getMessage();
                    mResponse = null;
                } finally {
                    try {
                        if (bufferedReaderResponse != null) {
                            bufferedReaderResponse.close();
                        }
                    } catch (IOException ignored) {}
                    mFetchingUrl = false;
                }
            }
        });
        thread.start();
        while(mFetchingUrl);
        if(mResponse != null) {
            aCallback.onSuccess(aRestRequestCode, mResponse);
        } else {
            aCallback.onError(aRestRequestCode, new CallbackError(-1, mError));
        }
    }

    public void doPost(@PropsRestRequestUrl final String aUrl, final List<NameValuePair> aDataToSend, IRestCallback<String> aCallback, @PropsRestRequestCode int aRestRequestCode) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                BufferedReader bufferedReaderResponse=null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(aUrl);
                    if (aDataToSend != null) {
                        httpPost.setEntity(new UrlEncodedFormEntity(aDataToSend));
                    }

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    bufferedReaderResponse = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    StringBuilder stringBuilderResponse = new StringBuilder();
                    String bufferLine;
                    while((bufferLine = bufferedReaderResponse.readLine()) != null) {
                        stringBuilderResponse.append(bufferLine).append('\n');
                    }
                    mResponse = stringBuilderResponse.toString();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mError = ex.getMessage();
                    mResponse = null;
                } finally {
                    try {
                        if (bufferedReaderResponse != null) {
                            bufferedReaderResponse.close();
                        }
                    } catch (IOException ignored) {}
                    mFetchingUrl = false;
                }
            }
        });
        thread.start();
        while(mFetchingUrl);
        if(mResponse != null) {
            aCallback.onSuccess(aRestRequestCode, mResponse);
        } else {
            aCallback.onError(aRestRequestCode, new CallbackError(-1, mError));
        }
    }
}
