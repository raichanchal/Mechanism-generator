package com.example.mechrator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class LineView extends View {
    private Paint paint = new Paint();
    private PointF pointA, pointB;
    private ArrayList<Float> prearray = new ArrayList<Float>();
    //private float[] array = new float[];

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        //canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y,paint);
        int length = prearray.size();
        float[] array = new float[length];

        for (int i = 0; i < length; i++) {
            array[i] = prearray.get(i);
        }



        canvas.drawLines(array, paint);
        super.onDraw(canvas);
    }

    public void setPrearray(ArrayList<Float> prearray) {
        this.prearray = prearray;

    }

    public void setPointA(PointF pointA) {
        this.pointA = pointA;
    }

    public void setPointB(PointF pointB) {
        this.pointB = pointB;
    }

    public void draw() {
        invalidate();
        requestLayout();
    }
}
