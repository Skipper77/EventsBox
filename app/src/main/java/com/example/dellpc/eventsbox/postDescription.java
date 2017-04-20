package com.example.dellpc.eventsbox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link postDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class postDescription extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button startImageBtn,endImageBtn;
    private ImageButton bannerBtn;
    private EditText title;
    private EditText society;
    private EditText about;
    private EditText sDate;
    private EditText eDate;
    private View view;
    private EditText venue;
    private postDescription self;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private PostEvent postEvent;
    private SimpleDateFormat dateFormatter;
    private static final int GALLERY_REQUEST=1;
    private Uri uri;

    public postDescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment postDescription.
     */
    // TODO: Rename and change types and number of parameters
    public static postDescription newInstance(String param1, String param2) {
        postDescription fragment = new postDescription();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_post_description, container, false);
        self=this;
        postEvent=(PostEvent) getActivity();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        this.mapping(view);
        this.listeners(view);
        this.setDateTimeField();
        return view;
    }

    private void setDateTimeField() {
        startImageBtn.setOnClickListener(self);
        endImageBtn.setOnClickListener(self);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                eDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void listeners(View view) {
        bannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent,1);
            }
        });
    }

    public void mapping(View v){
        title=(EditText)v.findViewById(R.id.Create_Event_Title);
        society=(EditText)v.findViewById(R.id.Create_Event_Society);
        about=(EditText)v.findViewById(R.id.Create_Event_About);
        sDate=(EditText)v.findViewById(R.id.Start_date);
        eDate=(EditText)v.findViewById(R.id.End_date);
        venue=(EditText)v.findViewById(R.id.Create_Event_Venue);
        startImageBtn=(Button)v.findViewById(R.id.Start_date_picker);
        endImageBtn=(Button)v.findViewById(R.id.End_date_picker);
        bannerBtn=(ImageButton)v.findViewById(R.id.Add_Banner);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            uri=data.getData();
            bannerBtn.setImageURI(uri);
        }
    }

    public void onClick(View view) {
        if(view == startImageBtn) {
            fromDatePickerDialog.show();
        } else if(view == endImageBtn) {
            toDatePickerDialog.show();
        }
    }

    public void collectData(){
        HashMap<String,String> descFragDataMap=new HashMap<>();
        String mtitle=title.getText().toString();
        String mdesc=about.getText().toString();
        String mvenue=venue.getText().toString();
        String msociety=society.getText().toString();
        String mfromDate=sDate.getText().toString();
        String mEndDate=eDate.getText().toString();

        boolean checkResult= !TextUtils.isEmpty(mtitle)&&!TextUtils.isEmpty(mdesc)
                &&!TextUtils.isEmpty(mvenue)&&!TextUtils.isEmpty(msociety)&&!TextUtils.isEmpty(mfromDate)&&!TextUtils.isEmpty(mEndDate);

        if(checkResult){
            descFragDataMap.put("bv","bv");
        }
        else{
            Toast.makeText(getActivity(),"Some Fields Are Empty",Toast.LENGTH_SHORT).show();
        }


    }
}
