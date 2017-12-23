package hessian;

import com.alibaba.com.caucho.hessian.io.*;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2SerializerFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2Serialization;


/**
 * Created by xupingmao on 2017/9/4.
 */

/**
 * @author ding.lid
 *
 */
public class HessianTest implements Serializable{
    // Hessian2

    SerializerFactory serialization = new SerializerFactory();

    static class A implements Serializable{
        private Set<String> names;

        public Set<String> getNames() {
            return names;
        }

        public void setNames(Set<String> names) {
            this.names = names;
        }
    }

    @Test
    public void test() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);
        AbstractHessianOutput output = new Hessian2Output(byteArrayOutputStream);
        output.setSerializerFactory(serialization);

        LinkedHashSet<String> sets = Sets.newLinkedHashSet();
        sets.add("b");
        sets.add("a");
        sets.add("c");
        A a = new A();
        a.setNames(sets);
        output.writeObject(a);
        output.close();
        byteArrayOutputStream.close();

        System.out.println(JSON.toJSONString(a, true));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        AbstractHessianInput input = new Hessian2Input(byteArrayInputStream);
        input.setSerializerFactory(serialization);
        Object o = input.readObject(A.class);
        System.out.println(o.getClass());
        // hessian根据定义的类型来反序列化，所以这里丢失了set顺序信息，声明类型为linkedHashSet
        System.out.println(JSON.toJSONString(o, true));
    }

}