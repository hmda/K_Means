package Content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.Writer;

import jvntagger.MaxentTagger;
import jvntagger.POSTagger;

/**
 *
 * @author N56V
 */
public class Tagger {
    public void tag(String pathRead, String pathWrite){
        String modelDir;
        modelDir = "model\\maxent";
        POSTagger tagger = null;
        tagger = new MaxentTagger(modelDir);
        File file = new File(pathWrite);
		file.getParentFile().mkdirs();
         
        try {
            FileInputStream fos1;
            fos1 = new FileInputStream(pathRead);
            Reader input = new java.io.InputStreamReader(fos1, "UTF8");
            BufferedReader inputbuf = new BufferedReader(input);
            String lineRead;
            
            FileOutputStream fos2 = new FileOutputStream(pathWrite);
            Writer output = new java.io.OutputStreamWriter(fos2,"UTF8");
            String lineWrite;
            
            // Tải từng sòng từ file "baomoi.content.20140315.txt"
            while ((lineRead = inputbuf.readLine()) != null){
                // Phân loại từ bảng VnTagger
                lineWrite = tagger.tagging(lineRead);
                output.write(lineWrite);
            }
            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file");
            e.printStackTrace();
        }
    }
}
