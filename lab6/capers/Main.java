package capers;

import java.util.Arrays;
import java.io.File;

import static capers.Utils.*;

/** Canine Capers: A Gitlet Prelude.
 * 狗狗的故事：Gitlet 的前奏.
 * @author XiaoChangXuan
 */
public class Main {
    /**
     * 运行三个命令之一：
     * story [text] -- 将 "text" + 一个换行符 追加到 .capers 目录中的故事文件中。
     *                 另外，打印当前故事。
     *
     * dog [name] [breed] [age] -- 使用指定的参数持久地创建一只狗；
     *                             还应该打印狗的 toString()。假设狗的名字是唯一的。
     *
     * birthday [name] -- 持久地增加一只狗的年龄，并打印庆祝消息。
     *
     * 所有持久数据应存储在当前工作目录中的 ".capers" 目录中。
     *
     * 推荐的结构（你不必遵循）:
     *
     * *不应手动创建这些文件夹/文件，
     * 你的程序应该创建这些文件夹/文件*
     *
     * .capers/ -- 你的 lab12 文件夹中所有持久数据的顶层文件夹
     *    - dogs/ -- 包含所有狗的持久数据的文件夹
     *    - story -- 包含当前故事的文件
     *
     * @param args 命令行中的参数
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.exitWithError("必须至少有一个参数");
        }
        // System.out.println("args: " + Arrays.toString(args));
        CapersRepository.setupPersistence();
        String name;
        switch (args[0]) {
            case "story":
                /* 这个调用已经为您处理了。其余的将类似。 */
                validateNumArgs("story", args, 2);
                String text = args[1].replaceAll("\"", "");
                File storyFile = CapersRepository.STORY_FOLD;
                String storyContent = Utils.readContentsAsString(storyFile);
                String newText = storyContent.isBlank() ? text : storyContent + "\n" + text;
                Utils.writeContents(storyFile, newText);
                System.out.println(newText);
                break;
            case "dog":
                validateNumArgs("dog", args, 4);
                name = args[1];
                String breed = args[2];
                int age = Integer.parseInt(args[3]);
                CapersRepository.makeDog(name, breed, age);
                break;
            case "birthday":
                validateNumArgs("birthday", args, 2);
                name = args[1];
                CapersRepository.celebrateBirthday(name);
                break;
            default:
                exitWithError(String.format("未知命令: %s", args[0]));
        }
        return;
    }

    /**
     * 检查参数数量与预期数量是否匹配，
     * 如果不匹配则抛出 RuntimeException。
     *
     * @param cmd 验证的命令名称
     * @param args 命令行参数数组
     * @param n 期望的参数数量
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("对于命令 %s，参数数量无效.", cmd));
        }
    }
}
