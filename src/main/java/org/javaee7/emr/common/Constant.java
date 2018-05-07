package org.javaee7.emr.common;

import java.io.File;

public class Constant {
	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final char SPACE_CHAR = ' ';
	public static final String SPLIT_DOT = "\\.";
	public static final String NEW_LINE = "\r\n";
	public static final String SEPARATOR_FILE = File.separator;
	public static final String SLASH = "/";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String COLON = ":";

	public static final String SEMICOLON = ";";
	public static final String UNDER_LINE = "_";
	public static final String SEPARATOR_URL = "/";
	public static final String BLANK = " ";
	public static final String BLANK_2BYTE = "  ";
	public static final String BLANK_16BYTE = "                ";
	public static final String ALL = "*";
	public static final String PERCENT = "%";
	public static final String LINE_FEED = "\\u000a";

	public static final String ENCODING = "UTF-8";
	public static final String ENCODING_EXCEL = "x-MS932_0213";
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String SEPARATOR_ID = ",";
	public static final String SEPARATOR_FILE_NAME = ":";

	public static final Integer MALE = 1;
	public static final Integer FEMALE = 2;
	public static final Integer MARITAL_STATUS_SINGLE = 0;

	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final Integer ZERO = 0;
	public static final Long ZERO_L = 0L;
	public static final Double ZERO_D = 0D;

	public static final Integer ON = 1;
	public static final Integer OFF = 0;

	public static final Integer AVAIABLE = 0;
	public static final Integer DISABLED = 1;

	public static final Integer MAIN_LAYOUT = 1;
	public static final Integer SUB_LAYOUT = 0;

	public static final Integer PRINTED = 1;
	public static final Integer NOT_PRINTED = 0;

	public static final Number SQL_PARAM_NUMBER_NULL = 0;

	// Extension
	public static final String EXTENSION_PAGE = ".html";

	public static final String EXTENSION_JASPER = ".jasper";
	public static final String EXTENSION_PDF = ".pdf";
	public static final String EXTENSION_XLS = ".xls";
	public static final String EXTENSION_JXL = ".jxl.xls";
	public static final String EXTENSION_CSV = ".csv";
	public static final String EXTENSION_ODT = ".odt";
	public static final String EXTENSION_ODS = ".ods";
	public static final String EXTENSION_DOCX = ".docx";
	public static final String EXTENSION_XLSX = ".xlsx";
	public static final String EXTENSION_PPTX = ".pptx";
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_HTML = ".html";
	public static final String EXTENSION_RTF = ".rtf";
	public static final String EXTENSION_ZIP = ".zip";
	public static final String EXTENSION_TIF = ".tif";
	public static final String EXTENSION_DAT = ".DAT";
	public static final String EXTENSION_JPEG = ".jpeg";
	public static final String EXTENSION_PNG = ".png";

	public static final String FILE_TYPE_JASPER = "jasper";
	public static final String FILE_TYPE_PDF = "pdf";
	public static final String FILE_TYPE_XLS = "xls";
	public static final String FILE_TYPE_JXL = "jxl.xls";
	public static final String FILE_TYPE_CSV = "csv";
	public static final String FILE_TYPE_ODT = "odt";
	public static final String FILE_TYPE_ODS = "ods";
	public static final String FILE_TYPE_DOCX = "docx";
	public static final String FILE_TYPE_XLSX = "xlsx";
	public static final String FILE_TYPE_PPTX = "pptx";
	public static final String FILE_TYPE_XML = "xml";
	public static final String FILE_TYPE_HTML = "html";
	public static final String FILE_TYPE_XHTML = "xhtml";
	public static final String FILE_TYPE_RTF = "rtf";
	public static final String FILE_TYPE_ZIP = "zip";
	public static final String FILE_TYPE_TIF = "tif";
	public static final String FILE_TYPE_DAT = "DAT";
	public static final String FILE_TYPE_JPEG = "jpeg";
	public static final String FILE_TYPE_PNG = "png";

	public static final String CONTENT_TYPE_DEFAULT = "application/pdf";
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
	public static final String CONTENT_TYPE_JXL = "application/jxl";
	public static final String CONTENT_TYPE_CSV = "application/vnd.ms-excel";
	public static final String CONTENT_TYPE_ODT = "application/vnd.oasis.opendocument.text";
	public static final String CONTENT_TYPE_ODS = "application/vnd.oasis.opendocument.spreadsheet";
	public static final String CONTENT_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	public static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_TYPE_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String CONTENT_TYPE_XHTML = "application/xhtml+xml";
	public static final String CONTENT_TYPE_RTF = "application/rtf";
	public static final String CONTENT_TYPE_ZIP = "application/zip";
	public static final String CONTENT_TYPE_JPEG = "image/jpeg";
	public static final String CONTENT_TYPE_PNG = "image/png";

	public static final String UNIT_TIME_BLANK = " ";
	public static final String UNIT_TIME_YEAR = "y";
	public static final String UNIT_TIME_MONTH = "m";
	public static final String UNIT_TIME_DAY = "d";
	public static final String UNIT_TIME_WEEK = "w";
	public static final String UNIT_TIME_HOUR = "h";
	public static final String UNIT_TIME_MINUTE = "i";

	public static final Integer LEVEL_DATA_SELF = 1;
	public static final Integer LEVEL_DATA_GROUP = 3;
	public static final Integer LEVEL_DATA_DEPARTMENT = 4;
	public static final Integer LEVEL_DATA_BRANCH = 5;
	public static final Integer LEVEL_DATA_COMPANY = 6;

	public static final String PARTNER_EMPLOYEE = "E";
	public static final String PARTNER_SUPPLIER = "S";
	public static final String PARTNER_FORWARDER = "F";
	public static final String PARTNER_CUSTOMER = "C";
	public static final String PARTNER_OTHER = "O";

	public static final String EXPORT_PDF = "exportPdfAction";
	public static final String EXPORT_XLS = "exportExcelAction";
	public static final String EXPORT_CSV = "exportCsvAction";
	public static final String EXPORT_DOC = "exportDocAction";
	public static final String EXPORT_PPT = "exportPptAction";
	public static final String EXPORT_IMAGE = "exportImageAction";
	public static final String EXPORT_HTML = "exportHtmlAction";
	public static final String EXPORT_DOCX = "exportDocxAction";
	public static final String EXPORT_XLSX = "exportExcelxAction";
	public static final String EXPORT_PPTX = "exportPptxAction";
	public static final String EXPORT_XML = "exportXmlAction";
	public static final String EXPORT_RTF = "exportHtmlAction";

	public static final Integer TAB_RECEIPT = 1;
	public static final Integer TAB_PAYMENT = 2;
	public static final Integer TAB_TICKET = 3;
	public static final Integer TAB_INVOICE = 4;
	public static final Integer TAB_STOCK_OUT = 5;
	public static final Integer TAB_STOCK_IN = 6;
	public static final Integer TAB_INFO_OTHER = 9;

	public static final Integer TAB_ITEMS = 1;
	public static final Integer TAB_CANCELED = 2;
	public static final Integer TAB_DOCUMENT = 3;
	public static final Integer TAB_ACCOUNTANT = 4;
	public static final Integer TAB_ADJUSTITEM = 5;
	public static final Integer TAB_SUMITEM = 6;
	public static final Integer TAB_ITEM_OTHER = 9;

	public static final Integer TAB_TOTAL = 1;
	public static final Integer TAB_CARD = 2;
	public static final Integer TAB_TRANSFER = 3;
	public static final Integer TAB_ADVANCE = 4;
	public static final Integer TAB_VOUCHER = 5;
	public static final Integer TAB_INSURANCE = 6;
	public static final Integer TAB_SUM_OTHER = 9;

	public static final Integer TAB_WAITING = 1;
	public static final Integer TAB_PROCESSING = 2;
	public static final Integer TAB_SUBMIT = 3;
	public static final Integer TAB_COMPLETED = 4;

	public static final Integer TAB_WORK_IN = 1;
	public static final Integer TAB_WORK_OUT = 2;
	public static final Integer TAB_WORK_ALL = 3;

	public static final Integer TAB_DATA_LIST = 1;
	public static final Integer TAB_DATA_LIST_ITEM = 2;
	public static final Integer TAB_DATA_LIST_SKU = 3;
	public static final Integer TAB_DATA_LIST_INFO = 4;
	public static final Integer TAB_DATA_LIST_STOCK = 5;
	public static final Integer TAB_DATA_LIST_INOUT = 6;
	public static final Integer TAB_DATA_LIST_GOODS = 7;

	public static final Integer TAB_DATA_SERVICE = 1;
	public static final Integer TAB_DATA_GOODS = 2;

	public static final Integer SORT_CODE = 1;
	public static final Integer SORT_NAME = 2;
	public static final Integer SORT_STOCK = 3;
	public static final Integer SORT_LOT = 4;
	public static final Integer SORT_EXPIRED = 5;
	public static final Integer SORT_MODEL = 6;
	public static final Integer SORT_SERIAL = 7;
	public static final Integer SORT_CATEGORY = 8;
	public static final Integer SORT_WAREHOUSE = 9;
	public static final Integer SORT_POSITON = 10;
	public static final Integer SORT_FINACIAL_CODE = 11;
	public static final Integer SORT_FINACIAL_DATE = 12;
	public static final Integer SORT_INVENTORY_CODE = 13;
	public static final Integer SORT_INVENTORY_DATE = 14;
	public static final Integer SORT_PARTNER_CODE = 15;
	public static final Integer SORT_PARTNER_NAME = 16;
	public static final Integer SORT_BUSINESS_CODE = 17;
	public static final Integer SORT_BUSINESS_NAME = 18;

	public static String ASC = "asc";
	public static String DESC = "desc";

	public static Integer OPTION_STANDARD = 1;
	public static Integer OPTION_REPLACE = 2;
	public static Integer OPTION_PAY_MONEY = 3;

	public static Integer TAB_ALL = 1;
	public static Integer TAB_ACTIVE = 2;
	public static Integer TAB_DISABLED = 3;
}
