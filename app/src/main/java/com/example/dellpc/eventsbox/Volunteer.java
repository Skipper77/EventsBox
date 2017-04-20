package com.example.dellpc.eventsbox;

/**
 * Created by DELL PC on 11-Apr-17.
 */

public class Volunteer {


    private String student_name;
    private String student_email;
    private String student_phoneno;

    public Volunteer(){

    }
    public Volunteer(String name,String phone,String email){
        this.student_name=name;
        this.student_phoneno=phone;
        this.student_email=email;
    }



    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_phoneno() {
        return student_phoneno;
    }

    public void setStudent_phoneno(String student_phoneno) {
        this.student_phoneno = student_phoneno;
    }

}
