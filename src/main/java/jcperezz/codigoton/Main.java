package jcperezz.codigoton;
import java.io.FileNotFoundException;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {

		if (args == null || args.length < 1) {
			throw new Exception("El parámetro de ubicación del archivo de entrada es obligatorio");
		}

		String path = "";

		for (String string : args) {
			if (string.startsWith("-f=")) {
				path = string.replace("-f=", "");
			}
		}

		Map<String, Map<String, String>> tables = null;
		
		try {
			tables = FileReader.read(path);
		} catch (FileNotFoundException e1) {
			System.out.println("El archivo de entrada no existe");
		}

		try {
			Maitre m = new Maitre();
			m.manage(tables);
		} catch (MyAppException e) {
			e.printStackTrace();
		}

	}

}
