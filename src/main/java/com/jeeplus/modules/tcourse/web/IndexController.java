package com.jeeplus.modules.tcourse.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.modules.sys.entity.CarouselImg;
import com.jeeplus.modules.tcourse.service.IndexService;
import com.yfhl.commons.web.BaseController;
/**
 * 首页action
 * @author Panjp
 *
 */
@Controller
public class IndexController extends BaseController{
	@Autowired
	IndexService indexService;
	/**
	 * 组织活动列表页面
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CarouselImg> lsCar = indexService.findCaruseImg(null);
		
		model.addAttribute("lsCar", lsCar);
		return "/index";
	}
	

}
