package model.decks;

import model.InvalidColourException;
import model.cards.WeaponCard;
import model.cards.weaponCards.*;

import java.util.ArrayList;

public class WeaponDeck extends Deck {

    private ArrayList<WeaponCard> weaponDeck = new ArrayList<>();

    public WeaponDeck() throws InvalidColourException {
        Cyberblade c1 = new Cyberblade();
            weaponDeck.add(c1);
        Electroscythe c2 = new Electroscythe();
            weaponDeck.add(c2);
        Flamethrower c3 = new Flamethrower();
            weaponDeck.add(c3);
        Furnace c4 = new Furnace();
            weaponDeck.add(c4);
        GrenadeLauncher c5 = new GrenadeLauncher();
            weaponDeck.add(c5);
        Heatseeker c6 = new Heatseeker();
            weaponDeck.add(c6);
        Hellion c7 = new Hellion();
            weaponDeck.add(c7);
        LockRifle c8 = new LockRifle();
            weaponDeck.add(c8);
        MachineGun c9 = new MachineGun();
            weaponDeck.add(c9);
        PlasmaGun c10 = new PlasmaGun();
            weaponDeck.add(c10);
        PowerGlove c11 = new PowerGlove();
            weaponDeck.add(c11);
        Railgun c12 = new Railgun();
            weaponDeck.add(c12);
        RocketLauncher c13 = new RocketLauncher();
            weaponDeck.add(c13);
        Shockwave c14 = new Shockwave();
            weaponDeck.add(c14);
        Shotgun c15 = new Shotgun();
            weaponDeck.add(c15);
        Sledgehammer c16 = new Sledgehammer();
            weaponDeck.add(c16);
        THOR c17 = new THOR();
            weaponDeck.add(c17);
        TractorBeam c18 = new TractorBeam();
            weaponDeck.add(c18);
        VortexCannon c19 = new VortexCannon();
            weaponDeck.add(c19);
        Whisper c20 = new Whisper();
            weaponDeck.add(c20);
        ZX2 c21 = new ZX2();
            weaponDeck.add(c21);
    }

    public void startingShuffle() {
        shuffleDeck(weaponDeck);
    }

    public ArrayList<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    public void addCard(WeaponCard c){
        this.getWeaponDeck().add(c);
    }
}
