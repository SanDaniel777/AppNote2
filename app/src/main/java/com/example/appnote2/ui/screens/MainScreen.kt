package com.example.appnote2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appnote2.data.model.Note
import com.example.appnote2.ui.viewmodel.NoteViewModel

@Composable
fun MainScreen(
    onCreateNote: () -> Unit,
    onNoteClick: (Int) -> Unit,
    noteViewModel: NoteViewModel = viewModel()
) {

    val notes by noteViewModel.notes.collectAsState()

    // ✅ Cargar notas al entrar a la pantalla
    LaunchedEffect(Unit) {
        noteViewModel.loadNotes()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNote) {
                Text("+")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(notes) { note ->
                NoteItem(note = note) {
                    onNoteClick(note.id)
                }
            }
        }
    }
}