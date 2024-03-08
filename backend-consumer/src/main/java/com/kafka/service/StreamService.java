package com.kafka.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kafka.entity.DataStream;

public interface StreamService 
{
	public ResponseEntity<List<DataStream>> getStreamData();
}
