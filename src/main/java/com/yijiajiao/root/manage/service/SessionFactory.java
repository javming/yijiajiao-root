package com.yijiajiao.root.manage.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-16:54
 */
public class SessionFactory {
    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                String resource = "mybatis-config.xml";
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
                return sqlSessionFactory;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSessionFactory;
    }

    public static void commit(SqlSession session){
        if (session != null ){
            session.commit();
            session.close();
        }
    }

    public static void rollBack(SqlSession session){
        if (session != null){
            session.rollback();
            session.close();
        }
    }

    public static void close(SqlSession session){
        if (session != null){
            session.close();
        }
    }
}
