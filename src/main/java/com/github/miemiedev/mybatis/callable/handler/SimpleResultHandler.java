package com.github.miemiedev.mybatis.callable.handler;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.type.JdbcType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单游标结果集
 * Created by limiemie on 2014/3/26.
 */
public class SimpleResultHandler implements CallableResultHandler {
    public Object proceed(List<ParameterMapping> parameterMappings, Map<String, Object> result) {
        List list = new ArrayList();
        for (ParameterMapping parameterMapping : parameterMappings){
            if(ParameterMode.OUT.equals(parameterMapping.getMode()) || ParameterMode.INOUT.equals(parameterMapping.getMode())){
                list.add(result.get(parameterMapping.getProperty()));
            }
        }
        return list;
    }
}
