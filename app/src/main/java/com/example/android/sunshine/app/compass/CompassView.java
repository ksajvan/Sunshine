package com.example.android.sunshine.app.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ajvan on 24/03/2016.
 */
public class CompassView extends View {
    private Paint mPaintView = new Paint();
    private double angle;
    private char[] windDirectionMark;

    public CompassView(Context context) {
        super(context);
        init(null, 0);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        windDirectionMark = new char[2];
        mPaintView.setColor(Color.DKGRAY);
    }

    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(wMeasureSpec),
                MeasureSpec.getSize(hMeasureSpec));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int r;

        if (w > h) {
            r = h / 4;
        } else {
            r = w / 4;
        }

        mPaintView.setAntiAlias(true);
        mPaintView.setStyle(Paint.Style.STROKE);
        mPaintView.setStrokeWidth(5);
        mPaintView.setColor(Color.GRAY);

        canvas.drawCircle(w / 2, h / 2, r, mPaintView);

        mPaintView.setColor(Color.rgb(62, 176, 220));
        canvas.drawLine(
                w / 2,
                h / 2,
                (float) (w / 2 + r * Math.sin(angle)),
                (float) (h / 2 - r * Math.cos(angle)),
                mPaintView);

        mPaintView.setColor(Color.rgb(138, 165, 227));
        mPaintView.setStyle(Paint.Style.FILL);
        canvas.drawCircle(w / 2, h / 2, r / 4, mPaintView);

        mPaintView.setTextSize(40);
        mPaintView.setStyle(Paint.Style.FILL);

        setSideColor("N");
        canvas.drawText("N", w / 2 - 10, h / 2 - r - 10, mPaintView);
        setSideColor("S");
        canvas.drawText("S", w / 2 - 10, h / 2 + r + 40, mPaintView);
        setSideColor("W");
        canvas.drawText("W", w / 2 - r - 40, h / 2 + 10, mPaintView);
        setSideColor("E");
        canvas.drawText("E", w/2 + r + 10, h/2 + 10, mPaintView);
    }

    public void setSideColor(String side) {
        if (windDirectionMark.length == 0) {
            mPaintView.setColor(Color.DKGRAY);
        } else if ((new String(windDirectionMark).contains(side))) {
            mPaintView.setColor(Color.RED);
        } else {
            mPaintView.setColor(Color.DKGRAY);
        }
    }

    public void update(float direction, String windDirectionShort){
        angle = direction * Math.PI / 180;
        windDirectionMark = windDirectionShort.toCharArray();

        // redraw page.
        invalidate();
    }
}
