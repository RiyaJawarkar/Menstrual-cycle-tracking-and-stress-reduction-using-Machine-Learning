package com.example.menstrualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.UserModel;
import com.example.menstrualapp.retrofit.database.UserPeriodModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText editTextName,editTextMobile,editTextAge;
    TextView textViewAverageLength,textViewAverageCycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editTextName=findViewById(R.id.editText_profile_name);
        editTextMobile=findViewById(R.id.editText_profile_mobile);
        editTextAge=findViewById(R.id.editText_profile_age);
        textViewAverageLength=findViewById(R.id.textView_profile_average_length);
        textViewAverageCycle=findViewById(R.id.textView_profile_average_cycle);
        Button button_save=findViewById(R.id.button_profile_save);

        SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "Unknown");
        String mobile = sharedPreferences.getString("mobile", "Unknown");
        String age = String.valueOf(sharedPreferences.getInt("age", 0));
        int id = sharedPreferences.getInt("userid",0);
        editTextName.setText(username);
        editTextMobile.setText(mobile);
        editTextAge.setText(age);
        AveragePeriodLengthandCycle(id);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString();
                String mobile=editTextMobile.getText().toString();
                int age=Integer.parseInt(editTextAge.getText().toString());
                //Access UserModel
                Call<UserModel> call= RetrofitClient.getInstance().getApi().oneUser(id);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModel=response.body();
                            userModel.setName(name);
                            userModel.setMobile(mobile);
                            userModel.setAge(age);
                            //Updating Name and Mobile
                            Call<UserModel> call2=RetrofitClient.getInstance().getApi().updateUser(userModel.getId(),userModel);
                            call2.enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    if(response.isSuccessful()){
                                        UserModel userModel1=response.body();
                                        editTextName.setText(userModel1.getName());
                                        editTextMobile.setText(userModel1.getMobile());
                                        SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name", userModel.getName());
                                        editor.putString("mobile", userModel.getMobile());
                                        editor.putInt("age", userModel.getAge());
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(),"Your Info in Updated...",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserModel> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_profile);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Intent intent=new Intent(ProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_history){
                    Intent intent=new Intent(ProfileActivity.this,HistoryActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_chat){
                    Intent intent=new Intent(ProfileActivity.this,ChatActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_profile){
                }
                if(item.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

    }
    public void AveragePeriodLengthandCycle(int userid){
        Call<List<UserPeriodModel>> call=RetrofitClient.getInstance().getApi().listUserPeriod(userid);
        call.enqueue(new Callback<List<UserPeriodModel>>() {
            @Override
            public void onResponse(Call<List<UserPeriodModel>> call, Response<List<UserPeriodModel>> response) {
                if(response.isSuccessful()){
                    List<UserPeriodModel> userPeriodModelList=response.body();
                    if(!userPeriodModelList.isEmpty()) {
                        int LengthSum = 0;
                        int CycleSum = 0;
                        for (UserPeriodModel period : userPeriodModelList) {
                            LengthSum = LengthSum + period.getPeriodLength();
                            CycleSum = CycleSum + period.getPeriodCycle();
                        }
                        int AverageLength = LengthSum / userPeriodModelList.size();
                        int AverageCycle = CycleSum / userPeriodModelList.size();
                        textViewAverageLength.setText(AverageLength + " Days");
                        textViewAverageCycle.setText(AverageCycle + " Days");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserPeriodModel>> call, Throwable t) {

            }
        });
    }
}