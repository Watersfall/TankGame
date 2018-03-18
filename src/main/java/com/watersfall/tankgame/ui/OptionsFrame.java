
package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    private JButton exitButton;
    private JButton returnButton;
    private JButton settingsButton;
    private JButton quitButton;


    public OptionsFrame() throws IOException
    {
        super();
        this.setSize(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new JPanel();
        musicLabel = new JLabel();
        sfxLabel = new JLabel();
        musicSlider = new JSlider();
        sfxSlider = new JSlider();
        exitButton = new JButton();
        returnButton = new JButton();
        settingsButton = new JButton();
        quitButton = new JButton();
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
            effectsVolume = Integer.parseInt(temp.substring(temp.indexOf("=") + 1));
        }

        panel.setLayout(null);
        musicLabel.setText("Music Volume");
        sfxLabel.setText("Effects Volume");
        musicSlider.setMaximum(100);
        musicSlider.setMinimum(0);
        musicSlider.setValue(musicVolume);
        sfxSlider.setMaximum(100);
        sfxSlider.setMinimum(0);
        sfxSlider.setValue(effectsVolume);
        exitButton.setText("Save & Exit");
        
        musicSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                musicVolume = musicSlider.getValue();
                Main.sound.setMusicVolume((float)musicVolume);
            }
        });

        sfxSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                effectsVolume = sfxSlider.getValue();
                Main.sound.setEffectsVolume((float)effectsVolume);
            }
        });

        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    fileWriter = new BufferedWriter(new FileWriter(configFile, false));
                    fileWriter.append("musicVolume=" + musicVolume);
                    fileWriter.newLine();
                    fileWriter.append("effectsVolume=" + effectsVolume);
                    fileWriter.close();
                    quitButton.setVisible(true);
                    returnButton.setVisible(true);
                    settingsButton.setVisible(true);
                    
                    musicLabel.setVisible(false);
                    musicSlider.setVisible(false);
                    sfxLabel.setVisible(false);
                    sfxSlider.setVisible(false);
                    exitButton.setVisible(false);
                }
                catch(IOException f)
                {
                    System.out.println(f.getMessage());
                }
            }
        });

        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        settingsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                musicLabel.setVisible(true);
                musicSlider.setVisible(true);
                sfxLabel.setVisible(true);
                sfxSlider.setVisible(true);
                exitButton.setVisible(true);
                
                quitButton.setVisible(false);
                returnButton.setVisible(false);
                settingsButton.setVisible(false);
            }
        });

        panel.add(musicLabel);
        panel.add(musicSlider);
        panel.add(sfxLabel);
        panel.add(sfxSlider);
        panel.add(exitButton);
        panel.add(quitButton);
        panel.add(returnButton);
        panel.add(settingsButton);
        setUndecorated(true);
        this.getContentPane().add(panel);
        this.setBackground(new Color(0, 0, 0, 0));
        panel.setBackground(new Color(127, 127, 127, 127));
        this.setAlwaysOnTop(true);
        pack();
        setVisible(false);
        toFront();
        requestFocus();
        initMenu();
        initSettings();
        Main.sound.setEffectsVolume((float)effectsVolume);
        Main.sound.setMusicVolume((float)musicVolume);
    }

    private void initSettings()
    {
        musicLabel.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        musicLabel.setForeground(new Color(0, 0, 0, 255));
        musicLabel.setLocation((int)(150 * Constants.SCALE_X), (int)(150 * Constants.SCALE_Y));
        musicLabel.setSize(10 * Constants.FONTSIZE, 100);

        musicSlider.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        musicSlider.setForeground(new Color(0, 0, 0, 255));
        musicSlider.setBackground(new Color(0, 0, 0, 0));
        musicSlider.setLocation((int)(150 * Constants.SCALE_X), (int)(205 * Constants.SCALE_Y));
        musicSlider.setSize((int)(200 * Constants.SCALE_X), 100);

        sfxLabel.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        sfxLabel.setForeground(new Color(0, 0, 0, 255));
        sfxLabel.setLocation((int)(150 * Constants.SCALE_X), (int)(300 * Constants.SCALE_Y));
        sfxLabel.setSize(10 * Constants.FONTSIZE, 100);

        sfxSlider.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        sfxSlider.setForeground(new Color(0, 0, 0, 255));
        sfxSlider.setBackground(new Color(0, 0, 0, 0));
        sfxSlider.setLocation((int)(150 * Constants.SCALE_X), (int)(355 * Constants.SCALE_Y));
        sfxSlider.setSize((int)(200 * Constants.SCALE_X), 100);

        exitButton.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2));
        exitButton.setLocation((int)(1650 * Constants.SCALE_X), (int)(950 * Constants.SCALE_Y));
        exitButton.setSize((int)(200 * Constants.SCALE_X), (int)(50 * Constants.SCALE_Y));
        
        musicLabel.setVisible(false);
        musicSlider.setVisible(false);
        sfxLabel.setVisible(false);
        sfxSlider.setVisible(false);
        exitButton.setVisible(false);
    }

    public void initMenu()
    {
        returnButton.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        returnButton.setSize((int)(350 * Constants.SCALE_X), (int)(125 * Constants.SCALE_Y));
        returnButton.setLocation((int)(1920 / 2 * Constants.SCALE_X - (returnButton.getWidth() / 2)), (int)(300 * Constants.SCALE_Y));
        returnButton.setText("Return to Game");

        settingsButton.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        settingsButton.setSize((int)(350 * Constants.SCALE_X), (int)(125 * Constants.SCALE_Y));
        settingsButton.setLocation((int)(1920 / 2 * Constants.SCALE_X - (settingsButton.getWidth() / 2)), (int)(500 * Constants.SCALE_Y));
        settingsButton.setText("Settings");

        quitButton.setFont(new Font("Arial", Font.PLAIN, Constants.FONTSIZE / 2)); 
        quitButton.setSize((int)(350 * Constants.SCALE_X), (int)(125 * Constants.SCALE_Y));
        quitButton.setLocation((int)(1920 / 2 * Constants.SCALE_X - (quitButton.getWidth() / 2)), (int)(700 * Constants.SCALE_Y));
        quitButton.setText("Exit Game");
    }
}