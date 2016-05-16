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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.media.tv.TvInputService.HardwareSession;

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
			/*try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter printWriter = Utilities.getWriter(socket);
				
				if (bufferedReader != null && printWriter != null) {
					String city = bufferedReader.readLine();
					String informationType = bufferedReader.readLine();
					
					HashMap<String, WeatherForecastInformation> data = serverThread.getData();
					WeatherForecastInformation weatherInfo = null;
					
					if (city != null && !city.isEmpty()
						&& informationType != null && !informationType.isEmpty()) {
						if (data.containsKey(city)) {
							weatherInfo = data.get(city);
						} else {
							HttpClient httpClient = new DefaultHttpClient();
							HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							
							params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
							UrlEncodedFormEntity urlEncodedFormEntity = 
													new UrlEncodedFormEntity(params, HTTP.UTF_8);
                            httpPost.setEntity(urlEncodedFormEntity);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String pageSourceCode = httpClient.execute(httpPost, responseHandler);
                            
                            if (pageSourceCode != null) {
                            	Document document = Jsoup.parse(pageSourceCode);
                                Element element = document.child(0);
                                Elements scripts = element.getElementsByTag(Constants.SCRIPT_TAG);
                                
                                for (Element script : scripts) {
                                	String scriptData = script.data();
                                	
                                	if (scriptData.contains(Constants.SEARCH_KEY)) {
                                		scriptData = scriptData.substring(scriptData.
                                						indexOf(Constants.SEARCH_KEY)
                                						+ Constants.SEARCH_KEY.length());
                                		
	                                	JSONObject content = new JSONObject(scriptData);
	                                	JSONObject info = content.getJSONObject(Constants.
	                                											CURRENT_OBSERVATION);
	                                	String cond = info.getString(Constants.CONDITION);
	                                	String temp = info.getString(Constants.TEMPERATURE);
	                                	String hum = info.getString(Constants.HUMIDITY);
	                                	String wind = info.getString(Constants.WIND_SPEED);
	                                	String pres = info.getString(Constants.PRESSURE);
	                                	
	                                	weatherInfo = new WeatherForecastInformation(temp, wind, cond,
	                                												 pres, hum);
	                                	
	                                	serverThread.setData(city, weatherInfo);
	                                	break;
                                	}
                                }
                            }
						}
						
						if (weatherInfo != null) {
                            String result = null;
                            if (Constants.ALL.equals(informationType)) {
                                result = weatherInfo.toString();
                            } else if (Constants.TEMPERATURE.equals(informationType)) {
                                result = weatherInfo.getTemperature();
                            } else if (Constants.WIND_SPEED.equals(informationType)) {
                                result = weatherInfo.getWindSpeed();
                            } else if (Constants.CONDITION.equals(informationType)) {
                                result = weatherInfo.getCondition();
                            } else if (Constants.HUMIDITY.equals(informationType)) {
                                result = weatherInfo.getHumidity();
                            } else if (Constants.PRESSURE.equals(informationType)) {
                                result = weatherInfo.getPressure();
                            } else {
                                result = "Wrong information type (all / temperature / wind_speed / condition / humidity / pressure)!";
                            }
                            
                            printWriter.print(result);
                            printWriter.flush();
						}
					}
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

}
