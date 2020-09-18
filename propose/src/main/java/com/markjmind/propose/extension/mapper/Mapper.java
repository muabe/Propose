package com.markjmind.propose.extension.mapper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;


public class Mapper {
	protected Context context;
	protected Finder finder;
	protected Object targetObject;
	protected Class<?> targetClass;
	protected HashMap<Integer, View> viewHash = new HashMap<>();

//	private HashMap<Class<?>, InjectAdapter<? extends Annotation>> adapterHashMap = new HashMap<>();
	private ArrayList<AccessibleInfo<Class<?>>> classAnnotations;
	private ArrayList<AccessibleInfo<? extends AccessibleObject>> fieldAnnotations;
	private ArrayList<AccessibleInfo<? extends AccessibleObject>> methodAnnotations;

	private Method[] methods;
	private Field[] fields;

	private Class<?> parentsClass;

	protected Mapper(){

	}


	public Mapper(Class<?> targetClass){
		this.targetClass = targetClass;
		viewHash.clear();
	}

	public Mapper(View finder){
		this(finder, finder);
	}

	public Mapper(Activity finder) {
		this(finder, finder);
	}

	public Mapper(Dialog finder){
		this(finder, finder);
	}

	public Mapper(View finder, Object targetObject){
		reset(finder, targetObject);
	}

	public Mapper(Activity finder, Object targetObject){
		reset(finder, targetObject);
	}

	public Mapper(Dialog finder, Object targetObject){
		reset(finder, targetObject);
	}

	public void reset(View finder, Object targetObject){
		this.context = finder.getContext();
		this.targetObject = targetObject;
		this.finder = new Finder(finder);
		this.targetClass = targetObject.getClass();
		viewHash.clear();
		classAnnotations = null;
		fieldAnnotations = null;
		methodAnnotations = null;
	}

	public void reset(Activity finder, Object targetObject){
		this.context = finder;
		this.targetObject = targetObject;
		this.finder = new Finder(finder);
		this.targetClass = targetObject.getClass();
		viewHash.clear();
		classAnnotations = null;
		fieldAnnotations = null;
		methodAnnotations = null;
	}

	public void reset(Dialog finder, Object targetObject){
		this.context = finder.getContext();
		this.targetObject = targetObject;
		this.finder = new Finder(finder);
		this.targetClass = targetObject.getClass();

		viewHash.clear();
		classAnnotations = null;
		fieldAnnotations = null;
		methodAnnotations = null;
	}

	class AccessibleInfo<T>{
		T accessibleObject;
		Annotation annotation;
		public AccessibleInfo(T accessibleObject, Annotation annotation){
			this.accessibleObject = accessibleObject;
			this.annotation = annotation;
		}
	}

	public void inject(InjectAdapter<? extends Annotation>... adapter){
		injectClass(adapter);
		injectField(adapter);
		injectMethod(adapter);
	}

	private void injectClass(InjectAdapter<? extends Annotation>... adapters){
		ArrayList<InjectListener<? extends Annotation, Class>> injectListeners = new ArrayList<>();
		for(InjectAdapter<? extends Annotation> adapter : adapters){
			if(adapter!=null && adapter.getClassInjector()!=null) {
				adapter.setMapper(this);
				injectListeners.add(adapter.getClassInjector());
			}
		}
		injectClass(injectListeners);
	}

	private void injectClass(ArrayList<InjectListener<? extends Annotation, Class>> injectListeners){
		if(classAnnotations==null) {
			classAnnotations = new ArrayList<>();
			ArrayList<Class<?>> classArrayList = getClasses(targetClass);
			for (Class<?> ab : classArrayList) {
				Annotation[] annotations = ab.getDeclaredAnnotations();
				ArrayList<AccessibleInfo<Class<?>>> info = new ArrayList<>();
				for(Annotation annotation : annotations){
					info.add(new AccessibleInfo<Class<?>>(ab, annotation));
				}
				if(info.size()>0){
					classAnnotations.addAll(info);
				}
			}
		}
		if (injectListeners!=null && classAnnotations.size() > 0) {
			for (AccessibleInfo<Class<?>> info: classAnnotations) {
				for (InjectListener listener : injectListeners) {
					if (info.annotation.annotationType().equals(listener.getAnnotationType())) {
						listener.inject(info.annotation, info.accessibleObject, targetObject);
					}
				}
			}
		}
	}

	private void injectField(InjectAdapter<? extends Annotation>... adapters){
		if (fields == null) {
			fields = getFields(targetClass);
		}

		ArrayList<InjectListener<? extends Annotation, ? extends AccessibleObject>> injectListeners = new ArrayList<>();
		for(InjectAdapter<? extends Annotation> adapter : adapters){
			if(adapter!=null && adapter.getFieldInjector()!=null) {
				adapter.setMapper(this);
				injectListeners.add(adapter.getFieldInjector());
			}
		}
		if(injectListeners.size()>0) {
			fieldAnnotations = injectAdapter(fields, fieldAnnotations, injectListeners);
		}

	}

	private void injectMethod(InjectAdapter<? extends Annotation>... adapters){
		if (methods == null) {
			methods = getMethods(targetClass);
		}
		ArrayList<InjectListener<? extends Annotation, ? extends AccessibleObject>> injectListeners = new ArrayList<>();
		for(InjectAdapter<? extends Annotation> adapter : adapters){
			if(adapter!=null && adapter.getMethodInjector()!=null) {
				adapter.setMapper(this);
				injectListeners.add(adapter.getMethodInjector());
			}
		}
		if(injectListeners.size()>0) {
			methodAnnotations = injectAdapter(methods, methodAnnotations, injectListeners);
		}
	}


	private ArrayList<AccessibleInfo<? extends AccessibleObject>> injectAdapter(AccessibleObject[] abs, ArrayList<AccessibleInfo<? extends AccessibleObject>> outPut, ArrayList<InjectListener<? extends Annotation, ? extends AccessibleObject>> injectListeners){

		if(outPut == null) {
			outPut = new ArrayList<>();
			if (abs != null) {
				for (AccessibleObject ab : abs) {
					Annotation[] annotations = ab.getDeclaredAnnotations();
					ArrayList<AccessibleInfo<? extends AccessibleObject>> info = new ArrayList<>();
					for(Annotation annotation : annotations){
						info.add(new AccessibleInfo<>(ab, annotation));
					}
					if (info.size()>0) {
						outPut.addAll(info);
					}
				}
			}
		}

		if (injectListeners!=null && outPut.size() > 0) {
			for (AccessibleInfo info : outPut) {
				for (InjectListener listener : injectListeners) {
					if (info.annotation.annotationType().equals(listener.getAnnotationType())) {
						listener.inject(info.annotation, info.accessibleObject, targetObject);
					}
				}
			}
		}
		return outPut;
	}

	public View findViewById(int id){
		return finder.findViewById(id);
	}

	public void setInjectParents(Class<?> parentsClass){
		this.parentsClass = parentsClass;
	}

	private ArrayList<Class<?>> getClasses(Class<?> targetClass){
		ArrayList<Class<?>> classesList = new ArrayList();
		if(parentsClass!=null){
			Class<?> tempClass = targetClass;
			Class<?> superClass = tempClass.getSuperclass();
			while(parentsClass.equals(superClass) || !Object.class.equals(superClass)){
				classesList.add(0, superClass);
				tempClass = superClass;
				superClass = tempClass.getSuperclass();
			}

		}
		classesList.add(targetClass);
		return classesList;
	}

	private Method[] getMethods(Class<?> targetClass){
		if(parentsClass!=null){
			Class<?> superClass = targetClass.getSuperclass();
			if(!parentsClass.equals(superClass) || Object.class.equals(superClass)){
				Method[] superMethods = getMethods(superClass);
				Method[] targetMethods = targetClass.getDeclaredMethods();
				Method[] allArray = new Method[targetMethods.length+superMethods.length];
				System.arraycopy(targetMethods, 0, allArray, 0, targetMethods.length);
				System.arraycopy(superMethods, 0, allArray, targetMethods.length, superMethods.length);
				return allArray;
			}
		}
		return targetClass.getDeclaredMethods();
	}

	private Field[] getFields(Class<?> targetClass){
		if(parentsClass!=null){
			Class<?> superClass = targetClass.getSuperclass();
			if(!superClass.equals(parentsClass) || Object.class.equals(superClass)) {
				Field[] superMethods = getFields(superClass);
				Field[] targetMethods = targetClass.getDeclaredFields();
				Field[] allArray = new Field[targetMethods.length + superMethods.length];
				System.arraycopy(targetMethods, 0, allArray, 0, targetMethods.length);
				System.arraycopy(superMethods, 0, allArray, targetMethods.length, superMethods.length);
				return allArray;
			}
		}
		return targetClass.getDeclaredFields();
	}
}
