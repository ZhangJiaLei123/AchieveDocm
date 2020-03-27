package com.jeeplus.modules.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.dao.MesanMarkDao;
import com.jeeplus.modules.resource.entity.MesanMark;

@Service
@Transactional(readOnly = true)
public class MesanMarkService extends CrudService<MesanMarkDao, MesanMark> {
	
	@Autowired
	private MesanMarkDao mesanMarkDao;
	
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
	public void saveMesanMark(MesanMark mesanMark) {
		super.save(mesanMark);
	}
	
	/**
	 * 
	  *<dl>
	  *<dt><span class="strong">方法说明:</span></dt>
	  *<dd>主要是实现了什么功能</dd>
	  *</dl> 
	  *<dl><dt><span class="strong">创建时间:</span></dt>
	  *<dd> 2017年6月21日 上午10:29:54</dd></dl> 
	  *<dl><dt><span class="strong">author:</span></dt>
	  *<dd> duan_lizhi</dd></dl> 
	  *</dl> 
	  *@param mesanMark
	  *@return MesanMark
	 */
	public MesanMark getMesanMarkByMesanIdAndUserId(MesanMark mesanMark) {
		return mesanMarkDao.getMesanMarkByMesanIdAndUserId(mesanMark);
	}
	
	/**
	 * 
	  *<dl>
	  *<dt><span class="strong">方法说明:</span></dt>
	  *<dd>获取最新的资料平均分</dd>
	  *</dl> 
	  *<dl><dt><span class="strong">创建时间:</span></dt>
	  *<dd> 2017年6月21日 下午4:07:56</dd></dl> 
	  *<dl><dt><span class="strong">author:</span></dt>
	  *<dd> duan_lizhi</dd></dl> 
	  *</dl> 
	  *@param mesanId
	  *@return Double
	 */
	public Double getAvgMarkByMesanId(String mesanId) {
		return mesanMarkDao.getAvgMarkByMesanId(mesanId);
	}
}
