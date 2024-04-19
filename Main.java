import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static final String savePath = "C://Users//Account//IntelliJ IDEA Project//" +
            "HWInstallGamas//Games//savegames//";

    public static boolean saveGame(GameProgress game, String file) {
        try (FileOutputStream fos = new FileOutputStream(savePath + file + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public static boolean zipFiles(String zipPath, ArrayList<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new
                FileOutputStream(savePath + zipPath))) {
            for (String file : filesToZip) {
                FileInputStream fis = new FileInputStream(savePath + file);
                ZipEntry entry = new ZipEntry(file);
                zos.putNextEntry(entry);

                byte[] buffer = new byte[fis.available()];

                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public static boolean deleteFile(ArrayList<String> filesToDelete) {
        try {
            for (String fileToDelete : filesToDelete) {
                Path path = Path.of(savePath + fileToDelete);
                Files.delete(path);
            }
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public static ArrayList<String> filesList() {
        ArrayList<String> filesToZip = new ArrayList<>();
        File dir = new File(savePath);
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isFile()) {
                    if (item.getName().contains(".dat")) {
                        filesToZip.add(item.getName());
                    }
                }
            }
        }
        return filesToZip;
    }
    public static void main(String[] args) {

        GameProgress gamer1 = new GameProgress(90, 105, 59, 108.4);
        GameProgress gamer2 = new GameProgress(89, 50, 43, 50.3);
        GameProgress gamer3 = new GameProgress(100, 35, 30, 46.7);

        saveGame(gamer1, "gamer1");
        saveGame(gamer2, "gamer2");
        saveGame(gamer3, "gamer3");

        ArrayList<String> filesToZip = filesList();

        zipFiles("zip.zip", filesToZip);
        System.out.println("Файлы успешно добавлены в архив");
        deleteFile(filesToZip);
    }
}

