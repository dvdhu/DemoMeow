package com.gunaya.demo.demomeow.data.repositories

import com.gunaya.demo.demomeow.utils.UseCaseResult
import com.gunaya.demo.demomeow.data.entities.Cat
import com.gunaya.demo.demomeow.data.remote.CatApi
import com.gunaya.demo.demomeow.data.remote.CatApi2
import io.reactivex.Observable

// Number of results we want
interface CatRepository2 {
    // Suspend is used to await the result from Deferred
    fun getCatsList(): Observable<List<Cat>>
}

class CatRepository2Impl(private val catApi2: CatApi2) : CatRepository2 {
    override  fun getCatsList(): Observable<List<Cat>> {
        return catApi2.getCats(50)
    }
}
