package com.example.prioridadesretrofit.data.repository

import com.example.prioridadesretrofit.data.remote.PrioridadApi
import com.example.prioridadesretrofit.data.remote.PrioridadRemoteDataSource
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.utils.Resource
import javax.inject.Inject


class PrioridadRepository @Inject constructor(
    private val prioridadRemoteDataSource: PrioridadRemoteDataSource
){

    suspend fun getPrioridadesList() : Resource<List<PrioridadDto>>{
        val response = try{
           prioridadRemoteDataSource.getPrioridades()
        }catch (e:Exception){
            return Resource.Error("An Unknown error occured")
        }
        return Resource.Success(response)
    }

    suspend fun addPrioridad(prioridadDto: PrioridadDto) : Resource<PrioridadDto>{
        val response = try{
            prioridadRemoteDataSource.addPrioridad(prioridadDto)
        }catch (e:Exception){
            return Resource.Error("No se ha guardado correctamente")
        }
        return Resource.Success(response)
    }

}