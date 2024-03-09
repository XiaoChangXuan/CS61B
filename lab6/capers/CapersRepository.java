package capers;

import java.io.File;
import static capers.Utils.*;

/** Capers 的仓库
 * @author XiaoChangXuan
 * Capers 仓库的结构如下:
 * .capers/ -- 你的 lab12 文件夹中所有持久数据的顶层文件夹
 *    - dogs/ -- 包含所有狗的持久数据的文件夹
 *    - story -- 包含当前故事的文件
 *
 */
public class CapersRepository {
    /** 当前工作目录 */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** 主要元数据文件夹 */
    static final File CAPERS_FOLDER = join(CWD, ".capers");
    static final File DOGS_FOLDER = join(CAPERS_FOLDER, "dogs");
    static final File STORY_FOLD = join(CAPERS_FOLDER, "story.txt");

    /**
     * 进行必要的文件系统操作以允许持久性。
     * (创建任何必要的文件夹或文件)
     * 提示: 推荐的结构（你不一定要遵循）:
     *
     * .capers/ -- 你的 lab12 文件夹中所有持久数据的顶层文件夹
     *    - dogs/ -- 包含所有狗的持久数据的文件夹
     *    - story -- 包含当前故事的文件
     */
    public static void setupPersistence() {
        DOGS_FOLDER.mkdirs();
        try {
            // 创建 story 文件
            STORY_FOLD.createNewFile();
        } catch (Exception e) {
            // 处理文件创建失败的情况
            System.out.println("无法创建文件: " + e.getMessage());
        }
    }

    /**
     * 将 args 中第一个非命令参数的文本附加到名为 `story` 的文件中，
     * 该文件位于 .capers 目录中。
     * @param text 要附加到故事中的文本字符串
     */
    public static void writeStory(String text) {
        File storyFile = STORY_FOLD;
        writeObject(storyFile, text);
    }

    /**
     * 使用 args 的前三个非命令参数（name, breed, age）创建并持久保存一只狗。
     * 同时使用 toString() 打印出狗的信息。
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * 持久地增加一只狗的年龄，并打印出庆祝消息。
     * 同时使用 toString() 打印出狗的信息。
     * 根据 args 的第一个非命令参数选择要增加年龄的狗。
     * @param name 要庆祝生日的狗的名字字符串
     */
    public static void celebrateBirthday(String name) {
        Dog dog = Dog.fromFile(name);
        dog.haveBirthday();
    }
}
