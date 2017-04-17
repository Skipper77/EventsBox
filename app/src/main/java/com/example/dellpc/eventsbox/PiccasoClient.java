package com.example.dellpc.eventsbox;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by DELL PC on 11-Apr-17.
 */

public class PiccasoClient {
    public static void downloadImage(Context ctx, String url, ImageView iv){
        Picasso.with(ctx).load(url).into(iv);
    }
}
