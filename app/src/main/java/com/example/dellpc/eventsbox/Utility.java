package com.example.dellpc.eventsbox;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

/**
 * Created by DELL PC on 22-Feb-17.
 */

public class Utility {

    public static String random_number(){
        String[] digits= "0123456789ABCDEF".split("");
        String randomnum="";
        for(int i=1;i<=4;i++){
          randomnum+=digits[(int)Math.floor((Math.random())*16)];
        }
       return randomnum;
    }
    public static String getDataReady(LinkedHashMap m)throws UnsupportedEncodingException{
        String data=null;
        String encryptedPassword=null;
        for(char ch:m.get(6).toString().toCharArray()){
            encryptedPassword+=((byte)ch)^18;
        }


            data = URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(0).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("universityrollno", "UTF-8") + "="
                    + URLEncoder.encode(m.get(1).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("studentid", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(2).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(3).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("branch", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(4).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("year", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(5).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(encryptedPassword, "UTF-8");
            data += "&" + URLEncoder.encode("phone", "UTF-8")
                    + "=" + URLEncoder.encode(m.get(8).toString(), "UTF-8");
        return data;

    }
}
