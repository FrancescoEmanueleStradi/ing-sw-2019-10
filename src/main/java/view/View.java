package view;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents the view component containing methods that mainly provide the client with information about changes in the
 * model, and await user input.
 */
public interface View extends Remote {

    /**
     * Gets view.
     *
     * @return view
     * @throws RemoteException RMI exception
     */
    View getView() throws RemoteException;

    /**
     * Gets game.
     *
     * @return game
     * @throws RemoteException RMI exception
     */
    int getGame() throws RemoteException;

    /**
     * Sets game.
     *
     * @param game game
     * @throws RemoteException RMI exception
     */
    void setGame(int game)throws RemoteException;

    /**
     * Sets identifier.
     *
     * @param identifier identifier
     * @throws RemoteException RMI exception
     */
    void setIdentifier(int identifier) throws RemoteException;

    /**
     * Gets nickname.
     *
     * @return nickname
     * @throws RemoteException RMI exception
     */
    String getNickName() throws RemoteException;

    /**
     * Sets player's notify information.
     *
     * @param identifier identifier
     * @throws RemoteException RMI exception
     */
    void setInformation(int identifier) throws RemoteException;

    /**
     * Notifies all active clients of disconnection.
     *
     * @param disconnected identifier
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void disconnected(int disconnected) throws RemoteException, InterruptedException;

    /**
     * Asks user to input name and colour.
     *
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void askNameAndColour() throws RemoteException, InterruptedException;

    /**
     * Asks user to select powerup card for their spawn point.
     *
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void selectSpawnPoint() throws RemoteException, InterruptedException;

    /**
     * Asks user to select their first action.
     *
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void action1() throws RemoteException, InterruptedException;

    /**
     * Asks user to input move (first action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void moveFirstAction() throws RemoteException, InterruptedException;

    /**
     * Asks user to input shoot (first action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void shootFirstAction() throws InterruptedException, IOException;

    /**
     * Asks user to input grab (second action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void grabFirstAction() throws RemoteException, InterruptedException;

    /**
     * Asks user to select their second action.
     *
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void action2() throws RemoteException, InterruptedException;

    /**
     * Asks user to input move (second action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void moveSecondAction() throws RemoteException, InterruptedException;

    /**
     * Asks user to input shoot (second action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void shootSecondAction() throws InterruptedException, IOException;

    /**
     * Asks user to input grab (second action) parameters.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    void grabSecondAction() throws RemoteException, InterruptedException;

    /**
     * Asks user if they want to use a powerup card.
     *
     * @return the boolean
     * @throws RemoteException RMI exception
     */
    boolean doYouWantToUsePUC() throws RemoteException;

    /**
     * Asks user which powerup card they want to use.
     *
     * @throws RemoteException RMI exception
     */
    void usePowerUpCard() throws RemoteException;

    /**
     * Asks users if they want to reload one or more wepaons.
     *
     * @throws RemoteException      RMI exception
     * @throws InterruptedException thread interruption
     */
    void reload() throws RemoteException, InterruptedException;

    /**
     * Notifies client whether or not scoring is taking place.
     *
     * @throws RemoteException RMI exception
     */
    void scoring() throws RemoteException;

    /**
     * Asks user to select powerup card for their new spawn point.
     *
     * @throws RemoteException RMI exception
     */
    void newSpawnPoint() throws RemoteException;

    /**
     * Notifies client that board replacing is taking place and that the player's turn has ended.
     *
     * @throws RemoteException RMI exception
     */
    void replace() throws RemoteException;

    /**
     * Asks user to choose one or more FF turn actions and enter their parameters.
     *
     * @throws RemoteException RMI exception
     */
    void finalFrenzyTurn() throws RemoteException;

    /**
     * Notifies client that Final Frenzy has ended.
     *
     * @throws RemoteException RMI exception
     */
    void endFinalFrenzy() throws RemoteException;

    /**
     * Prints final scores.
     *
     * @throws RemoteException RMI exception
     */
    void finalScoring()throws RemoteException;

    /**
     * Prints player information when they join the game.
     *
     * @param information notify info
     * @throws RemoteException RMI exception
     */
    void printPlayer(List<String> information) throws RemoteException;

    /**
     * Prints player's score.
     *
     * @param information notify info
     * @throws RemoteException RMI exception
     */
    void printScore(List<String> information) throws RemoteException;

    /**
     * Prints player's position.
     *
     * @param information notify info
     * @throws RemoteException RMI exception
     */
    void printPosition(List<String> information) throws RemoteException;

    /**
     * Prints player's received marks.
     *
     * @param information notify info
     * @throws RemoteException RMI exception
     */
    void printMark(List<String> information) throws RemoteException;

    /**
     * Prints player's received damage.
     *
     * @param information notify info
     * @throws RemoteException RMI exception
     */
    void printDamage(List<String> information) throws RemoteException;

    /**
     * Prints arena type.
     *
     * @throws RemoteException RMI exception
     */
    void printType() throws RemoteException;

    /**
     * Sets arena type.
     *
     * @param type type
     * @throws RemoteException RMI exception
     */
    void setType(int type) throws RemoteException;

    /**
     * Exits.
     *
     * @throws RemoteException RMI exception
     */
    void exit() throws RemoteException;
}
