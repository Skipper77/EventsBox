package com.example.dellpc.eventsbox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL PC on 08-Feb-17.
 */

public class Validator {


    public static boolean validEmailPatternTest(String emailPattern){
        String regex;
        regex = "\\w+(\\.\\w+)?@\\w+\\.\\w{3}";
        boolean value=emailPattern.matches(regex);
        if(emailPattern==null||value||emailPattern.isEmpty()){
            return true;
        }
        return false;
    }


    public static int notEmptyTest(String email,String password){
        if(email.isEmpty()&&!password.isEmpty())
        {
            return 1;
        }
        if(!email.isEmpty()&&password.isEmpty()){
            return 2;
        }
        if(email.isEmpty()&&password.isEmpty()){
            return 3;
        }
        return 0;
    }
    public static LinkedHashMap<Integer,String> emptyTestForCollection(LinkedHashMap<Integer,String> elements){
        LinkedHashMap<Integer,String> elem=new LinkedHashMap<>();
        if(!elem.isEmpty()){elem.clear();}
        elem.putAll(elements);
        for(Map.Entry m:elem.entrySet()){
             String value= (String) m.getValue();

            if(value==null||value.isEmpty()){
                m.setValue("EMPTY");

            }
            else{
                m.setValue("NOTEMPTY");
            }
         }
        return elem;
    }
    public static boolean emptyTestForSingleElement(String element){
        if(element.isEmpty()||element==null){
            return true;
        }
        return false;
    }

    public static boolean lengthCheck(String element,int length){
        if(element==null||element.length()==length||element.isEmpty()){
            System.out.println("Inside length==length");
            System.out.println(element.length());
            return true;
        }

        return false;
    }
    public static boolean phoneLengthTest(String element){
        String regex;
        regex = "[987][0-9]{9}";
        boolean value=element.matches(regex);
        if(element==null||value||element.isEmpty())
        {
            return true;
        }
        System.out.println(element.length());
        System.out.println(element.isEmpty());
        System.out.println(element);
        return false;

    }

    public static boolean matchPasswordTest(String pass1,String pass2){
        if(pass1==null||pass2==null||pass1.equals(pass2)||pass1.isEmpty()||pass2.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean passwordPatternTest(final String password){
        System.out.println("Password value ="+password);
        Pattern pattern;
        Matcher matcher;
        String regex="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#%&]).{8,})";
        pattern=Pattern.compile(regex);
        matcher=pattern.matcher(password);

        boolean value=matcher.matches();
        System.out.println("Password value ="+value);
        if(password==null||value||password.isEmpty()){
            return true;
        }
        return false;

    }
}
