package model.player;

import model.Colour;

import java.util.LinkedList;
import java.util.List;

public class DamageTrack {

    private DamageToken[] damageTr;                  //0,1 Normal -- 2,3,4 first power up -- 5,6,7,8,9 second power up -- 10 death -- 11 mark

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



}