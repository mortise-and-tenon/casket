package fun.mortnon.casket.common;

/**
 * @author Moon Wu
 * @date 2022/7/25
 */
public interface Constants {
    /**
     * 类加载协议-文件
     */
    String FILE_PROTOCOL = "file";

    /**
     * 类加载协议-jar包
     */
    String JAR_PROTOCOL = "jar";

    /**
     * class 文件后续
     */
    String CLASS_SUFFIX = ".class";

    /**
     * 分隔符 .
     */
    char SPLIT_DOT = '.';

    /**
     * 编码-UTF8
     */
    String UTF8 = "UTF-8";

    /**
     * MD5
     */
    String MD5 = "MD5";

    /**
     * 系统换行符属性 key
     */
    String LINE_SEPARATOR_KEY = "line.separator";

    /**
     * 字符串的 .
     */
    String SPLIT_DOT_STR = String.valueOf(SPLIT_DOT);

    /**
     * URL 分隔符 /
     */
    char URL_SEPARATOR = '/';

    /**
     * 逗号
     */
    char COMMA = ',';

    /**
     * 用于类的类型的字段名称
     */
    String FIELD_TYPE = "TYPE";
}
