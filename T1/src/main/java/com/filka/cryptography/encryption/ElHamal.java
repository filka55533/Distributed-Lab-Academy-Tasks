package com.filka.cryptography.encryption;

import java.util.ArrayList;
import java.util.List;

public class ElHamal{

    public static void test() {
        // write your code here
        Long[] a = getRoots(2);
        for (Long i: a)
            System.out.println(i.toString());

        long p = 911, g = 17, x = 9, k = 17;
        byte[] arr = {66, 83, 85, 73, 82};
        for (byte i: arr){
            long[] res = crypt(i, p, g, formPublicKey(x, g, p), k);
            System.out.println(res[0] + " "  + res[1]);
            System.out.println(decrypt(res[0], res[1], x, p));
        }

    }

    public static long findGCD(long a, long b){
        long tmp;
        if (a < b){
            tmp = a;
            a = b;
            b = tmp;
        }

        while (b != 0){
            a %= b;
            tmp = a;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static boolean isPrime(long a){
        long i = 2;
        while (i <= (long) Math.sqrt(a))
            if (a % i++ == 0) return false;
        return i != 1;
    }

    public static long fastExp(long a, long e, long p){
        long res = 1;
        while (e != 0){
            while (e % 2 == 0){
                a = ((a % p)*(a % p))%p;
                e /= 2;
            }
            res = ((res % p) * (a % p)) % p;
            e--;
        }
        return res;
    }

    public static long eulerFunc(long a){
        if (isPrime(a))
            return  a - 1;

        long res = 1;
        for (long i = 2; i < a; i++)
            if (findGCD(a, i) == 1)
                res++;
        return res;
    }

    public static Long[] getComPrime(long num){
        ArrayList<Long> res = new ArrayList<>();
        for (long i = 2; i <= num / 2; i++){
            if (num % i == 0 && isPrime(i))
                res.add(i);
        }
        return res.toArray(new Long[res.toArray().length]);
    }

    public static Long[] getRoots(long num){
        List<Long> res = new ArrayList<>();

        long tmp = eulerFunc(num);
        Long[] dividers = getComPrime(tmp);
        for (long i = 2; i < num; i++){
            boolean flag = findGCD(i, num) == 1;
            if (flag)
                for (Long j: dividers) {
                    if (fastExp(i, tmp / j, num) == 1) {
                        flag = false;
                        break;
                    }
                }

            if (flag)
                res.add(i);
        }
        return res.toArray(new Long[res.toArray().length]);
    }

    public static long formPublicKey(long x, long g, long p){
        return fastExp(g, x, p);
    }

    public static long[] crypt(byte m, long p, long g, long y, long k){
        long[] res = new long[2];
        res[0] = fastExp(g, k, p);
        int mm = m & 0xFF;
        res[1] = ((fastExp(y, k, p) % p) * (mm % p)) % p;
        return res;
    }

    public static byte decrypt(long a, long b, long x, long p){
        return (byte)( ((b % p) * fastExp(fastExp(a, x, p), p - 2, p)) % p);
    }

    public static boolean isGRootP(long g, long p){
        long tmp = eulerFunc(p);
        Long[] dividers = getComPrime(tmp);
        boolean res = findGCD(g, p) == 1;
        if (res){
            for (Long i: dividers){
                if (fastExp(g, tmp / i, p) == 1) {
                    res = false;
                    break;
                }
            }
        }

        return res;
    }
}



