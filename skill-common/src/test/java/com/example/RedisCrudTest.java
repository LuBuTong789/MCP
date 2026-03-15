package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisCrudTest {

    @Autowired
    private RedisCrudUtil redisCrudUtil;

    // 测试字符串CRUD
    @Test
    public void testStringCrud() {
        // 新增
        redisCrudUtil.setString("user:name", "张三");
        // 修改（覆盖）
        redisCrudUtil.setString("user:name", "李四");
        // 查询
        String name = redisCrudUtil.getString("user:name");
        System.out.println("查询结果：" + name); // 输出：李四
        // 判断存在
        Boolean exists = redisCrudUtil.existsKey("user:name");
        System.out.println("key是否存在：" + exists); // 输出：true


    }

    @Test
    public void testDelString() {
        // 删除
        Boolean deleted = redisCrudUtil.deleteString("user:name");
        System.out.println("是否删除成功：" + deleted); // 输出：true
    }

    // 测试Hash CRUD
    @Test
    public void testHashCrud() {
        // 批量新增
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", "1");
        userMap.put("name", "张三");
        userMap.put("age", "25");
        redisCrudUtil.setHashBatch("user:1", userMap);

        // 查询单个字段
        String age = redisCrudUtil.getHash("user:1", "age");
        System.out.println("用户年龄：" + age); // 输出：25

        // 查询所有字段
        Map<Object, Object> user = redisCrudUtil.getHashAll("user:1");
        System.out.println("用户所有信息：" + user); // 输出：{id=1, name=张三, age=25}

        // 修改字段
        redisCrudUtil.setHash("user:1", "age", "26");
        // 删除字段
        redisCrudUtil.deleteHash("user:1", "age");
        // 删除整个Hash
        redisCrudUtil.deleteString("user:1");
    }

    // 测试List CRUD
    @Test
    public void testListCrud() {
        // 新增元素
        redisCrudUtil.addListRight("user:list", "张三");
        redisCrudUtil.addListRight("user:list", "李四");
        // 查询所有元素
        System.out.println("列表所有元素：" + redisCrudUtil.getList("user:list", 0, -1)); // 输出：[张三, 李四]
        // 修改索引1的元素
        redisCrudUtil.updateList("user:list", 1, "王五");
        // 删除值为"王五"的元素
        redisCrudUtil.deleteList("user:list", 0, "王五");
        // 删除整个列表
        redisCrudUtil.deleteString("user:list");
    }
}