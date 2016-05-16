package com.example.practicaltest02;

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
	
	private String city;
	private String informationType;
	
    private TextView weatherForecastTextView;
    
    public ClientThread(String address, int port, String city, String informationType,
    					TextView weatherForecastTextView) {
    	this.address = address;
        this.port = port;
        this.city = city;
        this.informationType = informationType;
        this.weatherForecastTextView = weatherForecastTextView;
    }
    
    @Override
    public void run() {
    	try {
			socket = new Socket(address, port);
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            
            if (bufferedReader != null && printWriter != null) {
            	printWriter.println(city);
            	printWriter.flush();
            	printWriter.println(informationType);
            	printWriter.flush();
            	
            	String weatherInfo;
            	
            	while ((weatherInfo = bufferedReader.readLine()) != null) {
            		final String finalWeatherInfo = weatherInfo;
            		weatherForecastTextView.post(new Runnable() {
						
						@Override
						public void run() {
							weatherForecastTextView.append(finalWeatherInfo + "\n");
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
