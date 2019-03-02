package cn.itcast.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import cn.itcast.vo.User;

/**
 * ����ServletContext����Ĵ���������
 * @author 45969
 *
 */
public class MyServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	//ServletContext����һ���� initial�����ͻ�ִ��
	//ServletContextEvent�¼����󣬼���������ServletContext�����¼�Դ��
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Map<User,HttpSession> userMap=new HashMap<User,HttpSession>();
		sce.getServletContext().setAttribute("userMap", userMap);;
	}

}
