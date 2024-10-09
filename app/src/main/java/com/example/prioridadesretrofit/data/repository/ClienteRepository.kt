package com.example.prioridadesretrofit.data.repository

import com.example.prioridadesretrofit.data.remote.ClienteApi
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.utils.Resource
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: ClienteApi
) {
    suspend fun getClientes() : Resource<List<ClienteDto>> {
        val response = try{
            clienteApi.getClientes()
        }catch (e:Exception){
            return Resource.Error("An Unknown error occured")
        }
        return Resource.Success(response)
    }

    suspend fun addCliente(clienteDto: ClienteDto) : Resource<ClienteDto> {
        val response = try{
            clienteApi.addCliente(clienteDto)
        }catch (e:Exception){
            return Resource.Error("No se ha guardado correctamente")
        }
        return Resource.Success(response)
    }
}