#!/bin/bash
echo  "*****************************************************************************"
echo "Copyright (C) 2017 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)"
echo "School of Information Science and Engineering"
echo "Central South University"
echo "ChangSha"
echo "CHINA, 410083"
echo  "*****************************************************************************"
echo  "Assembly parameters set by the user:"
TEST=$(cat ./config.txt)
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
       Read_shortjump_length=$var
       echo "Read_shortjump_length="$var
    fi
    if [ $i = 5 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Read_longjump_length=$var
       echo "Read_longjump_length="$var
    fi
    if [ $i = 8 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       bless_short_kmerlength=$var
       echo "bless_short_kmerlength="$var
    fi
    if [ $i = 10 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       bless_shortjump_kmerlength=$var
       echo "bless_shortjump_kmerlength="$var
    fi
    if [ $i = 12 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       bless_longjump_kmerlength=$var
       echo "bless_longjump_kmerlength="$var
    fi
    if [ $i = 15 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       dsk_kmerlength=$var
       echo "dsk_kmerlength="$var
    fi
    if [ $i = 17 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       home=$var
       echo "home="$var
    fi
    if [ $i = 19 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_bin=$var
       echo "Abyss_bin="$var
    fi
    if [ $i = 22 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Ave_insertsize1=$var
       echo "Ave_insertsize1="$var
    fi
    if [ $i = 24 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Ave_insertsize2=$var
       echo "Ave_insertsize2="$var
    fi
    if [ $i = 26 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Ave_insertsize3=$var
       echo "Ave_insertsize3="$var
    fi
    if [ $i = 29 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib1_left_name=$var
       echo "lib1_left_name="$var
    fi
    if [ $i = 31 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib1_right_name=$var
       echo "lib1_right_name="$var
    fi
    if [ $i = 33 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib2_left_name=$var
       echo "lib2_left_name="$var
    fi
    if [ $i = 35 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib2_right_name=$var
       echo "lib2_right_name="$var
    fi
    if [ $i = 37 ] 
    then
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib3_left_name=$var
       echo "lib3_left_name="$var
    fi
    if [ $i = 39 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       lib3_right_name=$var
       echo "lib3_right_name="$var
    fi
    if [ $i = 42 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Soap2_kmerlength=$var
       echo "Soap2_kmerlength="$var
    fi
    if [ $i = 44 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Soap2_lowdepthkmerlength=$var
       echo "Soap2_lowdepthkmerlength="$var
    fi
    if [ $i = 47 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_k=$var
       echo "Abyss_k="$var
    fi
    if [ $i = 49 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_low_k=$var
       echo "Abyss_low_k="$var
    fi
    if [ $i = 51 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_l=$var
       echo "Abyss_l="$var
    fi
    if [ $i = 53 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_n=$var
       echo "Abyss_n="$var
    fi
    if [ $i = 55 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_s=$var
       echo "Abyss_s="$var
    fi
    if [ $i = 57 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       Abyss_name=$var
       echo "Abyss_name="$var
    fi
    if [ $i = 60 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       dsk_bin=$var
       echo "dsk_bin="$var
    fi
    if [ $i = 62 ] 
    then 
       var=`echo "${arra[$i]}"|awk -F '=' '{print $2}' `
       reference_path=$var
       echo "reference_path="$var
    fi
done
#Generate Config files.
rm -rf Run-config.txt
if [ "$lib2_left_name" = "*" ] ;then
    string="#
Read_Average_length=$Read_Average_length
#
bless_kmerlength=$bless_short_kmerlength
#
dsk_kmerlength=$dsk_kmerlength
#
home=$home
#
Abyss_bin=$Abyss_bin
#
Ave_insertsize1=$Ave_insertsize1
#
lib1_left_name=$lib1_left_name
#
lib1_right_name=$lib1_right_name
#
Soap2_kmerlength=$Soap2_kmerlength
#
Soap2_lowdepthkmerlength=$Soap2_lowdepthkmerlength
#
Abyss_k=$Abyss_k
#
Abyss_low_k=$Abyss_low_k
#
Abyss_l=$Abyss_l
#
Abyss_n=$Abyss_n
#
Abyss_s=$Abyss_s
#
Abyss_name=$Abyss_name
#
dsk_bin=$dsk_bin
#
reference_path=$reference_path"
	 echo -e "$string" > Run-config.txt
     echo "shell-1 running"
     $home/shell/run-1.sh
elif [ "$lib2_left_name" != "*" ] && [ "$lib3_left_name" = "*" ] ;then
     
    string="#
Read_Average_length=$Read_Average_length
#
Read_jump_length=$Read_shortjump_length
#
bless_short_kmerlength=$bless_short_kmerlength
#
bless_jump_kmerlength=$bless_shortjump_kmerlength
#
dsk_kmerlength=$dsk_kmerlength
#
home=$home
#
Abyss_bin=$Abyss_bin
#
Ave_insertsize1=$Ave_insertsize1
#
Ave_insertsize2=$Ave_insertsize2
#
lib1_left_name=$lib1_left_name
#
lib1_right_name=$lib1_right_name
#
lib2_left_name=$lib2_left_name
#
lib2_right_name=$lib2_right_name
#
Soap2_kmerlength=$Soap2_kmerlength
#
Soap2_lowdepthkmerlength=$Soap2_lowdepthkmerlength
#
Abyss_k=$Abyss_k
#
Abyss_low_k=$Abyss_low_k
#
Abyss_l=$Abyss_l
#
Abyss_n=$Abyss_n
#
Abyss_s=$Abyss_s
#
Abyss_name=$Abyss_name
#
dsk_bin=$dsk_bin
#
reference_path=$reference_path"
	 echo -e "$string" > Run-config.txt
     echo "shell-2 running"
     $home/shell/run-2.sh

else
string="#
Read_short_length=$Read_Average_length
#
Read_shortjump_length=$Read_shortjump_length
#
Read_longjump_length=$Read_longjump_length
#
bless_short_kmerlength=$bless_short_kmerlength
#
bless_shortjump_kmerlength=$bless_shortjump_kmerlength
#
bless_longjump_kmerlength=$bless_longjump_kmerlength
#
dsk_kmerlength=$dsk_kmerlength 
#
home=$home
#
Abyss_bin=$Abyss_bin
#
Ave_insertsize1=$Ave_insertsize1 
#
Ave_insertsize2=$Ave_insertsize2
#
Ave_insertsize3=$Ave_insertsize3
#
lib1_left_name=$lib1_left_name
#
lib1_right_name=$lib1_right_name
#
lib2_left_name=$lib2_left_name
#
lib2_right_name=$lib2_right_name
#
lib3_left_name=$lib3_left_name
#
lib3_right_name=$lib3_right_name
#
Soap2_kmerlength=$Soap2_kmerlength               
#
Soap2_lowdepthkmerlength=$Soap2_lowdepthkmerlength
#
Abyss_k=$Abyss_k  
#
Abyss_low_k=$Abyss_low_k
#
Abyss_l=$Abyss_l  
#
Abyss_n=$Abyss_n
#
Abyss_s=$Abyss_s
#
Abyss_name=$Abyss_name
#
dsk_bin=$dsk_bin
#
reference_path=$reference_path"
	 echo -e "$string" > Run-config.txt
     echo "shell-3 running"
     $home/shell/run-3.sh
fi
rm -rf Run-config.txt
cp $home/AssemblyResults/SOAP2/before/before2.contig  $home/FinalResults/SOAP2/before/before2.contig 
cp $home/AssemblyResults/SOAP2/before/before2.scafSeq  $home/FinalResults/SOAP2/before/before2.scafSeq
#
cp $home/AssemblyResults/SOAP2/after+filter/afterfilter2.contig  $home/FinalResults/SOAP2/after+filter/afterfilter2.contig
cp $home/AssemblyResults/SOAP2/after+filter/afterfilter2.scafSeq  $home/FinalResults/SOAP2/after+filter/afterfilter2.scafSeq
#
cp $home/AssemblyResults/Abyss/before/contigs2.fa  $home/FinalResults/Abyss/before/contigs2.fa 
cp $home/AssemblyResults/Abyss/before/scaffolds2.fa  $home/FinalResults/Abyss/before/scaffolds2.fa
#
cp $home/AssemblyResults/Abyss/after+filter/contigs2.fa  $home/FinalResults/Abyss/after+filter/contigs2.fa 
cp $home/AssemblyResults/Abyss/after+filter/scaffolds2.fa  $home/FinalResults/Abyss/after+filter/scaffolds2.fa
