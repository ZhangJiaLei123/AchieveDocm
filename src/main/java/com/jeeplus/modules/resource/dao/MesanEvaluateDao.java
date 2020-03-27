package com.jeeplus.modules.resource.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.MesanEvaluate;

@MyBatisDao
public interface MesanEvaluateDao extends CrudDao<MesanEvaluate> {
	/**
	 * 
	  *<dl>
	  *<dt><span class="strong">方法说明:</span></dt>
	  *<dd>根据资料id查询评论</dd>
	  *</dl> 
	  *<dl><dt><span class="strong">创建时间:</span></dt>
	  *<dd> 2017年6月22日 上午11:51:01</dd></dl> 
	  *<dl><dt><span class="strong">author:</span></dt>
	  *<dd> duan_lizhi</dd></dl> 
	  *</dl> 
	  *@param mesanEvaluate
	  *@return List<MesanEvaluate>
	 */
	public List<MesanEvaluate> findMesanEvaluateListByEvaluateId(MesanEvaluate mesanEvaluate);
}
