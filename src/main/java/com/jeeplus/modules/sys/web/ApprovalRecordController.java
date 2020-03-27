/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.lucene.LuceneCreateIndexByDirectory;
import com.jeeplus.common.utils.lucene.LuceneCreateIndexByFile;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.service.ApprovalRecordService;

/**
 * 审批意见Controller
 * @author panjp
 * @version 2017-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/approvalRecord")
public class ApprovalRecordController extends BaseController {

	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private MesanInfoService mesanInfoService;
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute
	public ApprovalRecord get(@RequestParam(required=false) String id) {
		ApprovalRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = approvalRecordService.get(id);
		}
		if (entity == null){
			entity = new ApprovalRecord();
		}
		return entity;
	}
	
	/**
	 * 审批意见列表页面
	 */
	@RequestMapping(value = {"list"})
	public String list(ApprovalRecord approvalRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApprovalRecord> page = approvalRecordService.findPage(new Page<ApprovalRecord>(request, response), approvalRecord); 
		model.addAttribute("page", page);
		return "modules/sys/approvalRecordList";
	}

	/**
	 * 查看，增加，编辑审批意见表单页面
	 */
	@RequestMapping(value = "form")
	public String form(ApprovalRecord approvalRecord, Model model,String ids,String url) {
		model.addAttribute("approvalRecord", approvalRecord);
		model.addAttribute("ids", ids);
		model.addAttribute("url", url);
		return "modules/sys/approvalRecordForm";
	}

	/**
	 * 保存审批意见
	 */
	@RequestMapping(value = "save")
	public String save(ApprovalRecord approvalRecord,String ids, String url,Model model, RedirectAttributes redirectAttributes) throws Exception{
		String idsSt[] = ids.split(",");
		for (int i = 0; i < idsSt.length; i++) {
			approvalRecord.setId(idsSt[i]);
			approvalRecord.setResourceId(idsSt[i]);
			approvalRecordService.save(approvalRecord,idsSt[i]);
			
			//创建lucene索引
			String resourceId = idsSt[i];//资源ID
			MesanInfo mesanInfo = mesanInfoService.get(resourceId);
			String path = Global.getConfig("userfiles.basedir");
			String filePath = path+mesanInfo.getMesanUrl().substring(6, mesanInfo.getMesanUrl().length());
			filePath = URLDecoder.decode(filePath,"UTF-8");//解码
			LuceneCreateIndexByFile.createIndex(filePath, Global.getConfig("lucene.indexdir"));
		}
		addMessage(redirectAttributes, "保存审批意见成功");
		return "redirect:"+Global.getAdminPath()+url+"?repage";
	}

	/**
	 * 初始化lucene索引
	 */
	@RequestMapping(value = "initLuceneIndex")
	public void initLuceneIndex(HttpServletResponse response) throws Exception{
		String message = "创建搜索索引成功";
		try {
			LuceneCreateIndexByDirectory.createIndex(Global.getConfig("userfiles.basedir"), Global.getConfig("lucene.indexdir"));
		}catch(Exception e) {
			message = "创建失败";
		}
		response.getWriter().print(message);
	}
	
	/**
	 * 删除审批意见
	 */
	@RequestMapping(value = "delete")
	public String delete(ApprovalRecord approvalRecord, RedirectAttributes redirectAttributes) {
		approvalRecordService.delete(approvalRecord);
		addMessage(redirectAttributes, "删除审批意见成功");
		return "redirect:"+Global.getAdminPath()+"/sys/approvalRecord/?repage";
	}
	
	/**
	 * 批量删除审批意见
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			approvalRecordService.delete(approvalRecordService.get(id));
		}
		addMessage(redirectAttributes, "删除审批意见成功");
		return "redirect:"+Global.getAdminPath()+"/sys/approvalRecord/?repage";
	}
	
	
	

}