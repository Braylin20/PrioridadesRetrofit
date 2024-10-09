package com.example.prioridadesretrofit.data.repository

import com.example.prioridadesretrofit.data.remote.TicketApi
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import com.example.prioridadesretrofit.utils.Resource
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: TicketApi
) {
    suspend fun getTickets() :Resource<List<TicketDto>>{
        val response = try{
            ticketApi.getTickets()
        }catch (e: Exception){
            return Resource.Error("No se ha podido realizar esta acción")
        }
        return Resource.Success(response)
    }

    suspend fun addTicket(ticketDto: TicketDto): Resource<TicketDto>{
        val response = try{
            ticketApi.addTicket(ticketDto)
        }catch (e: Exception){
            return Resource.Error("No se ha guardado correctamente")
        }
        return Resource.Success(response)
    }
}