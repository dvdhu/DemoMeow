package com.gunaya.demo.demomeow

/**
 *
 * @author davidhu on 2019-08-19
 */

import com.google.gson.GsonBuilder
import com.gunaya.demo.demomeow.data.remote.CatApi
import com.gunaya.demo.demomeow.data.remote.CatApi2
import com.gunaya.demo.demomeow.data.repositories.CatRepository
import com.gunaya.demo.demomeow.data.repositories.CatRepository2
import com.gunaya.demo.demomeow.data.repositories.CatRepository2Impl
import com.gunaya.demo.demomeow.data.repositories.CatRepositoryImpl
import com.gunaya.demo.demomeow.presentation.main.MainViewModel
import com.gunaya.demo.demomeow.presentation.main.MainViewModel2
import com.gunaya.demo.demomeow.service.CatService
import com.gunaya.demo.demomeow.service.CatServiceImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

val mockAppModules = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService<CatApi>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = CAT_API_BASE_URL
        )
        createWebService<CatApi2>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = CAT_API_BASE_URL
        )

    }
    // Tells Koin how to create an instance of CatRepository
    factory<CatRepository> { CatRepositoryImpl(catApi = get()) }
    factory<CatRepository2> { CatRepository2Impl(catApi2 = get()) }
    factory<CatService> { MockCatServiceImpl(catRepository = get()) }
    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { MainViewModel(catRepository = get()) }
    viewModel { MainViewModel2(catService = get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}