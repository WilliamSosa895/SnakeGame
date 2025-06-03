/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic_SNAKE;
import Snake_Cliente_Servidor.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author william
 */
public class LogicSnakeServidor {
    private SnakeServidor snakeServidor;

    public LogicSnakeServidor(int puerto) {
        this.snakeServidor = new Snake_Cliente_Servidor.SnakeServidor(puerto);
    }

    public SnakeServidor getSnakeServidor() {
        return snakeServidor;
    }


    @SuppressWarnings("unchecked")
    public static LogicPlayer[] loadLogicRanking() {
        File file = new File("scores.dat");
        if (!file.exists()) {
            return new LogicPlayer[0];
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Player> lista = (List<Player>) ois.readObject();
            Collections.sort(lista, (a, b) -> Integer.compare(b.getScore(), a.getScore()));

            LogicPlayer[] resultado = new LogicPlayer[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                Player p = lista.get(i);
                resultado[i] = new LogicPlayer(p.getName(), p.getScore());
            }
            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return new LogicPlayer[0];
        }
    }

    public LogicPlayer[] getLogicRanking() {
        return loadLogicRanking();
    }
}
