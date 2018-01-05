package com.oleksandrsuhomlinov.mymessenger;

/**
 * Created by Oleksandr on 13.07.2017.
 */

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class MethodPostAndroidAbstract
        extends AsyncTask<URL, String, String>{

    abstract void ConnectionErrorWithServerResponce(Integer ServerResponce);
    abstract void ConnectionError1();
    abstract void InputStreamReadError();
    TextView OutputText; //only for onPostExecute

    protected String OutPutStreamData;
    void SetOutPutStreamData(String x) {
        OutPutStreamData=x;}

    protected String doInBackground(URL... params){
        HttpURLConnection connection = null;


        try {
            connection = (HttpURLConnection) params[0].openConnection();

            connection.setRequestMethod("POST");


            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty( "charset", "utf-8");


            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //??????
            String urlParameters = "lettertype=REST++TEST+//&mailcontent=+This is message posted from client : "+OutPutStreamData;
            //String urlParameters = "{"lettertype":5,"mailcontent":"This+Is+Very +Simple Test #777 12345""}";

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            //wr.writeUTF(urlParameters);
            wr.flush();
            wr.close();

            //Integer response = 9875;
            Integer response = connection.getResponseCode();
            // int response1 = response+1;




            //System.out.println(response);
            //response = HttpURLConnection.HTTP_NOT_ACCEPTABLE;
            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (IOException e) {

                    InputStreamReadError();

                    //Snackbar.make(findViewById(R.id.coordinatorLayout),
                    //R.string.read_error, Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                String ServerOut = builder.toString();
                publishProgress(ServerOut);



                //return new JSONObject(builder.toString());
                return  builder.toString();
                //return response;
            } else {

                ConnectionErrorWithServerResponce(response);
                //Snackbar.make(findViewById(R.id.coordinatorLayout),
                        //R.string.connect_error1,
                        //response,
                       // Snackbar.LENGTH_LONG).show();
                return null;


            }
        } catch (Exception e) {

            ConnectionError1();
            //Snackbar.make(findViewById(R.id.coordinatorLayout),
                    //R.string.connect_error, Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            connection.disconnect(); // close the HttpURLConnection

        }

        return null;
    }





    //protected void onPostExecute();
    protected void onPostExecute(String answer) {
        OutputText.setText(answer);
    }

}
