package net.skhu.e05photo;

import android.os.Bundle;
import android.os.Environment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class Photo3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo3);
        setSupportActionBar(findViewById(R.id.toolbar));

        FloatingActionButton button = findViewById(R.id.btnTakePhoto);
        button.setOnClickListener((view) ->
                Snackbar.make(view, "이것은 Snackbar 메시지 입니다", Snackbar.LENGTH_LONG).show());

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = directory.listFiles();

        FileRecyclerView1Adapter fileRecyclerView1Adapter = new FileRecyclerView1Adapter(this, files);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileRecyclerView1Adapter);
    }
}