package org.javaee7.emr.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "baseBean")
@ViewScoped
public abstract class BaseBean extends CoreBean {
	public static final String FROM_BUSINESS_CODE = "fromBusinessCode";
	public static final String FROM_BUSINESS_ID = "fromBusinessId";
	public static final String BUSINESS_ADD_MORE = "businessAddMore";
	public static final String REPORT_DATA = "reportData";
	public static final String REPORT_FILE_NAME = "reportFileName";
	public static final String REPORT_CONTENT_TYPE = "reportContentType";
	public static final String REPORT_ENCODING = "reportEncoding";
	public static final String TITLE = "title";
	public static final String WINDOW_NAME = "windowName";
	public static final String P_SCREEN = "screen";
	public static final String MODE = "mode";
	public static final String HANDLE_RETURN = "handleReturn";

	public static final String POPUP_SRC = "popupSrc";
	public static final String POPUP_TITLE = "popupTitle";
	public static final String POPUP_URI = "popupUri";
	public static final String POPUP_MODE = "popupMode";
	public static final String POPUP_HANDLE_RETURN = "popupHandleReturn";
	public static final String POPUP_SHOW = "popupShow";

	public static final String MODE_SHOW = "show";
	public static final String MODE_EDIT = "edit";
	public static final String MODE_LIST = "list";
	public static final String MODE_SEARCH = "search";

	public static final String LOGIN = "login";

	private boolean isPopupPage = false;

	public BaseBean() {
		super();
	}

	public abstract void init() throws Exception;

	public boolean isPopupPage() {
		return isPopupPage;
	}
}
