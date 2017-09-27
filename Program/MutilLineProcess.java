//package shellproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MutilLineProcess {
	
	public static int getContigLine(String ContigPath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String encoding = "utf-8";
            File file = new File(ContigPath);
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
	
    public static int getLine(String ReadSetPath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
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
    		    		     line++;
    		    	     } 
                     } 
                     bufferedReader.close();
           }
           return line;
    }
	
    public static int kmerTxtFile(String KmerSetPath, String[] KmerSetArray) // Put the ReadSet into Array.
	{
		int count = 0;
		try {
			  String readtemp ="";
			  String encoding = "utf-8";
			  File file = new File(KmerSetPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine())!= null) 
				  {
					  if(readtemp.charAt(0)!='>')
					  {
					     KmerSetArray[count++]=readtemp;
					  }
				  }
				  bufferedReader.close();
			  } 
			  else {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return count;
	}
	
	public static int readContigFile(String ContigPath, String[] ContigSetArray) throws IOException 
	{
		int KmerCount=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  String ReadTemp="";
			  File file = new File(ContigPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp=bufferedReader.readLine()) != null) 
				  {
					  if(readtemp.charAt(0)=='>')
					  {
						  ContigSetArray[KmerCount++]=readtemp;
					  }
					  else
					  {
						  String Context=readtemp;
						  while(((ReadTemp=bufferedReader.readLine()) != null))
                          {
							  if(ReadTemp.charAt(0)!='>')
							  {
                        		  Context+=ReadTemp;  
							  }
							  else
							  {
								  break;
							  }
                          }
						  ContigSetArray[KmerCount]=Context;
						  ContigSetArray[++KmerCount]=ReadTemp;
						  KmerCount++;
					  }
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
	
    public static String reverse(String s) 
	{
		int length = s.length();
		String reverse = "";
		for (int i = 0; i < length; i++)
		{
			 if(s.charAt(i)=='A')
			 {
		         reverse="T"+reverse;
			 }
			 else if(s.charAt(i)=='T')
			 {
		         reverse="A"+reverse;
			 }
			 else if(s.charAt(i)=='G')
			 {
		         reverse="C"+reverse;
			 }
			 else
			 {
		         reverse="G"+reverse;
			 }
	    }
		return reverse;
	}

	public static void main(String[] args) throws IOException {
	   // TODO Auto-generated method stub
       String FinalContigWritePath=args[0];
	   String ContigAfterPath=args[1];
	   int SizeOfContigAfter=getContigLine(ContigAfterPath);
       String ContigSetAfterArray[]=new String[SizeOfContigAfter+1];
       int RealSizeOfContigSetAfter=readContigFile(ContigAfterPath,ContigSetAfterArray); 
       System.out.println("The real size of ContigSetAfter is:"+RealSizeOfContigSetAfter);
	   //low.
       String ContigMergePath=args[2];
	   int SizeOfContigMerge=getContigLine(ContigMergePath);
       String ContigSetMergeArray[]=new String[SizeOfContigMerge+1];
       int RealSizeOfContigSetMerge=readContigFile(ContigMergePath,ContigSetMergeArray); 
       System.out.println("The real size of ContigSetLoWdepth is:"+RealSizeOfContigSetMerge);
	   //high.
       String ContigHighPath=args[3];
	   int SizeOfContigHigh=getContigLine(ContigHighPath);
       String ContigSetHighArray[]=new String[SizeOfContigHigh+1];
       int RealSizeOfContigSetHigh=readContigFile(ContigHighPath,ContigSetHighArray); 
       System.out.println("The real size of ContigSetHighdepth is:"+RealSizeOfContigSetHigh);
	   //Merge and delete including.
	   int SizeOfAfterMerge=RealSizeOfContigSetAfter/2+RealSizeOfContigSetMerge/2+100;
	   String AfterMergeContigArray[]=new String[SizeOfAfterMerge];
	   int RealSizeOfAfterMerge=0;
	   //Loading After.
	   int LoadingContigAfterCount=0;
	   String LoadingContigAfterArray[]=new String[RealSizeOfContigSetAfter]; 
	   for(int r=0;r<RealSizeOfContigSetAfter-1;r++)
	   {
		   if(ContigSetAfterArray[r].charAt(0)!='>' && ContigSetAfterArray[r].length()>100)
		   {
			   LoadingContigAfterArray[LoadingContigAfterCount++]=ContigSetAfterArray[r];
		   }
	   }
	   System.out.println("File After loading process end!");
	   //Order ContigAfterArray by contig length.
       String exchange="";
	   for(int t=0;t<LoadingContigAfterCount;t++)
	   {
		   for(int w=t+1;w<LoadingContigAfterCount;w++)
		   {
			    if(LoadingContigAfterArray[t].length()<LoadingContigAfterArray[w].length())
			    {
				     exchange=LoadingContigAfterArray[t];
				     LoadingContigAfterArray[t]=LoadingContigAfterArray[w];
				     LoadingContigAfterArray[w]=exchange;
			    }
		   }
	   }
	   System.out.println("File After sort process end!");
	   //Loading Merge.
	   int LoadingContigMergeCount=0;
	   String LoadingContigMergeArray[]=new String[RealSizeOfContigSetMerge]; 
	   for(int r=0;r<RealSizeOfContigSetMerge-1;r++)
	   {
		   if(ContigSetMergeArray[r].charAt(0)!='>')
		   {
			   LoadingContigMergeArray[LoadingContigMergeCount++]=ContigSetMergeArray[r];
		   }
	   }
	   System.out.println("File lowdepth loading process end!");
	   //Loading High.
	   int LoadingContigHighCount=0;
	   String LoadingContigHighArray[]=new String[RealSizeOfContigSetHigh]; 
	   for(int r=0;r<RealSizeOfContigSetHigh-1;r++)
	   {
		   if(ContigSetHighArray[r].charAt(0)!='>')
		   {
			   LoadingContigHighArray[LoadingContigHighCount++]=ContigSetHighArray[r];
		   }
	   }
	   System.out.println("File highdepth loading process end!");
	   //Order ContigMergeArray by contig length.
	   String exchange1="";
	   for(int t=0;t<LoadingContigMergeCount;t++)
	   {
		   for(int w=t+1;w<LoadingContigMergeCount;w++)
		   {
			    if(LoadingContigMergeArray[t].length()<LoadingContigMergeArray[w].length())
			    {
				     exchange1=LoadingContigMergeArray[t];
				     LoadingContigMergeArray[t]=LoadingContigMergeArray[w];
				     LoadingContigMergeArray[w]=exchange1;
			    }
		   }
	   }
	   System.out.println("File lowdepth sort process end!");
	   System.out.println("File filter process start!");
           //Delete Including.
	   for(int f=0;f<LoadingContigAfterCount;f++)
	   {
		   for(int e=0;e<LoadingContigMergeCount;e++)
		   {
			    if(LoadingContigMergeArray[e].charAt(0)!='#'&&(LoadingContigAfterArray[f].length()>=LoadingContigMergeArray[e].length())&&(LoadingContigAfterArray[f].contains(LoadingContigMergeArray[e])||LoadingContigAfterArray[f].contains(reverse(LoadingContigMergeArray[e]))))
			    {
				    LoadingContigMergeArray[e]="#"+LoadingContigMergeArray[e];
			    }
		   }
		   if(f%1000==0)
		   {
			   System.out.println("Process rate:"+(double)f/LoadingContigAfterCount*100+"%");
		   }
	   }
	   //
	   for(int f=0;f<LoadingContigAfterCount;f++)
	   {
		   for(int e=0;e<LoadingContigHighCount;e++)
		   {
			    if(LoadingContigHighArray[e].charAt(0)!='#'&&(LoadingContigAfterArray[f].length()>=LoadingContigHighArray[e].length())&&(LoadingContigAfterArray[f].contains(LoadingContigHighArray[e])||LoadingContigAfterArray[f].contains(reverse(LoadingContigHighArray[e]))))
			    {
				    LoadingContigHighArray[e]="#"+LoadingContigHighArray[e];
			    }
		   }
		   if(f%1000==0)
		   {
			   System.out.println("Process rate:"+(double)f/LoadingContigAfterCount*100+"%");
		   }
	   }
	   
	   System.out.println("File filter process end!");
	   System.out.println("File  write process start!");
       //Write.
	   int LineNum1=0;
       for(int x=0;x<LoadingContigAfterCount;x++)
       {
		   FileWriter writer1= new FileWriter(FinalContigWritePath+"contig.AfterMerge.fa",true);
           writer1.write(">"+(LineNum1++)+":"+LoadingContigAfterArray[x].length()+"\n"+LoadingContigAfterArray[x]+"\n");
           writer1.close();
       } 
	   for(int h=0;h<LoadingContigMergeCount;h++)
	   {
		   if(LoadingContigMergeArray[h].charAt(0)!='#')
		   {
			    FileWriter writer2= new FileWriter(FinalContigWritePath+"contig.AfterMerge.fa",true);
                writer2.write(">"+(LineNum1++)+":"+LoadingContigMergeArray[h].length()+"\n"+LoadingContigMergeArray[h]+"\n");
                writer2.close();
		   }
	   }
	   //
	   for(int h=0;h<LoadingContigHighCount;h++)
	   {
		   if(LoadingContigHighArray[h].charAt(0)!='#')
		   {
			    FileWriter writer2= new FileWriter(FinalContigWritePath+"contig.AfterMerge.fa",true);
                writer2.write(">"+(LineNum1++)+":"+LoadingContigHighArray[h].length()+"\n"+LoadingContigHighArray[h]+"\n");
                writer2.close();
		   }
	   }
	   System.out.println("File write process end!");
	}
}
