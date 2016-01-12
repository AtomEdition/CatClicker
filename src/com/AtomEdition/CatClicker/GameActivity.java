package com.AtomEdition.CatClicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.AtomEdition.CatClicker.ad.AdService;
import com.AtomEdition.CatClicker.game.Kitty;
import com.AtomEdition.CatClicker.game.GameUtils;
import com.AtomEdition.KittyClicker.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 16.09.14
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends ParentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adService.loadInterstitial(this);
        setContentView(R.layout.game);
        initialization();
    }

    private void initialization(){
        startPlayer(R.raw.music_game);
        loadSoundPool();
        setImages();
        setBackground();
        setSettingsImages();
        setKittyCount(GameUtils.START_COUNT - 1);
        generate(getKittyCount());
        countDownTimer = new CountDownTimer(GameUtils.COUNT_DOWN_VALUE, 40) {
            public void onTick(long millisUntilFinished) {
                TextView textView;
                textView = (TextView)findViewById(R.id.timerTextView);
                textView.setText("seconds remaining: " + (millisUntilFinished / 1000 + 1));
                placeKitty();
                switch(flagClicked){
                    case 2:
                        flagClicked = 0;
                        this.cancel();
                        this.start();
                        break;
                }
            }
            public void onFinish() {
                toScorePage();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            countDownTimer.cancel();
            releasePlayer();
            setHighScore();
            GameUtils.SCORE = 0;
            toMenu();
            adService.displayInterstitial();
        }
        else
            Toast.makeText(getBaseContext(), "Press again to finish the game",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void toMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private CountDownTimer countDownTimer;
    private LinkedList<Kitty> kitties = new LinkedList<Kitty>();
    private Integer kittyCount;
    private Integer flagClicked = 0;

    public LinkedList<Kitty> getKitties() {
        return kitties;
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
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundResource(getTextureById(random.nextInt(GameUtils.TEXTURES_COUNT)));
    }

    public void setKitties(LinkedList<Kitty> kitties) {
        this.kitties = kitties;
    }

    /**
     * Gets count of objects at the screen. It's calling in onCreate method, so the count of objects at the first level
     * sets there. On every call increases the value of objects if the current count less then 20.
     * @return new screen objects count.
     */
    public Integer getKittyCount() {
        if (this.kittyCount <20)
            this.kittyCount++;
        return kittyCount;
    }

    public void setKittyCount(Integer kittyCount) {
        this.kittyCount = kittyCount;
    }

    public void setScoreText(){
        TextView textView;
        textView = (TextView)findViewById(R.id.scoreTextView);
        textView.setText(Integer.toString(GameUtils.SCORE));
    }

    /**
     * Close GameActivity and open MenuActivity
     */
    public void toScorePage(){
        mediaPlayer.stop();
        setHighScore();
        GameUtils.SCORE = 0;
        toMenu();
        adService.displayInterstitial();
    }

    /**
     * Checks gained score and compares it with old one.
     */
    private void setHighScore(){
        SharedPreferences settings = getSharedPreferences(GameUtils.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(!settings.contains(GameUtils.PREFERENCES_HIGH_SCORE)){
            editor.putInt(GameUtils.PREFERENCES_HIGH_SCORE, GameUtils.SCORE);
            editor.commit();
        }
        else if(settings.getInt(GameUtils.PREFERENCES_HIGH_SCORE, 0)<GameUtils.SCORE){
                editor.putInt(GameUtils.PREFERENCES_HIGH_SCORE, GameUtils.SCORE);
                editor.commit();
            }
    }

    /**
     * Convert logical (backend class) id to real (android object) id.
     * @param i logical id.
     * @return real id.
     */
    public Integer getImageButtonById(Integer i){
        return getResources().getIdentifier("kitty"+i, "id", getPackageName());
    }

    /**
     * Convert real (android object) id to logical (backend class) id.
     * @param imageButtonId real id.
     * @return logical id.
     */
    private Integer getIdByImageButton(Integer imageButtonId){
        Integer i=0;
        switch(imageButtonId){
            case R.id.kitty0:
                i=0;
                break;
            case R.id.kitty1:
                i=1;
                break;
            case R.id.kitty2:
                i=2;
                break;
            case R.id.kitty3:
                i=3;
                break;
            case R.id.kitty4:
                i=4;
                break;
            case R.id.kitty5:
                i=5;
                break;
            case R.id.kitty6:
                i=6;
                break;
            case R.id.kitty7:
                i=7;
                break;
            case R.id.kitty8:
                i=8;
                break;
            case R.id.kitty9:
                i=9;
                break;
            case R.id.kitty10:
                i=10;
                break;
            case R.id.kitty11:
                i=11;
                break;
            case R.id.kitty12:
                i=12;
                break;
            case R.id.kitty13:
                i=13;
                break;
            case R.id.kitty14:
                i=14;
                break;
            case R.id.kitty15:
                i=15;
                break;
            case R.id.kitty16:
                i=16;
                break;
            case R.id.kitty17:
                i=17;
                break;
            case R.id.kitty18:
                i=18;
                break;
            case R.id.kitty19:
                i=19;
                break;
        }
        return i;
    }

    /**
     * Method that place object at the screen depending on backend classes. Calling in countDownTimer, so this method
     * sets objects to move.
     */
    public void placeKitty(){
        int i=0;
        if (getKitties().size()>=GameUtils.COUNT_TO_START_MOVE)
        for (Kitty kitty : getKitties()){
            GameUtils.setMoving(kitty);
            ImageButton imageButton;
            imageButton = (ImageButton)findViewById(getImageButtonById(i));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(convertToPx(GameUtils.KITTY_SIZE), convertToPx(GameUtils.KITTY_SIZE));
            layoutParams.setMargins(kitty.getX(), kitty.getY(), 0, 0);
            imageButton.setLayoutParams(layoutParams);
            i++;
        }
    }

    /**
     * Places screen components depending on backend classes. Calls generateKitties, so uses only once per level while
     * generating it.
     * @param kittyCount number of objects generating on the screen.
     */
    public void generate(Integer kittyCount){
        setKitties(GameUtils.generateKitties(kittyCount));
        int i=0;
        Animation rotate_game = AnimationUtils.loadAnimation(this, R.anim.rotate_game);
        Animation fade_in_game = AnimationUtils.loadAnimation(this, R.anim.fade_in_game);
        for (Kitty kitty : getKitties()){
            ImageButton imageButton;
            imageButton = (ImageButton)findViewById(getImageButtonById(i));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(GameUtils.KITTY_SIZE, convertToPx(GameUtils.KITTY_SIZE));
            layoutParams.setMargins(kitty.getX(), kitty.getY(), 0, 0);
            imageButton.clearAnimation();
            imageButton.setLayoutParams(layoutParams);
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setClickable(true);
            imageButton.startAnimation(rotate_game);
            if(kitty.isFlag()){
                ImageButton imageButtonColorToClick;
                imageButtonColorToClick = (ImageButton)findViewById(R.id.colorToClick);
                imageButtonColorToClick.setBackgroundDrawable(imageButton.getBackground());
            }
            i++;
        }
        setScoreText();
    }

    /**
     * Checks screen object that has been clicked. Increases score (if object had true value) or destroying object (if
     * false value). Requires in countDownTimer.
     * @param kittyId id of clicked object.
     */
    public void checkFlag(Integer kittyId){
        if(getKitties().get(kittyId).isFlag()){
            flagClicked = 2;
            GameUtils.SCORE++;
            playSoundTrue();
            generate(getKittyCount());
        }
        else {
            flagClicked = 1;
            playSoundFalse();
            kittyDestroy(kittyId);
        }
    }

    /**
     * Destroys wrong clicked object. Also can be addict with animation of destroying and/or sounds.
     * @param kittyId id of destroying object.
     */
    public void kittyDestroy(Integer kittyId){
        final ImageButton imageButton;
        if(GameUtils.VIBRATE){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(GameUtils.VIBRATE_TIME);
        }
        Animation fade_out_game = AnimationUtils.loadAnimation(this, R.anim.fade_out_game);
        imageButton = (ImageButton)findViewById(getImageButtonById(kittyId));
        imageButton.clearAnimation();
        imageButton.setAnimation(fade_out_game);
        imageButton.setVisibility(View.INVISIBLE);
        imageButton.setClickable(false);
    }

    /**
     * Maybe useless method. But it had purpose once and maybe will have in future.
     * @param view current activity's view.
     */
    public void onKittyClick(View view){
        checkFlag(getIdByImageButton(view.getId()));
    }

    /**
     * Playing sound when correct object was clicked
     */
    public void playSoundTrue(){
        if(GameUtils.SOUNDS){
            playRandomTrue();
        }
    }

    /**
     * Playing sound when wrong object was clicked
     */
    public void playSoundFalse(){
        if(GameUtils.SOUNDS){
            playRandomFalse();
        }
    }

    /**
     * Convert logical id to picture id.
     * @param i logical id.
     * @return real id of picture.
     */
    private Integer getImageById(Integer i){
        return getResources().getIdentifier("cat"+i, "drawable", getPackageName());
    }

    private void setImages(){
        LinkedList<Integer> linkedList = GameUtils.excludeRandom(20);
        for(int i=0; i<20; i++){
            ImageView imageView;
            imageView = (ImageView)findViewById(getImageButtonById(i));
            imageView.setBackgroundResource(getImageById(linkedList.get(i)));
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    private void setSettingsImages(){
        ImageButton imageButton;
        imageButton = (ImageButton)findViewById(R.id.button_vibrate);
        if(GameUtils.VIBRATE)
            imageButton.setBackgroundResource(R.drawable.button_vibro_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_vibro_off);
        imageButton = (ImageButton)findViewById(R.id.button_sounds);
        if(GameUtils.SOUNDS)
            imageButton.setBackgroundResource(R.drawable.button_sound_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_sound_off);
        imageButton = (ImageButton)findViewById(R.id.button_music);
        if(GameUtils.MUSIC)
            imageButton.setBackgroundResource(R.drawable.button_music_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_music_off);
    }
}
