package com.example.dellpc.eventsbox;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public  class StudentLoginFragment extends Fragment {

private LinearLayout adminbtn;
private LinearLayout studentbtn;
    private EditText emailET;
    private EditText passwordET;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    private PrefManager prefManager;
    IntentListener intentListener;
    public StudentLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_student_login, container, false);
       /* link_signup=(TextView)v.findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);

            }
        });*/
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user==null){
                    Toast.makeText(getActivity(),"Not Logged In",Toast.LENGTH_SHORT).show();
                }
                else{
                    //FirebaseUser user=mAuth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();
                    Log.d(TAG, "onAuthStatechange " + user.getUid());
                    Log.d("studentFrag Current user uid", uid);
                    Log.d("studentFrag Current user email", email);

                    // IdentifyLogin.studentLogin=true;
                    backToHome(email, uid);
                }
            }
        };

        mProgressDialog=new ProgressDialog(getActivity());
        prefManager=new PrefManager(getActivity());
        this.mapping(v);
        this.listener(v);
    return v;
}

public void mapping(View v){
           adminbtn=(LinearLayout)v.findViewById(R.id.adminbtn);
           studentbtn=(LinearLayout)v.findViewById(R.id.studentbtn);
           emailET=(EditText)v.findViewById(R.id.input_email);
           passwordET=(EditText)v.findViewById(R.id.input_password);
       }

    public void listener(View v){
        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
               boolean checkCrediantialsTestValue = checkCrediantialsSyntax();
                                 if(checkCrediantialsTestValue){
                                     //Database call
                                    adminLogin();

                                 }
            }

        });
        studentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                boolean checkCrediantials=checkCrediantialsSyntax();
                if(checkCrediantials)
                { studentLogin();}
            }
        });

    }
    private void showDialog(){
        mProgressDialog.setMessage("Checking crediantials....");
        mProgressDialog.setTitle("Authenticating");
        mProgressDialog.show();
    }

    private void adminLogin(){
        String email=emailET.getText().toString().trim();
        String password=passwordET.getText().toString().trim();
        boolean checkAdminResult=checkIfAdmin(email,password);
     /*   mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    mProgressDialog.dismiss();

                    Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                }
                else{
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(),"Invaid email or password",Toast.LENGTH_LONG).show();
                }

            }
        });*/

        if(checkAdminResult){
            mProgressDialog.dismiss();
            prefManager.setStudentLogIn(false);
            prefManager.setAdminLogIn(true);
            prefManager.setData(email,"FOOTPRINTS");
            backToHome(email,null);
        }
        else{
            Toast.makeText(getActivity(),"Invalid email or password",Toast.LENGTH_SHORT).show();
        }

    }

    private void backToHome(String email,String uid){

        intentListener.sendDataBackToHome(email,uid);

        //startActivity(intent);
    }
    private void studentLogin(){
        String email=emailET.getText().toString().trim();
        String password=passwordET.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    mProgressDialog.dismiss();
                    /*FirebaseUser user=mAuth.getCurrentUser();
                    String email=user.getEmail();
                    String uid=user.getUid();
                    IdentifyLogin.studentLogin=true;
                    backToHome(email,uid);*/
                }
                else{
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(),"Invaid email or password",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public boolean checkCrediantialsSyntax(){
        String email=emailET.getText().toString();
        String password=passwordET.getText().toString();
        int  not_Empty_Test_Value = Validator.notEmptyTest(email,password);
        boolean valid_Pattern_Test_Value = Validator.validEmailPatternTest(email);
        System.out.println(email);
        System.out.print(valid_Pattern_Test_Value);
        if(not_Empty_Test_Value==0){
            if(valid_Pattern_Test_Value)
            {
                //everything passes
                //code for database
                return true;

            }
            else{
                mProgressDialog.dismiss();
                emailET.setError("example-> abc@example.com");
            }
        }
        else{
            mProgressDialog.dismiss();
            switch(not_Empty_Test_Value){
                case 1:emailET.setError("Empty Field");
                    break;
                case 2:passwordET.setError("Empty Field");
                    break;
                case 3:emailET.setError("Empty Field");
                       passwordET.setError("Empty Field");
            }

        }
        return false;
    }

    public interface IntentListener{
        public void sendDataBackToHome(String email,String uid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IntentListener) {
            intentListener = (IntentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public boolean checkIfAdmin(String email,String password){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String id=getResources().getString(R.string.getid);
        String pass=getResources().getString(R.string.getPassword);
        if(email.equals(id)&&password.equals(pass))
            return true;
        return false;
    }
}
