package com.gunaya.demo.demomeow.data.remote

import com.gunaya.demo.demomeow.data.entities.Cat
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi2 {
    /* Get route used to retrieve cat images, limit is the number of cats item */
    @GET("images/search")
    fun getCats(@Query("limit") limit: Int)
            : Observable<List<Cat>>
}