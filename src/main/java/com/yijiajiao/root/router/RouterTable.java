package com.yijiajiao.root.router;

import com.yijiajiao.root.manage.RouterService;
import com.yijiajiao.root.manage.model.RouterModel;
import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-18-13:22
 */
public class RouterTable implements Serializable{

    private static final long serialVersionUID  = -1563920797266872070L;

    private List<RouterInfo> routerInfos = new ArrayList<RouterInfo>();

    private static RouterTable instance = null;

    private String oauthAddress = "http://192.168.2.252:8501";

    private final static String ROUTER_TABLE_FILE = "RouterTable.xml";

    private static final Logger log  = LoggerFactory.getLogger(RouterTable.class);

    public static RouterTable getInstance() throws IOException {
        if (instance == null) {
            try {
                File loadFile = new File(ROUTER_TABLE_FILE);
                if (!loadFile.exists()) {
                    log.info("路由表文件不存在！");
                    instance = new RouterTable();
                    instance.save();
                } else {
                    log.info("实例化路由表配置...");
                    XMLDecoder decoder = new XMLDecoder(new FileInputStream(loadFile));
                    instance = (RouterTable) decoder.readObject();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    //初始化路由表
    public static void routerInit(){
        if ( instance == null ) {
            instance = new RouterTable();
        }

        if (!instance.routerInfos.isEmpty()){
            instance.routerInfos.clear();
        }

        List<RouterModel> routers = RouterService.routers();
        if (routers == null || routers.size() == 0){
            log.error("路由表为空，请检查！！！！！！");
            throw new RuntimeException("路由表为空，请检查！！！！！！");
        }
        int n = 1;
        for ( RouterModel router : routers){
            RouterInfo routerInfo = new RouterInfo(router.getRequestUrl(),
                                                   router.getRequestMethod(),
                                                   router.getRouterStatus(),
                                                   router.getMappingUrl(),
                                                   router.getRequestStatus(),
                                                   router.getReplaceRegex());
            instance.routerInfos.add(routerInfo);
            log.info("routerInfo"+ n + ":" + routerInfo);
            n++;
        }
        log.info("路由总数量：" + instance.routerInfos.size());
    }

    private void save() throws IOException {
        File file = new File(ROUTER_TABLE_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));
        encoder.writeObject(this);
        encoder.close();
    }

    public static void reload() {
        try {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(ROUTER_TABLE_FILE));
            instance = null;
            instance = (RouterTable) decoder.readObject();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    public static RouterInfo getByRequestURL(String requestURL, String mothed) {
        if (StringUtil.isEmpty(requestURL)) return null;
        for (RouterInfo router : instance.routerInfos) {
            Pattern p = Pattern.compile(router.getRequestURL());
            Matcher m = p.matcher(requestURL);
            log.debug("regex validate  : " + m.matches());
            if (m.matches() && mothed.equals(router.getRequestMothed())) {
                log.debug("requestURL is : " + requestURL);
                if (router.getReplaceRegex() != null && (!"".equals(router.getReplaceRegex()))) {
                    Pattern pa = Pattern.compile(router.getReplaceRegex());
                    Matcher ma = pa.matcher(router.getMappingURL());
                    String temp = ma.replaceAll(requestURL);
                    log.debug("MappingURL is :  " + temp);
                    router.setMappingURL(temp);
                } else {
                    return router;
                }
                return router;
            }
        }
        return null;
    }

    public List<RouterInfo> getRouterInfos() {
        return this.routerInfos;
    }

    public void setRouterInfos(List<RouterInfo> routerInfos) {
        this.routerInfos = routerInfos;
    }

    public String getOauthAddress() {
        return oauthAddress;
    }

    public void setOauthAddress(String oauthAddress) {
        this.oauthAddress = oauthAddress;
    }

    public static boolean output() throws IOException {
        //创建输出文件
        File fo = new File(ROUTER_TABLE_FILE);
        //创建文件输出流
        FileOutputStream fos = new FileOutputStream(fo);
        //创建XML文件对象输出类实例
        XMLEncoder encoder = new XMLEncoder(fos);
        //对象序列化输出到XML文件
        encoder.writeObject(instance);
        encoder.flush();
        //关闭序列化工具
        encoder.close();
        //关闭输出流
        fos.close();
        return true;
    }

    public static boolean copy(){
        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {
            File origin = new File(ROUTER_TABLE_FILE);
            String parent = origin.getParent();
            File copy = new File(parent+"/"+System.currentTimeMillis()+"_table.xml");
            if (!copy.exists()) copy.createNewFile();
            fi = new FileInputStream(origin);

            fo = new FileOutputStream(copy);

            in = fi.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;

        } catch (IOException e) {

            e.printStackTrace();
            return false;

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
    }

    public static void main(String[] args) throws IOException {

        RouterTable instance = getInstance();
        List<RouterModel> routers = new ArrayList<>();
        for (RouterInfo ri : instance.routerInfos){
            RouterModel routerModel = new RouterModel(null,ri.getRequestURL(),ri.getRequestMothed(),
                    ri.getRequestStatus(),ri.getMappingURL(),ri.getRouterStatus(),ri.getReplaceRegex(),"接口");
            routers.add(routerModel);
        }
        RouterService.addRouters(routers);
    }
}
