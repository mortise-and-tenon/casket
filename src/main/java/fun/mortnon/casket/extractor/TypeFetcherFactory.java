package fun.mortnon.casket.extractor;

import fun.mortnon.casket.extractor.fetcher.*;

/**
 * @author Moon Wu
 * @date 2022/8/5
 */
public class TypeFetcherFactory {

    public TypeFetcherFactory() {

    }


    public TypeFetcher build(Class<?> dataClazz) {
        TypeMapper type = TypeMapper.valueOfClass(dataClazz);
        switch (type) {
            case PRIMITIVE_BOOLEAN:
            case BOOLEAN:
                return new BooleanFetcher();
            case PRIMITIVE_BYTE:
            case BYTE:
                return new ByteFetcher();
            case PRIMITIVE_DOUBLE:
            case DOUBLE:
                return new DoubleFetcher();
            case PRIMITIVE_FLOAT:
            case FLOAT:
                return new FloatFetcher();
            case PRIMITIVE_INT:
            case INTEGER:
                return new IntegerFetcher();
            case PRIMITIVE_LONG:
            case LONG:
                return new LongFetcher();
            case PRIMITIVE_SHORT:
            case SHORT:
                return new ShortFetcher();
            case DATE:
                return new DateFetcher();
            case ENUM:
                return new EnumFetcher();
            case STRING:
            default:
                return new StringFetcher();
        }
    }
}
