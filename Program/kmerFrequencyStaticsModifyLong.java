//package ClusterReads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class kmerFrequencyStaticsModifyLong {
	
	public static int getDSKLine(String DSKPath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String encoding = "utf-8";
            File file = new File(DSKPath);
            if (file.isFile() && file.exists()) 
            { 
                     InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                     BufferedReader bufferedReader = new BufferedReader(read);
                     while((bufferedReader.readLine())!= null)
                     { 
    		          line++;
                     } 
                     bufferedReader.close();
           }
           return line;
    }
	
	public static int readDSKFile(String DSKPath, String[] KmerSetArray) throws IOException 
	{
		int KmerCount=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  File file = new File(DSKPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) 
				  {
					   String [] SplitLine = readtemp.split("\t|\\s+|\n");
                       KmerSetArray[KmerCount++]=SplitLine[1];
				  }
				  bufferedReader.close();
			  } 
			  else 
			  {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return KmerCount;
	}
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//String DSKPath="/home/liaoxingyu/FinalRealDataTest/S_aureus/dsk/";
		//String KmerStaticsFile="/home/liaoxingyu/FinalRealDataTest/S_aureus/KmerFreStatics/";
		String DSKPath=args[0];
		String KmerStaticsFile=args[1];
		String encoding="utf-8";
		String Context="";
		int SizeOfDSK=getDSKLine(DSKPath+"short2.txt");
		int ratio=100;
		int SumCountFre=0;
		double SumRate=0.0;
		String KmerSetArray[]=new String[SizeOfDSK];
		int KmerSetArraySize=readDSKFile(DSKPath+"short2.txt",KmerSetArray);
		System.out.println("The size of KmerHashSetArray is:"+KmerSetArraySize);
		for(int i=0;i<KmerSetArraySize;i++)
		{
			if(KmerSetArray[i].charAt(0)!='#')
			{
				int FreCount=0;
				double Rate=0; 
			    for (int k=i+1; k <KmerSetArraySize; k++) 
			    {
					if(KmerSetArray[k].charAt(0)!='#'&& KmerSetArray[i].equals(KmerSetArray[k]))
					{
						FreCount++;
						KmerSetArray[k]="#"+KmerSetArray[k];
					}
		        }
				Rate=ratio*((double)(FreCount+1)/(KmerSetArraySize));
				KmerSetArray[i]=KmerSetArray[i]+"\t\t"+(FreCount+1)+"\t\t"+Rate;
				if(i%1000==0)
				{
				    System.out.println("The line"+"\t"+i+"\t"+"has been processed!");
				}
				SumRate+=Rate;
			}
	    }
		String exchange="";
		for(int r=0;r<KmerSetArraySize;r++)
		{
			if(KmerSetArray[r].charAt(0)!='#')
			{
				for(int w=r+1;w<KmerSetArraySize;w++)
			    {
				    if(KmerSetArray[w].charAt(0)!='#')
			        {
						String [] linesplit1 = KmerSetArray[r].split("\t|\\s+");
                        String [] linesplit2 = KmerSetArray[w].split("\t|\\s+");
						int IndexCode1=Integer.parseInt(linesplit1[0]);
						int IndexCode2=Integer.parseInt(linesplit2[0]);
						if(IndexCode1>IndexCode2)
						{
							exchange=KmerSetArray[r];
							KmerSetArray[r]=KmerSetArray[w];
							KmerSetArray[w]=exchange;
						}
					}
			    }
			}
		}
		for(int t=0;t<KmerSetArraySize;t++)
		{
			if(KmerSetArray[t].charAt(0)!='#')
			{
				Context+=KmerSetArray[t]+"\n";
			}	
		}
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KmerStaticsFile+"KmerFreStaticsFile.short2.txt"),encoding)); 
		out.write(Context);
		out.close();
		System.out.println("SumRate:"+SumRate+"%");
	}	
}



