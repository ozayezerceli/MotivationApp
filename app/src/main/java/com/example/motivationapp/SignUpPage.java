package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.UUID;

public class SignUpPage extends AppCompatActivity {
    private EditText username,userPassword,userEmail,userBirthdate;
    private ImageView signUpLogo;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        username = findViewById(R.id.signUp_kullanıcıAdı);
        userPassword = findViewById(R.id.signUp_password);
        userEmail = findViewById(R.id.signUp_userEmail);
        userBirthdate = findViewById(R.id.signUp_birthDate);
        signUpLogo = findViewById(R.id.signUp_ImageView);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void signUp(View v){
    mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(),userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                createUserDatabase();
                Toast.makeText(SignUpPage.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();
                goToLoginPage();

            }else{
                Toast.makeText(SignUpPage.this, "Kayıt Başarısız!", Toast.LENGTH_LONG).show();
            }
        }
    });


    }

    public void goToLoginPage(){
        Intent intent= new Intent(getApplicationContext(),LoginPage.class);
        startActivity(intent);
        finish();

    }

    public void createUserDatabase(){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        databaseReference.child("Users").child(uuidString).child("username").setValue(username);
        databaseReference.child("Users").child(uuidString).child("useremail").setValue(userEmail);
        databaseReference.child("Users").child(uuidString).child("usersignuptime").setValue(ServerValue.TIMESTAMP);
        databaseReference.child("Users").child(uuidString).child("userbirthdate").setValue(userBirthdate);
        //databaseReference.child("Users").child(uuidString).child("userquotes");
        //databaseReference.child("Users").child(uuidString).child("favquotes");
    }
}
