package com.example.prioridadesretrofit.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
    @Serializable
    data object TicketList: Screen()
    @Serializable
    data class Ticket(val ticketId: Int): Screen()
    @Serializable
    data object SistemaList: Screen()
    @Serializable
    data class Sistema(val sistemaId: Int): Screen()
    @Serializable
    data object ClienteList: Screen()
    @Serializable
    data class Cliente(val clienteId: Int): Screen()
}