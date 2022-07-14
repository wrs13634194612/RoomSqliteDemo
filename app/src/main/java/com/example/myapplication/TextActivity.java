package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class TextActivity extends AppCompatActivity
        implements NoteAdapter.ActionCallback, View.OnClickListener {

    FloatingActionButton mFloatingActionButton;
    ConstraintLayout mConstraintLayout;
    TextInputEditText mTextInputEditText;
    CardView mCardView;
    TextInputLayout mTextInputLayout;

    private NoteAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        findViewById(R.id.add).setOnClickListener(this);
        adapter = new NoteAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteNote(adapter.getNote(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                startActivity(new Intent(this, EditNoteActivity.class));
                break;
        }
    }

    private void loadNotes() {
        new AsyncTask<Void, Void, List<CategoryNote>>() {
            @Override
            protected List<CategoryNote> doInBackground(Void... params) {
                return db.getNoteDao().getCategoryNotes();
            }

            @Override
            protected void onPostExecute(List<CategoryNote> notes) {
                adapter.setNotes(notes);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteNote(Note note) {
        new AsyncTask<Note, Void, Void>() {
            @Override
            protected Void doInBackground(Note... params) {
                db.getNoteDao().deleteAll(params);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                loadNotes();
            }
        }.execute(note);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onEdit(CategoryNote note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtras(EditNoteActivity.newInstanceBundle(note.getId()));
        startActivity(intent);
    }
}
