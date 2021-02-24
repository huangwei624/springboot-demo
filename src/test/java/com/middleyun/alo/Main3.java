package com.middleyun.alo;

public class Main3 {

    public static void main(String[] args) {
        int[] a1 = {1, 2, 3, 1};
        int[] a2 = {2, 7, 9, 3, 1};
        int[] a3 = {2, 1, 4, 5, 3, 1, 1, 3};
        int[] a4 = {};
        System.out.println(process(a1));
    }

    /**
     * 假设数列 a1, a2, a3, ..., a(n) 是符合要求的数列，并且数列和最大; 那么以a(n) 结尾的数列和（sum[n]）满足状态移动方程
     *  sum[n] = nums[n] + max{sum[n-2], sum[n-3]}  (n >=3 )
     * @param nums
     * @return
     */
    public static int process(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length ==1){
            return nums[0];
        }
        if(nums.length ==2){
            return Math.max(nums[0], nums[1]);
        }
        if(nums.length ==3){
            return Math.max(nums[0] + nums[2], nums[1]);
        }
        // 从小到大缓存结果
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        sum[1] = nums[1];
        sum[2] = nums[0] + nums[2];
        for (int i = 3; i < nums.length; i++) {
            sum[i] = nums[i] + Math.max(sum[i - 2], sum[i - 3]);    // 从小到大依次计算
        }
        // 找出sum中的最大值即可
        int max = 0;
        for (int i = 0; i < sum.length; i++) {
            if (max < sum[i]) {
                max = sum[i];
            }
        }
        return max;
    }
}
