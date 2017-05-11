package arch.sm213.machine.student;

import machine.AbstractMainMemory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by TonyKong on 1/11/2017.
 */
public class MainMemoryTest {
    private MainMemory test;
    @Before
    public void setUp() {
    test = new MainMemory(10);
    }

    @Test
    public void isAccessAligned() {
        assertTrue(test.isAccessAligned(0, 10));
        assertTrue(test.isAccessAligned(7, 7));
        assertTrue(test.isAccessAligned(2, 2));
        assertTrue(test.isAccessAligned(16, 4));
        assertFalse(test.isAccessAligned(1, 10));
        assertFalse(test.isAccessAligned(4, 16));
    }

    @Test
    public void bytesToInteger() {
        assertEquals(1, test.bytesToInteger((byte)0x0, (byte)0x0, (byte)0x0, (byte)0x1));
        assertEquals(16777217, test.bytesToInteger((byte)0x1, (byte)0x0, (byte)0x0, (byte)0x1));
        assertEquals(16909060, test.bytesToInteger((byte)0x1, (byte)0x2, (byte)0x3, (byte)0x4));
        assertEquals(-16777216, test.bytesToInteger( (byte)0xff , (byte)0x0, (byte)0x0, (byte)0x0));
    }

    @Test
    public void integerToBytes() {
        byte [] arrayTest = new byte[4];
        arrayTest[0] = (byte)0x1;
        arrayTest[1] = (byte)0x2;
        arrayTest[2] = (byte)0x3;
        arrayTest[3] = (byte)0x4;
        assertTrue(Arrays.equals(arrayTest, test.integerToBytes(16909060)));

        byte[] arrayTest2 = new byte[4];
        arrayTest2[0] = (byte)0xff;
        arrayTest2[1] = (byte)0x0;
        arrayTest2[2] = (byte)0x0;
        arrayTest2[3] = (byte)0x0;
        assertTrue(Arrays.equals(arrayTest2, test.integerToBytes(-16777216)));
    }

    @Test (expected = AbstractMainMemory.InvalidAddressException.class)
    public void getNegativeAddress() throws AbstractMainMemory.InvalidAddressException {
        test.get(-1, 2);
    }

    @Test (expected = AbstractMainMemory.InvalidAddressException.class)
    public void getOutOfRangeEnd() throws AbstractMainMemory.InvalidAddressException {
        test.get(0, 11);
    }

    @Test (expected = AbstractMainMemory.InvalidAddressException.class)
    public void getOutOfRangeStart() throws AbstractMainMemory.InvalidAddressException {
        test.get(10, 0);
    }

    @Test
    public void getWorking() throws AbstractMainMemory.InvalidAddressException {
        byte[] testEmpty = new byte[0];
        assertTrue(Arrays.equals(testEmpty, test.get(0, 0)));

        byte[] testArray =  new byte[2];
        testArray[0] = (byte)0x4;
        testArray[0] = (byte) 0xff;
        test.set(0, testArray);
        assertTrue(Arrays.equals(testArray, test.get(0,2)));
    }



    @Test(expected = AbstractMainMemory.InvalidAddressException.class)
    public void setNeg() throws AbstractMainMemory.InvalidAddressException{
        test.set(-1,new byte[5]);
    }

    @Test(expected = AbstractMainMemory.InvalidAddressException.class)
    public void setOutOfRangeEnd() throws AbstractMainMemory.InvalidAddressException{
        test.set(0, new byte[11]);
    }

    @Test(expected = AbstractMainMemory.InvalidAddressException.class)
    public void set() throws AbstractMainMemory.InvalidAddressException{
        test.set(10, new byte[0]);
    }

    @Test
    public void setWorking() throws AbstractMainMemory.InvalidAddressException {
        byte[] testEmpty = new byte[0];
        test.set(0, testEmpty);
        assertTrue(Arrays.equals(testEmpty, test.get(0,0)));

        byte[] testArray =  new byte[2];
        testArray[0] = (byte)0x4;
        testArray[0] = (byte) 0xff;
        test.set(0, testArray);
        assertTrue(Arrays.equals(testArray, test.get(0,2)));
    }


}