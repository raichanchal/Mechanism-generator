package com.example.mechrator;

import android.content.Context;

public class Groups{
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private Context context;

    public Groups(Context context) {
        this.context = context;
    }

    public Groups(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

/*public Groups(int number1, int number2, int number3, int number4) {
this.number1 = number1;
this.number2 = number2;
this.number3 = number3;
this.number4 = number4;
}*/


    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }


    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getNumber3() {
        return number3;
    }

    public int getNumber4() {
        return number4;
    }

}