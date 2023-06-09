package com.example.ap2_ex4.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Path;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.DELETE;
import com.example.ap2_ex4.User;
import com.example.ap2_ex4.ConnectionDetails;

public interface WebServicesApi {
    @POST("Users/")
    Call<Void> registerUser(@Body RequestBody user);

    @POST("Tokens/")
    Call<String> loginUser(@Body ConnectionDetails nameAndPassword);

    @GET("Users/{username}")
    Call<User> getUser(@Header("authorization") String authHeader,
                       @Path("username") String username);

    @GET("Chats")
    Call<List<Chat>> getChats(@Header("authorization") String authorization,
                              @Header("accept") String accept);

    @POST("Chats")
    Call<LastAddedContact> addContact(@Header("authorization") String authorization,
                          @Body TempContact username);

    @DELETE("Chats/{id}")
    Call<Void> deleteContact(@Header("authorization") String authorization,
                             @Path("id") String id);

    @POST("Chats/{id}/Messages")
    Call<Void> addMessage(@Header("authorization") String authorization,
                          @Body MessageString msg,
                          @Path("id") String id);

    @GET("Chats/{id}/Messages")
    Call<List<MessageFromServer>> getMessages(
            @Header("authorization") String authorization,
            @Header("accept") String accept,
            @Path("id") String id);
}