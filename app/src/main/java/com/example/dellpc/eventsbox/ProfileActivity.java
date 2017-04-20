package com.example.dellpc.eventsbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
private Button sign_out_btn;
private FirebaseAuth mAuth;
    private PrefManager prefManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

  mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfileActivity.this, Home.class));
                    finish();

                }
        }
        };

     prefManager=new PrefManager(this);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       sign_out_btn=(Button)findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setLoggedIn(false);
                if(prefManager.isAdminLogIn()){
                 prefManager.setAdminLogIn(false);
                   /* Home home=new Home();
                    home.setNavMenu();*/
                    startActivity(new Intent(ProfileActivity.this, Home.class));
                    finish();
                }
                else {
                    mAuth.signOut();
                }


            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(!prefManager.isAdminLogIn()){
            mAuth.addAuthStateListener(mAuthListener);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
