package com.example.dellpc.eventsbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link EventDetailsContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsContact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseDatabase database;
    private DatabaseReference mref;
    private ArrayList<Volunteer> volunteerList;
    private View v;

    private EditText contact_one_name,contact_one_phone,contact_one_email,contact_two_name,contact_two_phone,contact_two_email,contact_three_name,contact_three_phone,contact_three_email;
    private Volunteer volunteer1,volunteer2,volunteer3;
   // private OnFragmentInteractionListener mListener;

    public EventDetailsContact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EventDetailsContact.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailsContact newInstance(String volunteerListId) {
        EventDetailsContact fragment = new EventDetailsContact();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, volunteerListId);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database=FirebaseDatabase.getInstance();
        mref=database.getReference().child("contacts");
        if (getArguments() != null) {
            String contactsKey = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            getContacts(contactsKey);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v= inflater.inflate(R.layout.fragment_post_contacts, container, false);
        this.mapping();

        return v;
    }

    private void mapping(){
        contact_one_name=(EditText) v.findViewById(R.id.contact_one_name);
        contact_one_phone=(EditText) v.findViewById(R.id.contact_one_phone);
        contact_one_email=(EditText) v.findViewById(R.id.contact_one_email);


        contact_two_name=(EditText) v.findViewById(R.id.contact_two_name);
        contact_two_phone=(EditText) v.findViewById(R.id.contact_two_phone);
        contact_two_email=(EditText) v.findViewById(R.id.contact_two_email);


        contact_three_name=(EditText) v.findViewById(R.id.contact_three_name);
        contact_three_phone=(EditText) v.findViewById(R.id.contact_three_phone);
        contact_three_email=(EditText) v.findViewById(R.id.contact_three_email);
    }
    private void getContacts(String contactsKey){
        volunteerList=new ArrayList<>();


        mref.child(contactsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                volunteerList.clear();
                HashMap<String,Volunteer> td=new HashMap<>();
                if(dataSnapshot.hasChildren()){
                    Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                    for(DataSnapshot child:children){
                        Volunteer volunteer=child.getValue(Volunteer.class);
                        System.out.println(volunteer.toString());
                        td.put(child.getKey(),volunteer);

                    }
                     ArrayList<Volunteer> values=new ArrayList<Volunteer>(td.values());
                    List<String> keys = new ArrayList<String>(td.keySet());

                    for (Volunteer volunteer: values) {
                        //Log.d("firebase", job.getJobTitle());
                        volunteerList.add(volunteer);
                    }
                }
                else{
                    volunteerList.clear();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(volunteerList.size()>0){
            volunteer1=volunteerList.get(0);
            volunteer2=volunteerList.get(1);
            volunteer3=volunteerList.get(2);

            contact_one_name.setText(volunteer1.getStudent_name());
            contact_one_phone.setText(volunteer1.getStudent_phoneno());
            contact_one_email.setText(volunteer1.getStudent_email());

            contact_two_name.setText(volunteer2.getStudent_name());
            contact_two_phone.setText(volunteer2.getStudent_phoneno());
            contact_two_email.setText(volunteer2.getStudent_email());

            contact_three_name.setText(volunteer3.getStudent_name());
            contact_three_phone.setText(volunteer3.getStudent_phoneno());
            contact_three_email.setText(volunteer3.getStudent_email());

            contact_one_name.setEnabled(false);
            contact_one_phone.setEnabled(false);
            contact_one_email.setEnabled(false);

            contact_two_name.setEnabled(false);
            contact_two_phone.setEnabled(false);
            contact_two_email.setEnabled(false);

            contact_three_name.setEnabled(false);
            contact_three_phone.setEnabled(false);
            contact_three_email.setEnabled(false);


        }



    }
}

