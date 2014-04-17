package Content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author N56V
 */
public class Changer {
    private String[] words = new String[101];
    private long[] amount = new long[101];
    private String pathWrite = "Word_Dic.txt";
    public void change(){
    	File[] paths = readFolder_Tagger();
    	for(File pathRead:paths){
        try {        	
            FileInputStream fos1;
            fos1 = new FileInputStream(pathRead);
            Reader input = new java.io.InputStreamReader(fos1, "UTF8");
            BufferedReader inputbuf = new BufferedReader(input);
            String lineRead;
            
            String lineWrite;
            
            int length, index;
            String[] temp;
            BST tree = new BST();
            long find;
            
            // Tải từng dòng từ file "token.txt"
            while ((lineRead = inputbuf.readLine()) != null){
            for (String retval: lineRead.split(" ")){
                length = retval.length();
                length -= 1;
                // Phát hiện danh từ
                if ((retval.charAt(length) == 'N')
                        ||(retval.charAt(length) == 'p')
                        ||(retval.charAt(length) == 'c')
                        ||(retval.charAt(length) == 'y')
                        ||(retval.charAt(length) == 'u')){
                    temp = retval.split("/");
                    lineWrite = temp[0];
                    lineWrite = lineWrite.toLowerCase();    // Chuyển thành chữ thường
                    tree.insert(lineWrite);  // Đưa vào BST
                    find = tree.find(lineWrite);    // Số lượng các nút giống thế trong BST
                    // Danh từ đang trong số các danh từ nhiều nhất thì chỉ thay đổi số lượng
                    // Nếu không sẽ thay 1 từ ít nhất ra và nạp từ nhiều hơn vào
                    if (checkWord(lineWrite, find) == false)
			{
				index = minAmount();
				if (find > amount[index])
				{
					amount[index] = find;
					words[index] = lineWrite;
				}
			}
                }
            }
        }
            input.close();
            } catch (Exception e) {
            System.out.println("Khong doc duoc file1");
            e.printStackTrace();
        }
    	}
    	sort();
        changeWords(pathWrite);
    }
    
    // Kiểm tra danh từ có trong dãy nhiều nhất không?
    private boolean checkWord(String x, long find){
	int i = 0;
	while (words[i] != null)
	{
		if (words[i].equals(x))
		{
			amount[i] = find;
			return true;
		}
		i++;
	}
		return false;
    }

    // Tìm danh từ có số lượng ít nhất trong top 30
    private int minAmount(){
	int i, index;
	long min;
		
	min = amount[0]; index = 0;
	for (i = 1; i < 100; i++)
		if (amount[i] < min)
		{
			min = amount[i];
			index = i;
		}
		
	return index;
    }
    
    // Sắp xếp 30 danh từ nhiều nhất
    private void sort(){
	int i, j;
	long temp;
	String tempStr;
		
	for (i = 0; i < 99; i++)
		for (j = i+1; j < 100; j++)
			if (amount[i] < amount[j])
			{
				temp = amount[i]; 
                amount[i] = amount[j]; 
                amount[j] = temp;
                tempStr = words[i]; 
                words[i] = words[j]; 
                words[j] = tempStr;
			}
    }
    
    // Chuyển danh từ đúng định dạng yêu cầu và in ra file "AnhHMD_20140317_NOUN.tsv"
    private void changeWords(String pathWrite){
        FileOutputStream fos2;
        int i, j, lenght;
        StringBuilder temp;
        try {
            fos2 = new FileOutputStream(pathWrite);
            Writer output = new java.io.OutputStreamWriter(fos2,"UTF8");
            for (i=0; i<100; i++){
                output.write(words[i] + "\n");
            }
            output.close();
        } catch (Exception e) {
            System.out.println("Khong doc duoc file");
            e.printStackTrace();
        }
    }
    
    private File[] readFolder_Tagger() {
		 File f = null;
		 File[] paths = null;
		 try{      
	         // create new file
	         f = new File("output_tagger");
	         
	         // create new filename filter
	         FilenameFilter fileNameFilter = new FilenameFilter() {
	   
	            @Override
	            public boolean accept(File dir, String name) {
	               if(name.lastIndexOf('.')>0)
	               {
	                  // get last index for '.' char
	                  int lastIndex = name.lastIndexOf('.');
	                  
	                  // get extension
	                  String str = name.substring(lastIndex);
	                  
	                  // match path name extension
	                  if(str.equals(".txt"))
	                  {
	                     return true;
	                  }
	               }
	               return false;
	            }
	         };
	         // returns pathnames for files and directory
	         paths = f.listFiles(fileNameFilter);
	      }catch(Exception e){
	         // if any error occurs
	         e.printStackTrace();
	      }
		 return paths;
	   }
}
