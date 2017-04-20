package com.example.dellpc.eventsbox;

/**
 * Created by DELL PC on 12-Feb-17.
 */

public class Student {
private String _Name;
    private String _University_roll_no;
    private String _StudentId;
    private String _Branch;
    private String _Year;
    private String _Gender;
    private String _Phone;
    private String _Password;
    private String _Email;
    private String _Status;
    private String _Uid;
    //private String _KeyId;

    public Student(){

    }

    public Student(String _Name, String _University_roll_no, String _StudentId, String _Branch, String _Year, String _Gender, String _Phone, String _Password, String _Email, String _Status, String _Uid, String _KeyId) {
        this._Name = _Name;
        this._University_roll_no = _University_roll_no;
        this._StudentId = _StudentId;
        this._Branch = _Branch;
        this._Year = _Year;
        this._Gender = _Gender;
        this._Phone = _Phone;
        this._Password = _Password;
        this._Email = _Email;
        this._Status = _Status;
        this._Uid = _Uid;
       // this._KeyId = _KeyId;
    }

    public String get_Uid() {
        return _Uid;
    }

    public void set_Uid(String _Uid) {
        this._Uid = _Uid;
    }

    /*public String get_KeyId() {
        return _KeyId;
    }

    public void set_KeyId(String _KeyId) {
        this._KeyId = _KeyId;
    }*/

    public String get_Branch() {
        return _Branch;
    }

    public void set_Branch(String _Branch) {
        this._Branch = _Branch;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String _Email) {
        this._Email = _Email;
    }

    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public String get_Password() {
        return _Password;
    }

    public void set_Password(String _Password) {
        this._Password = _Password;
    }

    public String get_Phone() {
        return _Phone;
    }

    public void set_Phone(String _Phone) {
        this._Phone = _Phone;
    }

    public String get_Gender() {
        return _Gender;
    }

    public void set_Gender(String _Gender) {
        this._Gender = _Gender;
    }

    public String get_Year() {
        return _Year;
    }

    public void set_Year(String _Year) {
        this._Year = _Year;
    }

    public String get_StudentId() {
        return _StudentId;
    }

    public void set_StudentId(String _StudentId) {
        this._StudentId = _StudentId;
    }

    public String get_University_roll_no() {
        return _University_roll_no;
    }

    public void set_University_roll_no(String _University_roll_no) {
        this._University_roll_no = _University_roll_no;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }
}
