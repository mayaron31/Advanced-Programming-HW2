package dict;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;
import java.io.Writer;

/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * <p>
 * The file format has one keyword per line:
 * 
 * <pre>
 * word:def
 * </pre>
 * <p>
 * Note that an empty definition list is allowed (in which case the entry would
 * have the form:
 * 
 * <pre>
 * word:
 * </pre>
 *
 * @author talm
 */
public class InMemoryDictionary extends TreeMap<String, String> implements PersistentDictionary {
    private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
    private File dictFile;

    public InMemoryDictionary(File dictFile) {
        this.dictFile = dictFile;
    }

    @Override
    public void open() throws IOException {
        if (!this.dictFile.exists()) { // if the file doesnt exit- return
            return;
        }
        this.clear(); // clear current content from file

        // open FileReader and BufferedReader
        Reader in = new FileReader(this.dictFile);
        BufferedReader reader = new BufferedReader(in);
        String text = reader.readLine(); // read first line from reader file

        while (text != null) { // iterate lines if they exit
            int colonPosition = text.indexOf(":");
            if (colonPosition != -1) { // check if there is a colon
                String definition = ""; // empty
                if (colonPosition != text.length() - 1) { // if there is a definition after colon
                    definition = text.substring(colonPosition + 1);
                }
                this.put(text.substring(0, colonPosition), definition); // put together keys and their values in the
                                                                        // TreeMap
            }
            text = reader.readLine();
        }
        // close reader and bufferedReader
        reader.close();
        in.close();
    }

    @Override
    public void close() throws IOException {
        // create FileWriter and BufferedWriter objects
        Writer out = new FileWriter(this.dictFile);
        BufferedWriter writer = new BufferedWriter(out);

        Set<String> keysSet = this.keySet(); // get keys set from TreeMap
        Iterator<String> iterator = keysSet.iterator(); // create iterator to run keys

        while (iterator.hasNext()) { // run on the keys in the TreeMap
            String key = iterator.next();
            String word = key + ":" + this.get(key); // concatenate keys and seperate them from their values by colon

            writer.write(word);// write this to the file
            writer.newLine();// move to next line in file
        }
        writer.close();
        out.close();// make sure its close

    }
}
