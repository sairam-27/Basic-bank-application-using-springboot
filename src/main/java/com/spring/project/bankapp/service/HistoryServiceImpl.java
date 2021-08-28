package com.spring.project.bankapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.bankapp.models.History;
import com.spring.project.bankapp.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	HistoryRepository historyRepository;
	
	public HistoryServiceImpl(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	@Override
	@Transactional
	public List<History> getRecordOfUser(String username) {
		return historyRepository.findRecordsByUsername(username);
	}

	@Override
	@Transactional
	public void saveHistory(History history) {
		historyRepository.save(history);
	}

}
