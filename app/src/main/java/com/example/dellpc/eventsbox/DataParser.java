
package com.example.dellpc.eventsbox;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void,Void,Boolean> {
    Context ctx;
    String result;
    RecyclerView rv;
    ProgressDialog pd;
    ArrayList<Event> al=new ArrayList<Event>();
    public DataParser(Context ctx,String result,RecyclerView rv){
        this.ctx=ctx;
        this.result=result;
        this.rv=rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(ctx);
        pd.setTitle("Parser");
        pd.setMessage("parsing Please wait ....");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.getData();
    }

    @Override
    protected void onPostExecute(Boolean b) {
        pd.dismiss();
        if(b){
            HomeEventsAdapter customAdapter=new HomeEventsAdapter(ctx,al);
            rv.setAdapter(customAdapter);
        }
        else{
            Toast.makeText(ctx,"Unable to parse",Toast.LENGTH_SHORT).show();
        }
    }
    private Boolean getData()
    {
        boolean value=false;

        /*try {
            System.out.println(result);
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("mydata");
            JSONObject jsonObject1;
            if(jsonArray.length()>0){
                value=true;

            //al.clear();
            for(int i=0;i<jsonArray.length();i++) {
                jsonObject1 = jsonArray.getJSONObject(i);
                String title = jsonObject1.getString("title");
                String urlAddress = jsonObject1.getString("imageurl");
                String desc = jsonObject1.getString("description");
                String price = jsonObject1.getString("price");
                String id = jsonObject1.getString("product_id");
                String category = jsonObject1.getString("category");
                String email = jsonObject1.getString("email");
                String status = jsonObject1.getString("status");

                Product product = new Product();
                product.setProductTitle(title);
                product.setImageUrl(urlAddress);
                product.setProductPrice(price);
                product.setProductCategory(category);
                product.setUserEmail(email);
                product.setProductPrice(price);
                product.setProductId(Integer.parseInt(id));
                product.setProductStatus(status);
                product.setProductDesc(desc);
                al.add(product);*/
    //        }
      //      }

        /*} catch (JSONException e) {
            e.printStackTrace();
            return false;
        }*/
        return value;
    }
}



