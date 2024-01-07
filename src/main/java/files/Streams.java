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
     * helper function which convert byte List to a byte array
     * 
     * @param List<Byte> byteList the list the contains bytes
     * @return a byte Array of the same bytes as in the input list
     */
    private static byte[] arrayOfBytes(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()]; // calculate the array size
        // copy byte object from list to the same position in the array
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = (byte) byteList.get(i);
        }
        return byteArray;
    }

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
            while (c != -1 && c != '"') { // if we didn't find the first occurrence of '"' char and have'nt finished
                                          // running
                c = in.read(); // read next char
            }
            if (c == '"') { // if found first '"' char
                c = in.read();
                int index = 0; // start reading qoute from here
                while (c != -1 && c != '"') { // as long as not finished reading from file and not finding closing '"'
                                              // char
                    qouteout.add(index, (byte) c); // add char to the list
                    c = in.read();
                    index++; // increment index
                }
                return qouteout;
            }
        } catch (IOException e) { // catch an I/O error
            e.printStackTrace();
        }
        in.close();// close stream
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
        StringBuilder inputText = new StringBuilder(); // build new string to contain input until endmark.
        int c; // character
        try {
            c = in.read(); // read first char
            while (c != -1 && inputText.indexOf(endMark) == -1) { // read if the inputText is not empty and not reaching
                                                                  // endmark index
                inputText.append((char) c); // add char to inputText.
                c = in.read(); // read next chat
            }
            int endMarkFlag = inputText.indexOf(endMark); // position of endmark in inputText
            if (endMarkFlag != -1) {
                return inputText.substring(0, endMarkFlag); // delete endmark
            }
            return inputText.toString();
        } catch (IOException e) { // catch an I/O error
            e.printStackTrace();
        }
        in.close(); // make sure input stream is closed closed
        return null;

    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     *
     * @param in      an inputStream which we read the number from
     * @param out     the outputStream where we write the filter bytes
     * @param badByte The badBytes to be filterd/ignored
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        ArrayList<Byte> inputTextFilterd = new ArrayList<Byte>(); // add new array list of bytes
        int c;
        try {
            c = in.read();
            while (c != -1) {
                byte byteC = (byte) c;
                if (Byte.compare((byte) c, badByte) != 0) { // if current Byte is not badByte, add the current byte to
                                                            // the filtered list
                    inputTextFilterd.add(byteC);
                }
                c = in.read(); // read next byte
            }
            out.write(arrayOfBytes(inputTextFilterd)); // write to the outputstream
        } catch (IOException e) { // catch an I/O error
            e.printStackTrace();
        }
        // make sure input and output streams are closed
        in.close();
        out.close();
    }

    /**
     * Read a 40-bit (unsigned) integer from the stream and return it. The number is
     * represented as five bytes,
     * with the most-significant byte first.
     * If the stream ends before 5 bytes are read, return -1.
     *
     * @param in an inputStream which we read the number from
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        int counter = 0; // counter that will count until reaching 5
        long outputNum = 0; // the final number that will be returned
        try {
            int inputByte = in.read(); // read first byte
            while (inputByte != -1 && counter < 5) {
                outputNum = (outputNum << 8) + inputByte; // build number by shifting 8 bytes to the left and adding new
                                                          // byte
                counter++; // move to next byte in count
                inputByte = in.read(); // read next byte from inputstream
            }
            if (counter > 5) { // if finish before reading 5 bytes
                return -1;
            }
        } catch (IOException e) { // catch an I/O error
            e.printStackTrace();
        }
        return outputNum; // the 40-bytes
    }
}
