package com.example.manejadordedescarga;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderDatos> {
    ArrayList<FileImagen> listDatos;
    public RequestQueue cola;

    public AdapterData(ArrayList<FileImagen> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null, false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView filename;
        MaterialButton btnDownload;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            filename = (TextView) itemView.findViewById(R.id.titulo);
            btnDownload = (MaterialButton) itemView.findViewById(R.id.btnDownload);

        }

        public void asignarDatos(FileImagen s) {
            filename.setText(s.getTitulo());
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(s.getFile()));
                    request.setTitle(s.getTitulo());
                    request.setDescription("Descargando " + s.getTitulo());
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.allowScanningByMediaScanner();
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, s.getName());
                    DownloadManager manager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
            });
        }


    }
}
