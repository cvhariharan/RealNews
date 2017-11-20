/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hariharan.textsummarizer;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author thero
 */
public class ValueComparator implements Comparator {
    
    Map map;
    public ValueComparator(Map map)
    {
        this.map = map;
    }
    @Override
    public int compare(Object a,Object b)
    {
        if((Double)map.get(a) > (Double)map.get(b))
            return -1;
        else if((Double)map.get(a) == (Double)map.get(b))
            return 0;
        else
            return 1;
    }
    
}
