package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pojo.Candidate;
import pojo.User;


public interface CandidateDao {
	ArrayList<Candidate> getAllCandidate() throws SQLException;
	String incCandidateVote(int candidateId) throws SQLException;
	ArrayList<User> displayDataToAdmin() throws SQLException;
	Map<String,Integer> analysis() throws SQLException;
	Map<String,Integer> result() throws SQLException;
	
}
