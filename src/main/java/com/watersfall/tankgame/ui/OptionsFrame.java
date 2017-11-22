
package com.watersfall.tankgame.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.watersfall.tankgame.Main;

public class OptionsFrame extends JFrame
{
    public static final File configFile = new File(System.getProperty("user.home") 
    + File.separator + "Documents" 
    + File.separator + "My Games"
    + File.separator + "tankgame"
    + File.separator + "tankgame.txt");
    public static final File configFolder = new File(System.getProperty("user.home") 
    + File.separator + "Documents" 
    + File.separator + "My Games"
    + File.separator + "tankgame");
    private BufferedReader fileReader;
    private BufferedWriter fileWriter;
    private int musicVolume;
    private int effectsVolume;
    private JSlider sfxSlider;
    private JSlider musicSlider;
    private JLabel sfxLabel;
    private JLabel musicLabel;
    private JPanel panel;


    public OptionsFrame() throws IOException
    {
        super();
        this.setSize(Frame.SCREENWIDTH, Frame.SCREENHEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new JPanel();
        musicLabel = new JLabel();
        sfxLabel = new JLabel();
        musicSlider = new JSlider();
        sfxSlider = new JSlider();
        System.out.println(configFile.toPath().toString());
        configFolder.mkdirs();
        if(configFile.createNewFile())
        {
            fileWriter = new BufferedWriter(new FileWriter(configFile, false));
            fileWriter.append("musicVolume=100");
            fileWriter.newLine();
            fileWriter.append("effectsVolume=100");
            fileWriter.close();
            musicVolume = 100;
            effectsVolume = 100;
        }
        else
        {
            fileReader = new BufferedReader(new FileReader(configFile));
            String temp;
            temp = fileReader.readLine();
            musicVolume = Integer.parseInt(temp.substring(temp.indexOf("=") + 1));
            temp = fileReader.readLine();
            musicVolume = Integer.parseInt(temp.substring(temp.indexOf("=") + 1));
        }
        this.setLayout(null);
        musicLabel.setText("Music Volume");
        sfxLabel.setText("Effects Volume");
        musicSlider.setMaximum(100);
        musicSlider.setMinimum(0);
        sfxSlider.setMaximum(100);
        sfxSlider.setMinimum(0);
        panel.add(musicLabel);
        panel.add(musicSlider);
        panel.add(sfxLabel);
        panel.add(sfxSlider);
        this.getContentPane().add(panel);
        setUndecorated(true);
        pack();
        setVisible(true);
        toFront();
        requestFocus();
        initPanel();
    }

    private void initPanel()
    {
        
    }
}