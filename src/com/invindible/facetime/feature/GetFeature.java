package com.invindible.facetime.feature;

import java.util.ArrayList;
import Jama.Matrix;

public class GetFeature {
	
	ArrayList<int[]> arr = new ArrayList<int[]>();
	int[] v1 = new int[100];
	
	/**
	 * 计算平均人脸图像
	 * 形参arr中存放的数据为N副人脸图像（一维）
	 */
	public int[] CalAveVector(ArrayList<int[]> arr) {
		int n = arr.size();//n指向量的个数，X1,X2,X3...Xn;
		int length = arr.get(0).length;//length指向量的维数，X={1,2,3,...,length}
		int[] vAve = new int[length];//初始化平均向量(的维数)
		
		//构建平均向量的值
		for(int i=0; i<n; i++)
		{
			int[] temp = arr.get(i);
			for(int j=0; j<length; j++)
			{
				vAve[j] += temp[j];
			}
		}
		for(int j=0; j<length; j++)
		{
			vAve[j] = vAve[j]/n;
		}
		
		return vAve;		
	}
	
	
	/**
	 * 计算每张图片的均差
	 * 形参arr中存放的数据为N副人脸图像（一维），vAve为平均人脸图像的向量
	 */
	public ArrayList<int[]> CalAveDeviation(ArrayList<int[]> arr, int[] vAve) {
		int length = vAve.length;//获取向量的维数
		int n = arr.size();//获取向量的个数
		ArrayList<int[]> newArr = new ArrayList<int[]>();
		
		//计算每张图片的均差
		for(int i=0; i<n; i++)
		{
			int[] aveDev = new int[length];//图像的均差
			int[] temp = arr.get(i);
			for(int j=0; j<length; j++)
			{
				aveDev[j] = temp[j] - vAve[j];
			}
			newArr.add(aveDev);
		}
		
		return newArr;//newArr为每幅图像的均差，同时也可以表示成一个矩阵。维数为：n*length;
	}
	
	/**
	 * 计算协方差矩阵
	 * 	传入形参为 <方法：计算每张图片的均差> 的返回值
	 */
	public ArrayList<int[]> CalCovarianceMatrix(ArrayList<int[]> arr) {
		int length = arr.get(0).length;//获取向量的维数
		int n = arr.size();//获取向量的个数 
		
		ArrayList<int[]> covArr = new ArrayList<int[]>();//初始化协方差矩阵
		
		//构造协方差矩阵
		for(int i=0; i<n; i++)
		{
			int[] cov = new int[length];
			int[] temp1 = arr.get(i);//原矩阵第i行
			for(int j=0; j<length; j++)
			{
				//int[] temp1 = arr.get(i);//原矩阵第i行
				int[] temp2 = arr.get(j);//转置矩阵第j列，即原矩阵第j行
				int result;
				for(int k=0; k<length; k++)
				{
					result = temp1[k] * temp2[k];
					cov[j] += result;
				}
				cov[j] = cov[j] /n;
			}
			covArr.add(cov);
		}
		
		return covArr;
		
	}
	
	/**
	 * 通过SVD定理，计算矩阵的特征值和对应的特征向量
	 */
	

	
	public static void main(String[] args)
	{
		 double [][] array = { {4,6,0},{-3,-5,0},{-3,-6,1}};
		 //定义一个矩阵     
		 Matrix A = new Matrix(array);
		 A.eig().getD().print(4, 2);//求特征值
		 System.out.println("__");
		 A.eig().getV().print(4,2);//求特征向量
		 System.out.println("__");
		 Matrix sv = new Matrix( A.svd().getSingularValues(),1);
		 sv.print(4, 2);
		 
		 double[] ar = new double[3];
		 ar = sv.getRowPackedCopy();
		 System.out.println(ar[0] + " " + ar[1] + " " + ar[2]);
		 System.out.printf("%4.2f " ,ar[0] ); //格式化输出
		 
//		 Matrix B = A.transpose();
//		 B.print(4, 2);
//		 System.out.println();
//		 A.print(4, 2);
		 //A.eig().get
		 //A.svd();
	}
	

}
