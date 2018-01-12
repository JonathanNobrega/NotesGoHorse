package br.com.fatec.tg.gohorse.notes.data.repository.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.data.entity.Note;
import br.com.fatec.tg.gohorse.notes.data.repository.NoteRepository;
import io.realm.Realm;

public class LocalNoteDataSource implements NoteRepository {

    @NonNull
    @Override
    public List<Note> getAllNotes() {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(
                realm.where(Note.class)
                        .findAll()
        );
    }

    @NonNull
    @Override
    public Note getNoteById(@NonNull String id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(
                realm.where(Note.class)
                        .equalTo("id", id)
                        .findFirst()
        );
    }

    @Override
    public boolean hasAnyNotes() {
        return Realm.getDefaultInstance()
                .where(Note.class)
                .count() > 0;
    }

    @Override
    public void saveOrUpdateNote(@NonNull Note note) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(note);
            realm.commitTransaction();
        }
    }

    @Override
    public void deleteNoteById(@NonNull String noteId) {
        Realm.getDefaultInstance().executeTransaction(realm ->
                realm.where(Note.class)
                        .equalTo("id", noteId)
                        .findFirst()
                        .deleteFromRealm()
        );
    }
}
