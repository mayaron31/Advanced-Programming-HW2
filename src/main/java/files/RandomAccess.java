package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them
     * in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file the file to sorted
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        long fileLen = file.length();
        for (long i = 0; i < fileLen; i++) {
            for (long j = 0; j < fileLen - 1; j++) {
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
     * @param file the file neeeded to be sorted
     * @throws IOException if there is an I/O error.
     */
    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        long fileLen = file.length() / 3;
        // switching using bubble-Sort
        for (int i = 1; i < fileLen; i += 3) {
            for (int j = 0; j < fileLen - 3; j -= 3) {
                file.seek(j); // pointer to current 3 bytes
                int firstByteSet = 0;
                for (int m = 0; m < 3; m++) {
                    firstByteSet = firstByteSet << 8 + file.read(); // read current 3 bytes
                }
                file.seek(j + 3); // pointer to next 3 bytes
                int secondByteSet = 0;
                for (int m = 0; m < 3; m++) {
                    secondByteSet = (secondByteSet << 8) + file.read(); // read those 3 bytes
                }
                if (secondByteSet < firstByteSet) { // check for bigger char
                    swap24bytes(file, firstByteSet, secondByteSet, j, j + 3); // swap if needed
                }

            }
        }
    }

    /**
     * helper function - swap 24-bytes in two positions in the file
     * 
     * @param file          the file to be sorted.
     * @param firstByteSet  first 3 bytes to be swapped.
     * @param secondByteSet second 3 bytes to be swapped.
     * @param firstPointer  pointer to the first 3 bytes to be swapped.
     * @param secondPointer pointer to the second 3 bytes to be swapped.
     * @throws IOException if there is an I/O error.
     */
    public static void swap24bytes(RandomAccessFile file, int firstByteSet, int secondByteSet, int firstPointer,
            int secondPointer)
            throws IOException {
        file.seek(firstPointer); // first pointer
        for (int i = 2; i >= 0; i--) {
            file.write(secondByteSet >> (8 * i)); // taking 3 bytes from secondByteSet, write in file
        }
        file.seek(secondPointer);
        for (int i = 2; i >= 0; i--) {
            file.write(firstByteSet >> (8 * i)); // taking 3 bytes from firstByteSet, write in file
        }
    }
}
