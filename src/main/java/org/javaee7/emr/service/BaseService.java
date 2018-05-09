package org.javaee7.emr.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.javaee7.emr.client.ParamQuery;
import org.javaee7.emr.dto.Dto;
import org.javaee7.emr.dto.LoginInfo;
import org.javaee7.emr.dto.MessageDto;
import org.javaee7.emr.entities.LanguageResources;

public class BaseService extends DAService {
	protected MessageDto messageDto = new MessageDto();

	protected static HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> _i18n;
	protected static HashMap<Long, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>> _i18nCom;

	public BaseService() {
		super();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Dto doService(Object inLoginInfo, String serviceName, Object inDto) throws Exception {
		System.out.println(this.getClass().getSimpleName() + ": " + serviceName + "\n");
		LoginInfo loginInfo = (LoginInfo) inLoginInfo;
		Dto dto = (Dto) inDto;

		messageDto = new MessageDto();
		Method serviceMethod = null;
		try {
			setLoginInfo(loginInfo);

			Class<?> parameterType = null;
			if (dto != null) {
				// TODO
				// this.businessCode = dto.getBusinessCode();
				// this.businessName = dto.getBusinessName();
				this.serviceName = serviceName;

				parameterType = dto.getClass();
			}

			try {
				if (parameterType != null) {
					serviceMethod = this.getClass().getMethod(serviceName, parameterType);
				} else {
					serviceMethod = this.getClass().getMethod(serviceName);
				}
			} catch (NoSuchMethodException e) {
				throw e;
			}

			if (serviceMethod != null) {
				serviceMethod.invoke(this, dto);
				dto.setMessageDto(messageDto);
			}
		} catch (Exception e) {
			Throwable throwable = null;
			Exception f = null;
			if (e instanceof InvocationTargetException) {
				throwable = ((InvocationTargetException) e).getTargetException();
				if (throwable instanceof Exception) {
					f = (Exception) throwable;
				} else {
					f = new Exception(throwable.getMessage(), throwable);
				}
			} else {
				f = e;
			}
			throw f;
		}

		return dto;
	}

	public void loadResoures() {
		loadResoures(false);
	}

	public void loadResoures(boolean force) {
		if (!force && _i18n != null && !_i18n.isEmpty()) {
			return;
		}
		List<LanguageResources> list = getLanguageResources();
		if (list != null && !list.isEmpty()) {
			_i18n = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>();
			_i18nCom = new HashMap<Long, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>>();

			Long companyId = null;
			Long companyCur = null;
			String lang = null;
			String langCur = null;
			String category = null;
			String categoryCur = null;
			String type = null;
			String typeCur = null;

			HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> langMap = null;
			HashMap<String, HashMap<String, HashMap<String, String>>> catMap = null;
			HashMap<String, HashMap<String, String>> typeMap = null;
			HashMap<String, String> resMap = null;

			for (LanguageResources e : list) {
				companyId = e.getCompanyId();
				lang = e.getLanguage();
				category = e.getCategory();
				type = e.getTypeGroup();

				if (companyId.equals(companyCur)) {
					if (lang.equals(langCur)) {
						if (category.equals(categoryCur)) {
							if (type.equals(typeCur)) {
								resMap.put(e.getKey(), e.getValue());
							} else {
								typeCur = type;
								resMap = new HashMap<String, String>();
								resMap.put(e.getKey(), e.getValue());
								typeMap.put(type, resMap);
							}
						} else {
							categoryCur = category;
							typeCur = type;

							typeMap = new HashMap<String, HashMap<String, String>>();
							resMap = new HashMap<String, String>();

							resMap.put(e.getKey(), e.getValue());
							typeMap.put(type, resMap);
							catMap.put(category, typeMap);
						}
					} else {
						langCur = lang;
						categoryCur = category;
						typeCur = type;

						catMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
						typeMap = new HashMap<String, HashMap<String, String>>();
						resMap = new HashMap<String, String>();

						resMap.put(e.getKey(), e.getValue());
						typeMap.put(type, resMap);
						catMap.put(category, typeMap);
						langMap.put(lang, catMap);
					}
				} else {
					companyCur = companyId;
					langCur = lang;
					categoryCur = category;
					typeCur = type;

					langMap = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>();
					catMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
					typeMap = new HashMap<String, HashMap<String, String>>();
					resMap = new HashMap<String, String>();

					resMap.put(e.getKey(), e.getValue());
					typeMap.put(type, resMap);
					catMap.put(category, typeMap);
					langMap.put(lang, catMap);
					_i18nCom.put(companyId, langMap);
				}
			}

			// Get standard resource
			_i18n = _i18nCom.remove(0L);
		}
	}

	public List<LanguageResources> getLanguageResources() {
		ParamQuery paramQuery = new ParamQuery();
		paramQuery.setName("LanguageResources.findAll");
		List<LanguageResources> list = getEntities(paramQuery);
		return list;
	}

	/**
	 * Get i18n
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
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
		HashMap<String, String> tmp = new HashMap<String, String>();
		HashMap<String, HashMap<String, HashMap<String, String>>> res1 = _i18n.get(lang);
		HashMap<String, HashMap<String, String>> res2 = res1 != null ? res1.get(key1) : null;
		tmp = res2 != null ? res2.get(key2) : null;

		// Clone map object
		HashMap<String, String> value = new HashMap<String, String>();
		value.putAll(tmp);

		if (loginInfo != null && loginInfo.getCompanyId() != null) {
			HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> res = null;
			HashMap<String, String> map = null;
			res = _i18nCom.get(loginInfo.getCompanyId());
			res1 = res != null ? res.get(lang) : null;
			res2 = res1 != null ? res1.get(key1) : null;
			map = res2 != null ? res2.get(key2) : null;

			value = value == null ? new HashMap<String, String>() : value;
			if (map != null) {
				value.putAll(map);
			}
		}

		return value;
	}

	/**
	 * Clone i18n for company
	 * 
	 * @param companyId
	 * @return
	 */
	public HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> getI18N(Long companyId) {
		loadResoures();

		HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> i18n = null;
		i18n = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>();
		// Clone object
		Iterator<String> iteratorLang = null;
		Iterator<String> iteratorCat = null;
		Iterator<String> iteratorType = null;
		Iterator<String> iteratorKey = null;
		HashMap<String, HashMap<String, HashMap<String, String>>> catMap = null;
		HashMap<String, HashMap<String, String>> typeMap = null;
		HashMap<String, String> keyMap = null;

		HashMap<String, HashMap<String, HashMap<String, String>>> catClone = null;
		HashMap<String, HashMap<String, String>> typeClone = null;
		HashMap<String, String> keyClone = null;

		String keyLang = null;
		String keyCat = null;
		String keyType = null;
		String keyKey = null;
		iteratorLang = _i18n.keySet().iterator();
		while (iteratorLang.hasNext()) {
			keyLang = iteratorLang.next();
			catMap = _i18n.get(keyLang);

			catClone = new HashMap<String, HashMap<String, HashMap<String, String>>>();
			if (catMap != null) {
				iteratorCat = catMap.keySet().iterator();
				while (iteratorCat.hasNext()) {
					keyCat = iteratorCat.next();
					typeMap = catMap.get(keyCat);

					typeClone = new HashMap<String, HashMap<String, String>>();
					if (typeMap != null) {
						iteratorType = typeMap.keySet().iterator();
						while (iteratorType.hasNext()) {
							keyType = iteratorType.next();
							keyMap = typeMap.get(keyType);

							keyClone = new HashMap<String, String>();
							if (keyMap != null) {
								iteratorKey = keyMap.keySet().iterator();
								while (iteratorKey.hasNext()) {
									keyKey = iteratorKey.next();
									keyClone.put(keyKey, keyMap.get(keyKey));
								}
							}
							typeClone.put(keyType, keyClone);
						}
					}
					catClone.put(keyCat, typeClone);
				}
			}
			i18n.put(keyLang, catClone);
		}

		// Override for company
		HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> i18nCom = _i18nCom.get(companyId);
		if (i18nCom != null) {
			iteratorLang = i18nCom.keySet().iterator();
			while (iteratorLang.hasNext()) {
				keyLang = iteratorLang.next();
				catMap = i18nCom.get(keyLang);

				catClone = i18n.get(keyLang);
				if (catMap != null) {
					iteratorCat = catMap.keySet().iterator();
					while (iteratorCat.hasNext()) {
						keyCat = iteratorCat.next();
						typeMap = catMap.get(keyCat);

						typeClone = catClone.get(keyCat);
						if (typeMap != null) {
							iteratorType = typeMap.keySet().iterator();
							while (iteratorType.hasNext()) {
								keyType = iteratorType.next();
								keyMap = typeMap.get(keyType);

								keyClone = typeClone.get(keyType);
								if (keyMap != null) {
									keyClone.putAll(keyMap);
								}
							}
						}
					}
				}
			}
		}
		return i18n;
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
		if (loginInfo != null && loginInfo.getCompanyId() != null) {
			HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> res = null;
			res = _i18nCom.get(loginInfo.getCompanyId());
			res1 = res != null ? res.get(lang) : null;
			res2 = res1 != null ? res1.get(key[0]) : null;
			res3 = res2 != null ? res2.get(key[1]) : null;
			value = res3 != null ? res3.get(key[2]) : null;
		}

		if (value == null) {
			res1 = _i18n.get(lang);
			res2 = res1 != null ? res1.get(key[0]) : null;
			res3 = res2 != null ? res2.get(key[1]) : null;
			value = res3 != null ? res3.get(key[2]) : null;
		}

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
}
