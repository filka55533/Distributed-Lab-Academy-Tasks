package com.filka.cryptography.hash;

public interface Hasher<T> {

    T getHash(byte[] data);
}