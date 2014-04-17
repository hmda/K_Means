package Content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.LinkedList;

public class Cluster {
	
	private LinkedList<Vector> vectors;
	private Vector center;
	
	public Cluster() {
		// TODO Auto-generated constructor stub
		this.vectors = new LinkedList<Vector>();
	}

	public void put(Vector x) {
		this.vectors.add(x);
	}
	public void push(Vector x) {
		this.vectors.push(x);
	}
	
	public int size() {
		return this.vectors.size();
	}
	
	public LinkedList<Vector> getArticles() {
		return this.vectors;
	}
	
	public void setCenter(Vector center) {
		this.center = center;
	}
	public Vector getCenter() {
		return this.center;
	}
	public void reCalculatingCenter(int nVector){
		double[] vector = new double[nVector];
		for (Vector vector1 : this.vectors) {
			for (int i = 0; i < nVector; i++)
				vector[i] += vector1.getVector()[i];
		}
		for (int i = 0; i < nVector; i++) {
			vector[i] = vector[i] / nVector;
		}
		this.center.setVector(vector);
	}
	
	/**
	 * @param cluster
	 * @return true if == else return false
	 */
	public boolean compare(Cluster cluster) {
		if (this.vectors.size() != cluster.getArticles().size())
			return false;
		for (int i = 0; i < this.vectors.size(); i++)
			if (vectors.get(i).compare(cluster.getArticles().get(i)))
				return false;
		return true;
	}
	
	public void copy(Cluster cluster) {
		this.vectors.clear();
		this.vectors.addAll(cluster.getArticles());
	}

	public void printOutputFile(int stt){		
		String path, name, x;
		for (Vector vector : this.vectors) {
			try {
				name = vector.getName();
				FileInputStream fos1 = new FileInputStream(name);
				Reader input = new java.io.InputStreamReader(fos1, "UTF8");
				BufferedReader inputbuf = new BufferedReader(input);
            
				String name_output = name.replace("output_filter", "clusters\\" + stt + "\\");
				File file = new File(name_output);
				file.getParentFile().mkdirs();
				FileOutputStream fos2;
				fos2 = new FileOutputStream(name_output);
				Writer output = new java.io.OutputStreamWriter(fos2,"UTF8");
				
				String lineRead;
				while ((lineRead = inputbuf.readLine()) != null){
	            	lineRead = lineRead.trim();
					output.write(lineRead);
	            }  
				//System.out.println(content);
				input.close();
			    output.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Khong mo duoc file");
				e.printStackTrace();
			}
	    }
	}
	
}
