/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingtest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meetingtest.entity.MeetingTest;
import com.jeeplus.modules.meetingtest.mapper.MeetingTestMapper;

/**
 * 会议规划练习Service
 * @author LiuHao
 * @version 2019-02-18
 */
@Service
@Transactional(readOnly = true)
public class MeetingTestService extends CrudService<MeetingTestMapper, MeetingTest> {

	public MeetingTest get(String id) {
		return super.get(id);
	}
	
	public List<MeetingTest> findList(MeetingTest meetingTest) {
		return super.findList(meetingTest);
	}
	
	public Page<MeetingTest> findPage(Page<MeetingTest> page, MeetingTest meetingTest) {
		return super.findPage(page, meetingTest);
	}
	
	@Transactional(readOnly = false)
	public void save(MeetingTest meetingTest) {
		super.save(meetingTest);
	}
	
	@Transactional(readOnly = false)
	public void delete(MeetingTest meetingTest) {
		super.delete(meetingTest);
	}
	
}