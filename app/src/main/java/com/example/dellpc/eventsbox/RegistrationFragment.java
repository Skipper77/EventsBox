package com.example.dellpc.eventsbox;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STUDENTS_REF="students";
    private  static final String[] SPINNERLIST=new String[]{"CSE","IT","ECE","ME","CIVIL"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  MaterialBetterSpinner materialDesignSpinnerbranch,materialDesignSpinneryear;
    private EditText nameET,phoneET,emailET,universityrollnoET,studentidET,passwordET,confirmPasswordET,codeEt;

    private String branch=null,year=null;
    private Button registerbtn;
    private RegistrationFragment self;
    private Integer radiobtnId;
    private  PopupWindow mpopupwindow;
    private View v,customView;
    private LayoutInflater inflater;
    private String verificationCode;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListerner;
    private boolean value,value2;
    private ProgressDialog progressDialog;
    private Student student;
    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_registration, container, false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinnerbranch = (MaterialBetterSpinner)
                v.findViewById(R.id.android_material_design_spinner_branch);
        materialDesignSpinnerbranch.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinneryear = (MaterialBetterSpinner)
                v.findViewById(R.id.android_material_design_spinner_year);
        materialDesignSpinneryear.setAdapter(arrayAdapter2);

        //////////////////////////////////////////////////////////////////
        database=FirebaseDatabase.getInstance();
        System.out.println("ref : "+database);
        mRef=database.getReference().child(STUDENTS_REF);
        System.out.println("ref : "+mRef);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getActivity());
        /////////////////////////////////////////////////////////////////
        self=this;
        this.mapping(v);
        this.listener(v);
        return v;
    }
public void mapping(View v){
    nameET=(EditText) v.findViewById(R.id.input_name);
    universityrollnoET=(EditText) v.findViewById(R.id.input_university_rollno);
    phoneET=(EditText) v.findViewById(R.id.input_phone);
    emailET=(EditText)v.findViewById(R.id.input_emailid);
    studentidET=(EditText) v.findViewById(R.id.input_student_id);
    passwordET=(EditText)v.findViewById(R.id.input_passwordEt);
    confirmPasswordET=(EditText) v.findViewById(R.id.input_confirmPasswordEt);

    registerbtn=(Button)v.findViewById(R.id.registerbtn);

}
    public void listener(View v){
         registerbtn.setOnClickListener(this);
        materialDesignSpinnerbranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                self.setSelectedBranch((parent.getItemAtPosition(position)).toString());
            }
        });
        materialDesignSpinneryear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                self.setSelectedYear((parent.getItemAtPosition(position)).toString());
            }
        });


    }

    @Override
    public void onClick(View p) {

        String name=nameET.getText().toString();
        String universityroll=universityrollnoET.getText().toString();
        String studentid=studentidET.getText().toString();
        String email=emailET.getText().toString();
        String branch=self.getSelectedBranch();
        String year=self.getSelectedYear();
        /*radiobtnId=radiosex.getCheckedRadioButtonId();
        Log.d("radioid",radiobtnId.toString());
        if(radiobtnId!=-1)
        radiobtn=(RadioButton)v.findViewById(radiobtnId);
        String gender=radiobtn.getText().toString();*/
        String password=passwordET.getText().toString();
        String confirmPassword=confirmPasswordET.getText().toString();
        String phone=phoneET.getText().toString();
        ArrayList idset=new ArrayList();
        idset.add(nameET.getId());
        idset.add(universityrollnoET.getId());
        idset.add(studentidET.getId());
        idset.add(emailET.getId());
        idset.add(materialDesignSpinnerbranch.getId());
        idset.add(materialDesignSpinneryear.getId());
       /* idset.add(radiosex.getId());*/
        idset.add(passwordET.getId());
        idset.add(confirmPasswordET.getId());
        idset.add(phoneET.getId());


      final LinkedHashMap<Integer,String> m=new LinkedHashMap<>();
        m.put(0,name);
        m.put(1,universityroll);
        m.put(2,studentid);
        m.put(3,email);
        m.put(4,branch);
        m.put(5,year);
        /*m.put(6,gender);*/
        m.put(6,password);
        m.put(7,confirmPassword);
        m.put(8,phone);

        HashMap<Integer,ArrayList<Integer>>errorCodes=new HashMap<>();
        errorCodes=checkValidities(m);
        boolean value=errorCodes.isEmpty();
        if(!value){
            showerrors(errorCodes,idset);
        }
        else{

           /* final ProgressDialog progressDialog = new ProgressDialog(getActivity(),android.R.style.Theme_Holo_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("please wait..");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            //onLoginSuccess();
                            // onLoginFailed();
                            progressDialog.dismiss();
                            showVerificationPopup(m);
                        }
                    }, 3000);
*/
                   try {
                       startRegistration(m);
                   }catch(Exception e){
                       e.printStackTrace();
                   }
        }
    }

    private void startRegistration(LinkedHashMap<Integer,String> map){
        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
                   /* boolean emailexist = checkIfEmailAlreadyExists(map.get(3).toString());
                    boolean studentIdexist  = checkIfStudentIdExists(map.get(2).toString());*/

        /*System.out.println("StudentId exist "+studentIdexist);
        System.out.println("email exist "+emailexist);*/
                     Log.d("IN START REGISTRATION : ","AFTER EMAIL AND STUDENT ID ");
                /*   if(!emailexist && !studentIdexist){*/
                       //  Registering the User....
                       System.out.println("checked email and student id both are false");
                       registerNewUser(map);
                 //  }
                   /*else{
                       progressDialog.dismiss();
                       Toast.makeText(getActivity(),"Email  or Student id Already exist",Toast.LENGTH_SHORT).show();
         }*/
    }
                   private void registerNewUser(LinkedHashMap<Integer,String> map){

                       student=new Student();
                       student.set_Name(map.get(0).toString());
                       student.set_University_roll_no(map.get(1).toString());
                       student.set_StudentId(map.get(2).toString());
                       student.set_Email(map.get(3).toString());
                       student.set_Branch(map.get(4).toString());
                       student.set_Year(map.get(5).toString());
                       student.set_Password(map.get(6).toString());
                       student.set_Phone(map.get(8).toString());
                        mAuth.createUserWithEmailAndPassword(student.get_Email(), student.get_Password())
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       //On Success


                                       String uid = mAuth.getCurrentUser().getUid();
                                       Log.d("In create User with Email ", "Getting uid");
                                       System.out.println("Uid : " + uid);
                                       mAuth.signOut();
                                       self.insertIntoDatabase(uid, student);

                                   }

                               });

                   }

                   private void insertIntoDatabase(String uid,Student student){
                       System.out.println("Inside insert into database");
                       DatabaseReference keyRef=mRef.push();
                       String key=keyRef.getKey();
                       System.out.println("key "+key);
                       student.set_Uid(uid);
                       student.set_KeyId(key);

                   DatabaseReference kref= mRef.child(key).getRef();
                       kref.setValue(student, new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                               progressDialog.dismiss();
                               if(databaseError!=null){
                                   Toast.makeText(getActivity(),"Email or student id already exist!!",Toast.LENGTH_LONG).show();
                               }
                               else{
                                   Toast.makeText(getActivity(),"Successfully registered",Toast.LENGTH_LONG).show();
                                   nameET.setText("");
                                   emailET.setText("");
                                   phoneET.setText("");
                                   universityrollnoET.setText("");
                                   studentidET.setText("");
                                   passwordET.setText("");
                                   confirmPasswordET.setText("");
                                   materialDesignSpinnerbranch.setText("");
                                   materialDesignSpinneryear.setText("");
                                   getActivity().finish();

                               }


                           }
                       });



                     /*  addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                                       progressDialog.dismiss();
                               System.out.println("Inside addOnSuccess");
                               Toast.makeText(getActivity(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                           }

                       });*/
                   }


    private boolean checkIfStudentIdExists(String id){
        DatabaseReference query=mRef.orderByChild("_StudentId").equalTo(id).getRef();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String val=dataSnapshot.getValue().toString();
                if(dataSnapshot.exists()||val!=null)
                    value2=true;
                else
                    value2=false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return value2;
    }

    private boolean checkIfEmailAlreadyExists(String email){
      Log.d("Inside Check If EmailAlready Exist ","email exist or not");
        mRef.orderByChild("_Email").equalTo(email).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                            String val=dataSnapshot.getValue().toString();
                if(val!=null)
                    value=true;
                else
                    value=false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;
                  /*   public void setValue(boolean val){
                      value=val;
                         }*/
        return value;
    }

    public void setSelectedBranch(String branch){
            this.branch=branch;
    }
    public String getSelectedBranch(){
        return self.branch;
    }
    public void setSelectedYear(String year){
        this.year=year;
    }
    public String getSelectedYear(){
        return self.year;
    }

    public ArrayList<Integer> getEmptyFieldsIfAny(LinkedHashMap m){
        ArrayList<Integer> arr=new ArrayList<>();
        if(!arr.isEmpty()){arr.clear();}
        LinkedHashMap<Integer,String> elements=Validator.emptyTestForCollection(m);
        for(Map.Entry hm:elements.entrySet()){
            if(hm.getValue().equals("EMPTY"))
                arr.add((Integer)hm.getKey());
        }
        return arr;
    }


public HashMap<Integer,ArrayList<Integer>> checkValidities(LinkedHashMap m){
    HashMap<Integer,ArrayList<Integer>> errcodes=new HashMap<>();
     if(!errcodes.isEmpty()){errcodes.clear();}
    // check empty test
    ArrayList<Integer> arr=self.getEmptyFieldsIfAny(m);
    boolean emptyTestResult=arr.isEmpty();
    if(!emptyTestResult){
        errcodes.put(404,arr);
    }

    //check for email pattern
     boolean emailPatternResult= Validator.validEmailPatternTest(m.get(3).toString());

    //check university length
     boolean universityLengthTestResult=Validator.lengthCheck(m.get(1).toString(),10);
    //check student id length
     boolean studentIdTestResult=Validator.lengthCheck(m.get(2).toString(),7);
    //check phone number length
     boolean phoneTestResult=Validator.phoneLengthTest(m.get(8).toString());

    //password check
     boolean passwordPatternResult=Validator.passwordPatternTest(m.get(6).toString());

    //password confirmpassword match
    boolean passwordMatch=Validator.matchPasswordTest(m.get(6).toString(),m.get(7).toString());

    if(!emailPatternResult){errcodes.put(405,null);}
    if(!universityLengthTestResult){errcodes.put(406,null);}
    if(!studentIdTestResult){errcodes.put(407,null);}
    if(!phoneTestResult){errcodes.put(408,null);}
    if(!passwordPatternResult){errcodes.put(409,null);}
    if(!passwordMatch){errcodes.put(410,null);}


    return errcodes;
}
    public void showerrors(HashMap<Integer,ArrayList<Integer>> errorCodes,ArrayList<Integer> idset){

        if(errorCodes.containsKey(404)){
            ArrayList<Integer> arr=errorCodes.get(404);
              for(int a:arr){
                    Object ele=v.findViewById((Integer)idset.get(a));
                    if(ele instanceof EditText)
                    {
                        EditText field=(EditText)ele;
                        field.setError("Empty Field");
                    }
                    else if(ele instanceof MaterialBetterSpinner){
                        MaterialBetterSpinner mspinner=(MaterialBetterSpinner)ele;
                        mspinner.setError("Must select something from the items");
                    }

                }
            }
        if(errorCodes.containsKey(405))
        {
              emailET.setError("Check email. Expected pattern:abc@example.com");
        }
        if(errorCodes.containsKey(406)){
            universityrollnoET.setError("Expected Length : 10 ");
        }

        if(errorCodes.containsKey(407)){
            studentidET.setError("Expected length : 07");
        }
        if(errorCodes.containsKey(408)){
            phoneET.setError("Expected length : 10");
        }

        if(errorCodes.containsKey(409)){
            passwordET.setError("Must contain atleast 1 small alphabet,atleast 1 capital letter,1 digit,1 symbol");
            confirmPasswordET.setError(null);
        }
        if(errorCodes.containsKey(410)){
            confirmPasswordET.setError("Passwords donot match");
        }
    }

    private class PostData extends AsyncTask<Void,Void,String>{
       private Context context;
       private String url=null;
        private LinkedHashMap<Integer,String> m;
        private PostData postData=this;
        private String data;
        String code=null;
        public PostData(Context c,String url,LinkedHashMap<Integer,String> m){
            this.context=c;
            this.url=url;
            this.m=m;

        }

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(Void... params) {
            String stream="";
            code=Utility.random_number();
            self.verificationCode=code;

            //ready data

            try{
                System.out.println("Url -> "+url);
                data = Utility.getDataReady(m);
                data += "&" + URLEncoder.encode("code", "UTF-8")
                        + "=" + URLEncoder.encode(code, "UTF-8");

                URL urli =new URL(url);
                HttpURLConnection urlConnection=(HttpURLConnection)urli.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setInstanceFollowRedirects(false);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setUseCaches(false);
                urlConnection.connect();

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(data);
                writer.close();
                //read response

                InputStream is = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String response;
                while ((response = bufferedReader.readLine()) != null) {
                    result.append(response);
                }
                bufferedReader.close();
                is.close();
                //Log.v("hello", "in download method");
                stream = result.toString();
                urlConnection.disconnect();
                System.out.println(stream);
                //parseObject(stream);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String resResult) {
            // do on receiving message
            if(resResult.equals("SUCCESS")){
                      Toast.makeText(getContext(),"Register Successful", Toast.LENGTH_LONG);
            }
            if(resResult.equals("CONERR")){
                Toast.makeText(getContext(),"CONNECTION UN Successful", Toast.LENGTH_LONG);
            }
            if(resResult.equals("QYUN")){
                Toast.makeText(getContext(),"QUERY UN Successful", Toast.LENGTH_LONG);
            }
        }
    }

    public void popUpListeners(View customView,LinkedHashMap<Integer,String> m){

        codeEt=(EditText) customView.findViewById(R.id.codeEt);
        Button verify=(Button)customView.findViewById(R.id.verifyBtn);
        Button resend=(Button)customView.findViewById(R.id.resendBtn);
        final LinkedHashMap<Integer,String> me=new LinkedHashMap<>();
        me.putAll(m);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  code = codeEt.getText().toString();
                if(code.isEmpty()){codeEt.setError("Empty field");}
                else{
                    //check code

                    if(code.equals(verificationCode)){
                        Toast.makeText(getContext(),"Verify Button",Toast.LENGTH_LONG).show();
                        self.registerUser(me);
                    }

                }
            }
        });

          resend.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  PostData postData=new PostData(getContext(),"http://10.0.3.2:80/sendemail.php",me);
                  postData.execute();
              }
          });
    }
         public void registerUser(final LinkedHashMap m){
                    Student student=new Student();
                    student.set_Name(m.get(0).toString());
                    student.set_University_roll_no(m.get(1).toString());
                    student.set_StudentId(m.get(2).toString());
                    student.set_Email(m.get(3).toString());
                    student.set_Branch(m.get(4).toString());
                    student.set_Year(m.get(5).toString());
                    student.set_Password(m.get(6).toString());
                    student.set_Phone(m.get(8).toString());
                    PostData postDataThread=new PostData(getContext(),"http://10.0.3.2:80/insertData.php",m);
                    postDataThread.execute();


         }

    public void showVerificationPopup(LinkedHashMap<Integer,String> m){
        inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        customView = inflater.inflate(R.layout.custom_popup_layout,null);
        /*mpopupwindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mpopupwindow.setElevation(5.0f);
            mpopupwindow.setFocusable(true);
            mpopupwindow.update();
        }
        mpopupwindow.showAtLocation(v, Gravity.CENTER,0,0);*/
        AlertDialog.Builder mpopupwindow=new AlertDialog.Builder(getActivity());
        mpopupwindow.setView(customView);
        Dialog dialog=mpopupwindow.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        PostData postDataThread=new PostData(getContext(),"http://10.0.3.2:80/sendemail.php",m);
        postDataThread.execute();

        self.popUpListeners(customView,m);
    }
}
