/**
 * 
 */
/**
 * @author voduy
 *
 */
package org.javaee7.emr.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.javaee7.emr.common.Constant;

public class LoginInfo implements Serializable {
	private static final long serialVersionUID = -1803634850216573073L;

	private Long partnerId;

	private Long companyId;

	private Locale locale = Locale.forLanguageTag("vi-VN");
	private TimeZone zone = TimeZone.getTimeZone("GMT+07:00");

	private Map<String, String> business = new HashMap<String, String>();
	private Map<String, Map<String, String>> mapControl = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, String>> mapActionControl = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, Boolean>> mapRendered = new HashMap<String, Map<String, Boolean>>();
	private Map<String, Map<String, Boolean>> mapDisabled = new HashMap<String, Map<String, Boolean>>();
	private Map<String, Map<String, Boolean>> mapHidden = new HashMap<String, Map<String, Boolean>>();
	private Map<String, Map<String, Boolean>> mapReadOnly = new HashMap<String, Map<String, Boolean>>();

	private Map<String, Integer> levelData = new HashMap<String, Integer>();
	private Map<String, Boolean> approveLevel1 = new HashMap<String, Boolean>();
	private Map<String, Boolean> approveLevel2 = new HashMap<String, Boolean>();
	private Map<String, Boolean> approveLevel3 = new HashMap<String, Boolean>();

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Map<String, String> getBusiness() {
		return business;
	}

	public void setBusiness(Map<String, String> business) {
		this.business = business;
	}

	public Map<String, Map<String, String>> getMapControl() {
		return mapControl;
	}

	public void setMapControl(Map<String, Map<String, String>> mapControl) {
		this.mapControl = mapControl;
	}

	public Map<String, Map<String, String>> getMapActionControl() {
		return mapActionControl;
	}

	public void setMapActionControl(Map<String, Map<String, String>> mapActionControl) {
		this.mapActionControl = mapActionControl;
	}

	public Map<String, Integer> getLevelData() {
		return levelData;
	}

	public void setLevelData(Map<String, Integer> levelData) {
		this.levelData = levelData;
	}

	public Integer getLevelData(String business) {
		Integer levelData = this.levelData.get(business);
		return levelData != null ? levelData : Constant.LEVEL_DATA_SELF;
	}

	public boolean isLevelDataSelf(String business) {
		Integer levelData = this.levelData.get(business);
		return Constant.LEVEL_DATA_SELF.equals(levelData);
	}

	public boolean isLevelDataGroup(String business) {
		Integer levelData = this.levelData.get(business);
		return Constant.LEVEL_DATA_GROUP.equals(levelData);
	}

	public boolean isLevelDataDepartment(String business) {
		Integer levelData = this.levelData.get(business);
		return Constant.LEVEL_DATA_DEPARTMENT.equals(levelData);
	}

	public boolean isLevelDataBranch(String business) {
		Integer levelData = this.levelData.get(business);
		return Constant.LEVEL_DATA_BRANCH.equals(levelData);
	}

	public boolean isLevelDataCompany(String business) {
		Integer levelData = this.levelData.get(business);
		return Constant.LEVEL_DATA_COMPANY.equals(levelData);
	}

	public Map<String, Map<String, Boolean>> getMapRendered() {
		return mapRendered;
	}

	public void setMapRendered(Map<String, Map<String, Boolean>> mapRendered) {
		this.mapRendered = mapRendered;
	}

	public Map<String, Map<String, Boolean>> getMapDisabled() {
		return mapDisabled;
	}

	public void setMapDisabled(Map<String, Map<String, Boolean>> mapDisabled) {
		this.mapDisabled = mapDisabled;
	}

	public Map<String, Map<String, Boolean>> getMapHidden() {
		return mapHidden;
	}

	public void setMapHidden(Map<String, Map<String, Boolean>> mapHidden) {
		this.mapHidden = mapHidden;
	}

	public Map<String, Map<String, Boolean>> getMapReadOnly() {
		return mapReadOnly;
	}

	public void setMapReadOnly(Map<String, Map<String, Boolean>> mapReadOnly) {
		this.mapReadOnly = mapReadOnly;
	}

	public Map<String, Boolean> getRendered(String business) {
		return mapRendered != null ? mapRendered.get(business) : new HashMap<String, Boolean>();
	}

	public Map<String, Boolean> getDisabled(String business) {
		return mapDisabled != null ? mapDisabled.get(business) : new HashMap<String, Boolean>();
	}

	public Map<String, Boolean> getHidden(String business) {
		return mapHidden != null ? mapHidden.get(business) : new HashMap<String, Boolean>();
	}

	public Map<String, Boolean> getReadOnly(String business) {
		return mapReadOnly != null ? mapReadOnly.get(business) : new HashMap<String, Boolean>();
	}

	public Map<String, Boolean> getApproveLevel1() {
		return approveLevel1;
	}

	public void setApproveLevel1(Map<String, Boolean> approveLevel1) {
		this.approveLevel1 = approveLevel1;
	}

	public Map<String, Boolean> getApproveLevel2() {
		return approveLevel2;
	}

	public void setApproveLevel2(Map<String, Boolean> approveLevel2) {
		this.approveLevel2 = approveLevel2;
	}

	public Map<String, Boolean> getApproveLevel3() {
		return approveLevel3;
	}

	public void setApproveLevel3(Map<String, Boolean> approveLevel3) {
		this.approveLevel3 = approveLevel3;
	}

	public int getApproveLevel(String business) {
		int level = 0;
		if (isApproveLevel3(business)) {
			level = 3;
		} else if (isApproveLevel2(business)) {
			level = 2;
		} else if (isApproveLevel1(business)) {
			level = 1;
		}
		return level;
	}

	public boolean isApproveLevel1(String business) {
		Boolean approve = approveLevel1.get(business);
		return approve != null ? approve.booleanValue() : false;
	}

	public boolean isApproveLevel2(String business) {
		Boolean approve = approveLevel2.get(business);
		return approve != null ? approve.booleanValue() : false;
	}

	public boolean isApproveLevel3(String business) {
		Boolean approve = approveLevel3.get(business);
		return approve != null ? approve.booleanValue() : false;
	}
}