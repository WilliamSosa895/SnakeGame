/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;
import Snake_Cliente_Servidor.*;
import java.util.List;
/**
 *
 * @author william
 */
public class LogicSnakeDTO {
    private SnakeDTO snakeDTO;
    
    public LogicSnakeDTO(List<int[]> segmentos, int r, int g, int b){
        this.snakeDTO = new SnakeDTO(segmentos, r, g, b);
    }
    
    public SnakeDTO getSnakeDTO(){
        return snakeDTO;
    }
}
