package com.markjmind.propose.extension;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Store<Value> extends LinkedHashMap<String, Object> implements Comparable{

	private String compareString = null;
	private boolean ASC = true;
	
	enum Type{
		Object,
		StoreList
	}
	private final String Type_StoreList = "com.markjmind.mobile.api.hub.StoreList";
	
	private LinkedHashMap<String,String> types; 
	
	public Store(){
		super();
		types = new LinkedHashMap<>();
	}
	
	/**
	 * Store의 값을 출력한다.
	 */
	public String toString(){
		StringBuffer msg =new StringBuffer(); 
		msg.append("{");
		String[] keys = this.getKeys();
		String[] values = this.getStrValues();
		
		for(int i=0;i<keys.length;i++){
			if(i!=0){
				msg.append(",");
			}
			msg.append(keys[i] +":"+ values[i]);
		}
		msg.append("}");
		return msg.toString();
	}
	
	/**
	 * key값에 대응하는 Object 객체를 가져온다
	 */
	public Value get(String key){
		Value result = (Value)super.get(key);
		if(result==null){
			//result = super.get(key.toUpperCase());
			return null;
		}
		return result;
	}

	/**
	 * key값에 대응하는 Object 객체를 가져온다
	 */
	public Value get(Object key){
		return this.get(String.valueOf(key));
	}
	
	/**
	 * key값에 대응하는 String 객체를 가져온다
	 */
	public String getString(String key){
		Object result = this.get(key);
		if(result == null){
			return null;
		}
		return (String)result;
	}
	
	public String optString(String key, String defaultValue){
		Object result = this.get(key);
		if(result == null){
			return defaultValue;
		}
		return (String)result;
	}


	public Integer getInt(String key){
		Object result = this.get(key);
		if(result == null){
			return null;
		}
		if(result instanceof String){
			return Integer.parseInt((String)result);
		}
		return ((Integer)result).intValue();
	}

	public int optInt(String key, int defaultValue){
		Object result = this.get(key);
		if(result == null){
			return defaultValue;
		}
		if(result instanceof String){
			return Integer.parseInt((String) result);
		}
		return ((Integer)result).intValue();
	}

	public Float getFloat(String key){
		Object result = this.get(key);
		if(result == null){
			return null;
		}
		if(result instanceof String){
			return Float.parseFloat((String) result);
		}
		return ((Float)result).floatValue();
	}

	public Float optFloat(String key, float defaultValue){
		Object result = this.get(key);
		if(result == null){
			return defaultValue;
		}
		if(result instanceof String){
			return Float.parseFloat((String) result);
		}
		return ((Float)result).floatValue();
	}

	public Long getLong(String key){
		Object result = this.get(key);
		if(result == null){
			return null;
		}
		if(result instanceof String){
			return Long.parseLong((String)result);
		}
		return ((Long)result).longValue();
	}

	public Boolean getBoolean(String key){
		Object result = this.get(key);
		if(result == null){
			return null;
		}
		if(result instanceof String){
			return Boolean.parseBoolean((String)result);
		}
		return ((Boolean)result).booleanValue();
	}


	public Boolean optBoolean(String key, boolean defaultValue){
		Object result = this.get(key);
		if(result == null){
			return defaultValue;
		}
		if(result instanceof String){
			return Boolean.parseBoolean((String)result);
		}
		return ((Boolean)result).booleanValue();
	}

	/**
	 * Store의 에 배열로 값을 셋팅한다.
	 */
	public void put(String[] keys, Object[] values){
		if(keys.length!= values.length){
			System.out.println("Store클래스 put(Object keys[], Object[] values) 함수에는\nkeys와 values의 배열의 길이가 같아야합니다.");
		}
		for(int i=0; i<keys.length; i++){
			this.put(keys[i], values[i]);
		}
	}
	
	/**
	 * Store에 키값들을 String배열로 얻어온다
	 */
//	public String[] getIds(){
//		if(this.size()==0)
//			return null;
//		String[] keys=new String[this.size()];
//		Enumeration enu = this.keys();
//		for(int i=0; i<keys.length; i++){
//			keys[i] = enu.nextElement().toString();
//		}
//		return keys;
//	}
	
	public String[] getKeys(){
		if(this.size()==0)
		{
		    return new String[0];
		}

		String[] keys = new String[this.size()];
		this.keySet().toArray(keys);
		return keys;
	}
	/**
	 * Store의 list들에 해당하는 키값을 가져온다.
	 */
	public static String[] getKeys(List list){
		Store store = (Store)list.get(0);
		String[] col;
		col = store.getKeys();
		return col;
	}
	
	/**
	 * Store에 값들을 배열로 얻어온다
	 */
	public Value[] getValues(){
		if(this.size()==0)
			return null;
		Value[] o = (Value[])this.values().toArray();
		return o;
	}
	
	/**
	 * Store에 값들을 String 배열로 얻어온다
	 */
	public String[] getStrValues(){
		if(this.size()==0)
			return null;
		Object[] o = this.values().toArray();
		String[] values= new String[this.size()];
		for(int i=0; i<values.length; i++){
			values[i] = (String)o[i];
		}
		return values;
	}
	
	/**
	 * Store에 값들을 String 배열로 얻어온다
	 */
	public Value getValue(int index){
		Value[] o = getValues();
		return o[index];
	}
	
	/**
	 * Store list에서 key에 대응하는 value값만 배열로 가져온다 
	 */
	public static String[] getOneValues(List list, String key){
		String[] result = new String[list.size()];
		for(int i=0; i<result.length; i++){
			result[i]=((Store)list.get(i)).getString(key);
		}
		return result;
	}
	
	/**
	 * key값에 대응하는 Object 객체를 가져온다
	 */
	public static String[][] getListValues(List list){
		Store store = (Store)list.get(0);
		String[] col;
		String[][] values;
		col = store.getKeys();
		values = new String[list.size()+1][col.length];
		for(int i=0;i<list.size();i++){
			store = (Store)list.get(i);
			values[i]=store.getStrValues();
		}
		return values;
	}
	
	/**
	 * List의 index에 대하여 key값에 대응하는 Object 객체를 가져온다
	 */
	public static Object getValue(List list,int index,String key){
	    Store store = (Store)list.get(index);
	    return store.get(key);
	}
	
	/**
	 * List의 index에 대하여 key값에 대응하는 String 값을 가져온다
	 */
	public static String getStrValue(List list,int index,String key){
	    Store store = (Store)list.get(index);
	    return store.getString(key);
	}
	
	/**
	 * List의 index에 대하여 key값에 대응하는 String 값을 가져온다
	 */
	public static int getIntValue(List list,int index,String key){
	    Store store = (Store)list.get(index);
	    return store.getInt(key);
	}
	/**
	 * index를 컬럼으로 테이블형식의 2차원 배열을 리턴한다
	 * 배열 0번째는 index컬럼이고 그이후는 list의 모든 값들이다
	 * index가 null이면 store의 키값들이 컬럼이 된다
	 * @param list
	 * @param index
	 * @return
	 */
	public static String[][] getMatrix(List list, String[] index){
		Store store = (Store)list.get(0);
		String[] col;
		String[][] matrix;
		if(index ==null){
			col = store.getKeys();
		}else{
			col = index;
		}
		matrix = new String[list.size()+1][col.length];
		matrix[0] = col;
		for(int i=0;i<list.size();i++){
			store = (Store)list.get(i);
			if(index==null){
				matrix[i+1]=store.getStrValues();
			}else{
				for(int j=0;j<col.length;j++){
					matrix[i+1][j]= (String)store.get(col[j]);
				}
			}
		}
		return matrix;
	}
	
	public Map mapMapper(Object instance) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException{
		LinkedHashMap result = new LinkedHashMap();
		Class model = instance.getClass();
		Field[] fields = model.getDeclaredFields();
		String methodName = null;
		Object value = null;
		Method method = null;
		for(int i=0;i<fields.length; i++){
			methodName = "get"+fields[i].getName().substring(0,1).toUpperCase()+fields[i].getName().substring(1);
			try
			{
				method = model.getMethod(methodName);	
				value = method.invoke(instance);
				if(value!=null){
					result.put(fields[i].getName(), value);
				}
			} 
			catch (NoSuchMethodException e)
			{
			}
		}
		return result;
	}
	
	/**
	 * store를 지정한 클래스의 모델로 변환한다.
	 * @param modelClass
	 * @param store
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object modelMapper(Class modelClass,Map store) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object instance = modelClass.newInstance();
		Field[] fields = modelClass.getDeclaredFields();	
		String curName;
		Method curMethod;
		Class[] paramTypes = new Class[1];
		Object[] paramValues = new Object[1];
		String curStoreKey;
		for(int i=0; i<fields.length; i++){
			curName = fields[i].getName();
			if(store.get((curStoreKey=curName))!=null || store.get((curStoreKey=curName.toUpperCase()))!=null){				
				curName = "set"+curName.substring(0,1).toUpperCase()+curName.substring(1);
				paramTypes[0]= store.get(curStoreKey).getClass();
				paramValues[0] = store.get(curStoreKey);
				try {
					curMethod = modelClass.getMethod(curName,paramTypes);
					curMethod.invoke(instance, paramValues);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					System.out.println("modelMapper를 실행하는데 : "+curName+"("+paramTypes[0].getName()+")메소드가 없거나 매개변수가 잘못되었습니다.");
				}
			}
		}
		return instance;
	}
	
	/**
	 * store list를 지정한 클래스의 모델로 변환하여 배열로 반환한다.
	 * @param modelClass
	 * @param list
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object[] modelMappers(Class modelClass, List list) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		Object[] models = new Object[list.size()];
		for(int i=0; i<models.length; i++){
			models[i] = modelMapper(modelClass, (Store)list.get(i));
		}
		return models;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized Store<Value> add(String key, Object value){
	    put(key, value);
		return this;
	}

	public String getTypeName(String key){
		return types.get(key);
	}
	
	public Type getType(String key){
		if(Type_StoreList.equals(key)){
			return Type.StoreList;
		}
		return Type.Object;
	}
	
	public boolean isStoreList(String key){
		return Type_StoreList.equals(types.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized Store<Value> put(String key, Object value){
	    	super.put(key, value);
	    	types.put(key, "java.lang.Object");
	    	return this;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized Store<Value> put(String key, Object value, String type){
	    	super.put(key, value);
	    	types.put(key, type);
	    	return this;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public synchronized Object remove(String key){
			types.remove(key);	
	    	return super.remove(key);
		
	}
	
	/**
	 * 
	 */
	public synchronized void clear(){
			types.clear();
	    	super.clear();
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public synchronized void replaceAll(String arg0, String arg1){
	    String[] keys = this.getKeys();
	    for(int i=0;i<keys.length;i++){
		String key = keys[i];
		String value = (this.getString(key));
		value.replace(arg0, arg1);
		this.add(key, value);
	    }
	}

	public void setCompare(String key) {
		this.compareString = key;
	}
	
	public void setCompare(String key,boolean ASC) {
		boolean temp = this.ASC;
		this.ASC = ASC;
		this.compareString = key;
	}
	
	public void setASC(boolean ASC){
		this.ASC = ASC;
	}

	public int compareTo(Object another) {
		if(compareString==null){
			compareString=this.getString(this.getKeys()[0]);
		}
		if(ASC){
			return this.getString(compareString).compareTo(((Store)another).getString(compareString));
		}else{
			return this.getString(compareString).compareTo(((Store)another).getString(compareString))*(-1);
		}
	}


	

}



