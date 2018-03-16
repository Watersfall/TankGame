package com.watersfall.tankgame.game;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.sampled.FloatControl;
import org.newdawn.easyogg.OggClip;

public class Sound
{
    public static final int SONG_COUNT = 3;

    private HashMap<String, OggClip> sounds;
    private HashMap<String, OggClip> music;
    private String playingSong;
    
    public Sound() throws IOException
    {
        sounds = new HashMap<String, OggClip>();
        music = new HashMap<String, OggClip>();

        sounds.put("shoot", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/shoot.ogg"))));
        sounds.put("bounce", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/bounce.ogg"))));
        sounds.put("death", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/death.ogg"))));
        sounds.put("hit", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/hit.ogg"))));
        music.put("theme", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/Menus/Theme.ogg"))));
        music.put("GER", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/Anthems/GER.ogg"))));
        music.put("UK", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/Anthems/UK.ogg"))));
        music.put("US", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/Anthems/US.ogg"))));
        music.put("USSR", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/Anthems/USSR.ogg"))));
        music.put("1", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/BattleSongs/BattleSong1.ogg"))));
        music.put("2", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/BattleSongs/BattleSong2.ogg"))));
        music.put("3", new OggClip(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/BattleSongs/BattleSong3.ogg"))));
    }

    public void playSound(String name)
    {
        sounds.get(name).play();
    }

    public void playMusic(String name)
    {
        music.get(name).play();
    }

    public void playRandomMusic()
    {
        playingSong = Integer.toString((int)Math.random() * SONG_COUNT);
        this.playMusic(playingSong);
    }

    public void stopMusic(String name)
    {
        music.get(name).stop();
    }

    public void setEffectsVolume(float gain)
    {
        gain = gain / 100;
        if (gain > 1)
            gain = 1;
        if (gain < 0)
            gain = 0;
        sounds.get("bounce").setGain(gain);
        sounds.get("death").setGain(gain);
        sounds.get("hit").setGain(gain);
        sounds.get("shoot").setGain(gain);
    }

    public void setMusicVolume(float gain)
    {
        if(gain > 0)
            gain = (float) Math.log10(gain / 10);
        if(gain <= 0)
            gain = 0;
        music.get("theme").setGain(gain);
        music.get("GER").setGain(gain);
        music.get("UK").setGain(gain);
        music.get("US").setGain(gain);
        music.get("USSR").setGain(gain);
        music.get("1").setGain(gain);
        music.get("2").setGain(gain);
        music.get("3").setGain(gain);
    }
}