package com.demol.map.utils.http;



import com.demol.map.base.MyApplication;
import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by whd on 2017/12/23.
 */

public class HttpUtils {

    public static String ipAddress = "http://47.98.131.11:8087";

    public static String getInputStream(InputStream is) {
        String result = null;
        byte[] buffer = new byte[1024 * 10];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            result = new String(bos.toByteArray(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String baseUrl = "http://47.98.131.11:8087";

    public static String requestPost(String url, Map<String, Object> params) {
        String result = null;
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            HttpService httpService = retrofit.create(HttpService.class);
            String CONTENT_TYPE = "application/json";
            Gson gson = new Gson();
            String content = gson.toJson(params);
            RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE), content);
            Call<ResponseBody> call=httpService.postQuest(url,body);
            retrofit2.Response<ResponseBody> response = call.execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String requestGet(String url) {
        String result = null;
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            HttpService httpService = retrofit.create(HttpService.class);
            Call<ResponseBody> call=httpService.getRequest(url);
            retrofit2.Response<ResponseBody> response = call.execute();
            result = response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String uploadFile(String userId, File file){
        String result=null;
        try {
            Retrofit retrofit=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            HttpService userService=retrofit.create(HttpService.class);
            // 创建 RequestBody，用于封装构建RequestBody
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            // 执行请求
            Call<ResponseBody> call = userService.uploadFile(userId, body);
            retrofit2.Response<ResponseBody> response=call.execute();
             boolean success=response.isSuccessful();
            if (success){
                result=response.code()+"";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
