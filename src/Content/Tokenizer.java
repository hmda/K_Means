package Content;

import java.io.File;
import java.io.FileOutputStream;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class Tokenizer {
	private VietTokenizer tokenizer ;
	public Tokenizer(){
		tokenizer = new VietTokenizer();
	}
	public void token(String pathRead, String pathWrite){
		File file = new File(pathWrite);
		file.getParentFile().mkdirs();
	    tokenizer.tokenize(pathRead, pathWrite);
	}
}
