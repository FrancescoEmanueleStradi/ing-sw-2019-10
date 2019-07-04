package network;

import view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * This class contains the vital information to be stored for each active connection.
 * Each Player in a game has a nickName, identifier, and a boolean isMyTurn which are necessary
 * to maintain turn order, especially if a player reconnects should an issue be encountered.
 * The lists of lists are used in notify methods within socket connections.
 */
public class Connection {

    /**
     * Set to null if client uses socket
     */
    private View view;

    private String nickName;
    private int identifier;
    private boolean isMyTurn;

    private List<List<String>> printPlayerList = new LinkedList<>();
    private List<List<String>> printScoreList = new LinkedList<>();
    private List<List<String>> printPositionList = new LinkedList<>();
    private List<List<String>> printMarkList = new LinkedList<>();
    private List<List<String>> printDamageList = new LinkedList<>();

    /**
     * Gets view.
     *
     * @return view
     */
    public View getView() {
        return view;
    }

    /**
     * Gets player nickname.
     *
     * @return nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Gets player identifier.
     *
     * @return identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Boolean establishing whether or not it is the player's own turn.
     *
     * @return boolean
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * Sets view to CLI or GUI.
     *
     * @param view current view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Sets player's nickname.
     *
     * @param nickName current nickname
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Sets player identifier.
     *
     * @param identifier current identifier
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Sets isMyTurn to true or false.
     *
     * @param myTurn player's current own turn boolean
     */
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }


    //methods used for notify (socket)

    /**
     * Gets list of players to be printed.
     *
     * @return list of players
     */
    public List<List<String>> getPrintPlayerList() {
        return printPlayerList;
    }

    /**
     * Removes first member from player list to be printed.
     */
    public void removeFirstPrintPlayerList() {
        printPlayerList.remove(0);
    }

    /**
     * Appends player information to list of players to be printed.
     *
     * @param informationPrintPlayer player information
     */
    public void setPrintPlayerList(List<String> informationPrintPlayer) {
        printPlayerList.add(informationPrintPlayer);
    }

    /**
     * Gets score list to be printed.
     *
     * @return score list
     */
    public List<List<String>> getPrintScoreList() {
        return printScoreList;
    }

    /**
     * Removes first member from score list to be printed.
     */
    public void removeFirstPrintScoreList() {
        printScoreList.remove(0);
    }

    /**
     * Appends score information to score list to be printed.
     *
     * @param informationPrintScore current score information
     */
    public void setPrintScoreList(List<String> informationPrintScore) {
        printScoreList.add(informationPrintScore);
    }

    /**
     * Gets position list to be printed.
     *
     * @return position list
     */
    public List<List<String>> getPrintPositionList() {
        return printPositionList;
    }

    /**
     * Removes first member from position list to be printed.
     */
    public void removeFirstPrintPositionList() {
        printPositionList.remove(0);
    }

    /**
     * Appends position information to list to be printed.
     *
     * @param informationPrintPosition current position information
     */
    public void setPrintPositionList(List<String> informationPrintPosition) {
        printPositionList.add(informationPrintPosition);
    }

    /**
     * Gets mark list to be printed
     *
     * @return mark list
     */
    public List<List<String>> getPrintMarkList() {
        return printMarkList;
    }

    /**
     * Removes the first member from mark list to be printed.
     */
    public void removeFirstPrintMarkList() {
        printMarkList.remove(0);
    }

    /**
     * Appends mark information to list to be printed.
     *
     * @param informationPrintMark current mark information.
     */
    public void setPrintMarkList(List<String> informationPrintMark) {
        printMarkList.add(informationPrintMark);
    }

    /**
     * Gets damage list to be printed.
     *
     * @return damage list
     */
    public List<List<String>> getPrintDamageList() {
        return printDamageList;
    }

    /**
     * Removes first member from damage list to be printed.
     */
    public void removeFirstPrintDamageList() {
        printDamageList.remove(0);
    }

    /**
     * Appends damage information to list to be printed.
     *
     * @param informationPrintDamage current damage information
     */
    public void setPrintDamageList(List<String> informationPrintDamage) {
        printDamageList.add(informationPrintDamage);
    }
}