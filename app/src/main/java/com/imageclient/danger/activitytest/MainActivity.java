package com.imageclient.danger.activitytest;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    Button Next;
    public static final int TAKE_PHOTO_IMAGE_CODE = 100,  VIDEO_CAPTURE = 300;
    Button Camera,Video;
    ImageView mImageView;
    VideoView mVideoView;
    String ImgUrl = "" , ImgName = "";
    String VideoUrl = "" , VideoName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck;

        permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        Next = (Button) findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("msg","你好");
                startActivity(intent);
            }
        });

        mImageView = (ImageView) findViewById(R.id.imageview);
        Camera = (Button) findViewById(R.id.camera);
        Camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ImgUrl = Environment.getExternalStorageDirectory().getPath();
                ImgName = System.currentTimeMillis() + ".jpg";
                ImgUrl = ImgUrl + "/" + ImgName;
                File cameraPhoto = new File(ImgUrl);
                Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.imageclient.danger.activitytest",cameraPhoto );
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO_IMAGE_CODE);

            }
        });

        mVideoView = (VideoView) findViewById(R.id.videoview);
        Video = (Button) findViewById(R.id.video);
        Video.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                VideoUrl = Environment.getExternalStorageDirectory().getPath();
                VideoName = System.currentTimeMillis() + ".mp4";
                VideoUrl = VideoUrl + "/" + VideoName;
                File cameraPhoto = new File(VideoUrl);
                Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.imageclient.danger.activitytest",cameraPhoto );
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, VIDEO_CAPTURE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == TAKE_PHOTO_IMAGE_CODE)
            {
                File file = new File(ImgUrl);
                if(file.exists())
                {
                    Bitmap bitmap = BitmapFactory.decodeFile(ImgUrl);
                    mImageView.setImageBitmap(bitmap);
                }

            }
            else if (requestCode == VIDEO_CAPTURE)
            {
                File file = new File(VideoUrl);
                if(file.exists())
                {
                    MediaController mediaController  = new MediaController(MainActivity.this);
                    mVideoView.setMediaController(mediaController);
                    mVideoView.setVideoPath(VideoUrl);
                }
            }
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
