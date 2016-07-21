package com.shopping.manage.admin.action;

import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.shopping.core.annotation.SecurityMapping;
import com.shopping.core.domain.virtual.SysMap;
import com.shopping.core.mv.JModelAndView;
import com.shopping.core.query.support.IPageList;
import com.shopping.core.tools.CommUtil;
import com.shopping.core.tools.Md5Encrypt;
import com.shopping.core.tools.WebForm;
import com.shopping.core.tools.database.DatabaseTools;
import com.shopping.foundation.domain.Accessory;
import com.shopping.foundation.domain.Album;
import com.shopping.foundation.domain.Area;
import com.shopping.foundation.domain.Evaluate;
import com.shopping.foundation.domain.Goods;
import com.shopping.foundation.domain.GoodsCart;
import com.shopping.foundation.domain.Message;
import com.shopping.foundation.domain.OrderForm;
import com.shopping.foundation.domain.Store;
import com.shopping.foundation.domain.StoreClass;
import com.shopping.foundation.domain.StoreGradeLog;
import com.shopping.foundation.domain.Storefactory;
import com.shopping.foundation.domain.SysConfig;
import com.shopping.foundation.domain.User;
import com.shopping.foundation.domain.query.StoreGradeLogQueryObject;
import com.shopping.foundation.domain.query.StoreQueryObject;
import com.shopping.foundation.domain.query.StorefactoryQueryObject;
import com.shopping.foundation.service.IAccessoryService;
import com.shopping.foundation.service.IAlbumService;
import com.shopping.foundation.service.IAreaService;
import com.shopping.foundation.service.IConsultService;
import com.shopping.foundation.service.IEvaluateService;
import com.shopping.foundation.service.IGoodsCartService;
import com.shopping.foundation.service.IGoodsService;
import com.shopping.foundation.service.IMessageService;
import com.shopping.foundation.service.IOrderFormLogService;
import com.shopping.foundation.service.IOrderFormService;
import com.shopping.foundation.service.IRoleService;
import com.shopping.foundation.service.IStoreClassService;
import com.shopping.foundation.service.IStoreGradeLogService;
import com.shopping.foundation.service.IStoreGradeService;
import com.shopping.foundation.service.IStoreService;
import com.shopping.foundation.service.ISysConfigService;
import com.shopping.foundation.service.ITemplateService;
import com.shopping.foundation.service.IUserConfigService;
import com.shopping.foundation.service.IUserService;
import com.shopping.foundation.service.StorefactoryService;
import com.shopping.manage.admin.tools.AreaManageTools;
import com.shopping.manage.admin.tools.StoreTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StorefactoryManageAction {

	//Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 
	//通过 @Autowired的使用来消除 set ，get方法
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IStoreGradeService storeGradeService;

	@Autowired
	private IStoreClassService storeClassService;

	@Autowired
	private IAreaService areaService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IConsultService consultService;

	@Autowired
	private AreaManageTools areaManageTools;

	@Autowired
	private StoreTools storeTools;

	@Autowired
	private DatabaseTools databaseTools;

	@Autowired
	private ITemplateService templateService;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private IStoreGradeLogService storeGradeLogService;

	@Autowired
	private IEvaluateService evaluateService;

	@Autowired
	private IGoodsCartService goodsCartService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IOrderFormLogService orderFormLogService;

	@Autowired
	private IAccessoryService accessoryService;

	@Autowired
	private IAlbumService albumService;

	@Autowired
	private StorefactoryService storefactoryService;

	@SecurityMapping(display = false, rsequence = 0, title = "工厂列表", value = "/admin/storefactory_list.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/storefactory_list.htm" })
	public ModelAndView storefactory_list(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/storefactory_list.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			//http://localhost:9001/bjbx,basePath
			url = CommUtil.getURL(request);
		}
		String params = "";
		//查询对象
		StorefactoryQueryObject qo = new StorefactoryQueryObject(currentPage,
				mv, orderBy, orderType);
		//声明查询表单对象
		WebForm wf = new WebForm();
		//把查询表单转换为classType查询对象,满足基本类型及多对一类型转换查询
		//mv.addObject("sms", sms);
		wf.toQueryPo(request, qo, Storefactory.class, mv);
		//得到结果列表工厂list
		IPageList pList = this.storefactoryService.list(qo);
		//saveIPageList2ModelAndView用于mv.addObject（各种需要加入mv中的对象），类似与getRequest().setAttribute("","");
		//mv.addObject("objs", pList.getResult());故页面循环对象为objs
		CommUtil.saveIPageList2ModelAndView(url
				+ "/admin/storefactory_list.htm", "", params, pList, mv);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "工厂添加", value = "/admin/storefactory_add.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/storefactory_add.htm" })
	public ModelAndView storefactory_add(HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		ModelAndView mv = new JModelAndView("admin/blue/storefactory_add.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);

		List areas = this.areaService.query(
				"select obj from Area obj where obj.parent.id is null", null,
				-1, -1);

		mv.addObject("currentPage", currentPage);
		mv.addObject("areas", areas);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "工厂编辑", value = "/admin/storefactory_edit.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/storefactory_edit.htm" })
	public ModelAndView storefactory_edit(HttpServletRequest request,
			HttpServletResponse response, String id, String currentPage) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/storefactory_edit.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		if ((id != null) && (!id.equals(""))) {
			Storefactory storefactory = this.storefactoryService
					.getObjById(Long.valueOf(Long.parseLong(id)));
			List areas = this.areaService.query(
					"select obj from Area obj where obj.parent.id is null",
					null, -1, -1);
			mv.addObject("areas", areas);
			mv.addObject("obj", storefactory);
			mv.addObject("currentPage", currentPage);
			mv.addObject("edit", Boolean.valueOf(true));
			if (storefactory.getArea() != null) {
				mv.addObject("area_info", this.areaManageTools
						.generic_area_info(storefactory.getArea()));
			}
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "工厂保存", value = "/admin/storefactory_save.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/storefactory_save.htm" })
	public ModelAndView storefactory_save(HttpServletRequest request,
			HttpServletResponse response, String id, String area_id,
			String currentPage, String list_url, String add_url,
			String user_id, String userName, String password) throws Exception {
		WebForm wf = new WebForm();
		Storefactory storefactory = null;
		if (id.equals("")) {
			storefactory = (Storefactory) wf.toPo(request, Storefactory.class);
			storefactory.setAddTime(new Date());
		} else {
			Storefactory obj = this.storefactoryService.getObjById(Long
					.valueOf(Long.parseLong(id)));
			storefactory = (Storefactory) wf.toPo(request, obj);
		}
		Area area = this.areaService.getObjById(CommUtil.null2Long(area_id));
		storefactory.setArea(area);
		if (id.equals(""))
			this.storefactoryService.save(storefactory);
		else
			this.storefactoryService.update(storefactory);

		if (user_id.equals("")) {
			User user = new User();
			user.setUserName(userName);
			if ((password != null) && (!password.equals(""))) {
				user.setPassword(Md5Encrypt.md5(password).toLowerCase());
			}
			user.setUserRole("BUYER_SELLER");
			user.getRoles().clear();
			Map params = new HashMap();
			params.put("type", "BUYER_SELLER");
			List roles = this.roleService.query(
					"select obj from Role obj where obj.type=:type", params,
					-1, -1);
			user.getRoles().addAll(roles);
			user.setFactory(storefactory);
			user.setAddTime(new Date());
			this.userService.save(user);

			Album album = new Album();
			album.setAddTime(new Date());
			album.setAlbum_default(true);
			album.setAlbum_name("默认相册");
			album.setAlbum_sequence(-10000);
			album.setUser(user);
			this.albumService.save(album);
		}

		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("list_url", list_url);
		mv.addObject("op_title", "保存工厂成功");
		if (add_url != null) {
			mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "工厂删除", value = "/admin/storefactory_del.htm*", rtype = "admin", rname = "工厂管理", rcode = "admin_store_set", rgroup = "工厂")
	@RequestMapping({ "/admin/storefactory_del.htm" })
	public String store_del(HttpServletRequest request, String mulitId)
			throws Exception {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Storefactory storefactory = this.storefactoryService.getObjById(Long.valueOf(Long.parseLong(id)));
				if (storefactory.getUser() != null){
//					Long FactoryID = storefactory.getUser().getFactory().getId();
//					User user = storefactory.getUser();
					//关闭外键检查，删除shopping_user中相关用户信息
					String sql = "delete from shopping_user where factory_id="+id;
					String sql2 = "SET FOREIGN_KEY_CHECKS = 0;";
					String sql3 = "SET FOREIGN_KEY_CHECKS = 1;";
					storefactoryService.executeNativeSQL(sql2);
					int result = storefactoryService.executeNativeSQL(sql);
					storefactoryService.executeNativeSQL(sql3);
//					storefactory.getUser().setFactory(null);
					if(result==1){
						this.storefactoryService.delete(CommUtil.null2Long(id));
					}
				}
			}
		}
		return "redirect:/admin/storefactory_list.htm";
	}

}
