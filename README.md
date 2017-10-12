Latest Version
==============
Please see the latest version of ARC:https://github.com/bioinfomaticsCSU/ARC


License
=======

Copyright (C) 2017 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see <http://www.gnu.org/licenses/>.

Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)
School of Information Science and Engineering
Central South University
ChangSha
CHINA, 410083


Installation and operation of ARC 
==================================

### 1)Dependencies

When running ARC from GitHub source the following tools are
required:
* [jdk.1.8.0](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Bless.v0p12](https://sourceforge.net/projects/bless-ec/files/)
* [dsk.2.1.0](http://minia.genouest.org/dsk/)
* [amos-3.1.0](https://sourceforge.net/projects/amos/files/amos/3.1.0/)
* [SOAP2.2.04-r241](http://soap.genomics.org.cn/)
* [Abyss.2.0.2](http://www.bcgsc.ca/platform/bioinfo/software/abyss)
* [quast.4.3](https://sourceforge.net/projects/quast/files/)

### 2)System Requirements

Compiling BLESS requires MPI libraries. The latest version of BLESS was tested
with GCC 4.9.2, MPICH 3.1.3 (or OpenMPI 1.8.2).
If you see the following error message, you need to upgrade your MPI library:
error: initializing argument 2 of 'int MPI_File_open(MPI_Comm, char*, int, MPI_Info, ompi_file_t)'**

AMOS package makes use of Python and Perl (Practical Extraction and Report Language). Python and Perl are 
available on most systems and the latest versions can be downloaded free of charge. AMOS requires Perl 
version 5.6.0 or later. If ‘perl’ or ‘python’ are available from your system PATH, all is well, if not you 
will need to instruct ‘configure’ where they are located by setting the environment variable PERL and PYTHON 
to the full path of ‘perl’ and ‘python’ respectively (see the Defining Variables section in the ‘INSTALL’ file). 
Some Perl scripts in the AMOS package require additional modules that you should install:
DBI (http://search.cpan.org/~timb/DBI/)
Statistics::Descriptive (http://search.cpan.org/~shlomif/Statistics-Descriptive-3.0100/)
XML::Parser (http://search.cpan.org/~msergeant/XML-Parser-2.36/)

SOAP2 aims for large plant and animal genomes, although it also works well on bacteria and fungi genomes.  
It runs on 64-bit Linux system with a minimum of 5G physical memory. 
For big genomes like human, about 150 GB memory would be required.

ABySS requires a C++ compiler that supports
[OpenMP](http://www.openmp.org) such as [GCC](http://gcc.gnu.org).
ABySS will receive an error when compiling with Boost 1.51.0 or 1.52.0
since they contain a bug. Later versions of Boost compile without error.

quast default pipeline requires:python 2.5, 2.6, 2.7, 3.3, 3.4 or 3.5.
perl 5.6.0 or higher.
gcc 4.7 or higher.
basic UNIX tools (make, sh, sed, awk, ar).
In addition, QUAST submodules require:Java JDK (tested with OpenJDK 6) for GAGE.
Time::HiRes perl module for GeneMark-ES;Boost (tested with v1.56.0) for E-MEM.

### 3)Install auxiliary tools

When running ARC from GitHub source all the tools listed in 1) should be installed.
For specific installation methods,please read the README file of each tool carefully.

### 4)Add system environment variables
The user can modify the system environment variables with the following commands:

    sudo vim /etc/profile

       export JAVA_HOME="/usr/local/jdk1.8.0_20/bin" 
       export Bless_HOME="/home/.../Bless"
       export DSK_HOME="/home/.../dsk-2.1.0-Linux/bin" 
       export PERL5LIB=$PERL5LIB:/home/.../amos-3.1.0/lib/"  
       export SOAPdenovo2_HOME="/home/.../SOAPdenovo2-master" 
       export Abyss_HOME="/home/.../abyss/bin" 
       export Quast_HOME="/home/.../quast-4.3"
       export PATH="$JAVA_HOME:$SOAPdenovo2_HOME:$Bless_HOME:$Bowtie_Home:$DSK_HOME:$Abyss_HOME:$Quast_HOME:$PATH" 

    source /etc/profile
 
### 5)Install ARC

ARC automatically compiles all its sub-parts when needed (on the first use). 
Thus, installation is not required.

### 6)Run ARC.

#### Load library files into ARC-master.
    
	Before running ARC, we need to load the library files into lib folder under the ARC-master directory(/home/.../ARC-master/lib/).
	
	For example:
	
	### One library (The library is composed of paired-end reads).
	
	/home/.../ARC-master/lib/frag_1.fastq (The left mate reads of the library)
	/home/.../ARC-master/lib/frag_2.fastq (The right mate reads of the library)
	
	### Two libraries (The first library is composed of paired-end reads; the second library is composed of mate-paired reads).
    
	/home/.../ARC-master/lib/frag_1.fastq (The left mate reads of the first library)
	/home/.../ARC-master/lib/frag_2.fastq (The right mate reads of the first library)
	/home/.../ARC-master/lib/shortjump_1.fastq (The left mate reads of the second library)
	/home/.../ARC-master/lib/shortjump_2.fastq (The right mate reads of the second library)
	
	### Three libraries (The first library is composed of paired-end reads; the second library is composed of mate-paired reads; the third library is also composed of mate-paired reads).
	
	/home/.../ARC-master/lib/frag_1.fastq (The left mate reads of the first library)
	/home/.../ARC-master/lib/frag_2.fastq (The right mate reads of the first library)
	/home/.../ARC-master/lib/shortjump_1.fastq (The left mate reads of the second library)
	/home/.../ARC-master/lib/shortjump_2.fastq (The right mate reads of the second library)
	/home/.../ARC-master/lib/longjump_1.fastq (The left mate reads of the third library)
	/home/.../ARC-master/lib/longjump_2.fastq (The right mate reads of the third library)

#### Load the reference sequence file into ARC-master.

    To provide unbiased benchmarks, we use evaluation tool Quast (Gurevich et al., 2013) for correction analysis. The process of evaluation requires the reference sequence, so we need to load the reference sequence file into "Reference" folder under the ARC-master directory(/home/.../ARC-master/Reference/). 
	
	For example:
	
	/home/.../ARC-master/Reference/reference.fa (reference.fa is the reference sequence file)
	
#### Edit the configuration:
    
	Before running ARC, we also need configure the config.txt under the ARC-master directory(/home/.../ARC-master/config.txt).
    
	For example:
    
    #read_short_length.
    Read_short_length=101 
    #read_shortjump_length
    Read_shortjump_length=* 
    #read_longjump_length
    Read_longjump_length=*   
    ########################################################################
    #short_kmer_length_for_Bless.
    bless_short_kmerlength=71 
    #shortjump_kmer_length_for_Bless.
    bless_shortjump_kmerlength=*
    #longjump_kmer_length_for_Bless.
    bless_longjump_kmerlength=*
    ########################################################################
    #kmer_length_for_dsk.
    dsk_kmerlength=11 
    #The_directory_where_ARC-master_is_located.
    home=/home/liaoxingyu/ARC-master
    #Abyss_bin_path.
    Abyss_bin=/home/liaoxingyu/abyss/bin 
    ########################################################################
    #The_average_insert_size_of_lib1.
    Ave_insertsize1=220 
    #The_average_insert_size_of_lib2.
    Ave_insertsize2=* 
    #The_average_insert_size_of_lib3.
    Ave_insertsize3=*
    ########################################################################
    #The_name_of_left_lib1.
    lib1_left_name=insert_220_1
    #The_name_of_right_lib1.
    lib1_right_name=insert_220_2
    #The_name_of_left_lib2.
    lib2_left_name=*
    #The_name_of_right_lib2.
    lib2_right_name=*
    #The_name_of_left_lib3.
    lib3_left_name=*
    #The_name_of_right_lib3.
    lib3_right_name=*
    #########################################################################
    #kmer_length_for_SOAP2_assembly.
    Soap2_kmerlength=49               
    #Low_depth_kmer_length_for_SOAP2_assembly.
    Soap2_lowdepthkmerlength=21         
    #########################################################################
    #kmer_length_for_Abyss_assembly.
    Abyss_k=55  
    #Low_depth_kmer_length_for_Abyss_assembly.
    Abyss_low_k=21
    #Configuration_of_parameter_l_in_Abyss_assembly.
    Abyss_l=40  
    #Configuration_of_parameter_n_in_Abyss_assembly.
    Abyss_n=5 
    #Configuration_of_parameter_s_in_Abyss_assembly.
    Abyss_s=100
    #Configuration_of_parameter_Assembly_name_in_Abyss_assembly.
    Abyss_name=liaoxingyu-3 
    ##########################################################################
    #dsk_bin_path
    dsk_bin=/home/liaoxingyu/ClusterTool/dsk-2.1.0-Linux/bin
    #reference_path
    reference_name=genome_2.fasta
	
	* 'Read_short_length': The average length of reads in the first library(paired-end reads).
    * 'Read_shortjump_length': The average length of reads in the second library(mate-paired reads).If there is no the second library,the value of it is marked with "*".
	* 'Read_longjump_length': The average length of reads in the third library(mate-paired reads).If there is no the third library,the value of it is marked with "*".
	* 'bless_short_kmerlength': The k-mer length(odd number) for bless error correction in the first library(paired-end reads).
	* 'bless_shortjump_kmerlength': The k-mer length(odd number) for bless error correction in the second library(mate-paired reads).If there is no the second library,the value of it is marked with "*".
	* 'bless_longjump_kmerlength': The k-mer length(odd number) for bless error correction in the third library(mate-paired reads).If there is no the third library,the value of it is marked with "*".
	* 'dsk_kmerlength': The k-mer length(odd number) for dsk counts the depth of each unique k-mer(The default value is 11). ['11']
	* 'home': The work directory of ARC-master. eg: If ARC-master is stored in the directory of "/home/tool/", home=/home/tool/ARC-master.
	* 'Abyss_bin': The path of the bin directory of Abyss. eg: If Abyss is stored in the directory of "/home/tool/", Abyss_bin=/home/tool/Abyss/bin.
	* 'Ave_insertsize1': The average insertsize of the first library(paired-end reads).
	* 'Ave_insertsize2': The average insertsize of the second library(mate-paired reads).If there is no the second library,the value of it is marked with "*".
	* 'Ave_insertsize3': The average insertsize of the third library(mate-paired reads).If there is no the third library,the value of it is marked with "*".
	* 'lib1_left_name' : The name of the left fastq file of the first library. eg: If the left fastq file of the first library is "frag_1.fastq" , lib1_left_name=frag_1.
	* 'lib1_right_name' : The name of the right fastq file of the first library. eg: If the right fastq file of the first library is "frag_2.fastq" , lib1_right_name=frag_2.
	* 'lib2_left_name' : The name of the left fastq file of the second library. eg: If the left fastq file of the second library is "shortjump_1.fastq" , lib2_left_name=shortjump_1. If there is no the second library,the value of it is marked with "*".
	* 'lib2_right_name' : The name of the right fastq file of the second library. eg: If the right fastq file of the second library is "shortjump_2.fastq" , lib2_right_name=shortjump_2. If there is no the second library,the value of it is marked with "*".
	* 'lib3_left_name' : The name of the left fastq file of the third library. eg: If the left fastq file of the third library is "longjump_1.fastq" , lib3_left_name=longjump_1. If there is no the third library,the value of it is marked with "*".
	* 'lib3_right_name' : The name of the right fastq file of the third library. eg: If the right fastq file of the third library is "longjump_2.fastq" , lib3_right_name=longjump_2. If there is no the third library,the value of it is marked with "*".
	* 'Soap2_kmerlength' : The k-mer length(odd number) for SOAP2 assembles normal depth reads and high depth reads.
	* 'Soap2_lowdepthkmerlength' : The k-mer length(odd number) for SOAP2 assembles low depth reads.
	* 'Abyss_k' : The k-mer length(odd number) for Abyss assembles normal depth reads and high depth reads.
	* 'Abyss_low_k' : The k-mer length(odd number) for Abyss assembles low depth reads.
	* 'Abyss_l' : The minimum alignment length of a read(bp) in Abyss assembly (The default value is 40). ['40']
	* 'Abyss_n' : The minimum number of pairs required for building contigs in Abyss assembly (The default value is 10). ['10']
	* 'Abyss_s' : The minimum unitig size required for building contigs(bp) in Abyss assembly (The default value is 100). ['100']
	* 'Abyss_name' : The output prefix of Abyss.
	* 'dsk_bin' : The path of the bin directory of dsk. eg: If dsk is stored in the directory of "/home/tool/", dsk_bin=/home/tool/dsk/bin.
	* 'reference_name' : The name of the reference sequence file(only used in quast). eg: If the name of reference sequence file is "genome-2.fa", reference_name=genome-2.fa.

    
#### Run the following command to start the ARC.
     
	cd /home/.../ARC-master
	./run.sh
    
	If the system prompts "operation not permitted" ,we need to run the following commands to modify the permissions of ARC-master folder at this time.
    
	cd ..
	chmod -R 777  ARC-master
	cd ARC-master
	./run.sh

### 7)Output.

#### The final contigs and scaffolds.
  
	The final contig and scaffold generated by SOAP2 will be stored in "/home/.../ARC-master/FinalResults/SOAP2/before".
	The final contig and scaffold generated by SOAP2+ARC will be stored in "/home/.../ARC-master/FinalResults/SOAP2/after+filter".
    The final contig and scaffold generated by Abyss will be stored in "/home/.../ARC-master/FinalResults/SOAP2/before".
	The final contig and scaffold generated by Abyss+ARC will be stored in "/home/.../ARC-master/FinalResults/SOAP2/after+filter".    

#### The intermediate results of assembly.
    
	The intermediate results generated by SOAP2 will be stored in "/home/.../ARC-master/AssemblyResults/SOAP2/before".
	The intermediate results generated by SOAP2+ARC will be stored in "/home/.../ARC-master/AssemblyResults/SOAP2/after+filter".
    The intermediate results generated by Abyss will be stored in "/home/.../ARC-master/AssemblyResults/Abyss/before"
	The intermediate results generated by Abyss+ARC will be stored in "/home/.../ARC-master/AssemblyResults/Abyss/after+filter".
	
#### The final quast evaluation results.
 
    The final quast evaluation results of SOAP2 and SOAP2+ARC will be stored in "/home/.../ARC-master/FinalResults/SOAP2/Quast/".
    The final quast evaluation results of Abyss and Abyss+ARC will be stored in "/home/.../ARC-master/FinalResults/Abyss/Quast/").
