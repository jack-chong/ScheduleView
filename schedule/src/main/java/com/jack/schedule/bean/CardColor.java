package com.jack.schedule.bean;

import android.graphics.Color;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/24
 * desc   :
 */
public class CardColor {

    public int normalColor;

    public int bottomColor;


    public CardColor(String normalColor, String bottomColor) {
        this.normalColor = Color.parseColor(normalColor);
        this.bottomColor = Color.parseColor(bottomColor);
    }

    public CardColor(int normalColor, int bottomColor) {
        this.normalColor = normalColor;
        this.bottomColor = bottomColor;
    }
}
