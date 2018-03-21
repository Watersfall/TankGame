package com.watersfall.tankgame.Sound;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Wraps an OggClip to avoid the threading bugs in OggClip. The wrapper can be
 * played more than once at a time without problem.
 */
public class OggClipWrapper {
    
    private OggClip clip;
    private String resource;
    private float gain;
    private boolean isLooping = false;
    
    public OggClipWrapper(String resource, float gain) {
        this.resource = resource;
        this.gain = gain;
    }
    
    public void play() {
        if (clip != null && isLooping) {
            clip.stop();
        }
        isLooping = false;

        try {
            clip = new OggClip(new BufferedInputStream(getClass().getResourceAsStream(resource)));
            clip.setGain(gain);
            clip.play();
        } catch (IOException e) {
            e.printStackTrace();
            clip = null;
        }
    }
    
    public void stop() {
        if (clip != null) {
            isLooping = false;
            clip.stop();
            clip = null;
        }
    }
    
    public void loop() {
        if (clip != null && isLooping) {
            clip.stop();
        }        
        
        try {
            clip = new OggClip(new BufferedInputStream(getClass().getResourceAsStream(resource)));
            clip.setGain(gain);
            clip.loop();
            isLooping = true;
        } catch (IOException e) {
            e.printStackTrace();
            clip = null;
        }
    }
    
    public void pause() {
        if (clip != null) {
            clip.pause();
        }
    }
    
    public void resume() {
        if (clip != null) {
            clip.resume();
        }
    }

    public void setGain(float gain)
    {
        this.gain = gain;
        if(this.clip != null)
            clip.setGain(gain);
    }
}