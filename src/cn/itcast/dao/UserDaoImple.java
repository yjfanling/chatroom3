package cn.itcast.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.utils.JDBCUtils;
import cn.itcast.vo.User;

public class UserDaoImple implements UserDao{

	@Override
	public User login(User user) {
		//QueryRunner对象用来进行查询修改删除插入操作
		Connection conn=JDBCUtils.getConnection();
		String sql="select * from user where username=? and password=?";
		User existUser =new User();
		try{
			PreparedStatement ps =null;
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{	
				existUser.setid(rs.getInt("id"));
				existUser.setUsername(rs.getString("username"));
				existUser.setPassword(rs.getString("password"));
				existUser.setType(rs.getString("type"));
			}
			else
				existUser=null;
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException("用户登陆失败！");
		}
		return existUser;
	}

}
