package com.hhbgk.wristband.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demuxer.BtBandManager;
import com.demuxer.PayloadInfo;
import com.hhbgk.wristband.R;
import com.hhbgk.wristband.util.ClientManager;
import com.hhbgk.wristband.util.CommonUtils;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;

import java.util.Locale;
import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class CharacterActivity extends Activity implements View.OnClickListener {
    private String tag = getClass().getSimpleName();
    private String mMac;
    private UUID mService;
    private UUID mCharacter;

    private TextView mTvTitle;

    private Button mBtnRead;

    private Button mBtnWrite;
    private EditText mEtInput;

    private Button mBtnNotify;
    private Button mBtnUnnotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);

        Intent intent = getIntent();
        mMac = intent.getStringExtra("mac");
        mService = (UUID) intent.getSerializableExtra("service");
        mCharacter = (UUID) intent.getSerializableExtra("character");

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvTitle.setText(String.format("%s", mMac));

        mBtnRead = (Button) findViewById(R.id.read);

        mBtnWrite = (Button) findViewById(R.id.write);
        mEtInput = (EditText) findViewById(R.id.input);

        mBtnNotify = (Button) findViewById(R.id.notify);
        mBtnUnnotify = (Button) findViewById(R.id.unnotify);

        mBtnRead.setOnClickListener(this);
        mBtnWrite.setOnClickListener(this);

        mBtnNotify.setOnClickListener(this);
        mBtnNotify.setEnabled(true);

        mBtnUnnotify.setOnClickListener(this);
        mBtnUnnotify.setEnabled(false);
    }

    private final BleReadResponse mReadRsp = new BleReadResponse() {
        @Override
        public void onResponse(int code, byte[] data) {
            if (code == REQUEST_SUCCESS) {
                mBtnRead.setText(String.format("read: %s", ByteUtils.byteToString(data)));
                CommonUtils.toast("success");
            } else {
                CommonUtils.toast("failed");
                mBtnRead.setText("read");
            }
        }
    };

    private boolean isWriteSuccess = false;
    private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                isWriteSuccess = true;
                CommonUtils.toast("success");
            } else {
                isWriteSuccess = false;
                CommonUtils.toast("failed");
            }
        }
    };

    private final BleNotifyResponse mNotifyRsp = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            if (service.equals(mService) && character.equals(mCharacter)) {
                mBtnNotify.setText(String.format("%s", ByteUtils.byteToString(value)));
            }
            Log.e(tag, "mNotifyRsp="+ ByteUtils.byteToString(value));
        }

        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                mBtnNotify.setEnabled(false);
                mBtnUnnotify.setEnabled(true);
                CommonUtils.toast("success");
            } else {
                CommonUtils.toast("failed");
            }
        }
    };

    private final BleUnnotifyResponse mUnnotifyRsp = new BleUnnotifyResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                CommonUtils.toast("success");
                mBtnNotify.setEnabled(true);
                mBtnUnnotify.setEnabled(false);
            } else {
                CommonUtils.toast("failed");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read:
                ClientManager.getClient().read(mMac, mService, mCharacter, mReadRsp);
                break;
            case R.id.write:
                byte[] data;
                /*if (isWriteSuccess){
                    PayloadInfo payloadInfo = new PayloadInfo();
                    payloadInfo.setCommandId(0x06);
                    SparseArray<byte[]> sparseArray = new SparseArray<>();
                    sparseArray.put(0x03, null);
                    payloadInfo.setValue(sparseArray);
                    data = BtBandManager.getInstance().wrapData(payloadInfo);

                } else*/
                {
                    PayloadInfo payloadInfo = new PayloadInfo();
                    payloadInfo.setCommandId(0x06);
                    SparseArray<byte[]> sparseArray = new SparseArray<>();
                    sparseArray.put(0x03, null);
                    /*
                    sparseArray.put(Keys.KEY_REQUEST_BINDING_USER, new byte[]{
//                            0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8,
//                            0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8,
//                            0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8,
                            0x1,0x2,0x3,0x4,0x5,0x6,0x7,0x8});
                    */
                    payloadInfo.setValue(sparseArray);
                    data = BtBandManager.getInstance().wrapData(payloadInfo);
                }


                ClientManager.getClient().write(mMac, mService, mCharacter,data,
//                        ByteUtils.stringToBytes(mEtInput.getText().toString()),
                        mWriteRsp);
                break;
            case R.id.notify:
                ClientManager.getClient().notify(mMac, mService, mCharacter, mNotifyRsp);
                break;
            case R.id.unnotify:
                ClientManager.getClient().unnotify(mMac, mService, mCharacter, mUnnotifyRsp);
                break;
        }
    }

    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            BluetoothLog.v(String.format(Locale.US,"CharacterActivity.onConnectStatusChanged status = %d", status));

            if (status == STATUS_DISCONNECTED) {
                CommonUtils.toast("disconnected");
                mBtnRead.setEnabled(false);
                mBtnWrite.setEnabled(false);
                mBtnNotify.setEnabled(false);
                mBtnUnnotify.setEnabled(false);

                mTvTitle.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        ClientManager.getClient().registerConnectStatusListener(mMac, mConnectStatusListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ClientManager.getClient().unregisterConnectStatusListener(mMac, mConnectStatusListener);
    }
}
