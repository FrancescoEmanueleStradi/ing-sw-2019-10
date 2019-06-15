package model.decks;

import model.cards.WeaponCard;
import model.cards.weaponcards.*;

import java.util.ArrayList;
import java.util.List;

public class WeaponDeck implements Deck {

    private ArrayList<WeaponCard> deck = new ArrayList<>();

    public WeaponDeck() {
        Cyberblade c1 = new Cyberblade();
            deck.add(c1);
        Electroscythe c2 = new Electroscythe();
            deck.add(c2);
        Flamethrower c3 = new Flamethrower();
            deck.add(c3);
        Furnace c4 = new Furnace();
            deck.add(c4);
        GrenadeLauncher c5 = new GrenadeLauncher();
            deck.add(c5);
        Heatseeker c6 = new Heatseeker();
            deck.add(c6);
        Hellion c7 = new Hellion();
            deck.add(c7);
        LockRifle c8 = new LockRifle();
            deck.add(c8);
        MachineGun c9 = new MachineGun();
            deck.add(c9);
        PlasmaGun c10 = new PlasmaGun();
            deck.add(c10);
        PowerGlove c11 = new PowerGlove();
            deck.add(c11);
        Railgun c12 = new Railgun();
            deck.add(c12);
        RocketLauncher c13 = new RocketLauncher();
            deck.add(c13);
        Shockwave c14 = new Shockwave();
            deck.add(c14);
        Shotgun c15 = new Shotgun();
            deck.add(c15);
        Sledgehammer c16 = new Sledgehammer();
            deck.add(c16);
        THOR c17 = new THOR();
            deck.add(c17);
        TractorBeam c18 = new TractorBeam();
            deck.add(c18);
        VortexCannon c19 = new VortexCannon();
            deck.add(c19);
        Whisper c20 = new Whisper();
            deck.add(c20);
        ZX2 c21 = new ZX2();
            deck.add(c21);
    }

    public void startingShuffle() {
        shuffleDeck(this.deck);
    }

    List<WeaponCard> getDeck() {
        return this.deck;
    }

    public WeaponCard getTopOfDeck() {
        if(!this.deck.isEmpty()) {
            WeaponCard w = this.deck.get(0);
            this.deck.remove(0);
            return w;
        }
        return null;
    }
}