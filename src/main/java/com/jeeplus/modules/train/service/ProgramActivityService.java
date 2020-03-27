/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.ProgramActivity;
import com.jeeplus.modules.train.dao.ProgramActivityDao;

/**
 * 计划活动Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class ProgramActivityService extends CrudService<ProgramActivityDao, ProgramActivity> {

	@Autowired
	ProgramActivityDao programActivityDao;
	public ProgramActivity get(String id) {
		return super.get(id);
	}
	
	public List<ProgramActivity> findList(ProgramActivity programActivity) {
		return super.findList(programActivity);
	}
	
	public Page<ProgramActivity> findPage(Page<ProgramActivity> page, ProgramActivity programActivity) {
		return super.findPage(page, programActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(ProgramActivity programActivity) {
		super.save(programActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProgramActivity programActivity) {
		super.delete(programActivity);
	}
	
	@Transactional(readOnly = false)
	public void deleteTrainProm(String trainProgramId) {
		programActivityDao.deleteTrainProm(trainProgramId);
	}
	public List<ProgramActivity> findGzList(ProgramActivity programActivity) {
		return programActivityDao.findGzList(programActivity);
	}
	
	
}