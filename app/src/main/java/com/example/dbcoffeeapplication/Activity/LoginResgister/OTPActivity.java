package com.example.dbcoffeeapplication.Activity.LoginResgister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbcoffeeapplication.Activity.HomePage.HomePage;
import com.example.dbcoffeeapplication.DTO.Users;
import com.example.dbcoffeeapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText code1, code2, code3, code4;
    String otp1, otp2,otp3, otp4;
    Button btnVerify;
    TextView txtErrorOtp, sendAgain;
    EditText otp;

    String phoneNumber, phone_forgotpass,type_numbephone,otpid;
    FirebaseAuth mAuth;
    FirebaseDatabase database ;
    DatabaseReference databasReference;
    public void init(){
        btnVerify = findViewById(R.id.idsendcode);

        otp = (EditText) findViewById(R.id.idOTP);
        sendAgain = findViewById(R.id.sendAgain);
        txtErrorOtp = findViewById(R.id.errorOTP);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        init();
        type_numbephone =  getIntent().getStringExtra("type_numberphone").toString();
        Log.e("type_numbephone",type_numbephone);

        phoneNumber = getIntent().getStringExtra("phoneNumber").toString();
        Log.e("phoneNumber",phoneNumber);
        String OTP = otp.getText().toString();
        Log.e("OTP",OTP);

        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databasReference = database.getReference("Users");
        initOTP();
        sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOTP();
            }
        });
        txtErrorOtp.setText("");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                 forgotpassword
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                signInWithPhoneAuthCredential(credential);

            }

        });
    }

    private void initOTP() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s,forceResendingToken);
                        otpid = s;
                    }

                    @Override

                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    private String convertHashToString(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(type_numbephone.equals("signup")){
                                String resultUsername =  getIntent().getStringExtra("username").toString();
                                String resultPass =  getIntent().getStringExtra("password").toString();
                                String id = getIntent().getStringExtra("id").toString();

                                //ma hoa mat khau
                                try {
                                    String pass_convert = convertHashToString(resultPass);

                                    Users user = new Users(id, phoneNumber, resultUsername,pass_convert,"","","1","","","","1");
                                    databasReference.child(id).setValue(user);

                                    Intent intent = new Intent(OTPActivity.this, HomePage.class);

//
                                    startActivity(intent);

                                } catch (NoSuchAlgorithmException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                            else if(type_numbephone.equals("forgotpassword")){
                                Intent intent = new Intent(OTPActivity.this, ChangePasswordActivity.class);
                                intent.putExtra("numberphone",phoneNumber);
                                startActivity(intent);

                            }

                            finish();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                            txtErrorOtp.setText("Error OTP. Please enter again " );

                        }
                    }
                });
    }
}