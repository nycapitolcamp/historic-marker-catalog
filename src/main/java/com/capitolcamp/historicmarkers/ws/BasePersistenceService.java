package com.capitolcamp.historicmarkers.ws;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capitolcamp.historicmarkers.HibernateUtil;

/**
 * @see http://persistentdesigns.com/wp/jersey-spring-and-jpa/
 * @author ddinatal
 * 
 */
public abstract class BasePersistenceService {

	private static final Logger LOGGER = Logger.getLogger(BasePersistenceService.class);

	protected static EntityManager getNewEntityManager() {
		return HibernateUtil.getInstance().createEntityManager();
	}

	protected final Response handleError(final Exception ex) {
		if (!(ex instanceof IllegalArgumentException)) {
			// don't log validation errors.
			LOGGER.error(ex);
		}
		ex.printStackTrace();

		if (ex != null && StringUtils.trimToNull(ex.getMessage()) != null) {
			LOGGER.debug("message is " + ex.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(new ResponseMessage(ex.getMessage())).build();
		}
		else {
			LOGGER.debug("no message to show");
			return Response.status(Status.BAD_REQUEST).build();
		}

	}

	public static final String responseToString(Response r) {
		if (r == null)
			return "NULL";

		return r.getStatus() + " " + r.getEntity().toString();
	}

}
