import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * Created by xupingmao on 2017/9/21.
 */
public class FastJSONTest {

    public static class Period {
        DateTime start;
        DateTime end;

        public DateTime getStart() {
            return start;
        }

        public void setStart(DateTime start) {
            this.start = start;
        }

        public DateTime getEnd() {
            return end;
        }

        public void setEnd(DateTime end) {
            this.end = end;
        }
    }

    class PrivateClass {

    }

    @Test
    public void printDateTimeAsString() {
        Period period = new Period();
        period.setStart(new DateTime(2017, 8,1,0,0));
        period.setEnd(DateTime.now());

        System.out.println(JSON.toJSONString(period, createConfig(), SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(period, createConfig()));
    }

    @Test
    public void parseStringAsDateTime() {
        String jsonString = "{\"end\":\"2017-09-21T10:19:20.298+08:00\",\"start\":\"2017-08-01T00:00:00.000+08:00\"}";
        Period period = JSON.parseObject(jsonString, Period.class, createParserConfig());
        System.out.println(period);
    }

    @Test
    public void getPrivateClassConstructor() {
        // 私有的内部类默认构造函数是有一个外部类实例参数的
        Constructor<?>[] constructors = PrivateClass.class.getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            System.out.println(constructor);
            // output FastJSONTest$PrivateClass(FastJSONTest)
        }
    }

    /**
     * 定义parser配置
     * @return
     */
    private ParserConfig createParserConfig() {
        ParserConfig config = new ParserConfig();
        config.putDeserializer(DateTime.class, new AbstractDateDeserializer() {

            @Override
            public int getFastMatchToken() {
                return 0;
            }

            @Override
            protected DateTime cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object value) {
                return DateTime.parse(value.toString());
            }
        });
        return config;
    }


    private SerializeConfig createConfig() {
        SerializeConfig config = new SerializeConfig();
        config.put(DateTime.class, new ObjectSerializer() {
            @Override
            public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
                DateTime obj = (DateTime)object;
                serializer.write(obj.toString());
            }
        });
        return config;
    }

}
