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

import com.example.dbcoffeeapplication.DTO.Users;
import com.example.dbcoffeeapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPassword extends AppCompatActivity {
    private Button resetPasswordButton;
    private EditText numberPhone;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        numberPhone = (EditText) findViewById(R.id.numberPhone);
        resetPasswordButton = (Button) findViewById(R.id.idResetPassword);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryCodePicker ccp_su = (CountryCodePicker) findViewById(R.id.ccp);
                ccp_su.registerCarrierNumberEditText(numberPhone);
                String phone = ccp_su.getFullNumberWithPlus().replace(" ", "");

                DatabaseReference databasReference = FirebaseDatabase.getInstance().getReference().child("Users");

                databasReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    boolean existsUser = false;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren()){
                            Users user = data.getValue(Users.class);
                            Log.e("phone", user.getNumberphone());
                            if(phone.equals(user.getNumberphone())){

                                existsUser = true;
                                Intent intent = new Intent(ForgotPassword.this, OTPActivity.class);
                                intent.putExtra("type_numberphone","forgotpassword");
                                intent.putExtra("idUser", user.getId());

                                startActivity(intent);
                            }

                        }
                        if(!existsUser){
                            noticeExitUser();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

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
}