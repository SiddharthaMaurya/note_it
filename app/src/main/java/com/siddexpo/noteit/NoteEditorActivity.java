package com.siddexpo.noteit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.siddexpo.noteit.database.AppDatabase;
import com.siddexpo.noteit.model.Note;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteEditorActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtContent;

    private int noteId = -1;

    private boolean isPinned = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_editor);

        MaterialToolbar toolbar = findViewById(R.id.tabNote);
        setSupportActionBar(toolbar);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });



        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtTitle.requestFocus();

        edtContent.setHorizontallyScrolling(false);
        edtContent.setVerticalFadingEdgeEnabled(false);

        Intent intent = getIntent();

        if(intent.hasExtra("id")){

            noteId = intent.getIntExtra("id",-1);

            toolbar.setTitle("Edit Note");

            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");

            edtTitle.setText(title);
            edtContent.setText(content);

            isPinned = intent.getBooleanExtra("pinned",false);


        } else {
            toolbar.setTitle("New Note");
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveNote() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();

        if(title.isEmpty() && content.isEmpty()){
            finish();
            return;
        }

        //room code

        AppDatabase db = AppDatabase.getInstance(this);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            try{
                if(noteId == -1){
                    Note note = new Note(title,content,System.currentTimeMillis() ,isPinned);

                    db.noteDao().insert(note);
                } else {
                    //existing note
                    Note note = new Note(noteId, title, content,System.currentTimeMillis(),isPinned);

                    db.noteDao().update(note);
                }

                runOnUiThread(this::finish);
            } catch (Exception e) {
                e.printStackTrace();

            }


        });

    }

    public void deleteNote(){

        AppDatabase db = AppDatabase.getInstance(this);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() ->{
            Note note = new Note(
                    noteId,
                    edtTitle.getText().toString(),
                    edtContent.getText().toString(),
                    System.currentTimeMillis(),
                    false
            );

            db.noteDao().delete(note);

            runOnUiThread(this::finish);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);

        if(noteId == -1){
            menu.findItem(R.id.action_delete).setVisible(false);
        }

        MenuItem pinItem = menu.findItem(R.id.action_pin);

        pinItem.setIcon(isPinned ? R.drawable.pin_svgicon : R.drawable.pin_notr);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_pin){

            isPinned = !isPinned;

            item.setIcon(
                    isPinned ? R.drawable.pin_svgicon : R.drawable.pin_notr
            );

            return true;
        }

        if(id == R.id.action_share){
            String title  = edtTitle.getText().toString();
            String content = edtContent.getText().toString();

            Intent iShare = new Intent(Intent.ACTION_SEND);
            iShare.setType("text/plain");
            iShare.putExtra(Intent.EXTRA_SUBJECT,title);
            iShare.putExtra(Intent.EXTRA_TEXT,title + "\n\n" + content);

            startActivity(Intent.createChooser(iShare, "Share note via"));
            return true;
        }

        if(id == R.id.action_delete){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteNote();
                        }
                    }).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}