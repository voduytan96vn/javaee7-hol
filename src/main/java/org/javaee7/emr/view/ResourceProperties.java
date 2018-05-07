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

	/**
	 * 「ApplicationResources」Application resource getter
	 * 
	 * @return
	 */
	public static synchronized ResourceProperties getApplicationResources() throws Exception {
		if (applicationResources == null) {
			applicationResources = new ResourceProperties("ApplicationResources", true);
		}
		return applicationResources;
	}

	/**
	 * 「MessageResources」Message resource getter
	 * 
	 * @return
	 */
	public static synchronized ResourceProperties getMessageResources() throws Exception {
		if (messageResources == null) {
			messageResources = new ResourceProperties("MessageResources", true);
		}
		return messageResources;
	}

	/**
	 * 「LoggingResources」Logging resource getter
	 * 
	 * @return
	 */
	public static synchronized ResourceProperties getLoggingResources() throws Exception {
		if (loggingResources == null) {
			loggingResources = new ResourceProperties("LoggingResources", true);
		}
		return loggingResources;
	}

	/**
	 * 「Setting」Setting resource getter
	 * 
	 * @return
	 */
	public static synchronized ResourceProperties getSettingResources() throws Exception {
		if (setting == null) {
			setting = new ResourceProperties("Setting", true);
		}
		return setting;
	}

	/**
	 * 「ParamResources」Param resource getter
	 * 
	 * @return
	 */
	public static synchronized ResourceProperties getParamResources() throws Exception {
		if (paramResources == null) {
			paramResources = new ResourceProperties("ParamResources", true);
		}
		return paramResources;
	}

	/**
	 * Construct a new MessageResources according to the specified parameters.
	 * 
	 * @param config
	 *            The configuration parameter for this MessageResources
	 */
	public ResourceProperties(String config) throws Exception {
		this(config, false);
	}

	/**
	 * Construct a new MessageResources according to the specified parameters.
	 * 
	 * @param config
	 *            The configuration parameter for this MessageResources
	 * @param returnNull
	 *            The returnNull property we should initialize with
	 */
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

	/**
	 * Escape any single quote characters that are included in the specified
	 * message string.
	 * 
	 * @param string
	 *            The string to be escaped
	 */
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

	/**
	 * Returns a text message for the specified key, for the default Locale. A
	 * null string result will be returned by this method if no relevant message
	 * resource is found for this key or Locale, if the <code>returnNull</code>
	 * property is set. Otherwise, an appropriate error message will be
	 * returned.
	 * <p>
	 * This method must be implemented by a concrete subclass.
	 * 
	 * @param locale
	 *            The requested message Locale, or <code>null</code> for the
	 *            system default Locale
	 * @param key
	 *            The message key to look up
	 */
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

	/**
	 * Returns a text message after parametric replacement of the specified
	 * parameter placeholders. A null string result will be returned by this
	 * method if no resource bundle has been configured.
	 * 
	 * @param locale
	 *            The requested message Locale, or <code>null</code> for the
	 *            system default Locale
	 * @param key
	 *            The message key to look up
	 * @param args
	 *            An array of replacement parameters for placeholders
	 */
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

	/**
	 * Returns a text message for the specified key, for the default Locale.
	 * 
	 * @param key
	 *            The message key to look up
	 */
	public String getMessage(String key) throws Exception {
		return this.getMessage((Locale) null, key);
	}

	/**
	 * Returns a text message after parametric replacement of the specified
	 * parameter placeholder.
	 * 
	 * @param key
	 *            The message key to look up
	 * @param args
	 *            An array of replacement parameters for placeholders
	 */
	public String getMessage(String key, Object... args) throws Exception {
		return this.getMessage((Locale) null, key, args);
	}

	/**
	 * Load the messages associated with the specified Locale key. For this
	 * implementation, the <code>config</code> property should contain a fully
	 * qualified package and resource name, separated by periods, of a series of
	 * property resources to be loaded from the class loader that created this
	 * PropertyMessageResources applicationResources. This is exactly the same
	 * name format you would use when utilizing the
	 * <code>java.util.PropertyResourceBundle</code> class.
	 * 
	 * @param localeKey
	 *            Locale key for the messages to be retrieved
	 */
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

	/**
	 * Compute and return a key to be used in caching information by a Locale.
	 * <strong>NOTE </strong>- The locale key for the default Locale in our
	 * environment is a zero length String.
	 * 
	 * @param locale
	 *            The locale for which a key is desired
	 */
	protected String localeKey(Locale locale) {
		return (locale == null) ? "" : locale.toString();
	}

	/**
	 * Compute and return a key to be used in caching information by Locale and
	 * message key.
	 * 
	 * @param locale
	 *            The Locale for which this format key is calculated
	 * @param key
	 *            The message key for which this format key is calculated
	 */
	protected String messageKey(Locale locale, String key) {
		return (localeKey(locale) + "." + key);
	}

	/**
	 * Compute and return a key to be used in caching information by locale key
	 * and message key.
	 * 
	 * @param localeKey
	 *            The locale key for which this cache key is calculated
	 * @param key
	 *            The message key for which this cache key is calculated
	 */
	protected String messageKey(String localeKey, String key) {
		return (localeKey + "." + key);
	}
}
