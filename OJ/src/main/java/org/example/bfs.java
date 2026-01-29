package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 12
 * Create By 下午2:58
 */
public class bfs {

    static int n, m;
    static char[][] grid;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static boolean[][] bfs(int x, int y) {
        boolean[][] vis = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        vis[x][y] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int r = cur[0] + dr[i];
                int c = cur[1] + dc[i];
                if (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == '.' && !vis[r][c]) {
                    vis[r][c] = true;
                    q.add(new int[]{r, c});
                }
            }
        }
        return vis;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String mn = br.readLine();
        m = Integer.parseInt(mn.split(" ")[0]);
        n = Integer.parseInt(mn.split(" ")[1]);
        grid = new char[m][n];
        int startX = 0, startY = 0;
        int endX = 0, endY = 0;
        for (int i = 0; i < m; i++) {
            grid[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] == 'E') {
                    endX = i;
                    endY = j;
                }
            }
        }
        boolean[][] visStart = bfs(startX, startY);
        if (visStart[endX][endY]) {
            System.out.println("YES");
            return;
        }
        boolean[][] visEnd = bfs(endX, endY);
        boolean[] rowXCanReach = new boolean[m];
        boolean[] colYCanReach = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visStart[i][j]) {
                    rowXCanReach[i] = true;
                    colYCanReach[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visEnd[i][j]) {
                    //存在相邻列的场景
//                    if (rowXCanReach[i] || colYCanReach[j]) {
//                        System.out.println("YES");
//                        return;
//                    }
                    // 检查其自身及相邻行/列是否能被S区域的激光覆盖
                    boolean canBeReached = false;
                    for (int dr = -1; dr <= 1; dr++) {
                        if (i + dr >= 0 && i + dr < m && rowXCanReach[i + dr]) {
                            canBeReached = true;
                            break;
                        }
                    }
                    if (canBeReached) {
                        System.out.println("YES");
                        return;
                    }
                    for (int dc = -1; dc <= 1; dc++) {
                        if (j + dc >= 0 && j + dc < n && colYCanReach[j + dc]) {
                            canBeReached = true;
                            break;
                        }
                    }
                    if (canBeReached) {
                        System.out.println("YES");
                        return;
                    }
                }
            }
        }
        System.out.println("NO");
    }
}
