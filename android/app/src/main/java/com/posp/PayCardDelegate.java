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


public class PayCardDelegate extends CardDelegate {

    private Callback onSuccess;
    private Callback onError;
    private Callback onFailure;
    private Callback onShowTerminalOutput;


    public PayCardDelegate(Callback onSuccess, Callback onError, Callback onFailure, onShowTerminalOutput) {
        this.onSuccess = onSuccess;
        this.onError = onError
        this.onFailure = onFailure;
        this.onShowTerminalOutput = onShowTerminalOutput;
    }


    @Override
    public void onFailure(ResultState paramResultState) {
        onFailure.invoke(paramResultState.toString);
    }



    @Override
    public void onError(MAPIError paramMAPIError) {
        onError.invoke(DelegateUtils.convertMAPIErrorToMap(paramMAPIError));
    }

    @Override
    public void onSuccess(CardReadResult card) {
        onSuccess.invoke(DelegateUtils.convertCardReadToMap(card));
    }

    @Override
    public void showTerminalOutput(List < String > paramList) {
        String joined = StringUtil.join(paramList, "\n");
        onShowTerminalOutput.invoke(joined);
    }





}