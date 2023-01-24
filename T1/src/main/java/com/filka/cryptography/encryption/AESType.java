package com.filka.cryptography.encryption;

public enum AESType {

    AES_128(128, 10), AES_196(192, 12), AES_256(256, 14);

    private final int bitsCount;

    private final int roundsCount;

    AESType(int bitsCount, int roundsCount) {
        this.bitsCount = bitsCount;
        this.roundsCount = roundsCount;
    }

    public int getBitsCount() {
        return bitsCount;
    }

    public int getRoundsCount() {
        return roundsCount;
    }
}