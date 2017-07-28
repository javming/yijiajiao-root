package com.yijiajiao.root.manage.mapper;

import com.yijiajiao.root.manage.model.RouterModel;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-07-10-10:44
 */
public interface RouterMapper {

    List<RouterModel> routers();

    RouterModel routerDetail(int requestId);

    List<Map<String, Object>> routersByConditions(RouterModel param);

    void addRouter(RouterModel param);

    void updateRouter(RouterModel param);

    void addRouters(List<RouterModel> routers);

    void delete( Integer requestId);
}
