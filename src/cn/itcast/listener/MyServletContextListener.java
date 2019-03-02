package cn.itcast.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import cn.itcast.vo.User;

/**
 * 监听ServletContext对象的创建和销毁
 * @author 45969
 *
 */
public class MyServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	//ServletContext对象一创建 initial方法就会执行
	//ServletContextEvent事件对象，监听对象是ServletContext对象（事件源）
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Map<User,HttpSession> userMap=new HashMap<User,HttpSession>();
		sce.getServletContext().setAttribute("userMap", userMap);;
	}

}
