
package Logic_SNAKE;
import Snake_Cliente_Servidor.*;
import Snake_Cliente_Servidor.SnakeMessage.Type;
/**
 *
 * @author william
 */
public class LogicSnakeMessage {
    private SnakeMessage snakeMessage;
    
    public LogicSnakeMessage(Type type, String playerName) {
        this.snakeMessage = new SnakeMessage(type, playerName);
    }

    public LogicSnakeMessage(Type type, String playerName, String direction) {
        this.snakeMessage = new SnakeMessage(type, playerName, direction);
    }

    public LogicSnakeMessage(Type type, GameState state) {
        this.snakeMessage = new SnakeMessage(type, state);
    }

    public LogicSnakeMessage(Type type, String winnerName, boolean dummy) {
        this.snakeMessage = new SnakeMessage(type, winnerName, dummy);
    }
    
    public SnakeMessage getsSnakeMessage(){
        return snakeMessage;
    }
    
}
