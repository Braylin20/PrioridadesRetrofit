package com.example.prioridadesretrofit.presentation.prioridad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto

@Composable
fun PrioridadListScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    goToPrioridadScreen: (Int) -> Unit,
    createPrioridad: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBody(
        uiState = uiState,
        prioridades = uiState.prioridades,
        onAddArticulo = createPrioridad,
        onVerPrioridad = goToPrioridadScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBody(
    uiState: PrioridadViewModel.UiState,
    prioridades: List<PrioridadDto>,
    onAddArticulo: () -> Unit,
    onVerPrioridad: (Int) -> Unit,
) {

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddArticulo) {
                Icon(Icons.Filled.Add, "Agregar nueva entidad")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ID", modifier = Modifier.weight(0.10f))
                Text(text = "Descripción", modifier = Modifier.weight(0.300f))
                Text(text = "DiasCompromiso", modifier = Modifier.weight(0.30f))
            }

            Divider()

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


            Text(text = uiState.message?:"", color = Color.Red)


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                items(prioridades) { prioridad ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerPrioridad(prioridad.prioridadId) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = prioridad.prioridadId.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = prioridad.descripcion, modifier = Modifier.weight(0.300f))
                        Text(text = prioridad.diasCompromiso.toString(), modifier = Modifier.weight(0.30f))
                    }
                }
            }
        }
    }
}