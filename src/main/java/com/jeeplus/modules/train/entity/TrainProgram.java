/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 培训计划Entity
 * @author panjp
 * @version 2017-03-24
 */
public class TrainProgram extends DataEntity<TrainProgram> {
	
	private static final long serialVersionUID = 1L;
	private String proName;		// 计划名称
	private String proType;		// 计划类型
	private Date gzStartTime;		// 关注开始时间
	private Date gzEndTime;		// 关注结束时间
	private String proRelease;		// 是否发布计划
	private String status;		// 状态
	private String activeStr;   //计划活动字符串
	private List<ProgramActivity> proGramList;
	boolean isGz;//是否可以关注
	
	
	
	public boolean getIsGz() {
		SimpleDateFormat  sdf = new SimpleDateFormat ("yyyy-MM-dd");
		Date nowDate = null;
		try {
			nowDate = sdf.parse(DateUtils.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null!=gzEndTime){
			if(nowDate.getTime()<=gzEndTime.getTime()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public void setIsGz(boolean isGz) {
		this.isGz = isGz;
	}

	private List<String> listTrainProgramIds;//TrainProgram的id集合
	
	private String isCreateAdmin;//是否管理员添加
	
	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}

	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}

	public TrainProgram() {
		super();
	}

	public TrainProgram(String id){
		super(id);
	}

	@Length(min=0, max=200, message="计划名称长度必须介于 0 和 200 之间")
	@ExcelField(title="计划名称", align=2, sort=7)
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@Length(min=0, max=10, message="计划类型长度必须介于 0 和 10 之间")
	@ExcelField(title="计划类型", dictType="train_program_type", align=2, sort=8)
	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="关注开始时间", align=2, sort=9)
	public Date getGzStartTime() {
		return gzStartTime;
	}

	public void setGzStartTime(Date gzStartTime) {
		this.gzStartTime = gzStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="关注结束时间", align=2, sort=10)
	public Date getGzEndTime() {
		return gzEndTime;
	}

	public void setGzEndTime(Date gzEndTime) {
		this.gzEndTime = gzEndTime;
	}
	
	@Length(min=0, max=10, message="是否发布计划长度必须介于 0 和 10 之间")
	@ExcelField(title="是否发布计划", align=2, sort=11)
	public String getProRelease() {
		return proRelease;
	}

	public void setProRelease(String proRelease) {
		this.proRelease = proRelease;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveStr() {
		return activeStr;
	}

	public void setActiveStr(String activeStr) {
		this.activeStr = activeStr;
	}

	public List<ProgramActivity> getProGramList() {
		return proGramList;
	}

	public void setProGramList(List<ProgramActivity> proGramList) {
		this.proGramList = proGramList;
	}

	public List<String> getListTrainProgramIds() {
		return listTrainProgramIds;
	}

	public void setListTrainProgramIds(List<String> listTrainProgramIds) {
		this.listTrainProgramIds = listTrainProgramIds;
	}

}