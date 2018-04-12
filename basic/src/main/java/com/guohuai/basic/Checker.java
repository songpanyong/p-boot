package com.guohuai.basic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class Checker {

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public @ResponseBody ResponseEntity<Long> check() {
		
		return new ResponseEntity<Long>(System.currentTimeMillis(), HttpStatus.OK);
	}

}
