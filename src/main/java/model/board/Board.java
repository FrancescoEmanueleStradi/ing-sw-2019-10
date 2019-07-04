package model.board;

import model.*;
import model.cards.*;

/**
 * The board consists of the arena, modelled as a 3x4 matrix in which some cells are invalid, as well as the
 * killtrack and weapon slots.
 */
public class Board {

    private KillTrack k;
    private WeaponSlot w1;
    private WeaponSlot w2;
    private WeaponSlot w3;
    private Cell[][] arena = new Cell[3][4];

    /**
     * Types are 1, 2, 3 and 4, corresponding to the layouts in the manual from the highest to the lowest
     */
    private int aType;

    /**
     * Creates a new board of some arena type.
     *
     * @param type type number
     * @param ws1  weapon slot 1
     * @param ws2  wepaon slot 2
     * @param ws3  weapon slot 3
     */
    public Board(int type, WeaponSlot ws1, WeaponSlot ws2, WeaponSlot ws3) {
        this.k = new KillTrack();
        this.aType = type;
        this.w1 = ws1;
        this.w2 = ws2;
        this.w3 = ws3;

        Position p = new Position(0, 0);
        Position p1 = new Position(0, 1);
        Position p2 = new Position(0, 2);
        Position p3 = new Position(0, 3);
        Position p4 = new Position(1, 0);
        Position p5 = new Position(1, 1);
        Position p6 = new Position(1, 2);
        Position p7 = new Position(1, 3);
        Position p8 = new Position(2, 0);
        Position p9 = new Position(2, 1);
        Position p10 = new Position(2, 2);
        Position p11 = new Position(2, 3);

        int[] a0 = new int[]{0};
        int[] a1 = new int[]{1};
        int[] a2 = new int[]{2};
        int[] a3 = new int[]{3};
        int[] a4 = new int[]{4};
        int[] a12 = new int[]{1, 2};
        int[] a13 = new int[]{1, 3};
        int[] a14 = new int[]{1, 4};
        int[] a23 = new int[]{2, 3};
        int[] a24 = new int[]{2, 4};
        int[] a34 = new int[]{3, 4};

        if(aType == 1) {
            arena[0][0] = new Cell(0, Colour.RED, a14, a2, p);

            arena[0][1] = new Cell(0, Colour.BLUE, a1, a34, p1);

            arena[0][2] = new Cell(1, Colour.BLUE, a12, a3, p2);

            arena[0][3] = new Cell(-1, p3);

            arena[1][0] = new Cell(1, Colour.RED, a24, a3, p4);

            arena[1][1] = new Cell(0, Colour.PURPLE, a4, a13, p5);

            arena[1][2] = new Cell(0, Colour.PURPLE, a3, a12, p6);

            arena[1][3] = new Cell(0, Colour.YELLOW, a12, a4, p7);

            arena[2][0] = new Cell(0, Colour.WHITE, a34, a1, p8);

            arena[2][1] = new Cell(0, Colour.WHITE, a3, a1, p9);

            arena[2][2] = new Cell(0, Colour.WHITE, a13, a2, p10);

            arena[2][3] = new Cell(1, Colour.YELLOW, a23, a4, p11);
        }

        else if(aType == 2) {
            arena[0][0] = new Cell(0, Colour.BLUE, a14, a3, p);

            arena[0][1] = new Cell(0, Colour.BLUE, a13, a0, p1);

            arena[0][2] = new Cell(1, Colour.BLUE, a12, a3, p2);

            arena[0][3] = new Cell(-1, p3);

            arena[1][0] = new Cell(1, Colour.PURPLE, a34, a1, p4);

            arena[1][1] = new Cell(0, Colour.PURPLE, a1, a3, p5);

            arena[1][2] = new Cell(0, Colour.PURPLE, a3, a12, p6);

            arena[1][3] = new Cell(0, Colour.YELLOW, a12, a4, p7);

            arena[2][0] = new Cell(-1, p8);

            arena[2][1] = new Cell(0, Colour.WHITE, a34, a1, p9);

            arena[2][2] = new Cell(0, Colour.WHITE, a13, a2, p10);

            arena[2][3] = new Cell(1, Colour.YELLOW, a23, a4, p11);
        }

        else if(aType == 3) {
            arena[0][0] = new Cell(0, Colour.BLUE, a14, a3, p);

            arena[0][1] = new Cell(0, Colour.BLUE, a13, a0, p1);

            arena[0][2] = new Cell(1, Colour.BLUE, a1, a23, p2);

            arena[0][3] = new Cell(0, Colour.GREEN, a12, a34, p3);

            arena[1][0] = new Cell(1, Colour.RED, a34, a1, p4);

            arena[1][1] = new Cell(0, Colour.RED, a12, a3, p5);

            arena[1][2] = new Cell(0, Colour.YELLOW, a4, a1, p6);

            arena[1][3] = new Cell(0, Colour.YELLOW, a2, a1, p7);

            arena[2][0] = new Cell(-1, p8);

            arena[2][1] = new Cell(0, Colour.WHITE, a34, a12, p9);

            arena[2][2] = new Cell(0, Colour.YELLOW, a3, a4, p10);

            arena[2][3] = new Cell(1, Colour.YELLOW, a23, a0, p11);
        }

        else if(aType == 4) {
            arena[0][0] = new Cell(0, Colour.RED, a14, a2, p);

            arena[0][1] = new Cell(0, Colour.BLUE, a1, a34, p1);

            arena[0][2] = new Cell(1, Colour.BLUE, a1, a23, p2);

            arena[0][3] = new Cell(0, Colour.GREEN, a12, a34, p3);

            arena[1][0] = new Cell(1, Colour.RED, a24, a3, p4);

            arena[1][1] = new Cell(0, Colour.PURPLE, a24, a13, p5);

            arena[1][2] = new Cell(0, Colour.YELLOW, a4, a1, p6);

            arena[1][3] = new Cell(0, Colour.YELLOW, a2, a1, p7);

            arena[2][0] = new Cell(0, Colour.WHITE, a34, a1, p8);

            arena[2][1] = new Cell(0, Colour.WHITE, a3, a12, p9);

            arena[2][2] = new Cell(0, Colour.YELLOW, a3, a4, p10);

            arena[2][3] = new Cell(1, Colour.YELLOW, a23, a0, p11);
        }
    }

    /**
     * Gets killtrack.
     *
     * @return killtrack
     */
    public KillTrack getK() {
        return k;
    }

    /**
     * Gets weapon slot 1.
     *
     * @return weapon slot 1
     */
    public WeaponSlot getW1() {
        return w1;
    }

    /**
     * Gets weapon slot 2.
     *
     * @return weapon slot 2
     */
    public WeaponSlot getW2() {
        return w2;
    }

    /**
     * Gets weapon slot 3
     *
     * @return weapon slot 3
     */
    public WeaponSlot getW3() {
        return w3;
    }

    /**
     * Swaps the ammo card with another in some cell of the arena.
     *
     * @param p position
     * @param a ammo card
     */
    void changeAmmoCard(Position p, AmmoCard a) {
        this.arena[p.getX()][p.getY()].setA(a);
    }

    /**
     * Fills a single given empty weapon slot with a given weapon card.
     *
     * @param w  weapon slot
     * @param wC weapon card
     */
    void changeWeaponCard(WeaponSlot w, WeaponCard wC) {
        if(this.w1.equals(w)) {
            if(this.w1.getCard1() == null)
                this.w1.setCard1(wC);
            else if(this.w1.getCard2() == null)
                this.w1.setCard2(wC);
            else if(this.w1.getCard3() == null)
                this.w1.setCard3(wC);
        }

        else if(this.w2.equals(w)) {
            if(this.w2.getCard1() == null)
                this.w2.setCard1(wC);
            else if(this.w2.getCard2() == null)
                this.w2.setCard2(wC);
            else if(this.w2.getCard3() == null)
                this.w2.setCard3(wC);
        }

        else if(this.w3.equals(w)) {
            if(this.w3.getCard1() == null)
                this.w3.setCard1(wC);
            else if(this.w3.getCard2() == null)
                this.w3.setCard2(wC);
            else if(this.w3.getCard3() == null)
                this.w3.setCard3(wC);
        }
    }

    /**
     * Substitutes skulls.
     *
     * @param n  n
     * @return int (default: -1)
     */
    public int substituteSkull(int n) {
        for(int i = 0; i <this.k.getSkulls().length; i++) {
            if(this.k.getSkulls()[i] == 0) {
                this.k.getSkulls()[i] = n;
                return i;
            }

        }
        return -1;
    }

    /**
     * Get arena cell.
     *
     * @return cell
     */
    public Cell[][] getArena() {
        return arena;
    }

    /**
     * Gets arena type
     *
     * @return num type
     */
    public int getaType() {
        return aType;
    }
}