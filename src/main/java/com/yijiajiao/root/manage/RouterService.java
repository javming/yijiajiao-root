package com.yijiajiao.root.manage;

import com.yijiajiao.root.manage.mapper.RouterMapper;
import com.yijiajiao.root.manage.model.RouterModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-10-14:02
 */
public class RouterService {

    private static Logger log = LoggerFactory.getLogger(RouterService.class);
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

    /**
     * 批量插入
     */
    public static void addRouters(List<RouterModel> routers) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.addRouters(routers);
            log.info("插入成功！");
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }

    }

    /**
     * 单条插入
     */
    public static void addRouter(RouterModel router){
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.addRouter(router);
            log.info("插入成功！");
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }

    /**
     * 全部
     */
    public static List<RouterModel> routers(){
        SqlSession sqlSession;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routers();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 详情
     */
    public static RouterModel routerDetail(int requestId){
        SqlSession sqlSession;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routerDetail(requestId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改
     */
    public static void updateRouter(RouterModel param){
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.updateRouter(param);
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    public static List<RouterModel> routersByConditions(RouterModel param){

        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routersByConditions(param);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }

    public static void remove(Integer requestId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSessionFactory().openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.delete( requestId );
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.close();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
