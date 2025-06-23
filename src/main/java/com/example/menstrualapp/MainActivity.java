package com.example.menstrualapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText_mobile=findViewById(R.id.editText_login_mobile);
        EditText editText_password=findViewById(R.id.editText_login_password);
        Button button_signin=findViewById(R.id.button_login_signin);
        Button button_signup=findViewById(R.id.button_login_signup);
        Button button_test=findViewById(R.id.button_test);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile=editText_mobile.getText().toString();
                String password=editText_password.getText().toString();
                int c=0;
                if(!(mobile.length()==10)) {
                    editText_mobile.setError("Enter Valid Mobile Number");
                    c++;
                }
                if(password.isEmpty()){
                    editText_password.setError("Enter Password");
                    c++;
                }
                if(c==0){
                    Call<List<UserModel>> call= RetrofitClient.getInstance().getApi().listUser();
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if(response.isSuccessful()){
                                List<UserModel> list=response.body();
                                int interationCount=0;
                                for(UserModel userModel: list){
                                    if(userModel.getMobile().equals(mobile) && userModel.getPassword().equals(password))
                                    {
                                        Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                                        //save userinfo in app
                                        SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name", userModel.getName());
                                        editor.putString("mobile", userModel.getMobile());
                                        editor.putInt("userid", userModel.getId());
                                        editor.commit();

                                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        break;
                                    }
                                    interationCount++;
                                }
                                if(interationCount==list.size())
                                {
                                    Toast.makeText(getApplicationContext(),"Your are not Registered...",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
            }
        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call=RetrofitClient.getInstance().getApi().testConnet();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            String message=response.body();
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}