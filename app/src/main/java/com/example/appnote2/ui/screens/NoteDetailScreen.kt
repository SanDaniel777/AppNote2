package com.example.appnote2.ui.screens

import android.R.attr.padding
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appnote2.ui.viewmodel.NoteViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState()

    // --- 🔥 CARGAR NOTAS SI AÚN NO EXISTEN ---
    LaunchedEffect(Unit) {
        if (notes.isEmpty()) {
            viewModel.loadNotes()
        }
    }

    // --- 🔥 MIENTRAS CARGAN ---
    if (notes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // --- 🔥 BUSCAR LA NOTA ---
    val note = notes.find { it.id == noteId }

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isPlaying by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(noteId)
                    showDeleteDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("¿Eliminar nota?") },
            text = { Text("Esta acción no se puede deshacer.") }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Nota") },
                navigationIcon = {
                    IconButton(onClick = {
                        mediaPlayer?.release()
                        mediaPlayer = null
                        onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(noteId) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar Nota"
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            )
        }
    ) { padding ->

        if (note == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Nota no encontrada")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // TÍTULO
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // DESCRIPCIÓN
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // IMAGEN
            if (!note.imagePath.isNullOrEmpty()) {
                AsyncImage(
                    model = note.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // AUDIO
            if (!note.audioPath.isNullOrEmpty()) {
                Button(onClick = {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer().apply {
                            setDataSource(note.audioPath)
                            prepare()
                            start()
                        }
                        isPlaying = true
                    } else {
                        if (isPlaying) {
                            mediaPlayer?.pause()
                            isPlaying = false
                        } else {
                            mediaPlayer?.start()
                            isPlaying = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isPlaying) "Pausar Audio" else "Reproducir Audio")
                }
            }
        }
    }
}
