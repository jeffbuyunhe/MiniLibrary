package com.example.minilibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CurrentlyReadActivity extends AppCompatActivity {

    private RecyclerView recViewCurrentlyRead;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_read);

        initViews();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initViews(){
        recViewCurrentlyRead = findViewById(R.id.recViewCurrentlyRead);

        adapter = new BookRecViewAdapter(this, Utils.CURRENTLY_READ_BOOKS_KEY);
        recViewCurrentlyRead.setAdapter(adapter);
        recViewCurrentlyRead.setLayoutManager(new LinearLayoutManager(this));
        adapter.setBooks(Utils.getInstance(this).getCurrentlyReadBooks());
    }
}