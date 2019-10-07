package com.sponge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 生成验证码图片
 * @Description TODO
 * @Author kangtutu
 * @Site www.sponge-k.tech
 * @Company 海绵之家
 * @Create 2019-10-07 11:48
 **/
public class CaptchaPicture {

    /**
     * 定义map中存储使用的key值
     * 验证码key值：code
     * 验证码图片key值：image
     */
    private static final String IMAGE_MAP_CODE_KEY = "code";
    private static final String IMAGE_MAP_IMG_KEY="image";

    /**
     * 设置初始默认值若不传参数则直接使用默认值进行验证码图片初始化
     */
    private Integer img_width = 80;
    private Integer img_height = 35;

    /**
     * 生成验证码的取值范围
     * 3 -- 不限制（大/小写字母，数字），默认值
     * 0 -- 值在大写字母范围内生成验证码
     * 1 -- 只在小写字母范围内生成验证码
     * 2 -- 只在数字范围内生成验证码
     */
    private Integer verification_code_range = 3;

    //设置背景色红绿蓝色值范围
    private Integer color_red = 255;
    private Integer color_green = 255;
    private Integer color_blue = 255;

    //数字随机码
    private String[] img_code_number={"1","2","3","4","5","6","7","8","9","0"};
    private String[] img_code_capital={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private String[] img_code_lowercase={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    //生成全局随机对象
    private static final Random IMG_RANDOM = new Random();

    //设置验证码字体
    private static final String[] FONT_NAME = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};

    //验证码长度
    private Integer verification_code_length = 4;

    public CaptchaPicture() {}

    public CaptchaPicture(Integer img_width, Integer img_height, Integer verification_code_range, Integer color_red, Integer color_green, Integer color_blue, Integer verification_code_length) {
        this.img_width = img_width;
        this.img_height = img_height;
        this.color_red = color_red;
        this.color_blue = color_blue;
        this.color_green = color_green;
        this.verification_code_range = verification_code_range;
        this.verification_code_length = verification_code_length;
    }

    /**
     * 获取生成的随机验证码图片
     * @return
     */
    public Map<String,Object> getImageAndCode(){
        Map<String,Object> map = new HashMap<>();

        //获取背景图片
        BufferedImage image = creatImage(getColor());

        //获取生成的验证码
        String imgCode = getVerificationCode();

        //生成画笔对象 绘制的范围就是当前背景图片区域
        Graphics2D g2 = (Graphics2D)image.createGraphics();

        for(int i=0;i<imgCode.length();i++){
            //设置验证码的横向坐标
            float x = i*1.0F*img_width/4;
            //设置随机字体
            g2.setFont(getRandomFont());
            //设置随机字体颜色
            g2.setColor(getColor());
            //将生成的验证码画到图上 参数为：生成的随机字符，字符横向坐标，字符纵向坐标
            g2.drawString(String.valueOf(imgCode.charAt(i)),x,img_height-5);
        }

        //将生成的随机验证码存入map中
        map.put(IMAGE_MAP_CODE_KEY,imgCode);

        //生成干扰线
        drawLine(image);

        //创建输出对象
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            //将图片保存到输出流中
            ImageIO.write(image, "jpg", out);
            //获取验证码图片的字节输出数组
            byte[] bytes = out.toByteArray();
            //将生成的图片字节数组存入map中
            map.put(IMAGE_MAP_IMG_KEY,bytes);
            //将数据返回
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                //关闭输出流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图片颜色
     * @return
     */
    private Color getColor(){
        int red = IMG_RANDOM.nextInt(color_red);
        int blue = IMG_RANDOM.nextInt(color_blue);
        int green = IMG_RANDOM.nextInt(color_green);
        //参数顺序为：红-绿-蓝
        return new Color(red,green,blue);
    }

    /**
     * 获取验证码
     * @return
     */
    private String getVerificationCode(){

        //用于存储生成的随机验证码
        StringBuilder stringBuilder = new StringBuilder();

        //判断验证码随机范围值，通过随机范围值进行验证码的随机获取
        if(verification_code_range == 0){
            for(int i=0;i<verification_code_length;i++){
                stringBuilder.append(img_code_capital[IMG_RANDOM.nextInt(img_code_capital.length-1)]);
            }
        }

        if(verification_code_range == 1){
            for(int i=0;i<verification_code_length;i++){
                stringBuilder.append(img_code_lowercase[IMG_RANDOM.nextInt(img_code_lowercase.length-1)]);
            }
        }

        if(verification_code_range == 2){
            for(int i=0;i<verification_code_length;i++){
                stringBuilder.append(img_code_number[IMG_RANDOM.nextInt(img_code_number.length-1)]);
            }
        }

        if(verification_code_range == 3){
            for(int i=0;i<verification_code_length;i++){
                /**
                 * 获取从哪个取值数组中获取验证码
                 * 0 - 大写，1 - 小写，2 - 数字
                 */
                int num = IMG_RANDOM.nextInt(3);
                if(num == 0){
                    stringBuilder.append(img_code_capital[IMG_RANDOM.nextInt(img_code_capital.length-1)]);
                }
                if(num == 1){
                    stringBuilder.append(img_code_lowercase[IMG_RANDOM.nextInt(img_code_lowercase.length-1)]);
                }
                if(num == 2){
                    stringBuilder.append(img_code_number[IMG_RANDOM.nextInt(img_code_number.length-1)]);
                }
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 生成随机验证码字体
     * @return
     */
    private Font getRandomFont() {
        //获取随机的验证码字体
        int index = IMG_RANDOM.nextInt(FONT_NAME.length);
        String fontName = FONT_NAME[index];
        //生成随机的验证码字体样式 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int style = IMG_RANDOM.nextInt(4);
        //生成随机的验证码字体大小 取值范围 16~24
        int size = IMG_RANDOM.nextInt(9)+16;
        return new Font(fontName,style,size);
    }

    /**
     * 生成验证码图片中的干扰线
     * @param image
     */
    private void drawLine (BufferedImage image) {
        //定义验证码干扰线条数
        int num = 3;

        //生成画笔对象
        Graphics2D g2 = (Graphics2D)image.createGraphics();

        //生成每条干扰线两点的坐标
        for(int i=0;i<num;i++){
            int x1 = IMG_RANDOM.nextInt(img_width);
            int y1 = IMG_RANDOM.nextInt(img_height);
            int x2 = IMG_RANDOM.nextInt(img_width);
            int y2 = IMG_RANDOM.nextInt(img_height);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建BufferedImage生成背景图片
     * @param bgColor
     * @return
     */
    private BufferedImage creatImage(Color bgColor) {
        BufferedImage image = new BufferedImage(img_width,img_height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics(); //生成画笔
        g2.setColor(bgColor); //验证码背景色
        g2.fillRect(0, 0, img_width, img_height); //验证码图片宽高
        return image;
    }

}

