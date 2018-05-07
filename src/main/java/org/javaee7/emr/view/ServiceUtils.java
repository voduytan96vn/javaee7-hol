package org.javaee7.emr.view;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.javaee7.emr.view.Lib;

public class ServiceUtils {
	public static Object getService(String serviceName, Class<?> serviceClass) {
		if (serviceClass == null) {
			return null;
		}

		Object service = null;
		String className = serviceClass.getName();
		service = getService(serviceName, className);
		return service;
	}

	/**
	 * Get service ejb
	 * 
	 * @param serviceName
	 * @param className
	 * @return
	 */
	public static Object getService(String serviceName, String className) {
		try {
			Context context = getInitialContext();
			// String beanName = Lib.getSetting("ejb.bean.name", serviceName,
			// className);
			String beanName = Lib.getSetting("ejb.bean.name", serviceName, className);
			return context.lookup(beanName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Changed for throw exception. Relay NamingException by Exception.
	 * 
	 * @return
	 */
	private static Context getInitialContext() throws NamingException {
		Hashtable<?, ?> env = getEnvironment();
		return new InitialContext(env);
	}

	/**
	 * Get Environment for Context
	 * 
	 * @return
	 */
	private static Hashtable<String, Object> getEnvironment() {
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		try {
			// EJB Server connection details
			String providerUrl = Lib.getSetting("ejb.provider.url");
			String factory = Lib.getSetting("ejb.context.factory");
			String packageUrl = Lib.getSetting("ejb.url.pkg.prefixes");
			String authoritative = Lib.getSetting("ejb.authoritative");
			String username = Lib.getSetting("ejb.security.principal");
			String password = Lib.getSetting("ejb.security.credentials");

			// env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
			// env.put(Context.PROVIDER_URL, providerUrl);
			// env.put(Context.URL_PKG_PREFIXES, packageUrl);
			// env.put(Context.AUTHORITATIVE, authoritative);
			// env.put(Context.SECURITY_PRINCIPAL, username);
			// env.put(Context.SECURITY_CREDENTIALS, password);

			if ("JBoss".equals(Lib.getSetting("ejb.target.server"))) {
				// if ("true".equals(Lib.getSetting("ejb.app.remote"))) {
				env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
				env.put(Context.PROVIDER_URL, providerUrl);
				env.put(Context.URL_PKG_PREFIXES, packageUrl);
				env.put(Context.AUTHORITATIVE, authoritative);
				env.put(Context.SECURITY_PRINCIPAL, username);
				env.put(Context.SECURITY_CREDENTIALS, password);
				env.put("jboss.naming.client.ejb.context", "true");
				// }
				// env.put("jboss.naming.client.ejb.context", "true");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return env;
	}
}
