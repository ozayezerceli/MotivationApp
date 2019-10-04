package com.example.motivationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class SignUpPage extends AppCompatActivity {
    private EditText username,userPassword,userEmail,userBirthdate;
    private ImageView signUpLogo;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button signupButton;
    private StorageReference storageRef;
    private String imgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        username = findViewById(R.id.signUp_kullanıcıAdı);
        userPassword = findViewById(R.id.signUp_password);
        userEmail = findViewById(R.id.signUp_userEmail);
        userBirthdate = findViewById(R.id.signUp_birthDate);
        signUpLogo = findViewById(R.id.signUp_ImageView);
        signupButton = findViewById(R.id.signUp_kayıt);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageRef = FirebaseStorage.getInstance().getReference();



    }

    public void sign_Up(View view){
        mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(),userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.isComplete()){
                    createUserDatabase();
                    goToSplash();
                    Toast.makeText(SignUpPage.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpPage.this, "Kayıt Başarısız!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void createUserDatabase(){
        final DatabaseReference mRootRef = databaseReference.child("user");
        String userBirth = userBirthdate.getText().toString();
        UUID uuid = UUID.randomUUID();
        final String uuidString = uuid.toString();
        mRootRef.child(uuidString).child("name").setValue(username.getText().toString());
        mRootRef.child(uuidString).child("email").setValue(userEmail.getText().toString());
        mRootRef.child(uuidString).child("bdate").setValue(userBirth);
        mRootRef.child(uuidString).child("userId").setValue(uuidString);

        for (int i = 1 ; i <= 99; i++){
            StorageReference imgRef = FirebaseStorage.getInstance().getReference("MotivationalQuotes/"+i+".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imgUrl = uri.toString();
                    UUID quoteUid = UUID.randomUUID();
                    String quoteUidStr = quoteUid.toString();
                    mRootRef.child(uuidString).child("quotes").child(quoteUidStr).child("imgUrl").setValue(imgUrl.toString());
                    mRootRef.child(uuidString).child("quotes").child(quoteUidStr).child("isFav").setValue("false");
                    mRootRef.child(uuidString).child("quotes").child(quoteUidStr).child("qId").setValue(quoteUidStr);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        username.setText("");
        userPassword.setText("");
        userEmail.setText("");
        userBirthdate.setText("");
    }

    public void goToSplash(){
        Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
        intent.putExtra("signUp","1");
        startActivity(intent);
        finish();
    }
}
