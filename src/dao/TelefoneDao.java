package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefone;
import connection.SingleConnection;

public class TelefoneDao {
	
	private Connection connection;

	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvarTelefone(Telefone telefone) {

		try {
			String sql = "INSERT INTO telefones(numero, tipo, id_usuario) values ( ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getIdUsuario());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public List<Telefone> listar(Long idUser) throws Exception {

		String sql = "select * from telefones where id_usuario = " +idUser;
		List<Telefone> listar = new ArrayList<Telefone>();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Telefone telefone = new Telefone();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setIdUsuario(resultSet.getLong("id_usuario"));

			listar.add(telefone);
		}

		return listar;

	}

	public void deletarTelefone(Long id) {
		try {
			String sql = "DELETE FROM telefones WHERE id= " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
