package Content;

import java.io.File;
import java.io.FileOutputStream;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class Tokenizer {
	public void token(String pathRead, String pathWrite){
		File file = new File(pathWrite);
		file.getParentFile().mkdirs();
		 VietTokenizer tokenizer = new VietTokenizer();
	     tokenizer.tokenize(pathRead, pathWrite);
	}
}
