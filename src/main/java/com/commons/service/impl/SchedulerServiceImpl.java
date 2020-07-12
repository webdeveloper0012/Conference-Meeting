package com.commons.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import com.commons.entity.Scheduler;
import com.commons.repository.SchedulerRepository;
import com.commons.service.SchedulerService;

@Service
public class SchedulerServiceImpl extends BasicService<Scheduler, SchedulerRepository> implements SchedulerService {

}
