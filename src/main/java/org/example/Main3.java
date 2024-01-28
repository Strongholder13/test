package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main3 {

    public static void main(String[] args) {

        readGiftList();

    }

    public static List<Integer> readGiftList() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        List<Integer> giftPrices = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            giftPrices.add(scanner.nextInt());
        }
        int max = 0;
        for (int i = 0; i <= m; i++) {
            int summ = i;
            for (Integer gift : giftPrices) {
                if (summ - gift >= 0) {
                    summ = summ - gift;
                }
            }
            if (summ > max) {
                max = summ;
            }
        }

        System.out.println(max);
        scanner.close();

        return giftPrices;
    }
}