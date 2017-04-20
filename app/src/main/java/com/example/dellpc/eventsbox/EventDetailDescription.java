package com.example.dellpc.eventsbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link EventDetailDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailDescription extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Event event;
    private View v;
    private TextView event_detail_title,event_detail_society,event_detail_location,event_detail_startDate,event_detail_endDate,event_detail_description;
    public EventDetailDescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EventDetailDescription.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailDescription newInstance(Event event) {
        EventDetailDescription fragment = new EventDetailDescription();
        Bundle args = new Bundle();
       args.putSerializable(ARG_PARAM1, event);
        //args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_event_detail_description, container, false);
        this.mapping(v);
        this.fillData();
        return v;
    }
public void mapping(View v){
    event_detail_title=(TextView)v.findViewById(R.id.event_detail_title);
    event_detail_description=(TextView)v.findViewById(R.id.event_detail_description);
    event_detail_location=(TextView)v.findViewById(R.id.event_detail_location);
    event_detail_startDate=(TextView)v.findViewById(R.id.event_detail_startDate);
    event_detail_endDate=(TextView)v.findViewById(R.id.event_detail_endDate);
    event_detail_society=(TextView)v.findViewById(R.id.event_detail_society);

    event_detail_description.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()&MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        }
    });


}
public void fillData(){
    event_detail_title.setText(event.getTitle());
    event_detail_description.setText(event.getDescription());
    event_detail_location.setText(event.getVenue());
    event_detail_startDate.setText(event.getStartDate());
    event_detail_endDate.setText(event.getEndDate());
    event_detail_society.setText(event.getSocietyBelongTo());

}



}
