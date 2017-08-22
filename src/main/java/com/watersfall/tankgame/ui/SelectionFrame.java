/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.game.Frame;
import data.TankData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JComboBox player1Box, player2Box;
    private JLabel player1, player2, player1Info, player2Info;
    private JPanel panel;
    private JButton start;
    public Frame frame;
    
    //The things that will be passed into the main game
    private int player1Selection, player2Selection;
    private ArrayList<TankData> tankArray;
    
    public SelectionFrame() throws IOException
    {
        //Standard JFrame things
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setLayout(null);
        
        start = new JButton("<html><span style='font-size:20px'>Start!</span></html>");
        player1Info = new JLabel();
        player2Info = new JLabel();
        player1 = new JLabel();
        player2 = new JLabel();
        player1Box = new JComboBox();
        player2Box = new JComboBox();
        player1.setText("<html><span style='font-size:20px'>Player 1</span></html>");
        player2.setText("<html><span style='font-size:20px'>Player 2</span></html>");
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
        start.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                player1Selection = player1Box.getSelectedIndex();
                player2Selection = player2Box.getSelectedIndex();
                setVisible(false);
                try 
                {
                    frame = new Frame(player1Selection, player2Selection, tankArray);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(SelectionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        panel.add(start);
        panel.add(player1);
        panel.add(player2);
        panel.add(player1Box);
        panel.add(player2Box);
        panel.add(player1Info);
        panel.add(player2Info);
        
        //Finishing and displaying the JFrame
        getContentPane().add(panel);
        pack();
        setVisible(true);
        loadTanks();
        initFrame();
    }
    
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
    }
    
    public void loadTanks() throws IOException
    {
        tankArray = new ArrayList<TankData>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Info/TANKS.txt")));
        String line;
        while((line = reader.readLine()) != null)
        {
            if(!line.startsWith("#"))
            {
                tankArray.add(new TankData(line));
                player1Box.addItem(tankArray.get(tankArray.size() - 1).tankData[0]);
                player2Box.addItem(tankArray.get(tankArray.size() - 1).tankData[0]);
            }
        }
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
}
