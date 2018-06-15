package main;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class SuperPongTest {
	
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	
	SuperPongTest() throws IOException {
		socket = new Socket("localhost", 9090);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
	}
	
	@Test
	public void theServerShouldReturnTheGoodResponse() throws IOException {
		out.println("{\"name\":\"CONNECT\",\"data\":{\"@type\":\"Login\",\"username\":\"james\",\"password\":\"asdf\"}}");
		out.flush();
		assertEquals("{\"name\":\"CONNECT\",\"data\":{\"@type\":\"LoginConfirmation\",\"isConnected\":true}}", in.readLine());
		socket.close();
		
	}
}