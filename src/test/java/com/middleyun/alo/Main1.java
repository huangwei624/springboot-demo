package com.middleyun.alo;

public class Main1 {

    public static void main(String[] args) {
        int[] a = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1};
//        int[] a = {1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0};
        int k = 2;
        System.out.println(longestZeros(a, k));
    }

    /**
     * 算法思路：定义两个指针 i, j, (两层循环), 0<=i<=A.length, j在i位置上向右摸索,如果j位置指向最后一个数（比较特殊的情况），进行响应判断判断；
     * ，如果j指向的不是最后一个数据，然后看A[j]的值是1还是0：如果是0跳过；如果是1看K是否已经使用完, 如果使用完了，进行result比较，然后结束这一次的计算，
     * 如果K未使用完接着循环；
     * @param A
     * @param K
     * @return
     */
    private static int longestZeros(int[] A, int K) {
        if (A == null || A.length == 0) {
            return 0;
        }
        if (K < 0) {
            throw new RuntimeException("参数 K 不合法");
        }
        int result = 0;
        int _k = K;
        for (int i = 0; i < A.length; i++) {
            for (int j = i; j < A.length; j++) {
                // 如果j 指针指向了最后一个数据
                if (j == A.length - 1) {
                    if (A[j] == 0) {
                        result = Math.max(result, j - i + 1);
                    } else {
                        result = _k == 0 ? Math.max(result, j - i) : Math.max(result, j - i + 1);
                    }
                    break;
                }
                // j指针指向的数据不是0, 但是_k 已将使用完了，比较，结束内层循环
                if (A[j] != 0 && _k == 0) {
                    result = Math.max(result, j - i);
                    break;
                }
                // 使用将1 变成0 的机会
                if (A[j] != 0) {
                    _k--;
                }
            }
            _k = K;
        }
        return result;
    }
}