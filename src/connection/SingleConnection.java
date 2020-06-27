package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe Responsável por fazer conexão com o Banco de dados
 * 
 * @author Caio
 *
 */

public class SingleConnection {

	private static String banco = "jdbc:postgresql://localhost:5432/aula1-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String pass = "admin";
	private static Connection connection = null;

	static {
		conectar();
	}

	public SingleConnection() {
		conectar();
	}

	public static void conectar() {
		try {

			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, pass);
				connection.setAutoCommit(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao conectar no banco de dados");
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
