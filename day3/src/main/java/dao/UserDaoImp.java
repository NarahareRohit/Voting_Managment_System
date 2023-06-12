package dao;

import static Utils.DBUtils.closeConnection;
import static Utils.DBUtils.openConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.User;

public class UserDaoImp implements UserDao{
	private Connection cn;
	private PreparedStatement pst;
	private PreparedStatement pst1,pst2;
	
	public UserDaoImp() throws SQLException {
		cn = openConnection();
		pst = cn.prepareStatement("select * from users where email=? and password=?");
		pst1 = cn.prepareStatement("insert into users values (?,?,?,?,?,?,?,?)");
		pst2 = cn.prepareStatement("update users set status=1 where id=?");
		System.out.println("DAO is done");
	}

	@Override
	public User authentication(String email, String pass) throws SQLException {
		pst.setString(1, email);
		pst.setString(2, pass);
		try(ResultSet rst = pst.executeQuery()){
			if(rst.next()) {
				return new User(rst.getInt(1), rst.getString(2), rst.getString(3), email, pass, 
						rst.getDate(6), rst.getBoolean(7), rst.getString(8));
			}
		}
		return null;
	}
	
	@Override
	public boolean registerUser(User u) throws SQLException {
		pst1.setInt(1, u.getId());
		pst1.setString(2,u.getFirstName());
		pst1.setString(3, u.getLastName());
		pst1.setString(4, u.getEmail());
		pst1.setString(5, u.getPassword());
		pst1.setDate(6, u.getDob());
		pst1.setBoolean(7,u.isVotingStatus());
		pst1.setString(8, u.getRole());
		int s = pst1.executeUpdate();
		if(s==1)
			return true;
		return false;
	}
	
	
	
	public void cleanUp() throws SQLException {
		if(pst!=null) {
			pst.close();
		}
		closeConnection();
		System.out.println("dao is closed");
		
	}

	@Override
	public String updateVoting(int id) throws SQLException {
		pst2.setInt(1, id);
		int count = pst2.executeUpdate();
		if(count==1)
			return "Voting is done";
		return "Voting is not updated";
	}

	


	

}
