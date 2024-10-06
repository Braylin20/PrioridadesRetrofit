package com.example.prioridadesretrofit.data.repository

import android.util.Log
import com.example.prioridadesretrofit.data.entities.PrioridadEntity
import com.example.prioridadesretrofit.data.remote.PrioridadApi
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PrioridadRepository @Inject constructor(
    private val api: PrioridadApi
){

    suspend fun getPrioridadesList() : Resource<List<PrioridadDto>>{
        val response = try{
           api.getPrioriades()
        }catch (e:Exception){
            return Resource.Error("An Unknown error occured") }
        return Resource.Success(response)
    }


}