package com.example.wj.spannablestringdemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.wj.spannablestringdemo.OtherActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WJ
 * @项目名称： SpannableStringDemo
 * @创建时间： 2016年12月02日
 * @描述：  SpannableString通用方法
 */

public class SpannableStringUtil {

    /**
     * 将微博正文中的 @ 和 # ，url标识出
     *
     * @param text
     * @return
     */
    public static SpannableString getWeiBoText(Context context, String text) {
        Resources res = context.getResources();
        //四种正则表达式
        Pattern AT_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5\\w\\-]+");
        Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");
        Pattern Url_PATTERN = Pattern.compile("((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&)?)+)?");
        Pattern EMOJI_PATTER = Pattern.compile("\\[([\u4e00-\u9fa5\\w])+\\]");

        SpannableString spannable = new SpannableString(text);

        Matcher tag = TAG_PATTERN.matcher(spannable);
        while (tag.find()) {
            String tagNameMatch = tag.group();
            int start = tag.start();
//            spannable.setSpan(new MyTagSpan(context, tagNameMatch), start, start + tagNameMatch.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        Matcher at = AT_PATTERN.matcher(spannable);
        while (at.find()) {
            String atUserName = at.group();
            int start = at.start();
//            spannable.setSpan(new MyAtSpan(context, atUserName), start, start + atUserName.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        Matcher url = Url_PATTERN.matcher(spannable);
        while (url.find()) {
            String urlString = url.group();
            int start = url.start();
            spannable.setSpan(new MyURLSpan(context, urlString), start, start + urlString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
//表情
//        Matcher emoji = EMOJI_PATTER.matcher(spannable);
//        while (emoji.find()) {
//            String key = emoji.group(); // 获取匹配到的具体字符
//            int start = emoji.start(); // 匹配字符串的开始位置
//            Integer imgRes = Emotion.getImgByName(key);
//            System.out.println("@@@"+imgRes);
//            if (imgRes != null) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeResource(res, imgRes, options);
//
//                int scale = (int) (options.outWidth / 32);
//                options.inJustDecodeBounds = false;
//                options.inSampleSize = scale;
//                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, options);
//
//                ImageSpan span = new ImageSpan(context, bitmap);
//                spannable.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }

        return spannable;
    }

    /**
     * 用于weibo text中的连接跳转
     */
    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context context;

        MyURLSpan(Context ctx, String url) {
            context = ctx;
            mUrl = url;
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#f44336"));
        }

        @Override
        public void onClick(View widget) {
           // 页面跳转
            Intent intent = OtherActivity.newIntent(context, mUrl);
            context.startActivity(intent);

        }
    }
}
