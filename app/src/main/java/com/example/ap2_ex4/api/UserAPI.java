package com.example.ap2_ex4.api;

import retrofit2.Call;

import java.util.List;

import okhttp3.MediaType;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.RequestBody;

import java.io.IOException;

import okhttp3.OkHttpClient;

import android.widget.Toast;

import com.google.gson.Gson;
import com.example.ap2_ex4.R;
import com.example.ap2_ex4.User;
import com.example.ap2_ex4.MyApplication;
import com.example.ap2_ex4.ConnectionDetails;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private String token;
    private Retrofit retrofit;
    private User connectedUser;
    private boolean firstContacts;
    private boolean firstMessages;
    private static UserAPI userAPI;
    private List<Chat> currentChats;
    private LastAddedContact lastAdded;
    private WebServicesApi webServiceAPI;
    private List<MessageFromServer> currentMessages;

    private UserAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        webServiceAPI = retrofit.create(WebServicesApi.class);
    }

    public static synchronized UserAPI getInstance() {
        if (userAPI == null) {
            userAPI = new UserAPI();
        }
        return userAPI;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public LastAddedContact getLastAdded() {
        return lastAdded;
    }

    public List<MessageFromServer> getCurrentMessages() {
        return currentMessages;
    }

    public boolean isFirstContacts() {
        return firstContacts;
    }

    public boolean isFirstMessages() {
        return firstMessages;
    }

    public void setRetrofit(String BaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl + "/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServicesApi.class);
    }

    public void setFirstContacts(boolean firstContacts) {
        this.firstContacts = firstContacts;
    }

    public void setFirstMessages(boolean firstMessages) {
        this.firstMessages = firstMessages;
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
                        Toast.makeText(MyApplication.context,
                                "Username already exists", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MyApplication.context,
                                "A server error occurred, please try again", Toast.LENGTH_LONG).show();
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
                    firstContacts = true;
                    connectedUser = response.body();
                    callbackConnection.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context,
                            "A server error occurred, please try again", Toast.LENGTH_LONG).show();
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
        Call<String> call = this.webServiceAPI.loginUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    setToken(response.body());
                    getUser(user.getUsername(), success -> {
                        if (success) {
                            callback.onResponse(true);
                        }
                    });
                } else {
                    Toast.makeText(MyApplication.context, "Username or password doesn't match",
                            Toast.LENGTH_LONG).show();
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
        Call<List<Chat>> call = this.webServiceAPI.getChats("Bearer " + token,
                "application/json");
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    currentChats = response.body();
                    callback.onResponse(true);
                } else {
                    Toast.makeText(MyApplication.context,
                            "A server error occurred while getting chats", Toast.LENGTH_LONG).show();
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addContact(String username, CallbackResponse callback) {
        TempContact tempContact = new TempContact(username);
        Call<LastAddedContact> call = this.webServiceAPI.addContact("Bearer " + token, tempContact);
        call.enqueue(new Callback<LastAddedContact>() {
            @Override
            public void onResponse(Call<LastAddedContact> call, Response<LastAddedContact> response) {
                if (response.isSuccessful()) {
                    lastAdded = response.body();
                    callback.onResponse(true);
                } else {
                    try {
                        // Parse the error body
                        String errorBody = response.errorBody().string();
                        Toast.makeText(MyApplication.context, "Server Error: " + errorBody,
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<LastAddedContact> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Request Failure: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void deleteContact(String id, CallbackResponse callback) {
        Call<Void> call = this.webServiceAPI.deleteContact("Bearer " + token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    try {
                        // Parse the error body
                        String errorBody = response.errorBody().string();
                        Toast.makeText(MyApplication.context, "Server Error: " + errorBody,
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
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

    public void getMessages(String chatId, CallbackResponse callback) {
        Call<List<MessageFromServer>> call = this.webServiceAPI.getMessages(
                "Bearer " + token, "application/json", chatId);
        call.enqueue(new Callback<List<MessageFromServer>>() {
            @Override
            public void onResponse(Call<List<MessageFromServer>> call,
                                   Response<List<MessageFromServer>> response) {
                if (response.isSuccessful()) {
                    currentMessages = response.body();
                    callback.onResponse(true);

                } else {
                    currentMessages = null;
                    Toast.makeText(MyApplication.context, "A server error occurred while getting messages",
                            Toast.LENGTH_LONG).show();
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<List<MessageFromServer>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public List<Chat> getAllChatsAfterServer() {
        return currentChats;
    }

    public void addMessage(String msg, String chatId, CallbackResponse callback) {
        MessageString messageString = new MessageString(msg);
        Call<Void> call = this.webServiceAPI.addMessage("Bearer " + token, messageString, chatId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(MyApplication.context, "Server Error: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Request Failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
