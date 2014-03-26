package com.github.miemiedev.mybatis.callable;
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

        Object list = queryTotalCountClassBar(user);

        System.out.println(list);

    }

    public Object queryTotalCountClassBar(User user){
        SqlSession session = null;
        try{
            session = getSqlSession();
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("userId",userId);

            return session.selectList("db.table.user.queryTotalCountClassBar", 2950L);
        }finally {
            session.close();
        }

    }

    public class User{
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
