package com.yh.demo.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtil {
	public static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object unserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
