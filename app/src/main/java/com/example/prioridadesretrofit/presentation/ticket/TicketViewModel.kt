package com.example.prioridadesretrofit.presentation.ticket

import androidx.lifecycle.ViewModel
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import com.example.prioridadesretrofit.data.repository.PrioridadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.prioridadesretrofit.data.repository.TicketRepository
import com.example.prioridadesretrofit.utils.Resource
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) :ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
    }

    private fun getTickets(){
        viewModelScope.launch {
            val result = ticketRepository.getTickets()
            when(result){
                is Resource.Success -> _uiState.update {
                    it.copy(
                        tickets = result.data?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> _uiState.update {
                    it.copy(message = "No hay prioridades")
                }
                is Resource.Loading -> TODO()
            }
        }
    }

    data class UiState(
        val ticketId: Int? = null,
        val prioridadId: Int? = null,
        val date: String? = null,
        val clienteId: Int? = null,
        val asunto: String? = null,
        val descripcion: String? = null,
        val message: String? = null,
        val dateErrorMessage: String? = null,
        val clienteErrorMessage: String? = null,
        val asuntoErrorMessage: String? = null,
        val descripcionErrorMessage : String? = null,
        val isLoading: Boolean = false,
        val tickets: List<TicketDto> = emptyList()
    )

    fun UiState.toEntity() = TicketDto(
        ticketId = ticketId?: 0,
        prioridadId = prioridadId,
        date = date,
        clienteId = clienteId,
        asunto = asunto,
        descripcion= descripcion,
    )
}