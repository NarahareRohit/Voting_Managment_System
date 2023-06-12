package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;

import pojo.*;
import static Utils.DBUtils.*;

//CandidateDao
public class CandidateImpl implements CandidateDao {
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3,pst4,pst5;

	public CandidateImpl() throws SQLException {
		cn = openConnection();
		pst1 = cn.prepareStatement("select * from candidates");
		pst2 = cn.prepareStatement("update candidates set votes=votes+1 where id=?");
		pst3 = cn.prepareStatement("select * from users");
		pst4 = cn.prepareStatement("select party,sum(votes) from candidates group by party; ");
		pst5 = cn.prepareStatement("select name,votes from candidates order by votes desc limit 3;");
	}

	@Override
	public ArrayList<Candidate> getAllCandidate() throws SQLException {
		ArrayList<Candidate> candidate = new ArrayList<>();
//		pst1.executeQuery();
		try (ResultSet rst = pst1.executeQuery()) {
			while (rst.next()) {
				System.out.println(rst.getInt(1) + rst.getString(2) + rst.getString(3) + rst.getInt(4));
				candidate.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
			}
		}
		System.out.println(candidate.toString());
		return candidate;
	}

	@Override
	public String incCandidateVote(int candidateId) throws SQLException {
		pst2.setInt(1, candidateId);
		int count = pst2.executeUpdate();
		if (count == 1)
			return "Voting is Done";
		return "Voting is not Done";
	}

	@Override
	public ArrayList<User> displayDataToAdmin() throws SQLException {
		ArrayList<User> user = new ArrayList<>();
		try (ResultSet rst = pst3.executeQuery()) {
			while (rst.next()) {
				user.add(new User(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
						rst.getDate(6), rst.getBoolean(7), rst.getString(8)));
			}
		}
		return user;
	}
	
	@Override
	public Map<String,Integer> analysis() throws SQLException {
		Map<String,Integer> analysisMap = new HashMap<String,Integer>();
		try(ResultSet rst = pst4.executeQuery()){
			while(rst.next()) {
				analysisMap.put(rst.getString(1),rst.getInt(2));
			}
		}
		return analysisMap;
	}
	
	
	@Override
	public Map<String, Integer> result() throws SQLException {
		Map<String, Integer> allMap = new HashMap<String, Integer>();
		try(ResultSet rst = pst5.executeQuery()){
			while(rst.next()) {
				allMap.put(rst.getString(1),rst.getInt(2));
			}
		}
		return allMap;
	}
	

	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		closeConnection();
		System.out.println("Candidate Dao is closed");
	}

	
	

}
