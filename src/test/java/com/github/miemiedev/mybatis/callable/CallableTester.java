package com.github.miemiedev.mybatis.callable;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miemiedev
 */

public class CallableTester extends SimulateBaseDao{

    @Test
    public void controllerMethod() {
        Map<String, Object> map = query("yhfl_nbyh", "QLJNWS0102");
        System.out.println(map);
    }

    public Map<String, Object> query(String userType, String branchCode){
        SqlSession session = null;
        try{
            session = getSqlSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userType",userType);
            params.put("branchCode",branchCode);
            //存储过程返回多个参数，则使用selectOne返回Map
            //key为返回参数名，value为返回值
            Map<String, Object> value =  session.selectOne("db.table.user.query", params);
            return value;
        }finally {
            session.close();
        }
    }

    @Test
    public void controllerMethod2() {
        List<Map<String, Object>> list = query2("yhfl_nbyh", "QLJNWS0102");
        System.out.println(list);
    }

    public List<Map<String, Object>> query2(String userType, String branchCode){
        SqlSession session = null;
        try{
            session = getSqlSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userType",userType);
            params.put("branchCode",branchCode);
            //存储过程只返回一个游标，则使用selectList返回List
            List<Map<String, Object>> value =  session.selectList("db.table.user.query2", params);
            return value;
        }finally {
            session.close();
        }
    }

    @Test
    public void controllerMethod3() {
        Integer returnCode = query3("yhfl_nbyh", "QLJNWS0102");
        System.out.println(returnCode);
    }

    public Integer query3(String userType, String branchCode){
        SqlSession session = null;
        try{
            session = getSqlSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userType",userType);
            params.put("branchCode",branchCode);
            //存储过程只有一个返回值，并且不是游标，则使用selectOne
            Integer value =  session.selectOne("db.table.user.query3", params);
            return value;
        }finally {
            session.close();
        }
    }

}
