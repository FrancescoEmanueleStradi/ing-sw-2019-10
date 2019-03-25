public class KillTrack {

    private int[] skulls;     //0 teschio, 1 goccia, 2 doppia goccia, 3 vuoto
    private Colour[] c;

    public KillTrack(){
        skulls = new int[8];
        c = new Colour[8];       //Attenzione: l'array viene esportato, si possono implementare metodi che utilizzano l'indice del suddetto
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
