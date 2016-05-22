package co.techsylvania.rolocolor.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import co.techsylvania.rolocolor.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ColorBlindnessCorrectionActivity extends ActivityBase implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = ColorBlindnessCorrectionActivity.class.getSimpleName();

    public static final int VIEW_MODE_RGBA = 0;
    public static final int VIEW_MODE_ZOOM = 5;
    public static final int VIEW_MODE_ZOOMED_TRANSLATED = 8;

    private MenuItem mItemPreviewRGBA;
    private MenuItem mItemPreviewZoom;
    private MenuItem mItemPreviewZoomedTranslated;
    private CameraBridgeViewBase mOpenCvCameraView;

//    private Size                 mSize0;

    private Mat mIntermediateMat;
    private int mHistSizeNum = 25;
    public static int viewMode = VIEW_MODE_ZOOM;

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


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_b);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCameraPermission();

        } else {
            mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.image_manipulations_activity_surface_view);
            mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
            mOpenCvCameraView.setCvCameraViewListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemPreviewRGBA = menu.add("Preview RGBA");
        mItemPreviewZoom = menu.add("Zoom");
        mItemPreviewZoomedTranslated = menu.add("ZoomedTranslated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemPreviewRGBA)
            viewMode = VIEW_MODE_RGBA;
        else if (item == mItemPreviewZoom)
            viewMode = VIEW_MODE_ZOOM;
        else if (item == mItemPreviewZoomedTranslated)
            viewMode = VIEW_MODE_ZOOMED_TRANSLATED;
        return true;
    }

    public void onCameraViewStarted(int width, int height) {
        mIntermediateMat = new Mat();


        // Fill sepia kernel
//        mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
//        mSepiaKernel.put(0, 0, /* R */0.189f, 0.769f, 0.393f, 0f);
//        mSepiaKernel.put(1, 0, /* G */0.168f, 0.686f, 0.349f, 0f);
//        mSepiaKernel.put(2, 0, /* B */0.131f, 0.534f, 0.272f, 0f);
//        mSepiaKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
    }

    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mIntermediateMat != null)
            mIntermediateMat.release();

        mIntermediateMat = null;
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgba = inputFrame.rgba();
        Size sizeRgba = rgba.size();

        Mat rgbaInnerWindow;

        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        int left = 0;//cols / 8;
        int top = 0;//rows / 8;

        int width = cols;// * 3 / 4;
        int height = rows;// * 3 / 4;

        switch (ColorBlindnessCorrectionActivity.viewMode) {
            case ColorBlindnessCorrectionActivity.VIEW_MODE_RGBA:
                break;


            case ColorBlindnessCorrectionActivity.VIEW_MODE_ZOOM:

                Mat dst = new Mat();
                Mat mZoomWindow = inputFrame.rgba().submat(rows / 2 - 20 * rows / 100, rows / 2 + 10 * rows / 100, cols / 2 - 27 * cols / 100, cols / 2 + 5 * cols / 100);
                Size newSize = rgba.size();

                Imgproc.resize(mZoomWindow, dst, rgba.size());
                mZoomWindow.release();

                return dst;

            case ColorBlindnessCorrectionActivity.VIEW_MODE_ZOOMED_TRANSLATED:

                break;
        }

        return rgba;
    }

    private static final int REQUEST_CAMERA = 0;

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
//            Snackbar.make(mLayout, R.string.permission_camera_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.app_name, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(ColorBlindnessCorrectionActivity.this,
//                                    new String[]{Manifest.permission.CAMERA},
//                                    REQUEST_CAMERA);
//                        }
//                    })
//                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CAMERA) {

            // Received permission result for camera permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
//                Snackbar.make(mLayout, R.string.permision_available_camera,
//                        Snackbar.LENGTH_SHORT).show();
            } else {
//                Snackbar.make(mLayout, R.string.permissions_not_granted,
//                        Snackbar.LENGTH_SHORT).show();

            }
        }
    }
}
