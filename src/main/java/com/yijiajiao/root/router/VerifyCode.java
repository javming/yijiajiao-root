package com.yijiajiao.root.router;

import com.yijiajiao.root.utils.RedisUtil;
import com.yijiajiao.root.utils.StringUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author zhaoming
 * @since 2017-11-10-20:41
 */

public class VerifyCode extends HttpServlet {
    private static final int width = 70;
    private static final int height = 40;
    private static final String verify = "verify-code:";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tel = req.getParameter("tel");
        System.out.println("tel===============" + tel);
        if (StringUtil.isEmpty(tel) || tel.length()!=11){
            return;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        Font font = new Font("Atlantic Inline", Font.BOLD, 30);
        try {
            g.setColor(getRandomColor(200, 250));
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.fillRect(0, 0, width, height);
        g.setFont(font);
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            String str = "";
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                str = String.valueOf((char) (97 + random.nextInt(26)));
                code += str;
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                str = String.valueOf(random.nextInt(10));
                code += str;
            }
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(str, 13 * i + 6, 30);
        }
        g.dispose();
        RedisUtil.putRedis(verify+tel,code,60);
        System.out.println("图片验证码存放redis "+RedisUtil.ttl(verify+tel)+"s");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", output);
        resp.setContentType("image/jpeg");
        // 发响应头控制浏览器不要缓存图片
        resp.setDateHeader("expries", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        ImageIO.write(image, "jpg", resp.getOutputStream());

    }

    private static Color getRandomColor(int fc, int bc) throws Exception {
        Random random = new Random();
        if (fc > 255)
            fc = 200;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
