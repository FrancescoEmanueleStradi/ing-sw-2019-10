public class Board {

    private KillTrack k;
    private WeaponSlot w1, w2, w3;
    private Cell[][] arena = new Cell[3][4];        //3 rows 4 columns
    private int aType;                              // From 1 to 4 --> From the highest (the biggest) to the lowest in the rule file

    public Board(int type) {
        this.k = new KillTrack();
        this.aType = type;
        /*WeaponCard wc1
        WeaponCard wc2
        WeaponCard wc3
        WeaponCard wc4
        WeaponCard wc5
        WeaponCard wc6
        WeaponCard wc7
        WeaponCard wc8
        WeaponCard wc9*/
        this.w1 = new WeaponSlot(1, wc1, wc2, wc3); //randomly generated (before this call)
        this.w2 = new WeaponSlot(2, wc4, wc5, wc6);
        this.w3 = new WeaponSlot(3, wc7, wc8, wc9);

        if(this.aType == 1) {
            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{2};
            arena[0][0] = new Cell(0, Colour.RED, a1, b1 );

            int[] a2 = new int[]{1};
            int[] b2 = new int[]{3,4};
            arena[0][1] = new Cell(0, Colour.BLUE, a2, b2 );

            int[] a3 = new int[]{1,2};
            int[] b3 = new int[]{3};
            arena[0][2] = new Cell(1, Colour.BLUE, a3, b3 );

            arena[0][3] = new Cell(-1);

            int[] a4 = new int[]{2,4};
            int[] b4 = new int[]{3};
            arena[1][0] = new Cell(1, Colour.RED, a4, b4 );

            int[] a5 = new int[]{4};
            int[] b5 = new int[]{1,3};
            arena[1][1] = new Cell(0, Colour.PURPLE, a5, b5 );

            int[] a6 = new int[]{3};
            int[] b6 = new int[]{1,2};
            arena[1][2] = new Cell(0, Colour.PURPLE, a6, b6 );

            int[] a7 = new int[]{1,2};
            int[] b7 = new int[]{4};
            arena[1][3] = new Cell(0, Colour.YELLOW, a7, b7 );

            int[] a8 = new int[]{3,4};
            int[] b8 = new int[]{1};
            arena[2][0] = new Cell(0, Colour.WHITE, a8, b8 );

            int[] a9 = new int[]{3};
            int[] b9 = new int[]{1};
            arena[2][1] = new Cell(0, Colour.WHITE, a9, b9 );

            int[] a10 = new int[]{1,3};
            int[] b10 = new int[]{2};
            arena[2][2] = new Cell(0, Colour.WHITE, a10, b10 );

            int[] a11 = new int[]{2,3};
            int[] b11 = new int[]{4};
            arena[2][3] = new Cell(1, Colour.YELLOW, a11, b11 );

        }

        if(this.aType == 2) {

            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{3};
            arena[0][0] = new Cell(0, Colour.BLUE, a1, b1 );

            int[] a2 = new int[]{1,3};
            int[] b2 = new int[]{};
            arena[0][1] = new Cell(0, Colour.BLUE, a2, b2 );

            int[] a3 = new int[]{1,2};
            int[] b3 = new int[]{3};
            arena[0][2] = new Cell(1, Colour.BLUE, a3, b3 );

            arena[0][3] = new Cell(-1);

            int[] a4 = new int[]{3,4};
            int[] b4 = new int[]{1};
            arena[1][0] = new Cell(1, Colour.PURPLE, a4, b4 );

            int[] a5 = new int[]{1};
            int[] b5 = new int[]{3};
            arena[1][1] = new Cell(0, Colour.PURPLE, a5, b5 );

            int[] a6 = new int[]{3};
            int[] b6 = new int[]{1,2};
            arena[1][2] = new Cell(0, Colour.PURPLE, a6, b6 );

            int[] a7 = new int[]{1,2};
            int[] b7 = new int[]{4};
            arena[1][3] = new Cell(0, Colour.YELLOW, a7, b7 );

            arena[2][0] = new Cell(-1);

            int[] a9 = new int[]{3,4};
            int[] b9 = new int[]{1};
            arena[2][1] = new Cell(0, Colour.WHITE, a9, b9 );

            int[] a10 = new int[]{1,3};
            int[] b10 = new int[]{2};
            arena[2][2] = new Cell(0, Colour.WHITE, a10, b10 );

            int[] a11 = new int[]{2,3};
            int[] b11 = new int[]{4};
            arena[2][3] = new Cell(1, Colour.YELLOW, a11, b11 );

        }

        if(this.aType == 3) {

            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{3};
            arena[0][0] = new Cell(0, Colour.BLUE, a1, b1 );

            int[] a2 = new int[]{1,3};
            int[] b2 = new int[]{};
            arena[0][1] = new Cell(0, Colour.BLUE, a2, b2 );

            int[] a3 = new int[]{1};
            int[] b3 = new int[]{2,3};
            arena[0][2] = new Cell(1, Colour.BLUE, a3, b3 );

            int[] a4 = new int[]{1,2};
            int[] b4 = new int[]{3,4};
            arena[0][3] = new Cell(0, Colour.GREEN, a4, b4 );

            int[] a5 = new int[]{3,4};
            int[] b5 = new int[]{1};
            arena[1][0] = new Cell(1, Colour.RED, a5, b5 );

            int[] a6 = new int[]{1,2};
            int[] b6 = new int[]{3};
            arena[1][1] = new Cell(0, Colour.RED, a6, b6 );

            int[] a7 = new int[]{4};
            int[] b7 = new int[]{1};
            arena[1][2] = new Cell(0, Colour.YELLOW, a7, b7 );

            int[] a8 = new int[]{2};
            int[] b8 = new int[]{1};
            arena[1][3] = new Cell(0, Colour.YELLOW, a8, b8 );

            arena[2][0] = new Cell(-1);

            int[] a9 = new int[]{3,4};
            int[] b9 = new int[]{1,2};
            arena[2][1] = new Cell(0, Colour.WHITE, a9, b9 );

            int[] a10 = new int[]{3};
            int[] b10 = new int[]{4};
            arena[2][2] = new Cell(0, Colour.YELLOW, a10, b10 );

            int[] a11 = new int[]{2,3};
            int[] b11 = new int[]{};
            arena[2][3] = new Cell(1, Colour.YELLOW, a11, b11 );

        }

        if(this.aType == 4) {

            int[] a1 = new int[]{1,4};
            int[] b1 = new int[]{2};
            arena[0][0] = new Cell(0, Colour.RED, a1, b1 );

            int[] a2 = new int[]{1};
            int[] b2 = new int[]{3,4};
            arena[0][1] = new Cell(0, Colour.BLUE, a2, b2 );

            int[] a3 = new int[]{1};
            int[] b3 = new int[]{2,3};
            arena[0][2] = new Cell(1, Colour.BLUE, a3, b3 );

            int[] a4 = new int[]{1,2};
            int[] b4 = new int[]{3,4};
            arena[0][3] = new Cell(0, Colour.GREEN, a4, b4 );

            int[] a5 = new int[]{2,4};
            int[] b5 = new int[]{3};
            arena[1][0] = new Cell(1, Colour.RED, a5, b5 );

            int[] a6 = new int[]{2,4};
            int[] b6 = new int[]{1,3};
            arena[1][1] = new Cell(0, Colour.PURPLE, a6, b6 );

            int[] a7 = new int[]{4};
            int[] b7 = new int[]{1};
            arena[1][2] = new Cell(0, Colour.YELLOW, a7, b7 );

            int[] a8 = new int[]{2};
            int[] b8 = new int[]{1};
            arena[1][3] = new Cell(0, Colour.YELLOW, a8, b8 );

            int[] a9 = new int[]{3,4};
            int[] b9 = new int[]{1};
            arena[2][0] = new Cell(0, Colour.WHITE, a9, b9 );

            int[] a10 = new int[]{3};
            int[] b10 = new int[]{1,2};
            arena[2][1] = new Cell(0, Colour.WHITE, a10, b10 );

            int[] a11 = new int[]{3};
            int[] b11 = new int[]{4};
            arena[2][2] = new Cell(0, Colour.YELLOW, a11, b11 );

            int[] a12 = new int[]{2,3};
            int[] b12 = new int[]{0};
            arena[2][3] = new Cell(1, Colour.YELLOW, a12, b12 );


        }
    }


}
