package org.skynet.allelicbalancechecker;

import java.io.File;
import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;

public class BalanceChecker {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		int[] arr = new int[101];
		
		VCFFileReader vcfrdr = new VCFFileReader(new File(args[0]));
		double total = 0.0;
		CloseableIterator<VariantContext> iter = vcfrdr.iterator();
		while(iter.hasNext())
		{
			VariantContext vc = iter.next();
			if(vc.isBiallelic() && vc.isSNP() && vc.getGenotype(0).isHet() && vc.getGenotype(0).getDP() >= 10 && vc.isNotFiltered())
			{	
				arr[(int) Math.round((100 * (double) vc.getGenotype(0).getAD()[1] / (double) vc.getGenotype(0).getDP()))]++;
				total++;
			}
		}
		
		iter.close();
		
		vcfrdr.close();
		
		for(int a : arr)
		{
			System.out.println(100 * a/total);
		}
		
		/*for(int x=0; x<arr.length; x++)
		{
			for(int y = 0; y<arr[x]; y++)
				System.out.print("*");
			System.out.println();
		}*/

	}

}
