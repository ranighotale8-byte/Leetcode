class Solution {

    int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) {
            return 0;
        }
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    q.offer(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];
            
            for (int[] d : dir) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] > dist[r][c] + 1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        int left = 0, right = 2 * n, ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (isValidPath(dist, mid, n)) {
                ans = mid; 
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }
    public boolean isValidPath(int[][] dist, int safeFactor, int n) {
        if (dist[0][0] < safeFactor) {
            return false;
        }

        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        
        q.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];
            if (r == n - 1 && c == n - 1) {
                return true;
            }
            for (int[] d : dir) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc] && dist[nr][nc] >= safeFactor) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }
}