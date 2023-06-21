package com.example.ap2_ex4.api;

import com.example.ap2_ex4.ConnectionDetails;
import com.example.ap2_ex4.User;
import com.example.ap2_ex4.messages.Message;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServicesApi {
@POST("Users/")
Call<Void> registerUser(@Body RequestBody user);
@POST("Tokens/")
Call<String> loginUser(@Body ConnectionDetails nameAndPassword);
@GET("Users/{username}")
Call<User> getUser(@Header("authorization") String authHeader, @Path("username") String username);
@GET("Chats")
Call <List<Chat>> getChats(@Header("authorization") String authorization, @Header("accept")String accept);
@POST("Chats")
Call<ContactFormatFromServer> addContact(@Header("authorization") String authorization, @Body RequestBody username);
@GET("Chats/{id}/Messages")
Call <List<Message>> getMessages(@Header("authorization") String authorization, @Header("accept")String accept, @Path("id") int id); //check after yagel
//@POST("Chats/{id}/Messages")
//    Call <NewMessageFromServer> addMessage()
}