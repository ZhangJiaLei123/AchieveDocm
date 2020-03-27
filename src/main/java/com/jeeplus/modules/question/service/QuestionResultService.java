/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.question.entity.QuestionResult;
import com.jeeplus.modules.question.dao.QuestionResultDao;

/**
 * 用户答题信息表Service
 * @author wsp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class QuestionResultService extends CrudService<QuestionResultDao, QuestionResult> {

	@Autowired
	private QuestionResultDao questionResultDao;
	
	public QuestionResult get(String id) {
		return super.get(id);
	}
	
	public List<QuestionResult> findList(QuestionResult questionResult) {
		return super.findList(questionResult);
	}
	
	public Page<QuestionResult> findPage(Page<QuestionResult> page, QuestionResult questionResult) {
		return super.findPage(page, questionResult);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionResult questionResult) {
		super.save(questionResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuestionResult questionResult) {
		super.delete(questionResult);
	}
	
	//
	public Page<QuestionResult> findPageGroupRid(Page<QuestionResult> page, QuestionResult questionResult) {
		Page<QuestionResult> tmp = super.findPage(page, questionResult);
		tmp.setList(questionResultDao.findPageGroupRid(questionResult));
		return tmp;
	}
	
	
}