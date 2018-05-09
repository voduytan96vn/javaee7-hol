package org.javaee7.emr.entities;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;

import net.sf.json.JSONObject;

public class DaoEntity extends ComEntity implements Serializable {
	private static final long serialVersionUID = 8633315842265816520L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Long createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date")
	private Long updatedDate;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Column(name = "deleted_date")
	private Long deletedDate;

	@Version
	@Column(name = "version")
	private Integer version = 0;

	public DaoEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deletedBy != null;
	}

	public void setDeleted(boolean deleted) {

	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Long getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Long deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
