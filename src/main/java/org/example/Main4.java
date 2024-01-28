package org.example;


import java.util.Scanner;

public class Main4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int blockCount = scanner.nextInt();

        for (int blockIndex = 0; blockIndex < blockCount; blockIndex++) {
            int digitCount = scanner.nextInt();

            int min1 = 1000000000;
            int min2 = 1000000000;
            int max = 0;

            for (int i = 0; i < digitCount; i++) {
                int currentDigit = scanner.nextInt();
                if (currentDigit < min1) {
                    min2 = min1;
                    min1 = currentDigit;
                } else if (currentDigit < min2) {
                    min2 = currentDigit;
                }
                if (currentDigit > max) {
                    max = currentDigit;
                }
            }
            boolean ans = digitCount <= min1 + min2 || max >= Math.ceil((double) digitCount / 2);
            if (ans) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }

        }
        scanner.close();
    }

}