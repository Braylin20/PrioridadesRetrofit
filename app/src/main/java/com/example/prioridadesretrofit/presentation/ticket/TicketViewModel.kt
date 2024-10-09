package com.example.prioridadesretrofit.presentation.ticket

import androidx.lifecycle.ViewModel
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import com.example.prioridadesretrofit.data.repository.PrioridadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.data.repository.ClienteRepository
import com.example.prioridadesretrofit.data.repository.TicketRepository
import com.example.prioridadesretrofit.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository,
    private val clienteRepository: ClienteRepository
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
    fun save() {
        viewModelScope.launch {
            val result = ticketRepository.addTicket(_uiState.value.toEntity())
            when (result) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            message = "Agregado correctamente",
                        )
                    }
                    nuevo()
                }
                is Resource.Error -> _uiState.update {
                    it.copy(
                        message = "No se ha podido agregar"
                    )
                }
                is Resource.Loading -> TODO()
            }
        }
    }

    suspend fun getPrioridades(): List<PrioridadDto>{
        return viewModelScope.async {
            prioridadRepository.getPrioridadesList().data?: emptyList()
        }.await()

    }
    suspend fun getClientes(): List<ClienteDto>{
        return viewModelScope.async {
            clienteRepository.getClientes().data?: emptyList()
        }.await()
    }
    fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                prioridadesId = null,
                asunto = "",
                descripcion = "",
                clientesId = null,
                date = null
            )
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                descripcionErrorMessage = null
            )
        }
    }
    fun onDateChange(date: String?) {
        _uiState.update {
            it.copy(
                date = date,
                descripcionErrorMessage = null
            )
        }
    }
    fun onClienteChange(clienteId: Int?) {
        _uiState.update {
            it.copy(
                clientesId = clienteId,
                clienteErrorMessage = null
            )
        }
    }
    fun onAsuntoChange(asunto: String?) {
        _uiState.update {
            it.copy(
                asunto = asunto,
                clienteErrorMessage = null
            )
        }
    }
    fun onPrioridadIdChange(prioridadId: Int?) {
        _uiState.update {
            it.copy(
                prioridadesId = prioridadId,
            )
        }
    }
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    data class UiState(
        val ticketId: Int? = null,
        val prioridadesId: Int? = null,
        val date: String? = null,
        val clientesId: Int? = 2,
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

    private fun UiState.toEntity() = TicketDto(
        ticketId = ticketId?: 0,
        prioridadesId = prioridadesId?:0,
        date = date?:"",
        clientesId = clientesId?:0,
        asunto = asunto?:"",
        descripcion= descripcion?:"",
    )
}