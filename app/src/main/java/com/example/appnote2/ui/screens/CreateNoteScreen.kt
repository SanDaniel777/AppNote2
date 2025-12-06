package com.example.appnote2.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appnote2.ui.utils.createImageFile
import com.example.appnote2.ui.viewmodel.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    viewModel: NoteViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var audioUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (!success) imageUri = null
    }

    val audioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            audioUri = result.data?.data
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") },
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

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 📸 BOTÓN CÁMARA
            Button(onClick = {
                val uri = createImageFile(context)
                imageUri = uri
                cameraLauncher.launch(uri)
            }) {
                Text("Tomar Foto")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🎤 BOTÓN AUDIO
            Button(onClick = {
                val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                audioLauncher.launch(intent)
            }) {
                Text("Grabar Audio")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ GUARDAR NOTA
            Button(
                onClick = {
                    viewModel.createNote(
                        title,
                        description,
                        imageUri,
                        audioUri
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