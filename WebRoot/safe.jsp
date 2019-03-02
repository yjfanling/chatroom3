<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%if(null==session.getAttribute("existUser")){
	out.println("<script language='javascript'>alert('您的帐户已过期,请重新登录！');window.location='index.jsp';</script>");
	return;
} %>