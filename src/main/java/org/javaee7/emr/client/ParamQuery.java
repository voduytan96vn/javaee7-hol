package org.javaee7.emr.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ParamQuery implements Serializable {
	/**
	 * Refers to the query when using the EntityManager methods that create
	 * query objects.
	 */
	private String name;

	/** Native sql */
	private String sql;

	/** The class of the result. */
	private Class<?> resultClass;

	/** Bind an argument to a named parameter. */
	private Map<String, Object> param;

	/** Bind an argument to a named parameter. */
	private Map<String, Object> paramOutput;

	/** The start position of the first result, numbered from 0 */
	private int firstResult;

	/** Maximum number of results to retrieve. */
	private int maxResult;

	private boolean clear = true;

	private boolean checkMaxRow = false;

	private boolean first = false;

	private boolean storedProcedure = false;

	public ParamQuery() {
		param = new HashMap<String, Object>();
		paramOutput = new HashMap<String, Object>();
	}

	public void addParam(String key, Object value) {
		param.put(key, value);
	}

	public Object removeParam(String key) {
		return param.remove(key);
	}

	public void addParamOutput(String key, Object value) {
		paramOutput.put(key, value);
	}

	public Object removeParamOutput(String key) {
		return paramOutput.remove(key);
	}

	public Object getOutputValue(String key) {
		return paramOutput.get(key);
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	private boolean last = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Class<?> getResultClass() {
		return resultClass;
	}

	public void setResultClass(Class<?> resultClass) {
		this.resultClass = resultClass;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getParamOutput() {
		return paramOutput;
	}

	public void setParamOutput(Map<String, Object> paramOutput) {
		this.paramOutput = paramOutput;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public boolean isClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

	public boolean isCheckMaxRow() {
		return checkMaxRow;
	}

	public void setCheckMaxRow(boolean checkMaxRow) {
		this.checkMaxRow = checkMaxRow;
	}

	/**
	 * Format search string
	 * 
	 * @param src
	 * @return
	 */
	public static String wildCardFull(String query) {
		return wildCardFull(query, false);
	}

	public static String wildCardFull(String query, boolean sensitive) {
		if (query == null || "".equals(query)) {
			return "%";
		}
		query = sensitive ? query : query.toUpperCase();
		query = query.replace("%", "!%");
		query = query.replace("_", "!_");

		query = query.replaceAll("\\s+", " ").trim();
		String[] arr = query.split(" ");

		query = "%";
		for (String q : arr) {
			query += q + "%";
		}
		return query;
	}

	public static String wildCardLike(String query) {
		return wildCardLike(query, false);
	}

	public static String wildCardLike(String query, boolean sensitive) {
		if (query == null || "".equals(query)) {
			return "%";
		}
		query = sensitive ? query : query.toUpperCase();
		query = query.replace("%", "!%");
		query = query.replace("_", "!_");
		return "%".concat(query).concat("%");
	}

	public boolean isStoredProcedure() {
		return storedProcedure;
	}

	public void setStoredProcedure(boolean storedProcedure) {
		this.storedProcedure = storedProcedure;
	}

}
