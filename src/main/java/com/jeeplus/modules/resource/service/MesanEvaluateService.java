package com.jeeplus.modules.resource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.dao.MesanEvaluateDao;
import com.jeeplus.modules.resource.dao.MesanMarkDao;
import com.jeeplus.modules.resource.entity.MesanEvaluate;

@Service
@Transactional(readOnly = true)
public class MesanEvaluateService extends CrudService<MesanEvaluateDao, MesanEvaluate> {
	
	@Autowired
	private MesanEvaluateDao mesanEvaluateDao;
	
	/**
	 * 
	  *<dl>
	  *<dt><span class="strong">方法说明:</span></dt>
	  *<dd>新增资料评分</dd>
	  *</dl> 
	  *<dl><dt><span class="strong">创建时间:</span></dt>
	  *<dd> 2017年6月21日 上午09:50:00</dd></dl> 
	  *<dl><dt><span class="strong">author:</span></dt>
	  *<dd> duan_lizhi</dd></dl> 
	  *</dl> 
	  *@param mesanMark void
	 */
	@Transactional(readOnly = true)
	public void saveMesanEvaluation(MesanEvaluate mesanEvaluate) {
		super.save(mesanEvaluate);
	}
	
	public List<MesanEvaluate> findMesanEvaluateListByEvaluateId(MesanEvaluate mesanEvaluate) {
		return mesanEvaluateDao.findMesanEvaluateListByEvaluateId(mesanEvaluate);
	}
	
}
