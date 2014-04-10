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
        User user = new User();
        user.setId(2950L);
        user.setName("libin");
        user.setType("yhfl_nbyh");
        Object list = queryTotalCountClassBar(user);

        System.out.println(list);

    }

    public Object queryTotalCountClassBar(User user){
        SqlSession session = null;
        try{
            session = getSqlSession();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user",user);
            params.put("branchCode","QLJNWS0102");

            Object value =  session.selectList("db.table.user.queryTotalCountClassBar", params);
            System.out.println(params);
            return value;
        }finally {
            session.close();
        }

    }

}
