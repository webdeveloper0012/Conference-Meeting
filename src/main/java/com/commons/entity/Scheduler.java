package com.commons.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "scheduler")
public class Scheduler {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	private User owner;
	
	@Column(name = "event_name")
	private String eventName;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "schedules_user", 
			joinColumns = {@JoinColumn(name = "schedules_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = {@JoinColumn(name = "user_id",nullable = false, updatable = false) })
	public Set<User> user;

	@ManyToOne
	@JoinColumn(name = "meeting_room_details_id")
	private MeetingRoomDetails meetingRoomDetails;
	
	@ManyToOne
	@JoinColumn(name = "duration_id")
	private Duration duration;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "schedule_date")
	private Date scheduleDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public MeetingRoomDetails getMeetingRoomDetails() {
		return meetingRoomDetails;
	}

	public void setMeetingRoomDetails(MeetingRoomDetails meetingRoomDetails) {
		this.meetingRoomDetails = meetingRoomDetails;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Scheduler() {
	}

	public Scheduler(MeetingRoomDetails meetingRoomDetails, Duration duration) {
		super();
		this.meetingRoomDetails = meetingRoomDetails;
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((meetingRoomDetails == null) ? 0 : meetingRoomDetails.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scheduler other = (Scheduler) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (meetingRoomDetails == null) {
			if (other.meetingRoomDetails != null)
				return false;
		} else if (!meetingRoomDetails.equals(other.meetingRoomDetails))
			return false;
		return true;
	}

}
