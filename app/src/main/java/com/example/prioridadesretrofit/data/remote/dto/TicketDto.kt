package com.example.prioridadesretrofit.data.remote.dto

data class TicketDto (
    val ticketId: Int,
    val prioridadId: Int? = null,
    val date: String? = null,
    val cliente: String? = null,
    val asunto: String? = null,
    val descripcion: String? = null,
)