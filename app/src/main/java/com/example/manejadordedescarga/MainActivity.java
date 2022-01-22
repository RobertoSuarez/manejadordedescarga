package com.example.manejadordedescarga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int request_code = 100;
    public static final String urlImage = "https://images.unsplash.com/photo-1642756458099-458baa75f20c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80";
    String imagename = "imagen.jpg";
    public String API = "https://my-json-server.typicode.com/RobertoSuarez/manejadordedescarga/files";

    public RequestQueue cola;

    public ArrayList<FileImagen> listData;
    RecyclerView listView;
    LinearLayout linearLayout;

    public AdapterData adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        cola = Volley.newRequestQueue(this);

        // permisos en runtime
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, request_code);


        MaterialButton bntDownload = (MaterialButton) findViewById(R.id.btndownload);
        bntDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(urlImage, imagename);
            }
        });

        getDataFile();

        linearLayout = findViewById(R.id.containerList);

        listView = (RecyclerView) findViewById(R.id.listview);
        listView.setLayoutManager(new LinearLayoutManager(this));

        listData = new ArrayList<FileImagen>();
        listData.add(new FileImagen(1, "imagen1", "url 1"));
        listData.add(new FileImagen(2, "imagen3", "url 2"));
        listData.add(new FileImagen(3, "imagen3", "url 3"));

        adaptador = new AdapterData(listData);
        listView.setAdapter(adaptador);
    }

    public void getDataFile() {
        JsonArrayRequest requestArray = new JsonArrayRequest(
                Request.Method.GET,
                API,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject datafile = response.getJSONObject(i);
                                String name = datafile.getString("name");
                                String data = datafile.getString("file");
                                System.out.println(name + " : "+ data);
                                listData.add(new FileImagen(datafile.getInt("id"), datafile.getString("name"), datafile.getString("file")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Erro en la api");
                    }
                }
        );

        cola.add(requestArray);
    }


    public void downloadFile(String url, String outputFileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imagename);
        request.setDescription("Descargando " + imagename);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

}