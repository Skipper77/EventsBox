package com.example.dellpc.eventsbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private View layout;
    private RecyclerView rv;
    private Event event;
    private TextView studentView,nameView;
    private ArrayList<Event> eventList;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private int REQUEST_CODE=0;
    String value;
    private Home self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager=(ViewPager)findViewById(R.id.sliderContainer);
        viewPager.setAdapter(new CustomSwipeAdapter(this));
        final View headerLayout = navigationView.getHeaderView(0);
        layout=headerLayout.findViewById(R.id.click_me_to_login);
        nameView=(TextView) headerLayout.findViewById(R.id.nameView);
        studentView=(TextView) headerLayout.findViewById(R.id.studentView);
        //////////////////////////////////////////////////////////
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startLoginActivity();

            }
        });
        /////////////////////////////////////////////////////
        self=this;
        rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setItemAnimator(new DefaultItemAnimator());
////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Events");
        mAuth=FirebaseAuth.getInstance();
        /////////////////////////////////////////////
        eventList=new ArrayList<>();
        //getData();
        //Downloader downloader= (Downloader) new Downloader(Home.this,rv).execute();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                //        setUrl(children);
                for(DataSnapshot child:children){
                    value=child.getValue(String.class);
                }
                // value = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + value);
                event=new Event();
                event.setImageUrl(value);
                event.setTitle("Poster Making");
                event.setSocietyBelongTo("Footprints");
                eventList.add(event);
                Event event2=new Event();
                event2.setImageUrl(value);
                event2.setTitle("Sketch Competition");
                event2.setSocietyBelongTo("Footprints");
                eventList.add(event2);
                Log.d("Inside ref.onDatachange","After this HomeadapterLine");
                HomeEventsAdapter homeEventsAdapter=new HomeEventsAdapter(self,eventList);
                rv.setAdapter(homeEventsAdapter);
                Log.d("already set the adapter","ths is after the setAdapter");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
           public void startLoginActivity(){
               Intent i=new Intent(Home.this,Login_activity.class);
               startActivityForResult(i,REQUEST_CODE);
           }

           @Override
           protected void onActivityResult(int requestCode, int resultCode,
                                           Intent data) {
               if (requestCode == REQUEST_CODE) {
                   if (resultCode == RESULT_OK) {
                       // do your stuff

                       System.out.println("On ACtivity result");
                   String uid=data.getStringExtra("uid");
                       DatabaseReference studentref=database.getReference().child("students");
                       studentref.orderByChild("_Uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               String email=dataSnapshot.child("_Email").getValue(String.class);
                               String name=dataSnapshot.child("_Name").getValue(String.class);
                               String studentId=dataSnapshot.child("_StudentId").getValue(String.class);

                               nameView.setText(name);
                               studentView.setText(studentId);

                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });

                   }
               }
           }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } */
        else if (id == R.id.nav_share) {
            Intent i=new Intent(Home.this,SocietyDetail.class);
            startActivity(i);
        }
        //else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getData(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            FirebaseUser user=mAuth.getCurrentUser();
            DatabaseReference ref=database.getReference().child("students");
            ref.orderByChild("_Uid").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                    String email=dataSnapshot.child("_Email").getValue(String.class);
                    String name=dataSnapshot.child("_Name").getValue(String.class);
                    String studentId=dataSnapshot.child("_StudentId").getValue(String.class);

                    nameView.setText(name);
                    studentView.setText(studentId);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
