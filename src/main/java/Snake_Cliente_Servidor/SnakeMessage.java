/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake_Cliente_Servidor;

import java.io.Serializable;

/**
 *
 * @author william
 */
public class SnakeMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        CONNECT,        
        DISCONNECT,     
        DIRECTION,      
        STATE_UPDATE,   
        GAME_OVER       
    }

    private Type type;
    private String playerName; 
    private String direction;  
    private GameState state;   

    public SnakeMessage(Type type, String playerName) {
        this.type = type;
        this.playerName = playerName;
    }

    public SnakeMessage(Type type, String playerName, String direction) {
        this.type = type;
        this.playerName = playerName;
        this.direction = direction;
    }

    public SnakeMessage(Type type, GameState state) {
        this.type  = type;
        this.state = state;
    }

    public SnakeMessage(Type type, String winnerName, boolean dummy) {
        this.type = type;
        this.playerName = winnerName;
    }

    public Type getType() {
        return type;
    }
    public String getPlayerName() {
        return playerName;
    }
    public String getDirection() {
        return direction;
    }
    public GameState getState() {
        return state;
    }
}