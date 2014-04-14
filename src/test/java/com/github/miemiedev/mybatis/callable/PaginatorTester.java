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

public class PaginatorTester extends SimulateBaseDao{

    @Test
    public void controllerMethod() throws IOException {
        Object list = query("yhfl_nbyh", "QLJNWS0102");
        System.out.println(list);

    }

    public Object query(String userType, String branchCode){
        SqlSession session = null;
        try{
            session = getSqlSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userType",userType);
            params.put("branchCode",branchCode);

            Object value =  session.selectOne("db.table.user.query", params);
            System.out.println(params);
            return value;
        }finally {
            session.close();
        }

    }

}
