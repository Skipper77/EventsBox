
package com.example.dellpc.eventsbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Downloader extends AsyncTask<Void,Void,String> {
    Context ctx;
    String urlAddress;
    RecyclerView rv;
    ProgressDialog pd;
    SwipeRefreshLayout sw;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String value;
    private ArrayList<Event> eventList=new ArrayList<>();
    public Downloader(Context ctx,RecyclerView rv) {//, SwipeRefreshLayout sw){
        this.ctx=ctx;
        //this.urlAddress=urlAddress;
        this.rv=rv;
        //this.sw=sw;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(ctx);
        pd.setTitle("Retrieve");
        pd.setMessage("Retrieving Please Wait ...");
        pd.show();

    }

    @Override
    protected String doInBackground(Void... voids) {

        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Events");
        // Read from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 //value = dataSnapshot.getValue().toString();
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
  for(DataSnapshot child:children){
      value=child.getValue(String.class);
  }
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return value;// this.downloadData();

    }

    @Override
    protected void onPostExecute(String url) {
        super.onPostExecute(url);
        pd.dismiss();
        /*if(sw.isRefreshing())
            sw.setRefreshing(false);*/
        if(url==null)
            Toast.makeText(ctx,"Unable to Retrieve Data",Toast.LENGTH_SHORT).show();
        else{
            //new DataParser(ctx,url,rv).execute();
            Event event=new Event();
            event.setImageUrl(url);
            eventList.add(event);
            HomeEventsAdapter customAdapter=new HomeEventsAdapter(ctx,eventList);
            rv.setAdapter(customAdapter);

        }

    }


}

