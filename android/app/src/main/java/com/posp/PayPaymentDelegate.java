package com.posp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
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
import eu.ccvlab.mapi.opi.de.payment.CardDelegate;
import eu.ccvlab.mapi.opi.de.payment.PaymentDelegate.SignatureAsked;
import eu.ccvlab.mapi.core.payment.Payment;
import eu.ccvlab.mapi.core.payment.Money;
import eu.ccvlab.mapi.core.terminal.ExternalTerminal;
import eu.ccvlab.mapi.core.payment.*;
import eu.ccvlab.mapi.core.*;
import eu.ccvlab.mapi.opi.de.payment.machine.*;


public class PayPaymentDelegate extends PaymentDelegate {

    private Callback onPaymentSuccess;
    private Callback onPaymentError;
    private Callback onError;
    private Callback onShowTerminalOutput;
    private Callback onPrintMerchantReceiptAndSignature;
    private Callback onPrintCustomerReceiptAndSignature;
    private Callback onDrawCustomerSignature;
    private Callback onAskCustomerSignature;



    public PayPaymentDelegate(Callback onPaymentSuccess, Callback onPaymentError, Callback onError, onShowTerminalOutput, onPrintMerchantReceiptAndSignature,
        onPrintCustomerReceiptAndSignature, onDrawCustomerSignature) {
        this.onPaymentSuccess = onPaymentSuccess;
        this.onPaymentError = onPaymentError
        this.onError = onError;
        this.onShowTerminalOutput = onShowTerminalOutput;
        this.onPrintMerchantReceiptAndSignature = onPrintMerchantReceiptAndSignature;
        this.onPrintCustomerReceiptAndSignature = onPrintCustomerReceiptAndSignature;
        this.onDrawCustomerSignature = onDrawCustomerSignature;
        this.onAskCustomerSignature = onAskCustomerSignature;

    }


    private void log(String str) {
        Log.i("payment delegate ", str);
    }



    @Override
    public void onPaymentSuccess(PaymentResult paymentResult) {
        onPaymentSuccess.invoke(DelegateUtils.convertPaymentResultToMap(paymentResult));
    }

    @Override
    public void onPaymentError(PaymentResult paymentResult) {
        onPaymentError.invoke(DelegateUtils.convertPaymentResultToMap(paymentResult));
    }
    @Override
    public void onError(MAPIError error) {
        onError.invoke(DelegateUtils.convertMAPIErrorToMap(error)

        }

        @Override
        public void showTerminalOutput(List < String > output) {
            String joined = StringUtil.join(paramList, "\n");
            onShowTerminalOutput.invoke(joined);
        }
        @Override
        public void printMerchantReceiptAndSignature(PaymentReceipt receipt) {
            log("Print merchant receipt and signature " + receipt);
        }
        @Override
        public void printCustomerReceiptAndSignature(PaymentReceipt receipt) {
            log("Print customer receipt and signature " + receipt);
        }

        @Override
        public void drawCustomerSignature(CustomerSignatureCallback callback) {
            log("Draw customer signature");
        }
        @Override
        public void askCustomerSignature(SignatureAsked signatureAsked) {
            log("Ask Customer Signature");
        }




    }