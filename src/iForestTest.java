package isolation_forest;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.io.File;
public class iForestTest{

	public static void main(String[] args)
	{
		int attrNum = 0;			
		int line_count = 1;
		double[][] data;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("normal_data.csv"));
			String line = null;
			line = reader.readLine();
			String[] item = line.split(",");
			attrNum = item.length;
			while((line=reader.readLine())!=null)
			{
				line_count++;
				
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		data = new double[line_count][attrNum];
		try {
			BufferedReader reader = new BufferedReader(new FileReader("normal_data.csv"));
			String line = null;
			int i = 0;
			while((line=reader.readLine())!=null)
			{
				String item[] = line.split(",");
				for(int j=0;j<attrNum;j++)
				{
					data[i][j] = Double.parseDouble(item[j]);
				}
				i++;
			}
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//double[][] data = {{1.1,1.2},{1.11,1.21},{1.2,1.3},{2,5},{3,8},{1.6,1.9},{9.8,0.5}};
		//iTree tree = iTree.createTree(data,100,256);
		//double[] x = {3,8};
		//double test = iTree.pathLength(x,tree,0);
		//System.out.println(test);
		iForest forest = iForest.createForest(data, 100, 256);
		double[] score = forest.score();
		//for(double s: score)
			//System.out.println(s);
		int[] label = forest.label(50);
		//for(int l:label)
		//	System.out.println(l);
		System.out.println(data.length);
		System.out.println(score.length);
		try {
			File csv = new File("label_data.csv");
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true));
			for(int i=0;i<data.length;i++)
			{
				bw.write(data[i][0]+","+data[i][1]+","+label[i]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

  
  