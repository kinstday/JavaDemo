package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author 12
 * Create By 下午3:20
 */
public class dfs {

    static int n, m;
    static List<Integer> list = new ArrayList<>();
    static HashSet<Long> hashSet = new HashSet<>();
    static void dfs (int index, long sum) {
        if (index == n) {
            hashSet.add(sum);
            return ;
        }
        dfs(index + 1, sum);
        dfs(index + 1, (sum + list.get(index))%m);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        n = Integer.parseInt(str.split(" ")[0]);
        m = Integer.parseInt(str.split(" ")[1]);
        StringTokenizer st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        dfs(0, 0);
        hashSet.add(0L);
        System.out.println(Collections.max(hashSet));
    }
}
