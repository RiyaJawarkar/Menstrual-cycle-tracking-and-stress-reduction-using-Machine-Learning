package com.example.menstrualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.UserPeriodModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView=findViewById(R.id.listview_history);
        SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
        int id=sharedPreferences.getInt("userid",0);
        Call<List<UserPeriodModel>> call= RetrofitClient.getInstance().getApi().listUserPeriod(id);
        call.enqueue(new Callback<List<UserPeriodModel>>() {
            @Override
            public void onResponse(Call<List<UserPeriodModel>> call, Response<List<UserPeriodModel>> response) {
                if(response.isSuccessful()){
                    List<UserPeriodModel> list=response.body();
                    PeriodAdapter adapter=new PeriodAdapter(HistoryActivity.this,list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserPeriodModel>> call, Throwable t) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_history);
        bottomNavigationView.setSelectedItemId(R.id.nav_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Intent intent=new Intent(HistoryActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_history){

                }
                if(item.getItemId() == R.id.nav_chat){
                    Intent intent=new Intent(HistoryActivity.this,ChatActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_profile){
                    Intent intent=new Intent(HistoryActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }
}