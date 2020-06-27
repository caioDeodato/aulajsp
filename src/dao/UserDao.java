package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class UserDao {

	private Connection connection;

	public UserDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvarUsuario(BeanCursoJsp usuario) {

		try {
			String sql = "INSERT INTO usuarios(login, senha, nome, cep, rua, bairro, estado, cidade, ibge, fotobase64, contenttype) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getCep());
			statement.setString(5, usuario.getRua());
			statement.setString(6, usuario.getBairro());
			statement.setString(7, usuario.getEstado());
			statement.setString(8, usuario.getCidade());
			statement.setString(9, usuario.getIbge());
			statement.setString(10, usuario.getFotoBase64());
			statement.setString(11, usuario.getContentType());
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

	public List<BeanCursoJsp> listar() throws Exception {

		String sql = "select * from usuarios order by id";
		List<BeanCursoJsp> listar = new ArrayList<BeanCursoJsp>();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setFotoBase64(resultSet.getString("fotobase64"));
			beanCursoJsp.setContentType(resultSet.getString("contenttype"));

			listar.add(beanCursoJsp);
		}

		return listar;

	}

	public void deletarUsuario(Long id) {
		try {
			String sql = "DELETE FROM usuarios WHERE id= " + id;
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

	public BeanCursoJsp consultar(Long id) throws Exception {
		String sql = "SELECT * FROM usuarios WHERE id= " +id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setFotoBase64(resultSet.getString("fotobase64"));
			beanCursoJsp.setContentType(resultSet.getString("contenttype"));
			return beanCursoJsp;
		}

		return null;

	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			String sql = "UPDATE usuarios SET login= ?, senha= ?, nome= ?, cep= ?, rua= ?, bairro= ?, cidade= ?, estado= ?, ibge= ? WHERE id= " + usuario.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getCep());
			preparedStatement.setString(5, usuario.getRua());
			preparedStatement.setString(6, usuario.getBairro());
			preparedStatement.setString(7, usuario.getCidade());
			preparedStatement.setString(8, usuario.getEstado());
			preparedStatement.setString(9, usuario.getIbge());
			preparedStatement.executeUpdate();
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

	public boolean validarLogin(String login) throws Exception {

		String sql = "SELECT count(1) as qtd FROM usuarios WHERE login= '" + login + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0; //retorna true se encontrar nenhum usuario com o mesmo login
		}

		return false;
	}

}
