package com.AtomEdition.KittyClicker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;
import android.widget.ImageButton;
import com.AtomEdition.KittyClicker.game.GameUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 24.11.14
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class ParentActivity extends Activity {

    protected static long back_pressed;
    final String LOG_TAG = "myLogs";
    final int MAX_STREAMS = 4;
    SoundPool soundPool;
    int soundIdTrue0, soundIdTrue1, soundIdTrue2, soundIdTrue3, soundIdTrue4;
    int soundIdFalse0, soundIdFalse1, soundIdFalse2, soundIdFalse3, soundIdFalse4;
    int soundIdMenu;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void loadSoundPool(){
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        soundIdTrue0 = soundPool.load(this, R.raw.true0, 1);
        soundIdTrue1 = soundPool.load(this, R.raw.true1, 1);
        soundIdTrue2 = soundPool.load(this, R.raw.true2, 1);
        soundIdTrue3 = soundPool.load(this, R.raw.true3, 1);
        soundIdTrue4 = soundPool.load(this, R.raw.true4, 1);
        soundIdFalse0 = soundPool.load(this, R.raw.false0, 1);
        soundIdFalse1 = soundPool.load(this, R.raw.false1, 1);
        soundIdFalse2 = soundPool.load(this, R.raw.false2, 1);
        soundIdFalse3 = soundPool.load(this, R.raw.false3, 1);
        soundIdFalse4 = soundPool.load(this, R.raw.false4, 1);
        soundIdMenu = soundPool.load(this, R.raw.menu1, 1);
    }

    protected void playRandomTrue(){
        Random random = new Random();
        Integer rnd = random.nextInt(5);
        switch (rnd){
            case 0:
                soundPool.play(soundIdTrue0, 1, 1, 0, 0, 1);
                break;
            case 1:
                soundPool.play(soundIdTrue1, 1, 1, 0, 0, 1);
                break;
            case 2:
                soundPool.play(soundIdTrue2, 1, 1, 0, 0, 1);
                break;
            case 3:
                soundPool.play(soundIdTrue3, 1, 1, 0, 0, 1);
                break;
            case 4:
                soundPool.play(soundIdTrue4, 1, 1, 0, 0, 1);
                break;
        }
    }

    protected void playRandomFalse(){
        Random random = new Random();
        Integer rnd = random.nextInt(5);
        switch (rnd){
            case 0:
                soundPool.play(soundIdFalse0, 1, 1, 0, 0, 1);
                break;
            case 1:
                soundPool.play(soundIdFalse1, 1, 1, 0, 0, 1);
                break;
            case 2:
                soundPool.play(soundIdFalse2, 1, 1, 0, 0, 1);
                break;
            case 3:
                soundPool.play(soundIdFalse3, 1, 1, 0, 0, 1);
                break;
            case 4:
                soundPool.play(soundIdFalse4, 1, 1, 0, 0, 1);
                break;
        }
    }

    /**
     * Convert dimension pixels value to real pixels. Provides same object sizes on different screens.
     * @param valueDp dimension pixels value.
     * @return converted to real pixels value.
     */
    public int convertToPx(Integer valueDp){
        return (int)(valueDp * getApplicationContext().getResources().getDisplayMetrics().density);
    }

    protected boolean getSoundsAndMusic(String param){
        SharedPreferences settings = getSharedPreferences(GameUtils.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(!settings.contains(param)){
            editor.putBoolean(param, true);
            editor.commit();
        }
        return settings.getBoolean(param, true);
    }

    protected void setButtonSoundsText(){
        ImageButton imageButton;
        imageButton = (ImageButton)findViewById(R.id.button_sounds);
        if(GameUtils.SOUNDS)
            imageButton.setBackgroundResource(R.drawable.button_sound_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_sound_off);
    }

    protected void setButtonMusicText(){
        ImageButton imageButton;
        imageButton = (ImageButton)findViewById(R.id.button_music);
        if(GameUtils.MUSIC)
            imageButton.setBackgroundResource(R.drawable.button_music_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_music_off);
    }

    protected void setButtonVibrateText(){
        ImageButton imageButton;
        imageButton = (ImageButton)findViewById(R.id.button_vibrate);
        if(GameUtils.VIBRATE)
            imageButton.setBackgroundResource(R.drawable.button_vibro_on);
        else
            imageButton.setBackgroundResource(R.drawable.button_vibro_off);
    }

    public void clickSoundsAndMusic(String param) {
        SharedPreferences settings = getSharedPreferences(GameUtils.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(param, !settings.getBoolean(param, true));
        editor.commit();
    }

    public void onSoundsClick(View view){
        clickSoundsAndMusic(GameUtils.PREFERENCES_SOUNDS);
        GameUtils.SOUNDS = !GameUtils.SOUNDS;
        setButtonSoundsText();
    }

    public void onMusicClick(View view){
        clickSoundsAndMusic(GameUtils.PREFERENCES_MUSIC);
        GameUtils.MUSIC = !GameUtils.MUSIC;
        if(GameUtils.MUSIC)
            mediaPlayer.setVolume(1,1);
        else
            mediaPlayer.setVolume(0,0);
        setButtonMusicText();
    }

    public void onVibrateClick(View view){
        clickSoundsAndMusic(GameUtils.PREFERENCES_VIBRATE);
        GameUtils.VIBRATE = !GameUtils.VIBRATE;
        setButtonVibrateText();
    }

    protected void startPlayer(Integer songId){
        mediaPlayer = MediaPlayer.create(this, songId);
        mediaPlayer.setLooping(true);
        if(GameUtils.MUSIC)
            mediaPlayer.setVolume(1,1);
        else
            mediaPlayer.setVolume(0,0);
        mediaPlayer.start();
    }

    protected void releasePlayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
