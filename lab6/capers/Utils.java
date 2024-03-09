package capers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

/** 各种实用工具类.
 *  @author P. N. Hilfinger
 */
class Utils {

    /* 读取和写入文件内容 */

    /** 将文件 FILE 的全部内容作为字节数组返回。FILE 必须是普通文件。
     *  在出现问题时抛出 IllegalArgumentException 异常。 */
    static byte[] readContents(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("必须是普通文件");
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    /** 将文件 FILE 的全部内容作为字符串返回。FILE 必须是普通文件。
     *  在出现问题时抛出 IllegalArgumentException 异常。 */
    static String readContentsAsString(File file) {
        return new String(readContents(file), StandardCharsets.UTF_8);
    }

    /** 将 CONTENTS 中的字节连接起来并写入到 FILE 中，
     *  根据需要创建或覆盖文件。CONTENTS 中的每个对象可以是 String 或字节数组。
     *  在出现问题时抛出 IllegalArgumentException 异常。 */
    static void writeContents(File file, Object... contents) {
        try {
            if (file.isDirectory()) {
                throw new IllegalArgumentException("无法覆盖目录");
            }
            BufferedOutputStream str =
                    new BufferedOutputStream(Files.newOutputStream(file.toPath()));
            for (Object obj : contents) {
                if (obj instanceof byte[]) {
                    str.write((byte[]) obj);
                } else {
                    str.write(((String) obj).getBytes(StandardCharsets.UTF_8));
                }
            }
            str.close();
        } catch (IOException | ClassCastException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    /** 从文件 FILE 中读取一个类型为 T 的对象，并将其转换为 EXPECTEDCLASS 类型。
     *  在出现问题时抛出 IllegalArgumentException 异常。 */
    static <T extends Serializable> T readObject(File file,
                                                 Class<T> expectedClass) {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file));
            T result = expectedClass.cast(in.readObject());
            in.close();
            return result;
        } catch (IOException | ClassCastException
                 | ClassNotFoundException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    /** 将 OBJ 写入到文件 FILE 中。 */
    static void writeObject(File file, Serializable obj) {
        writeContents(file, serialize(obj));
    }


    /* 其他文件工具 */

    /* 将 FIRST 和 OTHERS 连接起来成为一个文件标识符，
     * 类似于 {@link java.nio.file.Paths.#get(String, String[])} 方法。 */
    static File join(String first, String... others) {
        return Paths.get(first, others).toFile();
    }

    /* 将 FIRST 和 OTHERS 连接起来成为一个文件标识符，
     * 类似于 {@link java.nio.file.Paths.#get(String, String[])} 方法。 */
    static File join(File first, String... others) {
        return Paths.get(first.getPath(), others).toFile();
    }


    /* 序列化工具 */

    /** 返回包含 OBJ 序列化内容的字节数组。 */
    static byte[] serialize(Serializable obj) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(obj);
            objectStream.close();
            return stream.toByteArray();
        } catch (IOException excp) {
            throw error("序列化对象时出现内部错误.");
        }
    }



    /* 消息和错误报告 */

    /**
     * 打印出 MESSAGE 并以错误代码 -1 退出。
     * 注意：
     *     错误/退出代码的功能在 Gitlet 中有所不同，
     *     所以请不要将其作为参考。
     *     请查阅规范以获取更多信息。
     * @param message 要打印的消息
     */
    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(-1);
    }

    /** 返回一个 RuntimeException，其消息由 MSG 和 ARGS 组成，
     *  与 String.format 方法相同。 */
    static RuntimeException error(String msg, Object... args) {
        return new RuntimeException(String.format(msg, args));
    }

}
