package com.example.dbcoffeeapplication.Activity.LoginResgister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dbcoffeeapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editTxt_oldPassword, editTxt_newPassword, editTxt_retypeNewPassword;
    Button btn_confirm;
    private Toolbar toolbar;
    String idUser;

    DatabaseReference databasReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        idUser = getIntent().getStringExtra("idUser").toString();
        editTxt_newPassword = findViewById(R.id.editTxt_newPassword);
        editTxt_retypeNewPassword = findViewById(R.id.editTxt_retypeNewPassword);
        btn_confirm = findViewById(R.id.btn_confirm);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editTxt_newPassword.getText().toString();
                String retypeNewPassword = editTxt_retypeNewPassword.getText().toString();
                if (newPassword.isEmpty()) {
                    editTxt_newPassword.setError("Password not empty");
                } else if (!newPassword.equals(retypeNewPassword)) {
                    editTxt_retypeNewPassword.setError("Password is correct");
                } else {

                    String resultPassword = convertHashToString(newPassword);

                    databasReference = FirebaseDatabase.getInstance().getReference("Users").child(idUser);

                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", idUser);
                    hashMap.put("password", resultPassword);
                    databasReference.updateChildren(hashMap);

                    Toast.makeText(ChangePasswordActivity.this, "Change Password sucessfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, SignIn.class);
                    startActivity(intent);
                    finish();

                }

            }

        });


    }

    public void setSupportActionBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> finish());
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
}