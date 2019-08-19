package com.gunaya.demo.demomeow.service

import com.gunaya.demo.demomeow.data.entities.Cat
import com.gunaya.demo.demomeow.data.repositories.CatRepository
import com.gunaya.demo.demomeow.data.repositories.CatRepository2
import com.gunaya.demo.demomeow.utils.UseCaseResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author davidhu on 2019-08-19
 */
interface CatService {
    fun getCatsList(): Observable<List<Cat>>
}

class CatServiceImpl(val catRepository: CatRepository2) : CatService {
    override fun getCatsList(): Observable<List<Cat>> {
        return catRepository.getCatsList().subscribeOn(Schedulers.io())
    }

}
