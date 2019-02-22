/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.lhtest.entity;

import com.jeeplus.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 测试练习Entity
 * @author Liuhao
 * @version 2019-02-15
 */
public class Lhtest extends DataEntity<Lhtest> {
	
	private static final long serialVersionUID = 1L;
	private String course;		// 练习课程
	private User practicer;		// 练习人
	private Date practicetime;		// 练习时间
	private String practicecontents;		// 练习内容
	
	public Lhtest() {
		super();
	}

	public Lhtest(String id){
		super(id);
	}

	@ExcelField(title="练习课程", align=2, sort=7)
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	@ExcelField(title="练习人", fieldType=User.class, value="practicer.name", align=2, sort=8)
	public User getPracticer() {
		return practicer;
	}

	public void setPracticer(User practicer) {
		this.practicer = practicer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="练习时间", align=2, sort=9)
	public Date getPracticetime() {
		return practicetime;
	}

	public void setPracticetime(Date practicetime) {
		this.practicetime = practicetime;
	}
	
	@ExcelField(title="练习内容", align=2, sort=10)
	public String getPracticecontents() {
		return practicecontents;
	}

	public void setPracticecontents(String practicecontents) {
		this.practicecontents = practicecontents;
	}
	
}