package com.example.esoldy;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class testregister extends AppCompatActivity {
    private Button CreateAccountButton,verifyemail;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    private EditText email;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testregister);

        email=(EditText)findViewById(R.id.editTextEmailAddress);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);

        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        loadingBar = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();






        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }







    private void CreateAccount()
    {   String emailid=email.getText().toString();
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();











        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailid) && emailid.matches(emailPattern))

        {

            Toast.makeText(this, "Please write your email in validate Format", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {


            //firebaseAuth.createUserWithEmailAndPassword(emailid, password);





            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();






            ValidatephoneNumber(name, emailid,phone, password);
        }
    }



    private void ValidatephoneNumber(final String name,final String emailid, final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {


                                        HashMap<String, Object> userdataMap = new HashMap<>();
                                        userdataMap.put("phone", phone);
                                        userdataMap.put("email", emailid);
                                        userdataMap.put("password", password);
                                        userdataMap.put("name", name);

                                        RootRef.child("Users").child(phone).updateChildren(userdataMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(testregister.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();




                                                            Intent intent = new Intent(testregister.this, testloginactivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            loadingBar.dismiss();
                                                            Toast.makeText(testregister.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(testregister.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Toast.makeText(testregister.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(testregister.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }