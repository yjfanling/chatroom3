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
	 * 踢人的功能
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException 
	 */
	public String kick(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		//接收参数
		int id=Integer.parseInt(req.getParameter("id"));
		//踢人：从userMap中将用户对应的session销毁
		Map<User,HttpSession> userMap=(Map<User,HttpSession>)getServletContext().getAttribute("userMap");
		User user=new User();
		user.setid(id);
		HttpSession session=userMap.get(user);
		session.invalidate();
		//重定向到登陆页面
		res.sendRedirect(req.getContextPath()+"/main.jsp");
		return null;
	}
	/**
	 * 登录功能
	 * @param req
	 * @param res
	 * @return
	 */
	public String login(HttpServletRequest req,HttpServletResponse res){
		//接收数据
		Map<String,String[]> map=req.getParameterMap();
		User user=new User();
		//封装数据
		try{
			BeanUtils.populate(user,map);//自动设置user的id和用户名等等
			//调用Service层处理数据
			UserService us=new UserService();
			User existUser=us.login(user);
			if(existUser==null)
			{
				//用户登陆失败
				req.setAttribute("msg", "用户名或密码错误");
				return "/index.jsp";
			}else
			{
				//用户登陆成功
				//第二个用户登录后要将之前的session销毁
				req.getSession().invalidate();
			
				//要判断用户是否已经在Map集合中，若存在，则销毁其session
				Map<User,HttpSession> userMap=(Map<User,HttpSession>)getServletContext().getAttribute("userMap");//得到ServletContext中存在的集合
				if(userMap.containsKey(existUser))
				{
					HttpSession session=userMap.get(existUser);
					session.invalidate();
				}
				//使用HttpSessionBandingListener（作用在JavaBean上的监听器）
				req.getSession().setAttribute("existUser", existUser);
				ServletContext application=getServletContext();//全局共享的域，大家都能看得到
				
				String sourceMessage="";
				if(null!=application.getAttribute("message")){
					sourceMessage=application.getAttribute("message").toString();
				}
				sourceMessage+="系统公告：<font color='gray'>"+existUser.getUsername()+"走进了聊天室！</font><br>";
				application.setAttribute("message",sourceMessage);
				res.sendRedirect(req.getContextPath()+"/main.jsp");//完成重定向
				return null;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取消息的方法
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
	 * 发送消息的方法
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
		//获得ServletContext对象
		ServletContext application=getServletContext();
		//从ServletContext对象中获取消息
		String sourceMessage=(String) application.getAttribute("message");
		//拼接发言内容 xx对yy说zzz
		sourceMessage+="<font color='blue'><strong>"+from+"</strong></font color='#CC0000'>"+face+"</font>对<font color='green'>["+to+"]</font>说："+"<font color='"+color+"'>"+content+"</font>("+sendTime+")<br>";
		application.setAttribute("message", sourceMessage);
		return getMessage(req, res);
	}
	/**
	 * 退出聊天室功能
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
