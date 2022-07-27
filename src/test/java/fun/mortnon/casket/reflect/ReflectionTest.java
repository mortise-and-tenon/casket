package fun.mortnon.casket.reflect;

import fun.mortnon.casket.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Moon Wu
 * @date 2022/7/26
 */
class ReflectionTest {

    @Test
    void getFields() {
        Map<String, Type> fields = Reflection.getColumns(User.class);
        int size = fields.size();
        Assertions.assertEquals(4, size);
    }

    @Test
    void getName() {
        String tableName = Reflection.getTableName(Table.class);
        assertEquals("Table", tableName);
        String tableName1 = Reflection.getTableName(TableAnno.class);
        assertEquals("TableAnno", tableName1);
        String tableName2 = Reflection.getTableName(TAbleAnnoName.class);
        assertEquals("table", tableName2);
    }

    @Test
    void getMethod() {
        try {
            Field field = MethodClass.class.getDeclaredField("name");
            Method method = Reflection.getSetter(field, MethodClass.class);
            assertNotNull(method);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    static class Table {

    }

    @javax.persistence.Table
    static class TableAnno {

    }

    @javax.persistence.Table(name = "table")
    static class TAbleAnnoName {

    }

    static class MethodClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}