package com.sercurity.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component

//tạo messageRepository để thêm mess and 2 fun để thêm và get mess
public class MessageRepository {

	
	private List<String> list = new ArrayList<>();

	public void addMessage(String message) {
		list.add(message);
	}

	public String getAllMessages() {
		return list.toString();
	}
}
