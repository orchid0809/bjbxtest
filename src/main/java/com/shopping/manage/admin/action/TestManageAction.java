package com.shopping.manage.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shopping.core.annotation.SecurityMapping;
import com.shopping.core.mv.JModelAndView;
import com.shopping.core.query.support.IPageList;
import com.shopping.core.tools.CommUtil;
import com.shopping.core.tools.Md5Encrypt;
import com.shopping.core.tools.WebForm;
import com.shopping.foundation.domain.Album;
import com.shopping.foundation.domain.Area;
import com.shopping.foundation.domain.Storefactory;
import com.shopping.foundation.domain.Test;
import com.shopping.foundation.domain.User;
import com.shopping.foundation.domain.query.StorefactoryQueryObject;
import com.shopping.foundation.domain.query.TestQueryObject;
import com.shopping.foundation.service.IAreaService;
import com.shopping.foundation.service.ISysConfigService;
import com.shopping.foundation.service.IUserConfigService;
import com.shopping.foundation.service.StorefactoryService;
import com.shopping.foundation.service.TestService;


@Controller
public class TestManageAction {
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private IAreaService areaService;

	@SecurityMapping(display = false, rsequence = 0, title = "test列表", value = "/admin/test_list.htm*", rtype = "admin", rname = "test管理", rcode = "admin_test_set", rgroup = "test")
	@RequestMapping({ "/admin/test_list.htm" })
	public ModelAndView test_list(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/test_list.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			//http://localhost:9001/bjbx,basePath
			url = CommUtil.getURL(request);
		}
		String params = "";
		//查询对象
		TestQueryObject qo = new TestQueryObject(currentPage,
				mv, orderBy, orderType);
		//声明查询表单对象
		WebForm wf = new WebForm();
		//把查询表单转换为classType查询对象,满足基本类型及多对一类型转换查询
		//mv.addObject("sms", sms);用于搜索条件
		wf.toQueryPo(request, qo, Test.class, mv);
		//得到结果列表工厂list
		IPageList pList = testService.list(qo);
		//saveIPageList2ModelAndView用于mv.addObject（各种需要加入mv中的对象），类似与getRequest().setAttribute("","");
		//mv.addObject("objs", pList.getResult());故页面循环对象为objs
		CommUtil.saveIPageList2ModelAndView(url
				+ "/admin/test_list.htm", "", params, pList, mv);
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "test添加", value = "/admin/test_add.htm*", rtype = "admin", rname = "test管理", rcode = "admin_test_set", rgroup = "test")
	@RequestMapping({ "/admin/test_add.htm" })
	public ModelAndView test_add(HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		ModelAndView mv = new JModelAndView("admin/blue/test_add.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		//实现父类抽象方法，可以用这种方式自己编写sql
//		List areas = this.areaService.query(
//				"select obj from Area obj where obj.parent.id is null", null,
//				-1, -1);

		mv.addObject("currentPage", currentPage);
//		mv.addObject("areas", areas);
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "test编辑", value = "/admin/test_edit.htm*", rtype = "admin", rname = "test管理", rcode = "admin_test_set", rgroup = "test")
	@RequestMapping({ "/admin/test_edit.htm" })
	public ModelAndView test_edit(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/test_edit.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		if ((id != null) && (!id.equals(""))) {
			Test test = this.testService.getObjById(Long.valueOf(Long.parseLong(id)));
			mv.addObject("obj", test);
			mv.addObject("currentPage", currentPage);
			mv.addObject("edit", Boolean.valueOf(true));
		}
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "test保存", value = "/admin/test_save.htm*", rtype = "admin", rname = "test管理", rcode = "admin_test_set", rgroup = "test")
	@RequestMapping({ "/admin/test_save.htm" })
	public ModelAndView test_save(HttpServletRequest request,
			HttpServletResponse response, String id, String area_id,
			String currentPage, String list_url, String add_url) throws Exception {
		WebForm wf = new WebForm();
		Test test = null;
		if (id.equals("")) {
			test = (Test) wf.toPo(request, Test.class);
			test.setAddTime(new Date());
		} else {
			Test obj = this.testService.getObjById(Long.valueOf(Long.parseLong(id)));
			test = (Test) wf.toPo(request, obj);
		}
		if (id.equals(""))
			this.testService.save(test);
		else
			this.testService.update(test);

		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("list_url", list_url);
		mv.addObject("op_title", "保存Test成功");
		if (add_url != null) {
			mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
		}
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "test删除", value = "/admin/storefactory_del.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/test_del.htm" })
	public String test_del(HttpServletRequest request, String mulitId) throws Exception {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				this.testService.delete(CommUtil.null2Long(id));
			}
		}
		return "redirect:/admin/test_list.htm";
	}
}
