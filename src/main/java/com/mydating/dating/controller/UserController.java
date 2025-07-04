package com.mydating.dating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mydating.dating.entity.User;
import com.mydating.dating.repository.UserRepository;
import com.mydating.dating.service.UserService;

@RestController
public class UserController {

    private final UserRepository userRepository;
	@Autowired
	UserService userService;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@PostMapping("/users")
	public ResponseEntity<?> saveUser(@RequestBody User user){
		return userService.saveUser(user);
	}
	
	
	@GetMapping("/users/gender/male")
	public ResponseEntity<?> findAllMaleUsers(){
		return userService.findAllMaleUsers();
	}
	
	@GetMapping("/users/gender/female")
	public ResponseEntity<?> findAllFemaleUsers(){
		return userService.findAllFemaleUsers();
	}
	
	@GetMapping("users/name/{name}")
	public ResponseEntity<?> findUserByName(@PathVariable String name){
		return userService.findUserByName(name);
	}
	
	
	@GetMapping("/users/best-match/{id}/{top}")
	public ResponseEntity<?> findBestMatch(@PathVariable int id, @PathVariable int top){
		return userService.findBestMatch(id, top);
	}
	
	@GetMapping("/users/age/{age}")
	public ResponseEntity<?> findUserByAge(@PathVariable int age){
		return userService.findUserByAge(age);
	}
	
	@GetMapping("/users/email/{email}")
	public ResponseEntity<?> findUserByEmail(@PathVariable String email){
		return userService.findUserByEmail(email);
	}
	
	@GetMapping("/users/phone/{phone}")
	public ResponseEntity<?> findUserByPhone(@PathVariable long phone){
		return userService.findUserBypPhone(phone);
	}
	
}
