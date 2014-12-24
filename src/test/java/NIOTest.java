import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;


public class NIOTest {

    /**
     * 深度拷贝
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        final Path p = Paths.get("D:", "project", "NIOLearning");
        final Path to = Paths.get("d:\\project\\t");
        Files.walkFileTree(p, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,

                new SimpleFileVisitor<Path>() {

                    @Override

                    public FileVisitResult preVisitDirectory(Path dir,

                                                             BasicFileAttributes attrs)

                            throws IOException {

                        Path targetdir =
                                to.resolve(to.getFileSystem().getPath(p.relativize(dir).toString()));

                        try {

                            Files.copy(dir, targetdir,
                                    StandardCopyOption.COPY_ATTRIBUTES);

                        } catch (FileAlreadyExistsException e) {

                            if (!Files.isDirectory(targetdir))

                                throw e;

                        }

                        return FileVisitResult.CONTINUE;

                    }

                    @Override

                    public FileVisitResult visitFile(Path file, BasicFileAttributes
                            attrs) throws IOException {

                        Path targetfile = to.resolve(to.getFileSystem()

                                .getPath(p.relativize(file).toString()));

                        Files.copy(file, targetfile,
                                StandardCopyOption.COPY_ATTRIBUTES);

                        return FileVisitResult.CONTINUE;

                    }

                });
    }

    @Test
    public void test2() throws IOException {
        Path p = Paths.get("D:", "project", "NIOLearning");

        System.out.println(Files.getOwner(p));
    }

    @Test
    public void test3() throws IOException {
        Path p = Paths.get("D:", "project", "NIOLearning");
        WatchEvent.Kind[] kinds = new WatchEvent.Kind[1];
        kinds[0] = StandardWatchEventKinds.ENTRY_CREATE;
        WatchService watcher = p.getFileSystem().newWatchService();
        WatchKey key = p.register(watcher, kinds);
/*        for (; ; ) {
            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                System.out.println(watchEvent.context());
            }
        }*/
        for (; ; ) {
            try {
                WatchKey watchKey = watcher.take();
                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    System.out.println(watchEvent.context());
                }

            } catch (InterruptedException ie) {

                break;

            }

        }
    }

    @Test
    public void test5() {
        System.out.println(FileSystems.getDefault().getPath("d:"));
    }
}