package com.example.npuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class PuzzleCell extends View {
    int idX = 0; //default
    int idY = 0; //default
    String value;
    int colCount, rowCount;

    public PuzzleCell(Context context, int x, int y, String value, int rowCount,int colCount) {
        super(context);
        idX = x;
        idY = y;
        this.value = value;
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    public PuzzleCell(Context context) {
        super(context);
    }

    public PuzzleCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PuzzleCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        DrewRect(canvas,paint);

        DrawNums(canvas,paint);
    }

    private void DrewRect(Canvas canvas,Paint paint){
        paint.setColor(Color.WHITE);
        paint.setAlpha(180);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(
                0,0,canvas.getWidth(),canvas.getHeight()
        );
        canvas.drawRoundRect(rectF,15,15,paint);
    }

    private void DrawNums(Canvas canvas, Paint paint){
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        if(colCount < 4 && rowCount< 4){
            paint.setTextSize(100);
            canvas.drawText(value,this.getWidth()/2-20,
                    this.getHeight()/2+20,paint);
        }else if(colCount < 7 && rowCount< 7){
            paint.setTextSize(70);
            if(value.length() == 1){
                canvas.drawText(value,this.getWidth()/2-20,
                        this.getHeight()/2+20,paint);
            }else {
                canvas.drawText(value, this.getWidth() / 2 - 40,
                        this.getHeight() / 2 + 20, paint);
            }
        }else {
            paint.setTextSize(50);
            if(value.length() == 1){
                canvas.drawText(value,this.getWidth()/2-15,
                        this.getHeight()/2+20,paint);
            }else {
                canvas.drawText(value, this.getWidth() / 2 - 30,
                        this.getHeight() / 2 + 20, paint);
            }
        }
    }

    public void SetValue(String value){
        this.value = value;
        this.invalidate();
    }
}


