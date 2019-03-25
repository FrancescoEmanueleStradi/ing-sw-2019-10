
public class Cell {
    private int respawn;   //1 è un punto di rigenerazione, 0 non lo è, -1 non esiste
    private Colour c;
    private Position p;
    private int[] pMuri;
    private int[] pPorte;              //1 indica sopra, 2 indica destra, 3 indica sotto, 4 indica sinistra
    private AmmoCard a;


    public Cell(int r){                     //r = -1
        this.respawn = r;

    }

    public Cell(int r, Colour c1, /*Position p1,*/  int[] pm,  int[] pp) {

        this.respawn = r;
        if(this.respawn==0)
            this.a = new AmmoCard();
        this.c = c1;
        //this.p = p1;
        this.pMuri = new int[pm.length];
        System.arraycopy(pm, 0, this.pMuri,0, pm.length);
        this.pPorte = new int[pp.length];
        System.arraycopy(pp, 0, this.pPorte,0, pp.length);


    }

    public int getRespawn() {
        return respawn;
    }

    public Colour getC() {
        return c;
    }

    public Position getP() {
        return p;
    }


    public int[] getpMuri() {
        return pMuri;
    }

    public int[] getpPorte() {
        return pPorte;
    }

    public AmmoCard getA() {
        return a;
    }

    public void setA(AmmoCard a) {
        this.a = a;
    }
}
