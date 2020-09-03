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


public class DelegateUtils {

    public static WritableMap convertCardReadToMap(CardReadResult card) {
        WritableMap map = new WritableMap();
        map.putString("cardCircuit", card.cardCircuit());
        map.putString("expiryDate", card.expiryDate());
        map.putString("track1", card.track1());
        map.putString("track2", card.track2());
        map.putString("track3", card.track3());
        return map
    }


    public static WritableMap convertMAPIErrorToMap(MAPIError paramMAPIError) {
        WritableMap map = new WritableMap();
        map.putInt("code", paramMAPIError.code);
        map.putString("description", paramMAPIError.description);
        return map
    }

    public static WritableMap convertPaymentResultToMap(PaymentResult paymentResult) {
        WritableMap map = new WritableMap();
        map.putString("paymentSTAN", paymentResult.paymentSTAN());
        map.putString("terminalId", paymentResult.terminalId());
        //create money map
        if (paymentResult.amount() != null) {
            WritableMap moneyMap = new WritableMap();
            moneyMap.putDouble("value", paymentResult.amount().value().toDouble());
            moneyMap.String("currency", paymentResult.amount().currency().toString());
            map.putMap("money", moneyMap);
        }
        map.putString("timestamp", paymentResult.timestamp());
        map.putString("acquirerId", paymentResult.acquirerId());
        map.putString("requestId", paymentResult.requestId());
        //crate card map
        if (paymentResult.card() != null) {
            WritableMap cardMap = new WritableMap();
            cardMap.putString("card", paymentResult.card().card());
            cardMap.putString("pan", paymentResult.card().pan());
            cardMap.putString("panHash", paymentResult.card().panHash());
            cardMap.putString("expiryDate", paymentResult.card().expiryDate());
            map.putMap("card", cardMap);
        }

        map.putString("receiptNumber", paymentResult.receiptNumber());
        map.putString("merchantId", paymentResult.merchantId());
        map.putString("cardHolderAuthentication", paymentResult.cardHolderAuthentication());
        map.putString("authorisationType", paymentResult.authorisationType());
        map.putString("approvalCode", paymentResult.approvalCode());
        return map;
    }


}