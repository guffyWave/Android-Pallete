package com.appxperts.androidpalleteexperiment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    MyAdapter myAdapter;

    TextView vibrantTextView, lightVibrantTextView, darkVibrantTextView, mutedTextView, darkMutedTextView, lightMutedTextView;

    Bitmap bitmap;

    int CAMERA_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        listView = (ListView) findViewById(R.id.listView);

        darkVibrantTextView = (TextView) findViewById(R.id.darkVibrantTextView);
        lightVibrantTextView = (TextView) findViewById(R.id.lightVibrantTextView);
        vibrantTextView = (TextView) findViewById(R.id.vibrantTextView);
        mutedTextView = (TextView) findViewById(R.id.mutedTextView);
        darkMutedTextView = (TextView) findViewById(R.id.darkMutedTextView);
        lightMutedTextView = (TextView) findViewById(R.id.lightMutedTextView);


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rose);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                setViewSwatch(vibrantTextView, palette.getVibrantSwatch());
                setViewSwatch(lightVibrantTextView, palette.getLightVibrantSwatch());
                setViewSwatch(darkVibrantTextView, palette.getDarkVibrantSwatch());
                setViewSwatch(mutedTextView, palette.getMutedSwatch());
                setViewSwatch(lightMutedTextView, palette.getLightMutedSwatch());
                setViewSwatch(darkMutedTextView, palette.getDarkMutedSwatch());

                int totalSwatchesPopulation = 0;
                for (Palette.Swatch s :
                        palette.getSwatches()) {
                    totalSwatchesPopulation += s.getPopulation();
                }

                myAdapter = new MyAdapter(palette.getSwatches(), getApplicationContext(), totalSwatchesPopulation);
                listView.setAdapter(myAdapter);
            }
        });

        imageView.setImageBitmap(bitmap);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });
    }


    public void setViewSwatch(TextView view, Palette.Swatch swatch) {
        if (swatch != null) {
            view.setTextColor(swatch.getTitleTextColor());
            view.setBackgroundColor(swatch.getRgb());
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                bitmap = (Bitmap) data.getExtras().get("data");

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        setViewSwatch(vibrantTextView, palette.getVibrantSwatch());
                        setViewSwatch(lightVibrantTextView, palette.getLightVibrantSwatch());
                        setViewSwatch(darkVibrantTextView, palette.getDarkVibrantSwatch());
                        setViewSwatch(mutedTextView, palette.getMutedSwatch());
                        setViewSwatch(lightMutedTextView, palette.getLightMutedSwatch());
                        setViewSwatch(darkMutedTextView, palette.getDarkMutedSwatch());


                        int totalSwatchesPopulation = 0;
                        for (Palette.Swatch s :
                                palette.getSwatches()) {
                            totalSwatchesPopulation += s.getPopulation();
                        }

                        myAdapter = new MyAdapter(palette.getSwatches(), getApplicationContext(), totalSwatchesPopulation);

                        listView.setAdapter(myAdapter);
                    }
                });

                imageView.setImageBitmap(bitmap);


            }
        }


    }
}
