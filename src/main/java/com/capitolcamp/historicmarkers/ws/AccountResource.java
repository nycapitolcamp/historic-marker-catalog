package com.capitolcamp.historicmarkers.ws;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capitolcamp.historicmarkers.domain.Account;
import com.google.common.base.Preconditions;

@Path("/sample")
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource extends BasePersistenceService {

	private static final Logger LOGGER = Logger
			.getLogger(AccountResource.class);

	@DELETE
	public Response deleteAccount(@QueryParam("email") final String email,
			@QueryParam("deviceToken") final String deviceToken) {
		try {
			final Account config = new Account();
			config.setEmail(email);
			config.setDeviceToken(deviceToken);

			validateAccountBase(config);

			final Account account = getAccountAsAccount(config.getEmail(),
					config.getDeviceToken());
			if (account != null) {
				final EntityManager em = getNewEntityManager();
				em.getTransaction().begin();
				final Account a = em.getReference(Account.class,
						account.getId());
				em.remove(a);
				em.getTransaction().commit();
				return Response.ok()
						.entity(new ResponseMessage("Account deleted."))
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ResponseMessage("Account not found"))
						.build();
			}
		} catch (final Exception ex) {
			return handleError(ex);
		}
	}

	@GET
	public Response getAccount(@QueryParam("email") final String email,
			@QueryParam("deviceToken") final String deviceToken) {

		try {

			if (StringUtils.trimToNull(email) == null) {
				// all accounts
				final Account[] accounts = getAccountAsAccount(deviceToken);

				if (accounts != null) {

					return Response.ok(accounts).build();
				} else {
					return Response.status(Status.NOT_FOUND)
							.entity(new ResponseMessage("Account not found"))
							.build();
				}
			} else {
				// single account
				final Account account = getAccountAsAccount(email, deviceToken);

				if (account != null) {

					return Response.ok(account).build();
				} else {
					return Response.status(Status.NOT_FOUND)
							.entity(new ResponseMessage("Account not found"))
							.build();
				}
			}
		} catch (final Exception ex) {

			return handleError(ex);
		}

	}

	private Account[] getAccountAsAccount(final String deviceToken) {
		Preconditions.checkNotNull(deviceToken, "DeviceToken is null.");

		final EntityManager em = getNewEntityManager();
		try {
			final TypedQuery<Account> q = em
					.createQuery(
							"SELECT a FROM Account a WHERE a.deviceToken = :deviceToken",
							Account.class);
			q.setParameter("deviceToken", deviceToken);
			em.getTransaction().begin();
			final List<Account> accounts = q.getResultList();
			em.getTransaction().commit();
			em.detach(accounts); // detach for transfer
			return accounts.toArray(new Account[] {});

		} catch (final javax.persistence.NoResultException ex) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
			LOGGER.debug("leaving getList");

		}
	}

	private Account getAccountAsAccount(final String email,
			final String deviceToken) {

		final Account config = new Account();
		config.setEmail(email);
		config.setDeviceToken(deviceToken);

		validateAccountBase(config);

		final EntityManager em = getNewEntityManager();
		try {
			final TypedQuery<Account> q = em
					.createQuery(
							"SELECT a FROM Account a WHERE a.email = :email AND a.deviceToken = :deviceToken",
							Account.class);
			q.setParameter("email", email);
			q.setParameter("deviceToken", deviceToken);
			em.getTransaction().begin();
			final Account account = q.getSingleResult();
			em.getTransaction().commit();
			em.detach(account); // detach for transfer
			return account;

		} catch (final javax.persistence.NoResultException ex) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
			LOGGER.debug("leaving getList");

		}
	}

	@PUT
	public Response saveAccount(final Account config) {

		try {
			validateAccountBase(config);
			Preconditions.checkArgument(config.getStatus() == null,
					"You can not update a status.");
			final EntityManager em = getNewEntityManager();
			final Account existing = getAccountAsAccount(config.getEmail(),
					config.getDeviceToken());
			if (existing == null) {
				// NEW RECORD
				config.setCreated(Calendar.getInstance().getTime());
				final com.capitolcamp.historicmarkers.domain.Status status = new com.capitolcamp.historicmarkers.domain.Status();
				status.setAccount(config);
				status.setCreated(Calendar.getInstance().getTime());
				status.setStatus("TODO");
				status.setWatchServer(InetAddress.getLocalHost().getHostName());
				em.getTransaction().begin();
				em.persist(status);
				em.persist(config);
				em.getTransaction().commit();

			} else {
				// UPDATE EXISTING RECORD
				validateAccountForSave(existing);

				// set what can be changed...

				if (config.getDescription() != null) {
					existing.setDescription(config.getDescription());
				}
				existing.setEnabled(config.isEnabled());
				existing.setFilter(config.getFilter());
				existing.setHost(config.getHost());
				existing.setLastUpdated(Calendar.getInstance().getTime());
				existing.setOauthRefreshToken(config.getOauthRefreshToken());
				existing.setPort(config.getPort());
				existing.setProtocol(config.getProtocol());

				validateAccountForSave(existing);

				em.getTransaction().begin();
				em.merge(existing);
				em.getTransaction().commit();

			}
			em.close();
			return Response.ok(existing).build();
		} catch (final Exception ex) {
			return handleError(ex);
		}
	}

	@POST
	public Response updateAccount(final Account config) {
		return saveAccount(config);
	}

	private void validateAccountBase(final Account config) {
		Preconditions.checkNotNull(config, "Config object is null.");
		Preconditions.checkNotNull(config.getDeviceToken(),
				"DeviceToken is null.");
		Preconditions.checkNotNull(config.getEmail(), "Account email is null.");

	}

	private void validateAccountForSave(final Account config) {
		validateAccountBase(config);
		Preconditions.checkNotNull(config.getId(), "Id is null.");
		Preconditions.checkNotNull(config.getOauthRefreshToken(),
				"OAuth RefreshToken is null.");
		Preconditions.checkNotNull(config.getHost(), "Host is null.");
		Preconditions.checkNotNull(config.getPort(), "Port is null.");
		Preconditions.checkArgument(config.getPort() > 0,
				"Port must be greater than 0.");
		Preconditions.checkNotNull(config.getProtocol(), "Protocol is null.");
		try {
			// TODO
		} catch (final IllegalArgumentException e) {
			// TODO
		}

	}
}
