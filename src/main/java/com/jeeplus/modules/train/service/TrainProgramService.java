/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.mapper.JsonMapper;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.train.entity.ProgramActivity;
import com.jeeplus.modules.train.entity.TrainProgram;
import com.jeeplus.modules.train.dao.TrainProgramDao;

/**
 * 培训计划Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class TrainProgramService extends CrudService<TrainProgramDao, TrainProgram> {
	@Autowired
	private ProgramActivityService programActivityService;

	@Autowired
	private TrainProgramDao trainProgramDao;
	
	public TrainProgram get(String id) {
		return super.get(id);
	}
	
	public List<TrainProgram> findList(TrainProgram trainProgram) {
		return super.findList(trainProgram);
	}
	
	public Page<TrainProgram> findPage(Page<TrainProgram> page, TrainProgram trainProgram) {
		//BaseService.dataScopeFileForOffice(trainProgram);
		return super.findPage(page, trainProgram);
	}
	
	@Transactional(readOnly = false)
	public void save(TrainProgram trainProgram) {
		
		String id = trainProgram.getId();
		if(null == id || "".equals(id)){
			id = UUID.randomUUID().toString();
			trainProgram.setId(id);
			trainProgram.setIsNewRecord(true);
		}
		programActivityService.deleteTrainProm(id);
		String activeStr = trainProgram.getActiveStr();
		if(null!=activeStr && !"".equals(activeStr)){
			JSONArray jsonArray = JSONArray.parseArray(activeStr);
			for (int i = 0; i < jsonArray.size(); i++) {
				ProgramActivity obj = JSONObject.parseObject(jsonArray.getString(i), ProgramActivity.class);
				if((null == obj.getName() || "".equals(obj.getName().trim()))&&
						(null == obj.getSpace() || "".equals(obj.getSpace().trim()))&&
						(null == obj.getProTime() || "".equals(obj.getProTime().trim()))
						){
					continue;
					
				}
				obj.setId(UUID.randomUUID().toString());
				obj.setTrainProgramId(id);
				obj.setIsNewRecord(true);
				programActivityService.save(obj);
			}
		}
		
		super.save(trainProgram);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrainProgram trainProgram) {
		super.delete(trainProgram);
	}
	
	
	@Transactional(readOnly = false)
	public void updateRelease(String id) {
		trainProgramDao.updateRelease(id);
		
	}
	
	
	
}