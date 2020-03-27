/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.CarouselImg;
import com.jeeplus.modules.sys.service.CarouselImgService;

/**
 * 轮播图Controller
 * @author panjp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/carouselImg")
public class CarouselImgController extends BaseController {

	@Autowired
	private CarouselImgService carouselImgService;
	
	@ModelAttribute
	public CarouselImg get(@RequestParam(required=false) String id) {
		CarouselImg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carouselImgService.get(id);
		}
		if (entity == null){
			entity = new CarouselImg();
		}
		return entity;
	}
	
	/**
	 * 轮播图列表页面
	 */
	@RequiresPermissions("sys:carouselImg:list")
	@RequestMapping(value = {"list", ""})
	public String list(CarouselImg carouselImg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarouselImg> page = carouselImgService.findPage(new Page<CarouselImg>(request, response), carouselImg); 
		model.addAttribute("page", page);
		return "modules/sys/carouselImgList";
	}

	/**
	 * 查看，增加，编辑轮播图表单页面
	 */
	@RequiresPermissions(value={"sys:carouselImg:view","sys:carouselImg:add","sys:carouselImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CarouselImg carouselImg, Model model) {
		model.addAttribute("carouselImg", carouselImg);
		return "modules/sys/carouselImgForm";
	}

	/**
	 * 保存轮播图
	 */
	@RequiresPermissions(value={"sys:carouselImg:add","sys:carouselImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CarouselImg carouselImg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, carouselImg)){
			return form(carouselImg, model);
		}
		if(!carouselImg.getIsNewRecord()){//编辑表单保存
			CarouselImg t = carouselImgService.get(carouselImg.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(carouselImg, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			carouselImgService.save(t);//保存
		}else{//新增表单保存
			carouselImgService.save(carouselImg);//保存
		}
		addMessage(redirectAttributes, "保存轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
	}
	
	/**
	 * 删除轮播图
	 */
	@RequiresPermissions("sys:carouselImg:del")
	@RequestMapping(value = "delete")
	public String delete(CarouselImg carouselImg, RedirectAttributes redirectAttributes) {
		carouselImgService.delete(carouselImg);
		addMessage(redirectAttributes, "删除轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
	}
	
	/**
	 * 批量删除轮播图
	 */
	@RequiresPermissions("sys:carouselImg:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			carouselImgService.delete(carouselImgService.get(id));
		}
		addMessage(redirectAttributes, "删除轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:carouselImg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CarouselImg carouselImg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "轮播图"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CarouselImg> page = carouselImgService.findPage(new Page<CarouselImg>(request, response, -1), carouselImg);
    		new ExportExcel("轮播图", CarouselImg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出轮播图记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:carouselImg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CarouselImg> list = ei.getDataList(CarouselImg.class);
			for (CarouselImg carouselImg : list){
				try{
					carouselImgService.save(carouselImg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条轮播图记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条轮播图记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入轮播图失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
    }
	
	/**
	 * 下载导入轮播图数据模板
	 */
	@RequiresPermissions("sys:carouselImg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "轮播图数据导入模板.xlsx";
    		List<CarouselImg> list = Lists.newArrayList(); 
    		new ExportExcel("轮播图数据", CarouselImg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/carouselImg/?repage";
    }
	
	@RequestMapping(value = "checkName")
	public @ResponseBody String checkName(String titlname){
		String checkTitle = carouselImgService.checkTitle(titlname);
		return checkTitle;
	}
   /**
	 * 验证标题是否存在
	 * @param titl
	 * @param oldtitl
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:carouselImg:add","sys:carouselImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkTitl")
	public String checkTitl(String titl, String oldtitl) {
		if (titl !=null && titl.equals(oldtitl)) {
			return "true";
		} else if (titl !=null && !"".equals(titl.trim())) {
			CarouselImg carouselImg = new CarouselImg();
			carouselImg.setTitl(titl);
			List<CarouselImg> listCarouselImg = carouselImgService.findList(carouselImg);
			if(null==listCarouselImg || listCarouselImg.isEmpty()){
				return "true";
			}
		}
		return "false";
	}
	/**
	 * 验证序号是否已经存在
	 * @param sort
	 * @param oldsort
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:carouselImg:add","sys:carouselImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkSort")
	public String checkSort(String sort,String oldsort) {
		if (sort !=null && sort.equals(oldsort)) {
			return "true";
		}else if (sort !=null && !"".equals(sort.trim())) {
			CarouselImg carouselImg = new CarouselImg();
			carouselImg.setSort(sort);
			List<CarouselImg> listCarouselImg = carouselImgService.findList(carouselImg);
			if(null==listCarouselImg || listCarouselImg.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
}