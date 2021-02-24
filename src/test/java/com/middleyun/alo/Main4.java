package com.middleyun.alo;

public class Main4 {

    public static void main(String[] args) {
        int input = 6;
        System.out.println(process(input));
    }

    /**
     * 可以想到的是第n个数一定是由前n个数中的某个数 *3 / *5 / *7 得到的, 这就可以考虑使用动态规划；
     **/
    public static int process(int n) {
        if (n <= 0) {
            throw new RuntimeException("参数不合法");
        }
        if (n == 1) {
            return 1;
        }
        // num中保存了所有满足条件的数,
        int[] nums = new int[n];
        nums[0] = 1;

        // 定义三个指针，分别指向的 nums[0] 的位置, 使用动态规划依次计算第i个满足题意的数
        int a = 0, b = 0, c = 0;
        for (int i = 1; i < n; i++) {
            // 从当前三个指针位置 *3 / *5 / *7 获取一个最小值
            nums[i] = Math.min(nums[c] * 7, Math.min(nums[a] * 3, nums[b] * 5));

            // 如果第i个数是由 nums[a] * 3 算出来的，那么第 i+1 个数必然是 min{ num[a+1]*3, num[b], num[c] }, 依次类推, b和c同理
            if (nums[i] == nums[a] * 3) {
                a++;
            }
            if (nums[i] == nums[b] * 5) {
                b++;
            }
            if (nums[i] == nums[c] * 7) {
                c++;
            }
        }
        return nums[n - 1];
    }
}
