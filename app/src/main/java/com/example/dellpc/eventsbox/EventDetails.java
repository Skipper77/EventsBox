package com.example.dellpc.eventsbox;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventDetails extends AppCompatActivity implements View.OnClickListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView profile_id;
    private Event event;
    private CoordinatorLayout.LayoutParams params;

    private ArrayList<Volunteer> volunteerList=new ArrayList<>();
    private FloatingActionButton registerOnThisEvent;
    //////////////////////////////////
    private PrefManager prefManager;
    private FirebaseDatabase database;
    private DatabaseReference mref;
    private FirebaseAuth mAuth;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager=new PrefManager(this);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

         database=FirebaseDatabase.getInstance();
         mref=database.getReference().child("registeredUsers");
         mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            uid=mAuth.getCurrentUser().getUid();
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("AKGEC");
        profile_id=(ImageView)findViewById(R.id.profile_id);


        dynamicToolbarColor();

        toolbarTextAppernce();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        this.mapping();
        this.getEvent();
        this.listeners();
    }

    private void mapping(){


        registerOnThisEvent=(FloatingActionButton)findViewById(R.id.registerEventBTN);
        params=(CoordinatorLayout.LayoutParams)registerOnThisEvent.getLayoutParams();
    }

    private void listeners(){
        registerOnThisEvent.setOnClickListener(this);
    }
     private void getEvent(){
       //  Bundle extras=getIntent().getExtras();
         event=(Event)getIntent().getSerializableExtra("ievent");
        if(event.getHasRegisterOption()&&!(prefManager.isAdminLogIn())&&prefManager.isLoggedIn()){
            /*registerOnThisEvent.setEnabled(true);
            registerOnThisEvent.setBackgroundColor((ContextCompat.getColor(this, R.color.registerBtnEnabled)));
       */ params.setBehavior(new FloatingActionButton.Behavior());
            registerOnThisEvent.requestLayout();
            registerOnThisEvent.setVisibility(View.VISIBLE);

        }
        else{
            /*registerOnThisEvent.setEnabled(false);
            registerOnThisEvent.setBackgroundColor((ContextCompat.getColor(this, R.color.registerBtnNotEnabled)));
            */
            params.setBehavior(null);
            registerOnThisEvent.requestLayout();
            registerOnThisEvent.setVisibility(View.GONE);


        }

         PiccasoClient.downloadImage(this,event.getImageUrl(),profile_id);
         collapsingToolbarLayout.setTitle(event.getTitle());

          // getContacts();
     }


    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.akgec1);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceAsColor")
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.color.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.color.colorPrimaryDark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        mref.child(event.getEventId()).child(uid).setValue(mAuth.getCurrentUser().getEmail()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                e.printStackTrace();
            }
        });


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_event_details, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           // return PlaceholderFragment.newInstance(position + 1);
            switch(position){
                case 0:
                    return EventDetailDescription.newInstance(event);
                case 1:

                    return EventDetailsContact.newInstance(event.getVolunteerListId());
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "Contacts";

            }
            return null;
        }
    }
}
