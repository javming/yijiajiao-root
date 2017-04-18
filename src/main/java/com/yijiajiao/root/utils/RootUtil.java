package com.yijiajiao.root.utils;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2016-12-20-13:26
 */
public class RootUtil {
    
    private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

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

    public static String randomCode() {
        String sRand = "";
        for (int i = 0; i < 6; i++) {
            String rand = getRandomChar();
            sRand = sRand.concat(rand);
        }
        return sRand;
    }
    private static String getRandomChar() {
        String randChar = "";
        randChar = String.valueOf(Math.round(Math.random() * 9));
        return randChar;
    }

    /**
     *  输出json格式结果
     */
    public static void jsonResult(ServletResponse response, Object object){
        System.out.println("RootUtil---response.hashCode:"+response.hashCode());
        response.setContentType(JSON_CONTENT_TYPE);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write((String) object);
            System.out.println("RootUtil 输出到页面！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
