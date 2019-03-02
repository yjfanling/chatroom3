package cn.itcast.action;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.service.UserService;
import cn.itcast.utils.BaseServlet;
import cn.itcast.vo.User;

public class UserServlet extends BaseServlet {
	/**
	 * ���˵Ĺ���
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException 
	 */
	public String kick(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		//���ղ���
		int id=Integer.parseInt(req.getParameter("id"));
		//���ˣ���userMap�н��û���Ӧ��session����
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)getServletContext().getAttribute("userMap");
		User user=new User();
		user.setid(id);
		HttpSession session=userMap.get(user);
		session.invalidate();
		//�ض��򵽵�½ҳ��
		res.sendRedirect(req.getContextPath()+"/main.jsp");
		return null;
	}
	/**
	 * ��¼����
	 * @param req
	 * @param res
	 * @return
	 */
	public String login(HttpServletRequest req,HttpServletResponse res){
		//��������
		Map<String,String[]> map=req.getParameterMap();
		User user=new User();
		//��װ����
		try{
			BeanUtils.populate(user,map);//�Զ�����user��id���û����ȵ�
			//����Service�㴦������
			UserService us=new UserService();
			User existUser=us.login(user);
			if(existUser==null)
			{
				//�û���½ʧ��
				req.setAttribute("msg", "�û������������");
				return "/index.jsp";
			}else
			{
				//�û���½�ɹ�
				//�ڶ����û���¼��Ҫ��֮ǰ��session����
				req.getSession().invalidate();
			
				//Ҫ�ж��û��Ƿ��Ѿ���Map�����У������ڣ���������session
				Map<User,HttpSession> userMap=(Map<User,HttpSession>)getServletContext().getAttribute("userMap");//�õ�ServletContext�д��ڵļ���
				if(userMap.containsKey(existUser))
				{
					HttpSession session=userMap.get(existUser);
					session.invalidate();
				}
				//ʹ��HttpSessionBandingListener��������JavaBean�ϵļ�������
				req.getSession().setAttribute("existUser", existUser);
				ServletContext application=getServletContext();//ȫ�ֹ�����򣬴�Ҷ��ܿ��õ�
				
				String sourceMessage="";
				if(null!=application.getAttribute("message")){
					sourceMessage=application.getAttribute("message").toString();
				}
				sourceMessage+="ϵͳ���棺<font color='gray'>"+existUser.getUsername()+"�߽��������ң�</font><br>";
				application.setAttribute("message",sourceMessage);
				res.sendRedirect(req.getContextPath()+"/main.jsp");//����ض���
				return null;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ��Ϣ�ķ���
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException 
	 */
	public String getMessage(HttpServletRequest req,HttpServletResponse res) throws IOException{
		String message=(String)getServletContext().getAttribute("message");
		if(message!=null)
			res.getWriter().println(message);
		return null;
	}
	/**
	 * ������Ϣ�ķ���
	 * @param req
	 * @param res
	 * @return
	 */
	public String sendMessage(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		System.out.println("sendMessage invoke...");
		String from=req.getParameter("from");
		String to=req.getParameter("to");
		String face=req.getParameter("face");
		String color=req.getParameter("color");
		String content=req.getParameter("content");
		String sendTime=new Date().toLocaleString();
		//���ServletContext����
		ServletContext application=getServletContext();
		//��ServletContext�����л�ȡ��Ϣ
		String sourceMessage=(String) application.getAttribute("message");
		//ƴ�ӷ������� xx��yy˵zzz
		sourceMessage+="<font color='blue'><strong>"+from+"</strong></font color='#CC0000'>"+face+"</font>��<font color='green'>["+to+"]</font>˵��"+"<font color='"+color+"'>"+content+"</font>("+sendTime+")<br>";
		application.setAttribute("message", sourceMessage);
		return getMessage(req, res);
	}
	/**
	 * �˳������ҹ���
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException 
	 */
	public String exit(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		req.getSession().invalidate();
		res.sendRedirect(req.getContextPath()+"/index.jsp");
		return null;
	}
	
	public String check(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		User existUser = (User) req.getSession().getAttribute("existUser");
		if(existUser == null){
			resp.getWriter().println("1");
		}else{
			resp.getWriter().println("2");
		}
		return null;
	}
}
