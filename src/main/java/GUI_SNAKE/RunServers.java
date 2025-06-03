/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI_SNAKE;
import Logic_SNAKE.*;
/**
 *
 * @author william
 */
public class RunServers {

    public static LogicSnakeServidor snakeServidor; 

    public static void main(String[] args) {
        snakeServidor = new LogicSnakeServidor(6000);
        ChatServidor chatServidor = new ChatServidor();
        snakeServidor.getSnakeServidor().start();
    }
}