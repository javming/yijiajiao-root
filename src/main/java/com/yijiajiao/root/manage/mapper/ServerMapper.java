package com.yijiajiao.root.manage.mapper;

import com.yijiajiao.root.manage.model.ServerModel;

import java.util.List;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-27-16:25
 */
public interface ServerMapper {

    void addServer(ServerModel server);

    ServerModel detail(int id);

    List<ServerModel> all();

    void update(ServerModel server);
}
