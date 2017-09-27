//package Statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenerateFastaFromFastqFilesLong {
	
	public static int getLine(String ReadSetPath) throws IOException //Get the number of line of ReadSet.
	{
		int line=0; 
		String encoding = "utf-8";
		String readtemp = "";
		File file = new File(ReadSetPath);
		if (file.isFile() && file.exists()) 
		{ 
			  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
			  BufferedReader bufferedReader = new BufferedReader(read);
		      while ((readtemp=bufferedReader.readLine()) != null)
			  { 
				   line++;   
		      } 
		      bufferedReader.close();
	    }
		return line/4;
	}
	
	
	public static int readTxtFile(String ReadSetPath, String[] ReadSetArray) // Put the ReadSet into Array.
	{
		int count = 0;
		try {
			  String encoding = "utf-8";
			  File file = new File(ReadSetPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((bufferedReader.readLine())!= null) {
						  ReadSetArray[count++]=bufferedReader.readLine();
						  bufferedReader.readLine();
						  bufferedReader.readLine();
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
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//String short_1_A_Path = "/home/liaoxingyu/FinalRealDataTest/S_aureus/lib/short1.A.corrected.fastq";
		//String short_1_B_Path = "/home/liaoxingyu/FinalRealDataTest/S_aureus/lib/short1.B.corrected.fastq";
		//String OutPutPath="/home/liaoxingyu/FinalRealDataTest/S_aureus/lib/";
		String short_1_A_Path = args[0];
		String short_1_B_Path = args[1];
		String OutPutPath=args[2];
		//short_1_A Path 
		int short_1_A_Size = getLine(short_1_A_Path);
		String[] short_1_A_Array = new String[short_1_A_Size];
		int scount_short_1_A = readTxtFile(short_1_A_Path, short_1_A_Array);	
	    System.out.println("The size of short_2_A_Array is:"+scount_short_1_A);
		//Left File.
		int Short1_NumLeft=0;
		for(int j=0;j<scount_short_1_A;j++)
		{
			FileWriter writer= new FileWriter(OutPutPath+"short2.left.fasta",true);
            writer.write(">"+(Short1_NumLeft++)+"\n"+short_1_A_Array[j]+"\n");
            writer.close();
		}
		//short_1_B Path 
		int short_1_B_Size = getLine(short_1_B_Path);
		String[] short_1_B_Array = new String[short_1_B_Size];
		int scount_short_1_B = readTxtFile(short_1_B_Path, short_1_B_Array);	
	    System.out.println("The size of short_2_B_Array is:"+scount_short_1_B);
		int Short1_NumRight=0;
		for(int j=0;j<scount_short_1_B;j++)
		{
			FileWriter writer= new FileWriter(OutPutPath+"short2.right.fasta",true);
            writer.write(">"+(Short1_NumRight++)+"\n"+short_1_B_Array[j]+"\n");
            writer.close();
		}
		//Generate Fasta File.
	    int Num=0;
		for(int m=0;m<scount_short_1_A;m++)
		{
			FileWriter writer= new FileWriter(OutPutPath+"short2.fasta",true);
            writer.write(">"+(Num++)+"\n"+short_1_A_Array[m]+"\n"+">"+(Num++)+"\n"+short_1_B_Array[m]+"\n");
            writer.close();
		}
	}
}
