package com.gunaya.demo.demomeow

import com.gunaya.demo.demomeow.data.entities.Cat
import com.gunaya.demo.demomeow.data.repositories.CatRepository2
import com.gunaya.demo.demomeow.service.CatService
import io.reactivex.Observable

class MockCatServiceImpl(val catRepository: CatRepository2) : CatService {
    override fun getCatsList(): Observable<List<Cat>> {
        return Observable.just(listOf(Cat("12","url")))
    }
}