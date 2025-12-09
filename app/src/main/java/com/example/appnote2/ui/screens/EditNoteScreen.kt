package com.example.appnote2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appnote2.ui.viewmodel.NoteViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onBack: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val context = LocalContext.current


    // 🔥 Debug para confirmar ID
    LaunchedEffect(Unit) {
        println("🔥 EDITING NOTE ID = $noteId")
    }

    // 🔥 Si no hay notas, cargarlas
    LaunchedEffect(Unit) {
        if (notes.isEmpty()) {
            println("⚠️ Notas vacías, cargando desde servidor...")
            viewModel.loadNotes()
        }
    }

    // 🔄 Mostrar cargando mientras llegan
    if (notes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // 🔍 Buscar la nota una vez que ya cargaron
    val note = notes.find { it.id == noteId }

    if (note == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nota no encontrada")
        }
        return
    }

    // ----- CAMPOS EDITABLES -----
    var title by remember { mutableStateOf(note.title) }
    var description by remember { mutableStateOf(note.description) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Nota") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.updateNote(
                        id = noteId,
                        title = title,
                        description = description,
                        imageUri = null, // luego agregamos edición de imagen si quieres
                        audioUri = null, // igual para audio
                        context = context
                    )
                    onBack()
                }
            ) {
                Text("Guardar cambios")
            }
        }
    }
}

