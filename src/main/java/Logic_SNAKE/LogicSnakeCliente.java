/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;
import Snake_Cliente_Servidor.SnakeCliente;

/**
 *
 * @author william
 */
public class LogicSnakeCliente {
    private SnakeCliente snakeCliente;
    
    public LogicSnakeCliente(){
        this.snakeCliente = new SnakeCliente();
    }
    
    public SnakeCliente getSnakeCliente(){
        return snakeCliente;
    }
    
}
