package com.example.appnote2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appnote2.ui.screens.CreateNoteScreen
import com.example.appnote2.ui.screens.MainScreen
import com.example.appnote2.ui.screens.NoteDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        // Pantalla principal
        composable("main") {
            MainScreen(
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
            CreateNoteScreen(
                onBack = { navController.popBackStack() },
                onSaveNote = { title, description ->
                    // Aquí guardas tu nota
                    navController.popBackStack()
                }
            )
        }

        // Detalle de nota (con argumento)
        composable(
            route = "noteDetail/{noteId}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""

            NoteDetailScreen(
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}