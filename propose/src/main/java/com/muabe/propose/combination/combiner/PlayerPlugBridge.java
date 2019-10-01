package com.muabe.propose.combination.combiner;

public interface PlayerPlugBridge<T extends PlayCombiner> {
    boolean play(T player, float ratio);
}
