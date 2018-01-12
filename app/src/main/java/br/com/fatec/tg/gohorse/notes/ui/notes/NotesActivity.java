package br.com.fatec.tg.gohorse.notes.ui.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.tg.gohorse.notes.R;
import br.com.fatec.tg.gohorse.notes.data.entity.Note;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.ItemListener {

    @BindView(R.id.view_flipper_note_content)
    ViewFlipper viewFlipperMainContent;
    @BindView(R.id.linear_layout_note_placeholder_container)
    LinearLayout linearLayoutPlaceholderContainer;
    @BindView(R.id.recycler_view_notes_list)
    RecyclerView recyclerViewNotes;
    @BindInt(R.integer.notes_columns_count)
    int columnsNumber;

    private NotesAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    /********** Click events **********/

    @OnClick(R.id.fab_note_add_note)
    void onAddNoteClicked() {
//        startActivity(AddEditNoteActivity.getStartIntent(this));
    }

    /********** NotesAdapter.ItemListener **********/

    @Override
    public void onNoteClicked(@NonNull Note note) {
//        startActivity(AddEditNoteActivity.getStartIntent(this, note.getId()));
    }

    /********** Methods **********/

    private void setupRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                columnsNumber, StaggeredGridLayoutManager.VERTICAL);
        mNoteAdapter = new NotesAdapter(new ArrayList<>(), this);
        recyclerViewNotes.setLayoutManager(layoutManager);
        recyclerViewNotes.setAdapter(mNoteAdapter);
    }

    private void loadNotes() {
        List<Note> notes = getAllNotes();

        if (notes.isEmpty()) {
            viewFlipperMainContent.setDisplayedChild(
                    viewFlipperMainContent.indexOfChild(linearLayoutPlaceholderContainer));
        } else {
            mNoteAdapter.setData(notes);
            viewFlipperMainContent.setDisplayedChild(
                    viewFlipperMainContent.indexOfChild(recyclerViewNotes));
        }
    }

    @NonNull
    public List<Note> getAllNotes() {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(
                realm.where(Note.class)
                        .findAll()
        );
    }
}
