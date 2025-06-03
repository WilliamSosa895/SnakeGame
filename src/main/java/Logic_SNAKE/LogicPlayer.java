/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;

import Snake_Cliente_Servidor.Player;

/**
 *
 * @author william
 */
public class LogicPlayer {
    private Player player;
    
    public LogicPlayer(String name, int score){
        this.player = new Player(name, score);
    }
    
    public Player getPlayer(){
        return player;
    }
    
}
