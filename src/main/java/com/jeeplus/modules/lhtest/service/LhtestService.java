/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.lhtest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.lhtest.entity.Lhtest;
import com.jeeplus.modules.lhtest.mapper.LhtestMapper;

/**
 * 测试练习Service
 * @author Liuhao
 * @version 2019-02-15
 */
@Service
@Transactional(readOnly = true)
public class LhtestService extends CrudService<LhtestMapper, Lhtest> {

	public Lhtest get(String id) {
		return super.get(id);
	}
	
	public List<Lhtest> findList(Lhtest lhtest) {
		return super.findList(lhtest);
	}
	
	public Page<Lhtest> findPage(Page<Lhtest> page, Lhtest lhtest) {
		return super.findPage(page, lhtest);
	}
	
	@Transactional(readOnly = false)
	public void save(Lhtest lhtest) {
		super.save(lhtest);
	}
	
	@Transactional(readOnly = false)
	public void delete(Lhtest lhtest) {
		super.delete(lhtest);
	}
	
}