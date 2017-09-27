//package shellproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GenerateConfigFile2 {
    
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
       		    	     if(readtemp.charAt(0)!='#')
    		    	     {
    		    		     line++;
    		    	     } 
                     } 
                     bufferedReader.close();
           }
           return line;
    }
	
    public static int ReadTxtFile(String FilePath, String[] FileSetArray) // Put the ReadSet into Array.
	{
		int count = 0;
		try {
			  String readtemp ="";
			  String encoding = "utf-8";
			  File file = new File(FilePath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // set format of encoding.
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine())!= null) 
				  {
					  if(readtemp.charAt(0)!='#')
					  {
						  FileSetArray[count++]=readtemp;
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String encoding="utf-8";
		String FileSetPath=args[0];
		String FileWritePath=args[1];
        String HomePath=args[2];
		int SizeOfFile=getLine(FileSetPath)+10;
	    String FileSetArray[]=new String[SizeOfFile];
	    int RealSizeOfFileSetArray=ReadTxtFile(FileSetPath,FileSetArray); 
	    //System.out.println("The real size of FileSetArray is:"+RealSizeOfFileSetArray);
	    String insertsize1="";
		String insertsize2="";
	    String readlength1="";
		String readlength2="";
		String write_estimate="";
	    String write_before="";
	    String write_after="";
	    String write_low="";
	    String write_high="";
	    String write_filter="";
            String write_gapcloser="";
		//lib.
		String lib1_left="";
		String lib1_right="";
		String lib2_left="";
		String lib2_right="";
		
	    for(int i=0;i<RealSizeOfFileSetArray;i++)
	    {
			if(i==0)
			{
				String [] SplitLine = FileSetArray[i].split("=");
	    		readlength1=SplitLine[1];
			}
			if(i==1)
			{
			    String [] SplitLine = FileSetArray[i].split("=");
	    		readlength2=SplitLine[1];
			}
	    	if(i==7)
	    	{
	    		String [] SplitLine = FileSetArray[i].split("=");
	    		insertsize1=SplitLine[1];
	    	}
			if(i==8)
	    	{
	    		String [] SplitLine = FileSetArray[i].split("=");
	    		insertsize2=SplitLine[1];
	    	}
			if(i==9)
			{
			    String [] SplitLine = FileSetArray[i].split("=");
	    		    lib1_left=SplitLine[1];
			}
			if(i==10)
			{
			    String [] SplitLine = FileSetArray[i].split("=");
	    		    lib1_right=SplitLine[1];
			}
			if(i==11)
			{
			    String [] SplitLine = FileSetArray[i].split("=");
	    		    lib2_left=SplitLine[1];
			}
			if(i==12)
			{
			    String [] SplitLine = FileSetArray[i].split("=");
	    		    lib2_right=SplitLine[1];
			}	
	    }
	    //Write gapreduce.
       write_gapcloser="[LIB]"+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"max_rd_len="+readlength1+"\n"+"rank=1"+"\n"+"q1="+HomePath+"/lib/"+lib1_left+".corrected.fastq"+"\n"+
        		"q2="+HomePath+"/lib/"+lib1_right+".corrected.fastq"+"\n"+
        		"[LIB]"+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"max_rd_len="+readlength2+"\n"+"rank=2"+"\n"+
        		"q1="+HomePath+"/lib/"+lib2_left+".corrected.fastq"+"\n"+
        		"q2="+HomePath+"/lib/"+lib2_right+".corrected.fastq";
       BufferedWriter out0 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"gapcloser.lib"),encoding)); 
       out0.write(write_gapcloser);
       out0.close();
		//Write before.
	    write_after="[LIB]"+"\n"+"max_rd_len="+readlength1+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"rank=1"+"\n"+"q1="+HomePath+"/lib/"+lib1_left+".corrected.fastq"+"\n"+"q2="+HomePath+"/lib/"+lib1_right+".corrected.fastq"+"\n"+"[LIB]"+"\n"+"[LIB]"+"\n"+"max_rd_len="+readlength2+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"rank=2"+"\n"+"q1="+HomePath+"/lib/"+lib2_left+".corrected.fastq"+"\n"+"q2="+HomePath+"/lib/"+lib2_right+".corrected.fastq"+"\n"+"[LIB]";
		BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"before.lib"),encoding)); 
		out1.write(write_after);
		out1.close();
		//Write After.
	    write_after="[LIB]"+"\n"+"max_rd_len="+readlength1+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"rank=1"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short1.Filter.fasta"+"\n"+"[LIB]"+"\n"+"[LIB]"+"\n"+"max_rd_len="+readlength2+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"rank=2"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short2.Filter.fasta"+"\n"+"[LIB]";
		BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"after.lib"),encoding)); 
		out2.write(write_after);
		out2.close();
	    //WriteLow
	    write_low="[LIB]"+"\n"+"max_rd_len="+readlength1+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"rank=1"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short1.LowFre.fasta"+"\n"+"[LIB]"+"\n"+"[LIB]"+"\n"+"max_rd_len="+readlength2+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"rank=2"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short2.LowFre.fasta"+"\n"+"[LIB]";
		BufferedWriter out3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"low.lib"),encoding)); 
		out3.write(write_low);
		out3.close();
	    //WriteHigh
	    write_high="[LIB]"+"\n"+"max_rd_len="+readlength1+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"rank=1"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short1.HighFre.fasta"+"\n"+"[LIB]"+"\n"+"[LIB]"+"\n"+"max_rd_len="+readlength2+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"rank=2"+"\n"+"p="+HomePath+"/NormalReadPartitions/ReadSet_short2.HighFre.fasta"+"\n"+"[LIB]";
		BufferedWriter out4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"high.lib"),encoding)); 
		out4.write(write_high);
		out4.close();
	    //WriteFilter
	    write_filter="[LIB]"+"\n"+"max_rd_len="+readlength1+"\n"+"avg_ins="+insertsize1+"\n"+"reverse_seq=0"+"\n"+"asm_flags=3"+"\n"+"rank=1"+"\n"+"p="+HomePath+"/NormalReadPartitions/AbnormalReadSet_short1.fasta"+"\n"+"[LIB]"+"\n"+"[LIB]"+"\n"+"max_rd_len="+readlength2+"\n"+"avg_ins="+insertsize2+"\n"+"reverse_seq=1"+"\n"+"asm_flags=3"+"\n"+"rank=2"+"\n"+"p="+HomePath+"/NormalReadPartitions/AbnormalReadSet_short2.fasta"+"\n"+"[LIB]";
		BufferedWriter out5 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileWritePath+"filter.lib"),encoding)); 
		out5.write(write_filter);
		out5.close();
	}
}
