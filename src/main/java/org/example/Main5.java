package org.example;

import java.util.*;

public class Main5 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.nextLine();
        List<String> interestingCompanies = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            String company = scanner.nextLine();
            interestingCompanies.add(company);
        }

        List<Node> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            int parent = scanner.nextInt();
            int cost = scanner.nextInt();
            String company = scanner.next();
            scanner.nextLine();
            tree.add(new Node(parent, cost, company));
        }

        HashMap<Integer, HashMap> nodeMap = new HashMap<>();
        for (Node node : tree) {
            HashMap<String, Integer> costMap = new HashMap<>();
            int par = node.parent;
            costMap.put(node.company, node.cost);
            if (nodeMap.containsKey(par)) {
                HashMap<String, Integer> curmap = nodeMap.get(par);
                if (curmap.containsKey(node.company)) {
                    curmap.put(node.company, Math.min(curmap.get(node.company), node.cost));

                } else {
                    curmap.put(node.company, node.cost);
                }


            } else {
                nodeMap.put(par, costMap);
            }
        }
        int sum = -1;
        for (Map.Entry<Integer, HashMap> entry : nodeMap.entrySet()) {

            int curSum = 0;
            HashMap<String, Integer> innerMap = entry.getValue();

            
            if (!(innerMap.values().size() < interestingCompanies.size())) {
                for (String key : interestingCompanies) {

                    if (innerMap.containsKey(key)) {
                        curSum += innerMap.get(key);
                    } else {
                        break;
                    }


                }
                if (sum != -1) {
                    sum = Math.min(sum, curSum);
                } else {
                    sum = curSum;
                }

            }

        }

        System.out.println(sum);


        scanner.close();
    }

    static class Node {
        int parent;
        int cost;
        String company;

        public Node(int parent, int cost, String company) {
            this.parent = parent;
            this.cost = cost;
            this.company = company;
        }
    }
}



