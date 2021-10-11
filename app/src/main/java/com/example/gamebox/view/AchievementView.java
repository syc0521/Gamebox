package com.example.gamebox.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.gamebox.bean.Achievement;

public class AchievementView extends View
{
    private Paint backPaint, fillPaint;
    private Achievement achievement;

    public AchievementView(Context context)
    {
        super(context);
    }

    public AchievementView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        init();
        int length = 565;
        int width = 15;
        canvas.drawRoundRect(new RectF(0,10,length,10 + width), 12,12, backPaint);
        canvas.drawRoundRect(new RectF(0,10,length * (achievement.getCurrent() * 1.0f / achievement.getTotal()),10 + width), 12,12, fillPaint);

    }

    private void init()
    {
        backPaint = new Paint();
        backPaint.setColor(Color.argb(233, 233, 233, 233));
        backPaint.setAntiAlias(true);
        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        float hueRaw = 120 - 160 * (achievement.getCurrent() * 1.0f / achievement.getTotal());
        if (hueRaw <= 0.0f)
            hueRaw += 360.0f;
        fillPaint.setColor(Color.HSVToColor(new float[]{hueRaw, 0.82f, 0.99f}));
    }

    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }
}
