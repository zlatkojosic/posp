package com.posp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
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
import java.io.*;
import java.util.*;


public class PMModule extends ReactContextBaseJavaModule {

   private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

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
                            bundle.putString("htmlData",html);
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



                @ReactMethod
                public void printPdf(ReadableArray readableArray,Promise promise){
                byte[] pdfData = new byte[readableArray.size()];
                for(int i = 0; i < readableArray.size();i++){
                    //System.out.println("type "+readableArray.getType(i));
                    pdfData[i] = (byte)readableArray.getInt(i);
                }
                System.out.println("printPdf called");
                    Log.i("print", "printPdf called");
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("de.ccv.payment.printservice", "de.ccv.payment.printservice.DirectPrintService"));
                    final ServiceConnection[] serviceConnectionArray = new ServiceConnection[1];
                    ServiceConnection connection = new ServiceConnection() {
                                public void onServiceConnected(ComponentName className, IBinder service) {
                                    Messenger messenger = new Messenger(service);
                                    Message msg = Message.obtain(null, 1, 0, 0);
                                    msg.replyTo = new Messenger(new PrintResponseHandler(promise));
                                    Bundle bundle = new Bundle();
                                    bundle.putByteArray("pdfData",pdfData);
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


                @ReactMethod
                public void printColl(ReadableArray readableArray,Promise promise){
                ArrayList<String> pdfCollection = new ArrayList<String>();
                for(int i = 0; i < readableArray.size();i++){
                    pdfCollection.add(readableArray.getString(i));
                }

                ReceiptPdfMapper mapper = new ReceiptPdfMapper(getReactApplicationContext(),"receipt");
                final File finalData = mapper.mapReceiptToPdf(pdfCollection);


                System.out.println("printPdf called");
                    Log.i("print", "printPdf called");
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("de.ccv.payment.printservice", "de.ccv.payment.printservice.DirectPrintService"));
                    final ServiceConnection[] serviceConnectionArray = new ServiceConnection[1];
                    ServiceConnection connection = new ServiceConnection() {
                                public void onServiceConnected(ComponentName className, IBinder service) {
                                    Messenger messenger = new Messenger(service);
                                    Message msg = Message.obtain(null, 1, 0, 0);
                                    msg.replyTo = new Messenger(new PrintResponseHandler(promise));
                                    Bundle bundle = new Bundle();

                                    try {
                                       Log.i("print", "before send message to print service");
                                        bundle.putByteArray("pdfData", toByteArray(new FileInputStream(finalData)));
                                        msg.setData(bundle);
                                        messenger.send(msg);
                                        Log.i("print", "after send message to print service");
                                    } catch(Exception exc) {
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


  public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

 public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }


    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


}