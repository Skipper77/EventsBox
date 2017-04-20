package com.example.dellpc.eventsbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DELL PC on 11-Apr-17.
 */

public class HomeEventsAdapter extends RecyclerView.Adapter<HomeEventsAdapter.MyViewHolder> {
    private ArrayList<Event> eventList;
    private Context ctx;
  //  private Event d;

    public HomeEventsAdapter(Context ctx, ArrayList<Event> eventList){
      this.ctx=ctx;
        this.eventList=eventList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       Event  d=eventList.get(position);
        //Log.d("Product position",d.getImageUrl());
        holder.home_row_list_title.setText(d.getTitle());
        holder.home_row_list_society.setText(d.getSocietyBelongTo());
        // holder.iv.setImageBitmap(d.getBitmapImage());
        //holder.bind(d,itemClickListener);
        /*holder.tv1.setText(d.getTitle());
        holder.tv2.setText("₹"+d.getPrice());*/
        PiccasoClient.downloadImage(ctx,d.getImageUrl(),holder.eventImage);

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView eventImage;
        TextView home_row_list_title;
        TextView home_row_list_society;
        TextView home_row_list_date;
        public MyViewHolder(View itemView) {
            super(itemView);
            eventImage=(ImageView)itemView.findViewById(R.id.eventImage);
            home_row_list_title=(TextView)itemView.findViewById(R.id.home_row_list_title);
            home_row_list_society=(TextView)itemView.findViewById(R.id.home_row_list_society);
            home_row_list_date=(TextView)itemView.findViewById(R.id.home_row_list_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(ctx,EventDetails.class);
                   // String url=d.getImageUrl();
                    Event event=eventList.get(getLayoutPosition());
                    intent.putExtra("ievent",event);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}
