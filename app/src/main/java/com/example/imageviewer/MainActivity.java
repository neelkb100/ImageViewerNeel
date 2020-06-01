package com.example.imageviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.imageviewer.adapter.PhotosAdapter;
import com.example.imageviewer.model.ResponseModel;
import com.example.imageviewer.network.ApiClient;
import com.example.imageviewer.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;

    ApiInterface apiInterface;

    List<ResponseModel> responseModel;

    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 786;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermissions();
        inti();
    }

    private void inti() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        recyclerViewMain = findViewById(R.id.recyclerViewMain);


        fetchJsonData();
    }


    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //PORTRAIT MODE
            PhotosAdapter.screenChanged("PORTRAIT");
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //LANDSCAPE MODE
            PhotosAdapter.screenChanged("LANDSCAPE");
        }
    }*/


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    private void fetchJsonData() {


        apiInterface.getList().enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {

                if(response.body()!= null){

                    responseModel = response.body();

                    PhotosAdapter photosAdapter = new PhotosAdapter(MainActivity.this,responseModel);
                    LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(MainActivity.this);
                    recyclerViewMain.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerViewMain.setAdapter(photosAdapter);


                }
                else if (response.errorBody()!= null) {

                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
}
