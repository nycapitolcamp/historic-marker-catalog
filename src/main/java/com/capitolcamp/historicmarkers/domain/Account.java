package com.capitolcamp.historicmarkers.domain;

// Generated Oct 23, 2012 12:55:14 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = { "email",
		"device_token" }))
@XmlRootElement
public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3760791562284558714L;
	private Date created;
	private String description;
	private String deviceToken;
	private String email;
	private boolean enabled;
	private String filter;
	private String host;
	private Integer id;
	private Date lastUpdated;
	private String oauthRefreshToken;
	private int port;
	private String protocol;

	private Status status;

	private int version;

	public Account() {
	}

	public Account(final String email, final String deviceToken, final String oauthRefreshToken, final String filter,
			final boolean enabled, final String protocol, final String host, final int port, final Date created) {
		this.email = email;
		this.deviceToken = deviceToken;
		this.oauthRefreshToken = oauthRefreshToken;
		this.filter = filter;
		this.enabled = enabled;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.created = created;
	}

	public Account(final String email, final String deviceToken, final String description,
			final String oauthRefreshToken, final String filter, final boolean enabled, final String protocol,
			final String host, final int port, final Date created, final Date lastUpdated, final Status status) {
		this.email = email;
		this.deviceToken = deviceToken;
		this.description = description;
		this.oauthRefreshToken = oauthRefreshToken;
		this.filter = filter;
		this.enabled = enabled;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.created = created;
		this.lastUpdated = lastUpdated;
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	@XmlTransient
	public Date getCreated() {
		return this.created;
	}

	@Column(name = "description", length = 45)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "device_token", nullable = false, length = 100)
	public String getDeviceToken() {
		return this.deviceToken;
	}

	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return this.email;
	}

	@Column(name = "filter", nullable = false)
	public String getFilter() {
		return this.filter;
	}

	@Column(name = "host", nullable = false, length = 45)
	public String getHost() {
		return this.host;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@XmlTransient
	public Integer getId() {
		return this.id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated", length = 19)
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	@Column(name = "oauth_refresh_token", nullable = false, length = 45)
	public String getOauthRefreshToken() {
		return this.oauthRefreshToken;
	}

	@Column(name = "port", nullable = false)
	public int getPort() {
		return this.port;
	}

	@Column(name = "protocol", nullable = false, length = 45)
	public String getProtocol() {
		return this.protocol;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
	public Status getStatus() {
		return this.status;
	}

	@Version
	@Column(name = "version", nullable = false)
	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setDeviceToken(final String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setFilter(final String filter) {
		this.filter = filter;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setLastUpdated(final Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setOauthRefreshToken(final String oauthRefreshToken) {
		this.oauthRefreshToken = oauthRefreshToken;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Account [deviceToken=" + deviceToken + ", email=" + email + ", id=" + id + ", description="
				+ description + ", lastUpdated=" + lastUpdated + "]";
	}

}