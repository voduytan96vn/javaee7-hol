package org.javaee7.emr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@NamedQueries({
		@NamedQuery(name = "LanguageResources.searchAll", query = "select o from LanguageResources o where o.deletedBy is null and o.companyId = :companyId and upper(o.value) like :value order by o.sort"),
		@NamedQuery(name = "LanguageResources.findAll", query = "select o from LanguageResources o where o.deletedBy is null order by o.companyId, o.language, o.category, o.typeGroup, o.sort"),
		@NamedQuery(name = "LanguageResources.searchAdvance", query = "select o from LanguageResources o where o.deletedBy is null and (:companyId = 0 or o.companyId = :companyId) and (:language = '' or o.language = :language) and (:category = '' or o.category = :category) and (:typeGroup = '' or o.typeGroup = :typeGroup) and (o.key like :key) ") })
@Table(name = "language_resources")
public class LanguageResources extends DaoEntity implements Serializable {
	private static final long serialVersionUID = 7480832870954302926L;

	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "language")
	private String language;

	@Column(name = "category")
	private String category;

	@Column(name = "type_group")
	private String typeGroup;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	@Column(name = "sort")
	private Integer sort;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Long createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date")
	private Long updatedDate;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Column(name = "deleted_date")
	private Long deletedDate;

	@Version
	@Column(name = "version")
	private Integer version = 0;

	public LanguageResources() {
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTypeGroup() {
		return this.typeGroup;
	}

	public void setTypeGroup(String typeGroup) {
		this.typeGroup = typeGroup;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Long getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Long deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isEmpty() {
		boolean ret = false;
		if ((this.key == null || "".equals(this.key.trim())) || (this.value == null || "".equals(this.value.trim()))) {
			ret = true;
		}
		return ret;
	}
}