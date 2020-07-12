package com.commons.service.impl;

import org.springframework.stereotype.Service;
import com.commons.entity.MeetingRoomDetails;
import com.commons.repository.MeetingRoomDetailsRepository;
import com.commons.service.MeetingRoomDetailsService;

@Service
public class MeetingRoomDetailsServiceImpl extends BasicService< MeetingRoomDetails,  MeetingRoomDetailsRepository> implements  MeetingRoomDetailsService {

}
