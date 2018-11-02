package com.muabe.propose.combine;

public interface Priority<ParamType> {
    int getPriority();
    float compare(ParamType param);
}
