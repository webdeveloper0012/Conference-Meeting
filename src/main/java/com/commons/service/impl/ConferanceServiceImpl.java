package com.commons.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.entity.Duration;
import com.commons.entity.MeetingRoomDetails;
import com.commons.entity.Scheduler;
import com.commons.entity.User;
import com.commons.model.DataTableJsonObject;
import com.commons.repository.DurationRepository;
import com.commons.repository.MeetingRoomDetailsRepository;
import com.commons.repository.SchedulerRepository;
import com.commons.service.ConferanceService;

@Service
public class ConferanceServiceImpl implements ConferanceService{

	@Autowired
	SchedulerRepository schedulerRepository;

	@Autowired
	DurationRepository durationRepository;

	@Autowired
	MeetingRoomDetailsRepository meetingRoomDetailsRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public DataTableJsonObject featchAll(String orderField, String orderDirection, String searchParameter, Integer startRec,
			Integer pageDisplayLength, Integer pageNumber, String location, String durationid, String time, Date date_,
			boolean issearch) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<Scheduler> list;
		if(!issearch) {
			System.out.println("today date- "+sf.format(new Date()));
			Query query = em.createQuery("select s from Scheduler s where s.scheduleDate = \'"+sf.format(new Date())+"\' order by "+orderField +" "+ orderDirection);
			list = query.getResultList();
		}else {
			System.out.println("search  date- "+sf.format(date_));
			Duration duration = durationRepository.findById(Long.parseLong(durationid)).get();
			List<MeetingRoomDetails> meetingRoomDetailsList = meetingRoomDetailsRepository.findByLocationOrderByNameDesc(location);

			Query query = em.createQuery("select s from Scheduler s where s.scheduleDate = \'"+sf.format(date_) + "\' and s.meetingRoomDetails.location like \'"+location + "\'") ;
			List<Scheduler> schedulerList = query.getResultList();

			list = new ArrayList<Scheduler>(0); 
			for (MeetingRoomDetails meetingRoomDetails : meetingRoomDetailsList) {

				if(schedulerList.contains(new Scheduler(meetingRoomDetails, duration))) {
					continue;
				}else {
					Scheduler s = new Scheduler();
					s.setId(new Long(0));
					s.setEventName("-");
					s.setDuration(duration);
					s.setMeetingRoomDetails(meetingRoomDetails);
					s.setScheduleDate(date_);
					s.setOwner(null);
					list.add(s);
				}
			}
		}
		DataTableJsonObject dt = new DataTableJsonObject();
		dt.setAaData(list);
		dt.setiTotalRecords(list.size());
		dt.setiTotalDisplayRecords(list.size());
		return dt;
	}


	@Override
	public List<Duration> fetchAllDuration() {
		return durationRepository.findAll();
	}


	@Override
	public Scheduler bookedSchedule(String meetingid, String durationid, String eventname, String scheduleDate, User user) {
		Scheduler s = new Scheduler();
		s.setMeetingRoomDetails(new MeetingRoomDetails(Long.parseLong(meetingid)));
		s.setDuration(new Duration(Long.parseLong(durationid)));
		s.setEventName(eventname);
		s.setScheduleDate(new Date(scheduleDate));
		s.setOwner(user);
		return schedulerRepository.save(s);
	}


}
