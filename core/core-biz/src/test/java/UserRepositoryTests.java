import cn.hutool.core.collection.CollUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.chensino.core.App;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysUserService sysUserService;

    //创建Index
    @Test
    void createIndex() throws IOException {
        InputStream input = this.getClass()
                .getResourceAsStream("user.json");
        CreateIndexRequest req = CreateIndexRequest.of(b -> b
                .index("user")
                .withJson(input)
        );
        boolean created = esClient.indices().create(req).acknowledged();
        assert created;
    }

    //初始化数据到elasticsearch
    @Test
    void initData() throws IOException {
        List<SysUser> list = sysUserService.list();
        assert CollUtil.isNotEmpty(list);
        List<BulkOperation> bulkOperationList = list.stream().map(sysUser -> BulkOperation.of(o -> o.index(j -> j.document(sysUser)))).toList();
        BulkResponse bulkResponse = esClient.bulk(b -> b.index("user").operations(bulkOperationList));
        log.info(bulkResponse.toString());
    }

    // 查询Index
    @Test
    void queryTest() throws IOException {
        GetIndexResponse indexResponse = esClient.indices().get(i -> i.index("user"));
        log.info(indexResponse.toString());
        assert indexResponse.result().size() == 1;
    }

    //判断Index是否存在
    @Test
    void existsTest() throws IOException {
        BooleanResponse booleanResponse = esClient.indices().exists(e -> e.index("user"));
        System.out.println(booleanResponse.value());
        assert booleanResponse.value();
    }

    //删除index
    @Test
    void deleteTest() throws IOException {
        DeleteIndexResponse deleteIndexResponse = esClient.indices().delete(d -> d.index("user"));
        System.out.println(deleteIndexResponse.acknowledged());
    }

    //添加document
    @Test
    void addDocumentTest() throws IOException {
        SysUser user = new SysUser();
        user.setUserId(1324L);
        user.setUsername("吴彦祖");
        user.setEmail("462488548@qq.com");
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

        IndexResponse indexResponse = esClient.index(i -> i
                .index("user")
                //设置id
                .id("1")
                //传入user对象
                .document(user));
        log.info(indexResponse.toString());
    }

    //查询document
    @Test
    void getDocumentTest() throws IOException {
        GetResponse<SysUser> getResponse = esClient.get(g -> g
                        .index("user")
                        .id("1")
                , SysUser.class
        );
        System.out.println(getResponse.source());
        assert getResponse.source() != null;
    }

    //删除document
    @Test
    void deleteDocumentTest() throws IOException {
        DeleteResponse deleteResponse = esClient.delete(d -> d
                .index("user")
                .id("1")
        );
        System.out.println(deleteResponse.id());
        assert deleteResponse.id().equals("1");
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
            SysUser user = new SysUser();
            user.setUserId(Long.valueOf(i));
            user.setUsername("吴彦祖");
            user.setEmail("462488548@qq.com");
            user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

            bulkOperationArrayList.add(BulkOperation.of(o -> o.index(j -> j.document(user))));
        }
        BulkResponse bulkResponse = esClient.bulk(b -> b.index("user")
                .operations(bulkOperationArrayList));

    }

    //search-----精确查找
    @Test
    void searchTest1() throws IOException {
        SearchResponse<SysUser> search = esClient.search(s -> s
                .index("user")
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q
                        .term(t -> t
                                .field("username")
                                .value("chensino")
                        ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(100), SysUser.class);
        for (Hit<SysUser> hit : search.hits().hits()) {
            log.info(hit.source().toString());
        }
    }

    //search-----单字段查找
    @Test
    void searchTest2() throws IOException {
        SearchResponse<SysUser> search = esClient.search(s -> s
                .index("user")
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q.match(
                        m -> m.field("username").query("chensino1")
                ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(100), SysUser.class);
        for (Hit<SysUser> hit : search.hits().hits()) {
            log.info(hit.source().toString());
        }
    }

    //multi-match
    @Test
    void searchTest3() throws IOException {
        SearchResponse<SysUser> search = esClient.search(s -> s
                .index("user")
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q.multiMatch(
                        m -> m.fields("nickName","username", "email")
                               .query("管理员的大哥chensino")
                ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(100), SysUser.class);
        for (Hit<SysUser> hit : search.hits().hits()) {
            log.info(hit.source().toString());
        }
    }
}