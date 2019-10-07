package com.sponge.error;

/**
 * 异常处理类
 * @Description TODO
 * @Author kangmiaoyuan
 * @Site www.sponge-k.tech
 * @Company 海绵之家
 * @Create 2019-10-07 12:14
 **/
public class CaptchaPictureException extends RuntimeException {

    private String message;

    public CaptchaPictureException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

