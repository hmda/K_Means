package Content;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.Reader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;



public class Vector {
	private LinkedList<String> vectorDicWords = new LinkedList<String>();
	private double[] vector;
	private String nameInput;
	
	public void setName(String name){
		nameInput = name.replace("output_tagger", "output_filter");
	}
	
	public String getName(){
		return nameInput;
	}
	
	public LinkedList<String> getVectorDicWord(){
		FileInputStream fos1;
		try {
			fos1 = new FileInputStream("Word_Dic.txt");
			Reader input = new java.io.InputStreamReader(fos1, "UTF8");
	        BufferedReader inputbuf = new BufferedReader(input);
			String word;
			while ((word = inputbuf.readLine()) != null)
				vectorDicWords.add(word);
			input.close();
			return vectorDicWords;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void createVector(LinkedList<String> vectorDicWords, String pathRead) {
		this.vector = new double[vectorDicWords.size()];
		BST tree = new BST();
		
		FileInputStream fos1;
		Reader input;
		BufferedReader inputbuf;
		String lineRead, lineWrite;
        int length, index;
        String[] temp;
        long find;
        
        try {
			fos1 = new FileInputStream(pathRead);
			input = new java.io.InputStreamReader(fos1, "UTF8");
	        inputbuf = new BufferedReader(input);
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
	                }
	            }
	        }
	        input.close();
	        for (String word : vectorDicWords) {
				long d = tree.find(word);
				if (d!=-1) vector[vectorDicWords.indexOf(word)] = d;
				else vector[vectorDicWords.indexOf(word)] = 0;
			}
	        tree.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void setVector(double[] vector) {
		this.vector = vector;
	}
	
	public double[] getVector(){
		return this.vector; 
	}
	
	public boolean compare(Vector x) {
		if (nameInput.compareTo(x.getName()) == 0)
			return true;
		return false;
	}
	
public double calculatingRange(Vector x){
		
		double XY = 0, X = 0, Y = 0;
		
		for (int i = 0; i < this.vector.length; i++) {
			XY += this.vector[i] * x.getVector()[i];
			X += this.vector[i] * this.vector[i];
			Y += x.getVector()[i] * x.getVector()[i];
		}
		
		X = Math.sqrt(X);
		Y = Math.sqrt(Y);
		
		if (X == 0 || Y == 0)
			return 0;
		
		DecimalFormat df = new DecimalFormat("0.00000");
		df.setRoundingMode(RoundingMode.UP);
		//System.out.println("XY: " + XY + ", X: " + X + ", Y: " + Y);
		double d = Double.parseDouble(df.format(XY / (X * Y)));
		
		return d;		
	}
}
