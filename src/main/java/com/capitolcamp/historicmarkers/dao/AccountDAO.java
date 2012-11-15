package com.capitolcamp.historicmarkers.dao;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.capitolcamp.historicmarkers.HibernateUtil;
import com.capitolcamp.historicmarkers.domain.Account;

public class AccountDAO {
	private static final Logger LOGGER = Logger.getLogger(AccountDAO.class);

	/**
	 * Sets all Status records to IDLE where server name is same as this server.
	 * 
	 * @return records affected
	 */
	public static int clearAbandonedAccountsForAllServers() {

		int recordsAffected = 0;
		final EntityManager em = HibernateUtil.getInstance()
				.createEntityManager();

		try {

			final Query query = em
					.createQuery("UPDATE Status s SET status = :status WHERE s.lastUpdated < :old AND s.status != :status");
			final Calendar old = Calendar.getInstance();
			old.add(Calendar.MINUTE, -1);
			// min
			// old.
			query.setParameter("old", old.getTime());

			em.getTransaction().begin();
			recordsAffected = query.executeUpdate();
			em.getTransaction().commit();

		} catch (final Exception ex) {
			LOGGER.error(ex);
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

		return recordsAffected;
	}

	/**
	 * Sets all Status records to IDLE where server name is same as this server.
	 * 
	 * @return records affected
	 */
	public static int clearAbandonedAccountsThisServer() {
		int recordsAffected = 0;
		final EntityManager em = HibernateUtil.getInstance()
				.createEntityManager();

		try {

			final Query query = em
					.createQuery("UPDATE Status s SET status = :status, lastUpdated = :lastUpdated WHERE s.watchServer = :watchServer AND s.status != :status");
			query.setParameter("watchServer", InetAddress.getLocalHost()
					.getHostName());
			query.setParameter("status", "TODO");
			query.setParameter("lastUpdated", Calendar.getInstance().getTime());

			em.getTransaction().begin();
			recordsAffected = query.executeUpdate();
			em.getTransaction().commit();

		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		LOGGER.debug("Set " + recordsAffected
				+ " abandoned accounts (this server) to " + "TODO");
		return recordsAffected;
	}

	public static List<Account> findAccountsToWatch(final int maxRecords) {
		LOGGER.debug("Looking for IDLE accounts to watch...");
		List<Account> accounts = null;
		final EntityManager em = HibernateUtil.getInstance()
				.createEntityManager();

		try {

			final TypedQuery<Account> query = em
					.createQuery(
							"SELECT a FROM Account a JOIN a.status s WHERE s.watchServer = :watchServer AND a.enabled = :enabled AND (s.status = NULL or s.status = :status)",
							Account.class);
			query.setParameter("watchServer", InetAddress.getLocalHost()
					.getHostName());
			query.setParameter("status", "TODO");
			query.setParameter("enabled", true);

			query.setMaxResults(maxRecords);

			accounts = query.getResultList();
			em.getTransaction().begin();
			for (final Account a : accounts) {
				a.getStatus().setStatus("TODO");
			}
			em.getTransaction().commit();
			LOGGER.debug("Found " + accounts.size() + " accounts to watch...");
		} catch (final Exception ex) {
			LOGGER.error(ex);
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		return accounts;
	}

	public static boolean updateStatus(final Account a, final String status,
			final String lastError) throws Exception {
		int recordsAffected = 0;
		final EntityManager em = HibernateUtil.getInstance()
				.createEntityManager();

		try {

			final Query query = em
					.createQuery("UPDATE Status s SET status = :status, lastUpdated = :lastUpdated, lastError = :lastError WHERE s.accountId = :accountId");
			query.setParameter("accountId", a.getId());
			query.setParameter("status", status);
			query.setParameter("lastError", lastError);
			query.setParameter("lastUpdated", Calendar.getInstance().getTime());

			em.getTransaction().begin();
			recordsAffected = query.executeUpdate();
			em.getTransaction().commit();

		}

		finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		LOGGER.debug("Set status to " + status + " for " + a.toString());
		return recordsAffected > 0 ? true : false;
	}
}
