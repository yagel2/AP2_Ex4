package com.example.ap2_ex4.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServicesApi {
// @GET
// Call<List<SingleContactInList>> getContacts();
// @POST
//Call<Void> createContact(@Body SingleContactInList post);
// @DELETE
//Call<Void> deleteContact(@Path("id") int id);
@POST("Users/")
Call<String> registerUser(@Body RequestBody user);
}