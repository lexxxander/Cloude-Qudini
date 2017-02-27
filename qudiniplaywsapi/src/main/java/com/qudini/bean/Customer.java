package com.qudini.bean;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qudini.util.JsonJodaDateTimeSerializer;

public class Customer {

	private Integer id;
	private String name;

	private DateTime duetime;

	private DateTime jointime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonSerialize(using = JsonJodaDateTimeSerializer.class)
	public DateTime getDuetime() {
		return duetime;
	}

	public void setDuetime(DateTime duetime) {
		this.duetime = duetime;
	}

	@JsonSerialize(using = JsonJodaDateTimeSerializer.class)
	public DateTime getJointime() {
		return jointime;
	}

	public void setJointime(DateTime jointime) {
		this.jointime = jointime;
	}

}
