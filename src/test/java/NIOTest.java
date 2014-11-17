import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

public class NIOTest extends TestCase {
    @Test
    public void test1() throws IOException {
        FileInputStream is = new FileInputStream(".gitignore");
        FileChannel channel = is.getChannel();
        System.out.println(channel.size());
    }
}