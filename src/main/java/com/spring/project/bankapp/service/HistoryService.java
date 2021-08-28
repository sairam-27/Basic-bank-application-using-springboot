package com.spring.project.bankapp.service;

import java.util.List;

import com.spring.project.bankapp.models.History;

public interface HistoryService {
	
	public List<History> getRecordOfUser(String username);
	
	public void saveHistory(History history);
	
}
