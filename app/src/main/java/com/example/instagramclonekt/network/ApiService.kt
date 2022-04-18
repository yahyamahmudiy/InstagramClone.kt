package com.example.instagramclonekt.network

import com.example.instagramclonekt.model.FirebaseRequest
import com.example.instagramclonekt.model.FirebaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiService {

    @Headers("Authorization:$ACCESS_KEY")
    @POST("send")
    fun sendNotification(@Body firebaseRequest: FirebaseRequest): Call<FirebaseResponse>

    companion object {
        const val ACCESS_KEY =
            "key=AAAAmfi8h4Y:APA91bHN-K5WObahSuFPn5i6UPIU0lD0YKAAc53NYeUKQ8iY6NhPlnY_OCJKHAxgZluQfa0-SzG-LHXI8TpCh53552oV1vm7KS3RdiY9m8ipDXQQ1EcgEsBit7VaKmrn2tAuKtunKh4Y"
    }
}