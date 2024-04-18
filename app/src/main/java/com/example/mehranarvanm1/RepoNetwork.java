package com.example.mehranarvanm1;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mehranarvanm1.mdoel.SignUpPostModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RepoNetwork {

    private static RepoNetwork repoNetwork;

    private OkHttpClient client;
    private Gson gson;

    private RepoNetwork() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public static RepoNetwork getInstance() {
        if (repoNetwork == null) repoNetwork = new RepoNetwork();
        return repoNetwork;
    }


    public void SignUp(SignUpPostModel model, CallBackNetwork<String> callBackNetwork) {
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(gson.toJson(model), MediaType.parse("application/json"));
        builder.url("http://wsk2019.mad.hakta.pro/api/users").post(requestBody);
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBackNetwork.onFail(e, true);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) callBackNetwork.onResponse("Success");
                else callBackNetwork.onFail(new Throwable(response.body().string()), false);
            }
        });
    }

    public void sendSMS(String country_code, String phone, CallBackNetwork<String> callBackNetwork) {
        Request.Builder builder = new Request.Builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("countryCode", country_code);
            jsonObject.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        builder.url("http://wsk2019.mad.hakta.pro/api/user/smsCode").post(requestBody);
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("TAG", "onFailure: " + e.getMessage());
                callBackNetwork.onFail(e, true);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) callBackNetwork.onResponse("Success");
                else callBackNetwork.onFail(new Throwable(response.body().string()), false);
            }
        });
    }

    public void activeCode(String code, CallBackNetwork<String> callBackNetwork) {
        Request.Builder builder = new Request.Builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        builder.url("http://wsk2019.mad.hakta.pro/api/user/activation").put(requestBody);
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBackNetwork.onFail(e, true);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) callBackNetwork.onResponse("Success");
                else callBackNetwork.onFail(new Throwable(response.body().string()), false);
            }
        });
    }

    public void Login(String email, String password, CallBackNetwork<String> callBackNetwork) {
        Request.Builder builder = new Request.Builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        builder.url("http://wsk2019.mad.hakta.pro/api/user/login").post(requestBody);
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBackNetwork.onFail(e, true);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.body().string());
                        callBackNetwork.onResponse(jsonObject1.getString("token"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else callBackNetwork.onFail(new Throwable(response.body().string()), false);
            }
        });
    }
}
