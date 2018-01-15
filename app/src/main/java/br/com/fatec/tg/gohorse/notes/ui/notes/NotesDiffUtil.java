package br.com.fatec.tg.gohorse.notes.ui.notes;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.data.entity.Note;

public class NotesDiffUtil extends DiffUtil.Callback {

    @NonNull
    private final List<Note> oldNotes;
    @NonNull
    private final List<Note> newNotes;

    NotesDiffUtil(@NonNull List<Note> oldNotes,
                  @NonNull List<Note> newNotes) {
        this.oldNotes = oldNotes;
        this.newNotes = newNotes;
    }

    @Override
    public int getOldListSize() {
        return oldNotes.size();
    }

    @Override
    public int getNewListSize() {
        return newNotes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition).getId().equals(
                newNotes.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition).equals(
                newNotes.get(newItemPosition));
    }
}
