package br.com.fatec.tg.gohorse.notes.ui.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.R;
import br.com.fatec.tg.gohorse.notes.data.entity.Note;
import br.com.fatec.tg.gohorse.notes.ui.addeditnote.AddEditNoteActivity;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.view_flipper_note_content)
    ViewFlipper viewFlipperMainContent;
    @BindView(R.id.linear_layout_note_placeholder_container)
    LinearLayout linearLayoutPlaceholderContainer;
    @BindView(R.id.recycler_view_notes_list)
    RecyclerView recyclerViewNotes;
    @BindInt(R.integer.notes_columns_count)
    int columnsNumber;

    @NonNull
    private NotesAdapter noteAdapter = new NotesAdapter();
    private List<Note> notes;

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
        startActivity(AddEditNoteActivity.getStartIntent(this));
    }

    /********** Methods **********/

    private void setupRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                columnsNumber, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewNotes.setLayoutManager(layoutManager);
        recyclerViewNotes.setAdapter(noteAdapter);
    }

    private void loadNotes() {
        if (getAllNotes().isEmpty()) {
            viewFlipperMainContent.setDisplayedChild(
                    viewFlipperMainContent.indexOfChild(linearLayoutPlaceholderContainer));
        } else {
            noteAdapter.updateData();
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

    /********** Inner classes **********/

    class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note, parent, false);

            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteViewHolder holder, int position) {
            Note note = notes.get(position);

            if (note.getTitle().isEmpty()) {
                holder.textViewTitle.setVisibility(View.GONE);
            } else {
                holder.textViewTitle.setVisibility(View.VISIBLE);
                holder.textViewTitle.setText(note.getTitle());
            }
            if (note.getDescription().isEmpty()) {
                holder.textViewDescription.setVisibility(View.GONE);
            } else {
                holder.textViewDescription.setVisibility(View.VISIBLE);
                holder.textViewDescription.setText(note.getDescription());
            }
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        void updateData() {
            notes = getAllNotes();
            noteAdapter.notifyDataSetChanged();
        }

        class NoteViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.text_view_note_title)
            TextView textViewTitle;
            @BindView(R.id.text_view_note_description)
            TextView textViewDescription;

            NoteViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.card_note_container)
            void onNoteClicked() {
                Note note = notes.get(getAdapterPosition());
                startActivity(AddEditNoteActivity.getStartIntent(NotesActivity.this, note.getId()));
            }
        }
    }
}
