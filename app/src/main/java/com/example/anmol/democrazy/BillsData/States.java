package com.example.anmol.democrazy.BillsData;


import java.io.Serializable;

public class States implements Serializable {

    int id;
    String name;
    boolean isChecked;

    //empty constructor
    public States(){

    }

    public States(int id, String name){
        this.id=id;
        this.name=name;
    }

    public States(int id,String name,boolean isChecked){
        this.id=id;
        this.name=name;
        this.isChecked=isChecked;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
