package org.javaee7.emr.service;

import java.util.List;

import org.javaee7.emr.entities.ComEntity;

public class CommonService extends BaseService {
	public CommonService() {
		super();
	}

	public <E> void setIndex(List<E> entities) {
		setIndex(entities, 1);
	}

	public <E> void setIndex(List<E> entities, int offset) {
		int len = entities.size();
		ComEntity entity = null;
		for (int i = 0; i < len; i++) {
			entity = (ComEntity) entities.get(i);
			entity.setIndex(i + offset);
		}
	}
}
