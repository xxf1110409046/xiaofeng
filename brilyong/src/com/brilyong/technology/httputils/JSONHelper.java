package com.brilyong.technology.httputils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class JSONHelper {

	private static String TAG = "JSONHelper";

	/**
	 * ������ת����Json�ַ���
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSON(Object obj) {
		JSONStringer js = new JSONStringer();
		serialize(js, obj);

		return js.toString();
	}

	/**
	 * ���л�ΪJSON
	 * 
	 * @param js
	 * @param o
	 *            5
	 */
	private static void serialize(JSONStringer js, Object o) {
		if (isNull(o)) {
			try {
				js.value(null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return;
		}

		Class<?> clazz = o.getClass();
		if (isObject(clazz)) { // ����
			serializeObject(js, o);
		} else if (isArray(clazz)) { // ����
			serializeArray(js, o);
		} else if (isCollection(clazz)) { // ����
			Collection<?> collection = (Collection<?>) o;
			serializeCollect(js, collection);
		} else { // ����ֵ
			try {
				js.value(o);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���л�����
	 * 
	 * @param js
	 * @param array
	 */
	private static void serializeArray(JSONStringer js, Object array) {
		try {
			js.array();
			for (int i = 0; i < Array.getLength(array); ++i) {
				Object o = Array.get(array, i);
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���л�����
	 * 
	 * @param js
	 * @param collection
	 */
	private static void serializeCollect(JSONStringer js,
			Collection<?> collection) {
		try {
			js.array();
			for (Object o : collection) {
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���л�����
	 * 
	 * @param js
	 * @param obj
	 */
	public static void serializeObject(JSONStringer js, Object obj) {
		try {
			js.object();
			Class<? extends Object> objClazz = obj.getClass();
			Method[] methods = objClazz.getDeclaredMethods();
			Field[] fields = objClazz.getDeclaredFields();
			for (Field field : fields) {
				try {
					String fieldType = field.getType().getSimpleName();
					String fieldGetName = parseMethodName(field.getName(),
							"get");
					if (!haveMethod(methods, fieldGetName)) {
						continue;
					}
					Method fieldGetMet = objClazz.getMethod(fieldGetName,
							new Class[] {});
					Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});
					String result = null;
					if ("Date".equals(fieldType)) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss", Locale.US);
						result = sdf.format((Date) fieldVal);

					} else {
						if (null != fieldVal) {
							result = String.valueOf(fieldVal);
						}
					}
					js.key(field.getName());
					serialize(js, result);
				} catch (Exception e) {
					continue;
				}
			}
			js.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ж��Ƿ����ĳ���Ե� get����
	 * 
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	public static boolean haveMethod(Method[] methods, String fieldMethod) {
		for (Method met : methods) {
			if (fieldMethod.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ƴ��ĳ���Ե� get����set����
	 * 
	 * @param fieldName
	 * @param methodType
	 * @return
	 */
	public static String parseMethodName(String fieldName, String methodType) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return methodType + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	/**
	 * set���Ե�ֵ��Bean
	 * 
	 * @param obj
	 * @param valMap
	 */
	public static void setFieldValue(Object obj, Map<String, String> valMap) {
		Class<?> cls = obj.getClass();
		// ȡ��bean������з���
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {
			try {
				String setMetodName = parseMethodName(field.getName(), "set");
				if (!haveMethod(methods, setMetodName)) {
					continue;
				}
				Method fieldMethod = cls.getMethod(setMetodName,
						field.getType());
				String value = valMap.get(field.getName());
				if (null != value && !"".equals(value)) {
					String fieldType = field.getType().getSimpleName();
					if ("String".equals(fieldType)) {
						fieldMethod.invoke(obj, value);
					} else if ("Date".equals(fieldType)) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss", Locale.US);
						Date temp = sdf.parse(value);
						fieldMethod.invoke(obj, temp);
					} else if ("Integer".equals(fieldType)
							|| "int".equals(fieldType)) {
						Integer intval = Integer.parseInt(value);
						fieldMethod.invoke(obj, intval);
					} else if ("Long".equalsIgnoreCase(fieldType)) {
						Long temp = Long.parseLong(value);
						fieldMethod.invoke(obj, temp);
					} else if ("Double".equalsIgnoreCase(fieldType)) {
						Double temp = Double.parseDouble(value);
						fieldMethod.invoke(obj, temp);
					} else if ("Boolean".equalsIgnoreCase(fieldType)) {
						Boolean temp = Boolean.parseBoolean(value);
						fieldMethod.invoke(obj, temp);
					} else {
						System.out.println("setFieldValue not supper type:"
								+ fieldType);
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

	}

	/**
	 * ����תMap
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> getFieldValueMap(Object obj) {
		Class<?> cls = obj.getClass();
		Map<String, String> valueMap = new HashMap<String, String>();
		// ȡ��bean������з���
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldType = field.getType().getSimpleName();
				String fieldGetName = parseMethodName(field.getName(), "get");
				if (!haveMethod(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls
						.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});
				String result = null;
				if ("Date".equals(fieldType)) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					result = sdf.format((Date) fieldVal);

				} else {
					if (null != fieldVal) {
						result = String.valueOf(fieldVal);
					}
				}
				valueMap.put(field.getName(), result);
			} catch (Exception e) {
				continue;
			}
		}
		return valueMap;

	}

	/**
	 * ��������ֶθ�ֵ
	 * 
	 * @param obj
	 * @param fieldSetMethod
	 * @param fieldType
	 * @param value
	 */
	public static void setFiedlValue(Object obj, Method fieldSetMethod,
			String fieldType, Object value) {

		try {
			if (null != value && !"".equals(value)) {
				if ("String".equals(fieldType)) {
					fieldSetMethod.invoke(obj, value.toString());
				} else if ("Date".equals(fieldType)) {
					Date temp = null;

					try {
						long mi = Long.parseLong(value + "");
						temp = new Date(mi);
					} catch (Exception e) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
						temp = sdf.parse(value.toString());
					}
					fieldSetMethod.invoke(obj, temp);
				} else if ("Integer".equals(fieldType)
						|| "int".equals(fieldType)) {
					Integer intval = Integer.parseInt(value.toString());
					fieldSetMethod.invoke(obj, intval);
				} else if ("Long".equalsIgnoreCase(fieldType)) {
					Long temp = Long.parseLong(value.toString());
					fieldSetMethod.invoke(obj, temp);
				} else if ("Double".equalsIgnoreCase(fieldType)) {
					Double temp = Double.parseDouble(value.toString());
					fieldSetMethod.invoke(obj, temp);
				} else if ("Boolean".equalsIgnoreCase(fieldType)) {
					Boolean temp = Boolean.parseBoolean(value.toString());
					fieldSetMethod.invoke(obj, temp);
				} else {
					fieldSetMethod.invoke(obj, value);

				}
			}

		} catch (Exception e) {
			//
		}

	}

	/**
	 * �����л��򵥶���
	 * 
	 * @param jo
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(JSONObject jo, Class<T> clazz) {
		if (clazz == null || isNull(jo)) {
			return null;
		}

		T obj = newInstance(clazz);
		if (obj == null) {
			return null;
		}
		if (isMap(clazz)) {
			setField(obj, jo);
		} else {
			// ȡ��bean������з���
			Method[] methods = clazz.getDeclaredMethods();
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				String setMetodName = parseMethodName(f.getName(), "set");
				if (!haveMethod(methods, setMetodName)) {
					continue;
				}
				try {
					Method fieldMethod = clazz.getMethod(setMetodName,
							f.getType());
					setField(obj, fieldMethod, f, jo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return obj;
	}

	/**
	 * �����л��򵥶���
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}

		JSONObject jo = null;
		try {
			jo = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseObject(jo, clazz);
	}

	/**
	 * �����л��������
	 * 
	 * @param ja
	 * @param clazz
	 * @return
	 */
	public static <T> T[] parseArray(JSONArray ja, Class<T> clazz) {
		if (clazz == null || isNull(ja)) {
			return null;
		}

		int len = ja.length();

		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(clazz, len);

		for (int i = 0; i < len; ++i) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				T o = parseObject(jo, clazz);
				array[i] = o;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return array;
	}

	/**
	 * �����л��������
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T[] parseArray(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}
		JSONArray jo = null;
		try {
			jo = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseArray(jo, clazz);
	}

	/**
	 * �����л����ͼ���
	 * 
	 * @param ja
	 * @param collectionClazz
	 * @param genericType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> parseCollection(JSONArray ja,
			Class<?> collectionClazz, Class<T> genericType) {

		if (collectionClazz == null || genericType == null || isNull(ja)) {
			return null;
		}

		Collection<T> collection = (Collection<T>) newInstance(collectionClazz);

		for (int i = 0; i < ja.length(); ++i) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				T o = parseObject(jo, genericType);
				collection.add(o);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return collection;
	}

	/**
	 * �����л����ͼ���
	 * 
	 * @param jsonString
	 * @param collectionClazz
	 * @param genericType
	 * @return
	 */
	public static <T> Collection<T> parseCollection(String jsonString,
			Class<?> collectionClazz, Class<T> genericType) {
		if (collectionClazz == null || genericType == null
				|| jsonString == null || jsonString.length() == 0) {
			return null;
		}
		JSONArray jo = null;
		try {
			jo = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseCollection(jo, collectionClazz, genericType);
	}

	/**
	 * �������ʹ�������
	 * 
	 * @param clazz
	 * @return
	 */
	private static <T> T newInstance(Class<T> clazz) {
		if (clazz == null)
			return null;
		T obj = null;
		try {

			obj = clazz.newInstance();
		} catch (Exception e) {
			// e.printStackTrace();
			if (clazz.toString().equals("java.util.List")
					|| "interface java.util.List".equals(clazz.toString())) {
				return (T) new ArrayList();
			}

		}
		return obj;
	}

	/**
	 * �趨Map��ֵ
	 * 
	 * @param obj
	 * @param jo
	 */
	private static void setField(Object obj, JSONObject jo) {
		try {
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jo.keys();
			String key;
			Object value;
			@SuppressWarnings("unchecked")
			Map<String, Object> valueMap = (Map<String, Object>) obj;
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jo.get(key);
				valueMap.put(key, value);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String paseString(String value) {
		value = value.replace("\\", "");
		value = value.replace("/", "");
		Pattern p = Pattern.compile("Date\\(-*[0-9]*\\)");
		Matcher m = p.matcher(value);
		while (m.find()) {
			String macth = m.group();
			String newStr = getDateStr(macth);
			value = value.replace(macth, newStr);
		}
		return value;
	}

	public static String getDateStr(String value) {

		String reslut = "";
		value = value.replace("Date(", "");
		value = value.replace(")", "");
		return value;
	}

	/**
	 * �趨�ֶε�ֵ
	 * 
	 * @param obj
	 * @param fieldSetMethod
	 * @param f
	 * @param jo
	 */
	private static void setField(Object obj, Method fieldSetMethod, Field f,
			JSONObject jo) {
		String name = f.getName();
		Class<?> clazz = f.getType();
		try {
			if (isArray(clazz)) { // ����
				Class<?> c = clazz.getComponentType();
				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object array = parseArray(ja, c);
					setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(),
							array);
				}
			} else if (isDate(clazz)) {
				Object o = jo.opt(name);
				if (o != null) {
					String data=paseString(o.toString());
					setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), data);
				}
			}

			else if (isCollection(clazz)) { // ���ͼ���
				// ��ȡ����ķ�������
				Class<?> c = null;
				Type gType = f.getGenericType();
				if (gType instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) gType;
					Type[] targs = ptype.getActualTypeArguments();
					if (targs != null && targs.length > 0) {
						Type t = targs[0];
						c = (Class<?>) t;
					}
				}

				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object o = parseCollection(ja, clazz, c);
					setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);
				}
			} else if (isSingle(clazz)) { // ֵ����
				Object o = jo.opt(name);
				if (o != null) {
					setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);
				}
			} else if (isObject(clazz)) { // ����
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);
				}
			} else if (isList(clazz)) { // �б�
				// JSONObject j = jo.optJSONObject(name);
				// if (!isNull(j)) {
				// Object o = parseObject(j, clazz);
				// f.set(obj, o);
				// }
			} else {
				throw new Exception("unknow type!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �趨�ֶε�ֵ
	 * 
	 * @param obj
	 * @param f
	 * @param jo
	 */
	private static void setField(Object obj, Field f, JSONObject jo) {
		String name = f.getName();
		Class<?> clazz = f.getType();
		try {
			if (isArray(clazz)) { // ����
				Class<?> c = clazz.getComponentType();
				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object array = parseArray(ja, c);
					f.set(obj, array);
				}
			} else if (isCollection(clazz)) { // ���ͼ���
				// ��ȡ����ķ�������
				Class<?> c = null;
				Type gType = f.getGenericType();
				if (gType instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) gType;
					Type[] targs = ptype.getActualTypeArguments();
					if (targs != null && targs.length > 0) {
						Type t = targs[0];
						c = (Class<?>) t;
					}
				}

				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object o = parseCollection(ja, clazz, c);
					f.set(obj, o);
				}
			} else if (isSingle(clazz)) { // ֵ����
				Object o = jo.opt(name);
				if (o != null) {
					f.set(obj, o);
				}
			} else if (isObject(clazz)) { // ����
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					f.set(obj, o);
				}
			} else if (isList(clazz)) { // �б�
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					f.set(obj, o);
				}
			} else {
				throw new Exception("unknow type!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ж϶����Ƿ�Ϊ��
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean isNull(Object obj) {
		if (obj instanceof JSONObject) {
			return JSONObject.NULL.equals(obj);
		}
		return obj == null;
	}

	/**
	 * �ж��Ƿ���ֵ����
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isSingle(Class<?> clazz) {
		return isBoolean(clazz) || isNumber(clazz) || isString(clazz);
	}

	/**
	 * �Ƿ񲼶�ֵ
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return (clazz != null)
				&& ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class
						.isAssignableFrom(clazz)));
	}

	/**
	 * �Ƿ���ֵ
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isNumber(Class<?> clazz) {
		return (clazz != null)
				&& ((Byte.TYPE.isAssignableFrom(clazz))
						|| (Short.TYPE.isAssignableFrom(clazz))
						|| (Integer.TYPE.isAssignableFrom(clazz))
						|| (Long.TYPE.isAssignableFrom(clazz))
						|| (Float.TYPE.isAssignableFrom(clazz))
						|| (Double.TYPE.isAssignableFrom(clazz)) || (Number.class
							.isAssignableFrom(clazz)));
	}

	/**
	 * �ж��Ƿ����ַ���
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isString(Class<?> clazz) {
		return (clazz != null)
				&& ((String.class.isAssignableFrom(clazz))
						|| (Character.TYPE.isAssignableFrom(clazz)) || (Character.class
							.isAssignableFrom(clazz)));
	}

	public static boolean isDate(Class<?> clazz) {

		return (clazz != null) && ((Date.class.isAssignableFrom(clazz)));
	}

	/**
	 * �ж��Ƿ��Ƕ���
	 * 
	 * @param clazz
	 * @return
	 */
	private static boolean isObject(Class<?> clazz) {
		return clazz != null && !isSingle(clazz) && !isArray(clazz)
				&& !isCollection(clazz);
	}

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isArray(Class<?> clazz) {
		return clazz != null && clazz.isArray();
	}

	/**
	 * �ж��Ƿ��Ǽ���
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * �ж��Ƿ���Map
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isMap(Class<?> clazz) {
		return clazz != null && Map.class.isAssignableFrom(clazz);
	}

	/**
	 * �ж��Ƿ����б�
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isList(Class<?> clazz) {
		return clazz != null && List.class.isAssignableFrom(clazz);
	}

}
