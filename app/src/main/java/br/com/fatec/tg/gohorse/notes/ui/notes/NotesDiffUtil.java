package br.com.fatec.tg.gohorse.notes.ui.notes;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.data.entity.Note;

public class NotesDiffUtil extends DiffUtil.Callback {

    @NonNull
    private final List<Note> mOldNoteList;
    @NonNull
    private final List<Note> mNewNoteList;

    NotesDiffUtil(@NonNull List<Note> oldNoteList,
                  @NonNull List<Note> newNoteList) {
        mOldNoteList = oldNoteList;
        mNewNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return mOldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNoteList.get(oldItemPosition).getId().equals(
                mNewNoteList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNoteList.get(oldItemPosition).equals(
                mNewNoteList.get(newItemPosition));
    }
}
