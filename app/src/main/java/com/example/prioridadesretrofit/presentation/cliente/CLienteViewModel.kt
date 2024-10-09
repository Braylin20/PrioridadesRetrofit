package com.example.prioridadesretrofit.presentation.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prioridadesretrofit.data.remote.ClienteApi
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import com.example.prioridadesretrofit.data.repository.ClienteRepository
import com.example.prioridadesretrofit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CLienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
    }

    private fun getClientes() {
        viewModelScope.launch {
            val result = clienteRepository.getClientes()
            when (result) {
                is Resource.Success -> _uiState.update {
                    it.copy(clientes = result.data ?: emptyList())
                }

                is Resource.Error -> _uiState.update {
                    it.copy(message = "Ha ocurrido un error")
                }

                is Resource.Loading -> TODO()

            }
        }
    }

    fun addCliente() {
        viewModelScope.launch {
            val result = clienteRepository.addCliente(_uiState.value.toEntity())
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

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(
                nombre = nombre,
                nombreError = null
            )
        }
    }
    fun onTelefonoChange(telefono: String) {
        _uiState.update {
            it.copy(
                telefono = telefono,
                telefonoError = null
            )
        }
    }
    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = null
            )
        }
    }
    fun onDireccionChange(direccion: String) {
        _uiState.update {
            it.copy(
                direccion = direccion,
                direccionError = null
            )
        }
    }
    private fun nuevo(){
        _uiState.update {
            it.copy(
                nombre = null,
                telefono = null,
                celular = null,
                email = null,
                rnc = null,
                direccion = null,
            )
        }
    }

    data class UiState(
        val clienteId: Int? = null,
        val nombre: String? = null,
        val telefono: String? = null,
        val celular: String? = null,
        val email: String? = null,
        val rnc: String? = null,
        val direccion: String? = null,
        val message: String? = null,
        val isLoading: Boolean = false,
        val nombreError: String? = null,
        val telefonoError: String? = null,
        val celularError: String? = null,
        val emailError: String? = null,
        val rncError: String? = null,
        val direccionError: String? = null,
        val clientes: List<ClienteDto> = emptyList()
    )

    private fun UiState.toEntity() = ClienteDto(
        clienteId = clienteId ?: 0,
        nombre = nombre ?: "",
        telefono = telefono ?: "",
        celular = celular ?: "",
        email = email ?: "",
        rnc = rnc ?: "",
        direccion = direccion ?: ""
    )
}