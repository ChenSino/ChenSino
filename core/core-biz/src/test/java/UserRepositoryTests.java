import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.chensino.core.App;
import com.chensino.core.api.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenkun
 * @description:
 * @create: 2024-01-03 11:05:57
 **/
@SpringBootTest(classes = App.class)
@Slf4j
class UserRepositoryTests {

    @Autowired
    @Qualifier("elasticsearchClient")
    private ElasticsearchClient esClient;


    // 查询Index
    @Test
    void queryTest() throws IOException {
        GetIndexResponse getIndexResponse = esClient.indices().get(i -> i.index("user"));
        System.out.println(getIndexResponse);
    }

    //判断Index是否存在
    @Test
    void existsTest() throws IOException {
        BooleanResponse booleanResponse = esClient.indices().exists(e -> e.index("user"));
        System.out.println(booleanResponse.value());
        assert booleanResponse.value();
    }

    //删除索引
    @Test
    void deleteTest() throws IOException {
        DeleteIndexResponse deleteIndexResponse = esClient.indices().delete(d -> d.index("user"));
        System.out.println(deleteIndexResponse.acknowledged());
    }
    //添加document

    @Test
    void addDocumentTest() throws IOException {

        User user = new User("user1", 10);
        IndexResponse indexResponse = esClient.index(i -> i
                .index("user")
                //设置id
                .id("1")
                //传入user对象
                .document(user));
    }

    //查询document
    @Test
    void getDocumentTest() throws IOException {
        GetResponse<User> getResponse = esClient.get(g -> g
                        .index("user")
                        .id("1")
                , User.class
        );
        System.out.println(getResponse.source());
    }

    //删除document
    @Test
    void deleteDocumentTest() throws IOException {
        DeleteResponse deleteResponse = esClient.delete(d -> d
                .index("user")
                .id("1")
        );
        System.out.println(deleteResponse.id());
    }

    // 批量删除
    @Test
    void deleteBulkTest() throws IOException {
        // 构建批量操作对象BulkOperation的集合
        List<BulkOperation> bulkOperations = new ArrayList<>();

        // 向集合中添加需要删除的文档id信息
        bulkOperations.add(new BulkOperation.Builder().delete(d -> d.index("user").id("1")).build());
        bulkOperations.add(new BulkOperation.Builder().delete(d -> d.index("user").id("2")).build());
        bulkOperations.add(new BulkOperation.Builder().delete(d -> d.index("user").id("3")).build());

        // 调用客户端的bulk方法，并获取批量操作响应结果
        BulkResponse response = esClient.bulk(e -> e.index("user").operations(bulkOperations));
        System.out.println(response.took());
        System.out.println(response.items());
    }

    //批量插入文档
    @Test
    void bulkTest() throws IOException {
        List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
        for (int i = 100; i < 300; i++) {
            int finalI = i;
            bulkOperationArrayList.add(BulkOperation.of(o -> o.index(j -> j.document(new User("user" + finalI, 11)))));
        }
        BulkResponse bulkResponse = esClient.bulk(b -> b.index("user")
                .operations(bulkOperationArrayList));

    }

    //search
    @Test
    void searchTest() throws IOException {
        SearchResponse<User> search = esClient.search(s -> s
                .index("user")
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q
                        .term(t -> t
                                .field("name")
                                .value(v -> v.stringValue("hello"))
                        ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(3)
                //按age降序排序
                .sort(f -> f.field(o -> o.field("age").order(SortOrder.Desc))), User.class
        );
        for (Hit<User> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }
    }
}