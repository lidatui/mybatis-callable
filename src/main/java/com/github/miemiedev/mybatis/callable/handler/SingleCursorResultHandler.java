package com.github.miemiedev.mybatis.callable.handler;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单游标结果集
 * Created by limiemie on 2014/3/26.
 */
public class SingleCursorResultHandler implements CallableResultHandler {
    public Object proceed(List<ParameterMapping> parameterMappings, Map<String, Object> result) {
        List list = new ArrayList();
        for (ParameterMapping parameterMapping : parameterMappings){
            if(parameterMapping.getJdbcType().equals(JdbcType.CURSOR)){
                list = (List) result.get(parameterMapping.getProperty());
                break;
            }
        }
        return list;
    }
}
