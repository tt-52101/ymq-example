package io.ymq.mybatis.test;


import com.alibaba.fastjson.JSONObject;
import io.ymq.mybatis.dao.base.YmqBaseDao;
import io.ymq.mybatis.po.SysConfigPo;
import io.ymq.mybatis.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 描述: 单元测试
 * author: yanpenglei
 * Date: 2017/10/19 19:49 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BaseTest {

    @Autowired
    private YmqBaseDao ymqBaseDao;

    @Test
    public void test() throws Exception {

        List<SysConfigPo> sysConfigPoList = ymqBaseDao.selectList(new SysConfigPo());

        for (SysConfigPo item : sysConfigPoList) {
            System.out.println("查询结果："+JSONObject.toJSONString(item));
        }

    }
}