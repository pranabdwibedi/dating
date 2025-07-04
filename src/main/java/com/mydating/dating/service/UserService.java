package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydating.dating.dao.UserDao;
import com.mydating.dating.dto.MatchingUser;
import com.mydating.dating.entity.User;
import com.mydating.dating.util.UserGender;
import com.mydating.dating.util.UserSorting;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	public ResponseEntity<?> saveUser(User user) {
		
		User savedUser = userDao.saveUser(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	public ResponseEntity<?> findAllMaleUsers() {
		
		List<User> maleUsers = userDao.findAllMaleUsers();
		if(maleUsers.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No male user found in the database");
		return ResponseEntity.status(HttpStatus.OK).body(maleUsers);
	}

	public ResponseEntity<?> findAllFemaleUsers() {
		List<User> femaleUser = userDao.findAllFemaleUser();
		if(femaleUser.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No female user found in the database");
		return ResponseEntity.status(HttpStatus.OK).body(femaleUser);
	}

	public ResponseEntity<?> findBestMatch(int id, int top) {
		Optional<User> optional = userDao.findById(id);
		if(optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id! User not found in DataBase");
		User user = optional.get();
		List<User> candidates = null;
		if(user.getGender().equals(UserGender.MALE))
			candidates = userDao.findAllFemaleUser();
		else
			candidates = userDao.findAllMaleUsers();
		List<MatchingUser> matchingUsers = new ArrayList<>();
		for(User u : candidates) {
			MatchingUser matchUser = new MatchingUser();
			matchUser.setId(u.getId());
			matchUser.setName(u.getName());
			matchUser.setEmail(u.getEmail());
			matchUser.setPhone(u.getPhone());
			matchUser.setAge(u.getAge());
			matchUser.setGender(u.getGender());
			matchUser.setInterests(u.getInterests());
			matchUser.setAgediff(Math.abs(user.getAge()-u.getAge()));
			int matchCount = 0;
			for(String interest : user.getInterests())
				if(u.getInterests().contains(interest))
					matchCount++;
			matchUser.setMatchingInterestCount(matchCount);
			matchingUsers.add(matchUser);
		}
		Collections.sort(matchingUsers, new UserSorting());		
		List<MatchingUser> result = new ArrayList<>();
		for(int i=0; i<top; i++)
			result.add(matchingUsers.get(i));
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public ResponseEntity<?> findUserByName(String name) {
		List<User> users = userDao.findUserByName(name);
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid name! Unable to find ");
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	public ResponseEntity<?> findUserByAge(int age) {
		List<User> users = userDao.findUserByAge(age);
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this age");
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	public ResponseEntity<?> findUserByEmail(String email) {
		Optional<User> optional = userDao.findUserByEmail(email);
		if(optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid email! User not found");
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}

	public ResponseEntity<?> findUserBypPhone(long phone) {
		Optional<User> optional = userDao.findUserByPhone(phone);
		if(optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid phone number! Unable to find user");
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}

	public ResponseEntity<?> searchUsersByLettersOfName(String letters) {
		List<User> users = userDao.searchUsersByLettersOfName("%"+letters+"%");
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find any user with letters : "+letters);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	public ResponseEntity<?> searchUsersByLetterOfEmail(String letters) {
		List<User> users = userDao.searchUsersByLettersOfEmail("%"+letters+"%");
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find any user with letters : "+letters);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
}
