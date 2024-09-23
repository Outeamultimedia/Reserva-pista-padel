
package com.example.clubdiversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity2 extends AppCompatActivity {


    TextView DescriMain2;
    PDFView pdfView;
    ProgressBar progressBar;

    // url of our PDF file.
    String Link = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DescriMain2 = findViewById(R.id.DescriMain2);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            DescriMain2.setText(bundle.getString("Descripcion"));
            Link = bundle.getString("Link");
            pdfView = findViewById(R.id.idPDFView);
            progressBar = findViewById(R.id.progressBar);
            new RetrivePDFfromUrl(progressBar).execute(Link);
        }
    }

    // create an async task class for loading pdf file from URL.
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {

        ProgressBar progressBar;
        public RetrivePDFfromUrl(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                Log.e("Salida","1");
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
            progressBar.setVisibility(View.GONE);
        }
    }
}