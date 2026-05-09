package org.example;

import java.io.*;
import java.util.*;

public class Main {

    //现场编程题题目内容：
    //输入一系列字符串，将这些字符串排序：数字按从小到大排序，非数字按字典顺序，数字排在非数字后面。
    //输入：2, 1, b, a
    //输出：a, b, 1, 2
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        List<String> res1 = new ArrayList<>();
        List<Integer> res2 = new ArrayList<>();
        for(Character c:str.toCharArray()){
            if(Character.isDigit(c)){
                res2.add(Integer.parseInt(c+""));
            }else if(Character.isLetter(c)){
                res1.add(c+"");
            }
        }
        res1.sort(String::compareTo);
        res2.sort(Comparator.comparingInt(o -> o));
        StringJoiner sj = new StringJoiner(",");
        for(String s:res1){
            sj.add(s);
        }
        for(Integer i:res2){
            sj.add(i+"");
        }
        System.out.println(sj);
    }

}