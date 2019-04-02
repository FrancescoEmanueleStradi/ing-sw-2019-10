package Model.Board;

import Model.Colour;

public class KillTrack {

    private int[] skulls;     //0 skull, 1 damage, 2 double damage, 3 empty
    private Colour[] c;

    public KillTrack(){
        skulls = new int[]{0,0,0,0,0,0,0,0};
        c = new Colour[8];       //Attention: the array could be exported, we could implement methods that use indexes of the array
    }

    public Colour[] getC() {
        return c;
    }

    public int[] getSkulls() {
        return skulls;
    }


    public void setC(Colour[] c) {
        this.c = c;
    }

    public void setSkulls(int[] skulls) {
        this.skulls = skulls;
    }
}
