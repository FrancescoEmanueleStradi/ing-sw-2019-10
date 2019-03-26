import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertTests {

    @Test
    public void SkullsArrayCorrectInitialization() {
        KillTrack k = new KillTrack();
        int[] skulls = k.getSkulls();

        assertEquals(0, skulls[0]);
        assertEquals(0, skulls[1]);
        assertEquals(0, skulls[2]);
        assertEquals(0, skulls[3]);
        assertEquals(0, skulls[4]);
        assertEquals(0, skulls[5]);
        assertEquals(0, skulls[6]);
        assertEquals(0, skulls[7]);
    }



    @Test
    public void CellDoesNotExists() {
        Cell cell = new Cell(-1);
        assertEquals(-1, cell.getRespawn());
    }
}
