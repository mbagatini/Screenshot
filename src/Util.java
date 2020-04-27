
import java.nio.ByteBuffer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 

public class Util {

    public static final int RESOLUCAO_X = 1920; // resolucao da tela em x
    public static final int RESOLUCAO_Y = 1080; // resolucao da tela em y

    public static final int BLOCK_X = 120; // em pixels
    public static final int BLOCK_Y = 120; // em pixels

    public static byte[] integerToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    // mesmo que o anterior, porem usando bytes (na unha)
    public static byte[] integerToBytesNail(int value) {
        return new byte[]{
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value};
    }

    public static int bytesToInteger(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

     // mesmo que o anterior, porem usando bytes (na unha)
    public static int bytesToIntegerNail(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | ((bytes[3] & 0xFF) << 0);
    }
}