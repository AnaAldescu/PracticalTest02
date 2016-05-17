package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class CommunicationThread extends Thread {
	private ServerThread serverThread;
	private Socket socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }
	
	@Override
    public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter printWriter = Utilities.getWriter(socket);
				
				if (bufferedReader != null && printWriter != null) {
					String word = bufferedReader.readLine();
					
					HashMap<String, String> data;
					String definition = null;
					
					if (word != null && !word.isEmpty()) {
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet(Constants.WEB_SERVICE_ADDRESS + "?word=" + word);
						/*HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						
						params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, word));
						UrlEncodedFormEntity urlEncodedFormEntity = 
												new UrlEncodedFormEntity(params, HTTP.UTF_8);
                        httpPost.setEntity(urlEncodedFormEntity);
                        */
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        String pageSourceCode = httpClient.execute(httpGet, responseHandler);
                        
                        Log.d(Constants.TAG, pageSourceCode);
                        
                        if (pageSourceCode != null) {
                        	Document document = Jsoup.parse(pageSourceCode);
                            Element element = document.child(0);
                            Elements wordDef = element.getElementsByTag(Constants.DEFINITION_TAG);
                            
                            definition = wordDef.first().data();
                            //serverThread.setData(word, definition);
						}
						
						if (word != null) {
                            printWriter.print(pageSourceCode);
                            printWriter.flush();
						}
					}
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
