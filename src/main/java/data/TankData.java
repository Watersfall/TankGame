/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Christopher
 */
public class TankData {
    
    public String[] tankData;
    private final int dataLength = 5;
    
    public TankData(String string)
    {
        tankData = new String[dataLength];
        for(int i = 0; i < dataLength; i++)
        {
            tankData[i] = string.substring(0, string.indexOf(","));
            string = string.substring(string.indexOf(",") + 2);
        }
    }
    
}
