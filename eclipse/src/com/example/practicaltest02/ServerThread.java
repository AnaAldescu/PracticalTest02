package com.example.practicaltest02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {
	private int port = 0;
	private ServerSocket serverSocket = null;
	
	//private HashMap<String, WeatherForecastInformation> data = null;
	
	public ServerThread(int port) {
		this.port = port;
		
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//this.data = new HashMap<String, WeatherForecastInformation>();
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setServerSocker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    /*public synchronized void setData(String city,
    								 WeatherForecastInformation weatherForecastInformation) {
        this.data.put(city, weatherForecastInformation);
    }

    public synchronized HashMap<String, WeatherForecastInformation> getData() {
        return data;
    }
    */
    
    @Override
    public void run() {
    	while (!Thread.currentThread().isInterrupted()) {
    		try {
				Socket socket = serverSocket.accept();
				
				CommunicationThread communicationThread = new CommunicationThread(this, socket);
				communicationThread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void stopThread() {
    	if (serverSocket != null) {
    		interrupt();
    		try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
