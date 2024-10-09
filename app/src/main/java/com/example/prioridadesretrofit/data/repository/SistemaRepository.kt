package com.example.prioridadesretrofit.data.repository

import com.example.prioridadesretrofit.data.remote.SistemaApi
import com.example.prioridadesretrofit.data.remote.dto.SistemaDto
import com.example.prioridadesretrofit.utils.Resource
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemaApi: SistemaApi
) {
    suspend fun getSistemas() : Resource<List<SistemaDto>> {
        val response = try{
            sistemaApi.getSistemas()
        }catch (e:Exception){
            return Resource.Error("An Unknown error occured")
        }
        return Resource.Success(response)
    }

    suspend fun addCliente(sistemasDto: SistemaDto) : Resource<SistemaDto> {
        val response = try{
            sistemaApi.addSistemas(sistemasDto)
        }catch (e:Exception){
            return Resource.Error("No se ha guardado correctamente")
        }
        return Resource.Success(response)
    }
}