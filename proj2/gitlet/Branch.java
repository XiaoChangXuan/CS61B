package gitlet;

import static gitlet.Utils.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Branch implements Serializable {
    /** 保存分支的文件, 一个是当前提交commit文件名, 另一个是branch库*/
    static final File BRANCH_FOLDER = Repository.BRANCHE_FOLDER;
    private final String branchName;
    private String commitId;
    private final List<String> removeFiles = new ArrayList<>();

    public Branch(String branchName, String curCommitId) {
        this.branchName = branchName;
        this.commitId = curCommitId;

    }
    public List<String> getRemoveFile() {
        return removeFiles;
    }
    public void addRemoveFile(String fileName) {
        removeFiles.add(fileName);
    }
    public String getBranchName() {
        return this.branchName;
    }
    /** 根据branch 名字返回branch当前提交的commit*/
    public Commit getCurCommit() {
        if (commitId == null) {
            return null;
        }
        File commit = join(Repository.COMMIT_FOLDER, commitId);
        return readObject(commit, Commit.class);
    }
    public void modifyCurCommit(String curCommitId) {
        commitId = curCommitId;
    }
    public static Branch fromFile(String branchName) {
        File branch = new File(BRANCH_FOLDER, branchName);
        return readObject(branch, Branch.class);
    }
    /** 保存branch为文件 */
    public void saveBranch() {
        File branch = new File(BRANCH_FOLDER, branchName);
        writeObject(branch, this);
    }
}
