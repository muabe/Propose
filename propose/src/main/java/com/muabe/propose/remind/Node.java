package com.muabe.propose.remind;

public class Node {
    enum NodeType{
        WITH,
        OR,
        NEXT,
        LAYER,
        ELEMENT
    }
    private Layer layer;
    private String name;
    private NodeType nodeType;
    private long ratio = 0;


    public Node(String name, NodeType nodeType){
        this.name = name;
        this.nodeType = nodeType;
    }

    public void setLayer(Layer layer){
        this.layer = layer;
    }
}
