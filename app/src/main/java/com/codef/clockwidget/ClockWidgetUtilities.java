package com.codef.clockwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockWidgetUtilities {

    public static Bitmap recreateClock(Context context) {


        SimpleDateFormat dateFormatTime = new SimpleDateFormat("h:mm", Locale.US);
        SimpleDateFormat dateFormatAmPm = new SimpleDateFormat("a", Locale.US);
        SimpleDateFormat dateFormatYearMonthDay = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);

        String currentTime = dateFormatTime.format(new Date());
        String currentAmPm = dateFormatAmPm.format(new Date());
        String currentDate = dateFormatYearMonthDay.format(new Date());

        int masterOffset = 160;
        int timeOffset = 0;
        int dateOffset = 140;

        int bitmapWidth = 2280;
        int bitmapHeight = 1080;

        float timeSize = 570;
        float dateSize = 105;

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
        int centerXComponent = bitmap.getWidth() / 2 - 8;
        int centerYComponent = bitmap.getHeight() / 2;

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Compctab.ttf");

        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(typeface);

        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);

        if (currentAmPm.equalsIgnoreCase("PM")) {
            paint.setTextSize(timeSize);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(22);
            canvas.drawText(currentTime, centerXComponent, centerYComponent + masterOffset + timeOffset, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(0);
            canvas.drawText(currentTime, centerXComponent, centerYComponent + masterOffset + timeOffset, paint);
        } else {
            paint.setTextSize(timeSize);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(22);
            canvas.drawText(currentTime, centerXComponent, centerYComponent + masterOffset + timeOffset, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(0);
            canvas.drawText(currentTime, centerXComponent, centerYComponent + masterOffset + timeOffset, paint);

        }

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);
        paint.setTextSize(dateSize);
        canvas.drawText(currentDate, bitmap.getWidth() / 2, bitmap.getHeight() / 2 + masterOffset + timeOffset + dateOffset,
                paint);

        //		oCanvas.drawLine(0, 0, nWidth, 0, oPaint);
        //		oCanvas.drawLine(0, 0, 0, nHeight, oPaint);
        //		oCanvas.drawLine(nWidth, 0, nWidth, nHeight, oPaint);
        //		oCanvas.drawLine(0, nHeight, nWidth, nHeight, oPaint);

        return bitmap;
    }

}
