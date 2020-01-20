/*
 * Kevin Markman-Lopez 
 * Sorts Project
 */

import java.util.Random;
import javax.swing.*;
public class sorts
{
private int  size;
private int[] randdata;
private int[] incrdata;
private int[] decrdata;

public int[] getranddata()
{ 
	return randdata; 
}
public int[] getincrdata()
{ 
	return incrdata; 
}
public int[] getdecrdata()
{ 
	return decrdata;
}

public sorts(int sz)
{
   size = sz;
   randdata = new int[size];
   incrdata = new int[size];
   decrdata = new int[size];
}
public void initialize()
{
   for(int i=0;i<size;i++)
   {
      randdata[i] = new Random().nextInt(size);
      incrdata[i] = i;
      decrdata[i] = size-i;
   }
      
}
public void list(int [] data) {
   for(int i=0; i<size; i++)
      System.out.print(" "+data[i]);
      System.out.println();
}
private void swapdata(int [] data, int i, int j)
{
   int temp = data[i]; data[i] = data[j]; data[j] = temp;
}

public int[] selectionsort(int [] data)
{
	//Initialize an array to keep track of sorts and swaps. 
	int[] counts = new int [2];
	int comparisons=0;
	int swaps= 0;
	int n = data.length;
	//parse though array
	for(int i =0;i<n;i++)
	{
		int min=i;
		//compare each element to min index 
		for(int j=i+1;j<n;j++)
		{
			comparisons++;
			//save new min index
			if(data[j]<data[min])
				min=j;
		}
		//swap only if a new min was found! 
		if(min!=i)
		{
		swapdata(data,min,i);
		swaps++;
		}
		
	}
	counts[0]=comparisons;
	counts[1]=swaps;
	return counts;
}       
public int[] insertionsort(int [] data)
{
	int counts[] = new int [2];
	int comparisons=0;
	int swaps =0;
	int n = data.length;
	//start from position i since insertion sort retroactively compares elements to see if current element
	//is indeed larger. 
	for(int i = 1; i<n;i++)
	{
		int key = data[i];
		int j = i-1;
		
		while(j>=0 && data[j]>key)
		{
			comparisons++;
			data[j+1]=data[j];
			j=j-1;
			
		}
		data[j+1]=key;
		swaps++;
	}
	counts[0]=comparisons;
	counts[1]=swaps;
	return counts;
}
public int [] mergeSort(int[] list)
{
	int counts[] = new int [2];

	//sends the list, first element, last element and count array to be partitioned
	mergesort(list,0,list.length-1,counts);

	return counts;
}

private void mergesort(int [] data, int low, int high, int []c)
{
	
	//Swaps = [0] comparisons = [1]
	if (low < high)
	{
		//finds the midpoint of the array(or subarray)
   	    int mid = (low + high)/2;
   	    
   	   //send the lower half of array(or subarray) 
   	    mergesort(data,low,mid,c);
   	    
   	  //sends the upper half of array(or subarray)
   	    mergesort(data,mid+1,high,c);
   	    
   	    //recursively merges the two halves
   	    merge(data,low,high,c);
		}

}
private void merge(int[] data, int low, int high,int [] count)
{
		
	int[] temp = copy(data);

	//sets the end points and midpoint of the new partions (or subarrays)
	int mid = (low + high)/2;
	int index1 = 0;
	int index2 = low;
	int index3 = mid + 1;

	//compares the two partition parts element by element and sorts them into the new array in the right 
	//order
	while (index2 <= mid && index3 <= high)
	{
		count[1]++;
		if (data[index2] < data[index3])
		{ 
			count[0]++;
			temp[index1] = data[index2];
			index1++;
			index2++;
			
		} else
		{
			temp[index1] = data[index3];
			count[0]++;
			index1++;
			index3++;
		
		}
	}

	//these while loops keep putting elements in place even if other array being merged has been emptied
	//into the merging array
	while (index2 <= mid)
	{
		temp[index1] = data[index2];
		count[0]++;
		index1++;
		index2++;
		
	}

	
	while (index3 <= high) 
	{
		temp[index1] = data[index3];
		count[0]++;
		index1++;
		index3++;
		
	}

	
	for (int i=low, j=0; i<=high; i++, j++)
	{
		data[i] = temp[j];
		count[0]++;
	}
}
public int[] copy(int[] list) {
	int[] temp = new int[list.length];
	for (int k=0; k<list.length; k++) {
			temp[k] = list[k];
		}
	return temp;
}

public int [] quicksort(int [] data)
{
	int [] quickCounts= new int [2];
	//quickCount[0] = comparisons quickCount[1]=swaps
	quickSort(data,0,data.length-1, quickCounts);
	
	return quickCounts; 
}

private void quickSort(int [] data, int low,int high, int [] qC)
{
	if (low < high)
	{
     int mid = partition(data,low,high,qC);
     quickSort(data,low,mid-1,qC);
     quickSort(data,mid+1,high,qC);
	}
}
private int partition(int[] list, int low, int high, int []qC) 
{
	//the pivot point is first item in the subarray
	int pivot = list[low];
	qC[1]++;

	
	do 
	{
		//are any elements smaller than the pivot point?
		while (low < high && list[high] >= pivot)
		{
				high--;
				qC[0]++;  
		}
		//yes-- swap
		if (low < high)
		{
			
			list[low] = list[high];
			qC[1]++;
			//search for a number larger than the pivot point
			while (low < high && list[low] <= pivot)
			{
	 		  	low++;
				qC[0]++;
			}
			if (low < high) 
			{
				//found: move into place and increment the counter
				list[high] = list[low];
				qC[1]++;
				
			}
		}   
	}
	//swap the pivot point into it's final spot
	while (low < high);
	list[low] = pivot;
	qC[1]++;
	

	return low;
	}
public int [] heapSort(int [] data)
{
	//heapCounts[0] = comparisons heapCounts[1] = swaps 
	int [] heapCounts = new int [2];
	heapsort(data,heapCounts);
	return heapCounts; 
}

private void heapsort(int [] data, int [] counts)
{
	int n = data.length; 

 
	//creates the heap. 
	for (int i = n / 2 - 1; i >= 0; i--) 
    heapify(data, n, i,counts); 


	for (int i=n-1; i>=0; i--) 
	{ 
    int temp = data[0]; 
    data[0] = data[i]; 
    data[i] = temp; 
    counts[1]++;
    //swaps the top of the heap with the last element, or final leaf in the tree, and sends the
    //reduced heap to be heapified again. 
    heapify(data, i, 0,counts); 
} 

}
private void heapify(int [] data, int n, int i, int[] counts)
{
	int topOfHeap = i;
    int left = 2*i + 1; //left child
    int right = 2*i + 2; //right child 

    
    counts[0]++;
    
  //The next three ifs go through the three elements that have been mathematically mapped out
   //as the parent, left, and right child and makes sure that the parent is larger than either child. 
    if (left < n && data[left] >data[topOfHeap])
    {
        topOfHeap = left; 
        
     }

     
    counts[0]++;
   
    if (right < n && data[right] > data[topOfHeap]) 
    {
    	topOfHeap = right;
    	
    }

   
    counts[0]++;
    if (topOfHeap != i) 
    { 
        int temp = data[i]; 
        data[i] = data[topOfHeap]; 
        data[topOfHeap] = temp; 
        counts[1]++;
        

        //heapifies the reduced heap
        heapify(data, n, topOfHeap,counts); 
    } 
}

public static void main(String args[])
{
   int datasize;
   datasize = Integer.parseInt(JOptionPane.showInputDialog("Enter Data Size:"));

   /*****************
    * SELECTION SORT*
    ****************/
   sorts selectionSort = new sorts(datasize);
   selectionSort.initialize();
   
   System.out.print("SELECTION SORT  (n ="+datasize+")\n");
   System.out.print("Unsorted Random array: " );
   selectionSort.list(selectionSort.getranddata());
   int [] randomCounts = selectionSort.selectionsort(selectionSort.getranddata());
   System.out.print("Sorted Random Array: ");
   selectionSort.list(selectionSort.getranddata());
   System.out.print("Comparisons: " +randomCounts[0] + " Swaps: " + randomCounts[1] + "\n");

   System.out.print("Unsorted Incremented Array: ");
   selectionSort.list(selectionSort.getincrdata());
   int [] incrementedCounts = selectionSort.selectionsort(selectionSort.getincrdata());
   System.out.print("Sorted Incremented Array: ");
   selectionSort.list(selectionSort.getincrdata());
   System.out.print("Comparisons: " + incrementedCounts[0] + " Swaps:" + incrementedCounts[1]);
   
   System.out.print("\nUnsorted Decremented Array: ");
   selectionSort.list(selectionSort.getdecrdata());
   int [] decrementedCounts = selectionSort.selectionsort(selectionSort.getdecrdata());
   System.out.print("Sorted Decremented Array: ");
   selectionSort.list(selectionSort.getdecrdata());
   System.out.print("Comparisons: " + decrementedCounts[0] + " Swaps:" + decrementedCounts[1]);
  
   /*****************
    * INSERTION SORT*
    ****************/
   
   System.out.println(" \n");
   sorts insertionSort = new sorts(datasize);
   insertionSort.initialize();
   
   System.out.print("INSERTION SORT (n= " + datasize+ ")\n");
   System.out.print("Unsorted Random Array: ");
   insertionSort.list(insertionSort.getranddata());
   int [] insertionRandomCounts = insertionSort.insertionsort(insertionSort.getranddata());
   System.out.print("Sorted Random Array: ");
   insertionSort.list(insertionSort.getranddata());
   System.out.print("Comparisons: "  + insertionRandomCounts[0] + " Swaps: " + insertionRandomCounts[1] );
   System.out.print("\nUnsortedIncrementedArray: ");
   insertionSort.list(insertionSort.getincrdata());
   int [] incrInsertionCounts = insertionSort.insertionsort(insertionSort.getincrdata());
   System.out.print("Sorted Incremented Array: ");
   insertionSort.list(insertionSort.getincrdata());
   System.out.print("Comparisons: "+ incrInsertionCounts[0] + " Swaps: " + incrInsertionCounts[1]);
   System.out.print("\nUnsorted Decremented Array: ");
   insertionSort.list(insertionSort.getdecrdata());
   int [] decrInsertionCounts = insertionSort.insertionsort(insertionSort.getdecrdata());
   System.out.print("Sorted Incremented Array: ");
   selectionSort.list(selectionSort.getdecrdata());
   System.out.print("Comparisons: " + decrInsertionCounts[0]+ " Swaps: " + decrInsertionCounts[1] );
   
   /***************
    * MERGE SORT *
    ***************/
  
   System.out.println("\n");
   
   sorts mergeSort = new sorts(datasize);
   mergeSort.initialize();
   System.out.print("\nMERGE SORT (n = " + datasize+ ")\n");
   System.out.print("Unsorted Random Merge: ");
   mergeSort.list(mergeSort.getranddata());
   int [] randomMergeCount = mergeSort.mergeSort(mergeSort.getranddata());
   System.out.print("Sorted Random Merge: ");
   mergeSort.list(mergeSort.getranddata());
   System.out.print("Comparisons: " + randomMergeCount[1] + " Swaps: " + randomMergeCount[0]);
   System.out.print("\nUnsorted Incremented Merge: ");
   mergeSort.list(mergeSort.getincrdata());
   int [] incrMerge = mergeSort.mergeSort(mergeSort.getincrdata());
   System.out.print("Sorted Incremented Merge: ");
   mergeSort.list(mergeSort.getincrdata());
   System.out.print("Comparisons: " + incrMerge[1] + " Swaps: " + incrMerge[0]);
   System.out.print("\nUnsorted Decremented Merge: ");
   mergeSort.list(mergeSort.getdecrdata());
   int [] decrMerge = mergeSort.mergeSort(mergeSort.getdecrdata());
   System.out.print("Sorted Decremented Merge: ");
   mergeSort.list(mergeSort.getdecrdata());
   System.out.print("Comparisons: " + decrMerge[1] + " Swaps: " + decrMerge[0] );
   
   /*************
    * Quick Sort*
    *************/
   System.out.println("\n");
   
   sorts qSort = new sorts(datasize);
   qSort.initialize();
   System.out.print("\nQUICK SORT (n="+ datasize+ ")\n");
   System.out.print("Unsorted Random Quick Sort:");
   qSort.list(qSort.getranddata());
   int [] randQSortCount = qSort.quicksort(qSort.getranddata());
   System.out.print("Sorted Random Quick Sort: ");
   qSort.list(qSort.getranddata());
   System.out.print("Comparisons: " + randQSortCount[0] + " Swaps: " + randQSortCount[1]);
   System.out.print("\nUnsorted Incremented Quick Sort: ");
   qSort.list(qSort.getincrdata());
   int [] incrQSortCount = qSort.getincrdata();
   System.out.print("Sorted Incremented Quick Sort: ");
   qSort.list(qSort.getincrdata());
   System.out.print("Comparisons: " + incrQSortCount[0] + " Swaps: " + incrQSortCount[1]);
   System.out.print("\nUnsorted Decremented Quick Sort: ");
   qSort.list(qSort.getdecrdata());
   int [] decrQSortCount = qSort.quicksort(qSort.getdecrdata());
   System.out.print("Sorted Decremented Quick Sort: ");
   qSort.list(qSort.getdecrdata());
   System.out.print("Comparisons:" + decrQSortCount[0] + " Swaps: " + decrQSortCount[1]);
   
   /************
    * HEAP SORT*
    ************/
   
   System.out.println("\n");
   sorts hSort = new sorts(datasize);
   hSort.initialize();
   System.out.print("\nHEAP SORT (n= " + datasize + ")\n");
   System.out.print("Unsorted Random Heap Sort:");
   hSort.list(hSort.getranddata());
   int [] hSortRandCount = hSort.heapSort(hSort.getranddata());
   System.out.print("Sorted Random Heap Sort:");
   hSort.list(hSort.getranddata());
   System.out.print("Comparisons: " + hSortRandCount[0]+ " Swaps: " + hSortRandCount[1]);
   System.out.print("\nUnsorted Incremented Heap Sort:");
   hSort.list(hSort.getincrdata());
   int [] incrHSortCount =hSort.heapSort(hSort.getincrdata()); 
   System.out.print("Sorted Incremented Heap Sort: ");
   hSort.list(hSort.getincrdata());
   System.out.print("Comparisons:" + incrHSortCount[0]+ " Swaps:" + incrHSortCount[1]);
   System.out.print("\nUnsorted Decremented Heap Sort:");
   hSort.list(hSort.getdecrdata());
   int [] decrHSortCount =hSort.heapSort(hSort.getincrdata()); 
   System.out.print("Sorted Decremented Heap Sort: ");
   hSort.list(hSort.getdecrdata());
   System.out.print("Comparisons:" + decrHSortCount[0]+ " Swaps:" + decrHSortCount[1]);
   
   
   
   
   
   
  }
}