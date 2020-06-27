package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class LoginDao {
	
	private Connection connection;
	
	public LoginDao() {
		connection = SingleConnection.getConnection();
	}

	public boolean validarLogin(String login, String senha) throws Exception{
		
		String sql = "SELECT * FROM usuarios WHERE login = '"+ login +"' and senha = '"+ senha +"'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) 
		{
			return true; // Significa que achou usuario
		} 
		else 
		{
			return false;
		}
	}
}
