package com.example.thoma.fliz;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.thoma.fliz.MainActivity.UI_ANIMATION_DELAY;

public class Lesson2 extends AppCompatActivity {

    private CardStackView cardStackView;
    private QuestionCardAdapter adapter;
    private int correctAns;
    private int incorrectAns;
    private int totalAns;
    private TextView resText;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);
        mContentView = findViewById(R.id.l2);

        setup();
        reload();
        hide();
        correctAns=incorrectAns=0;
        resText = findViewById(R.id.textView4);
        resText.setVisibility(View.GONE);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };


    public void hide() {
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

       // getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {

//            case R.id.menu_activity_main_reload:

//                reload();

//                break;

//            case R.id.menu_activity_main_add_first:

//                addFirst();

//                break;

//            case R.id.menu_activity_main_add_last:

//                addLast();

//                break;

//            case R.id.menu_activity_main_remove_first:

//                removeFirst();

//                break;

  //          case R.id.menu_activity_main_remove_last:

    //            removeLast();

      //          break;

    //        case R.id.menu_activity_main_swipe_left:

      //          swipeLeft();

//                break;

  //          case R.id.menu_activity_main_swipe_right:

//                swipeRight();

               // break;

         //   case R.id.menu_activity_main_reverse:

           //     reverse();

             //   break;

     //   }

        return super.onOptionsItemSelected(item);

    }

//TODO: move this into a resources file
    private List<Question> createQuestions() {
        List<Question> spots = new ArrayList<>();
        spots.add(new Question("APR", "APR stands for Annual Percentage Rate.", true));
        spots.add(new Question("Credit Checks", "My credit history will not be considered when applying for a credit card.", false));
        spots.add(new Question("Credit Limits","Almost all cards are subject to a credit limit.",true ));
        spots.add(new Question("Interest Rates","The level of interest levied on credit card debt is similar to that of other financial borrowing, such as a personal loan.",false));
        spots.add(new Question("Repayment","If I pay my credit card in full by the due date then I will not owe any interest to the bank",true));
        spots.add(new Question("Due Dates","There is no penalty incurred if I pay my balance after the due date.",false ));
        totalAns = spots.size();
        return spots;
    }

    private QuestionCardAdapter createTouristSpotCardAdapter() {
        final QuestionCardAdapter adapter = new QuestionCardAdapter(getApplicationContext());
        adapter.addAll(createQuestions());
        return adapter;
    }

    private void setup() {

        cardStackView = (CardStackView) findViewById(R.id.cardView);

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {

            @Override

            public void onCardDragging(float percentX, float percentY) {

                Log.d("CardStackView", "onCardDragging");

            }



            @Override

            public void onCardSwiped(SwipeDirection direction) {

                Log.d("CardStackView", "onCardSwiped: " + direction.toString());

                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());



                try {
                    if (direction.toString().equals("Top")) {
                        Log.d("oncardSwiped", "readdlast");

                        LinkedList<Question> spots = extractRemainingTouristSpots();
                        spots.addLast(adapter.getItem(cardStackView.getTopIndex() - 1));
                        adapter.clear();

                        adapter.addAll(spots);

                        adapter.notifyDataSetChanged();

                    } else if (adapter.getIsTrue(cardStackView.getTopIndex() - 1) && direction.toString().equals("Right")) {
                        //Log.d("Is True", "yes");
                        Log.d("correct", "case1");
                        correctAns++;
                    } else if (!adapter.getIsTrue(cardStackView.getTopIndex() - 1) && direction.toString().equals("Left")) {
                        Log.d("correct", "case2");
                        correctAns++;
                    } else incorrectAns++;
                }
                catch (Exception e)
                {

                }
                String str= String.valueOf(correctAns);
                Log.d("correctAns",str);
                Log.d("cards remaining",String.valueOf(totalAns - cardStackView.getTopIndex()));
                if(totalAns == correctAns + incorrectAns)
                {
                    showResults();
                }
                        // if (cardStackView.getTopIndex() == adapter.getCount()) {

                //    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());

                   // paginate();

                //}
            }



            @Override

            public void onCardReversed() {

                Log.d("CardStackView", "onCardReversed");

            }



            @Override

            public void onCardMovedToOrigin() {

                Log.d("CardStackView", "onCardMovedToOrigin");

            }



            @Override

            public void onCardClicked(int index) {

                Log.d("CardStackView", "onCardClicked: " + index);

            }

        });

    }



    private void reload() {
        cardStackView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
            }
        }, 10);
    }



    private LinkedList<Question> extractRemainingTouristSpots() {
        LinkedList<Question> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }
    private void addFirst() {

        LinkedList<Question> spots = extractRemainingTouristSpots();

//        spots.addFirst(createTouristSpot());

        adapter.clear();

        adapter.addAll(spots);

        adapter.notifyDataSetChanged();

    }



    private void addLast() {

        LinkedList<Question> spots = extractRemainingTouristSpots();

      //  spots.addLast(createTouristSpot());

        adapter.clear();

        adapter.addAll(spots);

        adapter.notifyDataSetChanged();

    }



    private void removeFirst() {

        LinkedList<Question> spots = extractRemainingTouristSpots();

        if (spots.isEmpty()) {

            return;

        }



        spots.removeFirst();

        adapter.clear();

        adapter.addAll(spots);

        adapter.notifyDataSetChanged();

    }



    private void removeLast() {

        LinkedList<Question> spots = extractRemainingTouristSpots();

        if (spots.isEmpty()) {

            return;

        }



        spots.removeLast();

        adapter.clear();

        adapter.addAll(spots);

        adapter.notifyDataSetChanged();

    }



    private void paginate() {

        cardStackView.setPaginationReserved();

        adapter.addAll(createQuestions());

        adapter.notifyDataSetChanged();

    }



    public void swipeLeft() {

        List<Question> spots = extractRemainingTouristSpots();

        if (spots.isEmpty()) {

            return;

        }



        View target = cardStackView.getTopView();



        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("rotation", -10f));

        rotation.setDuration(200);

        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));

        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));

        translateX.setStartDelay(50);

        translateY.setStartDelay(50);

        translateX.setDuration(300);

        translateY.setDuration(300);

        AnimatorSet set = new AnimatorSet();

        set.playTogether(rotation, translateX, translateY);



        cardStackView.swipe(SwipeDirection.Left, set);
      //  removeFirst();
    }



    public void swipeRight() {

        List<Question> spots = extractRemainingTouristSpots();

        if (spots.isEmpty()) {

            return;

        }



        View target = cardStackView.getTopView();



        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("rotation", 10f));

        rotation.setDuration(200);

        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));

        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));

        translateX.setStartDelay(100);

        translateY.setStartDelay(100);

        translateX.setDuration(500);

        translateY.setDuration(500);

        AnimatorSet set = new AnimatorSet();

        set.playTogether(rotation, translateX, translateY);



        cardStackView.swipe(SwipeDirection.Right, set);

    }


    public void swipeUp() {

        List<Question> spots = extractRemainingTouristSpots();

        if (spots.isEmpty()) {

            return;

        }



        View target = cardStackView.getTopView();



        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("rotation", 0f));

        rotation.setDuration(200);

        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationX", 0f, 0f));

        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(

                target, PropertyValuesHolder.ofFloat("translationY", 0f, -1000f));

        translateX.setStartDelay(100);

        translateY.setStartDelay(100);

        translateX.setDuration(500);

        translateY.setDuration(500);

        AnimatorSet set = new AnimatorSet();

        set.playTogether(rotation, translateX, translateY);



        cardStackView.swipe(SwipeDirection.Top, set);

    }



    private void reverse() {

        cardStackView.reverse();

    }

   // public void passPress(View v){
    //    swipeUp();
    //}

    public void truePress(View v){
         swipeRight();
    }

    public void falsePress(View v){
         swipeLeft();
    }

    private void showResults()
    {

        Log.d("you scored", String.valueOf(correctAns));
        Log.d("out of a possible", String.valueOf(totalAns));
        resText.setText("Quiz Complete.\n You Scored "+correctAns+" out of "+totalAns+".");
        resText.setVisibility(View.VISIBLE);
        findViewById(R.id.button5).setVisibility(View.VISIBLE);
        findViewById(R.id.button4).setVisibility(View.VISIBLE);
        findViewById(R.id.button).setVisibility(View.GONE);
        findViewById(R.id.textView5).setVisibility(View.GONE);
        findViewById(R.id.button6).setVisibility(View.GONE);
    }

    public void finish(View view) {
        finish();
    }

    public void retry(View view) {
        recreate();
    }
}
