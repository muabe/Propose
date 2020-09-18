package com.markjmind.propose.extension.mapper;

import java.lang.annotation.Annotation;

interface InjectListener<T extends Annotation, E> {
    void inject(T annotation, E element, Object targetObject);
    Class<T> getAnnotationType();
}
