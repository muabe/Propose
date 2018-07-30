package com.muabe.propose;

import com.muabe.propose.util.HashStore;
import com.muabe.propose.util.Tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 모션의 조합
 * 모션끼리 조합할수 있고 조합된 모션은 다시 하나의 모션으로 볼수있다.
 * 다시말해 모션이 조합되면 다시 모션을 리턴할것임
 * combinator는 모션 클래스 안에 포함될것이며 combinator는 모션 객체들과 조합 속성들을 가지고 있을 것이다.
 *
 *
 */
public abstract class Combinator {
    protected final int AND = 0;
    protected final int OR = 1;
    protected final int next = 2;

    private int mode = AND;
    private String name;

    private HashStore<Combinator> child = new HashStore<>();

    public Combinator(){
        name = Tools.getDefaultName("motion", this);
    }

    public Combinator(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    ArrayList<Combinator> filter(){
        ArrayList<Combinator> list = new ArrayList<>();

        return list;
    }

    public void and(Combinator... combinators){
        for(Combinator combinator : combinators){
            child.put(combinator.getName(), combinator);
        }
    }



}
