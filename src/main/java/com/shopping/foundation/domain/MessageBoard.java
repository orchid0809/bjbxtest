package com.shopping.foundation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.shopping.core.domain.IdEntity;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "shopping_message_board")
public class MessageBoard extends IdEntity{
	// 留言用户
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	// 接收用户
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="receive_user_id")
	private User receive_user;

	// 回复状态(0:未回复，1：已回复)
	@Column(columnDefinition = "int default 0")
	private int replyStatus;
	
//	// 发送状态(0:工厂发送，1：管理员发送)
//	private int sendStatus;
	
	// 阅读状态(0:未读，1：已读)
	@Column(columnDefinition = "int default 0")
	private int readStatus;

	// 留言编号
	private String message_code;

	// 错误信息
	@Column(columnDefinition = "int default 0")
	private String errorMessage;
	// 留言主题
	private String title;

	// 留言正文
	@Column(columnDefinition="LongText")
	private String text;

	// 回复正文
	@Column(columnDefinition="LongText")
	private String reply;
	
	// 留言发送日期
	private Date send_date;

	// 留言阅读日期
	private Date read_date;

	// 留言回复日期
	private Date reply_date;

	public User getReceive_user() {
		return receive_user;
	}

	public void setReceive_user(User receive_user) {
		this.receive_user = receive_user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage_code() {
		return message_code;
	}

	public void setMessage_code(String message_code) {
		this.message_code = message_code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(int replyStatus) {
		this.replyStatus = replyStatus;
	}

	public int getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public Date getRead_date() {
		return read_date;
	}

	public void setRead_date(Date read_date) {
		this.read_date = read_date;
	}

	public Date getReply_date() {
		return reply_date;
	}

	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	

}
