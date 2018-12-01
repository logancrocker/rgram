package com.rgram.rgram;
import android.content.Context;

import android.graphics.Canvas;


import android.graphics.Movie;
import android.util.AttributeSet;

import android.view.View;

import static com.rgram.rgram.R.drawable.search;

public class Mygif extends View {
    private long movieStart;

    private Movie movie;

    //此处必须重写该构造方法

    public Mygif(Context context,AttributeSet attributeSet) {

        super(context,attributeSet);

//以文件流（InputStream）读取进gif图片资源

       // movie=Movie.decodeStream(getResources().openRawResource(R.drawable.search));

    }



    @Override

    protected void onDraw(Canvas canvas) {

        long curTime=android.os.SystemClock.uptimeMillis();

//第一次播放

        if (movieStart == 0) {

            movieStart = curTime;

        }

        if (movie != null) {

            int duraction = movie.duration();

            int relTime = (int) ((curTime-movieStart)%duraction);

            movie.setTime(relTime);

            movie.draw(canvas, 0, 0);

//强制重绘

            invalidate();

        }

        super.onDraw(canvas);

    }
}
