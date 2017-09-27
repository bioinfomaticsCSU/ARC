//package Statistics;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.FileWriter;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GenerateReadSetByKmerFreThreadModify_Right {
	
    public static int getLine(String ReadSetPath) throws IOException //Get the number of line of ReadSet.
    {
            int line=0; 
            String encoding = "utf-8";
            String readtemp="";
            File file = new File(ReadSetPath+"short1.right.fasta");
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

	public static int readTxtFile(String ReadSetPath, String[] ReadSetArray) // Put the ReadSet into Array.
	{
		int count = 0;
		try {
			  String readtemp ="";
			  String encoding = "utf-8";
			  File file = new File(ReadSetPath+"short1.right.fasta");
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) {
					  if(readtemp.charAt(0)!='>'){
						  ReadSetArray[count++]=readtemp;
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
	
    public static String DeleSame(String ComStr)
    {
    	String ReturnStr="";
    	String [] linesplit = ComStr.split("\\+");
		ArrayList<String> numList = new ArrayList<String>();
		for (int i=0;i<linesplit.length;i++)
			numList.add(linesplit[i]);
		Set<String> numSet = new HashSet<String>();
		numSet.addAll(numList);
		String Store[]=new String[numSet.size()];
		Store=numSet.toArray(new String[0]);
		ReturnStr=Store[0];
		for(int t=1;t<Store.length;t++)
		{
			ReturnStr+="+"+Store[t];
		}
    	return ReturnStr;
    }
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub		
		String ReadSetPath=args[0];
		String RepeatKmerFile=args[1];
		String LowFreKmerFile=args[2];
		String LowFreReadPartitions=args[3];
		String RepeatReadPartitions=args[4];
		String NormalReadPartitions=args[5];
		String Read_len=args[6];
		String Kmer_len=args[7];
		//
		int ArraySize = getLine(ReadSetPath);
		String[] ReadSetArray = new String[ArraySize];
		int scount = readTxtFile(ReadSetPath,ReadSetArray);	
	    System.out.println("The size of ReadSetArray is:"+scount);
		//Repeat HashTable
		int SizeOfDSK=GetRepeatKmerLine(RepeatKmerFile)+100000;
		System.out.println("The size of Repeat Kmer File is :"+SizeOfDSK);
		String HashTable[]=new String[SizeOfDSK];
		int sizeofhashtable=readRepeatKmerFile(RepeatKmerFile,HashTable,SizeOfDSK);
		System.out.println("The size of Repeat KmerHashSetArray is:"+sizeofhashtable);
		//LowFre HashTable
		int SizeOfLowFreDSK=GetRepeatKmerLine(LowFreKmerFile)+100000;
		System.out.println("The size of LowFre Kmer File is :"+SizeOfLowFreDSK);
		String LowFreHashTable[]=new String[SizeOfLowFreDSK];
		int sizeofLowfrekmerhashtable=readRepeatKmerFile(LowFreKmerFile,LowFreHashTable,SizeOfLowFreDSK);
		System.out.println("The size of Low Frequency KmerHashSetArray is:"+sizeofLowfrekmerhashtable);
		//Thread start.
		ExecutorService pool_1 = Executors.newFixedThreadPool(48);
		int SplitSize = scount/48;
		int Readlength=Integer.parseInt(Read_len);
		int Kmerlength=Integer.parseInt(Kmer_len);
		@SuppressWarnings("rawtypes")
		Callable c1_1=new DemoOrginalModify(0,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,0,SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_2=new DemoOrginalModify(1,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,SplitSize,2*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_3=new DemoOrginalModify(2,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,2*SplitSize,3*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_4=new DemoOrginalModify(3,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,3*SplitSize,4*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_5=new DemoOrginalModify(4,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,4*SplitSize,5*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_6=new DemoOrginalModify(5,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,5*SplitSize,6*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_7=new DemoOrginalModify(6,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,6*SplitSize,7*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_8=new DemoOrginalModify(7,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,7*SplitSize,8*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_9=new DemoOrginalModify(8,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,8*SplitSize,9*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_10=new DemoOrginalModify(9,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,9*SplitSize,10*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);	
		@SuppressWarnings("rawtypes")
		Callable c1_11=new DemoOrginalModify(10,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,10*SplitSize,11*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_12=new DemoOrginalModify(11,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,11*SplitSize,12*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_13=new DemoOrginalModify(12,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,12*SplitSize,13*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_14=new DemoOrginalModify(13,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,13*SplitSize,14*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_15=new DemoOrginalModify(14,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,14*SplitSize,15*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_16=new DemoOrginalModify(15,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,15*SplitSize,16*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_17=new DemoOrginalModify(16,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,16*SplitSize,17*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_18=new DemoOrginalModify(17,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,17*SplitSize,18*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_19=new DemoOrginalModify(18,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,18*SplitSize,19*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_20=new DemoOrginalModify(19,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,19*SplitSize,20*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_21=new DemoOrginalModify(20,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,20*SplitSize,21*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_22=new DemoOrginalModify(21,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,21*SplitSize,22*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_23=new DemoOrginalModify(22,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,22*SplitSize,23*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);	
		@SuppressWarnings("rawtypes")
		Callable c1_24=new DemoOrginalModify(23,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,23*SplitSize,24*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_25=new DemoOrginalModify(24,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,24*SplitSize,25*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_26=new DemoOrginalModify(25,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,25*SplitSize,26*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_27=new DemoOrginalModify(26,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,26*SplitSize,27*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_28=new DemoOrginalModify(27,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,27*SplitSize,28*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_29=new DemoOrginalModify(28,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,28*SplitSize,29*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_30=new DemoOrginalModify(29,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,29*SplitSize,30*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_31=new DemoOrginalModify(30,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,30*SplitSize,31*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_32=new DemoOrginalModify(31,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,31*SplitSize,32*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);	
		@SuppressWarnings("rawtypes")
		Callable c1_33=new DemoOrginalModify(32,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,32*SplitSize,33*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_34=new DemoOrginalModify(33,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,33*SplitSize,34*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_35=new DemoOrginalModify(34,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,34*SplitSize,35*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_36=new DemoOrginalModify(35,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,35*SplitSize,36*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_37=new DemoOrginalModify(36,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,36*SplitSize,37*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_38=new DemoOrginalModify(37,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,37*SplitSize,38*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_39=new DemoOrginalModify(38,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,38*SplitSize,39*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_40=new DemoOrginalModify(39,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,39*SplitSize,40*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_41=new DemoOrginalModify(40,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,40*SplitSize,41*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_42=new DemoOrginalModify(41,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,41*SplitSize,42*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_43=new DemoOrginalModify(42,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,42*SplitSize,43*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);	
		@SuppressWarnings("rawtypes")
		Callable c1_44=new DemoOrginalModify(43,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,43*SplitSize,44*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_45=new DemoOrginalModify(44,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,44*SplitSize,45*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_46=new DemoOrginalModify(45,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,45*SplitSize,46*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_47=new DemoOrginalModify(46,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,46*SplitSize,47*SplitSize-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);
		@SuppressWarnings("rawtypes")
		Callable c1_48=new DemoOrginalModify(47,Readlength,Kmerlength,scount,ReadSetArray,SplitSize,47*SplitSize,scount-1,HashTable,LowFreHashTable,SizeOfDSK,SizeOfLowFreDSK,LowFreReadPartitions,RepeatReadPartitions,NormalReadPartitions);		
		// Future
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_1 = pool_1.submit(c1_1);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_2 = pool_1.submit(c1_2);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_3 = pool_1.submit(c1_3);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_4 = pool_1.submit(c1_4);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_5 = pool_1.submit(c1_5);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_6 = pool_1.submit(c1_6);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_7 = pool_1.submit(c1_7);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_8 = pool_1.submit(c1_8);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_9 = pool_1.submit(c1_9);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_10 = pool_1.submit(c1_10);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_11 = pool_1.submit(c1_11);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_12 = pool_1.submit(c1_12);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_13 = pool_1.submit(c1_13);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_14 = pool_1.submit(c1_14);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_15 = pool_1.submit(c1_15);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_16 = pool_1.submit(c1_16);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_17 = pool_1.submit(c1_17);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_18 = pool_1.submit(c1_18);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_19 = pool_1.submit(c1_19);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_20 = pool_1.submit(c1_20);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_21 = pool_1.submit(c1_21);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_22 = pool_1.submit(c1_22);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_23 = pool_1.submit(c1_23);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_24 = pool_1.submit(c1_24);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_25 = pool_1.submit(c1_25);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_26 = pool_1.submit(c1_26);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_27 = pool_1.submit(c1_27);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_28 = pool_1.submit(c1_28);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_29 = pool_1.submit(c1_29);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_30 = pool_1.submit(c1_30);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_31 = pool_1.submit(c1_31);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_32 = pool_1.submit(c1_32);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_33 = pool_1.submit(c1_33);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_34 = pool_1.submit(c1_34);
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_35 = pool_1.submit(c1_35);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_36 = pool_1.submit(c1_36);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_37 = pool_1.submit(c1_37);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_38 = pool_1.submit(c1_38);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_39 = pool_1.submit(c1_39);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_40 = pool_1.submit(c1_40);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_41 = pool_1.submit(c1_41);
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    Future f1_42 = pool_1.submit(c1_42);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_43 = pool_1.submit(c1_43);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_44 = pool_1.submit(c1_44);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_45 = pool_1.submit(c1_45);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_46 = pool_1.submit(c1_46);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_47 = pool_1.submit(c1_47);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future f1_48 = pool_1.submit(c1_48);
		//Return
		if(f1_1.get().toString()!=null)
	    {
			 System.out.println(f1_1.get().toString());
		}
		if(f1_2.get().toString()!=null)
		{
			 System.out.println(f1_2.get().toString());
		}
		if(f1_3.get().toString()!=null)
	    {
			 System.out.println(f1_3.get().toString());
		}
	    if(f1_4.get().toString()!=null)
	    {
	    	 System.out.println(f1_4.get().toString());
	    }
	    if(f1_5.get().toString()!=null)
	    {
			 System.out.println(f1_5.get().toString());
	    }
	    if(f1_6.get().toString()!=null)
	    {
			 System.out.println(f1_6.get().toString());
	    }
	    if(f1_7.get().toString()!=null)
	    {
			 System.out.println(f1_7.get().toString());
	    }
	    if(f1_8.get().toString()!=null)
	    {
			 System.out.println(f1_8.get().toString());
	    }
	    if(f1_9.get().toString()!=null)
	    {
			 System.out.println(f1_9.get().toString());
	    }
	    if(f1_10.get().toString()!=null)
	    {
			 System.out.println(f1_10.get().toString());
	    }
		if(f1_11.get().toString()!=null)
	    {
			 System.out.println(f1_11.get().toString());
		}
		if(f1_12.get().toString()!=null)
		{
			 System.out.println(f1_12.get().toString());
		}
		if(f1_13.get().toString()!=null)
	    {
			 System.out.println(f1_13.get().toString());
		}
	    if(f1_14.get().toString()!=null)
	    {
	    	 System.out.println(f1_14.get().toString());
	    }
	    if(f1_15.get().toString()!=null)
	    {
			 System.out.println(f1_15.get().toString());
	    }
	    if(f1_16.get().toString()!=null)
	    {
			 System.out.println(f1_16.get().toString());
	    }
		if(f1_17.get().toString()!=null)
	    {
			 System.out.println(f1_17.get().toString());
	    }
	    if(f1_18.get().toString()!=null)
	    {
			 System.out.println(f1_18.get().toString());
	    }
	    if(f1_19.get().toString()!=null)
	    {
			 System.out.println(f1_19.get().toString());
	    }
	    if(f1_20.get().toString()!=null)
	    {
			 System.out.println(f1_20.get().toString());
	    }
		if(f1_21.get().toString()!=null)
	    {
			 System.out.println(f1_21.get().toString());
		}
		if(f1_22.get().toString()!=null)
		{
			 System.out.println(f1_22.get().toString());
		}
		if(f1_23.get().toString()!=null)
	    {
			 System.out.println(f1_23.get().toString());
		}
	    if(f1_24.get().toString()!=null)
	    {
	    	 System.out.println(f1_24.get().toString());
	    }
		if(f1_25.get().toString()!=null)
	    {
			 System.out.println(f1_25.get().toString());
	    }
	    if(f1_26.get().toString()!=null)
	    {
			 System.out.println(f1_26.get().toString());
	    }
		if(f1_27.get().toString()!=null)
	    {
			 System.out.println(f1_27.get().toString());
	    }
	    if(f1_28.get().toString()!=null)
	    {
			 System.out.println(f1_28.get().toString());
	    }
	    if(f1_29.get().toString()!=null)
	    {
			 System.out.println(f1_29.get().toString());
	    }
	    if(f1_30.get().toString()!=null)
	    {
			 System.out.println(f1_30.get().toString());
	    }
		if(f1_31.get().toString()!=null)
	    {
			 System.out.println(f1_31.get().toString());
	    }
		if(f1_32.get().toString()!=null)
	    {
			 System.out.println(f1_32.get().toString());
	    }
		if(f1_33.get().toString()!=null)
	    {
			 System.out.println(f1_33.get().toString());
		}
	    if(f1_34.get().toString()!=null)
	    {
	    	 System.out.println(f1_34.get().toString());
	    }
	    if(f1_35.get().toString()!=null)
	    {
			 System.out.println(f1_35.get().toString());
	    }
	    if(f1_36.get().toString()!=null)
	    {
			 System.out.println(f1_36.get().toString());
	    }
		if(f1_37.get().toString()!=null)
	    {
			 System.out.println(f1_37.get().toString());
	    }
	    if(f1_38.get().toString()!=null)
	    {
			 System.out.println(f1_38.get().toString());
	    }
	    if(f1_39.get().toString()!=null)
	    {
			 System.out.println(f1_39.get().toString());
	    }
	    if(f1_40.get().toString()!=null)
	    {
			 System.out.println(f1_40.get().toString());
	    }
		if(f1_41.get().toString()!=null)
	    {
			 System.out.println(f1_41.get().toString());
		}
		if(f1_42.get().toString()!=null)
	    {
			 System.out.println(f1_42.get().toString());
	    }
		if(f1_43.get().toString()!=null)
	    {
			 System.out.println(f1_43.get().toString());
		}
	    if(f1_44.get().toString()!=null)
	    {
	    	 System.out.println(f1_44.get().toString());
	    }
	    if(f1_45.get().toString()!=null)
	    {
			 System.out.println(f1_45.get().toString());
	    }
	    if(f1_46.get().toString()!=null)
	    {
			 System.out.println(f1_46.get().toString());
	    }
		if(f1_47.get().toString()!=null)
	    {
			 System.out.println(f1_47.get().toString());
	    }
	    if(f1_48.get().toString()!=null)
	    {
			 System.out.println(f1_48.get().toString());
	    }
		System.out.println("All threads have been successfully completed!");
		System.out.println("Normal Frequency Reads Write to File Start!");
		int LineNumber=0;
		for(int m=0;m<scount;m++)	
		{
			FileWriter writer= new FileWriter(NormalReadPartitions+"NormalReadSet_short1_right.fasta",true);
            writer.write(">"+(LineNumber++)+"\n"+ReadSetArray[m]+"\n");
            writer.close();
		}	
		System.out.println("Normal Frequency Reads Write to File End!");
		System.out.println("Successful Compeleted!");
                //shutdown pool.
                pool_1.shutdown();
	}
}
class DemoOrginalModify implements Callable<Object>{
	
	int Index=0;
	int Start;
	int end;
	int CountRate=1;
	int ArraySize=0;
	int SizeOfDSK=0;
	int SizeOfLowFreDSK=0;
	int readlength=0;
	int kmerlength=0;
	int SplitSize=0;
	String ReadSetArray[];
	String HashTable[];
	String LowFreHashTable[];
	String LowFreStrContext="";
	String RepeatStrContext="";	
	String NormalStrContext="";
	String LowFreReadSetPath="";
	String RepeatReadSetPath="";
	String NormalReadSetPath="";
	String encoding="utf-8";
	
	public DemoOrginalModify(int index,int Readlength,int Kmerlength,int scount,String readSetArray[],int splitSize,int StartPosition,int Endposition,String hashTable[],String lowFreHashTable[],int sizeOfDSK,int sizeofLowFreDSK,String LowFreOutPut,String RepeatOutPut,String NormalOutPut)
	{
		Start=StartPosition;
		end=Endposition;
	    readlength=Readlength;
	    kmerlength=Kmerlength;
		ReadSetArray=readSetArray;
		HashTable=hashTable;
		LowFreHashTable=lowFreHashTable;
		ArraySize=scount;
		SizeOfDSK=sizeOfDSK;
		SizeOfLowFreDSK=sizeofLowFreDSK;
		Index=index;
		SplitSize=splitSize;
		LowFreReadSetPath=LowFreOutPut;
		RepeatReadSetPath=RepeatOutPut;
		NormalReadSetPath=NormalOutPut;
	}
    public String call() throws IOException
    {
		int LowFreLineNum=0;
		int RepeatLineNum=0;
	    int NormalLineNum=0;
		for(int i=Start;i<=end;i++)
		{
			int CountOfLow=0;
			int CountOfRepeat=0;
            long starTime=System.currentTimeMillis();
			for (int k=0; k <= ReadSetArray[i].length()-kmerlength; k++) 
			{
			    if(GenerateReadSetByKmerFreThreadModify_Right.getHashUnit(ReadSetArray[i].substring(k,k+kmerlength),LowFreHashTable,SizeOfLowFreDSK)!=-1)
				{	 
					 CountOfLow++;
				}
				if(GenerateReadSetByKmerFreThreadModify_Right.getHashUnit(ReadSetArray[i].substring(k,k+kmerlength),HashTable,SizeOfDSK)!=-1)
                {
                     CountOfRepeat++;
				}
			}
			if(CountOfLow>=5)
			{
				ReadSetArray[i]="#"+ReadSetArray[i];
		    }
			else if(CountOfRepeat>=2*(readlength-kmerlength+1)/3)
			{
				ReadSetArray[i]="$"+ReadSetArray[i];
			}
			//Time
			long endTime=System.currentTimeMillis();
		    long Time=endTime-starTime;
			if(CountRate%10000==0)
			{
		        System.out.println(Thread.currentThread().getName()+"--->"+Time+"--->"+100*(double)CountRate/SplitSize+"%");
			}
			CountRate++;
	    }
		return "The thread:"+Index+"running completed!";
    }
}
