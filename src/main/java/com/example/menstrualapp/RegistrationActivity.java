package com.example.menstrualapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText editTextName=findViewById(R.id.editText_regpage_name);
        EditText editTextMobile=findViewById(R.id.editText_regpage_mobile);
        EditText editTextPassword=findViewById(R.id.editText_regpage_password);
        EditText editTextCPassword=findViewById(R.id.editText_regpage_cpassword);
        Button buttonSignUp=findViewById(R.id.button_signup);
        Button buttonBack=findViewById(R.id.button_regpage_back);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString();
                String mobile=editTextMobile.getText().toString();
                String password=editTextPassword.getText().toString();
                String cpassword=editTextCPassword.getText().toString();
                int c=0;
                if(name.length()==0) {
                    editTextName.setError("Enter Name");
                    c++;
                }
                if(!(mobile.length()==10)) {
                    editTextMobile.setError("Mobile no. must have 10 digits");
                    c++;
                }
                if(password.length()<6){
                    editTextPassword.setError("Password must be 6 char Long");
                    c++;
                }
                if (!password.equals(cpassword)) {
                    editTextCPassword.setError("Password not match");
                    c++;
                }
                if(c==0){
                    UserModel userModel=new UserModel(name,mobile,password);
                    Call<UserModel> call=RetrofitClient.getInstance().getApi().createUser(userModel);
                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if(response.isSuccessful()){
                                UserModel userModel=response.body();
                                Toast.makeText(getApplicationContext(),"User Information Saved..",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegistrationActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                System.out.println(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            System.out.println(t.toString());
                        }
                    });

                }
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}