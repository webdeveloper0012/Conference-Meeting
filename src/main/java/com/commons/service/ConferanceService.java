package com.commons.service;

import java.util.Date;
import java.util.List;

import com.commons.entity.Duration;
import com.commons.entity.Scheduler;
import com.commons.entity.User;
import com.commons.model.DataTableJsonObject;

public interface ConferanceService {

	List<Duration> fetchAllDuration();

	DataTableJsonObject featchAll(String orderField, String orderDirection, String searchParameter, Integer startRec,
			Integer pageDisplayLength, Integer pageNumber, String location, String durationid, String time, Date date,
			boolean issearch);

	Scheduler bookedSchedule(String meetingid, String durationid, String eventname, String scheduleDate, User user);
}
