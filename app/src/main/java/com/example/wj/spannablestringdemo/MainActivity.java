package com.example.wj.spannablestringdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.example.wj.spannablestringdemo.util.MyLinkMovementMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tvText;

    final static String string = "#刘翔# //@成龙: @薛蛮子:@李开复: @李开复:@李开复:@李开复:lau@qq.com " +
            "http://www.baidu.com/index.php?tn=360se_dg " +
            "转发// //@新周刊:@李承鹏:@财经网: Email:lau@qq.com又是公安牵头。@李承鹏@李承鹏@李承鹏@李承鹏:随时随刻离不开暴力。";


    private static final String STR_PATTERN_AT = "@[\\u4e00-\\u9fa5\\w\\-]+";
    private static final String STR_PATTERN_THEME = "#[^\\#|.]+#";
    private static final String STR_PATTERN_LINK = "http://[&=?/\\.\\w]+";
    private static final String STR_PATTERN_EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

    private static final Pattern mPatternAt = Pattern.compile(STR_PATTERN_AT);
    private static final Pattern mPatternTheme = Pattern.compile(STR_PATTERN_THEME);
    private static final Pattern mPatternLink = Pattern.compile(STR_PATTERN_LINK);
    private static final Pattern mPatternEmail = Pattern.compile(STR_PATTERN_EMAIL);

    private SpannableStringBuilder ssb = new SpannableStringBuilder();
    private Matcher matcher = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        tvText  = (TextView) findViewById(R.id.tvText);
        ssb.append(string);
        findAt(string, 0);
        findTheme(string, 0);
        findLink(string, 0);
        findEmail(string, 0);

        tvText.setText(ssb);
        //设置使自定义的点击事件生效。
//        tvText.setMovementMethod(LinkMovementMethod.getInstance());
        tvText.setOnTouchListener(MyLinkMovementMethod.getInstance());
    }

    /**
     * 从位置pos开始查找@关键字
     * @param str
     * @param pos
     */
    private void findAt(String str, int pos) {
        if(str == null || str.length() == 0) {
            return;
        }
        matcher = mPatternAt.matcher(str);
        if(matcher.find()) {
            final String find = matcher.group();
            final int start = str.indexOf(find) + pos;
            final int end = start + find.length();
            //设置@xxxx可点击
            MyClickableSpan clickableSpan = new MyClickableSpan(string.substring(start, end));
            ssb.setSpan(clickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            pos = end;
            findAt(string.substring(end), pos);
        }
    }

    //从位置pos开始查找##关键字
    void findTheme(String str, int pos) {
        if(str == null || str.length() == 0) {
            return;
        }
        matcher = mPatternTheme.matcher(str);
        if(matcher.find()) {
            final String find = matcher.group();
            final int start = str.indexOf(find) + pos;
            final int end = start + find.length();
            MyClickableSpan clickableSpan = new MyClickableSpan(string.substring(start, end));
            ssb.setSpan(clickableSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            pos = end;
            findTheme(string.substring(end), pos);
        }
    }

    /**
     *
     *从位置pos开始查找链接关键字
     * @param str
     * @param pos
     */
   private  void findLink(String str, int pos) {
        if(str == null || str.length() == 0) {
            return;
        }
        matcher = mPatternLink.matcher(str);
        if(matcher.find()) {
            final String find = matcher.group();
            final int start = str.indexOf(find) + pos;
            final int end = start + find.length();
            //设置超链接
            URLSpanNotUnderLine urlSpan = new URLSpanNotUnderLine(string.substring(start, end));
            ssb.setSpan(urlSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            pos = end;
            findLink(string.substring(end), pos);
        }
    }

    /**
     * 从位置pos开始查找邮箱
     * @param str
     * @param pos
     */
    private void findEmail(String str, int pos) {
        if(str == null || str.length() == 0) {
            return;
        }
        matcher = mPatternEmail.matcher(str);
        if(matcher.find()) {
            final String find = matcher.group();
            final int start = str.indexOf(find) + pos;
            final int end = start + find.length();
            CharacterStyle[] span =  ssb.getSpans(start, end, CharacterStyle.class);
            if(span != null) {
                for(int i=0; i<span.length; i++) {
                    ssb.removeSpan(span[i]);
                }
            }
            pos = end;
            findEmail(string.substring(end), pos);
        }
    }

    /**
     * 超链接
     */
    @SuppressLint("ParcelCreator")
    class URLSpanNotUnderLine extends URLSpan {
        public URLSpanNotUnderLine(String url) {
            super(url);
        }
        @Override
        public void onClick(View widget) {
            super.onClick(widget);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置下划线
            ds.setUnderlineText(false);
        }
    }


    /**
     * 点击事件
     */
    class MyClickableSpan extends ClickableSpan {

        String str = null;

        public MyClickableSpan(String str) {
            this.str = str;
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), OtherActivity.class);
            intent.putExtra("name", str);
            startActivity(intent);
        }
    }
}
