package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.widget.TextView;

public class ClientThread extends Thread {
	private Socket socket;
	
	private String address;
	private int port;
	
	private String word;
    private TextView wordDefinitionTextView;
    
    public ClientThread(String address, int port, String word, TextView wordDefinitionTextView) {
    	this.address = address;
        this.port = port;
        this.word = word;
        this.wordDefinitionTextView = wordDefinitionTextView;
    }
    
    @Override
    public void run() {
    	try {
			socket = new Socket(address, port);
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            
            if (bufferedReader != null && printWriter != null) {
            	printWriter.println(word);
            	printWriter.flush();
            	
            	String wordDefinition;
            	
            	while ((wordDefinition = bufferedReader.readLine()) != null) {
            		final String finalwordDefinition = wordDefinition;
            		wordDefinitionTextView.post(new Runnable() {
						
						@Override
						public void run() {
							wordDefinitionTextView.append(finalwordDefinition + "\n");
						}
					});
            	}
            	
            	socket.close();
            }
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
