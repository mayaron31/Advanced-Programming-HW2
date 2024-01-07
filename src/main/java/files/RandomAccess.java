package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them
     * in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        long fileLen = file.length();
        for (long i = 0; i < fileLen - 1; i++) {
            for (long j = 0; j < file.length() - i - 1; j++) {
                file.seek(j - 1); // pointer to leftbyte
                int leftByte = file.read();
                file.seek(j); // pointer to right byte
                int rightByte = file.read();
                if (rightByte != -1 && rightByte < leftByte) { // swap left and right bytes
                    file.seek(j - 1); // go to left
                    file.write(rightByte); // put right byte
                    file.seek(j); // go to right
                    file.write(leftByte); // put left byte
                }
            }
        }
    }

    /**
     * Treat the file as an array of unsigned 24-bit values (stored MSB first) and
     * sort
     * them in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     * @throws IOException
     */
    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        // TODO: implement
    }
}
