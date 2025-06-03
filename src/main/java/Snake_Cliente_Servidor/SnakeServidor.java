
package Snake_Cliente_Servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author william
 */
public class SnakeServidor {

    private int puerto = 6000;
    private ServerSocket serverSocket;

    private Map<String, SnakeData> players = new ConcurrentHashMap<>();

    private int foodX, foodY;
    private int gridSize = 30;
    private int panelSize = 800;

    private final int TICK = 200;

    public SnakeServidor(int puerto) {
        this.puerto = puerto;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor Snake escuchando en puerto " + puerto);

            spawnFood();

            new Thread(this::gameLoop).start();

            while (true) {
                Socket s = serverSocket.accept();
                new Thread(() -> handleClient(s)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        try {
            while (true) {
                for (SnakeData sd : players.values()) {
                    sd.advance(gridSize);
                }
                for (SnakeData sd : players.values()) {
                    if (sd.checkAndGrow(foodX, foodY)) {
                        spawnFood();
                        break;
                    }
                }
                String collisionWinner = checkCollisions();
                if (collisionWinner != null) {
                    handleGameOver(collisionWinner);
                    return;
                }
                List<SnakeDTO> dtos = new ArrayList<>();
                for (SnakeData sd : players.values()) {
                    dtos.add(new SnakeDTO(sd.getSegments(), sd.r, sd.g, sd.b));
                }
                GameState gs = new GameState(dtos, foodX, foodY);
                SnakeMessage update = new SnakeMessage(SnakeMessage.Type.STATE_UPDATE, gs);
                broadcast(update);

                Thread.sleep(TICK);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String checkCollisions() {
        Map<String,int[]> headPositions = new ConcurrentHashMap<>();
        for (Map.Entry<String, SnakeData> entry : players.entrySet()) {
            String name = entry.getKey();
            SnakeData sd = entry.getValue();
            int[] head = sd.getHead();
            headPositions.put(name, head);
        }
        boolean anyCollision = false;
        for (String p1 : headPositions.keySet()) {
            int[] h1 = headPositions.get(p1);
            for (String p2 : headPositions.keySet()) {
                if (p1.equals(p2)) continue;
                int[] h2 = headPositions.get(p2);
                if (h1[0] == h2[0] && h1[1] == h2[1]) {
                    anyCollision = true;
                    break;
                }
            }
            if (anyCollision) break;
        }
        if (!anyCollision) {
            for (String p1 : headPositions.keySet()) {
                int[] head1 = headPositions.get(p1);
                for (Map.Entry<String, SnakeData> entry : players.entrySet()) {
                    String p2 = entry.getKey();
                    if (p1.equals(p2)) continue;
                    SnakeData sd2 = entry.getValue();
                    for (int[] seg : sd2.getSegments()) {
                        if (head1[0] == seg[0] && head1[1] == seg[1]) {
                            anyCollision = true;
                            break;
                        }
                    }
                    if (anyCollision) break;
                }
                if (anyCollision) break;
            }
        }
        if (!anyCollision) return null;

        int maxLength = -1;
        List<String> winners = new ArrayList<>();
        for (Map.Entry<String, SnakeData> entry : players.entrySet()) {
            String name = entry.getKey();
            int length = entry.getValue().getSegments().size();
            if (length > maxLength) {
                maxLength = length;
                winners.clear();
                winners.add(name);
            } else if (length == maxLength) {
                winners.add(name);
            }
        }
        if (winners.size() == 1) {
            return winners.get(0);
        } else {
            return "";
        }
    }

    private void handleGameOver(String winnerName) {
        saveScoresToFile();

        SnakeMessage overMsg = new SnakeMessage(SnakeMessage.Type.GAME_OVER, winnerName, true);
        broadcast(overMsg);

        try {
            for (SnakeData sd : players.values()) {
                try { sd.out.close(); } catch (Exception ignore) {}
            }
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void saveScoresToFile() {
        File file = new File("scores.dat");
        List<Player> listaFicheroAntiguo = new ArrayList<>();

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<Player> temp = (List<Player>) ois.readObject();
                listaFicheroAntiguo.addAll(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Player> mapaAntiguo = new HashMap<>();
        for (Player p : listaFicheroAntiguo) {
            mapaAntiguo.put(p.getName(), p);
        }

        for (Map.Entry<String, SnakeData> entry : players.entrySet()) {
            String nombre = entry.getKey();
            int scoreActual = entry.getValue().getSegments().size();

            if (mapaAntiguo.containsKey(nombre)) {
                Player viejo = mapaAntiguo.get(nombre);
                if (scoreActual > viejo.getScore()) {
                    viejo.setScore(scoreActual);
                }
            } else {
                mapaAntiguo.put(nombre, new Player(nombre, scoreActual));
            }
        }

        List<Player> listaFusionada = new ArrayList<>(mapaAntiguo.values());

        Collections.sort(listaFusionada, (a, b) -> Integer.compare(b.getScore(), a.getScore()));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(listaFusionada);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Player[] loadScoresFromFile() {
        File file = new File("scores.dat");
        if (!file.exists()) {
            return new Player[0];
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Player> lista = (List<Player>) ois.readObject();
            Collections.sort(lista, (a, b) -> Integer.compare(b.getScore(), a.getScore()));
            return lista.toArray(new Player[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Player[0];
        }
    }

    private void handleClient(Socket s) {
        String playerName = null;
        try (
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream  in  = new ObjectInputStream(s.getInputStream());
        ) {
            SnakeMessage msg = (SnakeMessage) in.readObject();
            if (msg.getType() != SnakeMessage.Type.CONNECT) {
                s.close();
                return;
            }
            playerName = msg.getPlayerName();
            System.out.println("Conectado: " + playerName + " desde " + s.getInetAddress());

            SnakeData sd = new SnakeData(gridSize, panelSize, playerName);
            sd.setOutput(out);
            players.put(playerName, sd);

            List<SnakeDTO> initialDtos = new ArrayList<>();
            for (SnakeData tempSd : players.values()) {
                initialDtos.add(new SnakeDTO(tempSd.getSegments(), tempSd.r, tempSd.g, tempSd.b));
            }
            GameState initialState = new GameState(initialDtos, foodX, foodY);
            SnakeMessage initialMsg = new SnakeMessage(SnakeMessage.Type.STATE_UPDATE, initialState);
            out.writeObject(initialMsg);
            out.reset();

            while (true) {
                SnakeMessage clientMsg = (SnakeMessage) in.readObject();
                if (clientMsg.getType() == SnakeMessage.Type.DIRECTION) {
                    players.get(playerName).setNextDirection(clientMsg.getDirection());
                } else if (clientMsg.getType() == SnakeMessage.Type.DISCONNECT) {
                    break;
                }
            }

        } catch (Exception e) {
        } finally {
            if (playerName != null) {
                players.remove(playerName);
                System.out.println(playerName + " se desconectó");
            }
            try { s.close(); } catch (Exception ignored) {}
        }
    }

    private void broadcast(SnakeMessage msg) {
        for (SnakeData sd : players.values()) {
            try {
                sd.out.writeObject(msg);
                sd.out.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void spawnFood() {
        Random rand = new Random();
        while (true) {
            int x = rand.nextInt(gridSize), y = rand.nextInt(gridSize);
            boolean collide = false;
            for (SnakeData sd : players.values()) {
                for (int[] seg : sd.getSegments()) {
                    if (seg[0] == x && seg[1] == y) {
                        collide = true;
                        break;
                    }
                }
                if (collide) break;
            }
            if (!collide) {
                foodX = x;
                foodY = y;
                return;
            }
        }
    }

}