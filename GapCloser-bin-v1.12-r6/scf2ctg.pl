#!/usr/bin/perl -w

use strict;

use AMOS::AmosFoundation;
use AMOS::AmosLib;
use AMOS::ParseFasta;

my $base = new AMOS::AmosFoundation();    
die("ERROR: problems creating an AMOS::Foundation object\n") unless($base);

my $HELP = q~
  Program that splits FASTA scaffolds into contigs.

  USAGE;
    scf2ctg.pl file [options]

  options:
    -minGap <n> : minimum numbers of consecutive N's used for a split; default=3
~;

$base->setHelpText($HELP);

###############################################################################
#             
# Main program       
#
###############################################################################

MAIN:         
{
	my $minGap=3;

	my $result = $base->getOptions(
		"minGap=i"	=> \$minGap,
	);
	
	####################		
		
        my $fh = new AMOS::ParseFasta(\*STDIN);
        $fh or $base->bail("Fasta format expected in $fh: $!\n");
	
	while (my ($head, $seq) = $fh->getRecord())
    	{       
		my @head=split /\s+/,$head;
	
		my $start=1;
		
		my @seq=();
		my @start=();
		my @end=();

		if($seq=~/N{$minGap,}/i)
		{
			while($seq) 
			{ 
				if($seq=~/^(N{$minGap,})(.*)/i)
				{
					$start+=length($1);
					$seq=$2;
				}			
				elsif($seq=~/^(.*?)(N{$minGap,})(.*)/i)
				{
					$seq=$1;

					push @seq,$seq;
					push @start,$start;
					push @end,$start+length($seq)-1;

					$start+=length($1)+length($2);
					$seq=$3;
				}
				elsif(length($seq))
                                {
					push @seq,$seq;
					push @start,$start;
					push @end,$start+length($seq)-1;								                      						
					
					$seq="";
				}
			}
		}
		elsif(length($seq))
		{
			push @seq,$seq;
			push @start,1;
			push @end,$start+length($seq)-1;
			
			$seq="";
		}

		
		my $n=scalar(@seq);
		foreach my $i (0..$n-1)
		{
			print ">",$head[0],".",$i+1," ",$head[0]," ",$start[$i]-1," ",$end[$i]," f\n",
				$seq[$i],"\n";
		}
	}
		        					
	exit 0;
}

