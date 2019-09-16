package com.itmuch.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NioTest7 {

    public static void main1(String args[]) throws Exception{
        RandomAccessFile file = new RandomAccessFile("niotest7.txt", "rw");

        FileChannel channel = file.getChannel();

        MappedByteBuffer buffer  =  channel.map(FileChannel.MapMode.READ_WRITE,0, 5 );

        buffer.put(0, (byte)'a');
        buffer.put(0, (byte)'b');

        file.close();
    }


    public static void main2(String args[]) throws Exception{
        RandomAccessFile file = new RandomAccessFile("niotest8.txt", "rw");

        FileChannel channel = file.getChannel();

        FileLock lock = channel.lock(3, 6, true);

    }


    public static void main3(String args[]) throws Exception{
        String str = "aaabbbcccddd";
        if(str.length() == 0){
            System.out.println("{}");
            return;
        }
        //分组分类
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < str.length(); ){
            char temp = str.charAt(i);
            int time = 1;
            for (int j = i+1; j < str.length(); j++){
                if(temp == str.charAt(j)){
                    time ++;
                    continue;
                }else{
                    break;
                }
            }
            if(map.containsKey(temp)){
                if(map.get(temp) < time)
                    map.put(temp, time );
            }else{
                map.put(temp, time);
            }
            i = i + time;
        }
        //取出最大的
        List<Map.Entry<Character, Integer>> maxs = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()){
            int time = entry.getValue();
            int max = 0;
            if(!maxs.isEmpty()){
                max = maxs.get(0).getValue();
            }
            if(time > max){
                maxs.clear();
                maxs.add(entry);
            }else if(time == max){
                maxs.add(entry);
            }
        }
        //拼接结果
        StringBuilder temp = new StringBuilder("{");
        for (Map.Entry<Character, Integer> entry : maxs){
            temp.append("'"+entry.getKey()+"':");
            temp.append(entry.getValue() + ",");
        }
        String result = temp.substring(0, temp.length()-1);
        result += "}";
        System.out.println(result);

    }

    public static void main(String args[]) throws Exception{
        String str = "1,2,3,5,4,3,2";
        String [] chs = str.split(",");
        int len = chs.length;
        List<String> sequence = new ArrayList<>();
        int pre = 0, after = 0;
        int loop = 0;
        while(pre <= len){
            int afterVal = Integer.valueOf(chs[after]);
            int preVal = Integer.valueOf(chs[++pre]);
            if((afterVal + ++loop ) != preVal){
                if(loop > 1){
                    sequence.add(afterVal + "~" + (afterVal + loop -1) +",");
                }else{
                    sequence.add(afterVal + ",");
                }
                after = pre;
                loop = 0;

            }
        }

//        for (int i = 0; i < chs.length;){
//            int loop = 0;
//            first = Integer.valueOf(chs[i]);
//            second = Integer.valueOf(chs[i+1]);
//            if(first + ++loop  != second){
//                if(loop > 1){
//                    sequence.add(first + "~" + (first + loop - 1) +",");
//                }else{
//                    sequence.add(first + ",");
//                }
//                i += loop;
//                break;
//            }else{
//                second = Integer.valueOf(chs[++i]);
//            }
//        }
        StringBuilder sb = new StringBuilder();
        for (String val : sequence){
            sb.append(val);
        }
        String result = sb.substring(0, sb.length() -1);
        System.out.println(result);
    }

}
