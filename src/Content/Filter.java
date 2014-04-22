package Content;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.File;

import com.google.gson.Gson;

/**
 *
 * @author N56V
 */
public class Filter {
	String lineRead, content = null;
    StringBuilder temp1;
    String temp = null;
    Article art = new Article();
    String pathWrite;
    public void readContent(File pathRead){
    	pathWrite = pathRead.toString();
    	pathWrite = pathWrite.replace("input", "output_filter");
    	pathWrite = pathWrite.replace(".json", ".txt");
        try {
            FileInputStream fos1 = new FileInputStream(pathRead);
            Reader input = new java.io.InputStreamReader(fos1, "UTF8");
            BufferedReader inputbuf = new BufferedReader(input);
            Gson gson = new Gson();
            
            int i = 0, length = 0;
            
            while ((lineRead = inputbuf.readLine()) != null){
            	i = i+1;
            	// Chỉ lấy nội dung trong file gốc tới dòng thứ 7
            	if (i < 7){
            		// Nếu là dòng đầu tiên thì thay xâu khởi đầu bàng xâu vừa đọc.
            		if (i==1) temp = lineRead;
            		else temp = temp + lineRead;
            	} else if (i == 7) {
            		// Xóa dấu "," cuối cùng.
            		length = lineRead.length();
            		length = length - 1;
            		temp1 = new StringBuilder(lineRead);
            		temp1.setCharAt(length, '}');	// Thay dấu "," cuối cùng bằng dấu "}".
            		lineRead = temp1.toString();
            		temp = temp + lineRead;
                	art = gson.fromJson(temp, Article.class);	// Lấy nội dung content.
                	content = art.getContent();
                	//System.out.println(content);
            		break;
            	}
            }            
            input.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file");
            e.printStackTrace();
        }
    }
    
    public void parseAndWriteContent(){
    	File file = new File(pathWrite);
		file.getParentFile().mkdirs();
		FileOutputStream fos2;
		try {
			fos2 = new FileOutputStream(pathWrite);
			Writer output = new java.io.OutputStreamWriter(fos2,"UTF8");
			//System.out.println(content);
			 content = content.trim();
		        // Cắt xâu tại các thẻ "<br>"
		    	for (String retval: content.split("<br>")){
		        	retval = retval.trim();
		        	if (retval.equals("")) continue;	 // Bỏ các dòng trắng sau khi chỉ lý xâu.
		        	else {
		        		// Xóa các thẻ html
		        		retval = retval.replaceAll("(<(\"[^\"]*\"|'[^']*'|[^'\">])*>)", "");
		        		retval = retval.trim();
		        		output.write(retval+"\n");
		        	}
		        }
		    	output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Khong mo duoc file");
			e.printStackTrace();
		}

    }
    
}

