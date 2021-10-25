package jcperezz.codigoton;
import java.util.List;
import java.util.Map;

/**
 * The Class Maitre.
 */
public class Maitre {

	/** The Constant QUERY. */
	private static final String QUERY = 
			"select\r\n" 
			+ "	c.id,\r\n" 
			+ "	c.code,\r\n" 
			+ "	c.male,\r\n"
			+ "	c.type,\r\n" 
			+ "	c.location,\r\n" 
			+ "	c.company,\r\n" 
			+ "	c.encrypt,\r\n" 
			+ "	a.total_balance\r\n"
			+ "from\r\n" 
			+ "	client c\r\n" 
			+ "join (\r\n" 
			+ "	select\r\n"
			+ "		tmp.client_id, sum(tmp.balance) total_balance\r\n" 
			+ "	from\r\n" + "		account tmp\r\n"
			+ "	group by\r\n" + "		tmp.client_id ) a on\r\n" 
			+ "	(c.id = a.client_id)\r\n"
			+ "order by a.total_balance desc, c.code ASC";
	
	/** The http conection. */
	private HttpConection httpConection = null;

	/**
	 * Instantiates a new maitre.
	 */
	public Maitre() {
		super();
		httpConection = new HttpConection();
	}

	/**
	 * Manage.
	 *
	 * @param tables the tables
	 * @throws MyAppException the my app exception
	 */
	public void manage(Map<String, Map<String, String>> tables) throws MyAppException {

		try (DbConnection con = DbConnection.getInstance()) {
			con.open();
			
			List<Map<String, String>> resultList = con.getResultList(QUERY);
			
			for (int i = 0; i < tables.size(); i++) {
				if (i == 0) {
					applyFilter(tables, resultList, "<General>");
				} else {
					applyFilter(tables, resultList, "<Mesa " + i + ">");
				}
			}

		} 
	}

	/**
	 * Apply filter.
	 *
	 * @param tables the tables
	 * @param resultList the result list
	 * @param tableName the table name
	 * @throws MyAppException the my app exception
	 */
	private void applyFilter(Map<String, Map<String, String>> tables, List<Map<String, String>> resultList,
			String tableName) throws MyAppException {
		
		Map<String, String> filters = tables.get(tableName);
		
		System.out.println(tableName);
		List<Map<String, String>> result = Filter.search(resultList, filters);

		if (result.size() < 4) {
			System.out.println("CANCELADA");
		} else {
			clientsToString(result);
		}
		
	}
	
	/**
	 * Clients to string.
	 *
	 * @param result the result
	 * @throws MyAppException the my app exception
	 */
	private void clientsToString(List<Map<String, String>> result) throws MyAppException {
		String separator = "";
		for (Map<String, String> map : result) {
			
			String code = map.get("code");
			
			if(map.get("encrypt").equals("true")) {
				code = httpConection.decrypt(code);
			}
			System.out.print(separator);
			System.out.print(code);
			separator = ",";
		}
		
		System.out.println();
	}
	

	

}
