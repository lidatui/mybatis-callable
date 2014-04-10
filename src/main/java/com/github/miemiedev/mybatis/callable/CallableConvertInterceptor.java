package com.github.miemiedev.mybatis.callable;

import com.github.miemiedev.mybatis.callable.handler.CallableResultHandler;
import com.github.miemiedev.mybatis.callable.handler.SimpleResultHandler;
import com.github.miemiedev.mybatis.callable.support.PropertiesHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 存储过程调用规则
 * Created by limiemie on 2014/3/26.
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class CallableConvertInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(CallableConvertInterceptor.class);
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    CallableResultHandler callableResultHandler = new SimpleResultHandler();

    public Object intercept(Invocation invocation) throws Throwable {
        final Executor executor = (Executor) invocation.getTarget();
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];

        if(!ms.getStatementType().equals(StatementType.CALLABLE)){
            return invocation.proceed();
        }

        BoundSql boundSql = ms.getBoundSql(parameter);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if(parameter instanceof Map){
            parameterMap = (Map)parameter;
        }else{
            for (ParameterMapping parameterMapping : parameterMappings) {
                if(parameterMapping.getMode().equals(ParameterMode.IN) || parameterMapping.getMode().equals(ParameterMode.INOUT)){
                    parameterMap.put(parameterMapping.getProperty(),parameter);
                }
            }
        }

        for (ParameterMapping parameterMapping : parameterMappings) {
            if(parameterMapping.getMode().equals(ParameterMode.OUT)){
                parameterMap.put(parameterMapping.getProperty(),null);
            }
        }

        queryArgs[PARAMETER_INDEX] = parameterMap;

        invocation.proceed();

        return callableResultHandler.proceed(parameterMappings, parameterMap);
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        PropertiesHelper propertiesHelper = new PropertiesHelper(properties);
        String dialectClass = propertiesHelper.getProperty("resultHandler","com.github.miemiedev.mybatis.callable.handler.SimpleResultHandler");
        try {
            setResultHandler((CallableResultHandler) Class.forName(dialectClass).newInstance());
        } catch (Exception e) {
            throw new RuntimeException("cannot create resultHandler instance by resultHandler:"+dialectClass,e);
        }
    }

    public void setResultHandler(CallableResultHandler resultHandler) {
        logger.debug("resultHandler: {} ", resultHandler.getClass().getName());
        this.callableResultHandler = resultHandler;
    }
}
