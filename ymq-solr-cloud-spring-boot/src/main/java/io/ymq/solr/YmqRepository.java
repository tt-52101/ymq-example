package io.ymq.solr;

import io.ymq.solr.po.Ymq;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-18 10:34
 **/
@Component
public interface YmqRepository extends SolrCrudRepository<Ymq, String> {

}
