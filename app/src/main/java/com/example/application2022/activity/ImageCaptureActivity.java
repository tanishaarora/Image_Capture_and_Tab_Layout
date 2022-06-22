package com.example.application2022.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.application2022.MainActivity;
import com.example.application2022.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageCaptureActivity extends AppCompatActivity {

    Button btn_camera, btn_gallery;
    String currentPhotoPath;

    private static final int CAMERA_REQUEST = 102;
    ImageView imageView;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int GALLERY_REQUEST = 105;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_gallery = (Button) findViewById(R.id.btn_gallery);

        imageView = (ImageView) findViewById(R.id.imageView);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     activityResultLauncher.launch("image/*");
                askCameraPermission();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher_gallery.launch(intent_gallery);
                //          Toast.makeText(ImageCaptureActivity.this, "GALLERY", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera Permission is Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher_camera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //super.onActivityResult(requestCode, resultCode, data);
                    //if (result.getResultCode() == CAMERA_REQUEST) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        File file = new File(currentPhotoPath);
      //                  Intent intent = result.getData();
                        imageView.setImageURI(Uri.fromFile(file));
                        Log.d("Tag", "Absolute Url of Image is " + Uri.fromFile(file));

                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(file);
             //           Uri contentUri = mediaScanIntent.getData();
                        mediaScanIntent.setData(contentUri);
                        this.sendBroadcast(mediaScanIntent);

                        //}
                        //          Bitmap image = (Bitmap) data.getExtras().get("data");
                        //          imageView.setImageBitmap(image);
                    }
                }

        private void sendBroadcast(Intent mediaScanIntent) {
        }
    });

        ActivityResultLauncher<Intent> activityResultLauncher_gallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    Uri contentUri = data.getData();
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timestamp + " . " + getFileExt(contentUri);
                    Log.d("tag", "onActivity: Gallery Image Uri: " + imageFileName);
                    imageView.setImageURI(contentUri);
                //}
            }
        }
        });


        private String getFileExt(Uri contentUri) {
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(contentResolver.getType(contentUri));
        }

        private File createImageFile() throws IOException {

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timestamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName, ".jpg", storageDir);
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }


        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photofile = null;
                try {
                    photofile = createImageFile();
                } catch (IOException e) {
                }
                if (photofile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider", photofile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    activityResultLauncher_camera.launch(takePictureIntent);

                }
            }
        }
}