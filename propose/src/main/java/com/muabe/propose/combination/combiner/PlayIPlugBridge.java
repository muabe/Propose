package com.muabe.propose.combination.combiner;

public interface PlayIPlugBridge<T extends PlayCombiner> {
    boolean play(T player, float ratio);
}
