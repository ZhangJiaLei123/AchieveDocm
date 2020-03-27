/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.ProgramFocus;
import com.jeeplus.modules.train.dao.ProgramFocusDao;

/**
 * 用户关注活动计划表Service
 * @author wsp
 * @version 2017-04-08
 */
@Service
@Transactional(readOnly = true)
public class ProgramFocusService extends CrudService<ProgramFocusDao, ProgramFocus> {

	public ProgramFocus get(String id) {
		return super.get(id);
	}
	
	public List<ProgramFocus> findList(ProgramFocus programFocus) {
		return super.findList(programFocus);
	}
	
	public Page<ProgramFocus> findPage(Page<ProgramFocus> page, ProgramFocus programFocus) {
		return super.findPage(page, programFocus);
	}
	
	@Transactional(readOnly = false)
	public void save(ProgramFocus programFocus) {
		super.save(programFocus);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProgramFocus programFocus) {
		super.delete(programFocus);
	}
	
	
	
	
}