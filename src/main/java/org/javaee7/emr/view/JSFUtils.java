package org.javaee7.emr.view;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtils {
	@SuppressWarnings({ "unchecked", "el-syntax" })
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) {
			return null;
		}
		return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

	@SuppressWarnings("el-syntax")
	public static void setBean(String beanName, Object newValue) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) {
			return;
		}
		Application app = context.getApplication();
		ExpressionFactory elFactory = app.getExpressionFactory();
		ELContext elContext = context.getELContext();
		ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + beanName + "}", Object.class);

		// Check that the input newValue can be cast to the property type
		// expected by the managed bean.
		// Rely on Auto-Unboxing if the managed Bean expects a primitive
		Class<?> bindClass = valueExp.getType(elContext);
		if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
			valueExp.setValue(elContext, newValue);
		}
	}

	/**
	 * 
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 */
	public static String addRequestParameter(String url, String key, String value) {
		if (url == null || "".equals(url) || key == null || "".equals(key) || value == null || "".equals(value)) {
			return url;
		}
		String kvp = key + "=" + value;
		String regex = "(&|\\?)" + key + "=[^\\&]*";
		String[] arrUrl = url.split("\\?");
		if (arrUrl.length > 1) {
			if (arrUrl[1].trim().length() > 0) {
				if (arrUrl[1].contains(key + "=")) {
					url = url.replaceAll(regex, "$1" + kvp);
				} else {
					url += "&" + kvp;
				}
			} else {
				url = arrUrl[0] + "?" + kvp;
			}
		} else {
			url = arrUrl[0] + "?" + kvp;
		}
		return url;
	}

	/**
	 * Get URL
	 * 
	 * @return
	 */
	public static String getRequestURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getRequestURL().toString();
		} else {
			return "";
		}
	}

	/**
	 * Get URL
	 * 
	 * @return
	 */
	public static String getRootURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;

			StringBuffer buf = new StringBuffer();
			buf.append(req.getScheme());
			buf.append("://");
			buf.append(req.getServerName());
			buf.append(':');
			buf.append(req.getServerPort());
			buf.append(req.getContextPath());
			buf.append(req.getServletPath());
			return buf.toString();
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * Return the {@link UIComponent}(if any) with the specified <code>id</code>
	 * , searching recursively starting at the specified <code>base</code>, and
	 * examining the base component itself, followed by examining all the base
	 * component's facets and children. Unlike findComponent method of
	 * UIComponent, which skips recursive scan each time it finds a
	 * NamingContainer, this method examines all components, regardless of their
	 * namespace (assuming IDs are unique).
	 * 
	 * @param base
	 *            Base {@link UIComponent}from which to search
	 * @param id
	 *            Component identifier to be matched
	 */
	public static UIComponent findComponent(UIComponent base, String id) {
		// Is the "base" component itself the match we are looking for?
		if (base == null) {
			return base;
		}
		return base.findComponent(id);
	}

	/**
	 * Find component by id
	 * 
	 * @param id
	 * @return
	 */
	public static UIComponent findComponent(String id) {
		UIComponent ret = null;
		if (id == null || "".equals(id)) {
			return ret;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = root.findComponent(id);
		}
		return ret;
	}
}
