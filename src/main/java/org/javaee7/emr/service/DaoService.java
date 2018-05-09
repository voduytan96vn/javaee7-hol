package org.javaee7.emr.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.eclipse.persistence.config.QueryHints;
import org.javaee7.emr.client.ParamQuery;

public class DaoService {
	@PersistenceContext(unitName = "movieplex7PU")
	EntityManager em;

	protected Locale locale;
	protected TimeZone zone;
	protected Locale localeEn = new Locale("en", "US");

	public DaoService(Locale locale, TimeZone zone) {
		super();
		this.locale = locale;
		this.zone = zone;
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public Integer getNodeId() {
		return 1001;
	}

	/**
	 * Merges Entity object to Database.
	 * 
	 * @param entity
	 *            the Entity object that is merged into database
	 * @return Entity object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E mergeEntity(E entity) {
		EntityManager em = getEntityManager();
		E ret = null;
		if (entity != null) {
			ret = em.merge(entity);
		}
		return ret;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E mergeEntity(E entity, boolean flush) {
		EntityManager em = getEntityManager();
		E ret = null;
		if (entity != null) {
			ret = em.merge(entity);
			if (flush) {
				em.flush();
			}
		}
		return ret;
	}

	/**
	 * Adds a new Entity object to Database.
	 * 
	 * @param entity
	 *            the Entity object that is added into database
	 * @return Entity object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E persistEntity(E entity) {
		EntityManager em = getEntityManager();
		if (entity != null) {
			em.persist(entity);
		}
		return entity;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E persistEntity(E entity, boolean flush) {
		EntityManager em = getEntityManager();
		if (entity != null) {
			em.persist(entity);
			if (flush) {
				em.flush();
			}
		}
		return entity;
	}

	/**
	 * Removes Entity object from Database.
	 * 
	 * @param entity
	 *            the Entity object that is removed from database
	 * @return Entity object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E removeEntity(E entity) {
		EntityManager em = getEntityManager();
		if (entity != null) {
			entity = em.merge(entity);
			em.remove(entity);
			em.flush();
		}
		return entity;
	}

	/**
	 * Refresh Entity object from Database.
	 * 
	 * @param entity
	 *            the Entity object that is refresh from database
	 * @return Entity object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E refreshEntity(E entity) {
		if (entity != null) {
			EntityManager em = getEntityManager();
			entity = em.merge(entity);
			em.refresh(entity);
		}
		return entity;
	}

	/**
	 * Find entity by primary key.
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return the found entity instance or null if the entity does not exist
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E findEntity(Class<E> entityClass, Object primaryKey) {
		EntityManager em = getEntityManager();
		E entity = null;
		try {
			if (primaryKey != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(QueryHints.MAINTAIN_CACHE, false);
				entity = (E) em.find(entityClass, primaryKey, properties);
			}
		} catch (Exception e) {
			entity = null;
		}
		return entity;
	}

	public <E> E findByCode(Class<E> entityClass, String code) {
		E entity = null;

		return entity;
	}

	/**
	 * Find entity by primary key.
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @param lock
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E findEntity(Class<E> entityClass, Object primaryKey, LockModeType lock) {
		EntityManager em = getEntityManager();
		E entity = null;
		try {
			if (primaryKey != null) {
				if (lock != null) {
					entity = (E) em.find(entityClass, primaryKey, lock);
				} else {
					entity = (E) em.find(entityClass, primaryKey);
				}
			}
		} catch (Exception e) {
			entity = null;
		}

		return entity;
	}

	/**
	 * Merges array Entities object to Database.
	 * 
	 * @param arrEntities
	 *            is array Entities object
	 * @return number of Entities is updated or inserted to database successful
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> mergeEntities(List<E> entities) {
		return mergeEntities(entities, false);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> mergeEntities(List<E> entities, boolean flush) {
		EntityManager em = getEntityManager();
		List<E> ret = null;
		E entity = null;
		if (entities != null) {
			ret = new ArrayList<E>();
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if (entity != null) {
					entity = em.merge(entity);
					ret.add(entity);
				}
			}
		}
		if (flush) {
			em.flush();
		}
		return ret;
	}

	/**
	 * Persist array Entities object to Database.
	 * 
	 * @param arrEntities
	 *            is array Entities object
	 * @return number of Entities is inserted to database successful
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> persistEntities(List<E> entities) {
		EntityManager em = getEntityManager();
		E entity = null;
		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if (entity != null) {
					em.persist(entity);
				}
			}
		}
		return entities;
	}

	/**
	 * Remove physical array Entities object from Database.
	 * 
	 * @param arrEntities
	 *            is array Entities object
	 * @return number of Entities is deleted to database successful
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> removeEntities(List<E> entities) {
		EntityManager em = getEntityManager();
		E entity = null;
		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if (entity != null) {
					entity = em.merge(entity);
					em.remove(entity);
				}
			}
		}
		return entities;
	}

	/**
	 * Get system date without time of database.
	 * 
	 * @return Date
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getSysDate() {
		return Calendar.getInstance(zone, locale).getTimeInMillis();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getSysTime() {
		Calendar cal = Calendar.getInstance(zone, locale);
		cal.set(1970, 0, 1);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * Update database
	 * 
	 * @param paramQueries
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int executeUpdate(ParamQuery... paramQueries) {
		EntityManager em = getEntityManager();
		Query query = null;
		int ret = 0;
		if (paramQueries == null) {
			return ret;
		}
		for (ParamQuery paramQuery : paramQueries) {
			if (paramQuery != null) {
				query = getQuery(paramQuery, em);
				if (query != null) {
					ret += query.executeUpdate();
				}
			}
		}
		return ret;
	}

	/**
	 * Gets Entity object from Database by a SELECT query.
	 * 
	 * @param paramQuery
	 * @return Entity object
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> E getEntity(ParamQuery paramQuery) {
		EntityManager em = getEntityManager();
		E result = null;
		Query query = null;
		List<E> listResult = null;
		if (paramQuery != null) {
			query = getQuery(paramQuery, em);

			if (query != null) {
				// em.flush();
				listResult = query.getResultList();

				// Stored output value
				if (paramQuery.isStoredProcedure()) {
					StoredProcedureQuery storedQuery = (StoredProcedureQuery) query;
					Map<String, Object> paramOutput = paramQuery.getParamOutput();
					Iterator<String> keyMap = null;
					String key = null;
					if (query != null) {
						if (paramOutput != null && paramOutput.size() > 0) {
							keyMap = paramOutput.keySet().iterator();
							while (keyMap.hasNext()) {
								key = keyMap.next();
								paramOutput.put(key, storedQuery.getOutputParameterValue(key));
							}
						}
					}
				}
			}

			if (listResult != null) {
				if (listResult.size() == 1) {
					result = listResult.get(0);
				} else if (listResult.size() > 1) {
					if (paramQuery.isFirst()) {
						result = listResult.get(0);
					} else if (paramQuery.isLast()) {
						result = listResult.get(listResult.size() - 1);
					} else {
						// Create exception
						query.getSingleResult();
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets list Entities object from database by a SELECT query.
	 * 
	 * @param paramQuery
	 * @return list Entities object
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> List<E> getEntities(ParamQuery paramQuery) {
		EntityManager em = getEntityManager();
		List<E> result = null;
		Query query = null;
		if (paramQuery != null) {
			query = getQuery(paramQuery, em);

			if (query != null) {
				// em.flush();
				result = query.getResultList();

				// Stored output value
				if (paramQuery.isStoredProcedure()) {
					StoredProcedureQuery storedQuery = (StoredProcedureQuery) query;
					Map<String, Object> paramOutput = paramQuery.getParamOutput();
					Iterator<String> keyMap = null;
					String key = null;
					if (query != null) {
						if (paramOutput != null && paramOutput.size() > 0) {
							keyMap = paramOutput.keySet().iterator();
							while (keyMap.hasNext()) {
								key = keyMap.next();
								paramOutput.put(key, storedQuery.getOutputParameterValue(key));
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Get count
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getCount(ParamQuery paramQuery) {
		Long ret = getLong(paramQuery);
		return ret == null ? 0L : ret;
	}

	/**
	 * Get field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer getInteger(ParamQuery paramQuery) {
		BigDecimal result = getNumber(paramQuery);
		return result != null ? result.intValue() : null;
	}

	/**
	 * Get field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getLong(ParamQuery paramQuery) {
		BigDecimal result = getNumber(paramQuery);
		return result != null ? result.longValue() : null;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double getDouble(ParamQuery paramQuery) {
		BigDecimal result = getNumber(paramQuery);
		return result != null ? result.doubleValue() : null;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public BigDecimal getNumber(ParamQuery paramQuery) {
		BigDecimal result = null;
		Object object = getObject(paramQuery);
		if (object instanceof BigDecimal) {
			result = ((BigDecimal) object);
		} else {
			result = object == null ? null : new BigDecimal(object.toString());
		}
		return result;
	}

	/**
	 * Get 1 field string
	 * 
	 * @param paramQuery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getString(ParamQuery paramQuery) {
		Object object = getObject(paramQuery);
		return object == null ? null : object.toString();
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Integer> getListInteger(ParamQuery paramQuery) {
		List list = getObjects(paramQuery);
		List<Integer> result = new ArrayList<Integer>();
		if (list != null && !list.isEmpty()) {
			Integer item = null;
			for (Object object : list) {
				if (object != null) {
					if (object instanceof BigDecimal) {
						item = ((BigDecimal) object).intValue();
					} else {
						item = object == null ? null : new BigDecimal(object.toString()).intValue();
					}
				} else {
					item = null;
				}
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Long> getListLong(ParamQuery paramQuery) {
		List list = getObjects(paramQuery);
		List<Long> result = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			Long item = null;
			for (Object object : list) {
				if (object != null) {
					if (object instanceof BigDecimal) {
						item = ((BigDecimal) object).longValue();
					} else {
						item = object == null ? null : new BigDecimal(object.toString()).longValue();
					}
				} else {
					item = null;
				}
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Double> getListDouble(ParamQuery paramQuery) {
		List list = getObjects(paramQuery);
		List<Double> result = new ArrayList<Double>();
		if (list != null && !list.isEmpty()) {
			Double item = null;
			for (Object object : list) {
				if (object != null) {
					if (object instanceof BigDecimal) {
						item = ((BigDecimal) object).doubleValue();
					} else {
						item = object == null ? null : new BigDecimal(object.toString()).doubleValue();
					}
				} else {
					item = null;
				}
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<BigDecimal> getListNumber(ParamQuery paramQuery) {
		List list = getObjects(paramQuery);
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		if (list != null && !list.isEmpty()) {
			BigDecimal item = null;
			for (Object object : list) {
				if (object != null) {
					if (object instanceof BigDecimal) {
						item = (BigDecimal) object;
					} else {
						item = object == null ? null : new BigDecimal(object.toString());
					}
				} else {
					item = null;
				}
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Get 1 field number
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> getListString(ParamQuery paramQuery) {
		List<?> list = getObjects(paramQuery);
		List<String> result = new ArrayList<String>();
		if (list != null && !list.isEmpty() && !(list.get(0) instanceof String)) {
			String item = null;
			for (Object object : list) {
				item = object != null ? object.toString() : null;
				result.add(item);
			}
		} else {
			result = (List<String>) list;
		}
		return result;
	}

	/**
	 * Get 1 field of table
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object getObject(ParamQuery paramQuery) {
		EntityManager em = getEntityManager();
		Object result = null;
		List listResult = null;
		Query query = null;
		if (paramQuery != null) {
			query = getQuery(paramQuery, em);

			if (query != null) {
				listResult = query.getResultList();

				// Stored output value
				if (paramQuery.isStoredProcedure()) {
					StoredProcedureQuery storedQuery = (StoredProcedureQuery) query;
					Map<String, Object> paramOutput = paramQuery.getParamOutput();
					Iterator<String> keyMap = null;
					String key = null;
					if (query != null) {
						if (paramOutput != null && paramOutput.size() > 0) {
							keyMap = paramOutput.keySet().iterator();
							while (keyMap.hasNext()) {
								key = keyMap.next();
								paramOutput.put(key, storedQuery.getOutputParameterValue(key));
							}
						}
					}
				}
			}
		}

		if (listResult != null) {
			if (listResult.size() == 1) {
				result = listResult.get(0);
			} else if (listResult.size() > 1) {
				if (paramQuery.isFirst()) {
					result = listResult.get(0);
				} else if (paramQuery.isLast()) {
					result = listResult.get(listResult.size() - 1);
				} else {
					// Create exception
					query.getSingleResult();
				}
			}
		}

		return result;
	}

	/**
	 * Get 1 field of table
	 * 
	 * @param paramQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List getObjects(ParamQuery paramQuery) {
		EntityManager em = getEntityManager();
		Query query = null;
		List result = null;
		if (paramQuery != null) {
			query = getQuery(paramQuery, em);

			if (query != null) {
				result = query.getResultList();

				// Stored output value
				if (paramQuery.isStoredProcedure()) {
					StoredProcedureQuery storedQuery = (StoredProcedureQuery) query;
					Map<String, Object> paramOutput = paramQuery.getParamOutput();
					Iterator<String> keyMap = null;
					String key = null;
					if (query != null) {
						if (paramOutput != null && paramOutput.size() > 0) {
							keyMap = paramOutput.keySet().iterator();
							while (keyMap.hasNext()) {
								key = keyMap.next();
								paramOutput.put(key, storedQuery.getOutputParameterValue(key));
							}
						}
					}
				}
			}
		}
		return result != null ? result : Collections.emptyList();
	}

	/**
	 * Get query from cache
	 * 
	 * @param name
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private Query getQuery(ParamQuery paramQuery, EntityManager em) {
		Query query = null;
		String name = paramQuery.getName();
		String sql = paramQuery.getSql();
		Class<?> resultClass = paramQuery.getResultClass();
		Map<String, Object> param = paramQuery.getParam();

		if (name == null || "".equals(name)) {
			if (sql == null || "".equals(sql)) {
				throw new RuntimeException("Can't get sql of query.");
			} else {
				query = em.createNativeQuery(sql, resultClass);
			}
		} else {
			if (paramQuery.isStoredProcedure()) {
				query = em.createNamedStoredProcedureQuery(name);
			} else {
				query = em.createNamedQuery(name);
			}
		}

		if (query != null) {
			// Set parameter to query
			query = setParameters(query, param);

			if (!paramQuery.isStoredProcedure()) {
				if (paramQuery.getFirstResult() > 0) {
					query.setFirstResult(paramQuery.getFirstResult());
				}
				if (paramQuery.getMaxResult() > 0) {
					query.setMaxResults(paramQuery.getMaxResult());
				}
			}
		} else {
			throw new RuntimeException("Can't create query: " + name + ".");
		}

		if (paramQuery.isClear()) {
			em.flush();
			em.clear();
		}
		query.setHint(QueryHints.MAINTAIN_CACHE, false);
		return query;
	}

	private Query setParameters(Query query, Map<String, Object> param) {
		Iterator<String> keyMap = null;
		String key = null;
		if (query != null) {
			if (param != null && param.size() > 0) {
				keyMap = param.keySet().iterator();
				while (keyMap.hasNext()) {
					key = keyMap.next();
					query.setParameter(key, param.get(key));
				}
			}
		}
		return query;
	}
}
