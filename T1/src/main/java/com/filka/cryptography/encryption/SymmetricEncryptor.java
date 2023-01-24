package com.filka.cryptography.encryption;

interface SymmetricEncryptor<T> extends Encryptor{

    void setSymmetricKey(T symmetricKeyKey);
    T getSymmetricKey();

}