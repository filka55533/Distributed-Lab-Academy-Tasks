package com.filka.cryptography.encryption;



public class AES implements SymmetricEncryptor<byte[]>{

    private byte[] data;

    private byte[] key;

    private AESType aesType;

    private static final int blockCount = 4;

    public AES(AESType aesType){

        this.aesType = aesType;

    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setSymmetricKey(byte[] symmetricKeyKey) {

        if (aesType.getBitsCount() / 8 != symmetricKeyKey.length)
            throw new IllegalArgumentException("Incorrect key length");

        this.key = symmetricKeyKey.clone();

    }

    @Override
    public byte[] getSymmetricKey() {
        return this.key;
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public void encryptData() {

        byte[] state = new byte[4 * blockCount];





    }

    @Override
    public void decryptData() {

    }
}