//package RCAssembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MarkLowAndHighFreKmerLong {
	
	public static int getLine(String KmerPath) throws IOException 
	{
		int line = 0;
		String encoding = "utf-8";
		File file = new File(KmerPath);
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((bufferedReader.readLine()) != null) {
				line++;
			}
			bufferedReader.close();
		}
		return line;
	}
	
	public static int LoadingData(String DataPath, String[] SetArray) throws IOException 
	{
		int KmerCount=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  File file = new File(DataPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) 
				  {
                       SetArray[KmerCount++]=readtemp;
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
        String encoding="utf-8";
		String readtemp ="";
        String NormalStr="";
        String LowFreStr="";
		String HighFreStr="";
        //String KmerFrePath="/home/liaoxingyu/FinalRealDataTest/S_aureus/dsk/";
        //String KmerFilterPath="/home/liaoxingyu/FinalRealDataTest/S_aureus/KmerFreStatics/";
        String KmerFrePath=args[0];
		String KmerFilterPath=args[1];
		//Store Error Frequency K-mer File
        /*int KmerErrorArraySize=getLine(KmerFilterPath+"FilterErrorFre.short2.txt");
        String [] KmerErrorArray=new String[KmerErrorArraySize];
        int CountKmerErrorArray=LoadingData(KmerFilterPath+"FilterErrorFre.short2.txt",KmerErrorArray);		
		String [] LineSplitError = KmerErrorArray[CountKmerErrorArray-1].split("\t\t|\\s+|\n");
        int ErrorFreMax=Integer.parseInt(LineSplitError[0]);*/
		//Store Low Frequency K-mer File
        int KmerLowArraySize=getLine(KmerFilterPath+"FilterLowFre.short2.txt");
        String [] KmerLowArray=new String[KmerLowArraySize];
        int CountKmerLowArray=LoadingData(KmerFilterPath+"FilterLowFre.short2.txt",KmerLowArray);		
		String [] LineSplitMax = KmerLowArray[CountKmerLowArray-1].split("\t\t|\\s+|\n");
        int LowFreMax=Integer.parseInt(LineSplitMax[0]);
		//Store high Frequency K-mer File
        int KmerRepeatArraySize=getLine(KmerFilterPath+"FilterRepeat.short2.txt");
        String [] KmerRepeatArray=new String[KmerRepeatArraySize];
        int CountKmerRepeatArray=LoadingData(KmerFilterPath+"FilterRepeat.short2.txt",KmerRepeatArray);		
		String [] LineSplitMin = KmerRepeatArray[0].split("\t\t|\\s+|\n");
        int HighFreMin=Integer.parseInt(LineSplitMin[0]);
	    //Store SDK File
		int Count=0;
		int dskSize=getLine(KmerFrePath+"short2.txt");
	    File file = new File(KmerFrePath+"short2.txt");
	    if (file.isFile() && file.exists()) 
		{
			 InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
			 BufferedReader bufferedReader = new BufferedReader(read);
			 while ((readtemp = bufferedReader.readLine()) != null) 
			 {
				   String [] LineSplit = readtemp.split("\t|\\s+|\n");
                   int LowFreCompare=Integer.parseInt(LineSplit[1]);
                   /*if(LowFreCompare<=ErrorFreMax)  
				   {
					   	FileWriter writer1= new FileWriter(KmerFilterPath+"ErrorFreKmer.short2.txt",true);
                        writer1.write(readtemp+"\n");
                        writer1.close();
				   }*/
				   if(LowFreCompare<=LowFreMax)  
				   {
					   	FileWriter writer1= new FileWriter(KmerFilterPath+"LowFreKmer.short2.txt",true);
                        writer1.write(readtemp+"\n");
                        writer1.close();
				   }
                   if(LowFreCompare>=HighFreMin)
				   {
					   	FileWriter writer2= new FileWriter(KmerFilterPath+"RepeatKmer.short2.txt",true);
                        writer2.write(readtemp+"\n");
                        writer2.close();
				   }
                   Count++;
                   if(Count%100000==0)
				   {
					   System.out.println("Process rate:"+100*(double)Count/dskSize+"%");
				   }					   
			 }
			 bufferedReader.close();
		} 
		else 
		{
		     System.out.println("File is not exist!");
	    }
	}
}
