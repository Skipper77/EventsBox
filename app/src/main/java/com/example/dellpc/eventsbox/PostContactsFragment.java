package com.example.dellpc.eventsbox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private EditText contact_one_name,contact_one_phone,contact_one_email,contact_two_name,contact_two_phone,contact_two_email,contact_three_name,contact_three_phone,contact_three_email;
    private Volunteer volunteer1,volunteer2,volunteer3;
    public PostContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostContactsFragment newInstance(String param1, String param2) {
        PostContactsFragment fragment = new PostContactsFragment();
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

        v= inflater.inflate(R.layout.fragment_post_contacts, container, false);
        this.mapping(v);
        return v;
    }

    private void mapping(View view){
        contact_one_name=(EditText) view.findViewById(R.id.contact_one_name);
        contact_one_phone=(EditText) view.findViewById(R.id.contact_one_phone);
        contact_one_email=(EditText) view.findViewById(R.id.contact_one_email);


        contact_two_name=(EditText) view.findViewById(R.id.contact_two_name);
        contact_two_phone=(EditText) view.findViewById(R.id.contact_two_phone);
        contact_two_email=(EditText) view.findViewById(R.id.contact_two_email);


        contact_three_name=(EditText) view.findViewById(R.id.contact_three_name);
        contact_three_phone=(EditText) view.findViewById(R.id.contact_three_phone);
        contact_three_email=(EditText) view.findViewById(R.id.contact_three_email);


    }

    public HashMap<String,Volunteer> collectContactsData(){
        HashMap<String,Volunteer> contactFragDataList=new HashMap<>();
        String c_one_name=contact_one_name.getText().toString();
        String c_one_phone=contact_one_phone.getText().toString();
        String c_one_email=contact_one_email.getText().toString();

        String c_two_name=contact_two_name.getText().toString();
        String c_two_phone=contact_two_phone.getText().toString();
        String c_two_email=contact_two_email.getText().toString();

        String c_three_name=contact_three_name.getText().toString();
        String c_three_phone=contact_three_phone.getText().toString();
        String c_three_email=contact_three_email.getText().toString();

        volunteer1=new Volunteer(c_one_name,c_one_phone,c_one_email);
        volunteer2=new Volunteer(c_two_name,c_two_phone,c_two_email);
        volunteer3=new Volunteer(c_three_name,c_three_phone,c_three_email);

        contactFragDataList.put("01",volunteer1);
        contactFragDataList.put("02",volunteer2);
        contactFragDataList.put("03",volunteer3);


        return contactFragDataList;


    }

}
