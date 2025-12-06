package com.example.appnote2.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appnote2.ui.viewmodel.NoteViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    viewModel: NoteViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


    var imagePart by remember { mutableStateOf<MultipartBody.Part?>(null) }
    var audioPart by remember { mutableStateOf<MultipartBody.Part?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {
                    // Próximo paso: Cámara
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Foto")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Próximo paso: Micrófono
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Grabar Audio")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    val titleBody: RequestBody =
                        title.toRequestBody("text/plain".toMediaTypeOrNull())

                    val descriptionBody: RequestBody =
                        description.toRequestBody("text/plain".toMediaTypeOrNull())

                    viewModel.createNote(
                        title = titleBody,
                        description = descriptionBody,
                        image = imagePart,
                        audio = audioPart
                    )

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Nota")
            }
        }
    }
}