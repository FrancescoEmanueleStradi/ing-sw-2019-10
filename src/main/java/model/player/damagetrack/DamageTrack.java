package model.player.damagetrack;

import model.Colour;
import model.player.DamageToken;

import java.util.LinkedList;
import java.util.List;

public class DamageTrack {

    private DamageToken[] damageTokens;                  //0,1 Normal -- 2,3,4 first power up -- 5,6,7,8,9 second power up -- 10 death -- 11 mark
    private List<NumColour> l;

    public DamageTrack(){
        this.damageTokens = new DamageToken[12];

    }

    public DamageToken[] getDamageTokens() {
        return damageTokens;
    }

    public void addDamage(int numDamage, Colour c) {
        for(int i = 0; i < damageTokens.length; i++){
            if(damageTokens[i] == null && numDamage != 0) {
                damageTokens[i] = new DamageToken(c);
                numDamage--;                             //Controller will check if damageTokens[10] is occupied (player is dead) and if damageTokens[11] is occupied (player is dead and marked)
               }
            else if(numDamage == 0)
                break;
           }
    }

    public DamageToken getDT(int index) {
        return this.damageTokens[index];
    }

    public void clean() {
        for(int i=0; i<12; i++){
            damageTokens[i] = null;
        }
    }

    private List<Colour> colours() {
        LinkedList<Colour> lC = new LinkedList<>();
        for(DamageToken d : this.damageTokens){
            if(d != null && !lC.contains(d.getC()))
                lC.add(d.getC());
        }
        return lC;
    }

    public void cleanL() {
        this.l.clear();
    }

    private void initializeListNumColour() {
        this.l = new LinkedList<>();
        for(Colour c : this.colours()){
            NumColour num = new NumColour(c);
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
        for(DamageToken d : this.damageTokens){
            if(d != null)
                giveNumColour(d.getC()).addNum();
        }
        this.tie();
    }

    private List<NumColour> getOrderedNumColour() {
        l.sort((numColour1, numColour2) -> numColour2.getNum() - numColour1.getNum());
        return l;
        //return this.l.stream().sorted((a, b) -> (a.colourDifference(b))).collect(Collectors.toList()); this is wrong!
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