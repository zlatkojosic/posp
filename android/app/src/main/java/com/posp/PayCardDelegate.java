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
import eu.ccvlab.mapi.opi.de.payment.CardReadDelegate;
import eu.ccvlab.mapi.opi.de.payment.PaymentDelegate.SignatureAsked;
import eu.ccvlab.mapi.core.payment.Payment;
import eu.ccvlab.mapi.core.payment.Money;
import eu.ccvlab.mapi.core.terminal.ExternalTerminal;
import eu.ccvlab.mapi.core.payment.*;
import eu.ccvlab.mapi.core.*;
import eu.ccvlab.mapi.opi.de.payment.machine.*;
import eu.ccvlab.mapi.core.requests.ResultState;
import eu.ccvlab.mapi.opi.de.payment.CardReadResult;


public class PayCardDelegate implements CardReadDelegate {

    private Promise promise;


    public PayCardDelegate(Promise promise) {
        this.promise = promise;
    }


    @Override
    public void onFailure(ResultState paramResultState) {
        promise.reject(paramResultState.toString());
    }



    @Override
    public void onError(MAPIError paramMAPIError) {
        //promise.reject(DelegateUtils.convertMAPIErrorToMap(paramMAPIError));
        promise.reject(paramMAPIError.toString());
     }

    @Override
    public void onSuccess(CardReadResult card) {
        promise.resolve(DelegateUtils.convertCardReadToMap(card));
    }

    @Override
    public void showTerminalOutput(List < String > paramList) {
        String joined = StringUtil.join(paramList, "\n");
        //Send it as event
    }





}