package com.example.ap2_ex4.api;
import android.widget.Toast;
import com.example.ap2_ex4.ConnectionDetails;
import com.example.ap2_ex4.MyApplication;
import com.example.ap2_ex4.R;
import com.example.ap2_ex4.User;
import com.example.ap2_ex4.messages.Message;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class UserAPI {
    private static UserAPI userAPI;
    private Retrofit retrofit;
    private WebServicesApi webServiceAPI;
    private String token;
    private List <Chat> currentChats;
    private User connectedUser;

    public User getConnectedUser() {
        return connectedUser;
    }


    private UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServicesApi.class);
    }

    public static synchronized UserAPI getInstance() {
        if (userAPI == null) {
            userAPI = new UserAPI();
        }

        return userAPI;
    }

    public void registerUser(User user, CallbackResponse callback) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        Call<Void> call = this.webServiceAPI.registerUser(requestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    if (response.code() == 409) {
                        Toast.makeText(MyApplication.context, "Username already exists", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MyApplication.context, "A server error occurred, please try again", Toast.LENGTH_LONG).show();
                    }
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getUser(String username, CallbackResponse callbackConnection) {
        Call<User> call = this.webServiceAPI.getUser("Bearer " + token, username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    connectedUser = response.body();
                    callbackConnection.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context, "A server error occurred, please try again", Toast.LENGTH_LONG).show();
                    callbackConnection.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loginUser(ConnectionDetails user, CallbackResponse callback) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        Call<String> call = this.webServiceAPI.loginUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    setToken(response.body());
                    callback.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context, "Username or password doesn't match", Toast.LENGTH_LONG).show();
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setToken(String newToken) {
        this.token = newToken;
    }

    public void getChats(CallbackResponse callback) {
        Call<List<Chat>> call = this.webServiceAPI.getChats("Bearer " + token, "application/json");
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context, "A server error occurred while getting chats", Toast.LENGTH_LONG).show();
                    callback.onResponse(false);
                }
            }
            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public List<Chat> getAllChatsAfterServer() {
        return currentChats;
    }
    public void getMessages(int chatId, CallbackResponse callback) {
        Call<List<Message>> call = this.webServiceAPI.getMessages("Bearer " + token, "application/json", chatId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context, "A server error occurred while getting messages", Toast.LENGTH_LONG).show();
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
