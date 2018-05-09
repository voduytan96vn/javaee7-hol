/**
 * 
 */
/**
 * @author voduy
 *
 */
package org.javaee7.emr.entities;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

@MappedSuperclass
public class ComEntity implements Serializable {
	public static Integer CANCELED = -1;
	public static Integer OFF = 0;
	public static Integer ON = 1;
	public static String EMPTY = "";

	public static String STYLE_ERROR = "error";
	public static String STYLE_SHOW = "show";
	public static String STYLE_HIDE = "hide";

	@Transient
	private boolean selected = false;

	@Transient
	private boolean removed = false;

	@Transient
	private int index;

	@Transient
	private boolean rowError = false;
	@Transient
	private boolean rowHide = false;

	public ComEntity() {
		super();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isRowError() {
		return rowError;
	}

	public void setRowError(boolean rowError) {
		this.rowError = rowError;
	}

	public boolean isRowHide() {
		return rowHide;
	}

	public void setRowHide(boolean rowHide) {
		this.rowHide = rowHide;
	}

	public String getRowStyle() {
		return "";
	}

	public void setShowHide(String searchText) {
		setShowHide(searchText, false);
	}

	public void setShowHide(String searchText, boolean isAdvance) {

	}

	public String getMatchingText() {
		return null;
	}

	public String truncate(String val, int at) {
		if (val != null) {
			int len = val.length();
			at = at < 10 ? 10 : at;
			if (len > (at + 3)) {
				val = val.substring(0, at) + "...";
			}
		}
		return val;
	}

	private static HashMap<String, String[]> entityFields = new HashMap<String, String[]>();
	private static HashMap<String, Method[]> entityMethods = new HashMap<String, Method[]>();

	public String getRowData() {
		String className = this.getClass().getSimpleName();
		String[] fieldNames = entityFields.get(className);
		Method[] methods = entityMethods.get(className);
		if (fieldNames == null) {
			getMethods();
			fieldNames = entityFields.get(className);
			methods = entityMethods.get(className);
			if (fieldNames == null) {
				return "{}";
			}
		}
		JSONObject json = new JSONObject();
		Object value = null;
		for (int i = 0; i < fieldNames.length; i++) {
			try {
				value = methods[i].invoke(this);
				json.put(fieldNames[i], value);
			} catch (Exception e) {
				continue;
			}
		}
		return json.toString();
	}

	private void getMethods() {
		Class<?> clazz = this.getClass();
		try {
			ArrayList<String> fieldNames = new ArrayList<String>();
			ArrayList<Method> methods = new ArrayList<Method>();

			Field[] fields = clazz.getDeclaredFields();
			String className = clazz.getSimpleName();
			String fieldName = "";
			String methodName = null;
			Method method = null;
			for (Field field : fields) {
				try {
					if (field.getAnnotation(Column.class) != null) {
						fieldName = field.getName();
						methodName = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
						try {
							method = clazz.getMethod("get" + methodName);
						} catch (NoSuchMethodException e) {
							try {
								method = clazz.getMethod("is" + methodName);
							} catch (NoSuchMethodException f) {
								method = null;
							}
						}

						if (fieldName != null && method != null) {
							fieldNames.add(fieldName);
							methods.add(method);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			entityFields.put(className, fieldNames.toArray(new String[fieldNames.size()]));
			entityMethods.put(className, methods.toArray(new Method[methods.size()]));
		} catch (Exception e) {
			return;
		}
	}

	protected String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		int strLen = str.length();
		int[] newCodePoints = new int[strLen];
		int outOffset = 0;

		boolean capitalizeNext = true;
		str = str.toLowerCase();
		for (int index = 0; index < strLen;) {
			final int codePoint = str.codePointAt(index);
			if (Character.isWhitespace(codePoint)) {
				capitalizeNext = true;
				newCodePoints[outOffset++] = codePoint;
				index += Character.charCount(codePoint);
			} else if (capitalizeNext) {
				int titleCaseCodePoint = Character.toTitleCase(codePoint);
				newCodePoints[outOffset++] = titleCaseCodePoint;
				index += Character.charCount(titleCaseCodePoint);
				capitalizeNext = false;
			} else {
				newCodePoints[outOffset++] = codePoint;
				index += Character.charCount(codePoint);
			}
		}
		return new String(newCodePoints, 0, outOffset);
	}
}