package com.springfirstproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springfirstproject.domain.User;
import com.springfirstproject.exception.NotFoundException;
import com.springfirstproject.model.PageRequestModel;
import com.springfirstproject.model.PageModel;
import com.springfirstproject.repository.UserRepository;
import com.springfirstproject.service.util.HashUtil;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	public User save(User user) {
		
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		User createdUser = userRepository.save(user);
		return createdUser;				
	}
	
	public User update(User user) {
		
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}
	
	public User getById(Long id) {
		Optional<User> result = userRepository.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not user with id = " + id));
	}
	
	public List<User> listAll() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public PageModel<User> listAllOnLazyMode(PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<User> page = userRepository.findAll(pageable);
		PageModel<User> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	    return pm;	
	}
	
	public User login(String email, String password) {
		
		String hash = HashUtil.getSecureHash(password);
		
		Optional<User> result = userRepository.login(email, hash);
		return result.get();
	}

}
