import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String s = File.separator;
        String srcFolder = "C:" + s + "Users" + s + "stimu" + s + "Desktop" + s + "src";
        String dstFolder = "C:" + s + "Users" + s + "stimu" + s + "Desktop" + s + "dst";
        int newWidth = 300;
        long start = System.currentTimeMillis();

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        int cores = Runtime.getRuntime().availableProcessors();

        List<File[]> filesList;
        if (files != null) {
            filesList = getListFiles(files, cores);
            for (File[] files1 : filesList) {
                new Thread(new ImageResizer(files1, newWidth, dstFolder, start)).start();
            }
        }
    }

    public static List<File[]> getListFiles(File[] files, int cores) {
        List<File[]> fileList = new ArrayList<>();
        int size = files.length / cores;
        for (int i = 0; i < cores; i++) {
            if (i == cores - 1) {
                int sizeLast = size + (files.length - (size * cores));
                File[] files1 = new File[sizeLast];
                System.arraycopy(files, size * i, files1, 0, sizeLast);
                fileList.add(files1);
            } else {
                File[] files1 = new File[size];
                System.arraycopy(files, size * i, files1, 0, size);
                fileList.add(files1);
            }
        }
        return fileList;
    }
}
