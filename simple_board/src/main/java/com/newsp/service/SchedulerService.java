package com.newsp.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	@Scheduled(cron="*/30 * * * * *")
	public void doScheduled() {
		//System.out.println("스케쥴러 실행");
	}
}
