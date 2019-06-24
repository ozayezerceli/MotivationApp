package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private EditText userEmail,userPassword;
    private ImageView imagelogo;
    private TextView signUpText;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userEmail = findViewById(R.id.user_email_login_edit_text);
        userPassword = findViewById(R.id.user_password_login_edit_text);
        signUpText = findViewById(R.id.sign_up_textView);
        imagelogo = findViewById(R.id.login_imageView);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null){
            goToMain();
            finish();
        }
    }

    public void signUp(View v){
        Intent intent = new Intent(getApplicationContext(),SignUpPage.class);
        startActivity(intent);
    }

    public void signIn(View v){
        mAuth.signInWithEmailAndPassword(userEmail.getText().toString(),userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    goToMain();
                    finish();
                }else{
                    Toast.makeText(LoginPage.this, "Giriş Yapılamadı!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void goToMain(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }


}
