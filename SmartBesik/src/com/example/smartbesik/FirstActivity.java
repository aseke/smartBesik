package com.example.smartbesik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends Activity{
	final String LOG_TAG = "myLogs";

	  final int DIALOG_ITEMS = 1;
	  DB db;
	  Cursor cursor;

	  String data[] = { "low","medium","high","off" };
	  String temprature = "";
	  String humin = "";
	  String urin = "";
	boolean state = false;
	int speed=3;
	final int DIALOG_EXIT = 1;
	  Button btnOn, btnOff;
	    
	  
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.first_layout);
	  
	    ListTask LT = new ListTask();
	    LT.execute();
	    db = new DB(this);
	    db.open();
	    cursor = db.getAllData();
	    startManagingCursor(cursor);
	   
	    
	}
	 private class Connection extends AsyncTask {
		 
	        @Override
	        protected Object doInBackground(Object... arg0) {
	            connect();
	            return null;
	        }
	 
	    }
	 private void connect() {
		 HttpClient httpClient = new DefaultHttpClient();
	    	HttpContext localContext = new BasicHttpContext();
	    	HttpGet httpGet = new HttpGet("http://bots.myrobots.com/update?key=6A87C4499E92481F&field1="+speed+"&status=Hello");
	    	try {
				HttpResponse response = httpClient.execute(httpGet, localContext);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	 }
	public void onclick(View v) throws ClientProtocolException, IOException {
	    switch (v.getId()) {
	    case R.id.speedButton:
	      showDialog(DIALOG_ITEMS);
	      break;
	    case R.id.manCradleButton:
	    	new Connection().execute();
				
		      break;
	    default:
	      break;
	    }
	  }
	protected Dialog onCreateDialog(int id) {
	    AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    switch (id) {
	    // массив
	    case DIALOG_ITEMS:
	      adb.setTitle("Speed type");
	      adb.setSingleChoiceItems(data, -1, myClickListener);
	      break;
	    // адаптер
	   
	  }
	    adb.setPositiveButton("OK", myClickListener);
	    return adb.create();
	}

    OnClickListener myClickListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          ListView lv = ((AlertDialog) dialog).getListView();
          if (which == Dialog.BUTTON_POSITIVE)
        	  switch(lv.getCheckedItemPosition()){
        	  case 0:
        		  speed = 3;
        		  break;
        	  case 1:
        		  speed = 2;
        		  break;
        	  case 2:
        		  speed = 1;
        		  break;
        	  case 3:
        		  speed = 0;
        		  break;
        	  }
          else
            // выводим в лог позицию нажатого элемента
            Log.d(LOG_TAG, "which = " + which);
        }
      };
	  

	  protected void onDestroy() {
		    super.onDestroy();
		    db.close();
		  }
	  

	  
	  private void errorExit(String title, String message){
	    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
	    finish();
	  }
	  public class ListTask extends AsyncTask<Void, Void, Void>{
			String feedUrl1 = "http://bots.myrobots.com/channels/1098/field/field"+1+"/last.json";
			String feedUrl2 = "http://bots.myrobots.com/channels/1098/field/field"+2+"/last.json";
			String feedUrl3 = "http://bots.myrobots.com/channels/1098/field/field"+4+"/last.json";
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {

				HttpClient HC = new DefaultHttpClient();
				HttpGet httpRequest = new HttpGet(feedUrl1);
				try {
					HttpResponse response = HC.execute(httpRequest);
					StatusLine SL = response.getStatusLine();
					if (SL.getStatusCode() != 200){

						Log.i("aibol","null");
						return null;
					}
					BufferedReader BR = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line;
					StringBuilder SB = new StringBuilder();
					while((line = BR.readLine()) != null){
						SB.append(line);
					}
					String jsonData = SB.toString();
					JSONObject JSON = new JSONObject(jsonData);
					 temprature = JSON.getString("field1");
					 
					 
					 HttpClient HC2 = new DefaultHttpClient();
						HttpGet httpRequest2 = new HttpGet(feedUrl2);
						
							HttpResponse response2 = HC2.execute(httpRequest2);
							StatusLine SL2 = response2.getStatusLine();
							if (SL2.getStatusCode() != 200){

								Log.i("aibol","null");
								return null;
							}
							BufferedReader BR2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
							String line2;
							StringBuilder SB2 = new StringBuilder();
							while((line2 = BR2.readLine()) != null){
								SB2.append(line2);
							}
							String jsonData2 = SB2.toString();
							JSONObject JSON2 = new JSONObject(jsonData2);
							 humin = JSON2.getString("field2");

							 HttpClient HC3 = new DefaultHttpClient();
								HttpGet httpRequest3 = new HttpGet(feedUrl3);
								
									HttpResponse response3 = HC3.execute(httpRequest3);
									StatusLine SL3 = response3.getStatusLine();
									if (SL3.getStatusCode() != 200){

										Log.i("aibol","null");
										return null;
									}
									BufferedReader BR3 = new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));
									String line3;
									StringBuilder SB3 = new StringBuilder();
									while((line3 = BR3.readLine()) != null){
										SB3.append(line3);
									}
									String jsonData3 = SB3.toString();
									JSONObject JSON3 = new JSONObject(jsonData3);
									 urin = JSON3.getString("field4");
									 Log.i("aibol",urin);
						
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(Void result) {
				 TextView TV = (TextView) findViewById(R.id.temperatureTV);
				 TV.setText(temprature);
				 
				 TextView TV2 = (TextView) findViewById(R.id.huminTV);
				 TV2.setText(humin);
				 ImageView IV  = (ImageView)findViewById(R.id.rubbishImg);
				 String str = "0.0";
				 if (urin.equals(str)){
				 IV.setImageResource(R.drawable.rabbish);
				 }else {
					 IV.setImageResource(R.drawable.full_rabbish);
				 }
				super.onPostExecute(result);
			}
	  }
}
