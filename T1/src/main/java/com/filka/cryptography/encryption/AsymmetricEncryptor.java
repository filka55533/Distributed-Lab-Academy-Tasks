package com.filka.cryptography.encryption;

interface AsymmetricEncryptor<T> extends Encryptor {

    void setPublicKey(T publicKey);
    T getPublicKey();
    void setPrivateKey(T privateKey);
    T getPrivateKey();

}