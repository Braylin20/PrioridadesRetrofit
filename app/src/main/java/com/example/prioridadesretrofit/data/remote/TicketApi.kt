package com.example.prioridadesretrofit.data.remote

import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TicketApi {
    @GET("api/Tickets")
    suspend fun getTickets(): List<TicketDto>
    @POST("api/Tickets")
    suspend fun addTicket(@Body ticketDto: TicketDto) : TicketDto
}