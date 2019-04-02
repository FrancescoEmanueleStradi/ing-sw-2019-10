public class DamageTrack {

    private DamageToken[] damageTr;                  //0,1 Normal -- 2,3,4 first power up -- 5,6,7,8,9 second power up -- 10 death -- 11 mark

    public DamageTrack(){
        this.damageTr = new DamageToken[12];

    }



    public void addDamage(int numDamage, Colour c){
       while(numDamage!=0) {
           int i = -1
           for (DamageToken d : damageTr){
               i++;
               if(d==null) {                            //attention to the index
                   damageTr[i] = DamageToken(c);
                   numDamage--;
               }
               if(damageTr[11]!=null)
                   break;
           }
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



