package com.example.prioridadesretrofit.data.remote

import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClienteApi {
    @GET("api/clientes")
    suspend fun getClientes(): List<ClienteDto>
    @POST("api/clientes")
    suspend fun addCliente(@Body clienteDto: ClienteDto) : ClienteDto
}