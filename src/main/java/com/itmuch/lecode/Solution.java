package com.itmuch.lecode;

import java.util.*;

public class Solution {



    public static void main(String args[]) throws Exception{
//        int target = 8;
//        int [] arr = new int[] {2,3,5};
//        List<List<Integer>> result = combinationSum(arr, target);
//        System.out.println(result.toString());
        System.out.println(isParallel("1234321", new HashMap<>()));
//        longestSte("432152373251234aaaaaaaabbbbbbbbbbbbbbbbbaaaaaaaa");
//        float a = 2;
//        int b = 5;
//        float c = b/2f;
//        System.out.println(String.valueOf(c).contains("."));
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        Arrays.sort(candidates); // 剪枝
        dfs(candidates, resultList, result, 0, target);
        return resultList;
    }

    private static void dfs(int[] candidates, List<List<Integer>> resultList, List<Integer> result, int start, int target) {
        if (target < 0){            // target不符合
            return;
        }else if (target == 0){   // target符合
            resultList.add(new ArrayList<>(result));
        }else {                  // 继续进行数的查找
            for (int i = start; i < candidates.length; i++){
                result.add(candidates[i]);
                dfs(candidates, resultList, result, i, target - candidates[i]);
                result.remove(result.size() - 1);     // 数查找完后要进行回溯
            }
        }
    }

    /**
     * [最长的][连续的]回文字符串.
     * @param str
     */
    public static void longestStr(String str){
        List<Integer> list = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        boolean put = true;
        int length = 0;
        for(int j = 0; j < str.length(); j++){
            for(int i = j; i < str.length(); i++){
                char temp = str.charAt(i);
                if(put){
                    //如果stack里面没有元素,则直接放进去
                    if(stack.isEmpty()){
                        stack.push(temp);
                        length++;
                        continue;
                    }
                    Character peek = stack.pop();
                    if(peek + 1 == temp){
                        stack.push(peek);
                        stack.push(temp);
                        length++;
                    }else {
                        if(stack.isEmpty()){
                            length = 0;
                            stack.empty();
                            break;
                        }
                        Character pop2 = stack.pop();
                        if(pop2 == temp){
                            put = false;
                            continue;
                        }else{
                            length = 0;
                            stack.empty();
                            break;
                        }
                    }
                }else{
                    if(stack.isEmpty()){
                        if(length > 0)
                        list.add(length);
                        length = 0;
                        stack.empty();
                        put = true;
                        break;
                    }
                    Character pop2 = stack.pop();
                    if(pop2 == temp){
                        continue;
                    }
                }
            }
        }
        System.out.println(list.toString());
    }

    /**
     * 最长的回文串
     * @param str
     */
    private static void longestSte2(String str){
        Map<String, Boolean> strMap = new HashMap<>();
        int max = 0;
        String maxStr = "";
        for(int i = 0; i <= str.length(); i++){
            for(int j = i+1; j <= str.length(); j++){
                String subStr = str.substring(i, j);
                if(isParallel(subStr, strMap) && subStr.length() > max){
                    max = subStr.length();
                    maxStr = subStr;
                }
            }
        }
        System.out.println(max);
        System.out.println(maxStr);
    }

    /**
     * 判断是否是回文串
     * @param str
     * @return
     */
    private static boolean isParallel(String str, Map<String, Boolean> map){
        if(map.containsKey(str)) return map.get(str);
        if(null == str || "".equals(str)) return false;
        if(str.length() <= 2) return false;
        //双指针
        int i ,j;
        if(str.length() % 2 == 0){
            j = str.length() / 2 ;
            i = j - 1;
        }else{
            int middle = str.length() / 2;
            j = middle + 1;
            i = middle - 1;
        }
        while(i > 0){
            char head = str.charAt(i--);
            char tail = str.charAt(j++);
            if(head != tail){
                map.put(str, false);
                return  false;
            }
        }
        map.put(str, true);
        return true;
    }
}
