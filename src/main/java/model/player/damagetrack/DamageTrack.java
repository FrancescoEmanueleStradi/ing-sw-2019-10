package model.player.damagetrack;

import model.Colour;
import model.player.DamageToken;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DamageTrack {

    private DamageToken[] damageTr;                  //0,1 Normal -- 2,3,4 first power up -- 5,6,7,8,9 second power up -- 10 death -- 11 mark
    private List<NumColour> l;

    public DamageTrack(){
        this.damageTr = new DamageToken[12];

    }

    public DamageToken[] getDamageTr() {
        return damageTr;
    }

    public void addDamage(int numDamage, Colour c){
        for(int i = 0; i < damageTr.length; i++){
            if(damageTr[i] == null && numDamage != 0) {
                damageTr[i] = new DamageToken(c);
                numDamage--;                             //Controller will check if damageTr[10] is occupied (player is dead) and if damageTr[11] is occupied (player is dead and marked)
               }
            else if(numDamage == 0)
                break;
           }
    }

    public DamageToken getDT(int index){
        return this.damageTr[index];
    }

    public void clean(){
        for(int i=0; i<12; i++){
            damageTr[i] = null;
        }
    }

    private int damageByColour(Colour c){
        int n = 0;
        for(int i = 0; i < 12; i++){
            if(c.equals(this.damageTr[i].getC()))
                n++;
        }
        return n;
    }

    private List<Colour> colours(){
        LinkedList<Colour> l = new LinkedList<>();
        for(DamageToken d : this.damageTr){
            if(!l.contains(d.getC()))
                l.add(d.getC());
        }
        return l;
    }


    public Colour bestKiller(){
        Colour c = null;
        int n = 0;
        for(Colour c1 : this.colours()){
            if(n < this.damageByColour(c1)){
                c = c1;
                n = this.damageByColour(c1);
            }
        }
       return c;
    }

    public void cleanL(){
        this.l.clear();
    }

    private void initializeListNumColour(){
        this.l = new LinkedList<>();
        for(Colour c : this.colours()){
            NumColour num = new NumColour(c);
            this.l.add(num);
        }
    }

    private NumColour giveNumColour(Colour c){
        for(NumColour n : this.l){
            if(n.getC().equals(c))
                return n;
        }
        return null;
    }

    private void tie(){
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

    private void listNumColour(){
        this.initializeListNumColour();
        for(DamageToken d : this.damageTr){
            giveNumColour(d.getC()).addNum();
        }
        this.tie();
    }

    /*private Colour firstAttacker(List<Colour> l){
        for(DamageToken d : this.damageTr){
            for(Colour c : l){
                if(c.equals(d.getC()))
                    return c;
            }
        }
        return null;
    }*/



    private List<NumColour> getOrderedNumColour(){
        return this.l.stream().sorted((a, b) -> (a.colourDifference(b))).collect(Collectors.toList());
        }

    private List<Colour> scoreBoard(){
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