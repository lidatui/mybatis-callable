package com.github.miemiedev.mybatis.callable.handler;

import org.apache.ibatis.mapping.ParameterMapping;

import java.util.List;
import java.util.Map;

/**
 * 结果处理器
 * Created by limiemie on 2014/3/26.
 */
public interface CallableResultHandler {
    public Object proceed(List<ParameterMapping> parameterMappings, Map<String,Object> result);
}
