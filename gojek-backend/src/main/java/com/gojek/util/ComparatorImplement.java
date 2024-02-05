package com.gojek.util;

import com.gojek.client.JsonMapper;

import java.util.Map;

public class ComparatorImplement implements IComparator{

    /*
    Compares two Json Responses by converting them to map
     */
    public boolean compare(String leftJson, String rightJson) {

        try {
            Map<String, Object> responseMap1 = JsonMapper.jsonToMap(leftJson);
            Map<String, Object> responseMap2 = JsonMapper.jsonToMap(rightJson);
            return equalityCheck(responseMap1, responseMap2);
        } catch (Exception e) {
            System.out.println("Error Processing JSON Response");
            System.out.println(e.getMessage());
            return false;
        }
    }


    private boolean equalityCheck(Map<String, Object> leftMap, Map<String, Object> rightMap) {
        //maps are not equal if they don't have same number of keys
        if (leftMap.keySet().size() != rightMap.keySet().size()) {
            return false;
        }

        for (Object keyFromLeftMap : leftMap.keySet()) {
            if (rightMap.containsKey(keyFromLeftMap)) {
                Object valueFromLeftMap = leftMap.get(keyFromLeftMap);
                Object valueFromRightMap = rightMap.get(keyFromLeftMap);
                if (!valueFromLeftMap.equals(valueFromRightMap)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}


