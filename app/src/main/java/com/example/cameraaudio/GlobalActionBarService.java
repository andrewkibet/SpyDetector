package com.example.cameraaudio;

import static com.example.cameraaudio.R.color.purple_700;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;

public class GlobalActionBarService extends AccessibilityService {
    FrameLayout mLayout;
    ImageView powerButton;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


        AccessibilityNodeInfo mNodeInfo = event.getSource();


    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {

//
//        System.out.println("onService Connected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 1000;
        info.packageNames = new String[]{"com.whatsapp", "com.instagram.android"};
        setServiceInfo(info);


        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.action_bar, mLayout);
        wm.addView(mLayout, lp);
        configurePowerButton();
        isCameraAvailable();

    }

    private void configurePowerButton() {
        powerButton = mLayout.findViewById(R.id.cameraIndicator);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isCameraAvailable();
            }
        });
    }


    public void isCameraAvailable() {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            manager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraAvailable(String cameraId) {
                    super.onCameraAvailable(cameraId);
                    //Do your work
                    powerButton.setBackgroundResource(purple_700);
                }


                @Override
                public void onCameraUnavailable(String cameraId) {
                    super.onCameraUnavailable(cameraId);
                    powerButton.setBackgroundResource(R.color.red);
                }
            }, handler);
        }
    }


    public boolean getMicrophoneAvailable(Context context) {
        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(new File(context.getCacheDir(), "MediaUtil#micAvailTestFile").getAbsolutePath());
        boolean available = true;
        try {
            recorder.prepare();
            recorder.start();

        } catch (Exception exception) {
            available = false;
        }
        recorder.release();
        return available;
    }


}



