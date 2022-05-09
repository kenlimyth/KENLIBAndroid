package com.kenlib.util;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化
 * 为什么要进实现Serializable接口：为了保存在内存中的各种对象的状态（也就是实例变量，不是方法），并且可以把保存的对象状态再读出来，这是java中的提供的保存对象状态的机制—序列化
 * 在什么情况下需要使用到Serializable接口
 * 　　1、当想把的内存中的对象状态保存到一个文件中或者数据库中时候；
 * 　　2、当想用套接字在网络上传送对象的时候；
 * 　　3、当想通过RMI传输对象的时候；
 */
public class SerializableMap implements Serializable {

	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}