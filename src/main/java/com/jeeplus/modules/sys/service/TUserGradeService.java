/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.TUserGradeDao;
import com.jeeplus.modules.sys.entity.TUserGrade;

/**
 * 用户等级管理Service
 * @author 潘兴武
 * @version 2018-01-04
 */
@Service
@Transactional(readOnly = true)
public class TUserGradeService extends CrudService<TUserGradeDao, TUserGrade> {

	public TUserGrade get(String id) {
		return super.get(id);
	}
	
	public List<TUserGrade> findList(TUserGrade tUserGrade) {
		return super.findList(tUserGrade);
	}
	
	public Page<TUserGrade> findPage(Page<TUserGrade> page, TUserGrade tUserGrade) {
		return super.findPage(page, tUserGrade);
	}
	
	@Transactional(readOnly = false)
	public void save(TUserGrade tUserGrade) {
		super.save(tUserGrade);
	}
	
	@Transactional(readOnly = false)
	public void delete(TUserGrade tUserGrade) {
		super.delete(tUserGrade);
	}
	
	
	
	
}