package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class PracticalTest02MainActivity extends Activity {
	// Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;
    
    // Client widgets
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText wordEditText = null;
    private Button getWordDefinitionButton = null;
    private TextView wordDefinitionTextView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;
    
    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			String serverPort = serverPortEditText.getText().toString();
			
			if (serverPort != null && !serverPort.isEmpty()) {
				serverThread = new ServerThread(Integer.parseInt(serverPort));
				
				if (serverThread.getServerSocket() != null) {
					serverThread.start();
				}
			}
		}
    }
    
    private GetWordDefinitionButtonClickListener getWordDefinitionButtonClickListener = 
			new GetWordDefinitionButtonClickListener();
			private class GetWordDefinitionButtonClickListener implements Button.OnClickListener {
			
				@Override
				public void onClick(View v) {
					String clientAddress = clientAddressEditText.getText().toString();
					String clientPort    = clientPortEditText.getText().toString();
					
					if (clientAddress != null && !clientAddress.isEmpty()
							&& clientPort != null && !clientPort.isEmpty()) {
						if (serverThread != null && serverThread.isAlive()) {
						String word = wordEditText.getText().toString();
						
						wordDefinitionTextView.setText(Constants.EMPTY_STRING);
						
						clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort),
										word, wordDefinitionTextView);
						
						clientThread.start();
						}
					}
				
				}
			}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        
        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        wordEditText = (EditText)findViewById(R.id.word);
        getWordDefinitionButton = (Button)findViewById(R.id.get_definition_button);
        getWordDefinitionButton.setOnClickListener(getWordDefinitionButtonClickListener);
        wordDefinitionTextView = (TextView)findViewById(R.id.definition_text_view);
    }
    
    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test02_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
