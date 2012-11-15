package com.capitolcamp.historicmarkers.servlet;

import java.io.IOException;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class InfoServlet extends HttpServlet {

	private static final long serialVersionUID = -9018765951190521373L;
	private static final Logger LOGGER = Logger.getLogger(InfoServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject o = new JSONObject();

		try {
			JSONObject properties = new JSONObject();

			o.put("properties", properties);

			JSONObject osValues = new JSONObject();
			osValues.append("name", System.getProperty("os.name") + " ("
					+ System.getProperty("os.version") + ")");
			osValues.append("architecture", System.getProperty("os.arch"));
			osValues.append("processors", Runtime.getRuntime()
					.availableProcessors());
			o.put("OperatingSystem", osValues);

			JSONObject memValues = new JSONObject();
			NumberFormat format = NumberFormat.getInstance();
			memValues.append("free",
					format.format(Runtime.getRuntime().freeMemory() / 1024)
							+ " kB");
			memValues.append("allocated",
					format.format(Runtime.getRuntime().totalMemory() / 1024)
							+ " kB");
			memValues.append(
					"committed",
					format.format((Runtime.getRuntime().totalMemory() - Runtime
							.getRuntime().freeMemory()) / 1024) + " kB");
			memValues.append("max",
					format.format(Runtime.getRuntime().maxMemory() / 1024)
							+ " kB");
			o.put("MemoryUsage", memValues);
		} catch (JSONException e) {
			LOGGER.error(e);
		}
		resp.setContentType("application/json");

		resp.getWriter().write(o.toString());

	}

}
