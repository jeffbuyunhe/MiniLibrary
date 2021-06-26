package com.example.minilibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recViewMenu;
    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.getInstance(this);
        initViews();
        btnAboutLogic();
    }

    private void initViews(){
        btnAbout = findViewById(R.id.btnAbout);
        recViewMenu = findViewById(R.id.recViewMenu);

        recViewMenu.setAdapter(new MenuRecViewAdapter(this));
        recViewMenu.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void btnAboutLogic(){
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder websiteDialog = new AlertDialog.Builder(MainActivity.this);
                websiteDialog.setTitle("MiniLibrary");
                websiteDialog.setCancelable(true);
                websiteDialog.setMessage("Designed by Jeff He, source code is located on my GitHub");
                websiteDialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                websiteDialog.setPositiveButton("Visit my GitHub", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent.putExtra("url", "https://github.com/jeffbuyunhe/personalprojects");
                        startActivity(intent);
                    }
                });
                websiteDialog.create().show();
            }
        });
    }
}