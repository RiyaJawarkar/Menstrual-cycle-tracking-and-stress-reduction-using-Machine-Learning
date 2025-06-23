package com.example.menstrualapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menstrualapp.retrofit.RetrofitClient;
import com.example.menstrualapp.retrofit.database.UserModel;
import com.example.menstrualapp.retrofit.database.UserPeriodModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartPerdictionAlertDailogs {
    public static void askAgeDailog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_cycle_picker, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView=dialogView.findViewById(R.id.textView_dialog_quesion);
        textView.setText("Enter your Age? ");

        // Get Views
        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // Configure NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(90);
        numberPicker.setValue(20);
        numberPicker.setWrapSelectorWheel(false);


        // Handle "Confirm" Button Click
        btnConfirm.setOnClickListener(v -> {
            int selectedValue = numberPicker.getValue();
            Toast.makeText(context, "Selected: " + selectedValue + " days", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPeriod", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Age",selectedValue);
            editor.commit();
            dialog.dismiss();
            askCycleDailog(context);
        });


    }
    public static void askCycleDailog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_cycle_picker, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView=dialogView.findViewById(R.id.textView_dialog_quesion);
        textView.setText("Your average Cycle length?");

        // Get Views
        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // Configure NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(90);
        numberPicker.setValue(20);
        numberPicker.setWrapSelectorWheel(false);


        // Handle "Confirm" Button Click
        btnConfirm.setOnClickListener(v -> {
            int selectedValue = numberPicker.getValue();
            Toast.makeText(context, "Selected: " + selectedValue + " days", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPeriod", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Cycle",selectedValue);
            editor.commit();
            dialog.dismiss();
            askLengthDailog(context);
        });


    }
    public static void askLengthDailog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_cycle_picker, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView=dialogView.findViewById(R.id.textView_dialog_quesion);
        textView.setText("Your Average Period Length? ");

        // Get Views
        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // Configure NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(90);
        numberPicker.setValue(5);
        numberPicker.setWrapSelectorWheel(false);


        // Handle "Confirm" Button Click
        btnConfirm.setOnClickListener(v -> {
            int selectedValue = numberPicker.getValue();
            Toast.makeText(context, "Selected: " + selectedValue + " days", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPeriod", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Length",selectedValue);
            editor.commit();
            asklastdate(context);
            dialog.dismiss();
        });
    }
    public static void asklastdate(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_date_picker, null);
        builder.setView(dialogView);

        CalendarView calendarView;
        calendarView = dialogView.findViewById(R.id.calendarView);
        calendarView.setMaxDate(System.currentTimeMillis());

        // Set listener for date selection
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Month is 0-based, so add 1
            String selectedDate = year+ "-" + (month + 1) + "-" + dayOfMonth  ;
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPeriod", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lastdate",selectedDate);
            editor.commit();
            Toast.makeText(context, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            askstresslevel(context);
            dialog.dismiss();
        });

    }
    public static void askstresslevel(Context context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_stress_level, null);

        // Create AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Get dialog components
        RadioGroup radioGroupStress = dialogView.findViewById(R.id.radioGroupStress);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // Close button click

        // Confirm button click
        btnConfirm.setOnClickListener(v -> {
            int selectedId = radioGroupStress.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadio = dialogView.findViewById(selectedId);
                Toast.makeText(context, "Stress Level: " + selectedRadio.getText(), Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPeriod", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("stress",selectedRadio.getText().toString());
                editor.commit();

                SharedPreferences sharedPreferences2 = context.getSharedPreferences("MyLogin", Context.MODE_PRIVATE);
                int userid=sharedPreferences2.getInt("userid",0);
                int Cycle=sharedPreferences.getInt("Cycle",20);
                int Length=sharedPreferences.getInt("Length",5);
                String date=sharedPreferences.getString("lastdate","");
                String stress=sharedPreferences.getString("stress","");
                int Age=sharedPreferences.getInt("Age",20);
                UserPeriodModel userPeriodModel=new UserPeriodModel(userid,Cycle,Length,date,stress);
                System.out.println(userPeriodModel);
                Call<UserPeriodModel> call=RetrofitClient.getInstance().getApi().createUserPeriod(userPeriodModel);
                call.enqueue(new Callback<UserPeriodModel>() {
                    @Override
                    public void onResponse(Call<UserPeriodModel> call, Response<UserPeriodModel> response) {
                        if(response.isSuccessful()){

                        }
                    }

                    @Override
                    public void onFailure(Call<UserPeriodModel> call, Throwable t) {

                    }
                });
                //Access UserModel
                Call<UserModel> call2= RetrofitClient.getInstance().getApi().oneUser(userid);
                call2.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            UserModel userModel = response.body();
                            userModel.setAge(Age);
                            //Updating Age
                            Call<UserModel> call2 = RetrofitClient.getInstance().getApi().updateUser(userModel.getId(), userModel);
                            call2.enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    if (response.isSuccessful()) {
                                        UserModel userModel1 = response.body();
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

            } else {
                Toast.makeText(context, "Please select a stress level", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        // Show dialog
        dialog.show();
    }
}
