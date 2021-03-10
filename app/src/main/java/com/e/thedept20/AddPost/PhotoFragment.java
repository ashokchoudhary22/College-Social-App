package com.e.thedept20.AddPost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.thedept20.R;
import com.e.thedept20.Utils.Permissions;
import com.e.thedept20.Utils.RotateBitmap;


/**
 * Created by User on 5/28/2017.
 */

public class PhotoFragment extends Fragment
{
    private static final String TAG = "PhotoFragment";

    //constant
    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int GALLERY_FRAGMENT_NUM = 2;
    private static final int CAMERA_REQUEST_CODE = 5;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
        {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.d(TAG, "onCreateView: started.");

        Button btnLaunchCamera = view.findViewById(R.id.btnLaunchCamera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
                {
                Log.d(TAG, "onClick: launching camera.");

                if (((ShareActivity) getActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUM)
                    {
                    if (((ShareActivity) getActivity()).checkPermissions(Permissions.CAMERA_PERMISSION[0]))
                        {
                        Log.d(TAG, "onClick: starting camera");
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                        } else
                        {
                        Intent intent = new Intent(getActivity(), ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        }
                    }
                }
        });

        return view;
        }

    private boolean isRootTask()
        {
        return ((ShareActivity) getActivity()).getTask() == 0;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE)
            {
            Log.d(TAG, "onActivityResult: done taking a photo.");
            Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");


            if (isRootTask())
                {
                try
                    {
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Log.d(TAG, "onActivityResult: received new bitmap from camera: " + bitmap);
                    Intent intent = new Intent(getActivity(), NextActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap), bitmap);
                    startActivity(intent);
                    } catch (NullPointerException e)
                    {
                    Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
                    }


                } else
                {
                getActivity().finish();
                }

            }

        }

}

































