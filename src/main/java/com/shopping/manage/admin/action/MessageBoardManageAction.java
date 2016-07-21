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
import com.shopping.core.domain.virtual.SysMap;
import com.shopping.core.mv.JModelAndView;
import com.shopping.core.query.support.IPageList;
import com.shopping.core.security.support.SecurityUserHolder;
import com.shopping.core.tools.CommUtil;
import com.shopping.core.tools.Md5Encrypt;
import com.shopping.core.tools.WebForm;
import com.shopping.foundation.domain.Area;
import com.shopping.foundation.domain.MessageBoard;
import com.shopping.foundation.domain.Store;
import com.shopping.foundation.domain.StoreGradeLog;
import com.shopping.foundation.domain.User;
import com.shopping.foundation.domain.query.MessageBoardQueryObject;
import com.shopping.foundation.service.IStoreService;
import com.shopping.foundation.service.ISysConfigService;
import com.shopping.foundation.service.IUserConfigService;
import com.shopping.foundation.service.IUserService;
import com.shopping.foundation.service.MessageBoardService;
import com.shopping.manage.admin.tools.DateUtil;
import com.shopping.manage.admin.tools.IdUtil;

@Controller
public class MessageBoardManageAction {
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;
	
	@Autowired
	private MessageBoardService messageBoardService;
	
	@Autowired
	private IStoreService storeService;
	
	@Autowired
	private IUserService userService;
	
	@SecurityMapping(display = false, rsequence = 0, title = "留言列表", value = "/admin/messageboardmanage_list.htm*", rtype = "admin", rname = "留言", rcode = "messageboardmanage_admin", rgroup = "留言")
	@RequestMapping({ "/admin/messageboardmanage_list.htm" })
	public ModelAndView messageboardmanage_list(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType, String q_message_status) {
		ModelAndView mv = new JModelAndView("admin/blue/messageboardmanage_list.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		MessageBoardQueryObject qo = new MessageBoardQueryObject(currentPage, mv,orderBy, orderType);
		qo.setPageSize(Integer.valueOf(15));
		qo.setOrderBy("addTime");
		qo.setOrderType("desc");
		//获取当前登陆用户信息
		User user = this.userService.getObjById(SecurityUserHolder.getCurrentUser().getId());
		q_message_status = CommUtil.null2String(q_message_status);
		//收到的留言
		if(q_message_status.equals("1")){
			qo.addQuery("obj.receive_user.id", new SysMap("receive_user_id", user.getId()), "=");
		}
		//发送的留言
		if(q_message_status.equals("2")){
			qo.addQuery("obj.user.id", new SysMap("user_id", user.getId()), "=");
		}
		//未读留言
		if(q_message_status.equals("3")){
			qo.addQuery("obj.readStatus",new SysMap("readStatus", Integer.valueOf(0)), "=");
		}
		//已读留言
		if(q_message_status.equals("4")){
			qo.addQuery("obj.readStatus",new SysMap("readStatus", Integer.valueOf(1)), "=");
		}
		IPageList pList = this.messageBoardService.list(qo);
		CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		
		mv.addObject("q_message_status", q_message_status);
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "后台发送留言", value = "/admin/messageboardmanage_add.htm*", rtype = "admin", rname = "留言管理", rcode = "admin_messageboardmanage_set", rgroup = "留言板")
	@RequestMapping({ "/admin/messageboardmanage_add.htm" })
	public ModelAndView messageboardmanage_add(HttpServletRequest request,HttpServletResponse response, String currentPage) {
		ModelAndView mv = new JModelAndView("admin/blue/messageboardmanage_add.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		//工厂列表
		List stores = this.storeService.query(
				"select obj from Store obj where 1=1", null,
				-1, -1);

		mv.addObject("currentPage", currentPage);
		mv.addObject("stores", stores);
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "后台回复留言", value = "/admin/messageboardmanage_edit.htm*", rtype = "admin", rname = "留言管理", rcode = "admin_messageboardmanage_set", rgroup = "留言")
	@RequestMapping({ "/admin/messageboardmanage_edit.htm" })
	public ModelAndView messageboardmanage_edit(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String id) {
		ModelAndView mv = new JModelAndView(
				"admin/blue/messageboardmanage_edit.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		if ((id != null) && (!id.equals(""))) {
			MessageBoard messageBoard = this.messageBoardService.getObjById(Long.valueOf(Long.parseLong(id)));
			//第一次回复时修改阅读时间
			Date read_date = messageBoard.getRead_date();
			if (read_date == null) {
				//标为已读且更新阅读时间
				messageBoard.setRead_date(new Date());
				messageBoard.setReadStatus(1);
				this.messageBoardService.update(messageBoard);
			}
			
			//工厂列表
			List stores = this.storeService.query(
					"select obj from Store obj where 1=1", null,
					-1, -1);
			mv.addObject("stores", stores);
			mv.addObject("obj", messageBoard);
			mv.addObject("currentPage", currentPage);
			mv.addObject("edit", Boolean.valueOf(true));
		}
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "留言保存", value = "/admin/messageboardmanage_save.htm*", rtype = "admin", rname = "留言管理", rcode = "admin_messageboard_set", rgroup = "留言")
	@RequestMapping({ "/admin/messageboardmanage_save.htm" })
	public ModelAndView messageboardmanage_save(HttpServletRequest request,
			HttpServletResponse response, String id, String receive_user_id,
			String currentPage, String cmd, String list_url, String add_url)
			throws Exception {
		WebForm wf = new WebForm();
		MessageBoard messageBoard = null;
		//依据主键操作实体
		if (id.equals("")) {
			messageBoard = (MessageBoard) wf.toPo(request, MessageBoard.class);
			//发送日期
			messageBoard.setSend_date(new Date());
			messageBoard.setAddTime(new Date());
		} else {
			MessageBoard obj = this.messageBoardService.getObjById(Long.valueOf(Long.parseLong(id)));
			messageBoard = (MessageBoard) wf.toPo(request, obj);
		}
		//设置接收人user
		User receive_user = this.userService.getObjById(CommUtil.null2Long(receive_user_id));
		messageBoard.setReceive_user(receive_user);
		ModelAndView mv = new JModelAndView("admin/blue/success.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		//依据主键判断操作类型
		if (id.equals(""))
		{
			//设置留言编号
			messageBoard.setMessage_code("MB"+IdUtil.getLongId());
			//后台添加的留言，发送人默认为管理员
			User send_user = this.userService.getObjById(CommUtil.null2Long(1));
			messageBoard.setUser(send_user);
			this.messageBoardService.save(messageBoard);
			mv.addObject("op_title", "发送留言成功");
		}
		else{
			//更新最新回复时间
			messageBoard.setReplyStatus(1);
			messageBoard.setReply_date(new Date());
			this.messageBoardService.update(messageBoard);
			mv.addObject("op_title", "回复留言成功");
		}
		
		mv.addObject("list_url", list_url);
		if (add_url != null) {
			mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
		}
		return mv;
	}
	
	@SecurityMapping(display = false, rsequence = 0, title = "留言删除", value = "/admin/messageboardmanage_del.htm*", rtype = "admin", rname = "留言管理", rcode = "admin_messageboardmanage_set", rgroup = "留言")
	@RequestMapping({ "/admin/messageboardmanage_del.htm" })
	public String messageboardmanage_del(HttpServletRequest request, String mulitId)
			throws Exception {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				this.messageBoardService.delete(CommUtil.null2Long(id));
//				send_site_msg(request, "msg_toseller_goods_delete_by_admin_notify", store);
			}
		}
		return "redirect:messageboardmanage_list.htm";
	}

}
