//package MRAssembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilterKmerStaticsA {
	
	public static int getLength(String ReadSetPath) throws IOException //Get the number of line of ReadSet.
    {
            int length=0; 
            String encoding = "utf-8";
            String readtemp="";
            File file = new File(ReadSetPath);
            if (file.isFile() && file.exists()) 
            { 
                 InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                 BufferedReader bufferedReader = new BufferedReader(read);
                 while ((readtemp=bufferedReader.readLine()) != null)
                 { 
       		    	 if(readtemp.charAt(0)!='>')
    		    	 {
    		    		 length+=readtemp.length();
    		    	 } 
                 } 
                 bufferedReader.close();
           }
           return length;
    }
	
	public static int GetFileLine(String FilePath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String readtemp="";
            String encoding = "utf-8";
            File file = new File(FilePath);
            if (file.isFile() && file.exists()) 
            { 
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                BufferedReader bufferedReader = new BufferedReader(read);
                while((readtemp=bufferedReader.readLine())!= null)
                {
                	if(readtemp.charAt(0)!='>')
                	{
                		line++;
                	}
                } 
                bufferedReader.close();
           }
           return line;
    }
	
	public static int GetList(String FilePath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String encoding = "utf-8";
            File file = new File(FilePath);
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
	
	public static int GetFileLenth(String FilePath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String readtemp="";
            String encoding = "utf-8";
            File file = new File(FilePath);
            if (file.isFile() && file.exists()) 
            { 
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
                BufferedReader bufferedReader = new BufferedReader(read);
                while((readtemp=bufferedReader.readLine())!= null)
                {
                	if(readtemp.charAt(0)!='>')
                	{
                		line+=readtemp.length();
                	}
                } 
                bufferedReader.close();
           }
           return line;
    }
	
	public static int readKmerStatisticFile(String KmerStatisticPath, String[] KmerStatisticArray) throws IOException 
	{
		int KmerCount=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  File file = new File(KmerStatisticPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) 
				  {
					   String [] SplitLine = readtemp.split("\t\t|\\s+|\n");
                       KmerStatisticArray[KmerCount++]=SplitLine[0]+"\t"+SplitLine[1];
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
	
	
	public static double depth(String FilePath , String LibPath , int AverageLength, int kmerlength , String [] KmerFreStatisiticArray ,int RealSizeOfArray) throws IOException
	{
		   int File_Length=GetFileLenth(FilePath);
		   int Size_Lib=GetFileLine(LibPath);
		   double FirstEstimate=(double)Size_Lib*(AverageLength-kmerlength+1)/File_Length;
		   System.out.println("Size_Lib:"+Size_Lib);
                                      System.out.println("AverageLength:"+AverageLength);
                      System.out.println("AverageLength-kmerlength+1:"+(AverageLength-kmerlength+1));
                      System.out.println("First Estimate:"+(double)(Size_Lib*AverageLength)/File_Length);
		   //Depth compute
		   int Index=0;
		   double MaxFre=0;
		   double MaxDep=0;
		   for(int r=0;r<RealSizeOfArray;r++)
		   {
			   	String [] SplitLine1 = KmerFreStatisiticArray[r].split("\t|\\s+");
			    double CurrentDepth1=Double.parseDouble(SplitLine1[0]);
				double CurrentFrequency1=Double.parseDouble(SplitLine1[1]);
				if((CurrentDepth1>=FirstEstimate))
				{
					Index=r;
					MaxDep=CurrentDepth1;
					MaxFre=CurrentFrequency1;
					break;
				}
		   }
		   System.out.println("MaxDep:"+MaxDep);
		   System.out.println("MaxFre:"+MaxFre);
		   for(int k=Index;k<RealSizeOfArray;k++)
		   {
			    String [] SplitLine2 = KmerFreStatisiticArray[k].split("\t|\\s+");
			    double CurrentDepth2=Double.parseDouble(SplitLine2[0]);
			    double CurrentFrequency2=Double.parseDouble(SplitLine2[1]);
      			if(CurrentFrequency2>MaxFre)
				{
					MaxDep=CurrentDepth2;
					MaxFre=CurrentFrequency2;
				}
		   }
		   return (MaxDep*AverageLength)/(AverageLength-kmerlength+1);
	}
	
	public static void main(String[] args) throws IOException {
		//Get Depth Start.
		String FilePath = args[0];
		String LibPath=args[1];
		String KmerFrePath=args[2];
		String AverageLength=args[3];
		String KmerLength=args[4];
		int Ave_len=Integer.parseInt(AverageLength);
		int Kmer_Len=Integer.parseInt(KmerLength);
		//Loading.
		String kmerstatisiticfile=KmerFrePath+"/KmerFreStaticsFile.short1.txt";
		int SizeOfkmerStatisticFile=GetList(kmerstatisiticfile);
		String KmerStatisticArray[]=new String[SizeOfkmerStatisticFile];
		int RealSizeOfKmerStatisticArray = readKmerStatisticFile(kmerstatisiticfile, KmerStatisticArray);
		System.out.println("The real size of kmer frequency statistic file is:"+RealSizeOfKmerStatisticArray);
		//over.
		double Depth=depth(FilePath,LibPath,Ave_len,Kmer_Len,KmerStatisticArray,RealSizeOfKmerStatisticArray); 
System.out.println("The final sequencing Depth estimate:"+Depth);
		double ErrorFreRate=Depth/30;
		double LowFreRate=Depth/10;
		double HighFreRate=2*Depth;
		System.out.println("The threshold of LowFre Kmer is:"+LowFreRate);
		System.out.println("The threshold of HighFre Kmer is:"+HighFreRate);
        String encoding="utf-8";
        String readtemp = "";
		String AfterFilterNormalStr="";
		String AfterFilterRepeatStr="";
		String FilterLowFreStr="";	
        String ErrorLowFreStr="";		
        File readfile = new File(KmerFrePath+"KmerFreStaticsFile.short1.txt");
        if (readfile.isFile() && readfile.exists()) 
		{
               InputStreamReader read = new InputStreamReader(new FileInputStream(readfile), encoding); 
               BufferedReader bufferedReader = new BufferedReader(read);
               while ((readtemp = bufferedReader.readLine()) != null) 
			   {  		  
					String [] linesplit = readtemp.split("\t\t|\\s+");
                    double CurrentRate=Double.parseDouble(linesplit[0]);
					if(CurrentRate<=LowFreRate)
					{
						FilterLowFreStr+=readtemp+"\n";
					}
					else if(CurrentRate>LowFreRate && CurrentRate<HighFreRate)
                    {
                        AfterFilterNormalStr+=readtemp+"\n";
                    }
                    else if(CurrentRate>=HighFreRate)
					{
						AfterFilterRepeatStr+=readtemp+"\n";
					}
               }
               bufferedReader.close();
			   //Filter Repeat.
	           BufferedWriter FilterRepeat = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KmerFrePath+"FilterRepeat.short1.txt"),encoding)); 
               FilterRepeat.write(AfterFilterRepeatStr);
               FilterRepeat.close();
               //Filter Normal
               BufferedWriter FilterNormal = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KmerFrePath+"FilterNormal.short1.txt"),encoding)); 
               FilterNormal.write(AfterFilterNormalStr);
               FilterNormal.close();
               //Filter LowFre.
	           BufferedWriter FilterLowFre = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KmerFrePath+"FilterLowFre.short1.txt"),encoding)); 
               FilterLowFre.write(FilterLowFreStr);
               FilterLowFre.close();
        }
        else
		{
			   System.out.println("File is not exist!");
		}			
	}
}
