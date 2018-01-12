package br.com.fatec.tg.gohorse.notes.ui.notes;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fatec.tg.gohorse.notes.R;
import br.com.fatec.tg.gohorse.notes.data.entity.Note;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    @NonNull
    private final List<Note> notes;
    @NonNull
    private final ItemListener listener;

    NotesAdapter(@NonNull List<Note> notes,
                 @NonNull ItemListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

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

    void setData(@NonNull List<Note> notes) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NotesDiffUtil(this.notes, notes));
        this.notes.clear();
        this.notes.addAll(notes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    List<Note> getData() {
        return notes;
    }

    void addItem(@NonNull Note note) {
        notes.add(0, note);
        notifyItemInserted(0);
    }

    void removeItem(@NonNull Note note) {
        int itemPosition = notes.indexOf(note);
        notes.remove(note);
        notifyItemRemoved(itemPosition);
    }

    void updateItem(@NonNull Note note) {
        int itemPosition = notes.indexOf(note);
        notes.set(itemPosition, note);
        notifyItemChanged(itemPosition);
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
            listener.onNoteClicked(note);
        }
    }

    interface ItemListener {

        void onNoteClicked(@NonNull Note note);
    }
}
