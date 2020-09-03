package com.posp;

import com.facebook.react.bridge.WritableMap;

public interface EventSender{

    public void sendEvent(String eventName, WritableMap params);

}

