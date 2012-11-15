package com.capitolcamp.historicmarkers.servlet;

import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4JInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(Log4JInitServlet.class);

	@Override
	public void init(final ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
		final URL log4jConfig = Log4JInitServlet.class.getResource("/log4j.properties");
		PropertyConfigurator.configure(log4jConfig);

		LOGGER.info("Log4J configured from: " + log4jConfig.toString());

		super.init(config);
	}

}
