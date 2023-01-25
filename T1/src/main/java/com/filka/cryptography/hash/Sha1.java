package com.filka.cryptography.hash;

public class Sha1 implements Hasher<byte[]>{

    private static final int PADDING_LENGTH_IN_BITS = 512;

    private static final int PADDING_MODULO = 448;

    private static final int HASH_LENGTH_IN_WORDS = 5;

    private static final int MESSAGE_SCHEDULE_LENGTH = 80;

    private static int getKNumberForPadding(int messageLengthInBits) {
        int m = (messageLengthInBits + 1 - PADDING_MODULO) / PADDING_LENGTH_IN_BITS;
        return m * PADDING_LENGTH_IN_BITS  + PADDING_MODULO - messageLengthInBits - 1;
    }

    private static byte[] formPaddingMessage(byte[] data){
        int kNumberForPadding = getKNumberForPadding(data.length * 8);

        byte[] res = new byte[data.length + 64 / 8 + (kNumberForPadding + 1)/ 8];
        for (int i = 0; i < res.length; i++) {

            if (i < data.length){

                res[i] = data[i];
            }
            else{
                int j = i - data.length;
                if (j == 0){
                    res[i] = (byte)0x80;
                }else if (kNumberForPadding - j * 8 < 0){
                    j += 4 + data.length;
                    res[j] = (byte)(data.length & 0xFF_00_00_00);
                    res[j + 1] = (byte)(data.length & 0x00_FF_00_00);
                    res[j + 2] = (byte)(data.length & 0x00_00_FF_00);
                    res[j + 3] = (byte)(data.length & 0x00_00_00_FF);

                    i += 7;
                }

            }
        }

        return res;
    }

    private static int[] getInitialValue(){
        return new int[] {0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0};
    }

    private static int ch(int x, int y, int z){
        return (x & y) ^ (x ^ z);
    }

    private static int parity(int x, int y, int z){
        return x ^ y ^ z;
    }

    private static int maj(int x, int y, int z){
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private static int f(int x, int y, int z, int t){
        int result;

        if (0 <= t && t <= 19){
            result = ch(x, y, z);

        }else if (40 <= t && t <= 59){
            result = maj(x, y, z);

        }else{
            result = parity(x, y, z);
        }

        return result;
    }

    private static int[] initializeMessageSchedule(byte[] data, int offset){

        int[] result = new int[MESSAGE_SCHEDULE_LENGTH];
        for (int t = 0; t < result.length; t++){
            if (t < 16){
                int index = offset + t * 4;
                int temp = data[index] << 24 | data[index + 1] << 16 + data[index + 2] << 8 + data[index + 3];
                result[t] = temp;
            }else{
                result[t] = Integer.rotateLeft(result[t - 3] ^ result[t - 8] ^ result[t - 14] ^ result[t - 16], 1);
            }
        }

        return result;
    }

    private static int getKConst(int t){
        int result;

        if (t <= 19){
            result = 0x5a827999;

        }else if (t <= 39){
            result = 0x6ed9eba1;

        }else if (t <= 59){
            result = 0x8f1bbcdc;

        }else {
            result = 0xca62c1d6;

        }

        return result;
    }

    @Override
    public byte[] getHash(byte[] data) {

        byte[] paddedMessage = formPaddingMessage(data);
        int[] hash = getInitialValue();

        for (int i = 0; i < paddedMessage.length; i += PADDING_LENGTH_IN_BITS / 8) {
            int[] messageSchedule = initializeMessageSchedule(paddedMessage, i);
            int a = hash[0];
            int b = hash[1];
            int c = hash[2];
            int d = hash[3];
            int e = hash[4];

            for (int t = 0; t < MESSAGE_SCHEDULE_LENGTH; t++){
                int T = Integer.rotateLeft(a, 5) + f(b, c, d, t) + e + getKConst(t) + messageSchedule[t];
                e = d;
                d = c;
                c = Integer.rotateLeft(b, 30);
                b = a;
                a = T;
            }

            hash[0] = a + hash[0];
            hash[1] = b + hash[1];
            hash[2] = c + hash[2];
            hash[3] = d + hash[3];
            hash[4] = e + hash[4];

        }

        byte[] result = new byte[hash.length * 4];
        for (int i = 0; i < result.length; i++){

            int offset = (3 - i % 4) * 8;
            result[i] = (byte)(hash[i / 4] >> offset);
        }
        return result;
    }
}