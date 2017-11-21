package com.example.thoma.fliz;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Lesson1 extends AppCompatActivity {
    private SeekBar mseekBar;
    private GraphView graph;
    private TextView percentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);

        graph = (GraphView) findViewById(R.id.graph);
        percentText = findViewById(R.id.textView2);

        LineGraphSeries mSeries1 = new LineGraphSeries<>(generateData(0.0));
        graph.addSeries(mSeries1);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(21);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(300);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScrollable(true); // enables vertical scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        mSeries1.setDrawBackground(true);
        mSeries1.setBackgroundColor(Color.parseColor("#5500FF00"));

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Years");
        gridLabel.setVerticalAxisTitle("£");
        mseekBar = findViewById(R.id.seekBar);
        mseekBar.setMax(10);
        mseekBar.setX(0);

        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            graph.removeAllSeries();
            LineGraphSeries series =new LineGraphSeries<>(generateData((double)i));
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#5500FF00"));
            graph.addSeries(series);
            percentText.setText("Interest: "+ i +"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private DataPoint[] generateData(double pc ) {
        int count = 22;
        DataPoint[] values = new DataPoint[count];
        values[0] = new DataPoint(0,0);
        for (int i = 1; i < count; i++) {
            double x = i-1;
            double y = 100*Math.pow((1+0.01*pc),i);
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

}