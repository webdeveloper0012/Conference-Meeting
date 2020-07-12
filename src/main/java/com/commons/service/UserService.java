package com.commons.service;

import com.commons.entity.User;
import com.commons.model.Authentication;

public interface UserService extends IService<User>,IFinder<User>{

	boolean isAuthenticate(Authentication auth);
}
