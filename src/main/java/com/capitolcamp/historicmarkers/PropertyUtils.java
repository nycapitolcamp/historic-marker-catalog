package com.capitolcamp.historicmarkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PropertyUtils {

	private static final Logger LOGGER = Logger.getLogger(PropertyUtils.class);

	private static final Properties properties = new Properties();

	static {

		final String propertyFilePath = System
				.getProperty("com.intellitech.selectivemail.propertyfile");
		LOGGER.debug("Searching for property file: " + propertyFilePath);
		final File f = new File(propertyFilePath + StringUtils.EMPTY);
		InputStream inputStream;

		try {
			inputStream = new FileInputStream(f);

			try {
				properties.load(inputStream);

			} catch (final IOException e1) {
				LOGGER.error(e1);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (final IOException e) {
						LOGGER.fatal("Can't close inputstream.", e);
					}
				}
			}

			LOGGER.info("Loaded " + f.getAbsolutePath());
		} catch (final FileNotFoundException e) {

			LOGGER.error("Unable to find properties file \"" + propertyFilePath
					+ "\", using internal file instead");
			loadInternalFile();

		}

	}

	public static String get(final String key) {
		return properties.getProperty(key	);
	}

	private static void loadInternalFile() {

		try {
			final InputStream inputStream = (PropertyUtils.class
					.getResourceAsStream("/system.properties"));
			properties.load(inputStream);
			inputStream.close();
		} catch (final IOException e) {
			LOGGER.fatal("No properties could be loaded!", e);
		}

	}

}
