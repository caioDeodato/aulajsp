package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Produtos;
import connection.SingleConnection;

public class ProdutoDao {

	private Connection connection;

	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvarProduto(Produtos produto) {

		try {
			String sql = "INSERT INTO produtos(nome, quantidade, valor) values ( ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNomeProduto());
			statement.setString(2, produto.getQuantidade());
			statement.setString(3, produto.getValor());
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

	public List<Produtos> listar() throws Exception {

		String sql = "select * from produtos order by id";
		List<Produtos> listar = new ArrayList<Produtos>();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Produtos produto = new Produtos();
			produto.setId(resultSet.getLong("id"));
			produto.setNomeProduto(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getString("quantidade"));
			produto.setValor(resultSet.getString("valor"));

			listar.add(produto);
		}

		return listar;

	}

	public void deletarProduto(Long id) {
		try {
			String sql = "DELETE FROM produtos WHERE id= " + id;
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

	public Produtos consultar(Long id) throws Exception {
		String sql = "SELECT * FROM produtos WHERE id= " + id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Produtos produto = new Produtos();
			produto.setId(resultSet.getLong("id"));
			produto.setNomeProduto(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getString("quantidade"));
			produto.setValor(resultSet.getString("valor"));
			return produto;
		}

		return null;

	}

	public void atualizar(Produtos produto) {
		try {
			String sql = "UPDATE produtos SET nome= ?, 	quantidade= ?, valor= ? WHERE id= " + produto.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNomeProduto());
			statement.setString(2, produto.getQuantidade());
			statement.setString(3, produto.getValor());
			statement.executeUpdate();
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

	public boolean validarProduto(String nomeProduto) throws Exception {

		String sql = "SELECT count(1) as qtd FROM produtos WHERE nome= '" + nomeProduto+ "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0; // retorna true
		}

		return false;
	}

}
