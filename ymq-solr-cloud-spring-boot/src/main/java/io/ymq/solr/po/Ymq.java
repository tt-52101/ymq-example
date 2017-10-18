package io.ymq.solr.po;


import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * 描述: 映射的实体类必须有@ID主键
 *
 * @author yanpenglei
 * @create 2017-10-17 18:28
 **/
@SolrDocument(solrCoreName = "test_collection")
public class Ymq {

    @Id
    @Field
    private String ymqId;

    @Field
    private String ymqTitle;

    @Field
    private String ymqUrl;

    @Field
    private String ymqContent;

    @Field
    private String ymqText;

    public String getYmqId() {
        return ymqId;
    }

    public void setYmqId(String ymqId) {
        this.ymqId = ymqId;
    }

    public String getYmqTitle() {
        return ymqTitle;
    }

    public void setYmqTitle(String ymqTitle) {
        this.ymqTitle = ymqTitle;
    }

    public String getYmqUrl() {
        return ymqUrl;
    }

    public void setYmqUrl(String ymqUrl) {
        this.ymqUrl = ymqUrl;
    }

    public String getYmqContent() {
        return ymqContent;
    }

    public void setYmqContent(String ymqContent) {
        this.ymqContent = ymqContent;
    }

    public String getYmqText() {
        return ymqText;
    }

    public void setYmqText(String ymqText) {
        this.ymqText = ymqText;
    }

}
