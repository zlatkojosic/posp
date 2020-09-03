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
import eu.ccvlab.mapi.opi.de.payment.PaymentService;
import eu.ccvlab.mapi.opi.de.payment.PaymentDelegate;
import eu.ccvlab.mapi.opi.de.payment.PaymentDelegate.SignatureAsked;
import eu.ccvlab.mapi.core.payment.Payment;
import eu.ccvlab.mapi.core.payment.Money;
import eu.ccvlab.mapi.core.terminal.ExternalTerminal;
import eu.ccvlab.mapi.core.payment.*;
import eu.ccvlab.mapi.core.*;
import eu.ccvlab.mapi.opi.de.payment.machine.*;
import org.simpleframework.xml.convert.Registry;

public class PayModule extends ReactContextBaseJavaModule {



  public PayModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
    public String getName() {
      return "PaymentService";
    }

        @ReactMethod
        public void payment(double amount,Promise promise){
            try{
                            Log.i("payment", "starting");
                            Payment payment = Payment.newBuilder()
                                .type(Payment.Type.SALE)
                                .amount(Money.EUR(amount))
                                .build();
                            ExternalTerminal terminal = ExternalTerminal
                                .newBuilder()
                                .ipAddress("192.0.0.2")
                                .port(20002)
                                .build();
                            PaymentService paymentService = new PaymentService();
                            paymentService.payment(terminal,payment,new DefaultPaymentDelegate());
            }catch(Exception e){
                promise.reject(e);
                Log.e("payment","error",e);
            }

        }


    class DefaultPaymentDelegate implements PaymentDelegate{

         private void log(String str){
            Log.i("payment", str);
         }


        @Override
        public void showTerminalOutput(List<String> output) {
               log("Terminal output: " +    output);
        }
        @Override
        public void printMerchantReceiptAndSignature(PaymentReceipt receipt) {
                log("Print merchant receipt and signature "  +    receipt);
        }
        @Override
        public void printCustomerReceiptAndSignature(PaymentReceipt receipt) {
               log("Print customer receipt and signature "  +    receipt);
        }
        @Override
        public void onPaymentSuccess(PaymentResult paymentResult) {
                log("Payment success: "  +    paymentResult);
        }

        @Override
        public void onPaymentError(PaymentResult paymentResult) {
                log("Payment error: "  + paymentResult);
        }
        @Override
        public void onError(MAPIError error) {
            log("Error: "  +    error);
        }
        @Override
        public void drawCustomerSignature(CustomerSignatureCallback callback) {
            log("Draw customer signature");
        }
        @Override
        public void askCustomerSignature(SignatureAsked signatureAsked) {
            log("Ask Customer Signature");
        }

//         @Override
//         public void askMerchantSignature(SignatureAsked signatureAsked) {
//             log("Ask Merchant Signature");
//         }

    }


}