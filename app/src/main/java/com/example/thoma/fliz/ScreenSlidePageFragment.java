package com.example.thoma.fliz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ScreenSlidePageFragment extends Fragment {

    public TextView content;
    int mNum;
    private SeekBar mseekBar;
    private GraphView graph;
    private TextView percentText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("index") : 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page1, container, false);
      //  View tv = rootView.findViewById(R.id.contentText);
//        ((TextView) tv).setText("Fragment #" + 3);
        switch (mNum) {
            case (0):
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page1, container, false);
                //               tv = rootView.findViewById(R.id.contentText);
                //            ((TextView) tv).setText("Fragment #" + 0);
                break;
            case (1):
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page2, container, false);
                //           tv = rootView.findViewById(R.id.contentText);
                //        ((TextView) tv).setText("Fragment #" + 1);
                break;
            case (2):
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page3, container, false);
                //       tv = rootView.findViewById(R.id.contentText);
                //    ((TextView) tv).setText("Fragment #" + 2);
                break;
            case (3):
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page4, container, false);
                //   tv = rootView.findViewById(R.id.contentText);
                //((TextView) tv).setText("Fragment #" + 3);

                graph = (GraphView) rootView.findViewById(R.id.graph);
                percentText = rootView.findViewById(R.id.textView2);

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
                mseekBar = rootView.findViewById(R.id.seekBar);
                mseekBar.setMax(10);
                mseekBar.setX(0);

                mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        graph.removeAllSeries();
                        LineGraphSeries series = new LineGraphSeries<>(generateData((double) i));
                        series.setDrawBackground(true);
                        series.setBackgroundColor(Color.parseColor("#5500FF00"));
                        graph.addSeries(series);
                        percentText.setText("Interest: " + i + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

                break;
            default:
                 rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page1, container, false);
                // tv = rootView.findViewById(R.id.contentText);
                //((TextView) tv).setText("Fragment #" + 1000);
                break;
        }

        return rootView;
    }

    public static ScreenSlidePageFragment newInstance(int index) {
        ScreenSlidePageFragment f = new ScreenSlidePageFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    //public int getShownIndex() {
     //   return getArguments().getInt("index", 0);
   // }

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
