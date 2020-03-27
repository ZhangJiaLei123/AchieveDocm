/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.question.entity.QuestionProblem;
import com.jeeplus.modules.question.entity.QuestionResult;

/**
 * 问卷题目DAO接口
 * @author wsp
 * @version 2017-03-26
 */
@MyBatisDao
public interface QuestionProblemDao extends CrudDao<QuestionProblem> {
	//获取题目和用户答题信息
	public List<QuestionProblem> findPageByQuestionIdAndUid(QuestionProblem questionProblem);
	//调查统计结果
	public List<QuestionProblem> formDetailTotal(QuestionProblem questionProblem);
	//获取当前最大的序号
	public String getMaxSortByQuestionId(QuestionProblem questionProblem);
	//根据questionId、prosort寻找未删除的
	public QuestionProblem getByQuestionIdAndSort(QuestionProblem questionProblem);
}