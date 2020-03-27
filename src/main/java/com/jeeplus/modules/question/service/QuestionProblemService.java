/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.question.entity.QuestionProblem;
import com.jeeplus.modules.question.entity.QuestionResult;
import com.jeeplus.modules.question.dao.QuestionProblemDao;
import com.jeeplus.modules.question.dao.QuestionResultDao;

/**
 * 问卷题目Service
 * @author wsp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class QuestionProblemService extends CrudService<QuestionProblemDao, QuestionProblem> {

	@Autowired
	private QuestionProblemDao questionProblemDao;
	@Autowired 
	private QuestionResultDao questionResultDao;
	
	public QuestionProblem get(String id) {
		return super.get(id);
	}
	
	public List<QuestionProblem> findList(QuestionProblem questionProblem) {
		return super.findList(questionProblem);
	}
	
	public Page<QuestionProblem> findPage(Page<QuestionProblem> page, QuestionProblem questionProblem) {
		return super.findPage(page, questionProblem);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionProblem questionProblem) {
		super.save(questionProblem);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuestionProblem questionProblem) {
		super.delete(questionProblem);
	}
	
	public Page<QuestionProblem> findPageByQuestionId(Page<QuestionProblem> page, QuestionProblem questionProblem) {
		return super.findPage(page, questionProblem);
	}
	
	//获取用户和答题信息
	public Page<QuestionProblem> findPageByQuestionIdAndUid(Page<QuestionProblem> page, QuestionProblem questionProblem) {
		questionProblem.setPage(page);
		Page<QuestionProblem> pageQuestionProblem = page.setList(questionProblemDao.findPageByQuestionIdAndUid(questionProblem));
		return pageQuestionProblem;
	}
	
	//调查统计结果
	public Page<QuestionProblem> formDetailTotal(Page<QuestionProblem> page, QuestionProblem questionProblem) {
		questionProblem.setPage(page);
		List<QuestionProblem> qusetionProblemList = questionProblemDao.findList(questionProblem);
		
		//编辑问题数目
		Iterator<QuestionProblem> it1 = qusetionProblemList.iterator();
        while(it1.hasNext()){
        	QuestionProblem temp = it1.next();
        	QuestionResult questionResult = new QuestionResult();
        	questionResult.setReleaseId(questionProblem.getReleaseId());
        	questionResult.setProblemId(temp.getId());
        	temp.setTotalNumA(0);
        	temp.setTotalNumB(0);
        	temp.setTotalNumC(0);
        	temp.setTotalNumD(0);
        	
        	List<QuestionResult> questionResultTmpList = questionResultDao.findList(questionResult);
        	Iterator<QuestionResult> itResult = questionResultTmpList.iterator();
        	while (itResult.hasNext()){
        		QuestionResult questionResultTmp = itResult.next();
        		if(questionResultTmp.getResAnswer().contains("a")){
        			temp.setTotalNumA(temp.getTotalNumA()+1);
            	}else if(questionResultTmp.getResAnswer().contains("b")){
            		temp.setTotalNumB(temp.getTotalNumB()+1);
            	}else if(questionResultTmp.getResAnswer().contains("c")){
            		temp.setTotalNumC(temp.getTotalNumC()+1);
            	}else if(questionResultTmp.getResAnswer().contains("d")){
            		temp.setTotalNumD(temp.getTotalNumD()+1);
            	}
        	}
        }
        
		Page<QuestionProblem> pageQuestionProblem = page.setList(qusetionProblemList);
		return pageQuestionProblem;
	}
	
	//获取当前排序最大的题目
	public String getMaxSortByQuestionId(QuestionProblem questionProblem) {
		return questionProblemDao.getMaxSortByQuestionId(questionProblem);
	}
	
	//根据questionId、prosort寻找未删除的
	public QuestionProblem getByQuestionIdAndSort(QuestionProblem questionProblem) {
		return questionProblemDao.getByQuestionIdAndSort(questionProblem);
	}
}