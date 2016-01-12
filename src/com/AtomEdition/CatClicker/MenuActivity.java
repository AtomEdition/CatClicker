package com.AtomEdition.CatClicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.AtomEdition.CatClicker.game.GameUtils;
import com.AtomEdition.CatClicker.promotion.FollowActivity;
import com.AtomEdition.CatClicker.promotion.PromotionButtonController;
import com.AtomEdition.KittyClicker.R;

import java.util.LinkedList;
import java.util.Random;

public class MenuActivity extends ParentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialization();
    }

    private void initialization(){
        setScreenSize();
        getHighScore();
        GameUtils.SOUNDS = getSoundsAndMusic(GameUtils.PREFERENCES_SOUNDS);
        GameUtils.MUSIC = getSoundsAndMusic(GameUtils.PREFERENCES_MUSIC);
        GameUtils.VIBRATE = getSoundsAndMusic(GameUtils.PREFERENCES_VIBRATE);
        setBackground();
        setImages();
        loadSoundPool();
        setButtonMusicText();
        setButtonSoundsText();
        setButtonVibrateText();
        loadAd();
        PromotionButtonController.getInstance(this).startTimer();
    }

    private void loadAd(){
        adService.showBanner(this);
    }

    /**
     * Opens GameActivity and closes MenuActivity.
     * @param view current activity's view.
     */
    public void onClickStart(View view) {
        if(GameUtils.VIBRATE){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(GameUtils.VIBRATE_TIME_SHORT);
        }
        if(GameUtils.SOUNDS){
            soundPool.play(soundIdTrue4, 1, 1, 0, 0, 1);
        }
        releasePlayer();
        PromotionButtonController.getInstance(this).breakTimer();
        if(GameUtils.HIGH_SCORE>5){
            toGame();
        }
        else
            setContentView(R.layout.tutorial);
    }

    public void onClickFromTutorial(View view){
        toGame();
    }

    public void toGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            releasePlayer();
            PromotionButtonController.getInstance(this).breakTimer();
            super.onBackPressed();
        }
        else
            Toast.makeText(getBaseContext(), "Press again to exit",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void setScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        GameUtils.SCREEN_HEIGHT = (display.getHeight()-convertToPx(GameUtils.INTERFACE_SIZE)-convertToPx(GameUtils.KITTY_SIZE));
        GameUtils.SCREEN_WIDTH = (display.getWidth()-convertToPx(GameUtils.KITTY_SIZE));
    }

    /**
     * Gets saved best score from android's preferences. Calling from onCreate method.
     */
    private void getHighScore(){
        SharedPreferences settings = getSharedPreferences(GameUtils.PREFERENCES_NAME, Context.MODE_PRIVATE);
        TextView textView = (TextView)findViewById(R.id.text_view_high_score);
        GameUtils.HIGH_SCORE = settings.getInt(GameUtils.PREFERENCES_HIGH_SCORE,0);
        textView.setText("Best: " + GameUtils.HIGH_SCORE);
    }

    /**
     * Convert logical id to texture id.
     * @param i logical id.
     * @return real id of texture.
     */
    public Integer getTextureById(Integer i){
        return getResources().getIdentifier("background"+i, "drawable", getPackageName());
    }

    protected void setBackground(){
        Random random = new Random();
        RelativeLayout relativeLayout;
        relativeLayout = (RelativeLayout)findViewById(R.id.menu_layout);
        relativeLayout.setBackgroundResource(getTextureById(random.nextInt(GameUtils.TEXTURES_COUNT)));
    }

    public void onImageClick(View view){
        if(GameUtils.VIBRATE){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(GameUtils.VIBRATE_TIME_MAIN_MENU);
        }
        if(GameUtils.SOUNDS){
            soundPool.play(soundIdMenu, 1, 1, 0, 0, 1);
        }
    }

    /**
     * Convert logical id to picture id.
     * @param i logical id.
     * @return real id of picture.
     */
    public Integer getImageById(Integer i){
        return getResources().getIdentifier("cat_menu"+i, "drawable", getPackageName());
    }

    public void setImages(){
        ImageView imageView;
        LinkedList<Integer> excludes = new LinkedList<Integer>();
        excludes.add(10);
        LinkedList<Integer> linkedList = GameUtils.excludeRandom(1, excludes);
        imageView = (ImageView)findViewById(R.id.menu_cat_2);
        imageView.setImageResource(getImageById(linkedList.get(0)));
        ImageButton imageButton;
        imageButton = (ImageButton)findViewById(R.id.button_start);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.paw_menu);
        imageButton.startAnimation(animation);
    }

    public void onClickOther(View view) {
        Intent intent = new Intent(this, FollowActivity.class);
        startActivity(intent);
        finish();
    }

    public void onPromotionClick(View view) {
        PromotionButtonController.getInstance(this).makeUsFamous();
    }
}
