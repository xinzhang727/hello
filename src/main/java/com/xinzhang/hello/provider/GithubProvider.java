package com.xinzhang.hello.provider;


import com.alibaba.fastjson.JSON;
import com.xinzhang.hello.dto.AccessTokenDTO;
import com.xinzhang.hello.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                System.out.println(string);
                return string;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
    }


    public GithubUser getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();

                GithubUser user=JSON.parseObject(string,GithubUser.class);
                Long id=user.getId();
                System.out.println(id);
                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }


}
