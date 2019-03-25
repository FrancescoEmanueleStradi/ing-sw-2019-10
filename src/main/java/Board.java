public class Board {

    private KillTrack k;
    private WeaponSlot w1, w2, w3;
    private Cell[][] arena = new Cell[3][4];        //3 righe 4 colonne
    private int aType;                              // Da 1 a 4 --> dalla più alta (quella grande) a quella più bassa nelle regole

    public Board(int type) {
        this.k = new KillTrack();
        this.aType = type;
        this.w1 = new WeaponSlot(1);
        this.w2 = new WeaponSlot(2);
        this.w3 = new WeaponSlot(3);

        if(this.aType == 1) {
            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{2};
            arena[0][0] = new Cell(0, Colour.ROSSO, a1, b1 );

            int[] a2 = new int[]{1};
            int[] b2 = new int[]{3,4};
            arena[0][1] = new Cell(0, Colour.AZZURRO, a2, b2 );

            int[] a3 = new int[]{1,2};
            int[] b3 = new int[]{3};
            arena[0][2] = new Cell(1, Colour.AZZURRO, a3, b3 );

            arena[0][3] = new Cell(-1);

            int[] a4 = new int[]{2,4};
            int[] b4 = new int[]{3};
            arena[1][0] = new Cell(1, Colour.ROSSO, a4, b4 );

            int[] a5 = new int[]{4};
            int[] b5 = new int[]{1,3};
            arena[1][1] = new Cell(0, Colour.VIOLA, a5, b5 );

            int[] a6 = new int[]{3};
            int[] b6 = new int[]{1,2};
            arena[1][2] = new Cell(0, Colour.VIOLA, a6, b6 );

            int[] a7 = new int[]{1,2};
            int[] b7 = new int[]{4};
            arena[1][3] = new Cell(0, Colour.GIALLO, a7, b7 );

            int[] a8 = new int[]{3,4};
            int[] b8 = new int[]{1};
            arena[2][0] = new Cell(0, Colour.BIANCO, a8, b8 );

            int[] a9 = new int[]{3};
            int[] b9 = new int[]{1};
            arena[2][1] = new Cell(0, Colour.BIANCO, a9, b9 );

            int[] a10 = new int[]{1,3};
            int[] b10 = new int[]{2};
            arena[2][2] = new Cell(0, Colour.BIANCO, a10, b10 );

            int[] a11 = new int[]{2,3};
            int[] b11 = new int[]{4};
            arena[2][3] = new Cell(1, Colour.GIALLO, a11, b11 );

        }

        if(this.aType == 2) {

            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{3};
            arena[0][0] = new Cell(0, Colour.AZZURRO, a1, b1 );

            int[] a2 = new int[]{1,3};
            int[] b2 = new int[]{};
            arena[0][1] = new Cell(0, Colour.AZZURRO, a2, b2 );

            int[] a3 = new int[]{1,2};
            int[] b3 = new int[]{3};
            arena[0][2] = new Cell(1, Colour.AZZURRO, a3, b3 );

            arena[0][3] = new Cell(-1);

            int[] a4 = new int[]{3,4};
            int[] b4 = new int[]{1};
            arena[1][0] = new Cell(1, Colour.VIOLA, a4, b4 );

            int[] a5 = new int[]{1};
            int[] b5 = new int[]{3};
            arena[1][1] = new Cell(0, Colour.VIOLA, a5, b5 );

            int[] a6 = new int[]{3};
            int[] b6 = new int[]{1,2};
            arena[1][2] = new Cell(0, Colour.VIOLA, a6, b6 );

            int[] a7 = new int[]{1,2};
            int[] b7 = new int[]{4};
            arena[1][3] = new Cell(0, Colour.GIALLO, a7, b7 );

            arena[2][0] = new Cell(-1);

            int[] a9 = new int[]{3.4};
            int[] b9 = new int[]{1};
            arena[2][1] = new Cell(0, Colour.BIANCO, a9, b9 );

            int[] a10 = new int[]{1,3};
            int[] b10 = new int[]{2};
            arena[2][2] = new Cell(0, Colour.BIANCO, a10, b10 );

            int[] a11 = new int[]{2,3};
            int[] b11 = new int[]{4};
            arena[2][3] = new Cell(1, Colour.GIALLO, a11, b11 );

        }

        if(this.aType == 3) {

        }

        if(this.aType == 4) {

        }
    }


}
