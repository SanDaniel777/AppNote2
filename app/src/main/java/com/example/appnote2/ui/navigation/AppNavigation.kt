package com.example.appnote2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appnote2.ui.screens.CreateNoteScreen
import com.example.appnote2.ui.screens.EditNoteScreen
import com.example.appnote2.ui.screens.MainScreen
import com.example.appnote2.ui.screens.NoteDetailScreen
import com.example.appnote2.ui.viewmodel.NoteViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            val viewModel: NoteViewModel = viewModel()

            MainScreen(
                viewModel = viewModel,
                onCreateNote = {
                    navController.navigate("createNote")
                },
                onNoteClick = { noteId ->
                    navController.navigate("noteDetail/$noteId")
                }
            )
        }

        // Crear Nota
        composable("createNote") {
            val viewModel: NoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            CreateNoteScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "noteDetail/{noteId}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType } // Cambiado a IntType
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0 // Obtenemos Int directamente
            val viewModel: NoteViewModel = viewModel()

            NoteDetailScreen(
                noteId = noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate("editNote/$id") },
                onDelete = { id ->
                    viewModel.deleteNote(id)
                    navController.popBackStack() // regresar al Main
                },
                onNavigateToNote = { newId ->
                    navController.navigate("noteDetail/$newId") {
                        launchSingleTop = true
                        popUpTo("noteDetail/$noteId") { inclusive = true }
                    }
                }

            )

        }


        // Editar Nota
        composable(
            route = "editNote/{noteId}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            val viewModel: NoteViewModel = viewModel()

            EditNoteScreen(
                noteId = noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }


    }
}