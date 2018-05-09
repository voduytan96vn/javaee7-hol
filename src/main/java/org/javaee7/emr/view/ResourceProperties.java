package org.javaee7.emr.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ResourceProperties implements Serializable {
	private static final long serialVersionUID = 7073379628989733167L;

	/**
	 * The configuration parameter used to initialize this MessageResources.
	 */
	private String config = null;

	/**
	 * The default Locale for our environment.
	 */
	private Locale defaultLocale = Locale.getDefault();

	/**
	 * The set of previously created MessageFormat objects, keyed by the key
	 * computed in <code>messageKey()</code>.
	 */
	private HashMap<String, MessageFormat> formats = new HashMap<String, MessageFormat>();

	/**
	 * The set of locale keys for which we have already loaded messages, keyed
	 * by the value calculated in <code>localeKey()</code>.
	 */
	private HashMap<String, String> locales = new HashMap<String, String>();

	/**
	 * The cache of messages we have accumulated over time, keyed by the value
	 * calculated in <code>messageKey()</code>.
	 */
	private HashMap<String, String> messages = new HashMap<String, String>();

	/**
	 * Indicate is a <code>null</code> is returned instead of an error message
	 * string when an unknown Locale or key is requested.
	 */
	private boolean returnNull = false;
	private Properties properties;

	private static ResourceProperties applicationResources;
	private static ResourceProperties messageResources;
	private static ResourceProperties setting;
	private static ResourceProperties loggingResources;
	private static ResourceProperties paramResources;

	public static synchronized ResourceProperties getApplicationResources() throws Exception {
		if (applicationResources == null) {
			applicationResources = new ResourceProperties("ApplicationResources", true);
		}
		return applicationResources;
	}


	public static synchronized ResourceProperties getMessageResources() throws Exception {
		if (messageResources == null) {
			messageResources = new ResourceProperties("MessageResources", true);
		}
		return messageResources;
	}


	public static synchronized ResourceProperties getLoggingResources() throws Exception {
		if (loggingResources == null) {
			loggingResources = new ResourceProperties("LoggingResources", true);
		}
		return loggingResources;
	}


	public static synchronized ResourceProperties getSettingResources() throws Exception {
		if (setting == null) {
			setting = new ResourceProperties("Setting", true);
		}
		return setting;
	}

	public static synchronized ResourceProperties getParamResources() throws Exception {
		if (paramResources == null) {
			paramResources = new ResourceProperties("ParamResources", true);
		}
		return paramResources;
	}


	public ResourceProperties(String config) throws Exception {
		this(config, false);
	}

	public ResourceProperties(String config, boolean returnNull) throws Exception {
		super();
		this.config = config;
		this.returnNull = returnNull;
		properties = new Properties();
		InputStream s = getClass().getClassLoader().getResourceAsStream(config + ".properties");
		if (s == null) {
			throw new Exception(config + " is not exist.");
		}
		// Reader reader = new InputStreamReader(s, Constant.ENCODING);
		properties.load(s);
	}

	public Map<?, ?> getProperties() {
		return properties;
	}


	protected String escape(String string) {
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


	public String getMessage(Locale locale, String key) throws Exception {
		// Initialize variables we will require
		String localeKey = localeKey(locale);
		String originalKey = messageKey(localeKey, key);
		String messageKey = null;
		String message = null;
		int underscore = 0;
		boolean addIt = false; // Add if not found under the original key

		// Loop from specific to general Locales looking for this message
		while (true) {
			// Load this Locale's messages if we have not done so yet
			loadLocale(localeKey);

			// Check if we have this key for the current locale key
			messageKey = messageKey(localeKey, key);
			synchronized (messages) {
				message = messages.get(messageKey);
				if (message != null) {
					if (addIt) {
						messages.put(originalKey, message);
					}
					return (message);
				}
			}

			// Strip trailing modifiers to try a more general locale key
			addIt = true;
			underscore = localeKey.lastIndexOf("_");
			if (underscore < 0) {
				break;
			}
			localeKey = localeKey.substring(0, underscore);
		}

		// Try the default locale if the current locale is different
		if (!defaultLocale.equals(locale)) {
			localeKey = localeKey(defaultLocale);
			messageKey = messageKey(localeKey, key);
			loadLocale(localeKey);
			synchronized (messages) {
				message = messages.get(messageKey);
				if (message != null) {
					messages.put(originalKey, message);
					return (message);
				}
			}
		}

		// As a last resort, try the default Locale
		localeKey = "";
		messageKey = messageKey(localeKey, key);
		loadLocale(localeKey);
		synchronized (messages) {
			message = messages.get(messageKey);
			if (message != null) {
				messages.put(originalKey, message);
				return (message);
			}
		}

		// Return an appropriate error indication
		if (returnNull) {
			return (null);
		}
		return ("???" + messageKey(locale, key) + "???");
	}

	public String getMessage(Locale locale, String key, Object... args) throws Exception {
		// Cache MessageFormat instances as they are accessed
		if (locale == null) {
			locale = defaultLocale;
		}

		MessageFormat format = null;
		String formatKey = messageKey(locale, key);

		synchronized (formats) {
			format = formats.get(formatKey);
			if (format == null) {
				String formatString = getMessage(locale, key);

				if (formatString == null) {
					return returnNull ? "" : ("???" + formatKey + "???");
				}

				format = new MessageFormat(escape(formatString));
				format.setLocale(locale);
				formats.put(formatKey, format);
			}
		}
		return format.format(args);
	}

	public String getMessage(String key) throws Exception {
		return this.getMessage((Locale) null, key);
	}

	public String getMessage(String key, Object... args) throws Exception {
		return this.getMessage((Locale) null, key, args);
	}

	protected synchronized void loadLocale(String localeKey) throws Exception {
		// Have we already attempted to load messages for this locale?
		if (locales.get(localeKey) != null) {
			return;
		}

		locales.put(localeKey, localeKey);

		// Set up to load the property resource for this locale key, if we can
		String name = config.replace('.', '/');
		if (localeKey.length() > 0) {
			name += "_" + localeKey;
		}

		name += ".properties";
		InputStream is = null;
		Properties props = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}

		is = classLoader.getResourceAsStream(name);
		if (is != null) {
			try {
				props.load(is);

			} catch (IOException e) {
				throw e;
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}

		// Copy the corresponding values into our cache
		if (props.size() < 1) {
			return;
		}

		synchronized (messages) {
			Iterator<?> names = props.keySet().iterator();
			while (names.hasNext()) {
				String key = (String) names.next();
				messages.put(messageKey(localeKey, key), props.getProperty(key));
			}
		}
	}


	protected String localeKey(Locale locale) {
		return (locale == null) ? "" : locale.toString();
	}

	protected String messageKey(Locale locale, String key) {
		return (localeKey(locale) + "." + key);
	}

	protected String messageKey(String localeKey, String key) {
		return (localeKey + "." + key);
	}
}
