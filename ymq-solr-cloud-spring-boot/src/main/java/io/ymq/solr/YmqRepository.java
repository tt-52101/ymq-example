package io.ymq.solr;

import io.ymq.solr.po.Ymq;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-18 10:34
 **/
public interface YmqRepository extends SolrCrudRepository<Ymq, String> {

    List<Ymq> findByName(String name);
}
