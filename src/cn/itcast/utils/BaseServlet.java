package cn.itcast.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	protected void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		
		//http://localhost:8080/chatroom3/XXX?method=login
		String methodName=req.getParameter("method");
		if(methodName==null||methodName.isEmpty())
			methodName="execute";
		Class c=this.getClass();
		try{
			//通过方法名称获得方法的反射对象
			Method m=c.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String result=(String)m.invoke(this, req,res);
			if(result!=null&&!result.isEmpty())
				req.getRequestDispatcher(result).forward(req, res);
		}catch(Exception ex){
			throw new ServletException(ex);
			
		}
	}
}
