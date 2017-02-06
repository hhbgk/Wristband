package com.inuker.bluetooth.library.connect.response;

/**
 * Created by bob on 17-2-3.
 */

public interface BleAckResponse {
    void onSuccess();
    void onError(String msg);
}
