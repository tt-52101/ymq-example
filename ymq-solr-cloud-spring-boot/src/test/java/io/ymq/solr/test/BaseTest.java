package io.ymq.solr.test;

import com.alibaba.fastjson.JSONObject;
import io.ymq.solr.YmqRepository;
import io.ymq.solr.po.Ymq;
import io.ymq.solr.run.Startup;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * 描述: 测试 solr cloud
 *
 * @author yanpenglei
 * @create 2017-10-17 19:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BaseTest {

    @Autowired
    private YmqRepository ymqRepository;

    @Autowired
    private CloudSolrClient cloudSolrClient;


    @Test
    public void testYmqRepository() throws Exception {

        Ymq ymq = new Ymq();
        ymq.setYmqId("4");
        ymq.setYmqTitle("test");
        ymq.setYmqUrl("www.ymq.io");
        ymq.setYmqContent("test content");
        ymq.setYmqText("text content");

        ymqRepository.save(ymq);
    }



    @Test
    public void testCloudSolrClient() throws Exception {

        Ymq ymq = new Ymq();
        ymq.setYmqId("5");
        ymq.setYmqTitle("test");
        ymq.setYmqUrl("www_ymq_io");
        ymq.setYmqContent("test content");
        ymq.setYmqText("text content");

        cloudSolrClient.setDefaultCollection("test_collection");
        cloudSolrClient.connect();

        cloudSolrClient.addBean(ymq);
        cloudSolrClient.commit();
    }

    @Test
    public void testYmqRepositorySearch() throws Exception {

        List<Ymq> list = ymqRepository.findByQueryAnnotation("test");

        for (Ymq ymq : list) {
            System.out.println(JSONObject.toJSONString(ymq));
        }
    }

    @Test
    public void testYmqSolrQuery() throws Exception {

        SolrQuery query = new SolrQuery();

        String ymqTitle="test";

        query.setQuery("ymqTitle:*"+ymqTitle);

        cloudSolrClient.setDefaultCollection("test_collection");
        cloudSolrClient.connect();
        QueryResponse response = cloudSolrClient.query(query);
        List<Ymq> list = response.getBeans(Ymq.class);

        for (Ymq ymq : list) {
            System.out.println(JSONObject.toJSONString(ymq));
        }
    }

}
