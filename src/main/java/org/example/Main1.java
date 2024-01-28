package org.example;

import java.util.*;

public class Main1 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] testInputs = readTestInputs();
        boolean boll = false;
        List<Character> tinList = convertStringToCharList("TINKOFF");
            for (int i = 0; i < testInputs.length; i++) {


                List<Character> stringList = new ArrayList<>(convertStringToCharList(testInputs[i]));
                if (stringList.size() == 7) {
                    for (char c : tinList){

                        stringList.remove(Character.valueOf(c));

                    }
                if (stringList.isEmpty()) {
                    boll = true;
                } else {
                    boll = false;
                }



                }else {
                    boll = false;
                }

               if (boll){
                   System.out.println("YES");
               } else {
                   System.out.println("NO");
               }
            }


    }

    public static String[] readTestInputs() {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        String[] testInputs = new String[t];

        for (int i = 0; i < t; i++) {
            testInputs[i] = scanner.nextLine();
        }

        scanner.close();

        return testInputs;
    }

    private static List<Character> convertStringToCharList(String str) {
        List<Character> charList = new ArrayList<>();
        for (char c : str.toCharArray()) {
            charList.add(c);
        }
        return charList;
    }


}




