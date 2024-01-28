package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main6 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        int n = Integer.parseInt(tokenizer.nextToken());
        int q = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int[] lazyAdd = new int[n * 4];
        int[] minVals = new int[n * 4];

        Stack<Pair> stack1 = new Stack<>();
        Stack<Pair> stack2 = new Stack<>();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < q; i++) {
            tokenizer = new StringTokenizer(reader.readLine());

            int type = Integer.parseInt(tokenizer.nextToken());
            if (type == 1) {
                int l = Integer.parseInt(tokenizer.nextToken());
                int r = Integer.parseInt(tokenizer.nextToken());
                int x = Integer.parseInt(tokenizer.nextToken());

                update(1, 1, n, l, r, x, lazyAdd, minVals);
            } else {
                int l = Integer.parseInt(tokenizer.nextToken());
                int r = Integer.parseInt(tokenizer.nextToken());
                int k = Integer.parseInt(tokenizer.nextToken());
                int b = Integer.parseInt(tokenizer.nextToken());

                stack1.clear();
                stack2.clear();

                query(1, 1, n, l, r, k, b, minVals, lazyAdd, stack1, stack2);

                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }

                while (!stack2.isEmpty()) {
                    Pair pair = stack2.pop();
                    result.append(pair.y).append(" ");
                }
                result.append("\n");
            }
        }

        System.out.println(result);
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static void push(int v, int tl, int tr, int[] lazyAdd, int[] minVals) {
        if (lazyAdd[v] != 0) {
            int tm = (tl + tr) / 2;
            lazyAdd[v * 2] += lazyAdd[v];
            lazyAdd[v * 2 + 1] += lazyAdd[v];
            minVals[v * 2] += lazyAdd[v];
            minVals[v * 2 + 1] += lazyAdd[v];
            lazyAdd[v] = 0;
        }
    }

    static void update(int v, int tl, int tr, int l, int r, int x, int[] lazyAdd, int[] minVals) {
        if (l > r) {
            return;
        }
        if (l == tl && r == tr) {
            lazyAdd[v] += x;
            minVals[v] += x;
        } else {
            push(v, tl, tr, lazyAdd, minVals);
            int tm = (tl + tr) / 2;
            update(v * 2, tl, tm, l, Math.min(r, tm), x, lazyAdd, minVals);
            update(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, x, lazyAdd, minVals);
            minVals[v] = Math.min(minVals[v * 2], minVals[v * 2 + 1]);
        }
    }

    static void query(int v, int tl, int tr, int l, int r, int k, int b, int[] minVals, int[] lazyAdd, Stack<Pair> stack1, Stack<Pair> stack2) {
        if (l > r) {
            return;
        }
        if (l == tl && r == tr) {
            stack1.push(new Pair(minVals[v], b + k * tl));
        } else {
            push(v, tl, tr, lazyAdd, minVals);
            int tm = (tl + tr) / 2;
            query(v * 2, tl, tm, l, Math.min(r, tm), k, b, minVals, lazyAdd, stack1, stack2);
            query(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, k, b, minVals, lazyAdd, stack1, stack2);
        }
    }
}