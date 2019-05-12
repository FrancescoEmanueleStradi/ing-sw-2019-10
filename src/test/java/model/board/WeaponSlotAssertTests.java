package model.board;

import model.cards.WeaponCard;
import model.cards.weaponcards.Furnace;
import model.cards.weaponcards.MachineGun;
import model.cards.weaponcards.RocketLauncher;
import model.cards.weaponcards.THOR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeaponSlotAssertTests {
    @Test
    void WeaponSlotCorrectConstructor()  {
        WeaponCard wc1 = new MachineGun();
        WeaponCard wc2 = new MachineGun();
        WeaponCard wc3 = new MachineGun();
        WeaponSlot w1 = new WeaponSlot(1, wc1, wc2, wc3);

        int n = w1.getWeaponSlot();
        WeaponCard wc11 = w1.getCard1();
        WeaponCard wc12 = w1.getCard2();
        WeaponCard wc13 = w1.getCard3();

        assertEquals(1, n);
        assertEquals(wc1, wc11);
        assertEquals(wc2, wc12);
        assertEquals(wc3, wc13);
    }


    @Test
    void WeaponSlotSetCard()  {
        WeaponCard wc1 = new MachineGun();
        WeaponCard wc2 = new MachineGun();
        WeaponCard wc3 = new MachineGun();
        WeaponSlot w1 = new WeaponSlot(1, wc1, wc2, wc3);

        WeaponCard wc11 = new Furnace();
        w1.setCard1(wc11);

        WeaponCard wc12 = new RocketLauncher();
        w1.setCard2(wc12);

        WeaponCard wc13 = new THOR();
        w1.setCard3(wc13);

        assertEquals(wc11, w1.getCard1());
        assertEquals(wc12, w1.getCard2());
        assertEquals(wc13, w1.getCard3());
    }
}