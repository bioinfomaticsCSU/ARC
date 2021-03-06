#!/bin/bash
echo  "*****************************************************************************"
echo  "Pretreatment: configuration"
TEST=$(cat ./Run-config.txt)
i=0;
for p in $TEST; do
	arra[$i]=$p
	#echo "arra[$i] is ${arra[$i]}"	
	i=$((i+1))
done
for ((i=0;i<${#arra[@]};i++))
    do
    if [ $i = 1 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Read_Average_length=$var
       echo "Read_Average_length="$var
    fi
    if [ $i = 3 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       bless_kmerlength=$var
       echo "bless_kmerlength="$var
    fi
    if [ $i = 5 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       dsk_kmerlength=$var
       echo "dsk_kmerlength="$var
    fi
    if [ $i = 7 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       home=$var
       echo "home="$var
    fi
    if [ $i = 9 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_bin=$var
       echo "Abyss_bin="$var
    fi
    if [ $i = 11 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Ave_insertsize=$var
       echo "Ave_insertsize="$var
    fi
    if [ $i = 13 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib_left_name=$var
       echo "lib_left_name="$var
    fi
    if [ $i = 15 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib_right_name=$var
       echo "lib_right_name="$var
    fi
    if [ $i = 17 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Soap2_kmerlength=$var
       echo "Soap2_kmerlength="$var
    fi
    if [ $i = 19 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Soap2_lowdepthkmerlength=$var
       echo "Soap2_lowdepthkmerlength="$var
    fi
    if [ $i = 21 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_k=$var
       echo "Abyss_k="$var
    fi
    if [ $i = 23 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_low_k=$var
       echo "Abyss_low_k="$var
    fi
    if [ $i = 25 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_l=$var
       echo "Abyss_l="$var
    fi
    if [ $i = 27 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_n=$var
       echo "Abyss_n="$var
    fi
    if [ $i = 29 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_s=$var
       echo "Abyss_s="$var
    fi
    if [ $i = 31 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_name=$var
       echo "Abyss_name="$var
    fi
    if [ $i = 33 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       dsk_bin=$var
       echo "dsk_bin="$var
    fi
    if [ $i = 35 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       reference_name=$var
       echo "reference_name="$var
    fi
done
#Generate SOAP2 Config files.
rm -rf $home/ConfigFile/*
javac  $home/Program/GenerateConfigFile1.java
java -classpath  $home/Program/ GenerateConfigFile1 $home/Run-config.txt $home/ConfigFile/ $home $lib_left_name $lib_right_name $Read_Average_length $Ave_insertsize
echo  "The configuration of SOAP2 has been generated!"
echo  " "
echo  "*****************************************************************************"
##################################################################################
#--------------------------------------clean-------------------------------------#
##################################################################################
#lib
rm -rf $home/lib/short1.right.fasta
rm -rf $home/lib/short1.left.fasta
rm -rf $home/lib/short1.fasta
rm -rf $home/AssemblyResults/SOAP2/tmp/*
#SOAP2
rm -rf $home/AssemblyResults/SOAP2/before/*
rm -rf $home/AssemblyResults/SOAP2/after/*
rm -rf $home/AssemblyResults/SOAP2/low/*
rm -rf $home/AssemblyResults/SOAP2/high/*
rm -rf $home/AssemblyResults/SOAP2/filter/*
rm -rf $home/AssemblyResults/SOAP2/after+filter/*
#Abyss
rm -rf $home/AssemblyResults/Abyss/low/*
rm -rf $home/AssemblyResults/Abyss/high/*
rm -rf $home/AssemblyResults/Abyss/after/*
rm -rf $home/AssemblyResults/Abyss/before/*
rm -rf $home/AssemblyResults/Abyss/filter/*
rm -rf $home/AssemblyResults/Abyss/after+filter/*
#quast
rm -rf $home/FinalResults/Abyss/before/*
rm -rf $home/FinalResults/Abyss/after+filter/*
rm -rf $home/FinalResults/SOAP2/before/*
rm -rf $home/FinalResults/SOAP2/after+filter/*
#NormalReadPartitions
rm -rf $home/NormalReadPartitions/*
#KmerFreStatics
rm -rf $home/KmerFreStatics/*
#dsk
rm -rf $home/dsk/*
rm -rf $dsk_bin/trashme_*

####################################################################################
####################################################################################
####################################################################################

#run Bless.
echo " "
echo  "*****************************************************************************"
echo  "Step-1:Bless running"
#check lib
rm -rf $home/CheckLib/checklib.txt
javac  $home/Program/ReadLengthCheck.java
java -classpath  $home/Program/ ReadLengthCheck $home/lib/ $lib_left_name $home/CheckLib/checklib.txt
java -classpath  $home/Program/ ReadLengthCheck $home/lib/ $lib_right_name $home/CheckLib/checklib.txt
TEST1=$(cat $home/CheckLib/checklib.txt)
i1=0;
for p1 in $TEST1; do
	arra1[$i1]=$p1
	#echo "arra1[$i1] is ${arra1[$i1]}"	
	i1=$((i1+1))
done
for ((i1=0;i1<${#arra1[@]};i1++))
    do
    if [ $i1 = 0 ]
    then
       if [ ${arra1[$i1]} = 1 ] 
       then
          echo  " "
          echo  "---> All reads in lib1_left is equal in length! Bless working!"
          echo  "---> Bless lib1 left."
          bless  -read $home/lib/$lib_left_name.fastq  -prefix  $home/lib/$lib_left_name  -kmerlength  $bless_kmerlength
       else
          echo  "The length of reads in $lib_left_name is not equal"
          echo  "Bless can not work! The system jumps to no error correction mode now......."
          cp $home/lib/$lib1_left_name.fastq $home/lib/$lib_left_name.corrected.fastq
       fi
    fi
    if [ $i1 = 1 ] 
    then
       if [ ${arra1[$i1]} = 1 ] 
       then
          echo  " "
          echo  "---> All reads in lib1_right is equal in length! Bless working!"
          echo  "---> Bless lib1 right."
          bless  -read $home/lib/$lib_right_name.fastq -prefix  $home/lib/$lib_right_name  -kmerlength  $bless_kmerlength
       else
          echo  "The length of reads in $lib_right_name is not equal"
          echo  "Bless can not work! The system jumps to no error correction mode now......."
          cp $home/lib/$lib_right_name.fastq $home/lib/$lib_right_name.corrected.fastq
       fi
    fi
done
#
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-2:Convert fastq to the fasta"
echo  "---> Convert"
javac  $home/Program/GenerateFastaFromFastqFiles.java
java -classpath  $home/Program/ GenerateFastaFromFastqFiles $home/lib/$lib_left_name.corrected.fastq $home/lib/$lib_right_name.corrected.fastq $home/lib/
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-3:dsk running"
cd $dsk_bin
echo  "---> dsk"
dsk  -file $home/lib/short1.fasta -kmer-size $dsk_kmerlength -abundance-min 1
echo  "---> h5 to txt"
dsk2ascii -file short1.h5 -out short1.txt
cp short1.txt $home/dsk/
rm -rf short1.txt
rm -rf short1.h5
cd $home
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-4:kmer Frequency Statistics"
echo  "---> Statistics"
rm -rf $home/KmerFreStatics/KmerFreStaticsFile.short1.txt
javac  $home/Program/kmerFrequencyStaticsModify.java
java -classpath  $home/Program/ kmerFrequencyStaticsModify $home/dsk/ $home/KmerFreStatics/
echo  "---> Sequencing depth estimate and kmer classification start!"
echo  "---> SOAP2 before"
SOAPdenovo-127mer all -L 101 -s $home/ConfigFile/before.lib -K $Soap2_kmerlength -R -o $home/AssemblyResults/SOAP2/before/before 1>$home/Log/SOAP2/Assembly_before 2>$home/Log/SOAP2/Assembly_Error_before
#Abyss
cd $Abyss_bin
echo  "---> Abyss before"
abyss-pe k=$Abyss_k l=$Abyss_l n=$Abyss_n s=$Abyss_s name=$Abyss_name  in="$home/lib/$lib_left_name.corrected.fastq $home/lib/$lib_right_name.corrected.fastq" > $home/Log/Abyss/Abyss_assembly_before.log
cp $Abyss_name-6.fa  contigs.fa
cp $Abyss_name-8.fa  scaffolds.fa
cp $Abyss_bin/contigs.fa $home/AssemblyResults/Abyss/before/
cp $Abyss_bin/scaffolds.fa $home/AssemblyResults/Abyss/before/
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
#End
cd $home
echo  "Abyss before Successful end!"
#before end.
rm -rf $home/KmerFreStatics/FilterLowFre.short1.txt
rm -rf $home/KmerFreStatics/FilterNormal.short1.txt
rm -rf $home/KmerFreStatics/FilterRepeat.short1.txt
echo  "---> Sequencing depth estimate"
javac  $home/Program/FilterKmerStaticsA.java
java -classpath  $home/Program/ FilterKmerStaticsA $home/AssemblyResults/Abyss/before/scaffolds.fa $home/lib/short1.fasta $home/KmerFreStatics/ $Read_Average_length $dsk_kmerlength
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-5:Mark low depth kmer and high depth kmer"
echo  "---> Mark"
rm -rf $home/KmerFreStatics/LowFreKmer.short1.txt
rm -rf $home/KmerFreStatics/RepeatKmer.short1.txt
javac  $home/Program/MarkLowAndHighFreKmer.java
java -classpath  $home/Program/ MarkLowAndHighFreKmer $home/dsk/ $home/KmerFreStatics/
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-6:classify Reads - Multi Threads"
echo  "---> left"
rm -rf $home/NormalReadPartitionst/NormalReadSet_short1_left.fasta
rm -rf $home/NormalReadPartitions/NormalReadSet_short1_right.fasta
javac  $home/Program/GenerateReadSetByKmerFreThreadModify_Left.java
javac  $home/Program/GenerateReadSetByKmerFreThreadModify_Right.java
java -classpath  $home/Program/ GenerateReadSetByKmerFreThreadModify_Left $home/lib/ $home/KmerFreStatics/RepeatKmer.short1.txt $home/KmerFreStatics/LowFreKmer.short1.txt $home/NormalReadPartitions/ $home/NormalReadPartitions/  $home/NormalReadPartitions/  $Read_Average_length  $dsk_kmerlength 
echo  "---> right"
java -classpath  $home/Program/ GenerateReadSetByKmerFreThreadModify_Right $home/lib/ $home/KmerFreStatics/RepeatKmer.short1.txt $home/KmerFreStatics/LowFreKmer.short1.txt $home/NormalReadPartitions/ $home/NormalReadPartitions/  $home/NormalReadPartitions/ $Read_Average_length  $dsk_kmerlength
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-7:Filter low depth reads and high depth reads"
echo  "---> Filter"
rm -rf $home/NormalReadPartitionst/ReadSetShort.LowFre.fasta
rm -rf $home/NormalReadPartitions/ReadSetShort.HighFre.fasta
rm -rf $home/NormalReadPartitionst/ReadSetShort.Filter.fasta
rm -rf $home/NormalReadPartitions/AbnormalReadSetShort.fasta
javac  $home/Program/FilterLowAndHighFreRead.java
java  -classpath  $home/Program/ FilterLowAndHighFreRead $home/NormalReadPartitions/NormalReadSet_short1_left.fasta $home/NormalReadPartitions/NormalReadSet_short1_right.fasta  $home/NormalReadPartitions/ $Read_Average_length
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-8:Assembly"
echo  "---> SOAP2 after"
SOAPdenovo-127mer all -s $home/ConfigFile/after.lib -K $Soap2_kmerlength -R -o $home/AssemblyResults/SOAP2/after/after 1>$home/Log/SOAP2/Assembly_after 2>$home/Log/SOAP2/Assembly_Error_after
echo  "SOAP2 after Successful end!"
echo  "---> SOAP2 low"
SOAPdenovo-127mer pregraph -s $home/ConfigFile/low.lib -K $Soap2_lowdepthkmerlength -R -o $home/AssemblyResults/SOAP2/low/low 1>$home/Log/SOAP2/Assembly_low 2>$home/Log/SOAP2/Assembly_Error_low
SOAPdenovo-127mer contig -g $home/AssemblyResults/SOAP2/low/low -R 1>$home/Log/SOAP2/Assembly_low 2>$home/Log/SOAP2/Assembly_Error_low
echo  "SOAP2 low Successful end!"
echo  "---> SOAP2 high"
SOAPdenovo-127mer pregraph -s $home/ConfigFile/high.lib -K $Soap2_lowdepthkmerlength -R -o $home/AssemblyResults/SOAP2/high/high 1>$home/Log/SOAP2/Assembly_high 2>$home/Log/SOAP2/Assembly_Error_high
SOAPdenovo-127mer contig -g $home/AssemblyResults/SOAP2/high/high -R 1>$home/Log/SOAP2/Assembly_high 2>$home/Log/SOAP2/Assembly_Error_high
echo  "SOAP2 high Successful end!"
echo  "---> Merge contigs"
#Line Process.
javac  $home/Program/MutilLineProcess.java
java  -classpath  $home/Program/ MutilLineProcess $home/AssemblyResults/SOAP2/after+filter/ $home/AssemblyResults/SOAP2/after/after.contig  $home/AssemblyResults/SOAP2/low/low.contig  $home/AssemblyResults/SOAP2/high/high.contig 
#SOAP2 Scaffolding.
SOAPdenovo-fusion -p 1 -D -c $home/AssemblyResults/SOAP2/after+filter/contig.AfterMerge.fa -g $home/AssemblyResults/SOAP2/after+filter/afterfilter
SOAPdenovo-127mer  map -p 1 -s  $home/ConfigFile/before.lib  -g  $home/AssemblyResults/SOAP2/after+filter/afterfilter
SOAPdenovo-127mer  scaff -p 1 -g $home/AssemblyResults/SOAP2/after+filter/afterfilter
echo  "SOAP2 Scaffolding Successful end!"
#gapcloser
echo  "gapcloser begin!"
cd $home/GapCloser-bin-v1.12-r6
./GapCloser -a $home/AssemblyResults/SOAP2/after+filter/afterfilter.scafSeq  -b $home/ConfigFile/before.lib -o $home/AssemblyResults/SOAP2/after+filter/afterfilter2.scafSeq -p 13 -t 16 
echo  "gapcloser Successful end!"
#regenerate contig.
echo  "scaff2contig begin!"
cd $home/GapCloser-bin-v1.12-r6
perl scf2ctg.pl < $home/AssemblyResults/SOAP2/after+filter/afterfilter2.scafSeq > $home/AssemblyResults/SOAP2/after+filter/afterfilter2.contig
#gapcloser before.
./GapCloser -a $home/AssemblyResults/SOAP2/before/before.scafSeq  -b $home/ConfigFile/before.lib -o $home/AssemblyResults/SOAP2/before/before2.scafSeq -p 49 -t 16 
echo  "gapcloser Successful end!"
#regenerate contig.
echo  "scaff2contig begin!"
cd $home/GapCloser-bin-v1.12-r6
perl scf2ctg.pl < $home/AssemblyResults/SOAP2/before/before2.scafSeq > $home/AssemblyResults/SOAP2/before/before2.contig
#Abyss after.
cd $Abyss_bin
echo  "---> Abyss after"
abyss-pe k=$Abyss_k l=$Abyss_l n=$Abyss_n s=$Abyss_s name=$Abyss_name  in="$home/NormalReadPartitions/ReadSet_short1.Filter.fasta" > $home/Log/Abyss/Abyss_assembly_after.log
cp $Abyss_name-6.fa  contigs.fa
cp $Abyss_name-8.fa  scaffolds.fa
cp $Abyss_bin/contigs.fa $home/AssemblyResults/Abyss/after/
cp $Abyss_bin/scaffolds.fa $home/AssemblyResults/Abyss/after/
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
#End
echo  "Abyss after Successful end!"
#after end.
echo  "---> Abyss merge"
abyss-pe k=$Abyss_low_k l=$Abyss_l n=$Abyss_n s=$Abyss_s name=$Abyss_name  in="$home/NormalReadPartitions/AbnormalReadSet_short1.fasta" > $home/Log/Abyss/Abyss_assembly_merge.log
cp $Abyss_name-6.fa  contigs.fa
cp $Abyss_name-8.fa  scaffolds.fa
cp $Abyss_bin/contigs.fa $home/AssemblyResults/Abyss/filter/
cp $Abyss_bin/scaffolds.fa $home/AssemblyResults/Abyss/filter/
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
#End
echo  "Abyss Merge Successful end!"
#End
echo  "Abyss high Successful end!"
#high end.
echo  "---> Merge contigs"
javac  $home/Program/FilterIncludingRelationship_Modify_603.java
java  -classpath  $home/Program/ FilterIncludingRelationship_Modify_603 $home/AssemblyResults/Abyss/after+filter/ $home/AssemblyResults/Abyss/after/contigs.fa  $home/AssemblyResults/Abyss/filter/contigs.fa  $home/KmerFreStatics/LowFreKmer.short1.txt
echo  "---> Abyss Scaffolding"
#Start scaffold
lib1=$home/lib/$lib_left_name.corrected.fastq
lib2=$home/lib/$lib_right_name.corrected.fastq
cd $Abyss_bin/
cp $home/AssemblyResults/Abyss/after+filter/contigs.fa $Abyss_bin/
abyss-map $lib1 $lib2 contigs.fa | abyss-fixmate -h lib.0.hist | sort -snk3 -k4 | DistanceEst --dot --mean -k$Abyss_k -s$Abyss_s -n$Abyss_n -o lib.0.dist.dot lib.0.hist
abyss-scaffold -k$Abyss_k -s$Abyss_s -n$Abyss_n -g x-6.path.dot contigs.fa lib.0.dist.dot > x-6.path
PathConsensus -k$Abyss_k -p0.9 -s x-7.fa -g x-7.adj -o x-7.path contigs.fa contigs.fa x-6.path
cat contigs.fa x-7.fa | MergeContigs -k$Abyss_k -o scaffolds.fa - x-7.adj x-7.path
#cp
cp scaffolds.fa $home/AssemblyResults/Abyss/after+filter/
#delete
rm -rf $Abyss_bin/*.fa
rm -rf $Abyss_bin/*.dot
rm -rf $Abyss_bin/*.path
rm -rf $Abyss_bin/*.path1
rm -rf $Abyss_bin/*.path2
rm -rf $Abyss_bin/*.path3
rm -rf $Abyss_bin/*-stats
rm -rf $Abyss_bin/*.hist
rm -rf $Abyss_bin/*.adj
rm -rf $Abyss_bin/*.dist
rm -rf $Abyss_bin/contigs.fa
rm -rf $Abyss_bin/scaffolds.fa
#Add
rm -rf $Abyss_bin/*.tab
rm -rf $Abyss_bin/*.md
rm -rf $Abyss_bin/*.csv
rm -rf $Abyss_bin/*.fai
rm -rf $Abyss_bin/*.dot1
#End
echo  "Abyss Scaffolding Successful end!"

cd $home/GapCloser-bin-v1.12-r6
#gapcloser before.
./GapCloser -a $home/AssemblyResults/Abyss/before/scaffolds.fa  -b $home/ConfigFile/before.lib -o $home/AssemblyResults/Abyss/before/scaffolds2.fa -p 21 -t 16 
echo  "gapcloser Successful end!"
#regenerate contig.
echo  "scaff2contig begin!"
cd $home/GapCloser-bin-v1.12-r6
perl scf2ctg.pl < $home/AssemblyResults/Abyss/before/scaffolds2.fa > $home/AssemblyResults/Abyss/before/contigs2.fa

#gapcloser afterfilter.
./GapCloser -a $home/AssemblyResults/Abyss/after+filter/scaffolds.fa  -b $home/ConfigFile/before.lib -o $home/AssemblyResults/Abyss/after+filter/scaffolds2.fa -p 13 -t 16 
echo  "gapcloser Successful end!"
#regenerate contig.
echo  "scaff2contig begin!"
cd $home/GapCloser-bin-v1.12-r6
perl scf2ctg.pl < $home/AssemblyResults/Abyss/after+filter/scaffolds2.fa > $home/AssemblyResults/Abyss/after+filter/contigs2.fa

cd $home
echo  " "
echo  "*****************************************************************************"
echo  " "
echo  "*****************************************************************************"
echo  "Step-9:Quast test"
quast.py  $home/AssemblyResults/SOAP2/before/before2.contig  $home/AssemblyResults/SOAP2/after+filter/afterfilter2.contig   $home/AssemblyResults/SOAP2/before/before2.scafSeq    $home/AssemblyResults/SOAP2/after+filter/afterfilter2.scafSeq -R $home/Reference/$reference_name -o $home/FinalResults/SOAP2/Quast/
quast.py  $home/AssemblyResults/Abyss/before/contigs2.fa  $home/AssemblyResults/Abyss/after+filter/contigs2.fa   $home/AssemblyResults/Abyss/before/scaffolds2.fa  $home/AssemblyResults/Abyss/after+filter/scaffolds2.fa -R $home/Reference/$reference_name -o $home/FinalResults/Abyss/Quast/
echo  " "
echo  "*****************************************************************************"
####################################################################################
####################################################################################
####################################################################################























