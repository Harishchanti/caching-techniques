package com.cache.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.cache.example.config.AerospikeConfiguration;

@Service
public class AerospikeCacheService implements CacheService {

	private final Logger log = LoggerFactory.getLogger(AerospikeCacheService.class);

	@Autowired
	AerospikeClient client;

	@Override
	public boolean put(String key, Object value) {
		boolean sucess = true;
		try {
			Value mapValue = Value.get(value);
			Key clientKey = getClientKey(AerospikeConfiguration.getNameSpace(), "book", key);
			Bin bin = new Bin("book", value);
			WritePolicy w = new WritePolicy();
			w.sendKey = true;
			client.put(w, clientKey, bin);
			log.info("Added Recored :  Key : {} \n value : {} \n size : {}", key, value, mapValue.estimateSize());
		} catch (AerospikeException e) {
			log.error("Error Adding recodes to Aerospike : Key : {} \n length : {} \n value : {} ", key, key.length(),
					value);
			sucess = false;
			e.printStackTrace();
		}
		return sucess;
	}

	private Key getClientKey(String nameSpace, String set, String key) throws AerospikeException {
		return new Key(nameSpace, set, key);
	}

	@Override
	public Object get(String key) {
		Object bookDTO = null;
		//BookDTO bookDTO = null;
		try {
			Key clientKey = getClientKey(AerospikeConfiguration.getNameSpace(), "book", key);
			Policy policy = new Policy();
			Record record = client.get(policy, clientKey);
			if (record != null) {
				//bookDTO = (BookDTO) record.bins.get("book");
				bookDTO = record.bins.get("book");
			}
		} catch (AerospikeException e) {
			log.error("Error while getting the recode for Key : {}", key);
			e.printStackTrace();
		}

		return bookDTO;
	}

	@Override
	public CacheType getCacheType() {
		return CacheType.AEROSPIKE;
	}

}
