package jcperezz.codigoton;
import java.io.IOException;

public class MyAppException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5951297582199748286L;

	public MyAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyAppException(String message) {
		super(message);
	}
	
	

}
