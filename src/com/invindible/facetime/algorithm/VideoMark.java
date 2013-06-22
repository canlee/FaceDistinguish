package com.invindible.facetime.algorithm;

public class VideoMark {
	public static int mark(double[][] test,double[][] model,double[] testMean,double[][] modelMean,double[] allMean){
		double value=L2Form.value(modelMean,allMean);		//l2 domain 
		double l1value=L1Form.l1value(modelMean,allMean);   //l1 domain			
		double[] madis=MixedMahalnobisDistance.calMixedMahalnobisDistance(testMean,modelMean);	 //ma distance	
		double[] mindis=MixedMahalnobisDistance.calMinDistance(testMean, modelMean);  //min distance
		int matmp=Identify.mark(Double.MAX_VALUE,madis);	//ma domain
		int mintmp=Identify.mark(Double.MAX_VALUE,mindis);  //min domain
		
		double[] facedis;  //l2 distance
		double[] l1dis;   //l1 distance
		int[] record=new int[3];
		for(int i=0;i<=test.length;i++){
			boolean l2,l1;
			int[] identify=new int [8];
			int error1=0;
			//
		}
	}
	
	private static int second(int min,double[] dis){
		double tmp=Double.MAX_VALUE;
		//System.out.println(tmp);
		int second=0;
		for(int i=0;i<dis.length;i++){
			//System.out.println(dis[i]);
			if(tmp>dis[i]&&i!=min)
				{
					tmp=dis[i];
					second=i;
				}
		}
		return second;
	}
}
