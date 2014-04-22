package Content;


import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;



public class InOu {
	static LinkedList<String> vectorDicWords;
    static LinkedList<Vector> vectors = new LinkedList<Vector>();
    private static LinkedList<Cluster> clusters , newClusters;
    static int k, n, q=0;
    static double ranges[][];

	 public static void main(String[] args) {
	        // TODO code application logic here
		 File[] paths = readInputFolder();
		 Filter filter = new Filter();
	     Changer changer = new Changer();
	     Vector vector = new Vector();
	     clusters = new LinkedList<Cluster>();
		 newClusters = new LinkedList<Cluster>();
	     
		 while(q == 0){
		 n = paths.length;
		 System.out.println("Có " + n +" bài báo.");
		 System.out.print("Nhập số cum K: ");
		 BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		 try {
			k = Integer.parseInt(bufferRead.readLine());
			if ((k>0)&&(n>0)&&(n>=k)) q=1;
			else System.out.println("Xin lỗi đầu vào không hợp lệ!");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		 Tokenizer tokenizer = new Tokenizer();
	     Tagger tagger = new Tagger();
		 
		 for(File path:paths)
         {
	       String path1 = path.toString();
	       path1 = path1.replace(".json", ".txt");
	       String path_Filter = path1.replace("input", "output_filter");
	       String path_Token = path1.replace("input", "output_token");
	       String path_Tag = path1.replace("input", "output_tagger");
	       filter.readContent(path);   // Lọc nội dung trong thẻ "content".
	       filter.parseAndWriteContent();	// Xử lý và lưu kết quả.
	       tokenizer.token(path_Filter, path_Token); // Xác định các từ.
	       tagger.tag(path_Token, path_Tag); // Phân loại các từ
         }
	       changer.change();
	       vectorDicWords = vector.getVectorDicWord();
	       paths = readFolder_Tagger();
	       for(File path:paths){
	    	   creatVectors(path);
	       }
	       ranges = new double[k][n];
			for (int i = 0; i < k; i++) {
				clusters.add(new Cluster());
				clusters.get(i).setCenter(vectors.get(i));
			}
			int times = 0;
			while (true) {
				times++;
				getRangeOfCoordinateList();							// Tính các khoảng cách bài báo so với tâm
				rebuildClusters();									// Xây dựng lại các cụm dựa trên khoảng cách vừa tính
				if (checkChange()) {								// Nếu có thay đổi so với các cụm trước
					for (int i = 0; i < k; i++) {
						clusters.get(i).copy(newClusters.get(i));
						clusters.get(i).reCalculatingCenter(vectorDicWords, vectorDicWords.size());					// Tính toán lại tâm của các cụm
					}
				} else break;
				if (times == 500) break;	// Lặp lại tối đa 500 lần.
			}
			for (int i = 0; i < k; i++) {
				System.out.println(i + ": " + clusters.get(i).getArticles().size());
				clusters.get(i).printOutputFile(i);
			}
		}
	 
	 private static void getRangeOfCoordinateList() {
			int i, j;
			
			for (i = 0; i < k; i++) {
				for (j = 0; j < n; j++) {
					ranges[i][j] = clusters.get(i).getCenter().calculatingRange(vectorDicWords, vectors.get(j));
				}
			}
		}
	 
	 private static void rebuildClusters() {
			int j, i;
			
			newClusters.clear();
			for (i = 0; i < k; i++)
				newClusters.add(new Cluster());
			
			for (j = 0; j < n; j++) {
				i = getMinRange(j);
				newClusters.get(i).put(vectors.get(j));
			}
		}
	 
	 private static int getMinRange(int j) {
			int i, min;
			double minRange;
			
			minRange = ranges[0][j];
			min = 0;
			for (i = 0; i < k; i++)
				if (ranges[i][j] < minRange) {
					min = i;
					minRange = ranges[i][j];
				}
			return min;
		}
	 
	 private static boolean checkChange() {
			int i;
			for (i = 0; i < k; i++)
				if (!clusters.get(i).compare(newClusters.get(i)))
					return true;
			return false;
		}
	 
	 private static void creatVectors(File path){		 
		 Vector vector = new Vector();
		 String pathString = path.toString();
		 vector.setName(pathString);
		 vector.createVector(vectorDicWords, pathString);
		 vectors.add(vector);
	 }
	 
	 private static File[] readInputFolder() {
		 File f = null;
		 File[] paths = null;
		 try{      
	         // create new file
	         f = new File("input");
	         
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
	                  if(str.equals(".json"))
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
	 
	 private static File[] readFolder_Tagger() {
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
