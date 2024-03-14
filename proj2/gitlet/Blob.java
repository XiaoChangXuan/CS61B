package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static gitlet.Utils.*;
public class Blob implements Serializable {
    /** 每个文件对应一个Bolb, 保存文件的名称, 内容 Sring格式, 相同的文件具有相同的Id*/
    static final File BOLB_FOLDER = Repository.BOLB_FOLDER;
    private final String fileName;
    private final String content;
    private final String blobId; // SHA-1 hash of the content

    public Blob(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.blobId = createBolbId();
    }
    private String createBolbId() {
        List<Object> vales = new ArrayList<>();
        vales.add(fileName);
        vales.add(content);
        return sha1(vales);
    }
    public String getContent() {
        return content;
    }
    public String getBlobId() {
        return blobId;
    }
    /**
     * @param blobId of Blob to load
     * @return bolb read from file
     */
    public static Blob fromFile(String blobId) {
        if (blobId == null) {
            return null;
        }
        File blob = new File(BOLB_FOLDER, blobId);
        return readObject(blob, Blob.class);
    }
    /**
     * 保存bolb为文件
     */
    public void saveBolb() {
        File blob = new File(BOLB_FOLDER, blobId);
        writeObject(blob, this);
    }
}
