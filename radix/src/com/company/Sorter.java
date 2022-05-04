package com.company;

public class Sorter {
    private static final int CUTOFF = 15;
    private static final int BITS_PER_INT = 32;
    private static final int BITS_PER_BYTE = 8;
    private static final int R = 256;

    public static void sort(int[] a){
        int firstPositiveIndex = partition(0, a, 0, a.length-1);// поиск негативного индекса
        int[] aux = new int[a.length];// временный буфер
        if(firstPositiveIndex>0){// если есть негативные числа
            recSort(a, firstPositiveIndex, a.length-1, 0,aux);// сортируем с негативного по последний
            recSort(a, 0, firstPositiveIndex-1, 0,aux);// сортиурем с 0 до негативного
        }else{//если только позитивные
            recSort(a, 0, a.length-1, 0, aux);
        }
    }

    private static void recSort(int[] array, int low, int high, int d, int[] aux){
        if(d>4)return;// проверка корректности массива
        if(high - low < CUTOFF){//простая вставка
            insert(array, low, high);
            return;
        }

        int[] count = new int[R+1];// кол-во каждого числа

        //compute counts
        int bitsToShift = BITS_PER_INT-BITS_PER_BYTE*d-BITS_PER_BYTE;
        int mask = 0b1111_1111;// расчет кол-ва чисел
        for(int i = low; i<= high; i++){
            int c = (array[i]>>bitsToShift) & mask;
            count[c+1]++;
        }

        for(int i = 0; i<R; i++){
            count[i+1]=count[i]+count[i+1];
        }

        for(int i = low; i<= high; i++){// запись в временный
            int c = (array[i]>>bitsToShift) & mask;
            aux[count[c]+ low] = array[i];
            count[c]++;
        }
        for(int i = low; i<= high; i++){// копирование из временного в нормальный
            array[i]=aux[i];
        }

        if(count[0]>0)// рекурсия
            recSort(array, low, low +count[0]-1, d+1, aux);
        for(int i = 1; i<R; i++){
            if(count[i]>0)
                recSort(array, low +count[i-1], low +count[i]-1, d+1, aux);
        }
    }

    // insertion sort array[lo..hi], starting at dth character
    private static void insert(int[] array, int low, int high) {
        for (int i = low; i <= high; i++)
            for (int j = i; j > low && array[j] < array[j-1]; j--)
                swap(array, j, j-1);
    }

    //returns the index of the partition or to the right of where it should be if the pivot is not in the array
    public static int partition(int pivot, int[] array, int low, int high){
        int curLo = low;
        int curHi = high;
        while(curLo<curHi){
            while(array[curLo]<pivot){
                if((curLo+1)>high)return high+1;
                curLo++;
            }

            while(array[curHi]>pivot){
                if((curHi-1)< low)return low -1;
                curHi--;
            }
            if(curLo<curHi){
                swap(array, curLo, curHi);
                if(array[curLo]!=pivot)curLo++;
                if(array[curHi]!=pivot)curHi--;
            }
        }
        return curLo;
    }

    private static void swap(int[] a, int first, int second){
        int t = a[first];
        a[first]=a[second];
        a[second]=t;
    }
}
