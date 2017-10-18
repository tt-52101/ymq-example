package io.ymq.solr;

import io.ymq.solr.po.Ymq;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-18 10:34
 **/
public interface YmqRepository extends SolrCrudRepository<Ymq, String> {


}
