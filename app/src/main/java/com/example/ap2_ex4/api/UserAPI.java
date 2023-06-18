package com.example.ap2_ex4.api;
import com.example.ap2_ex4.MyApplication;
import com.example.ap2_ex4.R;
import com.example.ap2_ex4.User;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private Retrofit retrofit;
    private WebServicesApi webServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServicesApi.class);
    }

    public void registerUser(User user) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        Call<String> call = this.webServiceAPI.registerUser(requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse( Call<String> call,  Response<String> response) {
                if (response.isSuccessful()) {
//                    String registeredUser = response.body();
                    System.out.println(response.body());
                } else {
                    System.out.println(2);                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
