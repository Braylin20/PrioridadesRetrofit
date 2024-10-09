package com.example.prioridadesretrofit.presentation.sistema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prioridadesretrofit.data.remote.dto.SistemaDto
import com.example.prioridadesretrofit.data.repository.SistemaRepository
import com.example.prioridadesretrofit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init{
        getSistemas()
    }

    private fun getSistemas() {
        viewModelScope.launch {
            val result = sistemaRepository.getSistemas()
            when(result){
                is Resource.Success ->_uiState.update {
                    it.copy(sistemas =  result.data?: emptyList())
                }
                is Resource.Error -> _uiState.update {
                    it.copy(message = "Ha ocurrido un error")
                }
                is Resource.Loading -> TODO()

            }
        }
    }

    data class UiState(
        val sistemaId: Int? = null,
        val nombreSistema: String? = null,
        val descripcion: String? = null,
        val message: String? = null,
        val nombreSistemaErrorMessage: String? = null,
        val descripcionErrorMessage: String? = null,
        val sistemas: List<SistemaDto> = emptyList()
    )

    fun UiState.toEntity() = SistemaDto(
        sistemaId = sistemaId?: 0,
        nombreSistema = nombreSistema?: "",
        descripcion = descripcion?:""
    )
}