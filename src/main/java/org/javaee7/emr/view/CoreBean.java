package org.javaee7.emr.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import org.javaee7.emr.client.LocalService;
import org.javaee7.emr.client.RemoteService;
import org.javaee7.emr.view.Lib;
import org.javaee7.emr.dto.Dto;
import org.javaee7.emr.dto.LoginInfo;
import org.javaee7.emr.dto.MessageDto;
import org.javaee7.emr.dto.PortalInfo;

public abstract class CoreBean {
	public static final String ID = "id";
	public static final String LOGIN_INFO = "loginInfo";
	public static final String PAGE_DTO = "pagedto";
	public static final String MENU_DTO = "menudto";
	public static final String FROM_PAGE = "fromPage";
	public static final String CACHE_DATA = "cacheData";

	public static final String SUPPLIER_ID = "supplierId";
	public static final String BRAND_ID = "brandId";
	public static final String CUSTOMER_ID = "customerId";

	public static final String TAB_ACTIVE = "tabActive";
	public static final String TAB_DETAIL_ACTIVE = "tabDetailActive";
	public static final String SROLL_WIDTH = "scrollWidth";
	public static final String SROLL_HEIGHT = "scrollHeight";

	private RemoteService remote;
	private LocalService local;
	private boolean isRemote = Lib.toBoolean(Lib.getSetting("ejb.app.remote"), false);
	protected LoginInfo loginInfo;
	protected MessageDto messageDto;
	protected Locale locale;
	protected TimeZone zone;

	protected PortalInfo portalInfo;
	protected Map<String, Object> viewValue;

	// protected HtmlOutputScriptHandler outputScript;

	protected boolean isCache = false;

	protected static HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> _i18n;
	protected Map<String, Object> pin;

	@SuppressWarnings("unchecked")
	public CoreBean() {
		try {
			loginInfo = JSFUtils.findBean("loginInfo");
			portalInfo = JSFUtils.findBean("portalInfo");
			messageDto = JSFUtils.findBean("messagedto");
			viewValue = JSFUtils.findBean("viewValue");
			_i18n = JSFUtils.findBean("i18n");
			locale = loginInfo.getLocale();
			zone = loginInfo.getZone();

			String nameService = getScreen() + "Service";
			if (isRemote) {
				remote = (RemoteService) ServiceUtils.getService(nameService, RemoteService.class);
			} else {
				local = (LocalService) ServiceUtils.getService(nameService, LocalService.class);
			}

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, Object> session = facesContext.getExternalContext().getSessionMap();
			pin = (Map<String, Object>) session.get("pin_" + getScreen());
			if (pin == null) {
				pin = new HashMap<String, Object>();
				session.put("pin_" + getScreen(), pin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract String getScreen();

	public String getBussinessCode() {
		return null;
	}

	public String getTitle() {
		return Lib.getResource(null, getBussinessCode() + "title");
	}

	/**
	 * Get current url
	 * 
	 * @return
	 */
	public String getUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ectx = context.getExternalContext();
		StringBuffer url = new StringBuffer(ectx.getRequestScheme());
		url.append("://");
		url.append(ectx.getRequestServerName());
		url.append(":");
		url.append(ectx.getRequestServerPort());
		url.append(ectx.getRequestContextPath());
		url.append(ectx.getRequestServletPath());
		return url.toString();
	}

	@PreDestroy
	public void release() {
		System.out.println("Destroy View Bean " + getScreen());

		// Release dto
		// JSFUtils.setBean(getPageCode() + "dto", null);
	}

	/**
	 * Get dto of page
	 * 
	 * @return
	 */
	public <D> D getDto() {
		D dto = null;
		try {
			dto = JSFUtils.findBean(getScreen() + "dto");
			isCache = isCacheDto();
			if (isCache) {
				D dtoCache = getCacheDto();
				if (dtoCache != null) {
					try {
						BeanUtils.copyProperties(dto, dtoCache);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (dto instanceof Dto) {
						putParam(ID, ((Dto) dto).getId());
						// putParamPage(FROM_PAGE, ((Dto) dto).getFromPage());
					}
				}
			}

			if (dto instanceof Dto) {
				((Dto) dto).setScreen(getScreen());

				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ServletContext context = (ServletContext) ec.getContext();
				String contextPath = context.getRealPath("/");
				String reportPath = Lib.getSetting("report.path.template");
				if (Lib.isEmpty(reportPath)) {
					reportPath = context.getRealPath("WEB-INF/classes/report");
				}
				((Dto) dto).setContextPath(contextPath);
				((Dto) dto).setReportPath(reportPath);
				((Dto) dto).setLocale(locale);

				((Dto) dto).rendered(loginInfo.getRendered(getBussinessCode()), true);
				((Dto) dto).disabled(loginInfo.getDisabled(getBussinessCode()), true);
				((Dto) dto).hidden(loginInfo.getHidden(getBussinessCode()), true);
				((Dto) dto).readOnly(loginInfo.getReadOnly(getBussinessCode()), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public <D> D getDto(String name) {
		D dto = JSFUtils.findBean(name + "dto");
		if (dto == null) {
			dto = JSFUtils.findBean(name);
		}
		return dto;
	}

	public void fromCacheDto(boolean cache) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(CACHE_DATA, cache);
	}

	public boolean isCacheDto() {
		FacesContext context = FacesContext.getCurrentInstance();
		return Lib.toBoolean(context.getExternalContext().getSessionMap().remove(CACHE_DATA), false);
	}

	/**
	 * Get dto from cache
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <D> D getCacheDto() {
		FacesContext context = FacesContext.getCurrentInstance();
		D dto = (D) context.getExternalContext().getSessionMap().remove(getScreen() + "_DTO_CACHE");
		return dto;
	}

	/**
	 * Get parameter of page
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParam(String key) {
		HashMap<String, Object> paramPage = JSFUtils.findBean("paramPage");
		T value = (T) paramPage.remove(key);
		if (value == null) {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			value = (T) params.get(key);
		}
		return value;
	}

	/**
	 * Pass parameter to next page
	 * 
	 * @param key
	 * @param value
	 */
	public <T> void putParam(String key, T value) {
		HashMap<String, Object> paramPage = JSFUtils.findBean("paramPage");
		paramPage.put(key, value);
	}

	/**
	 * Put dto to cache
	 * 
	 * @throws Exception
	 */
	public void putCacheDto() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(getScreen() + "_DTO_CACHE", getDto());
	}

	public <E> E findEntity(Class<E> entityClass, Object primaryKey) {
		E entity = null;
		if (isRemote) {
			entity = remote.findEntity(entityClass, primaryKey);
		} else {
			entity = local.findEntity(entityClass, primaryKey);
		}
		return entity;
	}

	/**
	 * Call service
	 * 
	 * @param serviceName
	 * @param dtos
	 * @throws Exception
	 */
	public void doService(String serviceName, Dto dto) throws Exception {
		long t1 = System.currentTimeMillis();
		Dto serviceResult = null;
		dto.setPin(pin);
		if (isRemote) {
			serviceResult = (Dto) remote.doService(loginInfo, serviceName, dto);
		} else {
			serviceResult = (Dto) local.doService(loginInfo, serviceName, dto);
		}

		// Process service result
		if (isRemote) {
			if (serviceResult != null) {
				BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
				beanUtilsBean.getConvertUtils().register(false, true, -1);
				BeanUtils.copyProperties(dto, serviceResult);
			}
		}
		messageDto = serviceResult.getMessageDto();
		long t2 = System.currentTimeMillis();
		System.out.println("doService: " + serviceName + " in " + (t2 - t1) + " ms");
	}

	public String doAction() throws Exception {
		messageDto.clearMessages();
		callViewScript("refreshViewHandler();");
		if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("osCallViewScript");
		}
		return null;
	}

	/**
	 * Handle action from view
	 * 
	 * @param actionName
	 * @return
	 * @throws Exception
	 */
	public String doAction(String actionName) throws Exception {
		long t1 = System.currentTimeMillis();
		messageDto.clearMessages();
		Method actionMethod = null;
		String ret = null;
		try {
			try {
				actionMethod = this.getClass().getMethod(actionName);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw e;
			}

			if (actionMethod != null) {
				ret = (String) actionMethod.invoke(this);
			}
		} catch (Exception e) {
			e = processException(e);
			if (e != null) {
				throw e;
			}
		}
		long t2 = System.currentTimeMillis();
		int length = FacesContext.getCurrentInstance().getExternalContext().getRequestContentLength();
		System.out.println(
				"doAction: " + actionName + " in " + (t2 - t1) + " ms. Request Content Length: " + length + " byte");

		callViewScript("refreshViewHandler();");
		if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("osCallViewScript");
		}
		return ret;
	}

	private Exception processException(Exception e) {
		e.printStackTrace();

		Exception exp = null;
		Throwable throwable = null;
		if (e instanceof InvocationTargetException) {
			throwable = ((InvocationTargetException) e).getTargetException();
			if (throwable instanceof Exception) {
				e = (Exception) throwable;
			} else {
				e = new Exception(throwable.getMessage(), throwable);
			}
		}

		if (throwable instanceof EJBException) {
			throwable = throwable.getCause();
			if (throwable instanceof OptimisticLockException) {
				messageDto.showError(Lib.getMessage(locale, "EM9903"));
			} else if (throwable instanceof EntityExistsException) {
				messageDto.showError(Lib.getMessage(locale, "EM9901", "-803"));
			} else {
				SQLException sqlException = checkSQLException(throwable);
				if (sqlException != null) {
					messageDto.showFatal(getMessageSQLException(sqlException));
				} else {
					Throwable t = throwable;
					while (t != null) {
						messageDto.showFatal("Cause: " + t + ". Message: " + t.getMessage());
						t = t.getCause();
					}
				}
			}
		} else if (throwable instanceof PersistenceException) {
			if (throwable instanceof RollbackException) {
				throwable = throwable.getCause();
			}

			if (throwable instanceof OptimisticLockException) {
				messageDto.showError(Lib.getMessage(locale, "EM9903"));
			} else if (throwable instanceof EntityExistsException) {
				messageDto.showError(Lib.getMessage(locale, "EM9901", "-803"));
			} else {
				SQLException sqlException = checkSQLException(throwable);
				if (sqlException != null) {
					messageDto.showFatal(getMessageSQLException(sqlException));
				} else {
					Throwable t = throwable;
					while (t != null) {
						messageDto.showFatal("Cause: " + t + ". Message: " + t.getMessage());
						t = t.getCause();
					}
				}
			}
		} else {
			SQLException sqlException = checkSQLException(throwable);
			if (sqlException != null) {
				messageDto.showFatal(getMessageSQLException(sqlException));
			} else {
				Throwable t = throwable;
				while (t != null) {
					messageDto.showFatal("Cause: " + t + ". Message: " + t.getMessage());
					t = t.getCause();
				}
			}
		}

		return exp;
	}

	/**
	 * Get SQLException if exist.
	 * 
	 * @param t
	 * @return
	 */
	private SQLException checkSQLException(Throwable t) {
		Throwable temp = t;
		while (temp != null) {
			if (temp instanceof SQLException) {
				return (SQLException) temp;
			}
			temp = temp.getCause();
		}
		return null;
	}

	/**
	 * Get message SQLException
	 * 
	 * @param ex
	 * @return
	 */
	private String getMessageSQLException(SQLException ex) {
		String message = "";
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				String sqlState = ((SQLException) e).getSQLState();
				int errorCode = ((SQLException) e).getErrorCode();
				if (!ignoreSQLException(sqlState)) {
					if (sqlState == null) {
						message = "The SQL state is not defined!";
					} else {
						if (errorCode == -803) {
							// Duplicate PK
							message = Lib.getMessage(locale, "EM9901", errorCode);
						} else if (errorCode == 100) {
							// No row data for delete
							message = Lib.getMessage(locale, "EM9902", errorCode);
						} else {
							// Other error
							message = Lib.getMessage(locale, "EM9900", sqlState, errorCode, e.getMessage());
						}
						break;
					}
				}
			}
		}
		return message;
	}

	/**
	 * Check SQLException
	 * 
	 * @param sqlState
	 * @return
	 */
	private static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			return false;
		}

		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32")) {
			return true;
		}

		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55")) {
			return true;
		}

		return false;
	}

	public void addRenderId(String... ids) {
		PartialViewContext pvc = FacesContext.getCurrentInstance().getPartialViewContext();
		if (pvc.isAjaxRequest() && ids != null && ids.length > 0) {
			pvc.getRenderIds().addAll(Arrays.asList(ids));
		}
	}

	/**
	 * Call javascript from server
	 * 
	 * @param script
	 */
	public void callViewScript(String script) {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		String viewScript = (String) requestMap.get("callViewScript");
		viewScript = viewScript == null ? script : viewScript + (script == null ? "" : script);
		requestMap.put("callViewScript", viewScript);
	}

	public String i18n(String key, String... args) {
		return i18n(locale, key, args);
	}

	public String i18n(Locale locale, String key, String... args) {
		String[] keys = key.split("\\.");
		return i18n(locale, keys, args);
	}

	public HashMap<String, String> i18nMap(String key1, String key2) {
		return i18nMap(locale, key1, key2);
	}

	public HashMap<String, String> i18nMap(Locale locale, String key1, String key2) {
		String lang = locale != null ? locale.toString() : this.locale.toString();
		HashMap<String, String> value = new HashMap<String, String>();
		HashMap<String, HashMap<String, HashMap<String, String>>> res1 = _i18n.get(lang);
		HashMap<String, HashMap<String, String>> res2 = res1 != null ? res1.get(key1) : null;
		value = res2 != null ? res2.get(key2) : new HashMap<String, String>();
		return value;
	}

	private static HashMap<String, MessageFormat> formats = new HashMap<String, MessageFormat>();

	public String i18n(String[] key, String... args) {
		return i18n(locale, key, args);
	}

	public String i18n(Locale locale, String[] key, String... args) {
		String value = null;
		HashMap<String, HashMap<String, HashMap<String, String>>> res1 = null;
		HashMap<String, HashMap<String, String>> res2 = null;
		HashMap<String, String> res3 = null;

		locale = locale != null ? locale : this.locale;
		String lang = locale.toString();
		res1 = _i18n.get(lang);
		res2 = res1 != null ? res1.get(key[0]) : null;
		res3 = res2 != null ? res2.get(key[1]) : null;
		value = res3 != null ? res3.get(key[2]) : null;

		if (value != null && args != null) {
			MessageFormat format = null;
			String formatKey = locale.toString() + "." + key[0] + "." + key[1] + "." + key[2];
			synchronized (formats) {
				format = formats.get(formatKey);
				if (format == null) {
					format = new MessageFormat(escape(value));
					format.setLocale(locale);
					formats.put(formatKey, format);
				}
			}
			value = format.format(args);
		}
		return value;
	}

	/**
	 * Escape any single quote characters that are included in the specified
	 * message string.
	 * 
	 * @param string
	 *            The string to be escaped
	 */
	private String escape(String string) {
		if ((string == null) || (string.indexOf('\'') < 0)) {
			return string;
		}

		int n = string.length();
		StringBuffer sb = new StringBuffer(n);

		for (int i = 0; i < n; i++) {
			char ch = string.charAt(i);

			if (ch == '\'') {
				sb.append('\'');
			}

			sb.append(ch);
		}

		return sb.toString();
	}

	public static void setI18N(HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> i18n) {
		_i18n = i18n;
		JSFUtils.setBean("i18n", i18n);
		LoginInfo loginInfo = JSFUtils.findBean("loginInfo");
		String lang = loginInfo.getLocale().toString();
		HashMap<String, HashMap<String, HashMap<String, String>>> langMap = i18n.get(lang);
		if (langMap != null) {
			JSFUtils.setBean("common", langMap.get("common"));
			JSFUtils.setBean("acc", langMap.get("acc"));
			JSFUtils.setBean("inv", langMap.get("inv"));
			JSFUtils.setBean("img", langMap.get("img"));
			JSFUtils.setBean("msg", langMap.get("msg"));
			JSFUtils.setBean("adm", langMap.get("adm"));
		}
	}
}
