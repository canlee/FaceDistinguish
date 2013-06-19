package com.invindible.facetime.algorithm;

public class Mark {
	public static boolean domark(double[][] test,double[][] model,double[] testMean,double[][] modelMean,double[] allMean){
		double value=L2Form.value(modelMean,allMean);		//l2 domain 
		double l1value=L1Form.l1value(modelMean,allMean);   //l1 domain
		
		double[] madis=MixedMahalnobisDistance.calMixedMahalnobisDistance(testMean,modelMean);	 //ma distance	
		double[] mindis=MixedMahalnobisDistance.calMinDistance(testMean, modelMean);  //min distance
		int matmp=Identify.mark(Double.MAX_VALUE,madis);	//ma domain
		int mintmp=Identify.mark(Double.MAX_VALUE,mindis);  //min domain
		
		double[] facedis;  //l2 distance
		double[] l1dis;   //l1 distance
		boolean[] flag=new boolean[3];
		for(int i=0;i<=test.length;i++){
			boolean l2,l1;
			int[] identify=new int [6];
			int error1=0,error2=0; //error2--l2  error1--l1
			if(i==test.length){
				l2=L2Form.inL2(testMean, allMean, value);
				l1=L1Form.inL1(testMean, allMean, l1value);	//whether in or not
				if(!l1||!l2)
				{
					flag[i]=false;
					continue;
				}
				facedis=L2Form.L2Form(model, testMean);  //calculate l2 distance
				l1dis=L1Form.L1Form(model, testMean);   //calculate l1 distance
			}
			else
			{
				l2=L2Form.inL2(test[i], allMean, value);
				l1=L1Form.inL1(test[i], allMean, l1value);
				if(!l1||!l2)
				{
					flag[i]=false;
					continue;
				}
				facedis=L2Form.L2Form(model, test[i]);  //calculate l2 distance
				l1dis=L1Form.L1Form(model, test[i]);   //calculate l1 distance
			}			
			int tmp=Identify.mark(value, facedis);  //l2 find out the nearest
			int l1tmp=Identify.mark(l1value, l1dis);  //l1 find out the nearest
			int tmpsecond;
			
			//---------------------------与总体比较
			if(tmp!=-1){
				tmpsecond=second(tmp,facedis);  //l2 find out the second nearest
				identify[0]=tmp;
				if(tmpsecond!=-1){
					if(Math.abs(facedis[tmpsecond]/facedis[tmp])<5)
					{;}
					else if(tmp/5+1!=tmpsecond/5+1)
						{
							error2++;				
						}
					}
			}
			else
			{
				flag[i]=false;
				continue;
			}
			
			
			int tmpl1second;
			if(l1tmp!=-1){	 //l1  find out the second nearest
			tmpl1second=second(l1tmp, l1dis);
			identify[2]=l1tmp;
			if(tmpl1second!=-1){
				if(Math.abs(l1dis[tmpl1second]/l1dis[l1tmp])<5)
				;			
				else if(l1tmp/5+1!=tmpl1second/5+1)
					{
					error1++;					
					}
				}
			}
			else
			{
				flag[i]=false;
				continue;
			}
			
			//---------------------------与总体比较
			
			
			
			//-----------------------与平均比较
			double[] l2facedis;
			double[] l1facedis;      //l1 form
			if(i==test.length){
				l2facedis=L2Form.L2Form(modelMean, testMean);		//l2 form
				l1facedis=L1Form.L1Form(modelMean, testMean); 
			}
			else
				{
					l2facedis=L2Form.L2Form(modelMean, test[i]);
					l1facedis=L1Form.L1Form(modelMean, test[i]);
				}
			tmp=Identify.mark(value, l2facedis);
			l1tmp=Identify.mark(l1value, l1facedis);
			
			 if(tmp!=-1){
					int second=second(tmp,l2facedis);
					identify[4]=tmp;
					if(second!=-1){
					if(l2facedis[tmp]<0.55*l2facedis[second])
					{;}
					else
						error2++;					
				}
			 }
			 else
			 {
					flag[i]=false;
					continue;
			}
			 
			 if(l1tmp!=-1){
					int second=second(tmp,l1facedis);
					identify[5]=l1tmp;
					if(second!=-1){
					if(l1facedis[l1tmp]<0.55*l1facedis[second])
					{;}
					else
						error1++;					
				}
			 }
			 else
			 {
					flag[i]=false; 
					continue;
				}
			 
			 identify[1]=matmp;
			 identify[3]=mintmp;
			 
			//-----------------------与平均比较
			 
			 for(int c=0;c<identify.length;c++){
					for(int d=c+1;d<identify.length;d++){
						if(identify[c]!=identify[d])
						{
							flag[i]=false;
							break;
						}
					}
					if(!flag[i])
						break;
				}
			
		}
		
		if(flag[0]&&flag[1]&&flag[2])
			return true;
		else if((flag[0]&&flag[2])||(flag[1]&&flag[2]))
			return true;
		else 
			return false;
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
