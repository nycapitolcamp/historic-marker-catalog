package com.capitolcamp.historicmarkers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static final EntityManagerFactory emfInstance;
	static {
		String istest = System.getProperty("RUNNING_TESTS");
		System.out.println("RUNNING_TESTS: " + istest);
		if (istest != null && istest.equalsIgnoreCase("true")) {
			emfInstance = Persistence.createEntityManagerFactory("integration-tests");
		}
		else {

			emfInstance = Persistence.createEntityManagerFactory("selectivemailpush");
		}
	}

	public static EntityManagerFactory getInstance() {
		return emfInstance;
	}

	private HibernateUtil() {
	}
}
