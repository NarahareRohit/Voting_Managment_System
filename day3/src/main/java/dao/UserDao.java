package dao;

import java.sql.Date;
import java.sql.SQLException;
import pojo.User;

public interface UserDao {
	User authentication(String email, String pass) throws SQLException;
	boolean registerUser(User u) throws SQLException;
	String updateVoting(int id) throws SQLException;
}
