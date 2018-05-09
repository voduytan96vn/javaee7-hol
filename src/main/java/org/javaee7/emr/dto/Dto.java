package org.javaee7.emr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.model.SelectItem;

import org.javaee7.emr.common.Constant;
import org.javaee7.emr.view.Lib;

import net.sf.json.JSONObject;

public class Dto implements Serializable {
	public static final String EMPTY_JSON = "{}";

	// private String locale = "vi_VN";
	private Locale locale = Locale.getDefault();
	private TimeZone zone = TimeZone.getDefault();
	private MessageDto messageDto;

	private String mode = "edit";
	private String screen;
	private String loadScreen;

	private String standardAction = Constant.EXPORT_PDF;
	private List<SelectItem> exportTypeItems;

	private Long id;
	private String code;
	private String statusName;
	private String tabActive;
	private String tabDetailActive;
	private String contextPath;
	private String reportPath;
	private byte[] dataExport;
	private String tabIndex = "";

	private Long idSelected;
	private int rowSelected;
	private int rowDetailSelected;
	private int rowChanged;

	private Map<String, Boolean> renderedLogin;
	private Map<String, Boolean> disabledLogin;
	private Map<String, Boolean> hiddenLogin;
	private Map<String, Boolean> readOnlyLogin;

	private Map<String, Boolean> rendered;
	private Map<String, Boolean> disabled;
	private Map<String, Boolean> hidden;
	private Map<String, Boolean> readOnly;

	private Map<String, Object> pin;
	private String adminUser;
	private String adminPass;
	private JSONObject configData;

	private String searchText;

	private int tabData = Constant.TAB_ALL;

	public Dto() {
		this.rendered = new HashMap<String, Boolean>();
		this.disabled = new HashMap<String, Boolean>();
		this.hidden = new HashMap<String, Boolean>();
		this.readOnly = new HashMap<String, Boolean>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getZone() {
		return zone;
	}

	public void setZone(TimeZone zone) {
		this.zone = zone;
	}

	public MessageDto getMessageDto() {
		return messageDto;
	}

	public void setMessageDto(MessageDto messageDto) {
		this.messageDto = messageDto;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getLoadScreen() {
		return loadScreen;
	}

	public void setLoadScreen(String loadScreen) {
		this.loadScreen = loadScreen;
	}

	public String getStandardAction() {
		return standardAction;
	}

	public void setStandardAction(String standardAction) {
		this.standardAction = standardAction;
	}

	public List<SelectItem> getExportTypeItems() {
		if (exportTypeItems == null) {
			exportTypeItems = new ArrayList<SelectItem>();
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_PDF, Lib.getResource(getLocale(), "common.combo.exportpdf"),
							Lib.getResource(getLocale(), "common.combo.exportpdf"), false));
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_XLSX, Lib.getResource(getLocale(), "common.combo.exportexcelx"),
							Lib.getResource(getLocale(), "common.combo.exportexcelx"), false));
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_CSV, Lib.getResource(getLocale(), "common.combo.exportcsv"),
							Lib.getResource(getLocale(), "common.combo.unsupport"), true));
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_DOCX, Lib.getResource(getLocale(), "common.combo.exportdocx"),
							Lib.getResource(getLocale(), "common.combo.exportdocx"), false));
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_IMAGE, Lib.getResource(getLocale(), "common.combo.exportimage"),
							Lib.getResource(getLocale(), "common.combo.unsupport"), true));
			exportTypeItems
					.add(new SelectItem(Constant.EXPORT_HTML, Lib.getResource(getLocale(), "common.combo.exporthtml"),
							Lib.getResource(getLocale(), "common.combo.exporthtml"), false));
		}
		return exportTypeItems;
	}

	public void setExportTypeItems(List<SelectItem> exportTypeItems) {
		this.exportTypeItems = exportTypeItems;
	}

	public String getTabActive() {
		return tabActive;
	}

	public void setTabActive(String tabActive) {
		this.tabActive = tabActive;
	}

	public String getTabDetailActive() {
		return tabDetailActive;
	}

	public void setTabDetailActive(String tabDetailActive) {
		this.tabDetailActive = tabDetailActive;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public byte[] getDataExport() {
		return dataExport;
	}

	public void setDataExport(byte[] dataExport) {
		this.dataExport = dataExport;
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Long getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(Long idSelected) {
		this.idSelected = idSelected;
	}

	public int getRowSelected() {
		return rowSelected;
	}

	public void setRowSelected(int rowSelected) {
		this.rowSelected = rowSelected;
	}

	public int getRowDetailSelected() {
		return rowDetailSelected;
	}

	public void setRowDetailSelected(int rowDetailSelected) {
		this.rowDetailSelected = rowDetailSelected;
	}

	public int getRowChanged() {
		return rowChanged;
	}

	public void setRowChanged(int rowChanged) {
		this.rowChanged = rowChanged;
	}

	public String getLabel(List<SelectItem> items, Object value) {
		String label = null;
		if (value != null && items != null) {
			for (SelectItem e : items) {
				if (value.equals(e.getValue())) {
					label = e.getLabel();
					break;
				}
			}
		}
		return label;
	}

	public String getTimeUnitName(String value) {
		return Lib.getResource(getLocale(), "unit.time.".concat(value));
	}

	public String getLevelData(Integer value) {
		return Lib.getResource(getLocale(), "level.data.".concat("" + value));
	}

	public Map<String, Boolean> getRendered() {
		return rendered;
	}

	public void setRendered(Map<String, Boolean> rendered) {
		this.rendered = rendered;
	}

	public Map<String, Boolean> getDisabled() {
		return disabled;
	}

	public void setDisabled(Map<String, Boolean> disabled) {
		this.disabled = disabled;
	}

	public Map<String, Boolean> getHidden() {
		return hidden;
	}

	public void setHidden(Map<String, Boolean> hidden) {
		this.hidden = hidden;
	}

	public Map<String, Boolean> getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Map<String, Boolean> readOnly) {
		this.readOnly = readOnly;
	}

	public boolean rendered(String id) {
		Boolean value = this.rendered.get(id);
		return value != null ? value.booleanValue() : true;
	}

	public void rendered(boolean rendered, String... ids) {
		if (ids != null) {
			Boolean value = null;
			for (String id : ids) {
				value = this.renderedLogin.get(id);
				value = value == null ? true : value;
				this.rendered.put(id, rendered && value);
			}
		}
	}

	public void rendered(Map<String, Boolean> rendered) {
		if (rendered != null) {
			this.rendered.putAll(rendered);
		}
	}

	public void rendered(Map<String, Boolean> rendered, boolean clear) {
		rendered = rendered == null ? new HashMap<String, Boolean>() : rendered;
		if (clear) {
			this.rendered.clear();
			this.renderedLogin = rendered;
		}
		this.rendered.putAll(rendered);
	}

	public boolean disabled(String id) {
		Boolean value = this.disabled.get(id);
		return value != null ? value.booleanValue() : false;
	}

	public void disabled(boolean disabled, String... ids) {
		if (ids != null) {
			Boolean value = null;
			for (String id : ids) {
				value = this.disabledLogin.get(id);
				value = value == null ? false : value;
				this.disabled.put(id, disabled || value);
			}
		}
	}

	public void disabled(Map<String, Boolean> disabled) {
		if (disabled != null) {
			this.disabled.putAll(disabled);
		}
	}

	public void disabled(Map<String, Boolean> disabled, boolean clear) {
		disabled = disabled == null ? new HashMap<String, Boolean>() : disabled;
		if (clear) {
			this.disabled.clear();
			this.disabledLogin = disabled;
		}
		this.disabled.putAll(disabled);
	}

	public boolean hidden(String id) {
		Boolean value = this.hidden.get(id);
		return value != null ? value.booleanValue() : false;
	}

	public void hidden(boolean hidden, String... ids) {
		if (ids != null) {
			Boolean value = null;
			for (String id : ids) {
				value = this.hiddenLogin.get(id);
				value = value == null ? false : value;
				this.hidden.put(id, hidden || value);
			}
		}
	}

	public void hidden(Map<String, Boolean> hidden) {
		if (hidden != null) {
			this.hidden.putAll(hidden);
		}
	}

	public void hidden(Map<String, Boolean> hidden, boolean clear) {
		hidden = hidden == null ? new HashMap<String, Boolean>() : hidden;
		if (clear) {
			this.hidden.clear();
			this.hiddenLogin = hidden;
		}
		this.hidden.putAll(hidden);
	}

	public boolean readOnly(String id) {
		Boolean value = this.readOnly.get(id);
		return value != null ? value.booleanValue() : false;
	}

	public void readOnly(boolean readOnly, String... ids) {
		if (ids != null) {
			Boolean value = null;
			for (String id : ids) {
				value = this.readOnlyLogin.get(id);
				value = value == null ? false : value;
				this.readOnly.put(id, readOnly || value);
			}
		}
	}

	public void readOnly(Map<String, Boolean> readOnly) {
		if (readOnly != null) {
			this.readOnly.putAll(readOnly);
		}
	}

	public void readOnly(Map<String, Boolean> readOnly, boolean clear) {
		readOnly = readOnly == null ? new HashMap<String, Boolean>() : readOnly;
		if (clear) {
			this.readOnly.clear();
			this.readOnlyLogin = readOnly;
		}
		this.readOnly.putAll(readOnly);
	}

	public String getAge(Long birthDate) {
		String ret = "";
		if (birthDate == null) {
			return ret;
		}
		Calendar cal = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTimeInMillis(birthDate);
		int age = cal.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		if (age > 0) {
			ret = age + " Tuổi";
		} else {
			age = cal.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
			if (age > 0) {
				ret = age + " Tháng";
			} else {
				age = cal.get(Calendar.DATE) - birth.get(Calendar.DATE);
				ret = age + " Ngày";
			}
		}
		return ret;
	}

	public String getGender(Integer gender) {
		String ret = "";
		if (gender == null) {
			return ret;
		}

		if (gender == 1) {
			ret = "Nam";
		} else {
			ret = "Nữ";
		}

		return ret;
	}

	public Map<String, Object> getPin() {
		return pin;
	}

	public void setPin(Map<String, Object> pin) {
		this.pin = pin;
	}

	public Object getPin(String key) {
		return pin != null ? pin.get(key) : null;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public JSONObject getConfigData() {
		return configData;
	}

	public void setConfigData(JSONObject configData) {
		this.configData = configData;
	}

	public boolean tabIndex(String val) {
		String[] str = val.split(",");
		for (String e : str) {
			if (e.equals(this.tabIndex)) {
				return true;
			}
		}
		return false;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getTabData() {
		return tabData;
	}

	public void setTabData(int tabData) {
		this.tabData = tabData;
	}

	public boolean isTabDataAll() {
		return Constant.TAB_ALL.equals(this.tabData);
	}

	public void setTabDataAll(boolean tabData) {
		this.tabData = tabData ? Constant.TAB_ALL : this.tabData;
	}

	public boolean isTabDataActive() {
		return Constant.TAB_ACTIVE.equals(this.tabData);
	}

	public void setTabDataActive(boolean tabData) {
		this.tabData = tabData ? Constant.TAB_ACTIVE : this.tabData;
	}

	public boolean isTabDataDisabled() {
		return Constant.TAB_DISABLED.equals(this.tabData);
	}

	public void setTabDataDisabled(boolean tabData) {
		this.tabData = tabData ? Constant.TAB_DISABLED : this.tabData;
	}
}
