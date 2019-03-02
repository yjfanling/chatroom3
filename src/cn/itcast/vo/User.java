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
	//��JavaBean������session��
	//session.setAttribute();
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		//ͨ���¼��������¼�Դ����
		
		HttpSession session=event.getSession();
		//��ServletContext�л����Ա�б��map����
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)session.getServletContext().getAttribute("userMap");
		//���û��Ͷ�Ӧ��session���뵽map������
		System.out.println(this.getUsername()+"�����ˡ�����");
		userMap.put(this, session);
	}
	//Java������session�����
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println(this.getUsername()+"�˳��ˡ�����");
		HttpSession session=event.getSession();
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)session.getServletContext().getAttribute("userMap");
		userMap.remove(this);
	}
}
