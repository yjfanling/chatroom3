package cn.itcast.vo;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener{
	private int id;
	private String username;
	private String password;
	private String type;
	
	public int getid() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setid(int uid) {
		this.id = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	//将JavaBean对象与session绑定
	//session.setAttribute();
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		//通过事件对象获得事件源对象
		
		HttpSession session=event.getSession();
		//从ServletContext中获得人员列表的map集合
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)session.getServletContext().getAttribute("userMap");
		//将用户和对应的session存入到map集合中
		System.out.println(this.getUsername()+"进入了。。。");
		userMap.put(this, session);
	}
	//Java对象与session解除绑定
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println(this.getUsername()+"退出了。。。");
		HttpSession session=event.getSession();
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)session.getServletContext().getAttribute("userMap");
		userMap.remove(this);
	}
}
