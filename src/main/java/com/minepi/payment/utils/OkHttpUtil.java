package com.minepi.payment.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    private static OkHttpClient client;

	static {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(10000L, TimeUnit.SECONDS);
		client = builder.build();
	}



    public static String doGet(String url, String authorization) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", authorization)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doPost(String url, String json, String authorization) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", authorization)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();

    }
}
