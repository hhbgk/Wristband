package com.inuker.bluetooth.library.connect.response;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.inuker.bluetooth.library.connect.listener.IBluetoothGattResponse;

import java.util.List;
import java.util.UUID;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;

/**
 * Created by dingjikerbo on 2016/8/25.
 */
public class BluetoothGattResponse extends BluetoothGattCallback {
    String tag = getClass().getSimpleName();

    private IBluetoothGattResponse response;

    public BluetoothGattResponse(IBluetoothGattResponse response) {
        this.response = response;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        response.onConnectionStateChange(status, newState);
    }

    UUID RX_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID RX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            readspcial(gatt, status, RX_SERVICE_UUID, RX_CHAR_UUID);
        }
        response.onServicesDiscovered(status);
    }
    /**
     * 读取特定service UUID data
     *
     * @param gatt
     * @param status
     */
    private  synchronized void readspcial(BluetoothGatt gatt, int status, UUID serviceUUID, UUID characteristicUUID) {
        if (serviceUUID != null) {
            try {
                BluetoothGattService mBlueToothService = gatt.getService(serviceUUID);
                if (characteristicUUID != null) {
                    Log.w(getClass().getSimpleName(), "----------------------readspcial----------------");
                    BluetoothGattCharacteristic RXCharacteristic = mBlueToothService.getCharacteristic(characteristicUUID);
                    gatt.setCharacteristicNotification(RXCharacteristic, true);
                    getDescriptor(gatt, RXCharacteristic);
                } else {
                    getCharacteristics(gatt, mBlueToothService);
                    Log.e(getClass().getSimpleName(), "characteristicUUID is null");
                }
            } catch (Exception e) {
//                displayGattServices(gatt);
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
        } else {
            Log.e(getClass().getSimpleName(), "service UUID is null");
        }
    }

    /**
     * 循环读取数据
     * @param gatt
     * @param gattService
     */
    private void getCharacteristics(final BluetoothGatt gatt, BluetoothGattService gattService) {
        List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
        for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
            if (gattCharacteristic.getUuid().equals(RX_CHAR_UUID)) {
                gatt.setCharacteristicNotification(gattCharacteristic, true);
//                gatt.readCharacteristic(gattCharacteristic);
                getDescriptor(gatt, gattCharacteristic);
            }
        }
    }
    // -----Descriptors的字段信息-----
    private void getDescriptor(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        List<BluetoothGattDescriptor> gattDescriptors = characteristic.getDescriptors();
        for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
            gattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(gattDescriptor);
            gatt.readDescriptor(gattDescriptor);
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        response.onCharacteristicRead(characteristic, status, characteristic.getValue());
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        response.onCharacteristicWrite(characteristic, status, characteristic.getValue());
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        response.onCharacteristicChanged(characteristic, characteristic.getValue());
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        response.onDescriptorWrite(descriptor, status);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        response.onDescriptorRead(descriptor, status, descriptor.getValue());
    }

    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        Log.e(tag, "onReliableWriteCompleted: " + (status==GATT_SUCCESS? "success":"failed"));
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        response.onReadRemoteRssi(rssi, status);
    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
    }
}
