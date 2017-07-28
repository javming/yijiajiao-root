package com.yijiajiao.root.manage.service;

import com.yijiajiao.root.manage.mapper.RouterMapper;
import com.yijiajiao.root.manage.model.RouterModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-10-14:02
 */
public class RouterService {

    private static Logger log = LoggerFactory.getLogger(RouterService.class);

    private static SqlSessionFactory factory = SessionFactory.getSqlSessionFactory();
    /**
     * 批量插入
     */
    public static void addRouters(List<RouterModel> routers) {
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.addRouters(routers);
            log.info("插入成功！");
            SessionFactory.commit(sqlSession);
        }catch (Exception e){
            SessionFactory.rollBack(sqlSession);
            e.printStackTrace();
        }

    }

    /**
     * 单条插入
     */
    public static void addRouter(RouterModel router){
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.addRouter(router);
            log.info("插入成功！");
            SessionFactory.commit(sqlSession);
        }catch (Exception e){
            SessionFactory.rollBack(sqlSession);
            e.printStackTrace();
        }

    }

    /**
     * 全部
     */
    public static List<RouterModel> routers(){
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routers();
        }catch (Exception e){
            e.printStackTrace();
            SessionFactory.close(sqlSession);
            return null;
        }
    }

    /**
     * 详情
     */
    public static RouterModel routerDetail(int requestId){
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routerDetail(requestId);
        } catch (Exception e) {
            e.printStackTrace();
            SessionFactory.close(sqlSession);
            return null;
        }
    }

    /**
     * 修改
     */
    public static void updateRouter(RouterModel param){
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.updateRouter(param);
            SessionFactory.commit(sqlSession);
        }catch (Exception e){
            SessionFactory.rollBack(sqlSession);
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> routersByConditions(RouterModel param){

        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            return routerMapper.routersByConditions(param);
        }catch (Exception e){
            e.printStackTrace();
            SessionFactory.rollBack(sqlSession);
            return null;
        }
    }

    public static void remove(Integer requestId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = factory.openSession();
            RouterMapper routerMapper = sqlSession.getMapper(RouterMapper.class);
            routerMapper.delete( requestId );
            SessionFactory.commit(sqlSession);
        }catch (Exception e){
            SessionFactory.rollBack(sqlSession);
            e.printStackTrace();
        }
    }
}
