package br.com.fatec.tg.gohorse.notes.ui.addeditnote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.fatec.tg.gohorse.notes.R;
import br.com.fatec.tg.gohorse.notes.data.entity.Note;
import br.com.fatec.tg.gohorse.notes.util.Database;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AddEditNoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_text_note_title)
    EditText editTextNoteTitle;
    @BindView(R.id.edit_text_note_description)
    EditText editTextNoteDescription;

    @Nullable
    private Note note;
    @NonNull
    private Realm realm = Realm.getDefaultInstance();

    @NonNull
    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, AddEditNoteActivity.class);
    }

    @NonNull
    public static Intent getStartIntent(@NonNull Context context,
                                        @Nullable String noteId) {
        Intent intent = new Intent(context, AddEditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        ButterKnife.bind(this);
        setupNote(getIntent().getStringExtra(EXTRA_NOTE_ID));
        setupToolbar();
        setupMenu();
    }

    @Override
    public void onBackPressed() {
        saveNote(editTextNoteTitle.getText().toString(),
                editTextNoteDescription.getText().toString());
        super.onBackPressed();
    }

    /********** Methods **********/

    private void setupNote(@Nullable String noteId) {
        if (noteId != null) {
            note = getNoteById(noteId);
            editTextNoteTitle.setText(note.getTitle());
            editTextNoteDescription.setText(note.getDescription());
        }
    }

    private void setupMenu() {
        if (note != null) {
            MenuItem menuActionDelete = toolbar.getMenu().findItem(R.id.add_edit_note_action_delete);
            menuActionDelete.setEnabled(true);
            menuActionDelete.setVisible(true);
        }
    }

    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(view -> {
            saveNote(editTextNoteTitle.getText().toString(),
                    editTextNoteDescription.getText().toString());
            navigateToNotesScreen();
        });
        toolbar.inflateMenu(R.menu.activity_add_edit_note);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.add_edit_note_action_delete) {
                if (note != null) {
                    deleteNoteById(note.getId());
                    clearInputFields();
                    navigateToNotesScreen();
                }
            }
            return false;
        });
    }

    private void navigateToNotesScreen() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void clearInputFields() {
        editTextNoteTitle.setText("");
        editTextNoteDescription.setText("");
    }

    private void saveNote(@NonNull String title, @NonNull String description) {
        if (!title.isEmpty() || !description.isEmpty()) {
            if (note != null) {
                note.setTitle(title);
                note.setDescription(description);
                saveOrUpdateNote(note);
            } else {
                Note note = new Note(Database.generateId(), title, description);
                saveOrUpdateNote(note);
            }
        }
    }

    public Note getNoteById(@NonNull String id) {
        return realm.copyFromRealm(
                realm.where(Note.class)
                        .equalTo("id", id)
                        .findFirst()
        );
    }

    public void deleteNoteById(@NonNull String noteId) {
        realm.executeTransaction(realm ->
                realm.where(Note.class)
                        .equalTo("id", noteId)
                        .findFirst()
                        .deleteFromRealm()
        );
    }

    public void saveOrUpdateNote(@NonNull Note note) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(note));
    }
}
