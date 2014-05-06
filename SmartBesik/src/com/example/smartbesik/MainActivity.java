package com.example.smartbesik;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


import android.app.Activity;
import android.app.TabActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

	    
	    TabHost tabHost = getTabHost();
        
        TabHost.TabSpec tabSpec;
        
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Control Besik");
        tabSpec.setContent(new Intent(this, FirstActivity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Link to social pages");
        tabSpec.setContent(new Intent(this, SecondActivity.class));
        tabHost.addTab(tabSpec);
        
	}
	 
		  

}
