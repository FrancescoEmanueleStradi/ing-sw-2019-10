package network;

import view.View;

import java.util.LinkedList;
import java.util.List;

public class Connection {
    private View view;          //null if client uses socket
    private String nickName;
    private int identifier;
    private boolean isMyTurn;

    private List<List<String>> printPlayerList = new LinkedList<>();
    private List<List<String>> printScoreList = new LinkedList<>();
    private List<List<String>> printPositionList = new LinkedList<>();
    private List<List<String>> printMarkList = new LinkedList<>();
    private List<List<String>> printDamageList = new LinkedList<>();

    public View getView() {
        return view;
    }

    public String getNickName() {
        return nickName;
    }

    public int getIdentifier() {
        return identifier;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }


    //methods used for notify (socket)


    public List<List<String>> getPrintPlayerList() {
        return printPlayerList;
    }

    public void removeFirstPrintPlayerList() {
        printPlayerList.remove(0);
    }

    public void setPrintPlayerList(List<String> informationPrintPlayer) {
        printPlayerList.add(informationPrintPlayer);
    }


    public List<List<String>> getPrintScoreList() {
        return printScoreList;
    }

    public void removeFirstPrintScoreList() {
        printScoreList.remove(0);
    }

    public void setPrintScoreList(List<String> informationPrintScore) {
        printScoreList.add(informationPrintScore);
    }


    public List<List<String>> getPrintPositionList() {
        return printPositionList;
    }

    public void removeFirstPrintPositionList() {
        printPositionList.remove(0);
    }

    public void setPrintPositionList(List<String> informationPrintPosition) {
        printPositionList.add(informationPrintPosition);
    }


    public List<List<String>> getPrintMarkList() {
        return printMarkList;
    }

    public void removeFirstPrintMarkList() {
        printMarkList.remove(0);
    }

    public void setPrintMarkList(List<String> informationPrintMark) {
        printMarkList.add(informationPrintMark);
    }


    public List<List<String>> getPrintDamageList() {
        return printDamageList;
    }

    public void removeFirstPrintDamageList() {
        printDamageList.remove(0);
    }

    public void setPrintDamageList(List<String> informationPrintDamage) {
        printDamageList.add(informationPrintDamage);
    }
}
