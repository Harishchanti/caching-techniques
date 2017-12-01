package com.cache.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cache.example.dto.BookDTO;
import com.cache.example.services.CacheFactory;
import com.cache.example.services.CacheService;
import com.cache.example.services.CacheType;

@RestController("/api")
public class BookController {

	@Autowired
	CacheFactory cacheFactory;

	@RequestMapping(method = RequestMethod.POST, value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean put(@RequestBody BookDTO bootDto, @RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "type", defaultValue = "aerospike", required = false) String type) {
		CacheService cacheService = cacheFactory.getCacheObject(getCacheType(type));
		return cacheService.put(key, bootDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<BookDTO> get(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "type", defaultValue = "aerospike", required = false) String type) {
		CacheService cacheService = cacheFactory.getCacheObject(getCacheType(type));
		Object value;
		if ((value = cacheService.get(key)) == null) {
			return new ResponseEntity<BookDTO>(new BookDTO(), HttpStatus.NO_CONTENT);
		}
		BookDTO book = (BookDTO) value;
		return new ResponseEntity<BookDTO>(book, HttpStatus.OK);
	}

	private CacheType getCacheType(String type) {
		CacheType cacheType = CacheType.AEROSPIKE;
		if (type.equals("redis")) {
			cacheType = CacheType.REDIS;
		}
		return cacheType;
	}
}
