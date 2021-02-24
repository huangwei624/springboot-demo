package com.middleyun.alo;

import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
//        List<double[]> points = Arrays.asList(new double[][]{{0, 0}, {0, 1}, {1, 1}, {1, 0}});
//        double[] point = {0.5, 0.5};
//        List<double[]> points = Arrays.asList(new double[][]{{0, 0}, {0, 1}, {1, 1}, {1, 0}});
//        double[] point = {1.5, 0.5};
        List<double[]> points = Arrays.asList(new double[][]{{0, 0}, {1, 1}, {2, 0.5}, {3, 1}, {4, 0}});
        double[] point = {2, 0.75};
        System.out.println(contains(points, point));
    }

    /**
     * 算法主要用到了多边形中一点向外引射线会与多变形相交奇数个交点，多边型外会有偶数个交点，
     * 算法通过特殊选点方式，将引出的射线构成一个向右侧延伸的平行与x轴的射线，
     * @param points
     * @param point
     * @return
     */
    public static boolean contains(List<double[]> points, double[] point){
        int pointSize = points.size();
        int crossPointCount = 0;
        for (int i = 0; i < pointSize; ++ i) {
            double[] x1 = points.get(i);
            double[] x2;
            if (i != pointSize - 1) {
                x2 = points.get(i + 1);
            } else { // 这里保证最后一个点和第一个点构成线段
                x2 = points.get(0);
            }

            // 这里取一点（m, point[1]）的点(m > point[0])，与point点构成与x轴平行的线, 这里比较特殊
            if (point[1] < Math.min(x1[1], x2[1]) || point[1] > Math.max(x1[1], x2[1])) {
                continue;
            }
            // 根据两相交直线，求出交点的横坐标 x， 这里需要推一下
            double x = ((point[1] - x2[1]) * (x2[0] - x1[0])) / (x2[1] - x1[1]) + x2[0];

            // 因为这里的m > point[0], 所以取得线是向右延伸的，如果点在内部， 需要满足下面条件
            if ( x > point[0] ) {
                crossPointCount++;
            }
        }
        // 如果交点个数为偶数，点在多边形之外，否则在内部；
        return (crossPointCount % 2 == 1);
    }
}
