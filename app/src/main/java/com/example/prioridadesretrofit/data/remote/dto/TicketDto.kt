package com.example.prioridadesretrofit.data.remote.dto

data class TicketDto (
    val ticketId: Int,
    val prioridadesId: Int? = null,
    val date: String? = null,
    val clientesId: Int? = null,
    val asunto: String? = null,
    val descripcion: String? = null,
)