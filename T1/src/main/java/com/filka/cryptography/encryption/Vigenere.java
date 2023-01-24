package com.filka.cryptography.encryption;

import java.nio.charset.StandardCharsets;

public class Vigenere implements SymmetricEncryptor<String>{

    private static final int alphabetLength = 26;

    private String symmetricKey;

    private String data;

    public Vigenere(){
        data = "";
        symmetricKey = "";
    }

    @Override
    public void encryptData() {
        String dataCopy = data.toUpperCase();
        String encryptResult = "";

        for (int i = 0; i < dataCopy.length(); i++) {
            char symbol = dataCopy.charAt(i);
            if ('A' <= symbol && symbol <= 'Z') {
                symbol = (char)((symbol + symmetricKey.charAt(i) - 'A') % alphabetLength + 'A');

                if (Character.isLowerCase(data.charAt(i))) {
                    symbol = Character.toLowerCase(symbol);
                }
            }
            encryptResult += symbol;
        }

        this.data = encryptResult;
    }

    @Override
    public void decryptData() {
        String dataCopy = this.data.toUpperCase();
        String decryptResult = "";

        for (int i = 0; i < dataCopy.length(); i++){

            char symbol = dataCopy.charAt(i);
            if ('A' <= symbol && symbol <= 'Z') {
                symbol = (char)((symbol + alphabetLength - symmetricKey.charAt(i) - 'A') % alphabetLength + 'A');

                if (Character.isLowerCase(data.charAt(i))){
                    symbol = Character.toLowerCase(symbol);
                }

            }
            decryptResult += symbol;
        }
        this.data = decryptResult;
    }

    @Override
    public void setData(byte[] data) {
        this.data = new String(data);
    }

    @Override
    public byte[] getData() {
        return data.getBytes();
    }

    @Override
    public String getSymmetricKey() {
        return symmetricKey;
    }

    @Override
    public void setSymmetricKey(String symmetricKey) {
        this.symmetricKey = symmetricKey.toUpperCase();
    }
}