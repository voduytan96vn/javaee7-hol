/**
 * 
 */
/**
 * @author voduy
 *
 */
package org.javaee7.emr.client;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RemoteService {
	Object doService(Object loginInfo, String serviceName, Object dto) throws Exception;

	<E> E findEntity(Class<E> entityClass, Object primaryKey);

	<E> E getEntity(ParamQuery paramQuery);

	<E> List<E> getEntities(ParamQuery paramQuery);

	Long getSysDate();

	Long getSysTime();
}
