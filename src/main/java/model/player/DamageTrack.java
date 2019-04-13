package model.player;

import model.Colour;

public class DamageTrack {

    private DamageToken[] damageTr;                  //0,1 Normal -- 2,3,4 first power up -- 5,6,7,8,9 second power up -- 10 death -- 11 mark

    public DamageTrack(){
        this.damageTr = new DamageToken[12];

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
}