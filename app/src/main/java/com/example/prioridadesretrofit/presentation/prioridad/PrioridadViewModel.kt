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
):ViewModel(){

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getPrioridades()
        }
    }

    private fun getPrioridades(){
        viewModelScope.launch {
            val result = prioridadRepository.getPrioridadesList()
            when(result){
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        prioridades = result.data?: emptyList()
                    )
                }
                is Resource.Error -> _uiState.update {
                    it.copy(message = "No hay prioridades")
                }
                is Resource.Loading -> TODO()

            }
        }
    }

    fun addPrioridad(){
        viewModelScope.launch {
            try{
                prioridadRepository.addPrioridad(uiState.value.toEntity())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    data class UiState(
        val prioridadId: Int?= null,
        val descripcion: String? = null,
        val diasCompromiso: Int? = null,
        val prioridades: List<PrioridadDto> = emptyList(),
        val message: String? = null,
        val isLoading: Boolean = false,
        val descripcionError: String? = null,
        val diasCompromisoError: String? = null
    )

    fun UiState.toEntity() = PrioridadDto(
        prioridadId = prioridadId?: 0,
        descripcion = descripcion?:"",
        diasCompromiso = diasCompromiso?: 0
    )
}