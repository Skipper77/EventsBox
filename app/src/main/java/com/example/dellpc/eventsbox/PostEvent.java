package com.example.dellpc.eventsbox;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class PostEvent extends AppCompatActivity {

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
    ///////////////////////////////////////////
    private FirebaseDatabase database;
    private DatabaseReference mref;
    private FirebaseStorage mstore;
    private StorageReference mstorageRef;
    private postDescription descFrag;
    private PostContactsFragment postContactsFragmentt;
    private Event newEvent;
    private Volunteer volunteer;
    HashMap<String,Event>dataMapFromDescFrag;
    HashMap<String,Volunteer>dataMapFromPostContactFrag;
    private View view2;
    private PostEvent self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        database=FirebaseDatabase.getInstance();
        mref=database.getReference();
        mstore=FirebaseStorage.getInstance();
        mstorageRef=mstore.getReference();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        self=this;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view2=view;
                storeInDataBase();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_event, menu);
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

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            View rootView = inflater.inflate(R.layout.fragment_post_event, container, false);
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
            //  return PlaceholderFragment.newInstance(position + 1);
            switch(position){
                case 0:
                    descFrag= postDescription.newInstance(null,null);
                    return descFrag;
                case 1:
                    postContactsFragmentt= PostContactsFragment.newInstance(null,null);
                    return postContactsFragmentt;
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


    ///////////////////////////////////////////////////////////////////////////////////////

    public void storeInDataBase(){
                System.out.println("in store in database");
                dataMapFromDescFrag=descFrag.collectData();
                dataMapFromPostContactFrag=postContactsFragmentt.collectContactsData();

               if(dataMapFromDescFrag!=null){

                   newEvent=dataMapFromDescFrag.get("event");
                   System.out.println(newEvent.toString());
                  Uri uri= newEvent.getImageUri();
               //Uri downloadUri;

                   //Database


                   if(uri!=null){

                  StorageReference filepath= mstorageRef.child("Photos").child(uri.getLastPathSegment());
                       filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             @SuppressWarnings("VisibleForTests")
                             Uri downloadUri=taskSnapshot.getDownloadUrl();
                               System.out.println("in onsuccess");
                               insertEvent(downloadUri);
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                                 e.printStackTrace();
                           }
                       });


               }




    }
    }

    public void insertEvent(Uri downloaduri){
       if(downloaduri!=null) {
           newEvent.setImageUrl(downloaduri.toString());
       }
       else{
           newEvent.setImageUrl(null);
       }

        DatabaseReference pushRef = mref.child("Events").push().getRef();
        DatabaseReference contactref=mref.child("contacts").push().getRef();
        String contactKey=contactref.getKey();
        newEvent.setVolunteerListId(contactKey);
        System.out.println(newEvent.toString());

        String pushKey=pushRef.getKey();

        newEvent.setEventId(pushKey);
        mref.child("Events").child(pushKey).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Successfully Posted the event", Toast.LENGTH_SHORT).show();
                self.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                      e.printStackTrace();
            }
        });


       mref.child("contacts").child(contactKey).setValue(dataMapFromPostContactFrag, new DatabaseReference.CompletionListener() {
           @Override
           public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
               if(databaseError!=null){
                   Snackbar.make(view2, "successfuly done", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
               }
               else{

                   Snackbar.make(view2, "Replace with your own action", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
                  System.out.println( databaseError.getDetails());
               }

           }

       });



    }
}
