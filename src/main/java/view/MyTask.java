package view;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class MyTask extends TimerTask {
    private int game;
    private int identifier;
    private String nickName;
    private ServerInterface server;


    public MyTask(int game, int identifier, String nickName, ServerInterface server){
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.server = server;
    }

    public void run() {
        try{
            server.manageDisconnection(game, identifier, nickName);
            System.exit(0);
        }catch (RemoteException e){
            System.exit(0);
        }catch (InterruptedException i){
            System.exit(1);
            Thread.currentThread().interrupt();
        }

    }
}
