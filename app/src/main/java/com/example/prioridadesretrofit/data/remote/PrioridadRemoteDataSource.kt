package com.example.prioridadesretrofit.data.remote

import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import javax.inject.Inject

class PrioridadRemoteDataSource @Inject constructor(
    private val prioridadApi: PrioridadApi
) {
    suspend fun getPrioridades() = prioridadApi.getPrioriades()

    suspend fun addPrioridad(prioridadDto: PrioridadDto) = prioridadApi.addPrioridad(prioridadDto)
}