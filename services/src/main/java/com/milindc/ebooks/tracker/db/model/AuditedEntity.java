package com.milindc.ebooks.tracker.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;

@Data
@MappedSuperclass
public class AuditedEntity implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Temporal(TemporalType.DATE)
	protected Date updateDate;

	protected String updateUser;

	@Temporal(TemporalType.DATE)
	protected Date createDate;

	protected String createUser;

	@Version
	protected int version;

	protected int enabled = 1;
}