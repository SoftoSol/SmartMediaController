package com.example.remoteController;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
public class ConnectorClass {
    private static final String TAG = ConnectorClass.class.getSimpleName();
    private static Socket serverSocket;
    private static OutputStream output;
    private static InputStream input;

    //private PrintWriter out;
    private static boolean connected;
    private static ConnectTask connectTsk;

    public ConnectorClass()
    {
        serverSocket = null;
        input=null;
        output=null;
        //out = null;
        connected = false;
    }

    public void connect(Context context, String host, int port)
    {
        connectTsk=new ConnectTask(context);
        connectTsk.execute(host, String.valueOf(port));
    }

    private class ConnectTask extends AsyncTask<String, Void, Void> {

        private Context context;
        private String host;
        private int port;

        public ConnectTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            showToast(context, "Connecting..");
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(String... params) {
            try {
                host = params[0];
                port = Integer.parseInt(params[1]);
                serverSocket = new Socket();
                serverSocket.connect(new InetSocketAddress(host, port));
                System.out.println("Is connected="+ serverSocket.isConnected());

                output = serverSocket.getOutputStream();
                input = serverSocket.getInputStream();

                connected = true;
            } catch (UnknownHostException e) {
                showToast(context, "Don't know about host: " + host + ":" + port);
                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
                System.out.println(e);
                showToast(context, "Couldn't get I/O for the connection to: " + host + ":" + port);
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (connected) {
                showToast(context, "Connection successfull");
                Intent intent = new Intent(context, ControllerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }//End of ConnectTask Class

    public boolean isConnected() {
        if(serverSocket!=null)
            return serverSocket.isConnected();
        else
            return false;
    }

    public void disconnect(Context context) {
        if ( connected )
        {
            try {
                serverSocket.shutdownInput();
                serverSocket.shutdownOutput();
                serverSocket.close();
                input.close();
                output.close();
                connected = false;
            } catch (IOException e) {
                showToast(context, "Couldn't get I/O for the connection");
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static String sendCommand(String message){
        //Sending
        //byte[] msgByte= new byte[1024]; //[0]
        try {
            //Sending
            byte[] msgByte = message.getBytes("UTF8");
            output.write(msgByte);
            return recMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String recMessage(){
        byte[] b=new byte[512];
        try {
            input = serverSocket.getInputStream();
            input.read(b);
            String rec=new String(b,"UTF8");
            if(rec!=null)
                rec=rec.substring(0,rec.indexOf("<EOF>"));
            System.out.println("Server response in recMessage(): "+rec);
            return rec;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private void showToast(final Context context, final String message) {
        new Handler(context.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } //LENGTH_LONG
        });
    }
}