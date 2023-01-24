package com.filka.cryptography.encryption;

interface Encryptor {

    void encryptData();
    void decryptData();
    byte[] getData();
    void setData(byte[] data);
}