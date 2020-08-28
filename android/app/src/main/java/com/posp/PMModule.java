package com.posp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import android.util.Log;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;


public class PMModule extends ReactContextBaseJavaModule {

   private static class PrintResponseHandler extends Handler {
           private Promise promise = null;
           public PrintResponseHandler(Promise promise){
                this.promise = promise;
           }

           @Override
           public void handleMessage(Message msg) {
               Log.i("print", "Response from printer received");
               int status = msg.getData().getInt("status", 666);
               Log.i("print", "Response from printer received status "+status);
               promise.resolve(status);
           }
       }

  public PMModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
    public String getName() {
      return "Printer";
    }

        @ReactMethod
        public void printHtml(String html,Promise promise){
        System.out.println("printHtml called");
            Log.i("print", "printHtml called");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("de.ccv.payment.printservice", "de.ccv.payment.printservice.DirectPrintService"));
            final ServiceConnection[] serviceConnectionArray = new ServiceConnection[1];
            ServiceConnection connection = new ServiceConnection() {
                        public void onServiceConnected(ComponentName className, IBinder service) {
                            Messenger messenger = new Messenger(service);
                            Message msg = Message.obtain(null, 1, 0, 0);
                            msg.replyTo = new Messenger(new PrintResponseHandler(promise));
                            Bundle bundle = new Bundle();
                            //bundle.putByteArray("html", html.getBytes());
                            bundle.puHtml("html",html.getBytes());
                            msg.setData(bundle);
                            try {
                               Log.i("print", "before send message to print service");
                                messenger.send(msg);
                                Log.i("print", "after send message to print service");
                            } catch(RemoteException exc) {
                                promise.reject(exc);
                                Log.e("print", "Remote exception", exc);
                            }
                        }

                        public void onServiceDisconnected(ComponentName className) {
                            Log.i("print", "disconnected from the print service");
                        }
                    };
            serviceConnectionArray[0] = connection;

            if(!getReactApplicationContext().bindService(intent, connection, getReactApplicationContext().BIND_AUTO_CREATE)) {
                Log.e("print", "Could not find printer service");
                getReactApplicationContext().unbindService(connection);
                promise.reject(new Exception("Could not bind to a printer service"));
            }
        }
}