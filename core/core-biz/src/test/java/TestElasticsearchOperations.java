import com.chensino.core.App;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

/**
 * @author chenkun
 * @description:
 * @create: 2024-01-04 09:22:07
 **/
@SpringBootTest(classes = App.class)
@Slf4j
class TestElasticsearchOperations {
    @Autowired
    private  ElasticsearchOperations elasticsearchOperations;



    @Test
    void test(){
        IndexQuery indexQuery = new IndexQuery();
        String user = elasticsearchOperations.index(null, IndexCoordinates.of("user"));
//        Person sysUser = elasticsearchOperations.get("1", Person.class);
//        System.out.println(sysUser);
        System.out.println(user);
    }
}
