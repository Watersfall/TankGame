package com.watersfall.tankgame.Sound;

import java.io.IOException;
import java.util.HashMap;

public class Sound
{
    public static final int SONG_COUNT = 3;

    private HashMap<String, OggClipWrapper> sounds;
    private HashMap<String, OggClipWrapper> music;
    private String playingSong;
    
    public Sound() throws IOException
    {
        sounds = new HashMap<String, OggClipWrapper>();
        music = new HashMap<String, OggClipWrapper>();

        sounds.put("shoot", new OggClipWrapper("/Sounds/Gameplay/shoot.ogg", 1));
        sounds.put("bounce", new OggClipWrapper("/Sounds/Gameplay/bounce.ogg", 1));
        sounds.put("death", new OggClipWrapper("/Sounds/Gameplay/death.ogg", 1));
        sounds.put("hit", new OggClipWrapper("/Sounds/Gameplay/hit.ogg", 1));
        music.put("theme", new OggClipWrapper("/Sounds/Music/Menus/Theme.ogg", 1));
        music.put("GER", new OggClipWrapper("/Sounds/Music/Anthems/GER.ogg", 1));
        music.put("UK", new OggClipWrapper("/Sounds/Music/Anthems/UK.ogg", 1));
        music.put("US", new OggClipWrapper("/Sounds/Music/Anthems/US.ogg", 1));
        music.put("USSR", new OggClipWrapper("/Sounds/Music/Anthems/USSR.ogg", 1));
        music.put("1", new OggClipWrapper("/Sounds/Music/BattleSongs/BattleSong1.ogg", 1));
        music.put("2", new OggClipWrapper("/Sounds/Music/BattleSongs/BattleSong2.ogg", 1));
        music.put("3", new OggClipWrapper("/Sounds/Music/BattleSongs/BattleSong3.ogg", 1));
    }

    public void playSound(String name)
    {
        sounds.get(name).play();
    }

    public void playMusic(String name)
    {
        playingSong = name;
        System.out.println(playingSong);
        music.get(playingSong).play();
    }

    public void playRandomMusic()
    {
        playingSong = Integer.toString((int)(Math.random() * SONG_COUNT) + 1);
        this.playMusic(playingSong);
    }

    public void stopMusic(String name)
    {
        music.get(name).stop();
    }

    public void stopMusic()
    {
        music.get(playingSong).stop();
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