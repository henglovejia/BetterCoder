package club.bettercoder.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import club.bettercoder.R;

import static android.graphics.PorterDuff.Mode.SRC_IN;

public class CircleImageView extends AppCompatImageView {

    private Paint mPaint;
    private Paint mTargetPaint;
    private Bitmap mSourceBitmap;
    private Bitmap mTargetBitmap;
    private Canvas mTargetCanvas;

    private int mWidth;
    private int mHeight;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTargetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTargetPaint.setXfermode(new PorterDuffXfermode(SRC_IN));
        mSourceBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.emoji);
        mTargetBitmap = Bitmap.createBitmap(mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mTargetCanvas = new Canvas(mTargetBitmap);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 生成圆形Bitmap过程.
        int radius = Math.min(mWidth, mHeight) / 2;
        // 先绘制圆形
        mTargetCanvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);
        // 再绘制Bitmap
        mTargetCanvas.drawBitmap(mSourceBitmap, 0, 0, mTargetPaint);
        canvas.drawBitmap(mTargetBitmap, 0, 0, null);
    }
}