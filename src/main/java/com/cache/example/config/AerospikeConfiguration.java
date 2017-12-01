package com.cache.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;

@Configuration
public class AerospikeConfiguration implements EnvironmentAware {

	private String host;
	private int port;
	private static String namespace;

	private Environment env;

	private final Logger log = LoggerFactory.getLogger(AerospikeConfiguration.class);

	public static String getNameSpace() {
		return namespace;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Bean
	public AerospikeClient asClient() throws AerospikeException {
		log.info("Initializing Aerospike ");
		host = env.getProperty("aerospike.host");
		port = Integer.parseInt(env.getProperty("aerospike.port"));
		namespace = env.getProperty("aerospike.namespace");
		return new AerospikeClient(host, port);
	}
}
