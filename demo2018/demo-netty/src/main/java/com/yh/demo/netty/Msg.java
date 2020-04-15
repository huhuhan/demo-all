package com.yh.demo.netty;

import java.io.Serializable;

/**
 * 自定义实体类，实现序列化
 */
public class Msg implements Serializable {
    private byte header;
    private String body;
    private int length;
    private byte type;

    public Msg() {
    }

    public Msg(byte type, String body, int length) {
        this.body = body;
        this.length = length;
        this.type = type;
    }

    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "header=" + header +
                ", body='" + body + '\'' +
                ", length=" + length +
                ", type=" + type +
                '}';
    }
}
