package com.xpm.test.jdk;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xupingmao on 2017/8/18.
 */
public class RTTITest {

    class Game {
        private List<String> users;

        public List<String> getUsers() {
            return users;
        }

        public void setUsers(List<String> users) {
            this.users = users;
        }
    }

    @Test
    public void getRTTIInfo() throws NoSuchFieldException {
        Field users = Game.class.getDeclaredField("users");
        Type genericType = users.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.printf("parameters: <");
            for (Type parameter: actualTypeArguments) {
                System.out.printf("%s, ", parameter.getTypeName());
            }
            System.out.printf(">\n");
        }

        System.out.println(genericType.getClass());
        System.out.println(JSON.toJSONString(genericType, true));
    }
}
