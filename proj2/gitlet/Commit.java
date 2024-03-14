package gitlet;

import static gitlet.Utils.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author XiaoChangXuan
 */
public class Commit implements Serializable {
    /** Represents a gitlet commit object.
    *  This class represents a commit in the version control system.
    *  Each commit has a unique ID, a message, a timestamp, and possibly
    *  other properties depending on the implementation.
    *
     */
    static final File COMIT_FOLDER = Repository.COMMIT_FOLDER;
    /** The message of this Commit. */
    private final String message;
    /** The timestamp of this Commit. */
    private final String timestamp;
    /** The unique ID (hash) of this Commit. */
    private final String commitID;
    /** The parent commit of this Commit. */
    private final String merge;
    private final String parentCommit;
    /** The files included in this Commit. */
    private final TreeMap<String, String> files;
    /** Constructor for a new Commit.
     *  @param message The commit message.
     *  @param timestamp The commit timestamp.
     *  @param parentCommit The parent commit of this commit.
     */
    public Commit(String message, String timestamp, String parentCommit,
                  TreeMap<String, String> files, String merge) {
        this.message = message;
        this.timestamp = timestamp;
        this.parentCommit = parentCommit;
        this.files = files;
        this.commitID = createCommitId();
        this.merge = merge;
    }
    private String createCommitId() {
        List<Object> vals = new ArrayList<>();
        vals.add(message);
        vals.add(timestamp);
        if (parentCommit != null) {
            vals.add(parentCommit);
        }
        if (files != null) {
            vals.addAll(files.values());
        }
        return sha1(vals);
    }

    public String getMessage() {
        return this.message;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public String getCommitID() {
        return this.commitID;
    }
    public String getMerge() {
        return this.merge;
    }
    public String getParentCommit() {
        return this.parentCommit;
    }
    public TreeMap<String, String> getFiles() {
        return this.files;
    }
    public String getBlobId(String fileName) {
        return files == null ? null : files.get(fileName);
    }
    /**
     * @param commitID Name of commitId to load
     * @return commit read from file
     */
    public static Commit fromFile(String commitID) {
        if (commitID == null) {
            return null;
        }
        File commit = new File(COMIT_FOLDER, commitID);
        return readObject(commit, Commit.class);
    }
    /**
     * 保存commit为文件
     */
    public void saveCommit() {
        File commit = new File(COMIT_FOLDER, commitID);
        writeObject(commit, this);
    }
}

