package co.techsylvania.rolocolor.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import co.techsylvania.rolocolor.R;

enum ColorblindMode {
    MODE_PROTANOPIA, MODE_PROTANOMALY, MODE_DEUTERANOPIA, MODE_DEUTERANOMALY,
    MODE_TRITANOPIA, MODE_TRITANOMALY, MODE_ACHROMATOPSIA, MODE_ACHROMATOMALY
}

public class ImageManipulationsActivity extends ActivityBase implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";
    private ColorblindMode currentMode;
    public static final int VIEW_MODE_RGBA = 0;
    public static final int VIEW_MODE_COLOR_SWITCH = 1;

    private CameraBridgeViewBase mOpenCvCameraView;


    private Mat mIntermediateMat;

    public static int viewMode = VIEW_MODE_RGBA;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public ImageManipulationsActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        currentMode = ColorblindMode.MODE_ACHROMATOMALY;

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_color_b);
        setupControlLayer();
        findViewById(R.id.tvShowAssist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showControls();
            }
        });
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            int MY_PERMISSIONS_REQUEST_CAMERA = 13;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.image_manipulations_activity_surface_view);
            mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
            mOpenCvCameraView.setCvCameraViewListener(this);
            viewMode = VIEW_MODE_COLOR_SWITCH;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    public void onCameraViewStarted(int width, int height) {
        mIntermediateMat = new Mat();
    }

    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mIntermediateMat != null)
            mIntermediateMat.release();

        mIntermediateMat = null;
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        Mat rgba = inputFrame.rgba();
        Size sizeRgba = rgba.size();

        Mat rgbaInnerWindow;

        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        int left = cols / 8;
        int top = rows / 8;

        int width = cols * 3 / 4;
        int height = rows * 3 / 4;

        switch (ImageManipulationsActivity.viewMode) {
            case ImageManipulationsActivity.VIEW_MODE_RGBA:
                break;
            case ImageManipulationsActivity.VIEW_MODE_COLOR_SWITCH:
                rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
                ApplyBlindnessFilter(rgbaInnerWindow, currentMode);
                //ApplyBlindnessFilter(rgbaInnerWindow, ColorblindMode.MODE_TRITANOMALY);

                rgbaInnerWindow.release();
                break;
        }
        return rgba;
    }

    void ApplyBlindnessFilter(Mat screenRegion, ColorblindMode mode) {
        Mat filter = new Mat(4, 4, CvType.CV_32F);
        //    {Normal:{ R:[100, 0, 0], G:[0, 100, 0], B:[0, 100, 0]},

        switch (mode) {
            case MODE_PROTANOPIA: {
                //Protanopia:{ R:[56.667, 43.333, 0], G:[55.833, 44.167, 0], B:[0, 24.167, 75.833]},
                filter.put(0, 0, /* R */0.56667, 0.43333, 0, 0);
                filter.put(1, 0, /* G */0.55833, 0.44167, 0, 0);
                filter.put(2, 0, /* B */0, 0.24167, 0.75833, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_PROTANOMALY: {
                //        Protanomaly:{ R:[81.667, 18.333, 0], G:[33.333, 66.667, 0], B:[0, 12.5, 87.5]},
                filter.put(0, 0, /* R */0.81667, 0.18333, 0, 0);
                filter.put(1, 0, /* G */0.33333, 0.66667, 0, 0);
                filter.put(2, 0, /* B */0, 0.125, 0.875, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_DEUTERANOPIA: {
                //        Deuteranopia:{ R:[62.5, 37.5, 0], G:[70, 30, 0], B:[0, 30, 70]},
                filter.put(0, 0, /* R */0.625, 0.375, 0, 0);
                filter.put(1, 0, /* G */0.70, 0.30, 0, 0);
                filter.put(2, 0, /* B */0, 0.30, 0.70, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_DEUTERANOMALY: {
                //        Deuteranomaly:{ R:[80, 20, 0], G:[25.833, 74.167, 0], B:[0, 14.167, 85.833]},
                filter.put(0, 0, /* R */0.80, 0.20, 0, 0);
                filter.put(1, 0, /* G */0.25833, 0.74167, 0, 0);
                filter.put(2, 0, /* B */0, 0.14167, 0.85833, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_TRITANOPIA: {
                //        Tritanopia:{ R:[95, 5, 0], G:[0, 43.333, 56.667], B:[0, 47.5, 52.5]},
//                filter.put(0, 0, /* R */1.05, -0.05, 0, 0);
//                filter.put(1, 0, /* G */0, 1.56667, -0.56667, 0);
//                filter.put(2, 0, /* B */0, 1.525, -0.525,0);
//                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                //
//                filter.put(0, 0, /* R */0.95*1.3, 0.05*1.3, 0, 0);
//                filter.put(1, 0, /* G */0, 0.43333*1.3, 0.56667*1.3, 0);
//                filter.put(2, 0, /* B */0, 0.475*1.3, 0.525*1.3,0);
//                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                //
//                filter.put(0, 0, /* R */0, 0, 0, 0);
//                filter.put(1, 0, /* G */0, 0.43333, 0.56667, 0);
//                filter.put(2, 0, /* B */0, 0.475, 0.525,0);
//                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                //
                filter.put(0, 0, /* R */0.95, 0.05, 0, 0);
                filter.put(1, 0, /* G */0, 0.43333, 0.56667, 0);
                filter.put(2, 0, /* B */0, 0.475, 0.525, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_TRITANOMALY: {
                //        Tritanomaly:{ R:[96.667, 3.333, 0], G:[0, 73.333, 26.667], B:[0, 18.333, 81.667]},
                filter.put(0, 0, /* R */0.96667, 0.03333, 0, 0);
                filter.put(1, 0, /* G */0, 0.73333, 0.26667, 0);
                filter.put(2, 0, /* B */0, 0.18333, 0.81667, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_ACHROMATOPSIA: {
                //        Achromatopsia:{ R:[29.9, 58.7, 11.4], G:[29.9, 58.7, 11.4], B:[29.9, 58.7, 11.4]},
                filter.put(0, 0, /* R */0.299, 0.587, 0.114, 0);
                filter.put(1, 0, /* G */0.299, 0.587, 0.114, 0);
                filter.put(2, 0, /* B */0.299, 0.587, 0.114, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            case MODE_ACHROMATOMALY: {
                //        Achromatomaly:{ R:[61.8, 32, 6.2], G:[16.3, 77.5, 6.2], B:[16.3, 32.0, 51.6]}
                filter.put(0, 0, /* R */0.618, 0.32, 0.062, 0);
                filter.put(1, 0, /* G */0.163, 0.775, 0.062, 0);
                filter.put(2, 0, /* B */0.163, 0.32, 0.516, 0);
                filter.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 0f);
                break;
            }
            default:
                Log.w("colorBlind", "Wrong mode selected.");
        }
        Core.transform(screenRegion, screenRegion, filter);
    }

    private void setupControlLayer() {
//        View v = LayoutInflater.from(this).inflate(R.layout.layout_diseases, (ViewGroup) findViewById(R.id.rlParent), false);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        ((RelativeLayout) findViewById(R.id.rlParent)).addView(v);
//        v.setLayoutParams(lp);


        //Protanopia
        findViewById(R.id.item11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_PROTANOPIA);
            }
        });

        // Protanomaly
        findViewById(R.id.item12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_PROTANOMALY);

            }
        });

        // Deuteranopia
        findViewById(R.id.item13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_DEUTERANOPIA);
            }
        });

        //Deuteranomaly
        findViewById(R.id.item21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentMode(ColorblindMode.MODE_DEUTERANOMALY);

            }
        });

        //Tritanopia
        findViewById(R.id.item22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_TRITANOPIA);
            }
        });

        // Tritanomaly
        findViewById(R.id.item23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_TRITANOMALY);
            }
        });

        //Achromatopsia
        findViewById(R.id.item31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_ACHROMATOPSIA);
            }
        });

        //Achromatomaly
        findViewById(R.id.item32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentMode(ColorblindMode.MODE_ACHROMATOMALY);
            }
        });

        //Normal RGB
        findViewById(R.id.item33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:replace qith rgb
//                currentMode = ColorblindMode.MODE_PROTANOMALY;
//                currentMode = ColorblindMode;
                showControls();
            }
        });

        hideControls();

    }

    private void setCurrentMode(ColorblindMode mode) {
        if (findViewById(R.id.rlParent).getVisibility() == View.GONE)
            showControls();
        else {
            currentMode = mode;
//            showControls();
            hideControls();
        }

    }

    private void hideControls() {
        findViewById(R.id.rlParent).animate().alpha(0.0f).setDuration(2000).start();
//        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setDuration(1000);
//        fadeOut.setFillAfter(true);
//
//        RelativeLayout rlParent = (RelativeLayout) findViewById(R.id.rlParent);
//        rlParent.setAnimation(fadeOut);
//        fadeOut.start();

    }

    private void showControls() {
        findViewById(R.id.rlParent).setVisibility(View.VISIBLE);
        findViewById(R.id.rlParent).animate().alpha(1.0f).setDuration(200).start();
//        RelativeLayout rlParent = (RelativeLayout) findViewById(R.id.rlParent);
//        AlphaAnimation anim = new AlphaAnimation(0, 1);
//        anim.setDuration(200);
//        anim.setFillAfter(true);
//        rlParent.setAnimation(anim);
//        anim.start();

    }
}
