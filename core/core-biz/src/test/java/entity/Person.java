package entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author chenkun
 * @description:
 * @create: 2024-01-04 09:24:08
 **/
@TypeAlias("human")
public class Person {
    @Id
    String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String firstname;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String lastname;
}