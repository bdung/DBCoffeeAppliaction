package com.example.dbcoffeeapplication.Activity.LoginResgister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignIn extends AppCompatActivity {
    private Button login;
    private EditText numberPhone, password;
    private TextView regester, forgotPassword;
    public DatabaseReference databaseReference;
    CheckBox checkSave;
    String saveUser = "phone_password";
    boolean checkedVisibles = true;

    public void initLogin() {
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkUser();
            }
        });


    }

    private String convertHashToString(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }
    @SuppressLint("WrongViewCast")
    private void checkUser() {
        final CountryCodePicker ccp_su = (CountryCodePicker) findViewById(R.id.ccp);
        ccp_su.registerCarrierNumberEditText(numberPhone);
        String phone = ccp_su.getFullNumberWithPlus().replace(" ", "");
        String pass = password.getText().toString();
        if (pass.isEmpty()) {
            password.setError("Password not empty");
        } else if (phone.isEmpty()) {
            numberPhone.setError("Number phone not empty");

        } else {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean existUser = false;
                    int typeUser = 1;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Users user = dataSnapshot.getValue(Users.class);

                        String pass_convert = convertHashToString(pass);


                        if (phone.equals(user.getNumberphone()) && pass_convert.equals(user.getPassword())) {
                            if (user.getStatus().equals("0")) {
                                typeUser = 0;
                            } else {
                                SharedPreferences sharedPreferences = getSharedPreferences(saveUser, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("numberPhone", phone);
                                editor.putString("password", pass);
                                editor.putBoolean("Save", checkSave.isChecked());
                                editor.commit();
                                existUser = true;

                                Intent intent = new Intent(SignIn.this, HomePage.class);
                                startActivity(intent);

                            }

                        }

                    }
                    if (existUser == false) {

                        noticeNotExitUser(typeUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    private void noticeNotExitUser(int type) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        if (type == 1) {
            alert.setMessage("Numberphone or password incorrect. Please enter again");
        } else {
            alert.setMessage("Account locked. Please create a new account");
        }

        alert.show();
    }

    private void initSignup() {
        regester.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Regester.class);
                startActivity(intent);
            }
        });
    }

    private void initForgotPassword() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login = findViewById(R.id.idLogin);
        numberPhone = findViewById(R.id.numberPhone);
        password = findViewById(R.id.idPassword);
        regester = findViewById(R.id.idRegester);
        forgotPassword = findViewById(R.id.idForgotPassword);

        checkSave = (CheckBox) findViewById(R.id.checkBox);
        initSignup();
        initLogin();
        initForgotPassword();


    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(saveUser, MODE_PRIVATE);
        String phone = sharedPreferences.getString("numberPhone", "");
        String pass = sharedPreferences.getString("password", "");

        boolean save = sharedPreferences.getBoolean("Save", false);
        if (save == true) {
            checkSave.setChecked(true);
            numberPhone.setText(phone);
            password.setText(pass);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent(SignIn.this, HomePage.class);
            startActivity(intent);
        }
    }
}