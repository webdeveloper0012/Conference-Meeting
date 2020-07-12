package com.commons.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.commons.entity.User;
import com.commons.model.Authentication;
import com.commons.repository.UserRepository;
import com.commons.service.UserService;

@Service
public class UserServiceImpl extends BasicService<User, UserRepository> implements UserService {

	@Autowired
	UserRepository UserRepository;
	
	@Autowired
	HttpSession session;
	
	@Override
	public boolean isAuthenticate(Authentication u) {
		if(null == UserRepository.findByEmailAndPassword(u.getEmail(), u.getPassword())) {
			return false;
		}else {
			session.putValue(u.getEmail(), u.getPassword());
			return true;
		}
	}

}
