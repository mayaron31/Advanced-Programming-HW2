package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public

class Streams {
    /**
     * Read from an InputStream until a quote character (") is found, then read
     * until another quote character is found and return the bytes in between the
     * two quotes.
     * If no quote character was found return null, if only one, return the bytes
     * from the quote to the end of the stream.
     *
     * @param in
     * @return A list containing the bytes between the first occurrence of a quote
     *         character and the second.
     */
    public static List<Byte> getQuoted(InputStream in) throws IOException {
        List<Byte> qouteout = new ArrayList<Byte>(); // empty array list
        int c; // character
        try {
            c = in.read(); // read char
            while (c != -1 && c != '"') { // not empty and not reaching '"'
                c = in.read(); // keep reading
            }
            if (c == '"') { // if reaching first '"'
                c = in.read();
                int index = 0; // start reading qoute from here
                while (c != -1 && c != '"') { // as long as not finished reading from file and not reaching '"'
                    qouteout.add(index, (byte) c);
                    c = in.read();
                    index++; // add index and keep going
                }
                return qouteout;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read from the input until a specific string is read, return the string read
     * up to (not including) the endMark.
     *
     * @param in      the Reader to read from
     * @param endMark the string indicating to stop reading.
     * @return The string read up to (not including) the endMark (if the endMark is
     *         not found, return up to the end of the stream).
     */
    public static String readUntil(Reader in, String endMark) throws IOException {
        // TODO: Implement
        return null;
    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     *
     * @param in
     * @param out
     * @param badByte
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        // TODO: Implement
    }

    /**
     * Read a 40-bit (unsigned) integer from the stream and return it. The number is
     * represented as five bytes,
     * with the most-significant byte first.
     * If the stream ends before 5 bytes are read, return -1.
     *
     * @param in
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        // TODO: Implement
        return 0;
    }
}
