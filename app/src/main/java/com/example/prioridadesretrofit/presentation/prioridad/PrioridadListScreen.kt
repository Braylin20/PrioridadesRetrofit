package com.example.prioridadesretrofit.presentation.prioridad

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import kotlinx.coroutines.delay

@Composable
fun PrioridadListScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    goToPrioridadScreen: (Int) -> Unit,
    createPrioridad: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBody(
        uiState = uiState,
        onAddArticulo = createPrioridad,
        goToPrioridadScreen = goToPrioridadScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBody(
    uiState: PrioridadViewModel.UiState,
    onAddArticulo: () -> Unit,
    goToPrioridadScreen: (Int) -> Unit,
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
                items(uiState.prioridades) { prioridad ->
                    PrioridadListRow(prioridad, goToPrioridadScreen)
                }
            }
        }
    }
}

@Composable
fun PrioridadListRow(
    it: PrioridadDto,
    goToPrioridadScreen: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToPrioridadScreen(it.prioridadId) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = it.prioridadId.toString(), modifier = Modifier.weight(0.10f))
        Text(text = it.descripcion, modifier = Modifier.weight(0.300f))
        Text(text = it.diasCompromiso.toString(), modifier = Modifier.weight(0.30f))
    }
}
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete:(T)-> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {value->
            if(value == SwipeToDismissBoxValue.EndToStart){
                isRemoved = true
                true
            }else
                false
        }
    )
    LaunchedEffect(key1 = isRemoved){
        if(isRemoved){
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(state)
            },
            content = {content(item)},
        )
    }

}

@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {
    val color = if(swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
        Color.Red
    }else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = Color.White,
        )
    }
}
