package com.example.android_final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class HangMan extends View {

    Paint figurePaint;
    int wrongGuess;
    int maxGuesses = 6;

    public HangMan(Context context) {
        super(context);
    }

    public HangMan(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HangMan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HangMan(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean failed(){

        if(this.wrongGuess < 6){
            return false;
        } else{

            return true;
        }
    }

    public void resetGuess(){

        this.wrongGuess = 0;
        invalidate();
    }
    public void addWrongGuees(){

        this.wrongGuess+=1;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas){



        figurePaint = new Paint();
        figurePaint.setColor(Color.BLACK);
        figurePaint.setStrokeWidth(20);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(20);

        float scale = 1.10f;
        canvas.scale(scale,scale);

        // Draw scaffold
        canvas.drawLine(100, 500, 400, 500, paint); // Draw horizontal scaffold beam
        canvas.drawLine(250, 500, 250, 100, paint); // Draw vertical scaffold beam
        canvas.drawLine(250, 100, 350, 100, paint); // Draw scaffold support beam
        canvas.drawLine(350, 100, 350, 150, paint); // Draw scaffold rope

        // Draw head
        if(this.wrongGuess >= 1) canvas.drawCircle(350, 115, 45, paint); // Draw head with a radius of 50

        // Draw body
        if(this.wrongGuess >= 2) canvas.drawLine(350, 100, 350, 250, paint); // Draw body from head to hip

        // Draw right arm
        if(this.wrongGuess >= 3) canvas.drawLine(350, 200, 400, 150, paint); // Draw right arm

        // Draw left arm
        if(this.wrongGuess >= 4) canvas.drawLine(350, 200, 300, 150, paint); // Draw left arm

        // Draw right leg
        if(this.wrongGuess >= 5) canvas.drawLine(350, 250, 400, 350, paint); // Draw right leg

        // Draw left leg
        if(this.wrongGuess >= 6) {
            canvas.drawLine(350, 250, 300, 350, paint); // Draw left leg

        }




        /*
        // Draw scaffold
        canvas.drawLine(100, 600, 400, 600, paint);
        canvas.drawLine(250, 600, 250, 200, paint);
        canvas.drawLine(250, 200, 350, 200, paint);
        canvas.drawLine(350, 200, 350, 250, paint);

        // Draw head
        canvas.drawCircle(350, 225, 50, paint);

        // Draw body
        canvas.drawLine(350, 250, 350, 400, paint);

        // Draw right arm
        canvas.drawLine(350, 350, 400, 300, paint);

        // Draw left arm
        canvas.drawLine(350, 350, 300, 300, paint);

        // Draw right leg
        canvas.drawLine(350, 400, 400, 500, paint);

        // Draw left leg
        canvas.drawLine(350, 400, 300, 500, paint); */




    }


}
