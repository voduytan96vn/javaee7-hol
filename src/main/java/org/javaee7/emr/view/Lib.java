/**
 * 
 */
/**
 * @author voduy
 *
 */
package org.javaee7.emr.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.javaee7.emr.common.Constant;
import org.javaee7.emr.common.CurrencyUtil;

public class Lib {
	public static final Logger log = Logger.getLogger("HUB");

	public static boolean toBoolean(Object value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(Object value, boolean defaultValue) {
		boolean ret = defaultValue;
		try {
			if (null == value) {
				ret = defaultValue;
			} else if (value instanceof Boolean) {
				ret = ((Boolean) value).booleanValue();
			} else if (value instanceof String) {
				ret = "true".equals(((String) value).toLowerCase()) || "1".equals(value);
			} else if (value instanceof Number) {
				ret = Integer.valueOf(value.toString()) > 0;
			} else {
				ret = Boolean.valueOf(value.toString());
			}
		} catch (Exception e) {
			ret = defaultValue;
		}
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Long toLong(Object value) {
		if (value == null) {
			return null;
		}
		Long ret = null;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).longValue();
			} else {
				ret = value.toString().trim().isEmpty() ? null : Long.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static Long toLong(Object value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		Long ret = defaultValue;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).longValue();
			} else {
				ret = value.toString().trim().isEmpty() ? defaultValue : Long.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = defaultValue;
		}
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Integer toInteger(Object value) {
		if (value == null) {
			return null;
		}
		Integer ret = null;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).intValue();
			} else {
				ret = value.toString().trim().isEmpty() ? null : Integer.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static Integer toInteger(Object value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		Integer ret = defaultValue;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).intValue();
			} else {
				ret = value.toString().trim().isEmpty() ? defaultValue : Integer.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = defaultValue;
		}
		return ret;
	}

	public static Integer toInteger(Object value, int defaultValue, int maxValue) {
		if (value == null) {
			return defaultValue;
		}
		Integer ret = defaultValue;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).intValue();
			} else {
				ret = value.toString().trim().isEmpty() ? defaultValue : Integer.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = defaultValue;
		}
		ret = ret > maxValue ? maxValue : ret;
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Double toDouble(Object value) {
		if (value == null) {
			return null;
		}
		Double ret = null;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).doubleValue();
			} else {
				ret = value.toString().trim().isEmpty() ? null : Double.valueOf(value.toString().trim());
			}

		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static Double toDouble(Object value, double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		Double ret = defaultValue;
		try {
			if (value instanceof Number) {
				ret = ((Number) value).doubleValue();
			} else {
				ret = value.toString().trim().isEmpty() ? defaultValue : Double.valueOf(value.toString().trim());
			}
		} catch (Exception e) {
			ret = defaultValue;
		}
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String toString(Object value) {
		if (value == null) {
			return null;
		}

		return value.toString();
	}

	public static String toString(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return value.toString();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Long toDate(Object value, TimeZone zone, Locale locale) {
		if (value == null) {
			return null;
		}

		return toDate(value, null, null, zone, locale);
	}

	public static Long toDate(Object value, long defaultValue, TimeZone zone, Locale locale) {
		return toDate(value, defaultValue, null, zone, locale);
	}

	public static Long toDate(Object value, Long defaultValue, String pattern, TimeZone zone, Locale locale) {
		if (value == null) {
			return defaultValue;
		}

		Long ret = defaultValue;
		try {
			if (value instanceof Long) {
				ret = (long) value;
			} else {
				if (pattern == null) {
					ret = str2Dt(value.toString(), zone, locale);
				} else {
					ret = str2Dt(value.toString(), pattern, zone, locale);
				}
			}
		} catch (Exception e) {
			ret = defaultValue;
		}

		return ret;
	}

	public static Long[] toDateRange(String value, TimeZone zone, Locale locale) {
		String sep = value.indexOf("~") > 0 ? "~" : value.indexOf("-") > 0 ? "-" : "~";
		return toDateRange(value, null, sep, zone, locale);
	}

	public static Long[] toDateRange(String value, Long[] defaultValue, TimeZone zone, Locale locale) {
		String sep = value.indexOf("~") > 0 ? "~" : value.indexOf("-") > 0 ? "-" : "~";
		return toDateRange(value, defaultValue, sep, zone, locale);
	}

	public static Long[] toDateRange(String value, Long startDateDef, Long endDateDef, TimeZone zone, Locale locale) {
		Long[] defaultValue = new Long[] { startDateDef, endDateDef };
		String sep = value.indexOf("~") > 0 ? "~" : value.indexOf("-") > 0 ? "-" : "~";
		return toDateRange(value, defaultValue, sep, zone, locale);
	}

	public static Long[] toDateRange(String value, String sep, TimeZone zone, Locale locale) {
		return toDateRange(value, null, sep, zone, locale);
	}

	public static Long[] toDateRange(String value, Long startDateDef, Long endDateDef, String sep, TimeZone zone,
			Locale locale) {
		Long[] defaultValue = new Long[] { startDateDef, endDateDef };
		return toDateRange(value, defaultValue, sep, zone, locale);
	}

	public static Long[] toDateRange(String value, Long[] defaultValue, String sep, TimeZone zone, Locale locale) {
		Long[] ret = defaultValue == null ? new Long[2] : defaultValue;
		if (Lib.isNotEmpty(value)) {
			sep = sep != null && sep.length() > 0 ? sep : "~";
			boolean haveSep = value.indexOf(sep) > 0;

			String[] arr = value.split(sep);
			String from = "";
			String to = "";
			if (arr.length > 1) {
				from = arr[0].trim();
				to = arr[1].trim();
			} else if (arr.length > 0) {
				from = arr[0].trim();
				if (!haveSep) {
					to = from;
				}
			}

			if (!from.isEmpty()) {
				ret[0] = Lib.getStartDate(Lib.toDate(from, zone, locale), zone, locale);
			} else {
				if (haveSep) {
					ret[0] = Lib.getMinDate(zone, locale);
				}
			}

			if (!to.isEmpty()) {
				ret[1] = Lib.getEndDate(Lib.toDate(to, zone, locale), zone, locale);
			} else {
				if (haveSep) {
					ret[1] = Lib.getMaxDate(zone, locale);
				}
			}
		}

		return ret;
	}

	/**
	 * to UpperCase
	 * 
	 * @param value
	 * @return
	 */

	public static String toUpperCase(String value) {
		if (value == null) {
			return null;
		}

		return value.toUpperCase();
	}

	/**
	 * to LowerCase
	 * 
	 * @param value
	 * @return
	 */
	public static String toLowerCase(String value) {
		if (value == null) {
			return null;
		}

		return value.toLowerCase();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Double toCurrency(String value) {
		try {
			return (null == value || value.trim().isEmpty()) ? 0D : Double.valueOf(value.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Convert number to text
	 * 
	 * @param num
	 * @param locale
	 * @return
	 */
	public static String toWords(Number num) {
		return toWords(num, null, null);
	}

	public static String toWords(Number num, String nameMajor) {
		return CurrencyUtil.toWordsVi(num, nameMajor, null);
	}

	public static String toWords(Number num, String nameMajor, String nameMinor) {
		return CurrencyUtil.toWordsVi(num, nameMajor, nameMinor);
	}

	public static String toWordsEn(Number num, String nameMajor, String nameMinor) {
		return CurrencyUtil.toWordsEn(num, nameMajor, nameMinor);
	}

	public static String dateWords(Long date, Locale locale) {
		String words = "";
		if (locale.getLanguage().equals(new Locale("vi").getLanguage())) {
			Date dt = new Date(date);
			SimpleDateFormat df = new SimpleDateFormat("dd");
			SimpleDateFormat mf = new SimpleDateFormat("MM");
			SimpleDateFormat yf = new SimpleDateFormat("yyyy");
			words = "Ngày " + df.format(dt) + " tháng " + mf.format(dt) + " năm " + yf.format(dt);
		}
		return words;
	}

	/**
	 * Join string
	 * 
	 * @param s
	 * @return
	 */
	public static String join(String... s) {
		if (s == null) {
			return "";
		}

		StringBuffer buf = new StringBuffer("");
		for (String e : s) {
			buf.append(e == null ? "" : e);
		}
		return buf.toString();
	}

	/**
	 * Sub string byte
	 * 
	 * @param src
	 * @return
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return "";
		}

		return substring(str, start, -1);
	}

	public static String substring(String str, int start, int end) {
		String ret = null;
		if (str == null) {
			return null;
		}

		int strlen = length(str);

		// handle negatives
		if (end < 0) {
			end = strlen; // remember end is negative
		}
		if (start < 0) {
			start = strlen + start; // remember start is negative
		}

		// check length next
		if (end > strlen) {
			end = strlen;
		}

		// if start is greater than end, return ""
		if (start > end) {
			return "";
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		ret = copyBytes(str, start, end, Constant.ENCODING);
		return ret;
	}

	private static String copyBytes(String source, int start, int end, String encodingName) {
		try {
			if (source != null) {
				int byteLength = end - start;
				byte[] destBytes = new byte[byteLength];
				byte[] byteChar;
				int k = 0;
				int c = 0;
				for (int i = 0; i < source.length() && k < byteLength; i++) {
					byteChar = String.valueOf(source.charAt(i)).getBytes(encodingName);
					if (c >= start) {
						if (k + byteChar.length <= byteLength) {
							for (byte b : byteChar) {
								destBytes[k++] = b;
							}
						} else {
							byte space = (byte) (' ');
							int remain = byteLength - k;
							for (int j = 0; j < remain; j++) {
								destBytes[k++] = space;
							}
						}
					} else {
						c += byteChar.length;
					}
				}
				return new String(destBytes, encodingName);
			}
		} catch (Exception e) {
			log.debug("subString() of " + source + ", from " + start + " to " + end + " fail.", e);
		}
		return source;
	}

	/**
	 * Get length byte of string
	 * 
	 * @param src
	 * @return
	 */
	public static int length(String src) {
		if (src == null) {
			return 0;
		}
		byte[] byteSrc = null;
		try {
			byteSrc = src.getBytes(Constant.ENCODING);
		} catch (UnsupportedEncodingException e) {
			byteSrc = src.getBytes();
		}
		return byteSrc.length;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (!isEmpty(str)) {
			return str.trim();
		}
		return "";
	}

	/**
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		} else {
			return substring(str, 0, len);
		}
	}

	/**
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		} else {
			return substring(str, str.length() - len);
		}
	}

	/**
	 * 
	 * @param str
	 * @param pos
	 * @param len
	 * @return
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return "";
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return substring(str, pos);
		} else {
			return substring(str, pos, pos + len);
		}
	}

	public static String leftJustify(String str, int length, char padChar) {
		if (str == null) {
			str = "";
		}
		if (length <= str.length()) {
			return str.substring(0, length);
		} else {
			int numPad = length - str.length();
			String paddStr = padding(numPad, padChar);
			return str + paddStr;
		}
	}

	public static String rightJustify(String str, int length, char padChar) {
		if (str == null) {
			str = "";
		}
		if (length <= str.length()) {
			return str.substring(str.length() - length);
		} else {
			int numPad = length - str.length();
			String paddStr = padding(numPad, padChar);
			return paddStr + str;
		}
	}

	private static final int PAD_LIMIT = 8192;

	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	/**
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * 
	 * @param str
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	/**
	 * 
	 * @param str
	 * @param size
	 * @param padStr
	 * @return
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	public static String rightPadWithJapanese(String str, int size) {
		return rightPadWithJapanese(str, size, Constant.BLANK);
	}

	public static String rightPadWithJapanese(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		int strBytes = Lib.length(str);
		if (strBytes < size) {
			int adjustSize = str.length() + (size - strBytes);
			return rightPad(str, adjustSize, padStr);
		} else {
			return str;
		}
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	public static String rightPadSoc(String str, int size) {
		if (isEmpty(str)) {
			return str;
		}

		int len = length(str);
		if (len < size) {

			if (len > 0 && (len % 2) == 1) {
				str += Constant.BLANK;
			}

			len = length(str);
			for (int i = len; i < size; i = i + 2) {
				str += Constant.BLANK_2BYTE;
			}
		}

		return str;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	/**
	 * 
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	/**
	 * 
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripStart(String str) {
		return stripStart(str, null);
	}

	/**
	 * 
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	/**
	 * 
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripEnd(String str) {
		return stripEnd(str, Constant.BLANK);
	}

	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	/**
	 * Capitalize String
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
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

	public static Date toDate(String y, String m, String d, TimeZone zone, Locale locale) {
		try {
			Integer y1 = isEmpty(y) ? null : Integer.parseInt(y);
			Integer m1 = isEmpty(m) ? null : Integer.parseInt(m);
			Integer d1 = isEmpty(d) ? null : Integer.parseInt(d);
			return toDate(y1, m1, d1, null, null, null, zone, locale);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(String y, String m, String d, String h, String mi, String s, TimeZone zone,
			Locale locale) {
		try {
			Integer y1 = isEmpty(y) ? null : Integer.parseInt(y);
			Integer m1 = isEmpty(m) ? null : Integer.parseInt(m);
			Integer d1 = isEmpty(d) ? null : Integer.parseInt(d);
			Integer h1 = isEmpty(h) ? null : Integer.parseInt(h);
			Integer mi1 = isEmpty(mi) ? null : Integer.parseInt(mi);
			Integer s1 = isEmpty(s) ? null : Integer.parseInt(s);
			return toDate(y1, m1, d1, h1, mi1, s1, zone, locale);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(Integer y, Integer m, Integer d, TimeZone zone, Locale locale) {
		return toDate(y, m, d, null, null, null, zone, locale);
	}

	public static Date toDate(Integer y, Integer m, Integer d, Integer h, Integer mi, Integer s, TimeZone zone,
			Locale locale) {
		try {

			Calendar cal = Calendar.getInstance(zone, locale);
			if (y == null || m == null || d == null || y == 0 || m == 0 || d == 0) {
				y = 9999;
				m = 11;
				d = 31;
			} else {
				m = m - 1;
				/*
				 * y = y == null ? cal.getActualMinimum(Calendar.YEAR) : y; m =
				 * m == null ? cal.getActualMinimum(Calendar.MONTH) : m - 1; d =
				 * d == null ? cal.getActualMinimum(Calendar.DATE) : d;
				 */
			}

			h = h == null ? cal.getActualMinimum(Calendar.HOUR_OF_DAY) : h;
			mi = mi == null ? cal.getActualMinimum(Calendar.MINUTE) : mi;
			s = s == null ? cal.getActualMinimum(Calendar.SECOND) : s;

			cal.set(Calendar.YEAR, y);
			cal.set(Calendar.MONTH, m);
			cal.set(Calendar.DATE, d);
			cal.set(Calendar.HOUR_OF_DAY, h);
			cal.set(Calendar.MINUTE, mi);
			cal.set(Calendar.SECOND, s);
			cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

			return cal.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get time begin of day
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date, TimeZone zone, Locale locale) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Date getEndDate(Date date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getEndDate(Long date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTimeInMillis(date);
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Get time begin of day
	 * 
	 * @param date
	 * @return
	 */
	public static Long getStartDate(Long date, TimeZone zone, Locale locale) {
		if (date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Get first day of week
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartWeek(Date date, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}
		firstDayOfWeek = firstDayOfWeek > 0 ? firstDayOfWeek : Calendar.MONDAY;
		cal.setFirstDayOfWeek(firstDayOfWeek);

		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (firstDayOfWeek != dayWeek) {
			while (true) {
				cal.add(Calendar.DATE, -1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
				if (firstDayOfWeek == dayWeek) {
					break;
				}
			}
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getStartWeek(Long date, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTimeInMillis(date);
		}
		firstDayOfWeek = firstDayOfWeek > 0 ? firstDayOfWeek : Calendar.MONDAY;
		cal.setFirstDayOfWeek(firstDayOfWeek);

		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (firstDayOfWeek != dayWeek) {
			while (true) {
				cal.add(Calendar.DATE, -1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
				if (firstDayOfWeek == dayWeek) {
					break;
				}
			}
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Date getEndWeek(Date date, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}
		firstDayOfWeek = firstDayOfWeek > 0 ? firstDayOfWeek : Calendar.MONDAY;
		cal.setFirstDayOfWeek(firstDayOfWeek);

		int endDayOfWeek = firstDayOfWeek > 1 ? (firstDayOfWeek - 1) : (firstDayOfWeek + 6);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (endDayOfWeek != dayWeek) {
			while (true) {
				cal.add(Calendar.DATE, 1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
				if (endDayOfWeek == dayWeek) {
					break;
				}
			}
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getEndWeek(Long date, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTimeInMillis(date);
		}
		firstDayOfWeek = firstDayOfWeek > 0 ? firstDayOfWeek : Calendar.MONDAY;
		cal.setFirstDayOfWeek(firstDayOfWeek);

		int endDayOfWeek = firstDayOfWeek > 1 ? (firstDayOfWeek - 1) : (firstDayOfWeek + 6);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (endDayOfWeek != dayWeek) {
			while (true) {
				cal.add(Calendar.DATE, 1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
				if (endDayOfWeek == dayWeek) {
					break;
				}
			}
		}

		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Get first day of month
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartMonth(Date date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}

		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getStartMonth(Long date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTimeInMillis(date);
		}

		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Date getEndMonth(Date date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getEndMonth(Long date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTimeInMillis(date);
		}

		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Get first day of year
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartYear(Date date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);

		if (date != null) {
			cal.setTime(date);
		}

		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));

		return cal.getTime();
	}

	public static Long getStartYear(Long date, TimeZone zone, Locale locale) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);

		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));

		return cal.getTimeInMillis();
	}

	public static Date getEndYear(Date date, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (date != null) {
			cal.setTime(date);
		}

		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static Long getEndYear(Long date, TimeZone zone, Locale locale) {
		if (date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);

		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Long getStartYear(Integer year, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (year != null) {
			cal.set(Calendar.YEAR, year);
		}

		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Long getEndYear(Integer year, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		if (year != null) {
			cal.set(Calendar.YEAR, year);
		}

		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Long getStartQuarter(Long date, TimeZone zone, Locale locale) {
		if (date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);

		int month = cal.get(Calendar.MONTH);
		if (month < 3) {
			cal.set(Calendar.MONTH, Calendar.JANUARY);
		} else if (month < 6) {
			cal.set(Calendar.MONTH, Calendar.APRIL);
		} else if (month < 9) {
			cal.set(Calendar.MONTH, Calendar.JULY);
		} else {
			cal.set(Calendar.MONTH, Calendar.OCTOBER);
		}

		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static Long getEndQuarter(Long date, TimeZone zone, Locale locale) {
		if (date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);

		int month = cal.get(Calendar.MONTH);
		if (month < 3) {
			cal.set(Calendar.MONTH, Calendar.MARCH);
		} else if (month < 6) {
			cal.set(Calendar.MONTH, Calendar.JUNE);
		} else if (month < 9) {
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		} else {
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
		}

		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Minimum date of system
	 * 
	 * @return
	 */
	public static Long getMinDate(TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);

		cal.set(Calendar.YEAR, cal.getActualMinimum(Calendar.YEAR));
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	public static boolean isMaxDate(Date dt, TimeZone zone, Locale locale) {
		if (dt == null) {
			return false;
		}
		String max = Lib.dt2Str(getMaxDate(zone, locale), "yyyyMMdd", zone, locale);
		String date = Lib.dt2Str(dt, "yyyyMMdd", zone, locale);
		return max.equals(date);
	}

	/**
	 * Maximum date of system
	 * 
	 * @return
	 */
	public static Long getMaxDate(TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.set(Calendar.YEAR, 9999);
		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

		return cal.getTimeInMillis();
	}

	/**
	 * Compare 2 object
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean equals(String v1, String v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			equal = v1.equals(v2);
		}
		return equal;
	}

	public static boolean equals(Number v1, Number v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			equal = v1.equals(v2);
		}
		return equal;
	}

	public static boolean equals(Long v1, Long v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			equal = v1.equals(v2);
		}
		return equal;
	}

	public static boolean equals(Integer v1, Integer v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			equal = v1.equals(v2);
		}
		return equal;
	}

	public static boolean equals(Double v1, Double v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			equal = v1.equals(v2);
		}
		return equal;
	}

	public static boolean equals(Date v1, Date v2) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {

		}
		return equal;
	}

	public static boolean equals(String v1, String v2, int size) {
		boolean equal = true;
		if (v1 == null && v2 == null) {
			equal = true;
		} else if (v1 == null || v2 == null) {
			equal = false;
		} else {
			if (size > 0) {
				v1 = v1.length() > size ? v1.substring(0, size) : v1;
				v2 = v2.length() > size ? v2.substring(0, size) : v2;
			}
			equal = v1.equals(v2);
		}
		return equal;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String formatCurrency(Number value, Locale locale) {
		if (value == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		String ret = null;
		try {
			ret = nf.format(value);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String formatNumber(Number value, Locale locale) {
		NumberFormat nf = NumberFormat.getInstance(locale);
		String ret = null;
		try {
			ret = nf.format(value);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}

	public static String formatNumber(Number value, String pattern) {
		if (value == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}

	/**
	 * Check tring empty
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(String src) {
		if (null == src || src.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object[] src) {
		if (null == src || src.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(@SuppressWarnings("rawtypes") List src) {
		if (null == src || src.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(@SuppressWarnings("rawtypes") Map src) {
		if (null == src || src.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(@SuppressWarnings("rawtypes") Vector src) {
		if (null == src || src.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Check tring not empty
	 * 
	 * @param iSrc
	 * @return
	 */
	public static boolean isNotEmpty(String src) {
		return !isEmpty(src);
	}

	public static boolean isNotEmpty(Object[] src) {
		return !isEmpty(src);
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List src) {
		return !isEmpty(src);
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Map src) {
		return !isEmpty(src);
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Vector src) {
		return !isEmpty(src);
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check single string
	 * 
	 * @param src
	 * @return
	 */
	public static boolean is1Byte(String src) {
		boolean ret = true;
		int length = 0;
		try {
			if (src != null) {
				length = src.getBytes(Constant.ENCODING).length;
			}
		} catch (UnsupportedEncodingException e) {
			length = src.getBytes().length;
		}
		if (src != null && length != src.length()) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Check all multiple byte string
	 * 
	 * @param src
	 * @return
	 */
	public static boolean is2Byte(String src) {
		if (src == null) {
			return false;
		}
		boolean ret = false;
		byte[] b = null;
		try {
			b = src.getBytes(Constant.ENCODING);
		} catch (Exception e) {
			b = src.getBytes();
		}

		if (b.length < (2 * src.length())) {
			ret = false;
		} else {
			ret = true;
		}
		return ret;
	}

	/**
	 * String have character 2 byte
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isInclude2Byte(String src) {
		if (src == null) {
			return false;
		}
		boolean ret = false;
		byte[] b = null;
		try {
			b = src.getBytes(Constant.ENCODING);
		} catch (Exception e) {
			b = src.getBytes();
		}
		if (src.length() < b.length) {
			ret = true;
		}
		return ret;
	}

	/**
	 * Checks if the String contains only unicode letters or digits. null will
	 * return false. An empty String ("") will return true.
	 * StringUtils.isAlphanumeric(null) = false StringUtils.isAlphanumeric("") =
	 * true StringUtils.isAlphanumeric("  ") = false Lib.isAlphanumeric("abc") =
	 * true Lib.isAlphanumeric("ab c") = false Lib.isAlphanumeric("ab2c") = true
	 * Lib.isAlphanumeric("ab-c") = false
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate email
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidEmail(String str) {
		return isValidEmail(str, false);
	}

	public static boolean isValidEmail(String str, boolean defaultValue) {
		if (str == null || str.trim().isEmpty()) {
			return defaultValue;
		}
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return str.matches(EMAIL_REGEX);
	}

	/**
	 * 
	 * @param num
	 * @param pattern
	 * @return
	 */
	public static String num2Str(Number num, String pattern) {
		String str = "";
		if (num != null) {
			DecimalFormat df = new DecimalFormat(pattern);
			str = df.format(num);
		}
		return str;
	}

	/**
	 * Convert Date to String
	 * 
	 * @param dt
	 * @return
	 */
	public static String dt2Str(Date dt, TimeZone zone, Locale locale) {
		if (dt != null) {
			if (isDefault(dt, zone, locale)) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
			return sdf.format(dt);
		} else {
			return "";
		}
	}

	/**
	 * Convert Date to String with pattern
	 * 
	 * @param dt
	 * @param pattern
	 * @return
	 */
	public static String dt2Str(Date dt, String pattern, TimeZone zone, Locale locale) {
		if (dt != null) {
			if (isDefault(dt, zone, locale)) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
			return sdf.format(dt);
		} else {
			return "";
		}
	}

	/**
	 * Convert Date to String
	 * 
	 * @param dt
	 * @return
	 */
	public static String dt2Str(Long dt, TimeZone zone, Locale locale) {
		if (dt != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
			return sdf.format(new Date(dt));
		} else {
			return "";
		}
	}

	/**
	 * Convert Date to String with pattern
	 * 
	 * @param dt
	 * @param pattern
	 * @return
	 */
	public static String dt2Str(Long dt, String pattern, TimeZone zone, Locale locale) {
		if (dt != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
			return sdf.format(new Date(dt));
		} else {
			return "";
		}
	}

	/**
	 * Convert String to Date
	 * 
	 * @param ymd
	 * @return
	 */
	public static Long str2Dt(String ymd, TimeZone zone, Locale locale) {
		if (ymd == null || ymd.isEmpty()) {
			return null;
		}
		Date ret = null;
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE, locale);
		try {
			ret = sdf.parse(ymd);
		} catch (ParseException e) {
			sdf = new SimpleDateFormat(PATTERN_DATE_TIME_SHORT);
			try {
				ret = sdf.parse(ymd);
			} catch (ParseException e1) {
				sdf = new SimpleDateFormat(PATTERN_DATE_TIME);
				try {
					ret = sdf.parse(ymd);
				} catch (ParseException e2) {
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						ret = sdf.parse(ymd);
					} catch (ParseException e3) {
						ret = null;
					}
				}
			}
		}

		return ret != null ? ret.getTime() : null;
	}

	/**
	 * Convert String to Date with pattern
	 * 
	 * @param ymd
	 * @param pattern
	 * @return
	 */
	public static Long str2Dt(String ymd, String pattern, TimeZone zone, Locale locale) {
		if (ymd == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		try {
			Date ret = sdf.parse(ymd);
			return ret.getTime();
		} catch (ParseException e) {
			if (pattern == null) {
				return str2Dt(ymd, zone, locale);
			} else {
				return null;
			}
		}
	}

	/**
	 * Add day to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addDate(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.DATE, amount, zone, locale);
	}

	public static Long addDate(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.DATE, amount, zone, locale);
	}

	/**
	 * Add hour to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.HOUR, amount, zone, locale);
	}

	public static Long addHour(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.HOUR, amount, zone, locale);
	}

	/**
	 * Add minute to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addMinute(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.MINUTE, amount, zone, locale);
	}

	public static Long addMinute(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.MINUTE, amount, zone, locale);
	}

	/**
	 * Add second to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.SECOND, amount, zone, locale);
	}

	public static Long addSecond(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.SECOND, amount, zone, locale);
	}

	/**
	 * Add month to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.MONTH, amount, zone, locale);
	}

	public static Long addMonth(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.MONTH, amount, zone, locale);
	}

	/**
	 * Add year to date
	 * 
	 * @param dt
	 * @param amount
	 * @return
	 */
	public static Date addYear(Date dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.YEAR, amount, zone, locale);
	}

	public static Long addYear(Long dt, int amount, TimeZone zone, Locale locale) {
		return addField(dt, Calendar.YEAR, amount, zone, locale);
	}

	/**
	 * Change value Field date
	 * 
	 * @param dt
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date addField(Date dt, int field, int amount, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Long addField(Long dt, int field, int amount, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		cal.add(field, amount);
		return cal.getTimeInMillis();
	}

	/**
	 * Calculate difference year
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffYear(Date date1, Date date2, TimeZone zone, Locale locale) {
		Calendar c1 = Calendar.getInstance(zone, locale);
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance(zone, locale);
		c2.setTime(date2);
		if (null != c1 && null != c2) {
			return Math.abs(c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR));
		}

		return -1;
	}

	/**
	 * Calculate difference month
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffMonth(Date date1, Date date2, TimeZone zone, Locale locale) {
		Calendar c1 = Calendar.getInstance(zone, locale);
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance(zone, locale);
		c2.setTime(date2);
		if (null != c1 && null != c2) {
			int nYear = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
			int nMonth = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
			return Math.abs(nYear * 12 + nMonth);
		}
		return -1;
	}

	/**
	 * Calculate difference day
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffWeek(Date date1, Date date2, int firstDayOfWeek) {
		if (null != date1 && null != date2) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			while (cal1.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
				cal1.add(Calendar.DATE, -1);
			}
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			while (cal2.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
				cal2.add(Calendar.DATE, -1);
			}
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);

			return Math.abs((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (ONE_DAY_MILLI_SEC * 7));
		}
		return -1;
	}

	/**
	 * Calculate difference day
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffDay(Date date1, Date date2) {
		if (null != date1 && null != date2) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);

			return Math.abs((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_DAY_MILLI_SEC);
		}
		return -1;
	}

	/**
	 * Calculate difference hour
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffHour(Date date1, Date date2) {
		if (null != date1 && null != date2) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);

			return Math.abs((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_HOUR_MILLI_SEC);
		}
		return -1;
	}

	/**
	 * Calculate difference minute
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffMinute(Date date1, Date date2) {
		if (null != date1 && null != date2) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);

			return Math.abs((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_MINUTE_MILLI_SEC);
		}
		return -1;
	}

	/**
	 * Get year of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getYear(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		int curYear = cal.get(Calendar.YEAR);
		cal.setTimeInMillis(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			year = curYear;
		}
		return year;
	}

	public static String getYear(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getYear(dt, zone, locale));
		}
		return getYear(dt, zone, locale) + "";
	}

	/**
	 * Get month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMonth(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			month = 1;
		} else {
			month++;
		}

		return month;
	}

	public static String getMonth(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getMonth(dt, zone, locale));
		}
		return getMonth(dt, zone, locale) + "";
	}

	/**
	 * Get day of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDate(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			date = 1;
		}

		return date;
	}

	public static String getDate(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getDate(dt, zone, locale));
		}
		return getDate(dt, zone, locale) + "";
	}

	/**
	 * Get month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getHour(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public static String getHour(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getHour(dt, zone, locale));
		}
		return getHour(dt, zone, locale) + "";
	}

	/**
	 * Get month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMinute(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int minute = cal.get(Calendar.MINUTE);
		return minute;
	}

	public static String getMinute(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getMinute(dt, zone, locale));
		}
		return getMinute(dt, zone, locale) + "";
	}

	/**
	 * Get month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getSecond(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int second = cal.get(Calendar.SECOND);
		return second;
	}

	public static String getSecond(Long dt, TimeZone zone, Locale locale, String pattern) {
		if (pattern != null) {
			NumberFormat nf = new DecimalFormat(pattern);
			return nf.format(getSecond(dt, zone, locale));
		}
		return getSecond(dt, zone, locale) + "";
	}

	/**
	 * Get week of year
	 * 
	 * @param dt
	 * @return
	 */
	public static int getWeekYear(Date dt, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Set week of year
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setWeekYear(Date dt, int firstDayOfWeek, int week, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		return cal.getTime();
	}

	/**
	 * Get week of year
	 * 
	 * @param dt
	 * @return
	 */
	public static int getWeekMonth(Date dt, int firstDayOfWeek, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * Get day of week
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDayWeek(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Get year of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getYear(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		int curYear = cal.get(Calendar.YEAR);
		cal.setTime(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			year = curYear;
		}
		return year;
	}

	/**
	 * Get month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMonth(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			month = 0;
		} else {
			month++;
		}

		return month;
	}

	/**
	 * Get day of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDate(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			date = 0;
		}

		return date;
	}

	/**
	 * Get hour of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getHour(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Get minute of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMinute(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * Get second of date
	 * 
	 * @param dt
	 * @return
	 */
	public static int getSecond(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		return cal.get(Calendar.SECOND);
	}

	/**
	 * Get Quarter
	 * 
	 * @param dt
	 * @param zone
	 * @param locale
	 * @return
	 */
	public static int getQuarter(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		int month = cal.get(Calendar.MONTH);
		if (month < 3) {
			return 1;
		} else if (month < 6) {
			return 2;
		} else if (month < 9) {
			return 3;
		} else {
			return 4;
		}
	}

	public static int getQuarter(Long dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(dt);
		int month = cal.get(Calendar.MONTH);
		if (month < 3) {
			return 1;
		} else if (month < 6) {
			return 2;
		} else if (month < 9) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * Set year of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setYear(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.YEAR, val);
		return cal.getTime();
	}

	public static Long setYear(Long date, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);
		cal.set(Calendar.YEAR, val);
		return cal.getTimeInMillis();
	}

	/**
	 * Set month of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setMonth(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.MONTH, val - 1);
		return cal.getTime();
	}

	public static Long setMonth(Long date, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);
		cal.set(Calendar.MONTH, val - 1);
		return cal.getTimeInMillis();
	}

	/**
	 * Set day of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setDate(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.DATE, val);
		return cal.getTime();
	}

	public static Long setDate(Long date, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTimeInMillis(date);
		cal.set(Calendar.DATE, val);
		return cal.getTimeInMillis();
	}

	/**
	 * Set hour of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setHour(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, val);
		return cal.getTime();
	}

	/**
	 * Set minute of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setMinute(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.MINUTE, val);
		return cal.getTime();
	}

	/**
	 * Set second of date
	 * 
	 * @param dt
	 * @return
	 */
	public static Date setSecond(Date dt, int val, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		cal.set(Calendar.SECOND, val);
		return cal.getTime();
	}

	/**
	 * 
	 * @param dt
	 * @return
	 */
	public static long getMilliSecond(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		return cal.getTimeInMillis();
	}

	public static boolean after(String date1, String date2, TimeZone zone, Locale locale) {
		Calendar c1 = getDateFromStr(date1, zone, locale);
		Calendar c2 = getDateFromStr(date2, zone, locale);
		if (null != c1 && null != c2) {
			return c1.after(c2);
		}
		return false;
	}

	public static boolean before(String date1, String date2, TimeZone zone, Locale locale) {
		Calendar c1 = getDateFromStr(date1, zone, locale);
		Calendar c2 = getDateFromStr(date2, zone, locale);
		if (null != c1 && null != c2) {
			return c1.before(c2);
		}

		return false;
	}

	/**
	 * 
	 * @param dt
	 * @return
	 */
	public static boolean isDefault(Date dt, TimeZone zone, Locale locale) {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.setTime(dt);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DATE);
		if (year == 9999 && month == Calendar.DECEMBER && date == 31) {
			return true;
		}
		return false;
	}

	public static int diffYear(String date1, String date2, TimeZone zone, Locale locale) {

		Calendar c1 = getDateFromStr(date1, zone, locale);
		Calendar c2 = getDateFromStr(date2, zone, locale);
		if (null != c1 && null != c2) {
			return Math.abs(c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR));
		}

		return -1;
	}

	public static int diffMonth(String date1, String date2, TimeZone zone, Locale locale) {
		Calendar c1 = getDateFromStr(date1, zone, locale);
		Calendar c2 = getDateFromStr(date2, zone, locale);
		if (null != c1 && null != c2) {
			int nYear = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
			int nMonth = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
			return Math.abs(nYear * 12 + nMonth);
		}
		return -1;
	}

	public static long diffDay(String date1, String date2, TimeZone zone, Locale locale) {

		Calendar c1 = getDateFromStr(date1, zone, locale);
		Calendar c2 = getDateFromStr(date2, zone, locale);
		if (null != c1 && null != c2) {
			return Math.abs((c1.getTimeInMillis() - c2.getTimeInMillis()) / ONE_DAY_MILLI_SEC);
		}
		return -1;
	}

	public static Calendar getDateFromStr(String str, TimeZone zone, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
		Calendar c = Calendar.getInstance(zone, locale);
		try {
			c.setTime(sdf.parse(str));
		} catch (ParseException e) {
			sdf = new SimpleDateFormat(PATTERN_DATE_TIME_SHORT);
			try {
				c.setTime(sdf.parse(str));
			} catch (ParseException e1) {
				sdf = new SimpleDateFormat(PATTERN_DATE_TIME);
				try {
					c.setTime(sdf.parse(str));
				} catch (ParseException e2) {
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						c.setTime(sdf.parse(str));
					} catch (ParseException e3) {
						c = null;
					}
				}
			}
		}
		return c;
	}

	/**
	 * Define pattern date
	 * 
	 * @return
	 */
	public static String getPatternDate() {
		return PATTERN_DATE;
	}

	public static String getPatternDateTime() {
		return PATTERN_DATE_TIME;
	}

	public static String getPatternDateTimeShort() {
		return PATTERN_DATE_TIME_SHORT;
	}

	public static String getPatternYearMonth() {
		return PATTERN_YEAR_MONTH;
	}

	public static String getPatternMonthDay() {
		return PATTERN_MONTH_DAY;
	}

	public static String getPatternTime() {
		return PATTERN_TIME;
	}

	public static String getPatternTimeShort() {
		return PATTERN_TIME_SHORT;
	}

	public static String getYYYYMMDDHHMMSSSSS() {
		return YYYYMMDDHHMMSSSSS;
	}

	public static String getYYYYMMDDHHMMSS() {
		return YYYYMMDDHHMMSS;
	}

	public static String getPatternHHMM() {
		return HHMM;
	}

	public static String getPatternYYYYMM() {
		return YYYYMM;
	}

	public static String getPatternYYYY() {
		return YYYY;
	}

	public static String getPatternMM() {
		return MM;
	}

	public static String getPatternDD() {
		return DD;
	}

	public static String getPatternYYYYMMDD() {
		return YYYYMMDD;
	}

	public static String getPatternDDMMYYYY() {
		return DDMMYYYY;
	}

	public static String getPatternEEEE() {
		return EEEE;
	}

	public static String getPatternYY_MM() {
		return YY_MM;
	}

	public static String getPatternYY() {
		return YY;
	}

	public static String getPatternDDMMYY() {
		return DDMMYY;
	}

	public static String getFullPatternDate(TimeZone zone, Locale locale) {
		return "(" + getTimeZoneName(zone, locale) + ")" + "yyyy/MM/dd E HH:mm:ss";
	}

	public static String getTimeZoneName(TimeZone zone, Locale locale) {
		return Calendar.getInstance(zone, locale).getTimeZone().getDisplayName();
	}

	public static int getTimeZoneOffsetHour(TimeZone zone, Locale locale) {
		return (int) (Calendar.getInstance(zone, locale).get(Calendar.ZONE_OFFSET) / ONE_HOUR_MILLI_SEC);
	}

	private static final long ONE_DAY_MILLI_SEC = 86400000;
	private static final long ONE_HOUR_MILLI_SEC = 3600000;
	private static final long ONE_MINUTE_MILLI_SEC = 60000;

	private static final String PATTERN_DATE = "dd/MM/yyyy";
	private static final String PATTERN_DATE_TIME_SHORT = "dd/MM/yyyy HH:mm";
	private static final String PATTERN_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
	private static final String PATTERN_YEAR_MONTH = "MM/yyyy";
	private static final String PATTERN_MONTH_DAY = "dd/MM";
	private static final String PATTERN_TIME = "HH:mm:ss";
	private static final String PATTERN_TIME_SHORT = "HH:mm";
	private static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	private static final String HHMM = "HHmm";
	private static final String YYYYMM = "yyyyMM";
	private static final String YYYY = "yyyy";
	private static final String MM = "MM";
	private static final String DD = "dd";
	private static final String YYYYMMDD = "yyyyMMdd";
	private static final String DDMMYYYY = "ddMMyyyy";
	private static final String EEEE = "EEEE";
	private static final String YY_MM = "yy-MM";
	private static final String YY = "yy";
	private static final String DDMMYY = "ddMMyy";

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idResource
	 * @author phong.a.nguyen
	 */
	public static String getResource(Locale locale, String idResource) {
		String result = "";
		try {
			result = ResourceProperties.getApplicationResources().getMessage(locale, idResource);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idResource
	 * @param param
	 * @author phong.a.nguyen
	 */
	public static String getResource(Locale locale, String idResource, Object... param) {
		String result = "";
		try {
			result = ResourceProperties.getApplicationResources().getMessage(locale, idResource, param);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idMessage
	 * @author phong.a.nguyen
	 */
	public static String getMessage(Locale locale, String idMessage) {
		String result = "";
		try {
			result = ResourceProperties.getMessageResources().getMessage(locale, idMessage);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idMessage
	 * @param param
	 * @author phong.a.nguyen
	 */
	public static String getMessage(Locale locale, String idMessage, Object... param) {
		String result = "";
		try {
			result = ResourceProperties.getMessageResources().getMessage(locale, idMessage, param);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idParam
	 * @author phong.a.nguyen
	 */
	public static String getParam(Locale locale, String idParam) {
		String result = "";
		try {
			result = ResourceProperties.getParamResources().getMessage(locale, idParam);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idSetting
	 * @author phong.a.nguyen
	 */
	public static String getSetting(String idSetting, Object... param) {
		String result = "";
		try {
			result = ResourceProperties.getSettingResources().getMessage(idSetting, param);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idSetting
	 * @author phong.a.nguyen
	 */
	public static String getSetting(String idSetting) {
		String result = "";
		try {
			result = ResourceProperties.getSettingResources().getMessage(idSetting);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idLogging
	 * @author phong.a.nguyen
	 */
	public static String getLogging(String idLogging) {
		String result = "";
		try {
			result = ResourceProperties.getLoggingResources().getMessage(idLogging);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Get message from ApplicationResources
	 * 
	 * @param idLogging
	 * @param param
	 * @author phong.a.nguyen
	 */
	public static String getLogging(String idLogging, Object... param) {
		String result = "";
		try {
			result = ResourceProperties.getLoggingResources().getMessage(idLogging, param);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Convert map to string
	 */
	public static String mapToString(Map<?, ?> data) {
		StringBuffer strBuf = new StringBuffer();
		if (data != null && data.size() > 0) {
			Iterator<?> keyMap = data.keySet().iterator();
			Object key = null;
			while (keyMap.hasNext()) {
				key = keyMap.next();
				// Add data to log
				strBuf.append('[');
				strBuf.append(key);
				strBuf.append(" = ");
				strBuf.append(data.get(key));
				strBuf.append("]\r\n");
			}
		}
		return strBuf.toString();
	}

	/**
	 * MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		try {
			byte[] buffer = str.getBytes();
			byte[] result = null;
			StringBuffer buf = null;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			// allocate room for the hash
			result = new byte[md5.getDigestLength()];
			// calculate hash
			md5.reset();
			md5.update(buffer);

			result = md5.digest();
			// create hex string from the 16-byte hash
			buf = new StringBuffer(result.length * 2);
			for (int i = 0; i < result.length; i++) {
				int intVal = result[i] & 0xff;
				if (intVal < 0x10) {
					buf.append('0');
				}
				buf.append(Integer.toHexString(intVal));
			}
			str = buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static byte[] createChecksum(byte[] data) throws Exception {
		MessageDigest complete = MessageDigest.getInstance("MD5");
		complete.update(data);
		return complete.digest();
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getMD5Checksum(byte[] data) throws Exception {
		byte[] b = createChecksum(data);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	private static final String src = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_. ";
	private static final String dest = "ABCDEFGHIJKLMNO-_. abcdefghijklmnoPQRSTUVWXYZ0123456789pqrstuvwxyz";

	/**
	 * Encode string
	 * 
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		if (s == null) {
			return s;
		}

		int len = s.length();
		char[] ret = dest.toCharArray();
		char[] buf = s.toCharArray();
		int index = 0;
		for (int i = 0; i < len; i++) {
			index = src.indexOf(buf[i]);
			buf[i] = index < 0 ? buf[i] : ret[index];
		}

		return new String(buf);
	}

	/**
	 * Decode string
	 * 
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		if (s == null) {
			return s;
		}

		int len = s.length();
		char[] ret = src.toCharArray();
		char[] buf = s.toCharArray();
		int index = 0;
		for (int i = 0; i < len; i++) {
			index = dest.indexOf(buf[i]);
			buf[i] = index < 0 ? buf[i] : ret[index];
		}

		return new String(buf);
	}

	/**
	 * SHA-1
	 * 
	 * @param src
	 * @return
	 */
	public static String getPassword(String password) {
		try {
			password = "SkyEMR@062016" + password;
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Remove multiple spaces in string
	 * 
	 * @param src
	 * @return
	 */
	public static String removeMultipleSpaces(String src) {
		return src == null ? src : src.replaceAll("\\s+", " ");
	}

	/**
	 * Format search string sql
	 * 
	 * @param src
	 * @return
	 */
	public static String wildCardSearch(Object src) {
		String ret = src == null ? "" : src.toString();
		ret = ret.replace("%", "!%");
		ret = ret.replace("_", "!_");
		return ret;
	}

	/**
	 * Read file to byte array
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(File file) throws IOException {
		if (file == null || !file.exists()) {
			return null;
		}

		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null) {
					ous.close();
				}
				if (ios != null) {
					ios.close();
				}
			} catch (IOException e) {
			}
		}

		return ous.toByteArray();
	}

	public static void writeFile(File file, byte[] data) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.close();
	}

	/**
	 * Copy file
	 * 
	 * @param src
	 * @param dest
	 */
	public static boolean copyFile(File source, File target) {
		boolean ret = true;
		try {
			Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Copy file
	 * 
	 * @param src
	 * @param dest
	 */
	public static boolean copyFile(String source, String target) {
		return copyFile(new File(source), new File(target));
	}

	/**
	 * Move file
	 * 
	 * @param src
	 * @param dest
	 */
	public static boolean moveFile(File source, File target) {
		try {
			File parent = target.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Move file
	 * 
	 * @param src
	 * @param dest
	 */
	public static boolean moveFile(String source, String target) {
		return moveFile(new File(source), new File(target));
	}

	/**
	 * Delete file
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		try {
			Files.deleteIfExists(file.toPath());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Delete file
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(String file) {
		return deleteFile(new File(file));
	}

	/**
	 * Delete folder
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean deleteFolder(File folder) {
		boolean deleted = false;
		if (folder.isDirectory()) {
			// directory is empty, then delete it
			if (folder.list().length == 0) {
				deleted = folder.delete();
			} else {
				// list all the directory contents
				String files[] = folder.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(folder, temp);
					// recursive delete
					deleted = deleteFolder(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (folder.list().length == 0) {
					deleted = folder.delete();
				}
			}
		} else {
			// if file, then delete it
			deleted = folder.delete();
		}

		return deleted;
	}

	/**
	 * Delete folder
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean deleteFolder(String folder) {
		return deleteFolder(new File(folder));
	}

	public static void copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	/**
	 * Get extension file
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		if (Lib.isEmpty(fileName)) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(Constant.DOT));
	}

	public static String getExtWithoutDot(String fileName) {
		if (Lib.isEmpty(fileName)) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(Constant.DOT) + 1);
	}

	/**
	 * Get image type from file name
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getImageType(String fileName) {
		String type = fileName.substring(fileName.lastIndexOf(Constant.DOT) + 1).toLowerCase();
		if ("jpeg".equals(type) || "jpg".equals(type) || "jpe".equals(type)) {
			return "jpeg";
		} else if ("gif".equals(type)) {
			return "gif";
		} else if ("svg".equals(type)) {
			return "svg";
		} else if ("svgz".equals(type)) {
			return "svgz";
		} else if ("png".equals(type)) {
			return "png";
		} else if ("tif".equals(type) || "tiff".equals(type)) {
			return "tiff";
		} else if ("bmp".equals(type)) {
			return "bmp";
		} else if ("pct".equals(type) || "pic".equals(type) || "pict".equals(type)) {
			return "pict";
		} else if ("cgm".equals(type)) {
			return "cgm";
		} else if ("rgb".equals(type)) {
			return "x-rgb";
		} else if ("ico".equals(type)) {
			return "x-icon";
		} else if ("ief".equals(type)) {
			return "ief";
		} else if ("jp2".equals(type)) {
			return "jp2";
		} else if ("djv".equals(type) || "djvu".equals(type)) {
			return "vnd.djvu";
		} else if ("mac".equals(type) || "pnt".equals(type) || "pntg".equals(type)) {
			return "x-macpaint";
		} else if ("pbm".equals(type)) {
			return "x-portable-bitmap";
		} else if ("pgm".equals(type)) {
			return "x-portable-graymap";
		} else if ("pnm".equals(type)) {
			return "x-portable-anymap";
		} else if ("ppm".equals(type)) {
			return "x-portable-pixmap";
		} else if ("qti".equals(type) || "qtif".equals(type)) {
			return "x-quicktime";
		} else if ("ras".equals(type)) {
			return "x-cmu-raster";
		} else if ("wbmp".equals(type)) {
			return "vnd.wap.wbmp";
		} else if ("xbm".equals(type) || "xpm".equals(type)) {
			return "x-xbitmap";
		} else if ("xwd".equals(type)) {
			return "x-xwindowdump";
		} else {
			return "jpeg";
		}
	}

	public static String getImageType(byte[] image) {
		String type = "";
		InputStream is = null;
		ImageInputStream iis = null;
		try {
			is = new ByteArrayInputStream(image);
			iis = ImageIO.createImageInputStream(is);
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
			while (imageReaders.hasNext()) {
				ImageReader reader = (ImageReader) imageReaders.next();
				type = reader.getFormatName();
				break;
			}
		} catch (IOException e) {
			type = "";
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return type;
	}

	/**
	 * Get file type from file name
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String type = fileName.substring(fileName.lastIndexOf(Constant.DOT) + 1).toLowerCase();
		if ("jpeg".equals(type) || "jpg".equals(type) || "jpe".equals(type)) {
			return "jpeg";
		} else if ("tif".equals(type) || "tiff".equalsIgnoreCase(type)) {
			return "tiff";
		} else if ("pct".equals(type) || "pic".equals(type) || "pict".equals(type)) {
			return "pict";
		} else if ("rgb".equals(type)) {
			return "x-rgb";
		} else if ("ico".equals(type)) {
			return "x-icon";
		} else if ("djv".equals(type) || "djvu".equals(type)) {
			return "vnd.djvu";
		} else if ("mac".equals(type) || "pnt".equals(type) || "pntg".equals(type)) {
			return "x-macpaint";
		} else if ("pbm".equals(type)) {
			return "x-portable-bitmap";
		} else if ("pgm".equals(type)) {
			return "x-portable-graymap";
		} else if ("pnm".equals(type)) {
			return "x-portable-anymap";
		} else if ("ppm".equals(type)) {
			return "x-portable-pixmap";
		} else if ("qti".equals(type) || "qtif".equals(type)) {
			return "x-quicktime";
		} else if ("ras".equals(type)) {
			return "x-cmu-raster";
		} else if ("wbmp".equals(type)) {
			return "vnd.wap.wbmp";
		} else if ("xbm".equals(type) || "xpm".equals(type)) {
			return "x-xbitmap";
		} else if ("xwd".equals(type)) {
			return "x-xwindowdump";
		} else {
			return type;
		}
	}

	/**
	 * Get content type for response
	 * 
	 * @param type
	 * @return
	 */
	public static String getContentType(String type) {
		type = type.toLowerCase();
		if ("jpeg".equals(type) || "jpg".equals(type) || "jpe".equals(type)) {
			return "image/jpeg";
		} else if ("png".equals(type)) {
			return "image/png";
		} else if ("pdf".equals(type)) {
			return "application/pdf";
		} else if ("svg".equals(type)) {
			return "image/svg+xml";
		} else if ("svgz".equals(type)) {
			return "image/svg+xml";
		} else if ("gif".equals(type)) {
			return "image/gif";
		} else if ("tif".equals(type) || "tiff".equals(type)) {
			return "image/tiff";
		} else if ("bmp".equalsIgnoreCase(type)) {
			return "image/bmp";
		} else if ("pct".equals(type) || "pic".equals(type) || "pict".equals(type)) {
			return "image/pict";
		} else if ("cgm".equals(type)) {
			return "image/cgm";
		} else if ("rgb".equals(type) || "x-rgb".equals(type)) {
			return "image/x-rgb";
		} else if ("ico".equals(type) || "x-icon".equals(type)) {
			return "image/x-icon";
		} else if ("ief".equals(type)) {
			return "image/ief";
		} else if ("jp2".equals(type)) {
			return "image/jp2";
		} else if ("djv".equals(type) || "djvu".equals(type) || "vnd.djvu".equals(type)) {
			return "image/vnd.djvu";
		} else if ("mac".equals(type) || "pnt".equals(type) || "pntg".equals(type) || "x-macpaint".equals(type)) {
			return "image/x-macpaint";
		} else if ("pbm".equals(type) || "x-portable-bitmap".equals(type)) {
			return "image/x-portable-bitmap";
		} else if ("pgm".equals(type) || "x-portable-graymap".equals(type)) {
			return "image/x-portable-graymap";
		} else if ("pnm".equals(type) || "x-portable-anymap".equals(type)) {
			return "image/x-portable-anymap";
		} else if ("ppm".equals(type) || "x-portable-pixmap".equals(type)) {
			return "image/x-portable-pixmap";
		} else if ("qti".equals(type) || "qtif".equals(type) || "x-quicktime".equals(type)) {
			return "image/x-quicktime";
		} else if ("ras".equals(type) || "x-cmu-raster".equals(type)) {
			return "image/x-cmu-raster";
		} else if ("wbmp".equals(type) || "vnd.wap.wbmp".equals(type)) {
			return "image/vnd.wap.wbmp";
		} else if ("xbm".equals(type) || "xpm".equals(type) || "x-xbitmap".equals(type)) {
			return "image/x-xbitmap";
		} else if ("xwd".equals(type) || "x-xwindowdump".equals(type)) {
			return "image/x-xwindowdump";
		} else if ("epub".equals(type) || "epub+zip".equals(type)) {
			return "application/epub+zip";
		} else if ("jar".equals(type) || "java-archive".equals(type)) {
			return "application/java-archive";
		} else if ("js".equals(type) || "javascript".equals(type)) {
			return "application/javascript";
		} else if ("json".equals(type)) {
			return "application/json";
		} else if ("doc".equals(type) || "msword".equals(type)) {
			return "application/msword";
		} else if ("arc".equals(type) || "bin".equals(type) || "octet-stream".equals(type)) {
			return "application/octet-stream";
		} else if ("ogx".equals(type)) {
			return "application/ogg";
		} else if ("rtf".equals(type)) {
			return "application/rtf";
		} else if ("ts".equals(type)) {
			return "application/typescript";
		} else if ("azw".equals(type) || "vnd.amazon.ebook".equals(type)) {
			return "application/vnd.amazon.ebook";
		} else if ("mpkg".equals(type) || "vnd.apple.installer+xml".equals(type)) {
			return "application/vnd.apple.installer+xml";
		} else if ("xul".equals(type) || "vnd.mozilla.xul+xml".equals(type)) {
			return "application/vnd.mozilla.xul+xml";
		} else if ("xlsx".equals(type) || "vnd.ms-excel".equals(type)) {
			return "application/vnd.ms-excel";
		} else if ("eot".equals(type) || "vnd.ms-fontobject".equals(type)) {
			return "application/vnd.ms-fontobject";
		} else if ("ppt".equals(type) || "vnd.ms-powerpoint".equals(type)) {
			return "application/vnd.ms-powerpoint";
		} else if ("odp".equals(type) || "vnd.oasis.opendocument.presentation".equals(type)) {
			return "application/vnd.oasis.opendocument.presentation";
		} else if ("ods".equals(type) || "vnd.oasis.opendocument.spreadsheet".equals(type)) {
			return "application/vnd.oasis.opendocument.spreadsheet";
		} else if ("odt".equals(type) || "vnd.oasis.opendocument.text".equals(type)) {
			return "application/vnd.oasis.opendocument.text";
		} else if ("vsd".equals(type) || "vnd.visio".equals(type)) {
			return "application/vnd.visio";
		} else if ("7z".equals(type) || "x-7z-compressed".equals(type)) {
			return "application/x-7z-compressed";
		} else if ("abw".equals(type) || "x-abiword".equals(type)) {
			return "application/x-abiword";
		} else if ("bz".equals(type) || "x-bzip".equals(type)) {
			return "application/x-bzip";
		} else if ("bz2".equals(type) || "x-bzip2".equals(type)) {
			return "application/x-bzip2";
		} else if ("csh".equals(type) || "x-csh".equals(type)) {
			return "application/x-csh";
		} else if ("xhtml".equals(type) || "xhtml+xml".equals(type)) {
			return "application/xhtml+xml";
		} else if ("xml".equals(type)) {
			return "application/xml";
		} else if ("rar".equals(type) || "x-rar-compressed".equals(type)) {
			return "application/x-rar-compressed";
		} else if ("sh".equals(type) || "x-sh".equals(type)) {
			return "application/x-sh";
		} else if ("swf".equals(type) || "x-shockwave-flash".equals(type)) {
			return "application/x-shockwave-flash";
		} else if ("tar".equals(type) || "x-tar".equals(type)) {
			return "application/x-tar";
		} else if ("zip".equals(type)) {
			return "application/zip";
		} else if ("aac".equals(type) || "aac".equals(type)) {
			return "audio/aac";
		} else if ("midi".equals(type)) {
			return "audio/midi";
		} else if ("oga".equals(type)) {
			return "audio/ogg";
		} else if ("weba".equals(type) || "webm".equals(type)) {
			return "audio/webm";
		} else if ("wav".equals(type) || "x-wav".equals(type)) {
			return "audio/x-wav";
		} else if ("otf".equals(type)) {
			return "font/otf";
		} else if ("ttf".equals(type)) {
			return "font/ttf";
		} else if ("woff".equals(type)) {
			return "font/woff";
		} else if ("woff2".equals(type)) {
			return "font/woff2";
		} else if ("ics".equals(type) || "calendar".equals(type)) {
			return "text/calendar";
		} else if ("css".equals(type)) {
			return "text/css";
		} else if ("csv".equals(type)) {
			return "text/csv";
		} else if ("html".equals(type) || "htm".equals(type)) {
			return "text/html";
		} else if ("3gp".equals(type) || "3gpp".equals(type)) {
			return "video/3gpp";
		} else if ("3g2".equals(type) || "3gpp2".equals(type)) {
			return "video/3gpp2";
		} else if ("mpeg".equals(type)) {
			return "video/mpeg";
		} else if ("ogv".equals(type)) {
			return "video/ogg";
		} else if ("webm".equals(type)) {
			return "video/webm";
		} else if ("avi".equals(type) || "x-msvideo".equals(type)) {
			return "video/x-msvideo";
		} else {
			return "application/" + type;
		}
	}

	/**
	 * Copy object
	 * 
	 * @param src
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <D> D clone(D src) {
		D dest = null;
		if (src != null) {
			try {
				BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
				beanUtilsBean.getConvertUtils().register(false, true, -1);
				dest = (D) beanUtilsBean.cloneBean(src);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	public static <D> List<D> clone(List<D> src) {
		List<D> dest = null;
		if (src != null) {
			dest = new ArrayList<D>();
			for (int i = 0; i < src.size(); i++) {
				dest.add(clone(src.get(i)));
			}
		}
		return dest;
	}

	/**
	 * Copy all field same name
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean copy(Object dest, Object src) {
		boolean ret = false;
		if (src != null && dest != null) {
			try {
				BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
				beanUtilsBean.getConvertUtils().register(false, true, -1);
				beanUtilsBean.copyProperties(dest, src);
				ret = true;
			} catch (Exception e) {
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * Copy object by field name
	 * 
	 * @param src
	 * @param dest
	 * @param fieldNames
	 * @return
	 */
	public static boolean copy(Object dest, Object src, String[] fieldNames) {
		boolean ret = false;
		if (src != null && dest != null && fieldNames != null && fieldNames.length > 0) {
			String fieldNameSrc = null;
			String fieldNameDest = null;
			Field fieldSrc = null;
			Field fieldDest = null;
			String arr[] = null;
			for (String fieldName : fieldNames) {
				if (isNotEmpty(fieldName)) {
					arr = fieldName.split(Constant.COLON);
					if (arr.length > 1) {
						fieldNameSrc = arr[0];
						fieldNameDest = arr[1];
					} else {
						fieldNameSrc = arr[0];
						fieldNameDest = arr[0];
					}

					try {
						fieldSrc = src.getClass().getDeclaredField(fieldNameSrc);
						fieldDest = dest.getClass().getDeclaredField(fieldNameDest);

						if (fieldSrc != null && fieldDest != null) {
							fieldSrc.setAccessible(true);
							fieldDest.setAccessible(true);

							fieldDest.set(dest, fieldSrc.get(src));
						}
					} catch (Exception e) {
						continue;
					}
				}
			}
			ret = true;
		}
		return ret;
	}

	/**
	 * Copy object by field name
	 * 
	 * @param src
	 * @param dest
	 * @param fieldNames
	 * @return
	 */
	public static boolean copy(Object dest, Object src, String fieldNames) {
		boolean ret = false;
		if (src != null && dest != null && isNotEmpty(fieldNames)) {
			String[] arr = fieldNames.split(Constant.COMMA);
			ret = copy(dest, src, arr);
		}
		return ret;
	}

	/**
	 * Parameter Number is null return 0
	 * 
	 * @param o
	 * @return
	 */
	public static Integer sqlParamNumber(Integer o) {
		return o == null ? 0 : o;
	}

	public static Long sqlParamNumber(Long o) {
		return o == null ? 0L : o;
	}

	public static Double sqlParamNumber(Double o) {
		return o == null ? 0D : o;
	}

	public static boolean validDate(int y, int m, int d) {
		try {
			String strDate = String.format("%04d%02d%02d", y, m, d);

			SimpleDateFormat formater = new SimpleDateFormat(YYYYMMDD);
			formater.parse(strDate);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int nullSafeStringComparator(final String one, final String two) {
		if (one == null ^ two == null) {
			return (one == null) ? -1 : 1;
		}

		if (one == null && two == null) {
			return 0;
		}

		return one.compareToIgnoreCase(two);
	}

	public static String stringOfSize(int size, char ch) {
		final char[] array = new char[size];
		Arrays.fill(array, ch);
		return new String(array);
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String getQueryParam(HashMap<String, String> param) throws Exception {

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> e : param.entrySet()) {

			if (sb.length() > 0) {

				sb.append('&');
			}
			sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=')
					.append(URLEncoder.encode(e.getValue(), "UTF-8"));
		}

		return sb.toString();
	}

	/**
	 * Just remove '\n\r' for display HTML content in DIV.
	 * 
	 * @param message
	 * @return
	 */
	public static String markupHtml(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}

		if (isHtml(value)) {
			value = value.replace("<strong>", "<strong><b>");
			value = value.replace("</strong>", "</b></strong>");
			value = value.replace("<em>", "<em><i>");
			value = value.replace("</em>", "</i></em>");
			return value;
		} else {
			value = value.replaceAll("  ", "&nbsp; ");
			StringBuilder result = new StringBuilder();
			String[] lines = value.split("[\\n\\r]+");

			if (lines != null && lines.length > 0) {
				result.append(lines[0]);

				for (int i = 1; i < lines.length; i++) {
					result.append("<br/>");
					result.append(lines[i]);
				}
			}
			return result.toString();
		}
	}

	public final static String tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
	public final static String tagEnd = "\\</\\w+\\>";
	public final static String tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
	public final static String htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;";
	public final static Pattern htmlPattern = Pattern.compile(
			"(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing + ")|(" + htmlEntity + ")", Pattern.DOTALL);

	/**
	 * Check string is html string
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isHtml(String s) {
		boolean ret = false;
		if (s != null) {
			ret = htmlPattern.matcher(s).find();
		}
		return ret;
	}

	/**
	 * Check file is Tiff
	 * 
	 * @param bytes
	 * @return
	 */
	public static boolean isTiffFile(byte[] bytes) {
		boolean isTiff = false;
		if (bytes != null && bytes.length >= 3) {
			int b1 = bytes[0];
			int b2 = bytes[1];
			if ((b1 == 0x49 && b2 == 0x49 && bytes[2] == 42 && bytes[3] == 0) // little
																				// endian
					|| (b1 == 0x4d && b2 == 0x4d && bytes[2] == 0 && bytes[3] == 42)) { // big
																						// endian
				isTiff = true;
			}
		}
		return isTiff;
	}

	/**
	 * Check file is pdf
	 * 
	 * @param bytes
	 * @return
	 */
	public static boolean isPdfFile(byte[] bytes) {
		boolean isPdf = false;
		if (bytes != null && bytes.length > 4) {
			int b1 = bytes[0]; // %
			int b2 = bytes[1]; // P
			int b3 = bytes[2]; // D
			int b4 = bytes[3]; // F
			int b5 = bytes[4]; // -

			if (b1 == 0x25 && b2 == 0x50 && b3 == 0x44 && b4 == 0x46 && b5 == 0x2D) {
				isPdf = true;
			}
		}
		return isPdf;
	}

	/**
	 * This function is used for excel. If value is empty, it still empty, do
	 * not padding. If value is not empty, we will padding blank.
	 * 
	 * @param txt
	 * @param size
	 * @return
	 */
	public static String getTextForExcel(String txt, int size) {
		if (txt != null) {
			if (txt.length() > 0) {
				return Lib.rightPadWithJapanese(txt, size);
			}
		}
		return "";
	}

	public static byte[] getBytesForExcel(String source) throws IOException {
		byte[] b;
		String str;
		ByteArrayOutputStream stream = new ByteArrayOutputStream(source.length() * 2);
		for (int i = 0; i < source.length(); i++) {
			str = String.valueOf(source.charAt(i));
			b = str.getBytes(Constant.ENCODING);
			// character which can not be encoded is 63
			if (b.length == 1 && b[0] == 63 && source.charAt(i) != 63) {
				b = str.getBytes(Constant.ENCODING_EXCEL);
			}
			stream.write(b);
		}
		stream.close();
		return stream.toByteArray();
	}

	public static String trimRightSpecialCharacter(String source) {
		if (source == null) {
			return "";
		}
		int i = source.length() - 1;
		while (i >= 0) {
			char c = source.charAt(i);
			if (c == ' ' || c == '\n' || c == '\r') {
				i--;
			} else {
				i++;
				break;
			}
		}
		if (i > 0) {
			source = source.substring(0, i);
		} else {
			source = "";
		}
		return source;
	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * 
	 * @param srcImg
	 *            - source image to scale
	 * @param w
	 *            - desired width
	 * @param h
	 *            - desired height
	 * @return - the new resized image
	 */
	public static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
		int finalw = w;
		int finalh = h;
		double factor = 1.0d;
		if (src.getWidth() > src.getHeight()) {
			factor = ((double) src.getHeight() / (double) src.getWidth());
			finalh = (int) (finalw * factor);
		} else {
			factor = ((double) src.getWidth() / (double) src.getHeight());
			finalw = (int) (finalh * factor);
		}

		int type = src.getType() == 0 ? BufferedImage.TYPE_INT_RGB : src.getType();
		BufferedImage resizedImg = new BufferedImage(finalw, finalh, type);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.drawImage(src, 0, 0, finalw, finalh, Color.WHITE, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Encode image to Base64 string
	 * 
	 * @param image
	 *            The image to Base64 encode
	 * @param type
	 *            jpeg, bmp, ...
	 * @return Base64 encoded string
	 */
	public static String encodeBase64(byte[] imageBytes) {
		String imageString = null;
		try {
			if (imageBytes != null) {
				imageString = Base64.encodeBase64String(imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageString;
	}

	public static String encodeBase64(byte[] imageBytes, String type) {
		StringBuffer imageString = new StringBuffer("data:");
		imageString.append(Lib.getContentType(type));
		imageString.append(";base64,");
		try {
			if (imageBytes != null) {
				imageString.append(Base64.encodeBase64String(imageBytes));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageString.toString();
	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	public static byte[] decodeBase64(String imageDataString) {
		int index = imageDataString.indexOf(";base64,");
		if (index > 0) {
			index = imageDataString.indexOf(",", index);
			imageDataString = imageDataString.substring(index + 1);
		}
		return Base64.decodeBase64(imageDataString);
	}

	public static String getImageTypeBase64(String imageDataString) {
		String type = "";
		if (imageDataString != null) {
			int begin = imageDataString.indexOf("/");
			int end = imageDataString.indexOf(";");
			if (begin > 0 && end > 0 && begin < end) {
				type = imageDataString.substring(begin + 1, end).toLowerCase();
			}
		}

		return type;
	}

	// data:image/png;

	/**
	 * Convert to vietnam no sign
	 * 
	 * @param org
	 * @return
	 */
	public static String removeAccent(String org) {
		if (org == null) {
			return org;
		}

		// Remove sign
		org = org.replaceAll("\u0309", ""); // ả
		org = org.replaceAll("\u0301", ""); // á
		org = org.replaceAll("\u0300", ""); // à
		org = org.replaceAll("\u0323", ""); // ạ
		org = org.replaceAll("\u0303", ""); // ã

		char arrChar[] = org.toCharArray();
		char result[] = new char[arrChar.length];
		for (int i = 0; i < arrChar.length; i++) {
			switch (arrChar[i]) {
			case '\u00E1':
			case '\u00E0':
			case '\u1EA3':
			case '\u00E3':
			case '\u1EA1':
			case '\u0103':
			case '\u1EAF':
			case '\u1EB1':
			case '\u1EB3':
			case '\u1EB5':
			case '\u1EB7':
			case '\u00E2':
			case '\u1EA5':
			case '\u1EA7':
			case '\u1EA9':
			case '\u1EAB':
			case '\u1EAD':
			case '\u0203':
			case '\u01CE': {
				result[i] = 'a';
				break;
			}
			case '\u00E9':
			case '\u00E8':
			case '\u1EBB':
			case '\u1EBD':
			case '\u1EB9':
			case '\u00EA':
			case '\u1EBF':
			case '\u1EC1':
			case '\u1EC3':
			case '\u1EC5':
			case '\u1EC7':
			case '\u0207': {
				result[i] = 'e';
				break;
			}
			case '\u00ED':
			case '\u00EC':
			case '\u1EC9':
			case '\u0129':
			case '\u1ECB': {
				result[i] = 'i';
				break;
			}
			case '\u00F3':
			case '\u00F2':
			case '\u1ECF':
			case '\u00F5':
			case '\u1ECD':
			case '\u00F4':
			case '\u1ED1':
			case '\u1ED3':
			case '\u1ED5':
			case '\u1ED7':
			case '\u1ED9':
			case '\u01A1':
			case '\u1EDB':
			case '\u1EDD':
			case '\u1EDF':
			case '\u1EE1':
			case '\u1EE3':
			case '\u020F': {
				result[i] = 'o';
				break;
			}
			case '\u00FA':
			case '\u00F9':
			case '\u1EE7':
			case '\u0169':
			case '\u1EE5':
			case '\u01B0':
			case '\u1EE9':
			case '\u1EEB':
			case '\u1EED':
			case '\u1EEF':
			case '\u1EF1': {
				result[i] = 'u';
				break;
			}
			case '\u00FD':
			case '\u1EF3':
			case '\u1EF7':
			case '\u1EF9':
			case '\u1EF5': {
				result[i] = 'y';
				break;
			}
			case '\u0111': {
				result[i] = 'd';
				break;
			}
			case '\u00C1':
			case '\u00C0':
			case '\u1EA2':
			case '\u00C3':
			case '\u1EA0':
			case '\u0102':
			case '\u1EAE':
			case '\u1EB0':
			case '\u1EB2':
			case '\u1EB4':
			case '\u1EB6':
			case '\u00C2':
			case '\u1EA4':
			case '\u1EA6':
			case '\u1EA8':
			case '\u1EAA':
			case '\u1EAC':
			case '\u0202':
			case '\u01CD': {
				result[i] = 'A';
				break;
			}
			case '\u00C9':
			case '\u00C8':
			case '\u1EBA':
			case '\u1EBC':
			case '\u1EB8':
			case '\u00CA':
			case '\u1EBE':
			case '\u1EC0':
			case '\u1EC2':
			case '\u1EC4':
			case '\u1EC6':
			case '\u0206': {
				result[i] = 'E';
				break;
			}
			case '\u00CD':
			case '\u00CC':
			case '\u1EC8':
			case '\u0128':
			case '\u1ECA': {
				result[i] = 'I';
				break;
			}
			case '\u00D3':
			case '\u00D2':
			case '\u1ECE':
			case '\u00D5':
			case '\u1ECC':
			case '\u00D4':
			case '\u1ED0':
			case '\u1ED2':
			case '\u1ED4':
			case '\u1ED6':
			case '\u1ED8':
			case '\u01A0':
			case '\u1EDA':
			case '\u1EDC':
			case '\u1EDE':
			case '\u1EE0':
			case '\u1EE2':
			case '\u020E': {
				result[i] = 'O';
				break;
			}
			case '\u00DA':
			case '\u00D9':
			case '\u1EE6':
			case '\u0168':
			case '\u1EE4':
			case '\u01AF':
			case '\u1EE8':
			case '\u1EEA':
			case '\u1EEC':
			case '\u1EEE':
			case '\u1EF0': {
				result[i] = 'U';
				break;
			}

			case '\u00DD':
			case '\u1EF2':
			case '\u1EF6':
			case '\u1EF8':
			case '\u1EF4': {
				result[i] = 'Y';
				break;
			}
			case '\u0110':
			case '\u00D0':
			case '\u0089': {
				result[i] = 'D';
				break;
			}

			// Space character
			case '\u00A0':
			case '\u1680':
			case '\u2000':
			case '\u2001':
			case '\u2002':
			case '\u2003':
			case '\u2004':
			case '\u2005':
			case '\u2006':
			case '\u2007':
			case '\u2008':
			case '\u2009':
			case '\u200A':
			case '\u202F':
			case '\u205F': {
				result[i] = ' ';
				break;
			}

			// 2 byte character
			case '\u3000': {
				result[i] = ' ';
				break;
			}

			default:
				result[i] = arrChar[i];
			}
		}

		return new String(result);
	}

	/**
	 * Transfer to unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String compound2Unicode(String str) {
		str = str.replaceAll("\u0065\u0309", "\u1EBB"); // ẻ
		str = str.replaceAll("\u0065\u0301", "\u00E9"); // é
		str = str.replaceAll("\u0065\u0300", "\u00E8"); // è
		str = str.replaceAll("\u0065\u0323", "\u1EB9"); // ẹ
		str = str.replaceAll("\u0065\u0303", "\u1EBD"); // ẽ
		str = str.replaceAll("\u00EA\u0309", "\u1EC3"); // ể
		str = str.replaceAll("\u00EA\u0301", "\u1EBF"); // ế
		str = str.replaceAll("\u00EA\u0300", "\u1EC1"); // ề
		str = str.replaceAll("\u00EA\u0323", "\u1EC7"); // ệ
		str = str.replaceAll("\u00EA\u0303", "\u1EC5"); // ễ
		str = str.replaceAll("\u0079\u0309", "\u1EF7"); // ỷ
		str = str.replaceAll("\u0079\u0301", "\u00FD"); // ý
		str = str.replaceAll("\u0079\u0300", "\u1EF3"); // ỳ
		str = str.replaceAll("\u0079\u0323", "\u1EF5"); // ỵ
		str = str.replaceAll("\u0079\u0303", "\u1EF9"); // ỹ
		str = str.replaceAll("\u0075\u0309", "\u1EE7"); // ủ
		str = str.replaceAll("\u0075\u0301", "\u00FA"); // ú
		str = str.replaceAll("\u0075\u0300", "\u00F9"); // ù
		str = str.replaceAll("\u0075\u0323", "\u1EE5"); // ụ
		str = str.replaceAll("\u0075\u0303", "\u0169"); // ũ
		str = str.replaceAll("\u01B0\u0309", "\u1EED"); // ử
		str = str.replaceAll("\u01B0\u0301", "\u1EE9"); // ứ
		str = str.replaceAll("\u01B0\u0300", "\u1EEB"); // ừ
		str = str.replaceAll("\u01B0\u0323", "\u1EF1"); // ự
		str = str.replaceAll("\u01B0\u0303", "\u1EEF"); // ữ
		str = str.replaceAll("\u0069\u0309", "\u1EC9"); // ỉ
		str = str.replaceAll("\u0069\u0301", "\u00ED"); // í
		str = str.replaceAll("\u0069\u0300", "\u00EC"); // ì
		str = str.replaceAll("\u0069\u0323", "\u1ECB"); // ị
		str = str.replaceAll("\u0069\u0303", "\u0129"); // ĩ
		str = str.replaceAll("\u006F\u0309", "\u1ECF"); // ỏ
		str = str.replaceAll("\u006F\u0301", "\u00F3"); // ó
		str = str.replaceAll("\u006F\u0300", "\u00F2"); // ò
		str = str.replaceAll("\u006F\u0323", "\u1ECD"); // ọ
		str = str.replaceAll("\u006F\u0303", "\u00F5"); // õ
		str = str.replaceAll("\u01A1\u0309", "\u1EDF"); // ở
		str = str.replaceAll("\u01A1\u0301", "\u1EDB"); // ớ
		str = str.replaceAll("\u01A1\u0300", "\u1EDD"); // ờ
		str = str.replaceAll("\u01A1\u0323", "\u1EE3"); // ợ
		str = str.replaceAll("\u01A1\u0303", "\u1EE1"); // ỡ
		str = str.replaceAll("\u00F4\u0309", "\u1ED5"); // ổ
		str = str.replaceAll("\u00F4\u0301", "\u1ED1"); // ố
		str = str.replaceAll("\u00F4\u0300", "\u1ED3"); // ồ
		str = str.replaceAll("\u00F4\u0323", "\u1ED9"); // ộ
		str = str.replaceAll("\u00F4\u0303", "\u1ED7"); // ỗ
		str = str.replaceAll("\u0061\u0309", "\u1EA3"); // ả
		str = str.replaceAll("\u0061\u0301", "\u00E1"); // á
		str = str.replaceAll("\u0061\u0300", "\u00E0"); // à
		str = str.replaceAll("\u0061\u0323", "\u1EA1"); // ạ
		str = str.replaceAll("\u0061\u0303", "\u00E3"); // ã
		str = str.replaceAll("\u0103\u0309", "\u1EB3"); // ẳ
		str = str.replaceAll("\u0103\u0301", "\u1EAF"); // ắ
		str = str.replaceAll("\u0103\u0300", "\u1EB1"); // ằ
		str = str.replaceAll("\u0103\u0323", "\u1EB7"); // ặ
		str = str.replaceAll("\u0103\u0303", "\u1EB5"); // ẵ
		str = str.replaceAll("\u00E2\u0309", "\u1EA9"); // ẩ
		str = str.replaceAll("\u00E2\u0301", "\u1EA5"); // ấ
		str = str.replaceAll("\u00E2\u0300", "\u1EA7"); // ầ
		str = str.replaceAll("\u00E2\u0323", "\u1EAD"); // ậ
		str = str.replaceAll("\u00E2\u0303", "\u1EAB"); // ẫ
		str = str.replaceAll("\u0045\u0309", "\u1EBA"); // Ẻ
		str = str.replaceAll("\u0045\u0301", "\u00C9"); // É
		str = str.replaceAll("\u0045\u0300", "\u00C8"); // È
		str = str.replaceAll("\u0045\u0323", "\u1EB8"); // Ẹ
		str = str.replaceAll("\u0045\u0303", "\u1EBC"); // Ẽ
		str = str.replaceAll("\u00CA\u0309", "\u1EC2"); // Ể
		str = str.replaceAll("\u00CA\u0301", "\u1EBE"); // Ế
		str = str.replaceAll("\u00CA\u0300", "\u1EC0"); // Ề
		str = str.replaceAll("\u00CA\u0323", "\u1EC6"); // Ệ
		str = str.replaceAll("\u00CA\u0303", "\u1EC4"); // Ễ
		str = str.replaceAll("\u0059\u0309", "\u1EF6"); // Ỷ
		str = str.replaceAll("\u0059\u0301", "\u00DD"); // Ý
		str = str.replaceAll("\u0059\u0300", "\u1EF2"); // Ỳ
		str = str.replaceAll("\u0059\u0323", "\u1EF4"); // Ỵ
		str = str.replaceAll("\u0059\u0303", "\u1EF8"); // Ỹ
		str = str.replaceAll("\u0055\u0309", "\u1EE6"); // Ủ
		str = str.replaceAll("\u0055\u0301", "\u00DA"); // Ú
		str = str.replaceAll("\u0055\u0300", "\u00D9"); // Ù
		str = str.replaceAll("\u0055\u0323", "\u1EE4"); // Ụ
		str = str.replaceAll("\u0055\u0303", "\u0168"); // Ũ
		str = str.replaceAll("\u01AF\u0309", "\u1EEC"); // Ử
		str = str.replaceAll("\u01AF\u0301", "\u1EE8"); // Ứ
		str = str.replaceAll("\u01AF\u0300", "\u1EEA"); // Ừ
		str = str.replaceAll("\u01AF\u0323", "\u1EF0"); // Ự
		str = str.replaceAll("\u01AF\u0303", "\u1EEE"); // Ữ
		str = str.replaceAll("\u0049\u0309", "\u1EC8"); // Ỉ
		str = str.replaceAll("\u0049\u0301", "\u00CD"); // Í
		str = str.replaceAll("\u0049\u0300", "\u00CC"); // Ì
		str = str.replaceAll("\u0049\u0323", "\u1ECA"); // Ị
		str = str.replaceAll("\u0049\u0303", "\u0128"); // Ĩ
		str = str.replaceAll("\u004F\u0309", "\u1ECE"); // Ỏ
		str = str.replaceAll("\u004F\u0301", "\u00D3"); // Ó
		str = str.replaceAll("\u004F\u0300", "\u00D2"); // Ò
		str = str.replaceAll("\u004F\u0323", "\u1ECC"); // Ọ
		str = str.replaceAll("\u004F\u0303", "\u00D5"); // Õ
		str = str.replaceAll("\u01A0\u0309", "\u1EDE"); // Ở
		str = str.replaceAll("\u01A0\u0301", "\u1EDA"); // Ớ
		str = str.replaceAll("\u01A0\u0300", "\u1EDC"); // Ờ
		str = str.replaceAll("\u01A0\u0323", "\u1EE2"); // Ợ
		str = str.replaceAll("\u01A0\u0303", "\u1EE0"); // Ỡ
		str = str.replaceAll("\u00D4\u0309", "\u1ED4"); // Ổ
		str = str.replaceAll("\u00D4\u0301", "\u1ED0"); // Ố
		str = str.replaceAll("\u00D4\u0300", "\u1ED2"); // Ồ
		str = str.replaceAll("\u00D4\u0323", "\u1ED8"); // Ộ
		str = str.replaceAll("\u00D4\u0303", "\u1ED6"); // Ỗ
		str = str.replaceAll("\u0041\u0309", "\u1EA2"); // Ả
		str = str.replaceAll("\u0041\u0301", "\u00C1"); // Á
		str = str.replaceAll("\u0041\u0300", "\u00C0"); // À
		str = str.replaceAll("\u0041\u0323", "\u1EA0"); // Ạ
		str = str.replaceAll("\u0041\u0303", "\u00C3"); // Ã
		str = str.replaceAll("\u0102\u0309", "\u1EB2"); // Ẳ
		str = str.replaceAll("\u0102\u0301", "\u1EAE"); // Ắ
		str = str.replaceAll("\u0102\u0300", "\u1EB0"); // Ằ
		str = str.replaceAll("\u0102\u0323", "\u1EB6"); // Ặ
		str = str.replaceAll("\u0102\u0303", "\u1EB4"); // Ẵ
		str = str.replaceAll("\u00C2\u0309", "\u1EA8"); // Ẩ
		str = str.replaceAll("\u00C2\u0301", "\u1EA4"); // Ấ
		str = str.replaceAll("\u00C2\u0300", "\u1EA6"); // Ầ
		str = str.replaceAll("\u00C2\u0323", "\u1EAC"); // Ậ
		str = str.replaceAll("\u00C2\u0303", "\u1EAA"); // Ẫ
		return str;
	}
}