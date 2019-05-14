package view;

public class Connection {
    private View view;
    private String nickName;
    private int identifier;
    private boolean isMyTurn;

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
}
