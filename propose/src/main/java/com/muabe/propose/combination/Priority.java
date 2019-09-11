package com.muabe.propose.combination;

public interface Priority<ParamType> {
    int getPriority();
    float compare(ParamType param);
}
