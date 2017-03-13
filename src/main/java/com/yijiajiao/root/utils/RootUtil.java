package com.yijiajiao.root.utils;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2016-12-20-13:26
 */
public class RootUtil {

    public static final String WARES_SERVER = Config.getString("wares_server");
    public static final String USER_SERVER = Config.getString("user_server");
    public static final String TEACH_SERVER = Config.getString("teach_server");
    public static final String SALE_SERVER = Config.getString("sale_server");
    public static final String SOLUTION_SERVER = Config.getString("solution_server");
    public static final String FINANCE_SERVER = Config.getString("finance_server");
    public static final String MSG_SERVER = Config.getString("msg_server");
    public static final String OSS_SERVER = Config.getString("oss_server");
    public static final String PROMOTION_SERVER = Config.getString("promotion_server"); 
    
    private static final String JSON_CONTENTTYPE = "application/json; charset=utf-8";

    private static final char[] chr1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] chr2 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] chr3 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] chr4 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     *  输出json格式结果
     */
    public static void jsonResult(ServletResponse response, Object object){
        response.setContentType(JSON_CONTENTTYPE);
        System.out.println((String)object);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write((String) object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
