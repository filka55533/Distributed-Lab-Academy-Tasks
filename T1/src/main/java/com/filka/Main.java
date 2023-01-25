package com.filka;

import com.filka.cryptography.hash.Sha1;

import java.security.MessageDigest;

public class Main {

    public static void main(String[] args) {
        String s = "test";
        byte[] hash = new Sha1().getHash(s.getBytes());

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] res = md.digest(s.getBytes());

            System.out.println("asdas");
        }catch(Exception e){

        }

    }


}