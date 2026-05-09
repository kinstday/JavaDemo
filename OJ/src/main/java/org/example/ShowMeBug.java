package org.example;

/**
 * @author 12
 * Create By 下午9:17
 */
import java.util.*;

// 可以引⼊的库和版本相关请参考 “环境说明”
// 请勿更改 `ShowMeBug` 类名以防执⾏失败

public class ShowMeBug {

    // 本题面试官已设置测试用例
    public static void main(String[] args) {
        // 在这⾥写代码
        Map<Integer,Integer> map = new  HashMap();

        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,5);
        map.put(5,7);
        map.put(6,10);

        int point = 1;
        int start = 2;
        int[]pointArr = new int[]{0,1,2,3,5,8,13,21};
        while(start<10){
            start++;
            int sum = 2;
            if(start>4){
                sum = sum-pointArr[point];
                point++;
            }
            for(int i = start-2 ;i>0;i--){
                sum += map.get(i);
            }
            map.put(start,sum);

        }

    }
}
