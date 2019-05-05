package model.board;

import model.Colour;
import model.player.damagetrack.NumColour;

import java.util.LinkedList;
import java.util.List;

public class KillTrack {

    private int[] skulls;     //0 skull, 1 damage, 2 double damage, 3 empty
    private Colour[] c;
    private List<NumColour> l;

    public KillTrack() {
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

//-------------------------------------------------------------------------------------------------------
    //SCORING: test! Maybe check tiebreaker paragraph in the rules

    private List<Colour> colours() {
        LinkedList<Colour> lC = new LinkedList<>();
        for(Colour colour : this.getC()){
            if(!lC.contains(colour))
                lC.add(colour);
        }
        return lC;
    }

    public void cleanL() {
        this.l.clear();
    }

    private void initializeListNumColour() {
        this.l = new LinkedList<>();
        for(Colour colour : this.colours()){
            NumColour num = new NumColour(colour);
            this.l.add(num);
        }
    }

    private NumColour giveNumColour(Colour c) {
        NumColour nullColour = new NumColour(null);
        for(NumColour n : this.l){
            if(n.getC().equals(c))
                return n;
        }
        return nullColour;
    }

    private void tie() {
        for(int i = 0; i < this.l.size()-1; i++){
            for(int j = i+1; j < this.l.size() ; j++){
                if(this.l.get(i).getNum() == this.l.get(j).getNum()){
                    this.l.get(i).setTie(true);
                    if(!(this.l.get(i).getcTie().contains(this.l.get(j).getC())))
                        this.l.get(i).getcTie().add(this.l.get(j).getC());
                    this.l.get(j).setTie(true);
                    if(!(this.l.get(j).getcTie().contains(this.l.get(i).getC())))
                        this.l.get(j).getcTie().add(this.l.get(i).getC());
                }
            }
        }
    }

    private void listNumColour() {
        this.initializeListNumColour();
        for(int i = 0; i < this.getC().length; i++){
            giveNumColour(this.getC()[i]).addNum();
            if(this.getSkulls()[i] == 2)
                giveNumColour(this.getC()[i]).addNum();
        }
        this.tie();
    }

    private List<NumColour> getOrderedNumColour() {
        l.sort((numColour1, numColour2) -> numColour2.getNum() - numColour1.getNum());
        return l;
        //return this.l.stream().sorted((a, b) -> (a.colourDifference(b))).collect(Collectors.toList()); //this is wrong!
    }

    public List<Colour> scoreBoard() {
        this.listNumColour();
        LinkedList<Colour> colour = new LinkedList<>();
        for(NumColour n : getOrderedNumColour())
            colour.add(n.getC());
        return colour;
    }

    public Colour getColourPosition(int n){             //remember the clean
        return scoreBoard().get(n);
    }
}