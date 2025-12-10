package com.example.appnote2.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState()


    LaunchedEffect(Unit) {
        if (notes.isEmpty()) viewModel.loadNotes()
    }


    if (notes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val note = notes.find { it.id == noteId }

    if (note == null) {
        Text("Nota no encontrada")
        return
    }

    var title by remember { mutableStateOf(note.title) }
    var description by remember { mutableStateOf(note.description) }


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var audioUri by remember { mutableStateOf<Uri?>(null) }


    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    val audioPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> audioUri = uri }

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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().height(180.dp)
            )


            if (!note.imagePath.isNullOrEmpty() && imageUri == null) {
                Text("Imagen actual:")
                AsyncImage(
                    model = note.imagePath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }


            if (imageUri != null) {
                Text("Nueva imagen seleccionada:")
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }

            Button(
                onClick = { imagePicker.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar imagen")
            }


            Button(
                onClick = { audioPicker.launch("audio/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar audio")
            }

            Button(
                onClick = {
                    viewModel.updateNote(
                        id = noteId,
                        title = title,
                        description = description,
                        imageUri = imageUri,
                        audioUri = audioUri,
                        context = context
                    )
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        }
    }
}

