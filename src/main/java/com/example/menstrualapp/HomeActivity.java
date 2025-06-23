package com.example.menstrualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.PredictionModel;
import com.example.menstrualapp.retrofit.database.UserPeriodModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    TextView textView_in_days,textView_in_date,textView_in_length;
    Button button_start_prediction,button_enter_prediction;
    ImageButton imageButtonCorrect,imageButtonCross;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView_in_days=findViewById(R.id.prediction_in_days);
        textView_in_date=findViewById(R.id.prediction_in_date);
        textView_in_length=findViewById(R.id.prediction_in_length);
        imageButtonCorrect=findViewById(R.id.imageButton_Prediction_correct);
        imageButtonCross=findViewById(R.id.imageButton_Prediction_cross);
        TextView textViewHomeUserName=findViewById(R.id.textView_homepage_username);
        button_start_prediction=findViewById(R.id.button_start_perdiction);
        button_enter_prediction=findViewById(R.id.button_enter_perdiction);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        button_start_prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartPerdictionAlertDailogs.askAgeDailog(HomeActivity.this);
            }
        });
        //Access UserName from SharedPrefrences
        SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "Unknown");
        userid = sharedPreferences.getInt("userid",0);

        textViewHomeUserName.setText("Hello, "+username);

        Call<PredictionModel> call= RetrofitClient.getInstance().getApi().getPredictUserPeriod(userid);
        call.enqueue(new Callback<PredictionModel>() {
            @Override
            public void onResponse(Call<PredictionModel> call, Response<PredictionModel> response) {
                if(response.isSuccessful()){
                    PredictionModel predictionModel=response.body();
                    textView_in_days.setText(String.valueOf(predictionModel.getNextPeriodindays()+1)+" Days");
                    textView_in_date.setText(predictionModel.getPeriodDate());
                    textView_in_length.setText(String.valueOf(predictionModel.getNextperiodLength()));
                    if("Start Prediction".equals(predictionModel.getPeriodDate()))
                        button_start_prediction.setVisibility(View.VISIBLE);

                    if((predictionModel.getNextPeriodindays()+1)<=0){
                        imageButtonCorrect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UserPeriodModel userPeriodModel=new UserPeriodModel(userid, predictionModel.getPeriodCycle(), predictionModel.getNextperiodLength(),predictionModel.getPeriodDate(),"Medium");
                                Call<UserPeriodModel> call2=RetrofitClient.getInstance().getApi().createUserPeriod(userPeriodModel);
                                call2.enqueue(new Callback<UserPeriodModel>() {
                                    @Override
                                    public void onResponse(Call<UserPeriodModel> call, Response<UserPeriodModel> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(HomeActivity.this,"Prediction done!",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(HomeActivity.this,HistoryActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserPeriodModel> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        imageButtonCross.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                button_enter_prediction.setVisibility(View.VISIBLE);
                                button_enter_prediction.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        EnterPeriodDataDailogs.askCycleDailog(HomeActivity.this);
                                    }
                                });
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<PredictionModel> call, Throwable t) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){

                }
                if(item.getItemId() == R.id.nav_history){
                    Intent intent=new Intent(HomeActivity.this,HistoryActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_chat){
                    Intent intent=new Intent(HomeActivity.this,ChatActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_profile){
                    Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

    }
}