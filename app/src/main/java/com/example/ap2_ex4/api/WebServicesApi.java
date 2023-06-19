package com.example.ap2_ex4.api;

import com.example.ap2_ex4.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServicesApi {
// @GET
// Call<List<SingleContactInList>> getContacts();
// @POST
//Call<Void> createContact(@Body SingleContactInList post);
// @DELETE
//Call<Void> deleteContact(@Path("id") int id);
@POST("Users/")
Call<String> registerUser(@Body RequestBody user);
@POST("Tokens/")
Call<String> loginUser(@Body RequestBody user);
@GET("Users/{username}")
Call<User> getUser(@Header("authorization") String authHeader, @Path("username") String username);
}