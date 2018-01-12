package br.com.fatec.tg.gohorse.notes.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.data.entity.Note;

public interface NoteRepository {

    @NonNull
    List<Note> getAllNotes();

    @NonNull
    Note getNoteById(@NonNull String id);

    boolean hasAnyNotes();

    void saveOrUpdateNote(@NonNull Note note);

    void deleteNoteById(@NonNull String noteId);
}
