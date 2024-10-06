package com.example.prioridadesretrofit.data.remote

import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrioridadApi {
    @GET("api/Prioridades/{id}")
    suspend fun getPrioriad(@Path("id") id: Int): PrioridadDto
    @GET("api/Prioridades")
    suspend fun getPrioriades(): List<PrioridadDto>
    @POST("api/Prioridades")
    suspend fun addPrioridad(@Body prioridad: PrioridadDto): PrioridadDto
}