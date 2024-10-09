package com.example.prioridadesretrofit.presentation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.data.repository.PrioridadRepository
import com.example.prioridadesretrofit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            val result = prioridadRepository.getPrioridadesList()
            when (result) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        prioridades = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(message = "No hay prioridades")
                }

                is Resource.Loading -> TODO()

            }
        }
    }

    fun addPrioridad() {
        viewModelScope.launch {
            if (descriptionExistOrEmpty(uiState.value.descripcion ?: "")) {
                return@launch
            }
            if (uiState.value.diasCompromiso == null) {
                _uiState.update {
                    it.copy(
                        diasCompromisoError = "Este campo es obligatorio"
                    )
                }
                return@launch
            }
            if (uiState.value.diasCompromiso!! < 0) {
                _uiState.update {
                    it.copy(
                        diasCompromisoError = "Este campo debe ser mayor a 0"
                    )
                }
                return@launch
            } else {
                val result = prioridadRepository.addPrioridad(_uiState.value.toEntity())
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
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                descripcionError = null
            )
        }
    }

    fun onDiasCompromisoChange(diasCompromiso: Int) {
        _uiState.update {
            it.copy(
                diasCompromiso = diasCompromiso,
                diasCompromisoError = null
            )
        }
    }

    private suspend fun descriptionExistOrEmpty(descripcion: String): Boolean {
        if (descripcion.isBlank()) {
            _uiState.update {
                it.copy(descripcionError = "La descripción no puede estar vacía")
            }
            return true
        }
        val prioridades = prioridadRepository.getPrioridadesList()

        val exist: Boolean = prioridades.data?.any { it.descripcion == descripcion } != null

        if (!exist) {
            _uiState.update {
                it.copy(descripcionError = "Esta descrpcion ya existe")
            }
            return true
        }

        return false
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                descripcion = null,
                diasCompromiso = null,
                diasCompromisoError = null,
                descripcionError = null,
                message = null
            )
        }
    }

    data class UiState(
        val prioridadId: Int? = null,
        val descripcion: String? = null,
        val diasCompromiso: Int? = null,
        val prioridades: List<PrioridadDto> = emptyList(),
        val message: String? = null,
        val isLoading: Boolean = false,
        val descripcionError: String? = null,
        val diasCompromisoError: String? = null
    )

    private fun UiState.toEntity() = PrioridadDto(
        prioridadId = prioridadId ?: 0,
        descripcion = descripcion ?: "",
        diasCompromiso = diasCompromiso ?: 0
    )
}