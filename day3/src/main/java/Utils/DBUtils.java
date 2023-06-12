package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private static Connection cn;
	
	public static Connection openConnection() throws SQLException {
		String dbURL = "jdbc:mysql://localhost:3306/java?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
		cn = DriverManager.getConnection(dbURL,"root","root123");
		System.out.println("DB is connected");
		return cn;
	}
	
	
	public static void closeConnection() throws SQLException {
		if(cn!=null) {
			cn.close();
			System.out.println("DB is closed");
		}
		
	}
}
