//package shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadLengthCheck {
	
    public static int ReadLengthChecking(String ReadSetPath,String LibFileName) throws IOException //Get the number of line of ReadSet.
    {
         int LineNum=0;
         int MaxReadLength=0;
         int SmallReadLength=0;
         String encoding = "utf-8";
         String readtemp="";
         File file = new File(ReadSetPath+LibFileName+".fastq");
         if (file.isFile() && file.exists()) 
         { 
               InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
               BufferedReader bufferedReader = new BufferedReader(read);
               while ((readtemp=bufferedReader.readLine()) != null)
               { 
            	    readtemp=bufferedReader.readLine();
            	    if(LineNum==0)
            	    {
            	    	MaxReadLength=readtemp.length();
            	    	SmallReadLength=readtemp.length();
            	    }
            	    else
            	    {
                        if(readtemp.length()>MaxReadLength)
                        {
                        	MaxReadLength=readtemp.length();
                        } 
                        if(readtemp.length()<SmallReadLength)
                        {
                        	SmallReadLength=readtemp.length();
                        }
            	    }
            	    LineNum++;
            	    readtemp=bufferedReader.readLine();
            	    readtemp=bufferedReader.readLine();
               } 
               bufferedReader.close();
         }
         System.out.println("Check: ("+"Lib:"+LibFileName+", "+"ReadLength_Max="+MaxReadLength+",ReadLength_Min="+SmallReadLength+")");
         if(MaxReadLength==SmallReadLength)
         {
               return 1;
         }
         else
         {
               return 0;
         }
    }
    public static void main(String[] args) throws IOException {
           // TODO Auto-generated method stub
           String ReadSetPath=args[0];
           String LibFileName=args[1];
           String FlagFilePath=args[2];
           //System.out.println("FlagFilePath:"+FlagFilePath);
           int Flag=ReadLengthChecking(ReadSetPath,LibFileName);
           //write.
	   FileWriter writer= new FileWriter(FlagFilePath,true);
	   writer.write(Flag+"\n");
	   writer.close();
    }
}
