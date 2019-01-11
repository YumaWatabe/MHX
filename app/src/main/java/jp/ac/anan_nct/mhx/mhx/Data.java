package jp.ac.anan_nct.mhx.mhx;

import android.graphics.Bitmap;

public class Data {
    public int id;
    public String name;
    public Bitmap image;
    public int tag;
    public int deadline;
    public int isComp;
    public String notif;
    public String memo;

    private boolean[] emp;

    public Data(){
        this.id = -1;
        this.name = null;
        this.image = null;
        this.tag = -1;
        this.deadline = -1;
        this.isComp = -1;
        this.notif = null;
        this.memo = null;

        emp = new boolean[8];
    }

    public Data(String name, Bitmap image, int tag, int deadline, int isComp, String notif, String memo){
        this.id = -1;
        this.name = name;
        this.image = image;
        this.tag = tag;
        this.deadline = deadline;
        this.isComp = isComp;
        this.notif = notif;
        this.memo = memo;

        emp = new boolean[]{false, this.name != null, this.image != null, this.tag != -1, this.deadline != -1, this.isComp != -1, this.notif != null, this.memo != null};
    }

    public void setEmp(int index, boolean val){
        emp[index] = val;
    }

    public boolean isData(int column){
        return emp[column];
    }
}
