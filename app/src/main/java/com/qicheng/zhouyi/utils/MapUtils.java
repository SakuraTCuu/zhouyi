package com.qicheng.zhouyi.utils;

import java.util.Map;

public class MapUtils {

    public static String Map2String(Map<String,Object> map){
           String result = "?";
           boolean flag =false;
           for(Map.Entry<String,Object> entry :map.entrySet()){
               if(entry.getKey()!=null){
                   if(flag){
                       result+="&";
                   }
                   flag = true;
                   result += entry.getKey();
                   result +="=";
                   result += entry.getValue();
               }
           }
           return result;
    }
}
