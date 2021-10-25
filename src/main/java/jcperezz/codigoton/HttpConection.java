package jcperezz.codigoton;
import java.io.Closeable;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * The Class HttpConection.
 */
public class HttpConection implements Closeable {

	/** The Constant DECRYPT_ENDPOINT. */
	private static final String DECRYPT_ENDPOINT = Config.getString("HttpConection.DecryptEndpoint");
	
	/** The httpclient. */
	private CloseableHttpClient httpclient;

	/**
	 * Instantiates a new http conection.
	 */
	public HttpConection() {
		super();
		httpclient = HttpClients.createDefault();
	}

	/**
	 * Close.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void close() throws IOException {
		if (httpclient != null) {
			httpclient.close();
		}
	}

	/**
	 * Decrypt.
	 *
	 * @param secret the secret
	 * @return the string
	 * @throws MyAppException the my app exception
	 */
	public String decrypt(String secret) throws MyAppException {

		String result = null;
		HttpGet httpGet = new HttpGet(DECRYPT_ENDPOINT + secret);

		try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
			
			if(response1.getCode() != 200) {
				throw new MyAppException(response1.getReasonPhrase());
			}
			HttpEntity entity1 = response1.getEntity();
			try {
				result = EntityUtils.toString(entity1);
				result = result.replace("\"", "");
			} catch (ParseException e) {
				throw new MyAppException("Error al interpretar la respuesta del servicio de encripción", e);
			} 
			EntityUtils.consume(entity1);
			
		} catch (IOException e) {
			throw new MyAppException("Error al conectarse al servicio de encripción", e);

		} 

		return result;

	}

}
