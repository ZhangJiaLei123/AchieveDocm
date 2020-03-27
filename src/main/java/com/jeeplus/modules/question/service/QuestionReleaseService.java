/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.question.entity.QuestionRelease;
import com.jeeplus.modules.question.dao.QuestionReleaseDao;

/**
 * 问卷发布Service
 * @author wsp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class QuestionReleaseService extends CrudService<QuestionReleaseDao, QuestionRelease> {

	public QuestionRelease get(String id) {
		return super.get(id);
	}
	
	public List<QuestionRelease> findList(QuestionRelease questionRelease) {
		return super.findList(questionRelease);
	}
	
	public Page<QuestionRelease> findPage(Page<QuestionRelease> page, QuestionRelease questionRelease) {
		return super.findPage(page, questionRelease);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionRelease questionRelease) {
		super.save(questionRelease);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuestionRelease questionRelease) {
		super.delete(questionRelease);
	}
	
	
	
	
}