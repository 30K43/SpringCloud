/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingtest.entity;

import com.jeeplus.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会议规划练习Entity
 * @author LiuHao
 * @version 2019-02-18
 */
public class MeetingTest extends DataEntity<MeetingTest> {
	
	private static final long serialVersionUID = 1L;
	private User prolocutor;		// 会议主持者
	private User conferree;		// 参加会议者
	private String meetingContent;		// 会议内容
	private Date meetingStartTime;		// 会议开始时间
	private Date meetingCloseTime;		// 会议结束时间
	private String meetingWelfare;		// 会议福利
	private String meetingName;		// 会议名称
	
	public MeetingTest() {
		super();
	}

	public MeetingTest(String id){
		super(id);
	}

	@ExcelField(title="会议主持者", align=2, sort=7)
	public User getProlocutor() {
		return prolocutor;
	}

	public void setProlocutor(User prolocutor) {
		this.prolocutor = prolocutor;
	}
	
	@ExcelField(title="参加会议者", align=2, sort=8)
	public User getConferree() {
		return conferree;
	}

	public void setConferree(User conferree) {
		this.conferree = conferree;
	}
	
	@ExcelField(title="会议内容", align=2, sort=9)
	public String getMeetingContent() {
		return meetingContent;
	}

	public void setMeetingContent(String meetingContent) {
		this.meetingContent = meetingContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会议开始时间", align=2, sort=10)
	public Date getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(Date meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会议结束时间", align=2, sort=11)
	public Date getMeetingCloseTime() {
		return meetingCloseTime;
	}

	public void setMeetingCloseTime(Date meetingCloseTime) {
		this.meetingCloseTime = meetingCloseTime;
	}
	
	@ExcelField(title="会议福利", align=2, sort=12)
	public String getMeetingWelfare() {
		return meetingWelfare;
	}

	public void setMeetingWelfare(String meetingWelfare) {
		this.meetingWelfare = meetingWelfare;
	}
	
	@ExcelField(title="会议名称", align=2, sort=13)
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
}