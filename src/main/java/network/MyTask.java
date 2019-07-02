package network;

import java.rmi.RemoteException;
import java.util.TimerTask;

/**
 * This class extends TimerTask and schedules a task for a given player to manage possible disconnections.
 */
public class MyTask extends TimerTask {

    private int game;
    private int identifier;
    private String nickName;
    private ServerInterface server;

    /**
     * Creates new instance of MyTask.
     *
     * @param game       game number
     * @param identifier player identifier
     * @param nickName   player nickname
     * @param server     server
     */
    public MyTask(int game, int identifier, String nickName, ServerInterface server) {
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.server = server;
    }

    /**
     * Launches thread to carry out the task. It aims to safely terminate the program should a player disconnect
     * (and the conditions in manageDisconnection are met).
     * In case of InterruptedException however, the game must not exit.
     */
    public void run() {
        try {
            server.manageDisconnection(game, identifier, nickName);
            server.finishTurn(game);
            System.exit(0);
        } catch (RemoteException e) {
            System.exit(0);
        } catch (InterruptedException i) {
            System.exit(1);
            Thread.currentThread().interrupt();
        }
    }
}