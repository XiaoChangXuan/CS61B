package gitlet;

import java.io.File;
import static gitlet.Utils.*;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @author XiaoChangXuan
 */
public class Repository {
    /*
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = join(new File(System.getProperty("user.dir")), "workstation");
    // public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGING_AREA = join(GITLET_DIR, ".staging");
    public static final File COMMIT_FOLDER = join(GITLET_DIR, ".commits");
    public static final File BOLB_FOLDER = join(GITLET_DIR, ".bolbs");
    public static final File BRANCHE_FOLDER = join(GITLET_DIR, ".branches");
    public static final File CUR_BRANCHES = join(GITLET_DIR, "curbranch");
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z");
    /**
     * 进行必要的文件系统操作以允许持久性。
     * (创建任何必要的文件夹或文件)
     * 提示: 推荐的结构（你不一定要遵循）:
     * proj2/ --proj2 文件夹中所有持久数据的顶层文件夹
     *     -.gitlet/ -- 存储区
     *         - staging/ -- 暂存工作区文件
     *         - commits/ -- 存储的commit类
     *         - bolbs/ -- 存储的bolb类
     *         - banches/ -- 存储当前分支中各个文件的对应情况
     */
    private static void setupPersistence() {
        GITLET_DIR.mkdirs();
        STAGING_AREA.mkdirs();
        COMMIT_FOLDER.mkdirs();
        BOLB_FOLDER.mkdirs();
        BRANCHE_FOLDER.mkdirs();
        try {
            CUR_BRANCHES.createNewFile();
        } catch (IOException e) {
            // 处理 IOException
            System.out.println("无法创建文件: " + e.getMessage());
        }
    }
    private static void isInitialized(String init) {
        // 检查当前工作目录是否包含 .gitlet 子目录
        File gitletDir = GITLET_DIR;
        boolean initial = gitletDir.exists() && gitletDir.isDirectory();
        if (init == null) {
            exitPrintMessage(!initial, "Not in an initialized Gitlet directory.");
        } else {
            exitPrintMessage(initial,
                    "A Gitlet version-control system already exists in the current directory.");
        }
    }
    private static List<String> isHaveUnTrackedFile() {
        List<String> unTrackedFileNames = getPathNamesList(CWD);
        List<String> stageFiles = getPathNamesList(STAGING_AREA);
        unTrackedFileNames.removeIf(file -> gitletCurCommit().getFiles().containsKey(file)
                || gitletCurBranch().getRemoveFile().contains(file) || stageFiles.contains(file));
        exitPrintMessage(!unTrackedFileNames.isEmpty(),
                "There is an untracked file in the way; delete it, or add and commit it first.");
        return unTrackedFileNames;
    }
    private static boolean isHaveBranch(String branchName) {
        List<String> branches = getPathNamesList(BRANCHE_FOLDER);
        return !branches.contains(branchName);
    }
    private static boolean fileIsNotCommitFile(String fileName, File file) {
        exitPrintMessage(!file.exists() || file.isDirectory(), "File does not exist.");
        Blob commitBold = gitletBold(fileName);
        String content = readContentsAsString(file);
        Blob workBold = new Blob(fileName, content);
        return commitBold == null || !Objects.equals(commitBold.getBlobId(), workBold.getBlobId());
    }
    /** 获取当前提交的commit 对象*/
    private static Commit gitletCurCommit() {
        Branch branch = gitletCurBranch();
        return branch.getCurCommit();
    }
    private static Branch gitletCurBranch() {
        return Branch.fromFile(readContentsAsString(CUR_BRANCHES));
    }
    private static Blob gitletBold(String fileName) {
        String blobId = gitletCurCommit().getBlobId(fileName);
        return blobId == null ? null : Blob.fromFile(blobId);
    }
    private static void printLog(Commit commit) {
        String commitId = commit.getCommitID();
        String merge = commit.getMerge();
        String data = commit.getTimestamp();
        String message = commit.getMessage();
        System.out.println("===");
        System.out.println("commit " + commitId);
        if (merge != null) {
            System.out.println("Merge: " + merge);
        }
        System.out.println("Date: " + data);
        System.out.println(message);
        System.out.println();
    }
    private static List<String> getPathNamesList(File path) {
        List<String> nameList = plainFilenamesIn(path);
        return (nameList == null)
                ? new ArrayList<>() : new ArrayList<>(Objects.requireNonNull(nameList));
    }
    private static List<String> commitFileNameList(String commitId) {
        Commit commit = Commit.fromFile(commitId);
        return commit != null ? new ArrayList<>(commit.getFiles().keySet()) : new ArrayList<>();

    }
    private static void resetCommit(String commitId) {
        isHaveCommitId(commitId);
        isHaveUnTrackedFile();
        List<String> stageFileList = getPathNamesList(STAGING_AREA);
        // 清除缓存区
        for (String fileName : stageFileList) {
            new File(STAGING_AREA, fileName).delete();
        }
        List<String> checkoutCommitFileNames = commitFileNameList(commitId);
        // write into workstation
        for (String checkoutCommitFileName : checkoutCommitFileNames) {
            gitletCheckoutCommitFile(commitId, checkoutCommitFileName);
        }
    }
    private static String isHaveCommitId(String commitId) {
        List<String> commitIds = getPathNamesList(COMMIT_FOLDER);
        int prefixLength = Math.max(commitId.length(), 5); // 最少截取前5个字符
        String prefixCommitId = commitId.substring(0, prefixLength);
        for (String id : commitIds) {
            if (id.startsWith(prefixCommitId)) {
                return id; // 找到匹配的提交 ID，返回完整的提交 ID
            }
        }
        exitPrintMessage(true, "No commit with that id exists.");
        return null; // 没有找到匹配的提交 ID，返回 null
    }
    // Main function
    public static void gitletInitial() {
        isInitialized("init");
        setupPersistence();
        String message = "initial commit";
        String timestamp = DATE_FORMAT.format(new Date(0));
        Commit commit = new Commit(message, timestamp, null, new TreeMap<>(), null);
        commit.saveCommit();
        Branch curBranch = new Branch("master", commit.getCommitID());
        curBranch.saveBranch();
        writeContents(CUR_BRANCHES, "master");
    }
    public static void gitletAddFile(String fileName) {
        isInitialized(null);
        File workFile = new File(CWD, fileName);
        exitPrintMessage(!workFile.exists() || workFile.isDirectory(), "File does not exist.");
        String content = readContentsAsString(workFile);
        File stagingFile = new File(STAGING_AREA, fileName);
        writeContents(stagingFile, content);
        if (!fileIsNotCommitFile(fileName, stagingFile)) {
            stagingFile.delete();
        }
        Branch curBranch = gitletCurBranch();
        if (curBranch.getRemoveFile().remove(fileName)) {
            curBranch.saveBranch();
        }
    }

    public static void gitletCommit(String message, String merge) {
        isInitialized(null);
        String timestamp = DATE_FORMAT.format(new Date());
        Branch branch = gitletCurBranch();
        Commit curCommit = gitletCurCommit();
        TreeMap<String, String> files = (curCommit.getFiles() == null)
                ? new TreeMap<>() : curCommit.getFiles();
        List<String> removeFiles = branch.getRemoveFile();
        List<String> tempList = new ArrayList<>(files.keySet());
        List<String> stagingFileList =  getPathNamesList(STAGING_AREA);
        boolean successRemove = tempList.removeIf(removeFiles::contains);
        exitPrintMessage(!successRemove && stagingFileList.isEmpty(),
                "No changes added to the commit.");
        removeFiles.clear();
        files.keySet().retainAll(tempList);
        for (String stagingFileName : stagingFileList) {
            File file = join(STAGING_AREA, stagingFileName);
            String content = readContentsAsString(file);
            Blob blob = new Blob(stagingFileName, content);
            blob.saveBolb();
            files.put(stagingFileName, blob.getBlobId());
            file.delete();
        }
        Commit commit = new Commit(message, timestamp, curCommit.getCommitID(), files, merge);
        commit.saveCommit();
        branch.modifyCurCommit(commit.getCommitID());
        branch.saveBranch();
    }
    public static void gitletRemoveFile(String fileName) {
        isInitialized(null);
        File stagingFile = new File(STAGING_AREA, fileName);
        Blob blob = gitletBold(fileName);
        if (stagingFile.exists() && !stagingFile.isDirectory()) {
            stagingFile.delete();
        } else if (blob != null && blob.getBlobId() != null) {
            Branch curBranch = gitletCurBranch();
            curBranch.addRemoveFile(fileName);
            curBranch.saveBranch();
            File workFile = join(CWD, fileName);
            restrictedDelete(workFile);
        } else {
            exitPrintMessage(true, "No reason to remove the file.");
        }
    }
    public static void gitletLog() {
        isInitialized(null);
        Commit curCommit = gitletCurCommit();
        for (; curCommit != null; curCommit = Commit.fromFile(curCommit.getParentCommit())) {
            printLog(curCommit);
        }
    }
    public static void gitletGlobalLog() {
        isInitialized(null);
        List<String> commitIds = getPathNamesList(COMMIT_FOLDER);
        for (String commitId : commitIds) {
            Commit curCommit = Commit.fromFile(commitId);
            printLog(curCommit);
        }
    }
    public static void gitletFindMessage(String message) {
        isInitialized(null);
        List<String> commitIds = getPathNamesList(COMMIT_FOLDER);
        for (String commitId : commitIds) {
            Commit curCommit = Commit.fromFile(commitId);
            String curMessage = curCommit.getMessage();
            if (message.equals(curMessage)) {
                System.out.println(curCommit.getCommitID());
                return;
            }
        }
        exitPrintMessage(true, "Found no commit with that message.");
    }
    public static void gitletStatus() {
        isInitialized(null);
        Branch curBranch = gitletCurBranch();
        Commit curCommit = gitletCurCommit();
        TreeMap<String, String> commitFiles = curCommit.getFiles();
        List<String> commitFileNames = new ArrayList<>(commitFiles.keySet());
        List<String> branches = getPathNamesList(BRANCHE_FOLDER);
        List<String> stageFiles = getPathNamesList(STAGING_AREA);
        List<String> removeFiles = curBranch.getRemoveFile();
        HashMap<String, String> modifications = new HashMap<>();
        List<String> unTrackedFiles = isHaveUnTrackedFile();
        // Files modified but not staged
        commitFileNames.removeIf(stageFiles::contains);
        commitFileNames.removeIf(removeFiles::contains);
        for (String commitFileName : commitFileNames) {
            File workFile = new File(CWD, commitFileName);
            if (!workFile.exists()) {
                modifications.put(commitFileName, "(deleted)");
            } else if (fileIsNotCommitFile(commitFileName, workFile)) {
                modifications.put(commitFileName, "(modified)");
            }
        }
        // Print the status
        String curBranchName = curBranch.getBranchName();
        System.out.println("=== Branches ===");
        System.out.println("*" + curBranchName);
        branches.stream().sorted().forEach(branch -> {
            if (!branch.equals(curBranchName)) {
                System.out.println(branch);
            }
        });
        System.out.println();

        System.out.println("=== Staged Files ===");
        stageFiles.stream().sorted().forEach(System.out::println);
        System.out.println();

        System.out.println("=== Removed Files ===");
        removeFiles.stream().sorted().forEach(System.out::println);
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===");
        modifications.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey()
                        + " " + entry.getValue()));
        System.out.println();

        System.out.println("=== Untracked Files ===");
        unTrackedFiles.stream().sorted().forEach(System.out::println);
        System.out.println();
    }
    public static void gitletCreateBranch(String branchName) {
        isInitialized(null);
        List<String> branches = getPathNamesList(BRANCHE_FOLDER);
        exitPrintMessage(branches.contains(branchName),
                "A branch with that name already exists.");
        Branch newBranch = new Branch(branchName, gitletCurCommit().getCommitID());
        newBranch.saveBranch();
    }
    public static void gitletDeleteBranch(String branchName) {
        isInitialized(null);
        exitPrintMessage(isHaveBranch(branchName), "A branch with that name does not exist");
        exitPrintMessage(Objects.equals(gitletCurBranch().getBranchName(), branchName),
                "Cannot remove the current branch.");
        File branch = new File(BRANCHE_FOLDER, branchName);
        branch.delete();
    }
    public static void gitletCheckoutFile(String fileName) {
        isInitialized(null);
        gitletCheckoutCommitFile(gitletCurCommit().getCommitID(), fileName);
    }
    public static void gitletCheckoutCommitFile(String shortCommitId, String fileName) {
        isInitialized(null);
        String commitId = isHaveCommitId(shortCommitId);
        String commitBoldId = Objects.requireNonNull(Commit.fromFile(commitId)).getBlobId(fileName);
        exitPrintMessage(commitBoldId == null, "File does not exist in that commit.");
        Blob commitBold = Blob.fromFile(commitBoldId);
        File workfile = new File(CWD, fileName);
        writeContents(workfile, commitBold.getContent());
    }
    public static void gitletCheckoutBranch(String branchName) {
        isInitialized(null);
        exitPrintMessage(isHaveBranch(branchName), "No such branch exists.");
        exitPrintMessage(Objects.equals(gitletCurBranch().getBranchName(), branchName),
                "No need to checkout the current branch.");
        resetCommit(Branch.fromFile(branchName).getCurCommit().getCommitID());
        writeContents(CUR_BRANCHES, branchName);
    }

    public static void gitletReset(String commitId) {
        isInitialized(null);
        isHaveCommitId(commitId);
        resetCommit(commitId);
        Branch branch = gitletCurBranch();
        branch.modifyCurCommit(commitId);
        branch.saveBranch();
    }
    public static void gitletMerge(String branchName) {
        isInitialized(null);
        isHaveUnTrackedFile();
        exitPrintMessage(isHaveBranch(branchName), "No such branch exists.");
        exitPrintMessage(Objects.equals(branchName, gitletCurBranch().getBranchName()),
                "Cannot merge a branch with itself.");
        Branch giveBranch = Branch.fromFile(branchName);
        Commit curCommit = gitletCurCommit();
        Commit otherCommit = giveBranch.getCurCommit();
        Commit splitCommit = findSplitPoint(curCommit, otherCommit);
        exitPrintMessage(splitCommit == null, "No SplitCommit");
        if (Objects.equals(curCommit.getCommitID(), splitCommit.getCommitID())) {
            resetCommit(Branch.fromFile(branchName).getCurCommit().getCommitID());
            gitletCurBranch().modifyCurCommit(otherCommit.getCommitID());
            gitletCurBranch().saveBranch();
            exitPrintMessage(true, "Current branch fast-forwarded.");
        } else if (Objects.equals(otherCommit.getCommitID(), splitCommit.getCommitID())) {
            giveBranch.modifyCurCommit(curCommit.getCommitID());
            giveBranch.saveBranch();
            exitPrintMessage(true, "Given branch is an ancestor of the current branch.");
        } else {
            TreeMap<String, String> otherCommitFiles = otherCommit.getFiles();
            TreeMap<String, String> splitCommitFiles = splitCommit.getFiles();
            TreeMap<String, String> curChangedFiles = curCommit.getFiles();
            boolean conflict = false;
            for (Map.Entry<String, String> splitFile : splitCommitFiles.entrySet()) {
                curChangedFiles.remove(splitFile.getKey(), splitFile.getValue());
                otherCommitFiles.remove(splitFile.getKey(), splitFile.getValue());
            }
            for (String fileName : otherCommitFiles.keySet()) {
                String curChangeFileBoldId = curChangedFiles.get(fileName);
                String otherChangeFileBoldId = otherCommitFiles.get(fileName);
                // 处理给定分支中, 修改分割节点后的文件和新添加的文件
                if (curChangeFileBoldId == null) { // 若当前分支没有添加或者修改,则放到缓存区
                    File file = new File(CWD, fileName);
                    String content = Blob.fromFile(otherChangeFileBoldId).getContent();
                    writeContents(file, content);
                    File stagingFile = new File(STAGING_AREA, fileName);
                    writeContents(stagingFile, content);
                } else {
                    if (Objects.equals(curChangeFileBoldId, otherChangeFileBoldId)) {
                        // 若添加 或者 修改的文件 具有不同的内容 处罚冲突
                        conflict = true;
                        Blob curBolb = Blob.fromFile(curChangeFileBoldId);
                        Blob otherBolb = Blob.fromFile(otherChangeFileBoldId);
                        String content = mergeFiles(curBolb.getContent(), otherBolb.getContent());
                        File workFile = new File(CWD, fileName);
                        writeContents(workFile, content);
                    } // 若添加或这修改的文件 具有相同的内容 则不修改
                }
            }
            for (String splitFile : splitCommitFiles.keySet()) {
                // 文件在当前分支不变,给定分支中移除
                if (!curChangedFiles.containsKey(splitFile)
                        && !otherCommitFiles.containsKey(splitFile)) {
                    new File(CWD, splitFile).delete();
                } else if (curChangedFiles.containsKey(splitFile)
                        && !otherCommitFiles.containsKey(splitFile)) {
                    conflict = true;
                    // 文件在一个分支删除,在另一个分支修改
                    Blob curBolb = Blob.fromFile(curCommit.getFiles().get(splitFile));
                    String content = mergeFiles(curBolb.getContent(), "");
                    File workFile = new File(CWD, splitFile);
                    writeContents(workFile, content);
                } else if (!curChangedFiles.containsKey(splitFile)
                        && otherCommitFiles.containsKey(splitFile)) {
                    conflict = true;
                    Blob otherBolb = Blob.fromFile(otherCommitFiles.get(splitFile));
                    String content = mergeFiles("", otherBolb.getContent());
                    File workFile = new File(CWD, splitFile);
                    writeContents(workFile, content);
                }
            }
            if (conflict) {
                exitPrintMessage(true, "Encountered a merge conflict.");
            } else {
                String curBranchName = gitletCurBranch().getBranchName();
                String message = "Merged " + branchName + " into " + curBranchName;
                String merge = curCommit.getCommitID().substring(0, 7) + " "
                        + otherCommit.getParentCommit().substring(0, 7);
                gitletCommit(message, merge);
                giveBranch.modifyCurCommit(curCommit.getCommitID());
                giveBranch.saveBranch();
            }
        }
    }
    private static Commit findSplitPoint(Commit commit1, Commit commit2) {
        Set<String> ancestors = new HashSet<>();
        // 将 commit1 的所有祖先加入 HashSet
        Commit pointer1 = commit1;
        while (pointer1 != null) {
            ancestors.add(pointer1.getCommitID());
            pointer1 = Commit.fromFile(pointer1.getParentCommit());
        }
        // 遍历 commit2 的祖先，查看是否存在于 HashSet 中
        Commit pointer2 = commit2;
        while (pointer2 != null) {
            if (ancestors.contains(pointer2.getCommitID())) {
                return pointer2;
            }
            pointer2 = Commit.fromFile(pointer2.getParentCommit());
        }

        // 如果循环结束仍然没有找到共同祖先，则返回null
        return null;
    }

    private static String mergeFiles(String currentContent, String givenContent) {
        StringBuilder mergedContent = new StringBuilder();
        String[] currentLines = currentContent.split("\n");
        String[] givenLines = givenContent.split("\n");

        int currentIdx = 0;
        int givenIdx = 0;

        while (currentIdx < currentLines.length || givenIdx < givenLines.length) {
            if (currentIdx < currentLines.length && givenIdx < givenLines.length) {
                // Compare lines from both branches
                if (currentLines[currentIdx].equals(givenLines[givenIdx])) {
                    // Both branches have the same line, add it to merged content
                    mergedContent.append(currentLines[currentIdx]).append("\n");
                    currentIdx++;
                    givenIdx++;
                } else {
                    // Lines are different, add conflict markers
                    mergedContent.append("<<<<<<< HEAD\n");
                    mergedContent.append(currentLines[currentIdx]).append("\n");
                    mergedContent.append("=======\n");
                    mergedContent.append(givenLines[givenIdx]).append("\n");
                    mergedContent.append(">>>>>>> \n");

                    // Move to next lines in both branches
                    currentIdx++;
                    givenIdx++;
                }
            } else if (currentIdx < currentLines.length) {
                // Add remaining lines from current branch
                mergedContent.append(currentLines[currentIdx]).append("\n");
                currentIdx++;
            } else {
                // Add remaining lines from given branch
                mergedContent.append(givenLines[givenIdx]).append("\n");
                givenIdx++;
            }
        }
        return mergedContent.toString();
    }
}
