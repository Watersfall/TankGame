/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.game.Frame;
import com.watersfall.tankgame.data.MapData;
import com.watersfall.tankgame.data.TankData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Christopher
 */
public class SelectionFrame extends JFrame {
    
    //The things that will be added to the frame
    private JComboBox player1Box, player2Box, mapBox;
    private JLabel player1, player2, player1Info, player2Info;
    private JPanel panel;
    private JButton start;
    public Frame frame;
    
    //The things that will be passed into the main game
    private int player1Selection, player2Selection, mapSelection;
    private ArrayList<TankData> tankArray;
    private ArrayList<MapData> mapArray;
    
    //SelectionFrame is the starting Frame for the game
    //This is where players will pick their vehicles, as well as be able to access the menu and settings for the game
    public SelectionFrame() throws IOException
    {
        //Standard JFrame things
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Creating the panel that everything else will go on
        //No layout allows manually setting the position and size for all the componenets
        panel = new JPanel();
        panel.setLayout(null);
        
        start = new JButton("<html><span style='font-size:20px'>Start!</span></html>");
        player1Info = new JLabel();
        player2Info = new JLabel();
        player1 = new JLabel();
        player2 = new JLabel();
        player1Box = new JComboBox();
        player2Box = new JComboBox();
        mapBox = new JComboBox();
        player1.setText("<html><span style='font-size:20px'>Player 1</span></html>");
        player2.setText("<html><span style='font-size:20px'>Player 2</span></html>");
        
        //The action listeners that the components use
        
        //Action listener to trigger when the player 1 tank selection box has been changed
        //This causes it to update the tank info box with the data for the selected tank
        player1Box.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int i = player1Box.getSelectedIndex();
                player1Info.setText("<html><span style='font-size:20px'>" 
                    + tankArray.get(i).tankData[0] + "<br>Front Armor: " 
                    + tankArray.get(i).tankData[1] + " mm<br>Side Armor: " 
                    + tankArray.get(i).tankData[2] + "mm<br>Rear Armor: " 
                    + tankArray.get(i).tankData[3] + "mm<br> Gun Penetration: " 
                    + tankArray.get(i).tankData[4] + "mm<br>" 
                            +  "</span></html>");
            }
        });
        
        //Same as the last one but for the player 2 box
        player2Box.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int i = player2Box.getSelectedIndex();
                player2Info.setText("<html><span style='font-size:20px'>" 
                    + tankArray.get(i).tankData[0] + "<br>Front Armor: " 
                    + tankArray.get(i).tankData[1] + " mm<br>Side Armor: " 
                    + tankArray.get(i).tankData[2] + "mm<br>Rear Armor: " 
                    + tankArray.get(i).tankData[3] + "mm<br> Gun Penetration: " 
                    + tankArray.get(i).tankData[4] + "mm<br>" 
                            +  "</span></html>");
            }
        });
        
        //Action listener for the start button
        //It sets the player1Selection and player2Selection variables to the indexes of the their boxes
        //It also makes the selection form invisible and opens the main frame
        start.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                player1Selection = player1Box.getSelectedIndex();
                player2Selection = player2Box.getSelectedIndex();
                mapSelection = mapBox.getSelectedIndex();
                setVisible(false);
                try 
                {
                    frame = new Frame(player1Selection, player2Selection, tankArray, mapSelection, mapArray);
                } 
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) 
                {
                    Logger.getLogger(SelectionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Adding the components to the panel
        panel.add(start);
        panel.add(player1);
        panel.add(player2);
        panel.add(player1Box);
        panel.add(player2Box);
        panel.add(player1Info);
        panel.add(player2Info);
        panel.add(mapBox);
        
        //Finishing and displaying the JFrame
        getContentPane().add(panel);
        pack();
        setVisible(true);
        
        //Calling the two local functions in this class to further set up and display
        loadTanks();
        loadMaps();
        initFrame();
    }
    
    //Method to set the location and sizes of all the components
    public void initFrame()
    {
        //Player 1 stuff, setting size and location
        player1Info.setSize(400, 400);
        player1.setSize(200, 35);
        player1Box.setSize(200, 25);
        player1Info.setLocation(this.getWidth() / 3, this.getHeight() / 2 - this.getHeight() / 4);
        player1.setLocation(this.getWidth() / 3, this.getHeight() / 2 - this.getHeight() / 4);
        player1Box.setLocation(this.getWidth() / 3, this.getHeight() / 2 - this.getHeight() / 5);
        
        
        //Player 2 stuff, setting size and location
        player2Info.setSize(400, 400);
        player2.setSize(200, 35);
        player2Box.setSize(200, 25);
        player2Info.setLocation(this.getWidth() - this.getWidth() / 3 - player2.getWidth(), this.getHeight() / 2 - this.getHeight() / 4);
        player2.setLocation(this.getWidth() - this.getWidth() / 3 - player2.getWidth(), this.getHeight() / 2 - this.getHeight() / 4);
        player2Box.setLocation(this.getWidth() - this.getWidth() / 3 - player2Box.getWidth(), this.getHeight() / 2 - this.getHeight() / 5);
        
        //Start button stuff, setting size and location
        start.setSize(150, 75);
        start.setLocation(this.getWidth() / 2 - start.getWidth() / 2, this.getHeight() - this.getHeight() / 5);

        //Setting the map boxes size and location
        mapBox.setSize(200, 25);
        mapBox.setLocation(this.getWidth() / 2 - 100, this.getHeight() - this.getHeight() / 4);
    }
    
    //Method to load all the tank data in from the TANKS.txt file in the jar resources
    //Format for the tank information is in the top of the file
    public void loadTanks() throws IOException
    {
        tankArray = new ArrayList<TankData>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Info/TANKS.txt")));
        String line;
        while((line = reader.readLine()) != null)
        {
            //Lines starting with # are comments, and should be ignored
            if(!line.startsWith("#"))
            {
                //Adding the tank data loaded to the array
                tankArray.add(new TankData(line));
                
                //Adding the name of the tank to the two selection boxes, so it shows for tank selection
                player1Box.addItem(tankArray.get(tankArray.size() - 1).tankData[0]);
                player2Box.addItem(tankArray.get(tankArray.size() - 1).tankData[0]);
            }
        }
        
        //HTML and CSS formatting can be used in text areas
        player1Info.setText("<html><span style='font-size:20px'>" 
                + tankArray.get(0).tankData[0] + "<br>Front Armor: " 
                + tankArray.get(0).tankData[1] + " mm<br>Side Armor: " 
                + tankArray.get(0).tankData[2] + "mm<br>Rear Armor: " 
                + tankArray.get(0).tankData[3] + "mm<br> Gun Penetration: " 
                + tankArray.get(0).tankData[4] + "mm<br>" 
                        +  "</span></html>");
        player2Info.setText("<html><span style='font-size:20px'>" 
                + tankArray.get(0).tankData[0] + "<br>Front Armor: " 
                + tankArray.get(0).tankData[1] + " mm<br>Side Armor: " 
                + tankArray.get(0).tankData[2] + "mm<br>Rear Armor: " 
                + tankArray.get(0).tankData[3] + "mm<br> Gun Penetration: " 
                + tankArray.get(0).tankData[4] + "mm<br>" 
                        +  "</span></html>");
    }

    public void loadMaps() throws IOException
    {
        mapArray = new ArrayList<MapData>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Info/MAPS.txt")));
        String line;
        while((line = reader.readLine()) != null)
        {
            //Lines starting with # are comments, and should be ignored
            if(!line.startsWith("#"))
            {
                //Adding the tank data loaded to the array
                mapArray.add(new MapData(line));
                System.out.println("adding map");
                
                //Adding the name of the tank to the two selection boxes, so it shows for tank selection
                mapBox.addItem(mapArray.get(mapArray.size() - 1).name);
            }
        }
    }
}
