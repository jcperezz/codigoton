package jcperezz.codigoton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader {

	private FileReader() {
		super();
	}

	public static Map<String, Map<String, String>> read(String filePath) throws FileNotFoundException {

		Map<String, Map<String, String>> tables = new HashMap<>();

		File myObj = new File(filePath);

		try (Scanner myReader = new Scanner(myObj)) {

			String table = null;

			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();

				if (line.startsWith("<")) {
					table = line;
				} else {
					String key = line.split(":")[0];
					String value = line.split(":")[1];

					if (tables.containsKey(table)) {
						
						tables.get(table).put(key, value);
						
					} else {
						
						Map<String, String> filters = new HashMap<>();
						filters.put(key, value);

						tables.put(table, filters);
					}
				}

			}

			return tables;
		}
	}

}
