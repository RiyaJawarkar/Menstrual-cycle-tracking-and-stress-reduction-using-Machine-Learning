package com.example.menstrualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed
        webView.loadUrl("http://"+Config.Server_ip+":8000/chatbot/chatview/");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Intent intent=new Intent(ChatActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_history){
                    Intent intent=new Intent(ChatActivity.this,HistoryActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_chat){

                }
                if(item.getItemId() == R.id.nav_profile){
                    Intent intent=new Intent(ChatActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

    }
}