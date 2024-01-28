package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        int num1 = scanner.nextInt();
//        int num2 = scanner.nextInt();
//        System.out.println(num1 + num2);
        scanner.close();
//        System.out.println(compress(new char[]{'a','b','b','c','c','c','c','c','c','c','c','c','c','c','c','c'}));;
        System.out.println(compress(new char[]{'a','a','b','b','c','c','c'}));;
    }


    public static int compress(char[] chars) {

        StringBuilder sb = new StringBuilder();
        char lastChar = '_';
        int i = 0;
        int count = 1;

        for (char ch : chars){


            if (ch != lastChar){
                if (count > 1) {
                   sb.append(count);
                    for (char cha : String.valueOf(count).toCharArray()){
                        chars[i]  = cha;
                        i++;
                    }

                }
                sb.append(ch);
                chars[i] = ch;
                count = 1;
                lastChar = ch;
                i++;
            } else {
                count++;
            }
        }
        if (count > 1){
            sb.append(count);
            for (char cha : String.valueOf(count).toCharArray()){
                chars[i]  = cha;
                i++;
            }
        }
        

        chars = sb.toString().toCharArray();

        return chars.length;


    }




    }