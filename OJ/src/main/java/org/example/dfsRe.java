package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author 12
 * Create By 下午3:46
 */
public class dfsRe {

    static int n, total = 0;
    static List<Integer> list = new ArrayList<>();

    static boolean dfs(int index, int current) {
        if (index == n) {
            return current == 0;
        }
        return dfs(index + 1, current + list.get(index)) | dfs(index + 1, current - list.get(index));
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        n = Integer.parseInt(str);

        StringTokenizer st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            int token = Integer.parseInt(st.nextToken());
            if (token % 5 == 0) {
                n--;
                total += token;
            } else if (token % 3 == 0) {
                n--;
                total -= token;
            } else {
                list.add(token);
            }
        }

        System.out.println(dfs(0, total));
    }
}
