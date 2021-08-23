package com.muabe.propose.remind;

import java.util.ArrayList;

public class Color {

    enum Type{
        PRIMARY,
        MIXED
    }

    enum Mix {
        WITH,
        OR,
        NEXT,
        NONE
    }

    enum Option{
        NONE,
        REPEAT,
        SLIDE,
        GRAVITY
    }
//    private Paper paper;
    private String name;
    private Mix mix;
    private long ratio = 0;

    ArrayList<Color> mixedColor = new ArrayList<>();

    public Color(){
        this.mix = Mix.NONE;
    }

    Color(Mix mix){
        this.mix = mix;
    }


//    public void setPaper(Paper paper){
//        this.paper = paper;
//    }

    public Object getValue(){
        return null;
    }
}
