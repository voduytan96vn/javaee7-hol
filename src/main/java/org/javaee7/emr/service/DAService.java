package org.javaee7.emr.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;
import org.javaee7.emr.dto.LoginInfo;
import org.javaee7.emr.entities.DaoEntity;
import org.javaee7.emr.entities.GenId;
import org.javaee7.emr.entities.GenSeq;

public class DAService extends DaoService {
	public static final Logger log = Logger.getLogger("HUB");

	protected LoginInfo loginInfo;
	protected String businessCode;
	protected String businessName;
	protected String serviceName;

	public DAService() {
		super(new Locale("vi", "VN"), TimeZone.getTimeZone("GMT+07:00"));
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		this.locale = loginInfo.getLocale();
		this.zone = loginInfo.getZone();
	}

	/**
	 * Gen id
	 * 
	 * @param name
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	synchronized public Long getGlobalId(String name) {
		Long id = 1L;
		GenId genId = findEntity(GenId.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (genId != null) {
			id = genId.getValue() + 1;
			genId.setValue(id);
			mergeEntity(genId);
		} else {
			genId = new GenId();
			genId.setName(name);
			genId.setValue(id);
			mergeEntity(genId, true);
		}
		return id;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	synchronized public Long getId(String name) {
		Integer nodeId = getNodeId();
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		name = year + name + nodeId;

		Long id = 1L;
		GenId genId = findEntity(GenId.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (genId != null) {
			id = genId.getValue() + 1;
			genId.setValue(id);
			mergeEntity(genId);
		} else {
			genId = new GenId();
			genId.setName(name);
			genId.setValue(id);
			mergeEntity(genId, true);
		}

		// Add suffix node
		int exp = (nodeId + "" + year).length();
		exp = exp < 8 ? 8 : exp;

		return (long) (id * Math.pow(10, exp) + nodeId * 10000 + year);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	synchronized public Long getSeq(String name) {
		Long seq = 1L;
		GenSeq genSeq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (genSeq == null) {
			genSeq = new GenSeq();
			genSeq.setName(name);
			genSeq.setValue(seq);
		} else {
			seq = genSeq.getValue() + 1;
			genSeq.setValue(seq);
		}
		genSeq = mergeEntity(genSeq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company) {
		Long seq = 1L;
		name = name + "_" + company;
		GenSeq genSeq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (genSeq == null) {
			genSeq = new GenSeq();
			genSeq.setName(name);
			genSeq.setValue(seq);
		} else {
			seq = genSeq.getValue() + 1;
			genSeq.setValue(seq);
		}
		genSeq = mergeEntity(genSeq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company, Long branch) {
		Long seq = 1L;
		name = name + "_" + company;
		name = name + "_" + branch;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company, Long branch, Long department) {
		Long seq = 1L;
		name = name + "_" + company;
		name = name + "_" + branch;
		name = name + "_" + department;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	synchronized public Long getSeq(String name, String date) {
		Long seq = 1L;
		name = name + "_" + date;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
			mseq.setSeqDate(date);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company, String date) {
		Long seq = 1L;
		name = name + "_" + company;
		name = name + "_" + date;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
			mseq.setSeqDate(date);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company, Long branch, String date) {
		Long seq = 1L;
		name = name + "_" + company;
		name = name + "_" + branch;
		name = name + "_" + date;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
			mseq.setSeqDate(date);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	synchronized public Long getSeq(String name, Long company, Long branch, Long department, String date) {
		Long seq = 1L;
		name = name + "_" + company;
		name = name + "_" + branch;
		name = name + "_" + department;
		name = name + "_" + date;
		GenSeq mseq = findEntity(GenSeq.class, name, LockModeType.PESSIMISTIC_WRITE);
		if (mseq == null) {
			mseq = new GenSeq();
			mseq.setName(name);
			mseq.setValue(seq);
			mseq.setSeqDate(date);
		} else {
			seq = mseq.getValue() + 1;
			mseq.setValue(seq);
		}
		mseq = mergeEntity(mseq);
		return seq;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E insertEntity(DaoEntity entity) {
		if (entity == null) {
			return null;
		}
		String entityName = entity.getClass().getSimpleName();
		Long currDate = getSysDate();
		if (entity.getId() == null) {
			entity.setId(getId(entityName));
			entity.setCreatedBy(this.loginInfo.getPartnerId());
			entity.setCreatedDate(currDate);
		}

		entity.setUpdatedBy(this.loginInfo.getPartnerId());
		entity.setUpdatedDate(currDate);

		entity = persistEntity(entity);
		return (E) entity;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E updateEntity(DaoEntity entity) {
		return updateEntity(entity, false);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E updateEntity(DaoEntity entity, boolean flush) {
		if (entity == null) {
			return null;
		}

		Long currDate = getSysDate();
		String entityName = entity.getClass().getSimpleName();
		// String crud = LogCrudData.CRUD_UPDATE;
		if (entity.getId() == null) {
			entity.setId(getId(entityName));
			// crud = LogCrudData.CRUD_INSERT;
			entity.setCreatedBy(this.loginInfo.getPartnerId());
			entity.setCreatedDate(currDate);
		}

		entity.setUpdatedBy(this.loginInfo.getPartnerId());
		entity.setUpdatedDate(currDate);

		entity = mergeEntity(entity, flush);
		// Log entity
		// persistEntity(logEntity(entity, entityName, crud, currDate));
		return (E) entity;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E deleteEntity(DaoEntity entity) {
		if (entity == null) {
			return null;
		}

		if (entity.getId() != null) {
			Long currDate = getSysDate();

			// entity.setDeleted(true);
			entity.setDeletedBy(this.loginInfo.getPartnerId());
			entity.setDeletedDate(currDate);

			entity = mergeEntity(entity);
			// persistEntity(logEntity(entity, entityName,
			// LogCrudData.CRUD_DELETE,
			// currDate));
		}
		return (E) entity;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> insertEntities(List<DaoEntity> entities) {
		if (entities == null || entities.isEmpty()) {
			return (List<E>) entities;
		}

		// List<LogCrudData> logs = new ArrayList<LogCrudData>();

		Long currDate = getSysDate();
		String entityName = entities.get(0).getClass().getSimpleName();
		// String crud = null;
		for (DaoEntity e : entities) {
			// crud = LogCrudData.CRUD_UPDATE;
			if (e.getId() == null) {
				e.setId(getId(entityName));
				// crud = LogCrudData.CRUD_INSERT;
				e.setCreatedBy(this.loginInfo.getPartnerId());
				e.setCreatedDate(currDate);
			}

			e.setUpdatedBy(this.loginInfo.getPartnerId());
			e.setUpdatedDate(currDate);

			// logs.add(logEntity(entity, entityName, crud, currDate));
		}

		entities = persistEntities(entities);

		// Log entities
		// DaoEntity entity = null;
		// LogCrudData log = null;
		// for (int i = 0; i < entities.size(); i++) {
		// entity = entities.get(i);
		// log = logs.get(i);
		// log.setRowId(entity.getId());
		// log.setRowData(entity.getRowData());
		// log.setVersion(entity.getVersion());
		// }
		// persistEntities(logs);
		return (List<E>) entities;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> updateEntities(List<E> entities) {
		return updateEntities(entities, false);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> updateEntities(List<E> entities, boolean flush) {
		if (entities == null || entities.isEmpty()) {
			return entities;
		}

		// List<LogCrudData> logs = new ArrayList<LogCrudData>();

		Long currDate = getSysDate();
		String entityName = entities.get(0).getClass().getSimpleName();
		// String crud = null;
		DaoEntity entity;
		for (E e : entities) {
			entity = (DaoEntity) e;
			// crud = LogCrudData.CRUD_UPDATE;
			if (entity.getId() == null) {
				entity.setId(getId(entityName));
				// crud = LogCrudData.CRUD_INSERT;
				entity.setCreatedBy(this.loginInfo.getPartnerId());
				entity.setCreatedDate(currDate);
			}

			entity.setUpdatedBy(this.loginInfo.getPartnerId());
			entity.setUpdatedDate(currDate);

			// logs.add(logEntity(entity, entityName, crud, currDate));
		}

		entities = mergeEntities(entities, flush);

		// Log entities
		// LogCrudData log = null;
		// for (int i = 0; i < entities.size(); i++) {
		// entity = (DaoEntity) entities.get(i);
		// log = logs.get(i);
		// log.setRowId(entity.getId());
		// log.setRowData(entity.getRowData());
		// log.setVersion(entity.getVersion());
		// }
		// persistEntities(logs);
		return entities;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> deleteEntities(List<E> entities) {
		if (entities == null) {
			return null;
		}

		Long currDate = getSysDate();
		DaoEntity entity;
		List<E> list = new ArrayList<E>();
		for (E e : entities) {
			entity = (DaoEntity) e;
			if (entity.getId() == null) {
				continue;
			}
			entity.setDeletedBy(this.loginInfo.getPartnerId());
			entity.setDeletedDate(currDate);

			list.add((E) entity);
		}
		return mergeEntities(list);
	}
}
