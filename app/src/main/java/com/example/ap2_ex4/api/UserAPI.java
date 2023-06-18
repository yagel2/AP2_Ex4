package com.example.ap2_ex4.api;
import android.widget.Toast;

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
    public boolean reqOk;
    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServicesApi.class);
    }
    public void registerUser(User user) {
        reqOk = false;
        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        Call<String> call = this.webServiceAPI.registerUser(requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse( Call<String> call,  Response<String> response) {
                if (response.isSuccessful()) {
                    reqOk = true;
                }
                else if (response.code() == 409) {
                    Toast.makeText(MyApplication.context, "Username already exists", Toast.LENGTH_LONG).show();
                    reqOk = false;
                } else {
                    Toast.makeText(MyApplication.context, "A server error occurred, please try again", Toast.LENGTH_LONG).show();
                    reqOk = false;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
//package com.example.ap2_ex4.api;
//        import android.widget.Toast;
//
//        import com.example.ap2_ex4.MyApplication;
//        import com.example.ap2_ex4.R;
//        import com.example.ap2_ex4.User;
//        import com.google.gson.Gson;
//        import okhttp3.MediaType;
//        import okhttp3.RequestBody;
//        import retrofit2.Call;
//        import retrofit2.Callback;
//        import retrofit2.Response;
//        import retrofit2.Retrofit;
//        import retrofit2.converter.gson.GsonConverterFactory;
//
//public class UserAPI {
//    private Retrofit retrofit;
//    private WebServicesApi webServiceAPI;
//    private boolean requestSuccessful;
//    public UserAPI() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServicesApi.class);
//    }
//    public boolean registerUser(User user) {
//        Gson gson = new Gson();
//        String jsonBody = gson.toJson(user);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
//        Call<String> call = this.webServiceAPI.registerUser(requestBody);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse( Call<String> call,  Response<String> response) {
//                if (response.isSuccessful()) {
//                    String registeredUser = response.body();
//                    requestSuccessful = true;
//                } else if (response.code() == 409) {
//                    Toast.makeText(MyApplication.context, "Username already exists", Toast.LENGTH_LONG).show();
//                    requestSuccessful = false;
//                } else {
//                    Toast.makeText(MyApplication.context, "A server error occurred, please try again", Toast.LENGTH_LONG).show();
//                    requestSuccessful = false;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                t.printStackTrace();
////                Toast.makeText(MyApplication.context, "A network error occurred, please try again", Toast.LENGTH_LONG).show();
////                requestSuccessful = false;
//            }
//        });
//        return requestSuccessful;
//    }
//
//}
