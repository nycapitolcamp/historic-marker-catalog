package com.capitolcamp.historicmarkers.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.capitolcamp.historicmarkers.domain.Account;

@Path("/checkin")
public class CheckinResource extends BasePersistenceService {
	private static final Logger LOGGER = Logger
			.getLogger(CheckinResource.class);

	public static void main(final String[] args) {

	}

	@GET
	public Response getSomething(@QueryParam("email") final String email) {

		try {
			Account a = new Account(); // TODO get it from DB.
			a.setEmail(email);

			return Response.ok().entity(a).build();

		} catch (final Exception e) {
			LOGGER.error(e);
			return Response.serverError()
					.entity(new ResponseMessage(e.getMessage())).build();
		}
	}
}
