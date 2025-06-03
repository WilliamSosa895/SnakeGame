
package Snake_Cliente_Servidor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author william
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SnakeDTO> snakes; 
    private int foodX, foodY;      

    public GameState(List<SnakeDTO> snakes, int foodX, int foodY) {
        this.snakes = snakes;
        this.foodX  = foodX;
        this.foodY  = foodY;
    }

    public List<SnakeDTO> getSnakes() {
        return snakes;
    }
    public int getFoodX() {
        return foodX;
    }
    public int getFoodY() {
        return foodY;
    }
}
