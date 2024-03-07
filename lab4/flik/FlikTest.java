package flik;
import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(128, 128));  // 这应该通过
        assertFalse(Flik.isSameNumber(128, 500)); // 这应该失败
    }
}
