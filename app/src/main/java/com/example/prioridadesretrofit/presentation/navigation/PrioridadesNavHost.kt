package com.example.prioridadesretrofit.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.prioridadesretrofit.presentation.cliente.ClienteListScreen
import com.example.prioridadesretrofit.presentation.cliente.ClienteScreen
import com.example.prioridadesretrofit.presentation.components.ModalDrawerSheet
import com.example.prioridadesretrofit.presentation.prioridad.PrioridadListScreen
import com.example.prioridadesretrofit.presentation.prioridad.PrioridadScreen
import com.example.prioridadesretrofit.presentation.sistema.SistemaListScreen
import com.example.prioridadesretrofit.presentation.ticket.TicketListScreen

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(coroutineScope, drawerState, navHostController)
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Menu Botton")
                        }
                    }
                )
            }
        ) { innerpadding ->
            NavHost(
                navController = navHostController,
                startDestination = Screen.Home,
                modifier = Modifier.padding(innerpadding)
            ) {
                composable<Screen.Home> {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("HOME",
                            modifier = Modifier.padding(innerpadding)
                                .align(alignment = Alignment.Center),
                            fontSize = 70.sp

                        )
                    }

                }
                composable<Screen.PrioridadList> {

                    PrioridadListScreen(
                        createPrioridad = { navHostController.navigate(Screen.Prioridad(0)) },
                        goToPrioridadScreen = { navHostController.navigate(Screen.Prioridad(it)) }
                    )
                }
                composable<Screen.Prioridad> {
                    val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
                    PrioridadScreen(
                        onGoToPrioridadListScreen = { navHostController.navigateUp() },
                    )
                }
                composable<Screen.TicketList> {
                    TicketListScreen(
                        createTicket = { navHostController.navigate(Screen.Ticket(0)) },
                        goToTicketScreen = { navHostController.navigate(Screen.Ticket(it)) }
                    )
                }
                composable<Screen.Sistema> {
                    val sistemaId = it.toRoute<Screen.Sistema>().sistemaId
                    PrioridadScreen(
                        onGoToPrioridadListScreen = { navHostController.navigateUp() },
                    )
                }
                composable<Screen.SistemaList> {
                    SistemaListScreen(
                        createSistema = { navHostController.navigate(Screen.Sistema(0)) },
                        goToSistemaScreen = { navHostController.navigate(Screen.Sistema(it)) }
                    )
                }
                composable<Screen.Cliente> {
                    val clienteId = it.toRoute<Screen.Cliente>().clienteId
                    ClienteScreen (
                        onGoToClienteListScreen = { navHostController.navigate(Screen.ClienteList) },
                    )
                }
                composable<Screen.ClienteList> {
                    ClienteListScreen (
                        createCliente = { navHostController.navigate(Screen.Cliente(0)) },
                        goToClienteScreen = { navHostController.navigate(Screen.Cliente(it)) }
                    )
                }
            }
        }

    }


}