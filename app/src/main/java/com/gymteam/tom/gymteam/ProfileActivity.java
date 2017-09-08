package com.gymteam.tom.gymteam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends Activity {

    private static final String TAG = "Profile";
    ImageView mImageView;
    public StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        Button button = (Button) findViewById(R.id.finish_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });



        mImageView = (ImageView) findViewById(R.id.imageView2);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        File imgFile = getOutputMediaFile();
        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mImageView.setImageBitmap(myBitmap);
        }
        loadPicFromFireBase();




    }

    private void loadPicFromFireBase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        StorageReference storageRef = storage.getReference().child("users").child(auth.getCurrentUser().getUid()).child("image.png");


        File localFile = null;
        try {
            localFile = File.createTempFile(auth.getCurrentUser().getUid(), "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File finalLocalFile = localFile;
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Log.d(TAG,taskSnapshot.toString());
                if(finalLocalFile.exists())
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    mImageView.setImageBitmap(myBitmap);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            storeImage(imageBitmap);
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
     return new File(this.getFilesDir(), "profile.png");

    }

    public void createUser()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();


        String email = auth.getCurrentUser().getEmail();
        User user = new User("tomtom hivertshabtay",email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        String userName = user.getName();
        Log.d(TAG, "adding to " + userName );

        myRef.child(userName).child("age").child("" + user.getAge());
        myRef.child(userName).child("id").child(auth.getCurrentUser().getUid());
        myRef.child(userName).child("name").child(user.getName());

        Log.d("USER", user.getName());

    }



    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        //save file into firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseAuth auth  = FirebaseAuth.getInstance();

        Log.d(TAG, "PATH" + storageRef.getPath().toString());
        StorageReference childRef = storageRef.child("users").child(auth.getCurrentUser().getUid()).child("image.png");
        childRef.putFile(Uri.fromFile(pictureFile));
        //END save file into firebase

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }
}
