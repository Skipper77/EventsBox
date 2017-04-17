package com.example.dellpc.eventsbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link SocietyDesciptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocietyDesciptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public SocietyDesciptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocietyDesciptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocietyDesciptionFragment newInstance(String param1, String param2) {
        SocietyDesciptionFragment fragment = new SocietyDesciptionFragment();
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
        View v=inflater.inflate(R.layout.fragment_society_desciption, container, false);
        TextView descTV=(TextView)v.findViewById(R.id.descTV);
        descTV.setText("Born on 5th of September 2005, with a motto of searching hidden creativity among technical\n" +
                "\n" +
                "students, itself makes FOOTPRINTS different from any other society present at AKGEC.\n" +
                "\n" +
                "Itihaas ke panno se…: Well! the credit for establishing the society goes to Mr. Kumar Varun  & Shubhashees Pandey .  Varun Agarwal,Shobhit Kulshreshtha laid stress on the fact that the society  mainly concentrates on nurturing creativity.One of the passed out senior wrote a poem “ALVIDA HOSTEL” and pasted on the college wall. The poem was not only praised but the concept of its display forced the students to think that there should be a board for the Wall Magazine where students can put their talents on display.\n" +
                "\n" +
                "FOOTPRINTS .....A BRIDGE BETWEEN STAFF & STUDENT: Footprints keeps on displaying matters likeinfo-waves, jigyasa,dil se,rubaroo,hridyansh dealing with various aspects of creativity.\n" +
                "\n" +
                "FOOTPRINTS ….IN SEARCH OF TALENTS: A K G E C  has participated several times at  MMA Dayal Annual debate competition . For the Academic session 2008-09, it was FOOTPRINTS which took the responsibility of filtering genuine candidates to represent college at this prestigious event.FOOTPRINTS conducted an intra-college debate and fortunately the winner of this debate later won the 1st prize and made the college proud….\n" +
                "\n" +
                "FOOTPRINTS …. AT VIBRATIONS : FOOTPRINTS  has always taken a keen interest in organizing various competitions at college ANNUAL FEST -“ VIBRATIONS”. Some of the events conducted by FOOTPRINTS every year are:Pot-decoration ,Rangoli making,Mehndi-competition,Sketching," +
                "T-shirt-Painting,Calligraphy,Collage-making,Face-Painting.");
        descTV.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }


}
