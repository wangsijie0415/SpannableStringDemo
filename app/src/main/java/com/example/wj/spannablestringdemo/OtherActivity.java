package com.example.wj.spannablestringdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author WJ
 * @项目名称： SpannableStringDemo
 * @创建时间： 2016年12月02日
 * @描述：
 */

public class OtherActivity extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);
        initView();
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, OtherActivity.class);
        intent.putExtra("Url", url);
        return intent;
    }

    private void initView() {
        String str1 = "我是前景色";
        setForeground(str1, Color.BLUE, 2, str1.length());

        String str2 = "我是背景色";
        setBackground(str2, Color.GRAY, 2, str2.length());

        String str3 = "我是可点击的哦";
        tv3.setMovementMethod(LinkMovementMethod.getInstance());
        setClickable(str3, 2, str3.length());

        String str4 = "为文字设置删除线";
        setDeleteLine(str4, 2, str4.length());

        String str5 = "BlurMaskFilter 和 EmbossMaskFilter";
        setMaskFilterSpan(str5);

        String str6 = "我是相对大小的字";
        setRelativeSizeSpan(str6);

        String str7 = "我是下划线";
        setUnderLineSpan(str7, 2, str7.length());

        setDynamicDrawableSpan(3, 4);

        setImageSpan(2, 4);

        setUrlSpan(2, 5);

        setSubscriptSpan(4, 6);

        setSuperscriptSpan(4,6);

    }

    /**
     * 设置前景色
     *
     * @param str
     * @param color
     */
    private void setForeground(String str, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv1.setText(spannableString);
    }

    /**
     * 设置背景色
     *
     * @param str
     * @param color
     * @param start
     * @param end
     */
    private void setBackground(String str, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv2.setText(spannableString);
    }

    /**
     * 设置文本可点击，有点击事件
     *
     * @param str
     * @param start
     * @param end
     */
    private void setClickable(String str, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        String sub = str.substring(start, end);
        spannableString.setSpan(new MyClickableSpan(sub), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv3.setText(spannableString);
    }

    class MyClickableSpan extends ClickableSpan {
        String str = null;

        public MyClickableSpan(String str) {
            this.str = str;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.YELLOW);
        }

        @Override
        public void onClick(View view) {
            //TODO 自定义点击事件
            Toast.makeText(OtherActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 为文字设置删除线
     *
     * @param str
     * @param start
     * @param end
     */
    private void setDeleteLine(String str, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv4.setText(spannableString);
    }

    /**
     * 设置模糊与浮雕
     *
     * @param str
     */
    private void setMaskFilterSpan(String str) {
        SpannableString spannableString = new SpannableString(str);
        int length = spannableString.length();
        //模糊(BlurMaskFilter)
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER));
        spannableString.setSpan(maskFilterSpan, 0, length - 14, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //浮雕(EmbossMaskFilter)
        //direction 是float数组，定义长度为3的数组标量[x,y,z]，来指定光源的方向
        //ambient 取值在0到1之间，定义背景光 或者说是周围光
        // specular 定义镜面反射系数。
        // blurRadius 模糊半径。
        maskFilterSpan = new MaskFilterSpan(new EmbossMaskFilter(new float[]{1, 1, 3}, 1.5f, 8, 3));

        spannableString.setSpan(maskFilterSpan, length - 16, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        tv5.setText(spannableString);

    }

    /**
     * 设置文字相对大小
     *
     * @param str
     */
    private void setRelativeSizeSpan(String str) {
        SpannableString spannableString = new SpannableString(str);

        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan03 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan04 = new RelativeSizeSpan(1.8f);
        RelativeSizeSpan sizeSpan05 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan06 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan07 = new RelativeSizeSpan(1.2f);

        spannableString.setSpan(sizeSpan01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan02, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan03, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan04, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan05, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan06, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan07, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv6.setText(spannableString);
    }

    /**
     * 设置下划线
     *
     * @param str
     * @param start
     * @param end
     */
    private void setUnderLineSpan(String str, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv7.setText(spannableString);
    }


    /**
     * 设置图片，基于文本基线或底部对齐
     *
     * @param start
     * @param end
     */
    private void setDynamicDrawableSpan(int start, int end) {
        SpannableString spannableString = new SpannableString("这是测试字符串，这是测试字符串");
//        DynamicDrawableSpan.ALIGN_BOTTOM和DynamicDrawableSpan.ALIGN_BASELINE
        DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BASELINE) {
            @Override
            public Drawable getDrawable() {
                Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
                d.setBounds(0, 0, 50, 50);
                return d;
            }
        };
        spannableString.setSpan(drawableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv8.setText(spannableString);
    }

    /**
     * 图片
     *
     * @param start
     * @param end
     */
    private void setImageSpan(int start, int end) {
        SpannableString spannableString = new SpannableString("这是图片哦");
        Drawable drawable = getResources().getDrawable(R.mipmap.smail);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv9.setText(spannableString);
    }

    /**
     * 设置超链接
     *
     * @param start
     * @param end
     */
    private void setUrlSpan(int start, int end) {
        SpannableString spannableString = new SpannableString("这是超链接哦");
        spannableString.setSpan(new MyUrlSpan("http://www.baidu.com"), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv10.setMovementMethod(LinkMovementMethod.getInstance());
        tv10.setText(spannableString);
    }

    class MyUrlSpan extends URLSpan {

        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            super.onClick(widget);
            // TODO 可自定义点击事件
        }
    }


    /**
     * 设置下标
     *
     * @param start
     * @param end
     */
    private void setSubscriptSpan(int start, int end) {
        SpannableString spannableString = new SpannableString("这是我的下标");
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spannableString.setSpan(subscriptSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv11.setText(spannableString);
    }


    /**
     * 设置上标
     *
     * @param start
     * @param end
     */
    private void setSuperscriptSpan(int start, int end) {
        SpannableString spannableString = new SpannableString("这是我的上标");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv12.setText(spannableString);
    }
}
