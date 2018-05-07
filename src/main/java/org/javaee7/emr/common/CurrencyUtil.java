/**
 * 
 */
/**
 * @author voduy
 *
 */
package org.javaee7.emr.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrencyUtil {

	/**
	 * Convert number to text
	 * 
	 * @param num
	 * @param currencyName
	 * @param locale
	 * @return
	 */

	/**
	 * 
	 * @param num
	 * @param currencyName
	 * @return
	 */
	public static String toWordsVi(Number num, String nameMajor, String nameMinor) {
		if (num == null) {
			return "";
		}

		long numLong = num.longValue();
		long numAfter = Math.round(((num.doubleValue() - numLong) * 100));

		String value = convertVi(numLong);
		value = value.trim();
		if (value.startsWith("không")) {
			value = "";
		} else {
			if (nameMajor != null && !nameMajor.trim().isEmpty()) {
				value = value.trim() + " " + nameMajor;
				if (numAfter == 0) {
					value = value.trim() + " chẵn";
				}
			}
		}

		if (numAfter > 0) {
			value = value.trim() + " và " + convertVi(numAfter);
			if (nameMinor != null && !nameMinor.trim().isEmpty()) {
				value = value.trim() + " " + nameMinor;
			}
		}

		if (!value.isEmpty()) {
			value = value.substring(0, 1).toUpperCase() + value.substring(1);
		}
		return value;
	}

	private static String convertVi(long number) {
		if (number == 0) {
			return "không";
		}

		String numRead = Long.toString(number);

		List<String> arrBillion = new ArrayList<String>();

		String billion = "";
		while (numRead.length() > 0) {
			if (numRead.length() < 9) {
				arrBillion.add(numRead);
				break;
			} else {
				billion = numRead.substring(numRead.length() - 9);
				numRead = numRead.substring(0, numRead.length() - 9);
				arrBillion.add(billion);
			}
		}

		String value = "";
		int size = arrBillion.size();
		if (size == 1) {
			value += toBillion(arrBillion.get(0), false);
		} else if (size == 2) {
			value += toBillion(arrBillion.get(1), false) + " tỷ ";
			value += toBillion(arrBillion.get(0), true);
		} else {
			value += toBillion(arrBillion.get(size - 1), false) + " " + getBillion(size - 1) + " ";
			for (int i = (size - 2); i > 0; i--) {
				value += toBillion(arrBillion.get(i), true) + " " + getBillion(i) + " ";
			}
			value += toBillion(arrBillion.get(0), true);
		}
		return value;
	}

	public static String getBillion(int repeat) {
		String value = "tỷ";
		for (int i = 1; i < repeat; i++) {
			value += " tỷ";
		}
		return value;
	}

	public static String toBillion(String num, boolean first) {
		if (num == null || num.isEmpty() || Long.valueOf(num) == 0) {
			return "";
		}

		List<String> arrThousand = new ArrayList<String>();

		String million = null;
		String thousand = null;
		String unit = null;
		while (num.length() > 0) {
			if (num.length() < 3) {
				arrThousand.add(num);
				break;
			} else {
				arrThousand.add(num.substring(num.length() - 3));
				num = num.substring(0, num.length() - 3);
			}
		}

		if (arrThousand.size() > 0) {
			unit = arrThousand.get(0);
		}

		if (arrThousand.size() > 1) {
			thousand = arrThousand.get(1);
		}

		if (arrThousand.size() > 2) {
			million = arrThousand.get(2);
		}

		String value = "";
		if (million != null && !million.isEmpty() && Integer.valueOf(million) > 0) {
			value += toThousand(million, first) + " triệu ";
		}

		if (thousand != null && !thousand.isEmpty() && Integer.valueOf(thousand) > 0) {
			value += toThousand(thousand, first) + " nghìn ";
		}

		if (unit != null && !unit.isEmpty() && Integer.valueOf(unit) > 0) {
			value += toThousand(unit, first);
		}

		return value.trim();
	}

	public static String toThousand(String num, boolean first) {
		if (num == null || num.isEmpty() || Integer.valueOf(num) == 0) {
			return "";
		}
		String value = "";
		String text[] = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };

		int unit = 0;
		int dozen = 0;
		int hundred = 0;

		int len = num.length();
		if (len > 0) {
			unit = Integer.valueOf(num.substring(len - 1));
		}
		if (len > 1) {
			dozen = Integer.valueOf(num.substring(len - 2, len - 1));
		}
		if (len > 2) {
			hundred = Integer.valueOf(num.substring(len - 3, len - 2));
		}

		if (unit == 0 && dozen == 0 && hundred == 0) {
			value = "";
		} else {
			if (hundred == 0 && dozen == 0) {
				if (first) {
					value += "linh ";
				}
				value += text[unit];
			} else {
				if (hundred != 0 && dozen == 0) {
					if (unit == 0) {
						value += text[hundred] + " trăm";
					} else {
						value += text[hundred] + " trăm linh " + text[unit];
					}
				} else if (hundred == 0 && dozen != 0) {
					if (first) {
						value += "không trăm ";
					}

					if (dozen == 1) {
						value += " mười";
					} else {
						value += " " + text[dozen] + " mươi";
					}

					if (unit == 1) {
						if (dozen == 1) {
							value += " một";
						} else {
							value += " mốt";
						}
					} else if (unit == 5) {
						value += " lăm";
					} else if (unit == 0) {
						value += "";
					} else {
						value += " " + text[unit];
					}
				} else {
					value = text[hundred] + " trăm";
					if (dozen == 1) {
						value += " mười";
					} else {
						value += " " + text[dozen] + " mươi";
					}

					if (unit == 1) {
						if (dozen == 1) {
							value += " một";
						} else {
							value += " mốt";
						}
					} else if (unit == 5) {
						value += " lăm";
					} else if (unit == 0) {
						value += "";
					} else {
						value += " " + text[unit];
					}
				}
			}
		}

		return value.trim();
	}

	/**
	 * 
	 * @param num
	 * @param currencyName
	 * @return
	 */
	public static String toWordsEn(Number num, String nameMajor, String nameMinor) {
		if (num == null) {
			return "";
		}

		long numLong = num.longValue();
		long numAfter = Math.round(((num.doubleValue() - numLong) * 100));

		String value = convertEn(numLong);
		value = value.trim();
		if (value.startsWith("zero")) {
			value = "";
		} else {
			if (nameMajor != null && !nameMajor.trim().isEmpty()) {
				value = value.trim() + " " + nameMajor;
			}
		}

		if (numAfter > 0) {
			value = value.trim() + " and " + convertEn(numAfter);
			if (nameMinor != null && !nameMinor.trim().isEmpty()) {
				value = value.trim() + " " + nameMinor;
			}
		}

		if (!value.isEmpty()) {
			value = value.substring(0, 1).toUpperCase() + value.substring(1);
		}
		return value;
	}

	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };
	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	private static String convertEn(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);
		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);
		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

}
