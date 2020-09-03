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


public class PayModule extends ReactContextBaseJavaModule {


    PaymentService paymentService = new PaymentService();

    public PayModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "PaymentService";
    }


    private ExternalTerminal getExternalTerminal() {
        return ExternalTerminal
            .newBuilder()
            .ipAddress("192.0.0.2")
            .port(20002)
            .build();
    }


    @ReactMethod
    public void readCard(Callback onSuccess, Callback onError, Callback onFailure, Callback onShowTerminalOutput) {
        try {
            Log.i("readCard", "starting");
            ExternalTerminal terminal = getExternalTerminal()
            Log.i("readCard", "ExternalTerminal object created");
            PaymentService paymentService = new PaymentService();
            Log.i("readCard", "calling PaymentService");
            paymentService.paymentAfterCardRead(terminal, payment, new PayCardDelegate(onSuccess,onError,onFailure,onShowTerminalOutput));
            Log.i("readCard", "after calling PaymentService");
        } catch (Exception e) {
            promise.reject(e);
            Log.e("payment", "error", e);
        }

    }


    @ReactMethod
    public void payment(double amount,Callback onPaymentSuccess,Callback onPaymentError, Callback onError,
                        Callback onShowTerminalOutput,Callback onPrintMerchantReceiptAndSignature,
                        Callback onPrintCustomerReceiptAndSignature,Callback onDrawCustomerSignature) {
        try {
            Log.i("payment", "starting");
            Payment payment = Payment.newBuilder()
                .type(Payment.Type.SALE)
                .amount(Money.EUR(amount))
                .build();
            Log.i("payment", "payment object created");
            ExternalTerminal terminal = getExternalTerminal()
            Log.i("payment", "ExternalTerminal object created");
            Log.i("payment", "calling PaymentService");

            paymentService.paymentAfterCardRead(terminal, payment, new PayPaymentDelegate(onPaymentSuccess,onPaymentError,onError,onShowTerminalOutput,
            onPrintMerchantReceiptAndSignature,onPrintCustomerReceiptAndSignature,onDrawCustomerSignature));
            Log.i("payment", "after calling PaymentService");
        } catch (Exception e) {
            Log.e("payment", "error", e);
        }

    }



}