//package shellproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FilterIncludingRelationship_Modify_603 {

    public static int getLine(String ContigSetPath) throws IOException //Get the number of line of ReadSet.
    {
         int line=0; 
         String encoding = "utf-8";
         String readtemp="";
         File file = new File(ContigSetPath);
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
    
	public static int readTxtFile(String ContigSetPath, String[] ContigSetArray,int length) 
	{
		int count = 0;
		try {
			  String readtemp ="";
			  String encoding = "utf-8";
			  File file = new File(ContigSetPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) {
					  if(readtemp.charAt(0)!='>' && readtemp.length()>length){
						  ContigSetArray[count++]=readtemp;
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
	
	public static int RSHash(String str)
    {
        int hash = 0; 
        for(int i=0;i<str.length();i++)
        {
            hash = str.charAt(i)+ (hash << 6) + (hash << 16) - hash;
        }
        return (hash & 0x7FFFFFFF);
    }
		
	public static int GetRepeatKmerLine(String RepeatKmerPath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String encoding = "utf-8";
            File file = new File(RepeatKmerPath);
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
	
	public static int readRepeatKmerFile(String RepeatKmerPath, String[] RepeatKmerHashTable,int SizeOfHash) throws IOException 
	{
		int countDSK=0;
		String encoding = "utf-8";
		try {
			  String readtemp ="";
			  File file = new File(RepeatKmerPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) 
				  {
                        String [] linesplit = readtemp.split("\t\t|\\s+|\n");
						int hashCode=RSHash(linesplit[0]);
						int i=1; 
						hashCode %= SizeOfHash;
				        while(RepeatKmerHashTable[hashCode]!= null)
					    {    
							 hashCode = hashCode + i*i;
                             hashCode %= SizeOfHash; 
                             i++;							 
                        }  
				        RepeatKmerHashTable[hashCode]=readtemp+"\t"; 
                        countDSK++;
						if(countDSK%10000000==0)
						{
							System.out.println((double)countDSK/(SizeOfHash-100000)*100+"%");
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
		return countDSK;
	}

    public static int getHashUnit(String KmerStr,String HashTable[],int SizeOfDSK)
    {
		int i=1; 
    	int hashcode=RSHash(KmerStr);
    	hashcode%=SizeOfDSK;
		int KmerHashCode=hashcode;
		String NewHashCode="";
		if(HashTable[hashcode]!=null)
		{
			String [] SplitHashUnit = HashTable[hashcode].split("\t|\\s+");
			if(KmerHashCode==RSHash(SplitHashUnit[0])%SizeOfDSK)
			{
				if(SplitHashUnit[0].equals(KmerStr))
    	        {
    		       return hashcode;
    	        }
			}
    	    else
    	    {
			    while((NewHashCode=HashTable[(hashcode+i*i)%SizeOfDSK])!=null)
			    { 
			        String [] HashUnitSplit = NewHashCode.split("\t|\\s+");
					if(KmerHashCode==RSHash(HashUnitSplit[0])%SizeOfDSK)
					{
						if(HashUnitSplit[0].equals(KmerStr))
				        {
					       return (hashcode+i*i)%SizeOfDSK;
                        }
					}
                    i++;
			    }
    	    }
		}
		return -1;
    }
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub.
	    String WritePath=args[0];
		String ContigAfterPath=args[1];
		int SizeOfAfter=getLine(ContigAfterPath);
		String ContigSetAfter[]= new String[SizeOfAfter];
		int RealSizeOfAfter=readTxtFile(ContigAfterPath, ContigSetAfter,200); 
		System.out.println("The size of After is:"+RealSizeOfAfter);
		//Merge.
		String ContigMergePath=args[2];
		int SizeOfMerge=getLine(ContigMergePath);
		String ContigSetMerge[]= new String[SizeOfMerge];
		int RealSizeOfMerge=readTxtFile(ContigMergePath, ContigSetMerge,200); 
		System.out.println("The size of Merge is:"+RealSizeOfMerge);
	    //LowFre HashTable
	    String ErrorFreKmerFile=args[3];
	    int SizeOfLowFreDSK=GetRepeatKmerLine(ErrorFreKmerFile)+100000;
	    System.out.println("The size of LowFre Kmer File is :"+SizeOfLowFreDSK);
	    String LowFreHashTable[]=new String[SizeOfLowFreDSK];
	    int sizeofLowfrekmerhashtable=readRepeatKmerFile(ErrorFreKmerFile,LowFreHashTable,SizeOfLowFreDSK);
	    System.out.println("The size of Low Frequency KmerHashSetArray is:"+sizeofLowfrekmerhashtable);
		//over.
	    String exchange1="";
	    for(int t=0;t<RealSizeOfAfter;t++)
	    {
		   for(int w=t+1;w<RealSizeOfAfter;w++)
		   {
			    if(ContigSetAfter[t].length()<ContigSetAfter[w].length())
			    {
				     exchange1=ContigSetAfter[t];
				     ContigSetAfter[t]=ContigSetAfter[w];
				     ContigSetAfter[w]=exchange1;
			    }
		   }
	    }
	    System.out.println("File After sort process completed!");
		//over.
	    String exchange2="";
	    for(int t=0;t<RealSizeOfMerge;t++)
	    {
		   for(int w=t+1;w<RealSizeOfMerge;w++)
		   {
			    if(ContigSetMerge[t].length()<ContigSetMerge[w].length())
			    {
				     exchange2=ContigSetMerge[t];
				     ContigSetMerge[t]=ContigSetMerge[w];
				     ContigSetMerge[w]=exchange2;
			    }
		   }
	    }
	    System.out.println("File Merge sort process completed!");
	    //Delete Including.
	    for(int e=0;e<RealSizeOfAfter;e++)
	    {
			for(int f=0;f<RealSizeOfMerge;f++)
		    {
			    if((ContigSetMerge[f].charAt(0)!='#')&&(ContigSetAfter[e].length()>=ContigSetMerge[f].length())&&(ContigSetAfter[e].contains(ContigSetMerge[f])||ContigSetAfter[e].contains(reverse(ContigSetMerge[f]))))
			    {
			        ContigSetMerge[f]="#"+ContigSetMerge[f];
			    }
		    }
			if(e%1000==0)
			{
			    System.out.println("Process rate:"+(double)e/RealSizeOfMerge*100+"%");
			}				
	    }
		//Write After.
		int LineCount=0;
		for(int g=0;g<RealSizeOfAfter;g++)
		{
			int LowFreNum=0;
			//for (int k=0; k <= ContigSetAfter[g].length()-11; k++) 
			//{
				/*if(getHashUnit(ContigSetAfter[g].substring(k,k+11),LowFreHashTable,SizeOfLowFreDSK)!=-1)
				{	 
					LowFreNum++;
				}
				if(LowFreNum<=(ContigSetAfter[g].length()-11+1)/3)
				{*/
			        FileWriter writer= new FileWriter(WritePath+"contigs.fa",true);
                    writer.write(">"+(LineCount++)+"\n"+ContigSetAfter[g]+"\n");
                    writer.close();
				//}
			//}
		}
		//Write Merge.
		for(int e=0;e<RealSizeOfMerge;e++)
		{
			 if(ContigSetMerge[e].charAt(0)!='#')
			 { 	
				//int CountOfLow=0;
			   	/*for (int k=0; k <= ContigSetMerge[e].length()-11; k++) 
			    {
			         if(getHashUnit(ContigSetMerge[e].substring(k,k+11),LowFreHashTable,SizeOfLowFreDSK)!=-1)
				     {	 
					     CountOfLow++;
				     }
			    }
				if(CountOfLow<(ContigSetMerge[e].length()-11+1)/3)
				{*/
			        FileWriter writer= new FileWriter(WritePath+"contigs.fa",true);
                    writer.write(">"+(LineCount++)+" Add "+"\n"+ContigSetMerge[e]+"\n");
                    writer.close();
				//}
			 }
		}
	    System.out.println("File filter process completed!");
	    System.out.println("File  write process begin!");
	}
}
