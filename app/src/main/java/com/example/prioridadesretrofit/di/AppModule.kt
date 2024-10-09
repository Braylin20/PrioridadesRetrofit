package com.example.prioridadesretrofit.di

import com.example.prioridadesretrofit.data.remote.ClienteApi
import com.example.prioridadesretrofit.data.remote.PrioridadApi
import com.example.prioridadesretrofit.data.remote.SistemaApi
import com.example.prioridadesretrofit.data.remote.TicketApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val BASE_URL = "https://prioridades-api-fne6etabafgug6f0.eastus-01.azurewebsites.net/"
    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesPrioridadesApi(moshi: Moshi): PrioridadApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadApi::class.java)
    }
    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): TicketApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketApi::class.java)
    }
    @Provides
    @Singleton
    fun providesClienteApi(moshi: Moshi): ClienteApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ClienteApi::class.java)
    }
    @Provides
    @Singleton
    fun providesSistemaApi(moshi: Moshi): SistemaApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemaApi::class.java)
    }

}