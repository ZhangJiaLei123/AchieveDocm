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
import com.jeeplus.modules.question.entity.QuestionInfo;
import com.jeeplus.modules.question.dao.QuestionInfoDao;

/**
 * 问卷信息Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class QuestionInfoService extends CrudService<QuestionInfoDao, QuestionInfo> {

	@Autowired
	QuestionInfoDao questionInfoDao;
	
	public QuestionInfo get(String id) {
		return super.get(id);
	}
	
	public List<QuestionInfo> findList(QuestionInfo questionInfo) {
		return super.findList(questionInfo);
	}
	public  Page<QuestionInfo> findSelQuesInfoPage(Page<QuestionInfo> page,QuestionInfo questionInfo) {
		questionInfo.setPage(page);
		page.setList(questionInfoDao.findSelQuesInfoPage(questionInfo));
		return page;
	}
	
	public Page<QuestionInfo> findPage(Page<QuestionInfo> page, QuestionInfo questionInfo) {
		return super.findPage(page, questionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionInfo questionInfo) {
		super.save(questionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuestionInfo questionInfo) {
		super.delete(questionInfo);
	}
	
	
	
	
}