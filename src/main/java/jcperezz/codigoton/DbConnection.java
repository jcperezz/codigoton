package jcperezz.codigoton;

/*
 * 
 */
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Clase de conexión a la base de datos, la configuración de la conexión se
 * encuentra en el archivo db-connection.properties
 */
public class DbConnection implements Closeable {


	/** The instance. */
	private static DbConnection instance;

	/** The con. */
	private Connection con;

	/** The string conection. */
	private String stringConection;

	/**
	 * Instantiates a new db connection.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 */
	private DbConnection() throws ClassNotFoundException {
		super();
		Class.forName("com.mysql.jdbc.Driver"); //$NON-NLS-1$

		String server = Config.getString("DbConnection.server"); //$NON-NLS-1$
		String port = Config.getString("DbConnection.port"); //$NON-NLS-1$
		String dbName = Config.getString("DbConnection.dbName"); //$NON-NLS-1$
		String user = Config.getString("DbConnection.user"); //$NON-NLS-1$
		String secret = Config.getString("DbConnection.password"); //$NON-NLS-1$

		stringConection = "jdbc:mysql://" + server + ":" + port + "/" + dbName + "?user=" + user + "&password=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				+ secret;

	}

	/**
	 * Gets the single instance of DbConnection.
	 *
	 * @return single instance of DbConnection
	 * @throws MyAppException the my app exception
	 */
	public static DbConnection getInstance() throws MyAppException {
		if (instance == null) {
			try {
				instance = new DbConnection();
			} catch (ClassNotFoundException e) {
				throw new MyAppException(
						"Error al conectarse a la BD, verifique que el mysql driver esté en el classpath", e); //$NON-NLS-1$
			}
		}

		return instance;
	}

	/**
	 * Open.
	 *
	 * @throws MyAppException the my app exception
	 */
	public void open() throws MyAppException {
		try {
			con = DriverManager.getConnection(stringConection);
		} catch (SQLException e) {
			throw new MyAppException("Error al conectarse a la BD, verifique las credenciales de acceso", e); //$NON-NLS-1$
		}
	}

	/**
	 * Close.
	 *
	 * @throws MyAppException the my app exception
	 */
	public void close() throws MyAppException {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			throw new MyAppException("Error al cerrar la conexion de la BD", e); //$NON-NLS-1$

		}
	}

	/**
	 * Gets the result list.
	 *
	 * @param query      the query
	 * @param parameters the parameters
	 * @return the result list
	 * @throws MyAppException the my app exception
	 */
	public List<Map<String, String>> getResultList(String query, String... parameters) throws MyAppException {

		try (PreparedStatement statement = con.prepareStatement(query)) {

			for (int i = 0; i < parameters.length; i++) {
				statement.setString(i + 1, parameters[i]);
			}

			ResultSet rs = statement.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			List<Map<String, String>> data = new LinkedList<>();

			while (rs.next()) {
				Map<String, String> rowData = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(rsmd.getColumnName(i), String.valueOf(rs.getObject(i)));
				}

				data.add(rowData);
			}
			return data;
		} catch (SQLException e) {
			throw new MyAppException("Error al ejecutar la consulta SQL", e); //$NON-NLS-1$
		}
	}


}
