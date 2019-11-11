package isolation_forest;


import java.util.ArrayList;
import java.util.Random;





public class iForest {
	private int treeNum;
	private int subsampleSize;
	private double[][] data;
	private ArrayList<iTree> treeList;
	
	public iForest()
	{
		treeList = new ArrayList<>();
	}
	public static iForest createForest(double[][] data, int treeNum, int subsampleSize)
	{
		iForest forest = new iForest();
		int heightLimit = (int)Math.ceil(Math.log((double)subsampleSize)/Math.log(2.0));
		Random random = new Random(System.currentTimeMillis());
		int attrNum = data[0].length;
		int dataNum = data.length;
		for(int i=0;i<treeNum;i++)
		{
			double[][] subData = new double[subsampleSize][attrNum];
			for (int j=0;j<subsampleSize;j++)
			{
				int index = random.nextInt(dataNum);
				subData[j] = data[index];
			}
			iTree tree = iTree.createTree(subData, 0, heightLimit);
			forest.treeList.add(tree);
		}
		forest.treeNum = treeNum;
		forest.subsampleSize = subsampleSize;
		forest.data = data;
		return forest;
	}
	public double[] score()
	{
		ArrayList<iTree> tList = this.treeList;
		double[] score = new double[this.data.length];
		double accPathLength = 0;
		double avgPathLength = 0;
		for(int i=0;i<this.data.length;i++)
		{
			accPathLength = 0;
			for(iTree t:tList)
			{
				accPathLength+=iTree.pathLength(this.data[i], t, 0);
			}
			avgPathLength = accPathLength / tList.size();
			score[i] = Math.pow(2, (-avgPathLength/iTree.c(this.subsampleSize)));
		}
		return score;
	}
	public int[] label(int iter)
	{
		double[] score = this.score();
		double c1 = score[0];
		double c0 = score[0];
		int[] label = new int[score.length];
		for(double s:score)
		{
			if (s>c1) c1 = s;
			else if(s<c0) c0 = s;
		}
		double d0 = 0;
		double d1 = 0;
		int count0 = 0;
		int count1 = 0;
		double tc0 = 0;
		double tc1 = 0;
		for(int i=0;i<iter;i++)
		{
			count0 = 0;
			count1 = 0;
			for(int j=0;j<score.length;j++)
			{
				d0 = Math.abs(score[j]-c0);
				d1 = Math.abs(score[j]-c1);
				if(d0<d1) {label[j] = 0; count0++;}
				else {label[j] = 1; count1++;}
			}
			tc0 = c0;
			tc1 = c1;
			for (int j=0;j<score.length;j++)
			{
				if(label[j]==1) {
					c1 += score[j];
				}
				else {
					c0 += score[j];
				}
			}
			c1 /= count1;
			c0 /= count0;
			if(Math.abs(c1-tc1)<=1e-6 && Math.abs(c0-tc0)<=1e-6) break;
		}
		return label;
	}

	
}
