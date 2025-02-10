package com.anas.lostfound.feature_lostfound.data.remote

import com.anas.lostfound.feature_lostfound.data.remote.dto.RemoteItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url


// api to get data from firebase
interface RemoteApi {
    @GET("item.json")
    suspend fun getAllItems(): List<RemoteItem>


    @GET("item/{id}.json")
    suspend fun getSingleItemById(@Path("id") id: Int?): RemoteItem



    @PUT
    suspend fun addItem(@Url url: String, @Body updatedTodo: RemoteItem): Response<Unit>


    @DELETE("item/{id}.json")
    suspend fun deleteItem(@Path("id") id :Int?) : Response<Unit>

    @PUT("item/{id}.json")
    suspend fun updateItem(@Path("id") id :Int?, @Body todoItem: RemoteItem): Response<Unit>



}