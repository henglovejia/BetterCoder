package club.bettercoder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 雷达图控件
 */

public class RadarView extends View {
    private static final String TAG = "RadarView";
    //数据个数
    private int count;
    //最小个数
    private int minCount = 6;
    //网格最大半径
    private float radius;
    //中心X
    private int centerX;
    //中心Y
    private int centerY;
    //雷达区画笔
    private Paint mainPaint;
    //文本画笔
    private Paint textPaint;
    //数据区画笔
    private Paint valuePaint;
    //标题文字
    private List<String> titles = new ArrayList<>();
    //各维度分值
    private List<Double> data = new ArrayList<>();
    //数据最大值
    private double maxValue = 0;
    private float angle;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setColor(Color.BLACK);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1);
        mainPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(15);
        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);

        valuePaint = new Paint();
        valuePaint.setColor(Color.RED);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < minCount; i++) {
            titles.add("");
            data.add(0.0);
        }
    }

    public void initData(List<String> titles, List<Double> data) {
        this.titles.clear();
        this.data.clear();
        maxValue = 0;
        for (int i = 0; i < titles.size() || i < minCount; i++) {
            if (i < titles.size()) {
                this.titles.add(titles.get(i));
                this.data.add(data.get(i));
                maxValue = maxValue > data.get(i) ? maxValue : data.get(i);
            } else {
                this.titles.add("");
                this.data.add(0.0);
            }
        }
        maxValue++;
        count = this.titles.size();
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        radius = Math.min(w, h) / 2 * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldW, oldH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPolygon(canvas);
        drawLines(canvas);
        drawTitle(canvas);
        drawRegion(canvas);
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数
        //中心与相邻两个内角相连的夹角角度
        angle = (float) (2 * Math.PI / count);
        //每个蛛丝之间的间距
        float r = radius / (count - 1);
        for (int i = 0; i < count; i++) {
            //当前半径
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    //对于直角三角形sin(x)是对边比斜边，cos(x)是底边比斜边，tan(x)是对边比底边
                    //因此可以推导出:底边(x坐标)=斜边(半径)*cos(夹角角度)
                    //               对边(y坐标)=斜边(半径)*sin(夹角角度)
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i + Math.PI));
            float y = (float) (centerY + radius * Math.sin(angle * i + Math.PI));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制标题文字
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        //相关知识点:http://mikewang.blog.51cto.com/3826268/871765/
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        //绘制文字时不让文字和雷达图形交叉,加大绘制半径
        float textRadius = radius + fontHeight;
        double pi = Math.PI;
        for (int i = 0; i < count; i++) {
            Log.d(TAG,titles.get(i));
            float x = (float) (centerX + textRadius * Math.cos(angle * i + Math.PI));
            float y = (float) (centerY + textRadius * Math.sin(angle * i + Math.PI));
            //当前绘制标题所在顶点角度
            double degrees = angle * i;
            //从右下角开始顺时针画起,与真实坐标系相反
            if (degrees >= 0 && degrees < pi / 2) {//第四象限
                float dis = textPaint.measureText(titles.get(i)) / (titles.get(i).length() - 1);
                canvas.drawText(titles.get(i), x + dis, y, textPaint);
            } else if (degrees >= pi / 2 && degrees < pi) {//第三象限
                float dis = textPaint.measureText(titles.get(i)) / (titles.get(i).length() - 1);
                canvas.drawText(titles.get(i), x - dis, y, textPaint);
            } else if (degrees >= pi && degrees < 3 * pi / 2) {//第二象限
                float dis = textPaint.measureText(titles.get(i)) / (titles.get(i).length());
                canvas.drawText(titles.get(i), x - dis, y, textPaint);
            } else if (degrees >= 3 * pi / 2 && degrees <= 2 * pi) {//第一象限
                canvas.drawText(titles.get(i), x, y, textPaint);
            }
        }
    }

    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setAlpha(255);
        Path path = new Path();
        count = titles.size();
        for (int i = 0; i < count; i++) {
            //计算该数值与最大值比例
            Double perCenter = data.get(i) / maxValue;
            //小圆点所在位置距离圆心的距离
            double perRadius = perCenter * radius;
            float x = (float) (centerX + perRadius * Math.cos(angle * i + Math.PI));
            float y = (float) (centerY + perRadius * Math.sin(angle * i + Math.PI));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, 5, valuePaint);
        }
        //闭合覆盖区域
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        //绘制覆盖区域外的连线
        canvas.drawPath(path, valuePaint);
        //填充覆盖区域
        valuePaint.setAlpha(128);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, valuePaint);
    }

    //设置数值种类
    public void setCount(int count) {
        this.count = count;
        postInvalidate();
    }

    //设置蜘蛛网颜色
    public void setMainPaint(Paint mainPaint) {
        this.mainPaint = mainPaint;
        postInvalidate();
    }

    //设置标题颜色
    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    //设置标题
    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    //设置覆盖局域颜色
    public void setValuePaint(Paint valuePaint) {
        this.valuePaint = valuePaint;
        postInvalidate();
    }

    public List<Double> getData() {
        return data;
    }

    public List<String> getTitles() {
        return titles;
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
}