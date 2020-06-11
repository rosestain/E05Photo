package net.skhu.e05photo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class Photo3Activity extends AppCompatActivity {

    OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            button.show();
            imageView.setVisibility(View.GONE);
            this.setEnabled(false);
        }
    };

    ImageView imageView;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo3);
        setSupportActionBar(findViewById(R.id.toolbar));

        final FloatingActionButton button = findViewById(R.id.btnTakePhoto);
        button.setOnClickListener((view) ->
                Snackbar.make(view, "이것은 Snackbar 메시지 입니다", Snackbar.LENGTH_LONG).show());

        final ImageView imageView = findViewById(R.id.imageView1);
        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener((view) ->
        {
            button.show();
            view.setVisibility(View.GONE);
            backPressedCallback.setEnabled(false);
        });

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = directory.listFiles();

        OnFileClickListener clickListener = (index, file) -> {
            button.hide();
            imageView.setImageURI(Uri.fromFile(file));
            imageView.setVisibility(View.VISIBLE);
            backPressedCallback.setEnabled(true);
        };

        FileRecyclerView2Adapter adapter = new FileRecyclerView2Adapter(this, files, clickListener);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getOnBackPressedDispatcher().addCallback(backPressedCallback);
    }
}