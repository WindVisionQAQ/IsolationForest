package isolation_forest;



public class iTree {
	private int attribute;
	private double splitValue;
	private int currentHeight;
	private int nodesNum;
	private iTree leftTree;
	private iTree rightTree;
	
	public iTree()
	{
		attribute = 0;
		currentHeight = 0;
		leftTree = null;
		rightTree = null;
	}
	
	public static iTree createTree(double[][] data, int currentHeight, int heightLimit)
	{
		iTree tree = new iTree();
		if(currentHeight>=heightLimit||data.length<=1) {
			tree.currentHeight = currentHeight;
			tree.nodesNum = data.length;
			return tree;
		}
		int flag = 0;
		for(int i=0;i<data.length-1;i++)
		{
			for (int j=i+1;j<data.length;j++)
			{
				if (data[i] != data[j])
				{
					flag = 1;
					break;
				}
			}
			if (flag == 1) break;
		}
		if (flag == 0) {
			tree.currentHeight = currentHeight;
			tree.nodesNum = data.length;
			return tree;
		}
		
		int attrNum = data[0].length;
		// randomly-picked attribute
		int attr = (int)Math.random()*attrNum;
		tree.attribute = attr;
		// search max and min value of the random attribute
		double max = data[0][attr];
		double min = data[0][attr];
		for (int i=1;i<data.length;i++)
		{
			if (data[i][attr]>max) max = data[i][attr];
			if (data[i][attr]<min) min = data[i][attr];
		}
		// randomly-picked split value
		double sv = min + (max-min)*Math.random();		
		tree.splitValue = sv;
		int leftNodeNum = 0;
		int rightNodeNum = 0;
		for(int i=0;i<data.length;i++)
		{
			if(data[i][attr]<sv) leftNodeNum++;
			else rightNodeNum++;
		}
		double[][] leftData = new double[leftNodeNum][attrNum];
		double[][] rightData = new double[rightNodeNum][attrNum];
		int leftIndex = 0;
		int rightIndex = 0;
		for(int i=0;i<data.length;i++)
		{
			if(data[i][attr]<sv) leftData[leftIndex++] = data[i];
			else rightData[rightIndex++] = data[i];
		}
		tree.currentHeight = currentHeight;
		tree.nodesNum = data.length;
		tree.leftTree = createTree(leftData, currentHeight+1, heightLimit);
		tree.rightTree = createTree(rightData, currentHeight+1, heightLimit);
		return tree;
	}
	
	public static double pathLength(double[] x, iTree tree, int currentPathLength)
	{
		if(tree.leftTree==null || tree.rightTree==null)
			return currentPathLength+c((double)tree.nodesNum);
		double sv = tree.splitValue;
		int index = tree.attribute;
		if(x[index]<sv) return pathLength(x, tree.leftTree, currentPathLength+1);
		else return pathLength(x, tree.rightTree, currentPathLength+1);
	}
	public static double c(double n)
	{
		if(n<=1) return 0;
		else return 2*(Math.log(n-1)+0.5772156649)-2*(n-1)/n;	
	}
}
