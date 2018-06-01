package com.watersfall.tankgame.RichPresence;

import com.github.psnrigner.discordrpcjava.DiscordEventHandler;
import com.github.psnrigner.discordrpcjava.DiscordJoinRequest;
import com.github.psnrigner.discordrpcjava.DiscordRpc;
import com.github.psnrigner.discordrpcjava.ErrorCode;

public class RichPresenceHandler
{
    private DiscordRpc discordRpc;
    private DiscordEventHandler discordEventHandler;

    public RichPresenceHandler()
    {
        discordEventHandler = new DiscordEventHandler(){
        
            @Override
            public void spectateGame(String spectateSecret) 
            {
                
            }
        
            @Override
            public void ready() 
            {
                
            }
        
            @Override
            public void joinRequest(DiscordJoinRequest joinRequest) 
            {
                
            }
        
            @Override
            public void joinGame(String joinSecret) 
            {
                
            }
        
            @Override
            public void errored(ErrorCode errorCode, String message) 
            {
                
            }
        
            @Override
            public void disconnected(ErrorCode errorCode, String message) 
            {
                
            }
        };
    }
}