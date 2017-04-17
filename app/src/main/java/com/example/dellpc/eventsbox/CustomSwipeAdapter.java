package com.example.dellpc.eventsbox;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by DELL PC on 29-Mar-17.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private int[] image_resources={R.drawable.akgec1,R.drawable.akgec2,R.drawable.akgec3};
    private LayoutInflater layout_inflater;
    public CustomSwipeAdapter(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layout_inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layout_inflater.inflate(R.layout.swipe_layout,container,false);

        ImageView imageView=(ImageView) item_view.findViewById(R.id.image_view);
        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
