package com.yijiajiao.root.manage.service;

import com.yijiajiao.root.manage.mapper.ServerMapper;
import com.yijiajiao.root.manage.model.ServerModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.yijiajiao.root.manage.service.SessionFactory.*;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-17:22
 */
public class ServerService {

    private static final Logger log = LoggerFactory.getLogger(ServerService.class);
    private static SqlSessionFactory factory = getSqlSessionFactory();

    public static ServerModel detail(int id){
        SqlSession session = null;
        try {
            session = factory.openSession();
            ServerMapper mapper = session.getMapper(ServerMapper.class);
            return mapper.detail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        close(session);
        return null;
    }

    public static List<ServerModel> all(){
        SqlSession session = null;
        try {
            session = factory.openSession();
            ServerMapper mapper = session.getMapper(ServerMapper.class);
            return mapper.all();
        }catch (Exception e){
            e.printStackTrace();
        }
        close(session);
        return null;
    }

    public static void add(ServerModel server){
        SqlSession session = null;
        try {
            session = factory.openSession();
            ServerMapper mapper = session.getMapper(ServerMapper.class);
            mapper.addServer(server);
            commit(session);
        }catch (Exception e){
            e.printStackTrace();
            rollBack(session);
        }
    }

    public static void update(ServerModel server ){
        SqlSession session = null;
        try {
            session = factory.openSession();
            ServerMapper mapper = session.getMapper(ServerMapper.class);
            mapper.update(server);
            commit(session);
        }catch (Exception e){
            e.printStackTrace();
            rollBack(session);
        }
    }
}
