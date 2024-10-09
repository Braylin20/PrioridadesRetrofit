package com.example.prioridadesretrofit.data.remote

import com.example.prioridadesretrofit.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SistemaApi {
    @GET("api/Sistemass")
    suspend fun getSistemas(): List<SistemaDto>
    @POST("api/Sistemas")
    suspend fun addSistemas(@Body sistemasDto: SistemaDto) : SistemaDto
}