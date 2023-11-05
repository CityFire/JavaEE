package com.wjc.scw;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ScwUserApplicationTests {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;

	@Test
	void test01() throws SQLException {
		
		Connection conn = dataSource.getConnection();
		
		System.out.println(conn); // 代理对象  com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@277bc3a5
		
		conn.close(); // 不是销毁对象，而是归还到连接池。
	}
	
	@Test
	public void test02() {
		stringRedisTemplate.opsForValue().set("key111", "value111");
		
		//redisTemplate.opsForValue().set("haha", "2");
		
	}
	
	@Test
	public void test03() {
		String value = stringRedisTemplate.opsForValue().get("key111");
		//Object object = redisTemplate.opsForValue().get("haha");
		System.out.println(value);
		//System.out.println(object);
	}

}
