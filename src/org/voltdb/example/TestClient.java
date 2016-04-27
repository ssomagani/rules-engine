/**
 * 
 */
package org.voltdb.example;

import java.io.IOException;

import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

/**
 * @author seetasomagani
 *
 */
public class TestClient {

	private static Client client;

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		client = ClientFactory.createClient(config);

		try {
			client.createConnection("localhost");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
