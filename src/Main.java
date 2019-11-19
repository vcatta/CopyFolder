import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import static java.nio.file.FileVisitResult.CONTINUE;

public class Main {

    private static final Path SOURCE = Paths.get("C:/Users/v.namochilina/desktop/test");
    private static final Path TARGET = Paths.get("C:/Users/v.namochilina/desktop/test2");

    public static void main(String[] args) throws IOException{

        Files.walkFileTree(SOURCE, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException
                    {
                        Path targetDir = TARGET.resolve(SOURCE.relativize(dir));
                        try {
                            Files.copy(dir, targetDir);
                        } catch (FileAlreadyExistsException ex) {
                            if (!Files.isDirectory(targetDir))
                                ex.printStackTrace();
                        }
                        return CONTINUE;
                    }
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException
                    {
                        Files.copy(file, TARGET.resolve(SOURCE.relativize(file)));
                        return CONTINUE;
                    }
                });



    }
}
