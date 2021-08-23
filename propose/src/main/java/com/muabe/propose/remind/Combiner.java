package com.muabe.propose.remind;

import java.util.Arrays;

public class Combiner {

    public Color combine(Color.Mix mix, Color... colors){
        if(colors == null){
            throw new NullPointerException("colors argments must not null");
        }else if(colors.length == 1){
            return colors[0];
        }

        Color color = new Color(mix);
        color.mixedColor.addAll(Arrays.asList(colors));

        return color;
    }
}
