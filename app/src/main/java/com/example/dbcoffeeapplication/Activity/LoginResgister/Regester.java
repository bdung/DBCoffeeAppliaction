package com.example.dbcoffeeapplication.Activity.LoginResgister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbcoffeeapplication.Activity.HomePage.HomePage;
import com.example.dbcoffeeapplication.DTO.Users;
import com.example.dbcoffeeapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Regester extends AppCompatActivity {
    private Button regester;
    private EditText numberphone, username, password,passwordAgain;
    private TextView login;
    public DatabaseReference databaseReference;

    public void initRegester(){
        regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }
    private void createUser(){
        String phone = numberphone.getText().toString();
        String name = username.getText().toString();
        String pass = password.getText().toString();
        String passAgain = passwordAgain.getText().toString();

        if(phone.equals("")){
            numberphone.setError("Please enter a phone number");
        }
        else if(name.equals("")){
            username.setError("Please enter a username");
        }
        else if(pass.equals("")){
            password.setError("Please enter a password");
        }
        else if(passAgain.equals("")){
            passwordAgain.setError("Please enter a password again");
        }
        else if(!pass.equals(passAgain)){
            passwordAgain.setError("Password incorrect");
        }
        else{

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
            Date now = new Date();
            String idUser = formatter.format(now);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean exitUser = false;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Users user = dataSnapshot.getValue(Users.class);
                        if(user.getNumberphone().equals(phone)){
                            noticeExitUser();
                            exitUser = true;
                            break;
                        }
                    }
                    if(!exitUser){
                        Intent intent = new Intent(Regester.this, OTPActivity.class);
                        intent.putExtra("type_numberphone","signup");
                        intent.putExtra("id",idUser);
                        intent.putExtra("username", name);
                        intent.putExtra("password", pass);

                        startActivity(intent);
                    }
                    else{
                        noticeExitUser();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private void noticeExitUser() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.setMessage("Numberphone invalid. Please enter a valid phone number");
        alert.show();
    }
    public void initLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Regester.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        regester = findViewById(R.id.idRegesterApp);
        numberphone = findViewById(R.id.numberPhone);
        username = findViewById(R.id.idUsername);
        password = findViewById(R.id.idPassword);
        passwordAgain = findViewById(R.id.idPasswordAgain);
        login = findViewById(R.id.idLogin);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        init();
        initLogin();



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        Date now = new Date();
        String idUser = formatter.format(now);


        regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryCodePicker ccp_su = (CountryCodePicker) findViewById(R.id.ccp);
                ccp_su.registerCarrierNumberEditText(numberphone);
                String phone = ccp_su.getFullNumberWithPlus().replace(" ", "");

                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String passAgain = passwordAgain.getText().toString().trim();

                if(phone.equals(" ")){
                    numberphone.setError("Please enter a phone number");
                }
                else if(name.equals(" ")){
                    username.setError("Please enter a username");
                }
                else if(pass.equals(" ")){
                    password.setError("Please enter a password");
                }
                else if(passAgain.equals(" ")){
                    passwordAgain.setError("Please enter a password again");
                }
                else if(!pass.equals(passAgain)){
                    passwordAgain.setError("Password incorrect");
                }
                else{

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean exitUser = false;
                            Log.d("read database", "user before change");
                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Users user = dataSnapshot.getValue(Users.class);
                                Log.d("read database", "user name"+ user.getNumberphone());

                                if(user.getNumberphone().equals(phone)){
                                    noticeExitUser();
                                    exitUser = true;
                                    break;
                                }
                            }
                            if(exitUser == false){
                                Intent intent = new Intent(Regester.this, OTPActivity.class);
                                intent.putExtra("type_numberphone","signup");
                                intent.putExtra("phoneNumber", ccp_su.getFullNumberWithPlus().replace(" ", ""));
                                intent.putExtra("id",idUser);
                                intent.putExtra("username", name);
                                intent.putExtra("password", pass);

                                startActivity(intent);
                            }
                            else{
                                noticeExitUser();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });



    }
}