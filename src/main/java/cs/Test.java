package cs;

import cs.graph.DS;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    private static int sum;

    static  class Person{
        int amount;
        String id;
         Person(int amount, String id){
             this.amount = amount;
             this.id = id;
         }

        public String getId() {
            return id;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return id + " (" + amount + ")";
        }
    }
    public static void main(String[] args) {

        // binarySearch();
        // array();
        //   dp();
         graph();
         //  tree();
         // list();
    }

    private static void Javas8Func() {
        List<Person> list = new ArrayList<>();
        list.add(new Person(10,"a_chandra"));
        list.add(new Person(10,"chandra"));
        list.add(new Person(50,"shekhar"));
        list.add(new Person(80,"ijit"));
        list.add(new Person(90,"dev"));

        Collections.sort(list, Comparator
                .comparing(Person::getAmount)
                .thenComparing(Person::getId));

        List<String> collect = list.stream()
                .sorted(Comparator.comparingInt(Person::getAmount).reversed()
                        .thenComparing(Person::getId))
                .limit(14)
                .map(x -> x.getId() + "(" + x.getAmount() + ")")
                .collect(Collectors.toList());

        System.out.println(collect);
    }

    private static void array() {
        int a3[] = {-2,-2,-2,-1,-1,-1,0,0,0,2,2,2,2};
        int a[][] = {{1,3},{2,6},{8,9},{9,11},{8,10},{2,4},{15,18},{16,17}};
        int[][] grid = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}
        };

        //3,1,2,2,31
        //-1,-5,-4
        int n1 = grid.length;
        int m1 = grid[0].length;
        int n=a.length;
        //[[-2, 0, 2], [-1, -1, 2], [0, 0, 0]]
               //   2+ 5+ 7+ 7
        int b1[] = {40, 25, 19, 12,9,6,2};
        int b2[] = {1, 2, 3, 1, 1, 1, 1,3,3};
        System.out.println( maxSubArraySumLenUsing2Pointer(b2.length,6, b2));

    }

    private static int reversePairs(int i, int j, int[] a) {
        int count=0;
        if(i>=j) return 0 ;
        int mid = i + j >> 1;
        count+=reversePairs(i,mid,a);
        count+=reversePairs(mid+1,j,a);
        count+=mergeAndCountReversePairs(i,mid,mid+1,j,a);
        return count;
    }

    private static int mergeAndCountReversePairs(int start, int mid, int left, int right, int[] a) {
        int []temp=new int[right-start+1];
        int ind=0 ,initialStart=start;
        //all are index
        while (start<=mid && left<=right){
            if(a[start]<a[left]){
                temp[ind]=a[start];start++;ind++;
            }
            else {
                temp[ind]=a[left]; left++;ind++;
            }
        }

        while (start<=mid) { temp[ind]=a[start];start++;ind++;}
        while (left<=right) { temp[ind]=a[left];left++;ind++;}

        for (int i = initialStart; i <=right ; i++) {
            a[i]=temp[i-initialStart];
        }
        return countPairDoubleOfSecond(mid,right,a);
    }

    private static int countPairDoubleOfSecond(int mid, int right, int[] a) {
        int count=0,j=0;
        for (int i = 0; i <= mid ; i++) {
            while (j<=right && (a[i]> 2*a[j])){
                j++;
            }
            //its already incremented so not need to do j+1
            count+=j;
            //  System.out.println(a[i]+",j:"+j+",total:"+count);
        }
        return count;
    }
    private static int countInversionInArray(int i, int j, int[] a) {
        int count=0;
        if(i>=j) return count;
        int mid = i + j >> 1;
        count+=countInversionInArray(i,mid,a);
        count+=countInversionInArray(mid+1,j,a);
        count+=mergeAndCountInVersion(i,mid,mid+1,j,a);
        return count;
    }

    private static int mergeAndCountInVersion(int start, int mid, int left, int right, int[] a) {
        int []temp=new int[right-start+1];
        int ind=0 ,initialStart=start , count=0 ;
        //all are index
        while (start<=mid && left<=right){
            if(a[start]<=a[left]){
                temp[ind]=a[start];start++;ind++;
            }
            //right is smaller
            else {
                count+=mid-start+1;
                temp[ind]=a[left]; left++;ind++;
            }
        }

        while (start<=mid) { temp[ind]=a[start];start++;ind++;}
        while (left<=right) { temp[ind]=a[left];left++;ind++;}

        for (int i = initialStart; i <=right ; i++) {
            a[i]=temp[i-initialStart];
        }
        return count;
    }

    private static void mergeSort(int i, int j, int[] a) {
        if(i>=j) return;
        int mid = i + j >> 1;
        mergeSort(i,mid,a);
        mergeSort(mid+1,j,a);
        merge(i,mid,mid+1,j,a);
    }

    private static void merge(int start, int mid, int left, int right, int[] a) {
        int []temp=new int[right-start+1];
        int ind=0 ,initialStart=start;
        //all are index
        while (start<=mid && left<=right){
            if(a[start]<a[left]){
                temp[ind]=a[start];start++;ind++;
            }
            else {
                temp[ind]=a[left]; left++;ind++;
            }
        }

        while (start<=mid) { temp[ind]=a[start];start++;ind++;}
        while (left<=right) { temp[ind]=a[left];left++;ind++;}

        for (int i = initialStart; i <=right ; i++) {
            a[i]=temp[i-initialStart];
        }

    }

    private static int maxProductSubArray(int n, int[] a) {
        int prefix=1, suffix=1 ,ans=0;
        for (int i = 0; i < n ; i++) {
            if(a[i]==0) prefix=1;
            if(a[n-i-1]==0) suffix=1;
            prefix*=a[i];
            suffix*=a[n-i-1];
            ans=Math.max(ans,Math.max(prefix,suffix));
        }
        return ans;
    }

    private static int[] missingAndDuplicateXor(int n, int[] a) {
        int xor=0;

        for (int i = 0; i < n ; i++) {
            xor^=a[i];
            xor^=(i+1);
        }

        //find first set bit position
        // int number = xor & ~(xor-1);
        // replacing 1<<bitNo with number generated above also works
        int bitNo= 0;
        while (true){
            if((xor & (1<<bitNo))!=0) break;
            else bitNo++;
        }

        int zero=0,one=0;
        //give two number
        for (int i = 0; i < n; i++) {
            if((a[i] & (1<<bitNo) )==0){
              zero^=a[i];
            }
            else  one^=a[i];
        }

        for (int i = 1; i <= n; i++) {
            if((i & (1<<bitNo) )==0){
                zero^=i;
            }
            else  one^=i;
        }
        int count=0;
        for (int i = 0; i < n; i++) {
            if(a[i]==zero) count++;
        }
        //zero is duplicate
        if(count ==2)
            //pair of duplicate and missing return
            return new int[]{zero,one};
        return new int[]{one,zero};
    }

    private static int[] missingAndDuplicate(int n, int[] a) {
        int tempSum=0,Sn= n*(n+1)/2;
        int tempSqSum=0,S2n= (n*(n+1)*(2*n+1))/6;
        for (int i = 0; i < n; i++) {
            tempSum+=a[i];
            tempSqSum+=a[i]*a[i];
        }
        //x is repeating , y is duplicate
        int val1 = Sn-tempSum; //x-y
        int val2 = S2n - tempSqSum ; // x^2-y^2

         int x_plus_y=val2/val1;

         int x=(val1+x_plus_y)/2; int y = x_plus_y - x;
         return new int[] {x,y};
    }

    private static void mergeSortedListWithoutExtraSpaceUsingGapMethod(int n, int m, int[] a, int[] b) {
        int len = n + m;
        int gap = (len / 2) + (len % 2);
        int start = 0, end = start + gap;
        while (gap >0) {
            while (end < len) {
                //   System.out.println("gap:"+gap);
                // both is in first array
                if (end < n) {
                    if (a[start] > a[end]) swap(start, end, a, a);
                }
                //both is in second array
                else if (start >= n) {
                    if (b[start - n] > b[end - n]) swap(start - n, end - n, b, b);
                } //b/w a & b array
                else {
                    //    System.out.println("start:"+start+",end:"+end);
                    if (a[start] > b[end - n]) swap(start, end - n, a, b);
                }
                start++;
                end++;
            }
            //gap 1 is already processed break now
            if (gap == 1) return;
            gap = (gap / 2) + (gap % 2);
            start = 0;
            end = start + gap;
        }
    }

    private static void mergeSortedListWithoutExtraSpace(int n, int m, int[] a, int[] b) {
        int aptr=n-1 , btpr=0;

        while (aptr>=0  && btpr<m){
            if(a[aptr]>b[btpr]){
                swap(aptr,btpr,a,b);
                aptr--; btpr++;
            }
            else break;
        }
        Arrays.sort(a);
        Arrays.sort(b);
    }

    private static void swap(int aptr, int btpr, int[] a, int[] b) {
        a[aptr]^=b[btpr];
        b[btpr]^=a[aptr];
        a[aptr]^=b[btpr];
    }


    private static List<List<Integer>> mergeIntervala(int n, int[][] a) {
        Arrays.sort(a, Comparator.comparingInt((int[] b) -> b[0])
                .thenComparingInt(b -> b[1]));

        List<List<Integer>> ans=new LinkedList<>();
        for (int i = 0; i < n; i++) {
             if(ans.isEmpty() || ans.get(ans.size()-1).get(1)<a[i][0]){
                ans.add(Arrays.stream(a[i]).boxed().collect(Collectors.toList()));
            }
           else {
                 List<Integer> ints = ans.get(ans.size() - 1);
                 ints.set(1, Math.max(ints.get(1), a[i][1]));
            }
        }
        return ans;
    }

    private static int noOfSubArrayWithXORK(int n, int k, int[] a) {

        int count=0;
        //Xor, count
        Map<Integer,Integer> map=new HashMap<>();
        int xR=0;
        map.put(xR,1);
        for (int i = 0; i < n ; i++) {
            xR ^=a[i];
            count+=map.getOrDefault( xR ^ k,0);

            map.merge(xR,1,Integer::sum);
        }
        return count;
    }

    private static List<List<Integer>> sum4(int n, int[] a, int target) {
        Arrays.sort(a);
        List<List<Integer>> ans=new LinkedList<>();
        for (int i = 0; i < n ; i++) {
            if(i>0 && a[i-1]==a[i]) continue;
            for (int j = i+1; j < n ; j++) {
                //not first element then only check
                if(j>i+1 && a[j-1]==a[j]) continue;
                int k=j+1,l=n-1;
                while (k < l ) {
                    int sum = a[i] + a[j] + a[k] + a[l];
                    if (sum == target ) {
                        ans.add(List.of(a[i],  a[j],a[k], a[l]));
                        k++;l--;
                        while (k<l && a[k-1]==a[k])k++;
                        while (k<l && a[l]==a[l+1])l--;
                    } else if (sum > target) l--;
                    else k++;
                }
            }
        }
        return ans;
    }

    private static List<List<Integer>> sum3(int n, int[] a) {
        Arrays.sort(a);
        List<List<Integer>> ans=new LinkedList<>();
        for (int i = 0; i < n ; i++) {
            if(i>0 && a[i-1]==a[i]) continue;
            int j=i+1,k=n-1;
            while (j < k ) {
                int sum = a[i] + a[j] + a[k];
                if (sum == 0 ) {
                    ans.add(List.of(a[i], a[j], a[k]));
                    j++;k--;
                    while (j<k && a[j-1]==a[j])j++;
                    while (j<k && a[k]==a[k+1])k--;
                } else if (sum > 0) k--;
                else j++;
            }
        }
        return ans;
    }

    private static Set<List<Integer>> sum3Brute2(int n, int[] a) {
        Arrays.sort(a);
        Set<List<Integer>> ans=new LinkedHashSet<>();
        for (int i = 0; i < n ; i++) {
            Set<Integer> kSet=new HashSet<>();
            for (int j = i+1; j < n ; j++) {
                int ijSum = a[i] + a[j];
                if(kSet.contains(-ijSum)){
                    List<Integer> list = Arrays.asList(a[i], a[j], -ijSum);
                    Collections.sort(list);
                    ans.add(list);
                }
                kSet.add(a[j]);
            }
        }
        return ans;
    }

    private static List<Integer> majorityElement2(int n, int[] a) {
        int countRequired=n/3+1;
        int num1 = 0,count1 = 0;
        int num2 = 0,count2 = 0;
        for (int i = 0; i < n; i++) {
            if(count1==0 && num2!=a[i]){
                num1=a[i];
                count1++;
            } else if (count2==0 && num1!=a[i]) {
                num2=a[i];
                count2++;
            }
            else if(a[i]==num1) count1++;
            else if(a[i]==num2) count2++;
            else {
                count1--;
                count2 --;
            }
        }
        count1=0;count2=0;
        for (int i = 0; i <n ; i++) {
            if(a[i]==num1) count1++;
            if(a[i]==num2) count2++;
        }
        List<Integer> ans=new ArrayList<>();
        if(count1>=countRequired) ans.add(num1);
        if(count2>=countRequired) ans.add(num2);

        return ans;

    }

    //print n row
    private static List<List<Integer>> generatePascalTrianglePrintWithRow(int n) {
        List<List<Integer>> ans=new ArrayList<>();
        for (int row = 1; row <=n ; row++) {
            ans.add(pascalTrianglePrintRowOptimized(row));
        }
        return ans;
    }

    // print nth row
    private static List<Integer> pascalTrianglePrintRowOptimized(int row) {
        int ithRow=1;
        List<Integer> ans=new ArrayList<>();
        ans.add(1);
        for (int col = 1; col < row; col++) {
            ithRow*=row-col;
            ithRow/= col;
            ans.add(ithRow);
        }
        return ans;
    }

    private static void pascalTrianglePrintRowBruteForce(int n) {

        for (int col = 1; col <=n ; col++) {
            System.out.println(pascalTriangleRowAndCol(n,col));
        }
    }

    // row start with number 1 and column starts with number 1 while finding
    private static int pascalTriangleRowAndCol(int row, int col) {
        return nCr(row-1,col-1);
    }

    // 10C3
    private static int nCr(int n, int r) {
        int res=1;
        for (int i = 0; i < r; i++) {
            res*=n-i;
            res/=(i+1);
        }
        return  res;
    }

    // TODO: Revise this
    private static int countSubarraySumEqualsK(int n, int k, int[] a) {
        //sum,count
        Map<Integer,Integer> map =new HashMap<>();
        int prefixSum=0 , count=0;
        map.put(0,1);
        for (int i = 0; i < n; i++) {
            prefixSum+=a[i];
            if(map.containsKey(prefixSum - k)){
                count+=map.get(prefixSum-k);
            }
            map.put(prefixSum,1);
        }
        return count;
    }

    private static boolean spiralMatrix(int n, int m, int[][] grid) {
        int top = 0, bottom = n - 1;
        int left = 0, right = m - 1;
        while (top<=bottom && left<=right) {

            for (int i = left; i <= right; i++) {
                System.out.println(grid[top][i]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                System.out.println(grid[i][right]);
            }
            right--;

            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    System.out.println(grid[bottom][i]);
                } bottom--;
            }


            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.println(grid[i][left]);
                } left++;
            }

        }  return true;
    }

    private static int[][] rotateMatrix(int n, int m, int[][] grid) {
        for (int i = 0; i < n-1 ; i++) {
            for (int j = i+1; j < m ; j++) {
                int temp=grid[i][j];
                grid[i][j] = grid[j][i];
                grid[j][i]=temp;
            }
        }

        for (int i = 0; i < n; i++) {
            reverse(grid[i],0,m-1);
        }

        return grid;
    }

    private static int[][] rotateMatrixBrtueForce(int n, int m, int[][] grid) {
        int[][] ans= new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                ans[j][n-1-i]= grid[i][j];
            }
        }
        return ans;
    }

    private static int[][] setMatrixzeroBruteOptimal(int n, int m, int[][] grid) {

        int colFirt=0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                if(grid[i][j]==0){
                    //row update
                    grid[i][0]=0;

                    //col update
                    if(j!=0)grid[0][j]=0;
                    else if(j==0) colFirt=0;
                }
            }
        }

        for (int i = 1; i < n ; i++) {
            for (int j = 1; j < m; j++) {
                if(grid[i][j]==1 && (grid[0][j]==0 || grid[i][0]==0)){
                    grid[i][j]=0;
                }
            }
        }

        // row fill
        if(grid[0][0]==0){
            for (int j = 0; j < m ; j++) {
                grid[0][j]= 0;
            }
        }

        //col fill
        if(colFirt==0){
            for (int i = 0; i < n ; i++) {
                grid[i][0]= 0;
            }
        }
        return grid;
    }
    private static int[][] setMatrixzeroBetter(int n, int m, int[][] a) {
        boolean visrow[]=new boolean [n];
        boolean viscol[]=new boolean[m];
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(a[i][j]==0){
                    visrow[i]=true;
                    viscol[j]=true;
                }
            }
        }

        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(a[i][j]==1 && (visrow[i]||viscol[j])){
                    a[i][j]=0;
                }
            }
        }
        return a;
    }

    private static int[][] setMatrixzeroBruteForce(int n, int m, int[][] a) {

        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(a[i][j]==0){
                    for (int col = 0; col < m ; col++) {
                        if(a[i][col]!=0)a[i][col]=-1;
                    }
                    for (int row = 0; row < n ; row++) {
                        if(a[row][j]!=0) a[row][j]=-1;
                    }
                }
            }
        }

        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(a[i][j]==-1){
                    a[i][j]=0;
                }
            }
        }

        return a;
    }

    private static int longestConsecutiveSequenceUsingSet(int n, int[] a) {
        Set<Integer> set=new HashSet<>();
        int  ans = 0;
        for (int i = 0; i < n ; i++) {
            set.add(a[i]);
        }
        for (int i = 0; i < n; i++) {
            int count=1;
            int key=a[i];
            if (!set.contains(key-1)){
                while (set.contains(key)){
                    key++;count++;
                }
                ans=Math.max(ans,count);
            }
        }
        return ans;
    }
    private static int longestConsecutiveSequenceUsingSorting(int n, int[] a) {
        Arrays.sort(a);
        int prevMax=Integer.MIN_VALUE , count=0 ,ans = 0;
        for (int i = 0; i < n; i++) {
            if(prevMax+1==a[i]){
                count++;
                prevMax=a[i];
            }else if(prevMax!=a[i]) {
                prevMax=a[i];
                count=1;
            }
            ans=Math.max(ans,count);
        }
        return ans;
    }

    private static List<Integer> leader(int ind, int[] a) {
        int maxi=Integer.MIN_VALUE;
        List<Integer> ans=new ArrayList<>();
        for (int i = ind; i >=0 ; i--) {
            if(a[i]>maxi) ans.add(a[i]);
            maxi=Math.max(maxi,a[i]);
        }
        return ans;
    }

    private static int[] nextPermutation(int n, int[] a) {
        int index=-1;
        for (int i = n; i >=1 ; i--) {
            if(a[i-1] < a[i]){ index=i-1;break; }
        }

        if(index==-1) { reverse(a,0,n); return  a;}

        int minGreaterIndex= index+1;
        for (int i = index +1; i <=n ; i++) {
            if(a[i]>a[index] &&  a[i]<a[minGreaterIndex] ){
                minGreaterIndex=i;
            }
        }
        swap(index,minGreaterIndex,a);
        reverse(a,index+1,n);
        return a;
    }

    private static List<Integer> intersection(int n1, int n2, int[] a, int[] b) {
        int i=0, j=0;
        List<Integer> ans=new ArrayList<>();
        while (i<n1 && j<n2){
            if(a[i]==b[j]){
                 ans.add(a[i]);
                i++; j++;
            }
            else if(a[i]<b[j]){
                i++;
            }
            else j++;
        }

        return ans;
    }

    private static List<Integer> union(int n1, int n2, int[] a, int[] b) {
        int i=0, j=0;
        List<Integer> ans=new ArrayList<>();
        while (i<n1 && j<n2){
            int size = ans.size();
            if(a[i]<b[j]){
                if(size==0 || ans.get(size -1)!=a[i]) ans.add(a[i]);
                i++;
            }
            else {
                if(size==0 || ans.get(size -1)!=b[j]) ans.add(b[j]);
                j++;
            }
        }

        while (i<n1) {
            if (ans.get(ans.size() - 1) != a[i]) ans.add(a[i]);
            i++;
        }

        while (j<n2) {
            if(ans.get( ans.size() -1)!=a[j]) ans.add(b[j]);
            j++;
        }
        return ans;
    }

    private static void moveZeroToEnd(int n, int[] a) {
        int i = -1;
        for (int j = 0; j < n; j++) {
            if (a[j] == 0) {
                i = j;
                break;
            }
        }

        for (int j = i + 1; j < n; j++) {
            if (a[j] != 0) {
                swap(i, j, a);
                i++;
            }
        }
        System.out.println(Arrays.toString(a));

    }

    private static void swap(int i, int j, int[] a) {
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }

    private static void leftRotateByDPlace(int n, int d, int[] a) {
             reverse(a,0,d-1);
             reverse(a,d,n-1);
             reverse(a,0,n-1);
        System.out.println(Arrays.toString(a));
    }

    private static void reverse(int[] a, int start, int end) {
        while (start<end){
            int temp=a[start];
            a[start]=a[end];
            a[end]=temp;
            start++;end--;
        }
    }


    private static void dp() {
        int[][] aa = {
                {1,0,1,0,0},
                {1,0,1,1,1},
                {1,1,1,1,1},
                {1,0,0,1,0}
        };


        int wt[] = {2, 4, 6};
        int val[] = {5, 11, 13};

        int m=aa.length,m1=aa[0].length;

        String s="bababcbadcede";
        String t="";

        int n1=s.length();
        int n2=t.length();
        int K=2;
        int transactionFee=2;
        int a[] = {3,1,5,6,2,3};
        int a3[] = {3, 1, 5, 8};
        String[] s1={"a","b","ba","bca","bda","bdca"};
        int n = a.length;
        int [][] dpp = new int [s.length()+2][s.length()+2];
        int dp[] = new int[a.length+1];
        int[] updated = new int[a.length + 2];
        System.arraycopy(a, 0, updated, 1, a.length); // Insert the original elements
        updated[0] = 1 ;
        updated[updated.length - 1] = 1; // Manually set the last element to 7
       // System.out.println(Arrays.toString(updated));
        System.out.println(maximumRectangleAreaWithall1s(m,m1,aa));
    }

    // DP - 56
    private static int maximumRectangleAreaWithall1s(int m, int n, int[][] a) {

        int maxi= largestAreaHistogram(n,a[0]);
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                if(a[i][j]!=0) a[i][j]+=a[i-1][j];
                else a[i][j]=0;
            }
            int curHistogramMaxValue=largestAreaHistogram(n,a[i]);
            maxi=Math.max(maxi,curHistogramMaxValue);
        }
        return maxi;
    }

    private static int largestAreaHistogram(int n, int[] a) {

        //Index
        Stack<Integer> stack=new Stack<>();
        stack.add(0);
        int maxi=0;
        for (int i = 1; i <= n ; i++) {
            while (!stack.isEmpty() && (i==n || a[stack.peek()]>a[i])){
                int rs_index = stack.pop();
                Integer width;
                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width =  i - stack.peek()  - 1;
                }
                maxi=Math.max(maxi,a[rs_index]*width);
            }
            stack.add(i);
        }
        return maxi;
    }

    private static int countNumberOfSquares(int m, int n, int[][] a) {
        for (int i = 1; i < m; i++) {
            for (int j = 1; j <n ; j++) {
                if(a[i][j]==1) {
                    a[i][j] = Math.min(a[i - 1][j], Math.min(a[i - 1][j - 1], a[i][j - 1]))+1;
                }
            }
        }

        return Arrays.stream(a).flatMapToInt(IntStream::of).sum();
    }

    private static int partitionArrayForMaxSumBottomUP(int i1, int n, int k, int[] a, int[] dp) {

        for (int i = n; i >=0 ; i--) {
            if(i==n) continue;
            // if(dp[i]!=0) return 0;
            int maxiSum=Integer.MIN_VALUE;
            int maxVal=Integer.MIN_VALUE , len=0;
            // {1,15,7,9,2,5,10};
            for (int j = i; j < Math.min(i+k,n) ; j++) {
                maxVal =Math.max(maxVal,a[j]);len++;
                int ithStep= len*maxVal+dp[j+1];
                maxiSum =Math.max(maxiSum,ithStep);
            }
             dp[i]=maxiSum;
        }
        return dp[i1];

    }
    private static int partitionArrayForMaxSum(int i, int n, int k, int[] a, int[] dp) {
        if(i==n) return 0;
       // if(dp[i]!=0) return 0;
        int maxiSum=Integer.MIN_VALUE;
        int maxVal=Integer.MIN_VALUE , len=0;
        // {1,15,7,9,2,5,10};
        for (int j = i; j < Math.min(i+k,n) ; j++) {
          maxVal =Math.max(maxVal,a[j]);len++;
          int ithStep= len*maxVal+partitionArrayForMaxSum(j+1,n,k,a, dp);
          maxiSum =Math.max(maxiSum,ithStep);
        }
        return dp[i]=maxiSum;
    }

    private static int partitionArrayForMaxSum(int i, int n, int k, int[] a) {
        if(i==n) return 0;
        int maxiSum=Integer.MIN_VALUE;
        int maxVal=Integer.MIN_VALUE , len=0;
        // {1,15,7,9,2,5,10};
        for (int j = i; j < Math.min(i+k,n) ; j++) {
            maxVal =Math.max(maxVal,a[j]);len++;
            int ithStep= len*maxVal+partitionArrayForMaxSum(j+1,n,k,a);
            maxiSum =Math.max(maxiSum,ithStep);
        }
        return maxiSum;
    }

    //minPallindromePartition2(0,s.length() ,s.toCharArray())-1
    private static int minPallindromePartition2BottomUp(int i1, int n, char[] s, int[] dp) {

        for (int i = n; i >=0 ; i--) {
            if(i==n) continue;
            int minPartion=Integer.MAX_VALUE;
            for (int j = i; j <n ; j++) {
                int ithPartition=Integer.MAX_VALUE;
                if(isPallindrome(i,j,s)){
                    ithPartition=1+dp[j+1];
                }
                minPartion=Math.min(minPartion,ithPartition);
            }
             dp[i]=minPartion;

        }
        return dp[i1];

    }

    //minPallindromePartition2(0,s.length() ,s.toCharArray(),dp)-1
    //front partition
    private static int minPallindromePartition2(int i, int n, char[] s, int[] dp) {
        if(i==n) return 0;
        if(dp[i]!=0) return dp[i];
        int minPartion=Integer.MAX_VALUE;
        for (int j = i; j <n ; j++) {
            int ithPartition=Integer.MAX_VALUE;
            if(isPallindrome(i,j,s)){
                ithPartition=1+minPallindromePartition2(j+1, n, s, dp);
            }
            minPartion=Math.min(minPartion,ithPartition);
        }
        return dp[i]=minPartion;
    }

    //minPallindromePartition2(0,s.length() ,s.toCharArray())-1
    private static int minPallindromePartition2(int i, int n, char[] s) {
        if(i==n) return 0;
        int minPartion=Integer.MAX_VALUE;
        for (int j = i; j <n ; j++) {
            int ithPartition=Integer.MAX_VALUE;
            if(isPallindrome(i,j,s)){
                ithPartition=1+minPallindromePartition2(j+1, n, s);
            }
            minPartion=Math.min(minPartion,ithPartition);
        }
        return minPartion;
    }

    private static boolean isPallindrome(int i, int j, char[] s) {
        if(i==j) return true;
        while (i<j){
            if(s[i]!=s[j]) return false;
            i++;j--;
        }
        return true;
    }

    // evaluateToTrue(0,s.length()-1, 1, s.toCharArray())
    private static int evaluateToTrueBottomUp(int i1, int j1, int isTrue, char[] a, int[][] dpp) {

        for (int i = j1; i >=0 ; i--) {
            for (int j = 0; j <=j1 ; j++) {

                if (i > j) continue;
                if (i == j) {
                    if (isTrue == 1)  dpp[i][j]= a[i] == 'T' ? 1 : 0;
                    else  dpp[i][j]= a[i] == 'F' ? 1 : 0;
                }

                int ways = 0;
                for (int ind = i + 1; ind <= j - 1; ind += 2) {
                    int lt = evaluateToTrue(i, ind - 1, 1, a, dpp);
                    int lf = evaluateToTrue(i, ind - 1, 0, a, dpp);
                    int rt = evaluateToTrue(ind + 1, j, 1, a, dpp);
                    int rf = evaluateToTrue(ind + 1, j, 0, a, dpp);

                    if (a[ind] == '&') {
                        if (isTrue == 1) {
                            ways += lt & rt;
                        } else {
                            ways += lf & rf;
                        }
                    } else if (a[ind] == '|') {
                        if (isTrue == 1) {
                            ways += lt * rt + lf * rt + rf * lt;
                        } else {
                            ways += lf * rf;
                        }
                    } else if (a[ind] == '^') {
                        if (isTrue == 1) {
                            ways += lf * rt + rf * lt;
                        } else {
                            ways += lf * rf + rt * lt;
                        }
                    }
                }
                 dpp[i][j]=ways;
            }
        }

        return dpp[i1][j1];
    }

    // evaluateToTrue(0,s.length()-1, 1, s.toCharArray())
    private static int evaluateToTrue(int i, int j, int isTrue, char[] a, int[][] dpp) {
        if (i > j) return 0;
        if(dpp[i][j]!=0) return dpp[i][j];
        if (i == j) {
            if (isTrue == 1) return a[i] == 'T' ? 1 : 0;
            else return a[i] == 'F' ? 1 : 0;
        }

        int ways = 0;
        for (int ind = i + 1; ind <= j - 1; ind += 2) {
            int lt = evaluateToTrue(i, ind - 1, 1, a, dpp);
            int lf = evaluateToTrue(i, ind - 1, 0, a, dpp);
            int rt = evaluateToTrue(ind + 1, j, 1, a, dpp);
            int rf = evaluateToTrue(ind + 1, j, 0, a, dpp);

            if (a[ind] == '&') {
                if (isTrue == 1) {
                    ways += lt & rt;
                } else {
                    ways += lf & rf;
                }
            } else if (a[ind] == '|') {
                if (isTrue == 1) {
                    ways += lt * rt + lf * rt + rf * lt;
                } else {
                    ways += lf * rf;
                }
            } else if (a[ind] == '^') {
                if (isTrue == 1) {
                    ways += lf * rt + rf * lt;
                } else {
                    ways += lf * rf + rt * lt;
                }
            }
        }
        return dpp[i][j]=ways;
    }

    private static int evaluateToTrue(int i, int j, int isTrue, char[] a) {
        if (i > j) return 0;
        if (i == j) {
            if (isTrue == 1) return a[i] == 'T' ? 1 : 0;
            else return a[i] == 'F' ? 1 : 0;
        }

        int ways = 0;
        for (int ind = i + 1; ind <= j - 1; ind += 2) {
            int lt = evaluateToTrue(i, ind - 1, 1, a);
            int lf = evaluateToTrue(i, ind - 1, 0, a);
            int rt = evaluateToTrue(ind + 1, j, 1, a);
            int rf = evaluateToTrue(ind + 1, j, 0, a);

            if (a[ind] == '&') {
                if (isTrue == 1) {
                    ways += lt & rt;
                } else {
                    ways += lf & rf;
                }
            } else if (a[ind] == '|') {
                if (isTrue == 1) {
                    ways += lt * rt + lf * rt + rf * lt;
                } else {
                    ways += lf * rf;
                }
            } else if (a[ind] == '^') {
                if (isTrue == 1) {
                    ways += lf * rt + rf * lt;
                } else {
                    ways += lf * rf + rt * lt;
                }
            }
        }
        return ways;
    }


    //TODO : not giving correct value
    private static int burstBallonBottomUPSpaceOptimized(int i1, int j1, int[] a, int[][] dpp) {
        int [] prev = new int [j1+2];
        int [] cur = new int [j1+2];

        for (int i =j1 ; i >=1 ; i--) {
            for (int j = 0; j <=j1 ; j++) {
                if(i>j) cur[j]= 0;
                else {
                    int maxValue=Integer.MIN_VALUE;
                    for (int ind = i; ind <=j ; ind++) {
                        int ithValue =a[i-1]*a[ind]*a[j+1]+cur[ind-1]+prev[j];
                        maxValue=Math.max(maxValue,ithValue);
                    }
                    cur[j]=maxValue;
                } } prev=cur;
        }
        return prev[j1];
    }

    private static int burstBallonBottomUP(int i1, int j1, int[] a, int[][] dpp) {

        for (int i =j1 ; i >=1 ; i--) {
            for (int j = 0; j <=j1 ; j++) {
                if(i>j) dpp[i][j]= 0;
                else {
                int maxValue=Integer.MIN_VALUE;
                for (int ind = i; ind <=j ; ind++) {
                    int ithValue =a[i-1]*a[ind]*a[j+1]+dpp[i][ind-1]+dpp[ind+1][j];
                    maxValue=Math.max(maxValue,ithValue);
                }
                 dpp[i][j]=maxValue;
            } }
        }
        return dpp[i1][j1];
    }

    private static int burstBallon(int i, int j, int[] a, int[][] dpp) {
        if(i>j) return 0;
        if(dpp[i][j]!=0) return dpp[i][j];
        int maxValue=Integer.MIN_VALUE;
        for (int ind = i; ind <=j ; ind++) {
            int ithValue =a[i-1]*a[ind]*a[j+1]+burstBallon(i,ind-1,a, dpp)+burstBallon(ind+1,j,a, dpp);
            maxValue=Math.max(maxValue,ithValue);
        }
        return dpp[i][j]=maxValue;
    }

    private static int burstBallon(int i, int j, int[] a) {
        if(i>j) return 0;
        int maxValue=Integer.MIN_VALUE;
        for (int ind = i; ind <=j ; ind++) {
            int ithValue =a[i-1]*a[ind]*a[j+1]+burstBallon(i,ind-1,a)+burstBallon(ind+1,j,a);
            maxValue=Math.max(maxValue,ithValue);
        }
        return maxValue;
    }
    private static int minStickCostBottomUP(int i1, int j1, int[] cuts, int[][] dpp) {

        for (int i = cuts.length-1; i >=1 ; i--) {
            for (int j = 0; j <= j1 ; j++) {
                if(i>j)  dpp[i][j]= 0;
                else {
                int mini=Integer.MAX_VALUE;
                for (int ind = i; ind <=j ; ind++) {
                    int ithCost=cuts[j+1]-cuts[i-1]+dpp[i][ind-1]+dpp[ind+1][j];
                    mini=Math.min(mini,ithCost);
                }
                 dpp[i][j]=mini;
            }
            }
        }
        return dpp[i1][j1];

    }
    //sorting array will make it indedpentd
    private static int minStickCost(int i, int j, int[] cuts, int[][] dpp) {
        if(i>j) return 0;
        if(dpp[i][j]!=0) return dpp[i][j];
        int mini=Integer.MAX_VALUE;
        for (int ind = i; ind <=j ; ind++) {
            int ithCost=cuts[j+1]-cuts[i-1]+minStickCost(i,ind-1,cuts,dpp)+minStickCost(ind+1,j,cuts,dpp);
            mini=Math.min(mini,ithCost);
        }
        return dpp[i][j]=mini;
    }
    private static int minStickCost(int i, int j, int[] cuts) {
        if(i>j) return 0;
        int mini=Integer.MAX_VALUE;
        for (int ind = i; ind <=j ; ind++) {
            int ithCost=cuts[j+1]-cuts[i-1]+minStickCost(i,ind-1,cuts)+minStickCost(ind+1,j,cuts);
            mini=Math.min(mini,ithCost);
        }
        return mini;
    }

    //TODO: issue in mcmBottomUPSpaceOptimized changing j=0 to j = i+1 worked
    private static int mcmBottomUPSpaceOptimized(int i1, int j1, int[] a, int[][] dpp) {
        int [] prev = new int [a.length];
        int [] cur = new int [a.length];

        for (int i = a.length; i >=1 ; i--) {
            for (int j = i+1; j <=a.length-1 ; j++) {
                if(i==j)  cur[j1]= 0;
                else {
                    int minSteps=Integer.MAX_VALUE;
                    for (int k = i; k <j ; k++) {
                        int kthSteps=a[i-1]*a[k]*a[j]+cur[k]+prev[j];
                        minSteps=Math.min(minSteps,kthSteps);
                    }
                    cur[j]=minSteps;
                }
            } cur=prev;
        }
        return cur[j1];

    }
    private static int mcmBottomUP(int i1, int j1, int[] a, int[][] dpp) {

        for (int i = a.length; i >=1 ; i--) {
            for (int j = 0; j <=a.length-1 ; j++) {
                if(i==j)  dpp[i1][j1]= 0;
                else {
                int minSteps=Integer.MAX_VALUE;
                for (int k = i; k <j ; k++) {
                    int kthSteps=a[i-1]*a[k]*a[j]+dpp[i][k]+dpp[k+1][j];
                    minSteps=Math.min(minSteps,kthSteps);
                }
                 dpp[i][j]=minSteps;
                }
            }
        }
        return dpp[i1][j1];

    }
    private static int mcm(int i, int j, int[] a, int[][] dpp) {
        if(i==j) return 0;
        if( dpp[i][j]!=0) return  dpp[i][j];
        int minSteps=Integer.MAX_VALUE;
        for (int k = i; k <j ; k++) {
            int kthSteps=a[i-1]*a[k]*a[j]+mcm(i,k,a, dpp)+mcm(k+1,j,a, dpp);
            minSteps=Math.min(minSteps,kthSteps);
        }
        return dpp[i][j]=minSteps;
    }

    private static int mcm(int i, int j, int[] a) {
        if(i==j) return 0;
        int minSteps=Integer.MAX_VALUE;
        for (int k = i; k <j ; k++) {
            int kthSteps=a[i-1]*a[k]*a[j]+mcm(i,k,a)+mcm(k+1,j,a);
            minSteps=Math.min(minSteps,kthSteps);
        }
        return minSteps;
    }

    private static int lisCount(int n, int[] a) {

        int [] dpLen=new int[n];
        int [] count=new int[n];
        Arrays.fill(dpLen,1);
        Arrays.fill(count,1);
        int max=0 , maxIndex=-1;
        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[prev] < a[i]) {
                    if(dpLen[prev]+1 == dpLen[i]){
                        count[i]+=count[prev];
                    }
                   else {
                        dpLen[i]=dpLen[prev]+1;
                        count[i]=count[prev];
                    }
                }
                if(max<dpLen[i]){
                    max=dpLen[i];
                    maxIndex=i;
                }
            }
        }

        int totalSum = 0;
        //do sum of all index having maximum count
        //max len lis count
        for (int i = 0; i < a.length; i++) {
            if (dpLen[i] == max) totalSum += count[i];
        }
        return totalSum;
    }

    private static int longestBiotonicSubSequence(int n, int[] a) {

        int [] dp=new int[n];
        Arrays.fill(dp,1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && dp[i] < 1 + dp[j]) {
                    dp[i] = 1 + dp[j];
                }
            }
        }

        int [] dp2=new int[n];
        Arrays.fill(dp2,1);
        for (int i = n-2; i >=0; i--) {
            for (int prev = n-1; prev > i; prev--) {
                if (a[prev] < a[i] && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                }
            }
        }

        int ans=0;
        for (int i = 0; i <n ; i++) {
            ans=Math.max(ans,dp[i]+dp2[i]-1);
        }
        return ans;
    }

    // code is same as printLis , change only isDiffBy1 method and sorting
    private static List<String> longestStringChain(int n, String [] a) {
        Arrays.sort(a , Comparator.comparingInt(String::length));
        int [] dp=new int[n];
        int [] hashIndex=new int[n];
        Arrays.fill(dp,1);
        int maxLen=0, lastIndex=0;
        for (int i = 1; i < n; i++) {
            hashIndex[i] = i;
            for (int j = 0; j < i; j++) {
                if (isDiffBy1(a[i],a[j]) && dp[i] < 1 + dp[j]) {
                    dp[i] = 1 + dp[j];
                    hashIndex[i] = j;
                }
                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    lastIndex = i;
                }
            }
        }

        List<String> lisString=new ArrayList<>();

        lisString.add(a[lastIndex]);
        while (hashIndex[lastIndex]!=lastIndex){
            lastIndex=hashIndex[lastIndex];
            lisString.add(a[lastIndex]);
        }
        Collections.reverse(lisString);

       /* System.out.println(lisString);
        System.out.println(Arrays.toString(dp));
        System.out.println(Arrays.toString(hashIndex));*/
        return lisString;
    }

    private static boolean isDiffBy1(String s, String t) {
        if(s.length()!=t.length()+1) return false;
        int first=0,second=0;
        //note this loop , had caused issue
        while (second<t.length()){
            if(s.charAt(first)==t.charAt(second)){
                first++;second++;
            }
            else first++;
        }
        if(first==s.length() && second==t.length()) return true;
        else return false;
    }

    // or lds
    // code is same as printLis , change only is > to %
    private static List<Integer> largestDivisibleSubset(int n, int[] a) {
        //sorting will take care of prev cases
        Arrays.sort(a);
        int [] dp=new int[n];
        int [] hashIndex=new int[n];
        Arrays.fill(dp,1);
        int maxLen=0, lastIndex=0;
        for (int i = 1; i < n; i++) {
            hashIndex[i] = i;
            for (int j = 0; j < i; j++) {
                if (a[i]%a[j]==0 && dp[i] < 1 + dp[j]) {
                    dp[i] = 1 + dp[j];
                    hashIndex[i] = j;
                }
                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    lastIndex = i;
                }
            }
        }

        List<Integer> lisString=new ArrayList<>();

        lisString.add(a[lastIndex]);
        while (hashIndex[lastIndex]!=lastIndex){
            lastIndex=hashIndex[lastIndex];
            lisString.add(a[lastIndex]);
        }
        Collections.reverse(lisString);

       /* System.out.println(lisString);
        System.out.println(Arrays.toString(dp));
        System.out.println(Arrays.toString(hashIndex));*/
        return lisString;
    }

    //DP -43
    private static int lisBinarySearch(int n, int[] a) {
        List<Integer> lis =new ArrayList<>();
        lis.add(a[0]);
        for (int i = 1; i < n; i++) {
            if(a[i]>lis.get(lis.size()-1)){
                lis.add(a[i]);
            }
            else {
                int[] a1 = lis.stream().mapToInt(i1 -> i1).toArray();
                int lowerBound=lowerBound(a1.length,a[i], a1);
                lis.set(lowerBound,a[i]);
            }
        }
        return lis.size();
    }

    //   5,4,11,1,16,8
    private static List<Integer> printLis(int n, int[] a) {
        int [] dp=new int[n];
        int [] parent=new int[n];
        Arrays.fill(dp,1);
        int maxLen=0, lastIndex=0;
        for (int i = 1; i < n; i++) {
            parent[i] = i;
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && dp[i] < 1 + dp[j]) {
                    dp[i] = 1 + dp[j];
                    parent[i] = j;
                }
                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    lastIndex = i;
                }
            }
        }

        List<Integer> lisString=new ArrayList<>();

        lisString.add(a[lastIndex]);
        while (lastIndex!= parent[lastIndex]){
            lastIndex=parent[lastIndex];
            lisString.add(a[lastIndex]);
        }
        Collections.reverse(lisString);

       /* System.out.println(lisString);
        System.out.println(Arrays.toString(dp));
        System.out.println(Arrays.toString(parent));*/
        return lisString;
    }

    private static int lisAnotherWay(int ind , int n, int[] a) {
        int [] dp=new int[n];
        Arrays.fill(dp,1);
        int maxi=0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (a[i] > a[j]) {
                    dp[i] = 1 + dp[j];
                    maxi = Math.max(maxi, dp[i]);
                }
            }
        }
        return maxi;
    }
    private static int lisBottomUPSpaceOptimized(int i1, int prevIndex1, int[] a) {
        int [] prev = new int [a.length+1];
        int [] cur = new int [a.length+1];
        for (int i = a.length-1; i >=0 ; i--) {
            for (int prevIndex = a.length-1; prevIndex >=-1 ; prevIndex--) {
                int not_take=0+prev[prevIndex+1];
                int take=0;
                //changed i to i+1 since prevIndex was stored as prevIndex + 1
                if(prevIndex==-1 || a[i]>a[prevIndex]){
                    take=1+prev[i+1];
                }
                cur[prevIndex+1]=Math.max(take,not_take);
            } prev=cur;
        }
        return prev[prevIndex1+1];
    }
    private static int lisBottomUP(int i1, int prevIndex1, int[] a, int[][] dpp) {
        for (int i = a.length-1; i >=0 ; i--) {
            for (int prevIndex = a.length-1; prevIndex >=-1 ; prevIndex--) {
                    int not_take=0+dpp[i+1][prevIndex+1];
                    int take=0;
                    //changed i to i+1 since prevIndex was stored as prevIndex + 1
                    if(prevIndex==-1 || a[i]>a[prevIndex]){
                        take=1+dpp[i+1][i+1];
                    }
                    dpp[i][prevIndex+1]=Math.max(take,not_take);
                }
        }
        return dpp[i1][prevIndex1+1];
    }

    private static int lis(int i, int prevIndex, int[] a, int[][] dpp) {
        if(i>=a.length) return 0;
        if(dpp[i][prevIndex+1]!=0) return dpp[i][prevIndex+1];
        int not_take=0+lis(i+1,prevIndex,a, dpp);
        int take=0;
        if(prevIndex==-1 || a[i]>a[prevIndex]){
            take=1+lis(i+1,i,a, dpp);
        }
        return dpp[i][prevIndex+1]=Math.max(take,not_take);
    }

    private static int lis(int i, int prev, int[] a) {
        if(i>=a.length) return 0;
        int not_take=0+lis(i+1,prev,a);
        int take=0;
        if(prev==-1 || a[i]>prev){
            take=1+lis(i+1,a[i],a);
        }
        return Math.max(take,not_take);
    }
    private static int stock6WithTransactionFee(int i, int buy, int n, int[] a, int[][] dpp, int transactionFee) {
        if(i>=n) return 0;
        if(dpp[i][buy]!=0) return dpp[i][buy];
        if(buy==1)
            return dpp[i][buy]=Math.max(-a[i]+ stock6WithTransactionFee(i+1,0, n, a, dpp, transactionFee),0+ stock6WithTransactionFee(i+1,1, n, a, dpp, transactionFee));
        else
            return dpp[i][buy]=Math.max(a[i]-transactionFee+ stock6WithTransactionFee(i+1,1, n, a, dpp, transactionFee),0+ stock6WithTransactionFee(i+1,0, n, a, dpp, transactionFee));
    }
    private static int stock5(int i, int buy, int n, int[] a, int[][] dpp) {
        if(i>=n) return 0;
        if(dpp[i][buy]!=0) return dpp[i][buy];
        if(buy==1)
            return dpp[i][buy]=Math.max(-a[i]+stock5(i+1,0, n, a, dpp),0+stock5(i+1,1, n, a, dpp));
        else
            return dpp[i][buy]=Math.max(a[i]+stock5(i+2,1, n, a, dpp),0+stock5(i+1,0, n, a, dpp));
    }

    private static int stock5(int i, int buy, int n, int[] a) {
        if(i>=n) return 0;
        if(buy==1)
            return Math.max(-a[i]+stock5(i+1,0, n, a),0+stock5(i+1,1, n, a));
        else
            return Math.max(a[i]+stock5(i+2,1, n, a),0+stock5(i+1,0, n, a));
    }

    private static int stockTxnNumberBottomUPSpaceOptimized(int i1, int txnNumber1, int K, int[] a) {
        int [] prev = new int [K*2+1];
        int [] cur = new int [K*2+1];
        for (int i = a.length - 1; i >= i1; i--) {
            for (int txnNumber = K * 2 - 1; txnNumber >= 0; txnNumber--) {
                if (txnNumber % 2 == 0)
                    cur[txnNumber] = Math.max(-a[i] + prev[txnNumber + 1], 0 + prev[txnNumber]);

                else cur[txnNumber] = Math.max(a[i] + prev[txnNumber + 1], 0 + prev[txnNumber]);
            } prev=cur;
        }
        return prev[txnNumber1];
    }
    private static int stockTxnNumberBottomUP(int i1, int txnNumber1, int K, int[] a, int[][] dpp) {

        for (int i = a.length - 1; i >= i1; i--) {
            for (int txnNumber = K * 2 - 1; txnNumber >= 0; txnNumber--) {
                if (txnNumber % 2 == 0)
                    dpp[i][txnNumber] = Math.max(-a[i] + dpp[i + 1][txnNumber + 1], 0 + dpp[i + 1][txnNumber]);

                else dpp[i][txnNumber] = Math.max(a[i] + dpp[i + 1][txnNumber + 1], 0 + dpp[i + 1][txnNumber]);
            }
        }
        return dpp[i1][txnNumber1];
    }
    private static int stockTxnNumber(int i, int txnNumber, int K, int[] a, int[][] dpp) {
        if(txnNumber == K*2 ) return 0;
        if(i==a.length) return 0;
        if(dpp[i][txnNumber]!=0) return dpp[i][txnNumber];
        if(txnNumber%2==0)
            return dpp[i][txnNumber]=Math.max(-a[i]+stockTxnNumber(i+1,txnNumber+1,K, a,dpp),0+stockTxnNumber(i+1,txnNumber,K, a,dpp));

        return dpp[i][txnNumber]=Math.max(a[i]+stockTxnNumber(i+1,txnNumber+1,K, a,dpp),0+stockTxnNumber(i+1,txnNumber,K, a,dpp));
    }
    private static int stockTxnNumber(int i, int txnNumber, int K, int[] a) {
        if(txnNumber == K*2 ) return 0;
        if(i==a.length) return 0;
        if(txnNumber%2==0)
            return Math.max(-a[i]+stockTxnNumber(i+1,txnNumber+1,K, a),0+stockTxnNumber(i+1,txnNumber,K, a));

        return Math.max(a[i]+stockTxnNumber(i+1,txnNumber+1,K, a),0+stockTxnNumber(i+1,txnNumber,K, a));
    }

    //change cap1 to K , that is only change
    private static int stock4WithCapBottomUpSpaceOptimzed(int i1, int buy1, int cap1, int n, int[] a, int[][][] dpp) {
        int [][] prev = new int [2][cap1+1];
        int [][] cur = new int [2][cap1+1];
        for (int i = n-1; i >=i1 ; i--) {
            for (int buy = 0; buy <=1 ; buy++) {
                for (int cap = 0; cap <=cap1 ; cap++) {
                    if(cap==0) cur[buy][cap] = 0;
                    else if(buy==1)
                        cur[buy][cap]=Math.max(-a[i]+prev[0][cap],prev[1][cap]);
                    else
                        cur[buy][cap]=Math.max(a[i]+prev[1][cap-1],prev[0][cap]);
                }
            } prev=cur;
        }
        return prev[buy1][cap1];
    }
    private static int stock3WithCapBottomUpSpaceOptimzed(int i1, int buy1, int cap1, int n, int[] a, int[][][] dpp) {
        int [][] prev = new int [2][cap1+1];
        int [][] cur = new int [2][cap1+1];
        for (int i = n-1; i >=i1 ; i--) {
            for (int buy = 0; buy <=1 ; buy++) {
                for (int cap = 0; cap <=cap1 ; cap++) {
                    if(cap==0) cur[buy][cap] = 0;
                    else if(buy==1)
                        cur[buy][cap]=Math.max(-a[i]+prev[0][cap],prev[1][cap]);
                    else
                        cur[buy][cap]=Math.max(a[i]+prev[1][cap-1],prev[0][cap]);
                }
            } prev=cur;
        }
        return prev[buy1][cap1];
    }
    private static int stock3WithCapBottomUp(int i1, int buy1, int cap1, int n, int[] a, int[][][] dpp) {

        for (int i = n-1; i >=i1 ; i--) {
            for (int buy = 0; buy <=1 ; buy++) {
                for (int cap = 0; cap <=cap1 ; cap++) {
                    if(cap==0) dpp[i][buy][cap] = 0;
                    else if(buy==1)
                        dpp[i][buy][cap]=Math.max(-a[i]+dpp[i+1][0][cap],dpp[i+1][1][cap]);
                    else
                     dpp[i][buy][cap]=Math.max(a[i]+dpp[i+1][1][cap-1],dpp[i+1][0][cap]);
                }
            }
        }
        return dpp[i1][buy1][cap1];
    }
    private static int stock3WithCap(int i, int buy, int cap, int n, int[] a, int[][][] dpp) {
        if(cap==0) return 0;
        if(i==n) return 0;
        if(dpp[i][buy][cap]!=0) return dpp[i][buy][cap];
        if(buy==1)
           return dpp[i][buy][cap]=Math.max(-a[i]+stock3WithCap(i+1,0,cap,n,a,dpp),stock3WithCap(i+1,1,cap,n,a,dpp));

       return dpp[i][buy][cap]=Math.max(a[i]+stock3WithCap(i+1,1,cap-1,n,a,dpp),stock3WithCap(i+1,0,cap,n,a,dpp));
    }

    private static int stock3WithCap(int i, int buy, int cap, int n, int[] a) {
        if(cap==0) return 0;
        if(i==n) return 0;
        if(buy==1)
            return Math.max(-a[i]+stock3WithCap(i+1,0,cap,n,a),stock3WithCap(i+1,1,cap,n,a));

        return Math.max(a[i]+stock3WithCap(i+1,1,cap-1,n,a),stock3WithCap(i+1,0,cap,n,a));
    }

    private static int stock2BottomUpSpaceOptimized(int i1, int buy1, int n, int[] a, int[][] dpp) {
        int [] prev = new int [2];
        int [] cur = new int [2];
        for (int i = n-1; i >=i1 ; i--) {
            for (int buy = 0; buy <=buy1 ; buy++) {
                if (buy == 1)
                    cur[buy] = Math.max(-a[i] + prev[ 0], prev[ 1]);
                else
                    cur[buy] = Math.max(a[i] + prev [1], prev [0]);
            } prev=cur;
        }
        return prev[buy1];
    }
    private static int stock2BottomUp(int i1, int buy1, int n, int[] a, int[][] dpp) {

        for (int i = n-1; i >=i1 ; i--) {
            for (int buy = 0; buy <=buy1 ; buy++) {
                     if (buy == 1)
                        dpp[i][buy] = Math.max(-a[i] + dpp[i + 1][ 0], dpp[i + 1][ 1]);
                    else
                        dpp[i][buy] = Math.max(a[i] + dpp[i + 1] [1], dpp[i + 1] [0]);
            }
        }
        return dpp[i1][buy1];
    }
    private static int stock2(int i, int buy, int n, int[] a, int[][] dpp) {
        if(i==n) return 0;
        int profit;
        if(dpp[i][n]!=0) return dpp[i][n];
        if(buy==1)
            dpp[i][n]=profit=Math.max(-a[i]+stock2(i+1,0, 1, a, dpp),0+stock2(i+1,1, 1, a, dpp));
        else
            dpp[i][n]=profit=Math.max(a[i]+stock2(i+1,1, 1, a, dpp),0+stock2(i+1,0, 1, a, dpp));

        return profit;
    }

    private static int stock2(int i, int buy, int n, int[] a) {
        if(i==n) return 0;
        int profit=Integer.MIN_VALUE;
        if(buy==1)
            profit=Math.max(-a[i]+stock2(i+1,0, 1, a),0+stock2(i+1,1, 1, a));
        else
            profit=Math.max(a[i]+stock2(i+1,1, 1, a),0+stock2(i+1,0, 1, a));

        return profit;
    }

    private static int editDistanceBottomUPSpaceOptimized(int i1, int j1, char[] s, char[] t) {
        int[] prev = new int[j1+1];
        for (int i = 0; i <=i1 ; i++) {
            int[] cur = new int[j1+1];
            for (int j = 0; j <=j1 ; j++) {
                if(i==0) cur[j]= j;
                else if(j==0)  cur[j]= i;
                else if(s[i-1]==t[j-1]) {
                    cur[j]=prev[j-1]; }
                else {
                    int insert=1+cur[j-1];
                    int replace=1+prev[j-1];
                    int delete=1+prev[j-1];
                    cur[j]=Math.min(insert,Math.min(replace,delete));
                }
            } prev=cur;
        }
        return prev[j1];
    }
    private static int editDistanceBottomUP(int i1, int j1, char[] s, char[] t, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0) dpp[i][j]= j;
                else if(j==0)  dpp[i][j]= i;
                else if(s[i-1]==t[j-1]) {
                     dpp[i][j]=dpp[i-1][j-1]; }
                else {
                int insert=1+dpp[i][j-1];
                int replace=1+dpp[i-1][j-1];
                int delete=1+dpp[i-1][j-1];
                 dpp[i][j]=Math.min(insert,Math.min(replace,delete));
                }
            }
        }
        return dpp[i1][j1];
    }
    private static int editDistance(int i, int j, char[] s, char[] t, int[][] dpp) {
        if(i==0) return j;
        if(j==0) return i;
        if(dpp[i][j]!=0)return dpp[i][j];
        if(s[i-1]==t[j-1])
            return dpp[i][j]=editDistance(i-1,j-1,s,t, dpp);

        int insert=1+editDistance(i,j-1,s,t, dpp);
        int replace=1+editDistance(i-1,j-1,s,t, dpp);
        int delete=1+editDistance(i-1,j-1,s,t, dpp);
        return dpp[i][j]=Math.min(insert,Math.min(replace,delete));
    }

    private static int editDistance(int i, int j, char[] s, char[] t) {
        if(i<0) return j+1;
        if(j<0) return i+1;
        if(s[i]==t[j])
            return editDistance(i-1,j-1,s,t);

        int insert=1+editDistance(i,j-1,s,t);
        int replace=1+editDistance(i-1,j-1,s,t);
        int delete=1+editDistance(i-1,j,s,t);
        return Math.min(insert,Math.min(replace,delete));
    }

    //TODO: lean 1D optimization
    private static int countDistinctSubSequenceBottomUpSpaceOptimized1D(int i1, int j1, char[] s, char[] t) {
        int[]prev = new int[j1+1];
        int[] cur = new int[j1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int j = j1; j >=0 ; j--) {
                if(j==0) prev[j]= 1;
                else if(i==0) prev[j]= Integer.MIN_VALUE;
                else {
                    //match
                    if(s[i-1]==t[j-1])
                        prev[j]=prev[j-1]+ prev[j];
                }
            }
        }
        return prev[j1];
    }
    private static int countDistinctSubSequenceBottomUpSpaceOptimized(int i1, int j1, char[] s, char[] t) {
        int[]prev = new int[j1+1];
        int[] cur = new int[j1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(j==0) cur[j]= 1;
                else if(i==0) cur[j]= Integer.MIN_VALUE;
                else {
                    //match
                    if(s[i-1]==t[j-1])
                        cur[j]=prev[j-1]+ prev[j];
                    else cur[j]=prev[j];
                }
            } prev=cur;
        }
        return prev[j1];

    }

    private static int countDistinctSubSequenceBottomUp(int i1, int j1, char[] s, char[] t, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(j==0) dpp[i][j]= 1;
                else if(i==0) dpp[i][j]= Integer.MIN_VALUE;
                else {
                //match
                if(s[i-1]==t[j-1])
                      dpp[i][j]=countDistinctSubSequence(i-1,j-1,s,t,dpp)+ countDistinctSubSequence(i-1,j,s,t,dpp);
                 else dpp[i][j]=countDistinctSubSequence(i-1,j,s,t,dpp);
                }
            }
        }
        return dpp[i1][j1];
    }

    private static int countDistinctSubSequence(int i, int j, char[] s, char[] t, int[][] dpp) {
        if(j==0) return 1;
        if(i==0) return Integer.MIN_VALUE;
        if(dpp[i][j]!=0) return dpp[i][j];
        //match
        if(s[i-1]==t[j-1])
            return  dpp[i][j]=countDistinctSubSequence(i-1,j-1,s,t,dpp)+ countDistinctSubSequence(i-1,j,s,t,dpp);
        return dpp[i][j]=countDistinctSubSequence(i-1,j,s,t,dpp);
    }

    private static int countDistinctSubSequence(int i, int j, char[] s, char[] t) {
        if(j<0) return 1;
        if(i<0) return Integer.MIN_VALUE;
        //match
        if(s[i]==t[j])
            return  countDistinctSubSequence(i-1,j-1,s,t)+ countDistinctSubSequence(i-1,j,s,t);
        return countDistinctSubSequence(i-1,j,s,t);
    }

    //DP - 31
    private static List<Character> shortestCommonSuperSequenceString(int n1, int n2, char[] s1, char[] s2, int[][] dpp) {
        lcsBottomUp(n1,n2,s1,s2,dpp);
        // System.out.println(Arrays.deepToString(dpp));
        int i=n1,j=n2;
        List<Character> ans=new ArrayList<>();
        while (i>0 && j>0){
            if(s1[i-1]==s2[j-1]){
                ans.add(s1[i-1]);i--;j--;
            } else if (dpp[i][j-1]>dpp[i-1][j]) {
                ans.add(s2[j-1]);
                j--;
            }
            else{ ans.add(s1[i-1]); i--; }
        }
        while (i>0) { ans.add(s1[i-1]);}
        while (j>0) { ans.add(s2[j-1]);j--;}

        Collections.reverse(ans);
        return ans;
    }
    private static int shortestCommonSuperSequenceLength(int n1, int n2, String s1, String s2, int[][] dpp) {
        return n1+n2-lcs(n1,n2,s1.toCharArray(),s2.toCharArray(),dpp);
    }

    //DP - 30
    private static int minInsertionToConvertStrings1ToS2(int n1, int n2, String s1, String s2, int[][] dpp) {
        int lcs=lcs(n1,n2,s1.toCharArray(),s2.toCharArray(),dpp);
        int deletion=n1-lcs;
        int insertion=n2-lcs;
        return deletion+insertion;
    }

    // minInsertiion required is n-length of pallindromic subsequence
    //DP - 29
    private static int minInsertionToMakeStringPallindrome(int n1, String s, int[][] dpp) {
        return n1- lcs(n1-1,n1-1,s.toCharArray(),new StringBuffer(s).reverse().toString().toCharArray(),dpp);
    }

    // reverse string and find lcs
    private static List<Character> longestPallindromincSubseq(int n1, String s, int[][] dpp) {
        // for lengtg
        //return lcs(n1-1,n1-1,s.toCharArray(),new StringBuffer(s).reverse().toString().toCharArray(),dpp);
        // for string
        return lcsPrint(n1,n1,s.toCharArray(),new StringBuffer(s).reverse().toString().toCharArray(),dpp);

    }

    private static int longestCommonSubStringSpaceOptimized(int i1, int j1, char[] s, char[] t) {
        int maxi=0;
        int[] prev = new int[j1+1];
        int[] cur = new int[j1+1];
        for (int i = 1; i <i1 ; i++) {
            for (int j = 1; j <j1 ; j++) {
                if(s[i-1]==t[j-1]){
                    cur[j]=1+prev[j-1];
                    maxi=Math.max(maxi,cur[j]);
                }
            } prev=cur;
        }
        return maxi;
    }
    private static int longestCommonSubString(int i1, int j1, char[] s, char[] t, int[][] dpp) {
        int maxi=0;
        for (int i = 1; i <i1 ; i++) {
            for (int j = 1; j <j1 ; j++) {
                if(s[i-1]==t[j-1]){
                    dpp[i][j]=1+dpp[i-1][j-1];
                    maxi=Math.max(maxi,dpp[i][j]);
                }
            }
        }
        return maxi;
    }

    private static List<Character> lcsPrint(int n1, int n2, char[] s1, char[] s2, int[][] dpp) {
        lcsBottomUp(n1,n2,s1,s2,dpp);
       // System.out.println(Arrays.deepToString(dpp));
        int i=n1,j=n2;
        List<Character> ans=new ArrayList<>();
        while (i>0 && j>0){
            if(s1[i-1]==s2[j-1]){
                ans.add(s1[i-1]);i--;j--;
            } else if (dpp[i][j-1]>dpp[i-1][j]) {
                j--;
            }
            else i--;
        }
        Collections.reverse(ans);
        return ans;
    }

    private static int lcsBottomUpSpaceOptimized(int i1, int j1, char[] s1, char[] s2) {
        int[] prev = new int[j1+1];
        int[] cur = new int[j1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 || j==0) cur[j]= 0;
                else if(s1[i-1]==s2[j-1]){
                    cur[j]=1+prev[j-1];
                }
                else cur[j]=Math.max(prev[j],cur[j-1]);
            } prev=cur;
        }
        return prev[j1];

    }
    private static int lcsBottomUp(int i1, int j1, char[] s1, char[] s2, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 || j==0) dpp[i][j]= 0;
                else if(s1[i-1]==s2[j-1]){
                    dpp[i][j]=1+dpp[i-1][j-1];
                }
               else dpp[i][j]=Math.max(dpp[i-1][j],dpp[i][j-1]);
            }
        }
        return dpp[i1][j1];

    }
    private static int lcs(int i, int j, char[] s1, char[] s2, int[][] dpp) {
        if(i==0 || j==0) return 0;
        if(dpp[i][j]!=0) return dpp[i][j];
        if(s1[i-1]==s2[j-1]){
            return 1+lcs(i-1,j-1,s1,s2, dpp);
        }
        return dpp[i][j]=Math.max(lcs(i-1,j,s1,s2, dpp),lcs(i,j-1,s1,s2, dpp));
    }

    private static int lcs(int i, int j, char[] s1, char[] s2) {
        if(i<0 || j<0) return 0;
        if(s1[i]==s2[j]){
            return 1+lcs(i-1,j-1,s1,s2);
        }
        return Math.max(lcs(i-1,j,s1,s2),lcs(i,j-1,s1,s2));
    }
    private static int rodCutBottomUPSpaceOptimized1D(int i1, int N1, int[] a, int[][] dpp) {
        int[] prev = new int[N1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int N = 0; N <=N1 ; N++) {
                int nottake=prev[N];
                int take=Integer.MIN_VALUE;
                if(N-(i+1)>=0)
                    take=a[i]+prev[N-(i+1)];
                prev[N]=Math.max(nottake,take);
            }
        }
        return prev[N1];

    }
    private static int rodCutBottomUPSpaceOptimized(int i1, int N1, int[] a, int[][] dpp) {
        int[] prev = new int[N1+1];
        int[] cur = new int[N1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int N = 0; N <=N1 ; N++) {
                int nottake=prev[N];
                int take=Integer.MIN_VALUE;
                if(N-(i+1)>=0)
                    take=a[i]+cur[N-(i+1)];
                cur[N]=Math.max(nottake,take);
            }prev=cur;
        }
        return prev[N1];

    }
    private static int rodCutBottomUP(int i1, int N1, int[] a, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int N = 0; N <=N1 ; N++) {
                int nottake=dpp[i-1+1][N];
                int take=Integer.MIN_VALUE;
                if(N-(i+1)>=0)
                    take=a[i]+dpp[i+1][N-(i+1)];
                 dpp[i+1][N]=Math.max(nottake,take);
            }
        }
        return dpp[i1+1][N1];

    }
    private static int rodCut(int i, int N, int[] a, int[][] dpp) {
        if(i<0) return 0;
        if(dpp[i][N]!=0) return dpp[i][N];
        int nottake=rodCut(i-1,N,a, dpp);
        int take=Integer.MIN_VALUE;
        if(N-(i+1)>=0)
        take=a[i]+rodCut(i,N-(i+1),a, dpp);
        return dpp[i][N]=Math.max(nottake,take);
    }

    private static int rodCut(int i, int N, int[] a) {
        if(i<0) return 0;
        int nottake=rodCut(i-1,N,a);
        int take=Integer.MIN_VALUE;
        if(N-(i+1)>=0)
            take=a[i]+rodCut(i,N-(i+1),a);
        return Math.max(nottake,take);
    }
    private static int unBoundedKnapSackBottomUPSpaceOptimized1D(int i1, int W1, int[] wt, int[] val) {
        int[] prev = new int[W1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int W = 0; W <=W1 ; W++) {
                int nottake=prev[W];
                int take=Integer.MIN_VALUE;
                if(W-wt[i]>=0)
                    take=val[i]+prev[W-wt[i]];
                prev[W]=Math.max(take,nottake);
            }
        }
        return prev[W1];
    }
    //TODO: change to 1D array
    private static int unBoundedKnapSackBottomUPSpaceOptimized(int i1, int W1, int[] wt, int[] val) {
        int[] prev = new int[W1+1];
        int[] cur = new int[W1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int W = 0; W <=W1 ; W++) {
                int nottake=prev[W];
                int take=Integer.MIN_VALUE;
                if(W-wt[i]>=0)
                    take=val[i]+cur[W-wt[i]];
                cur[W]=Math.max(take,nottake);
            } prev=cur;
        }
        return prev[W1];
    }
    private static int unBoundedKnapSackBottomUP(int i1, int W1, int[] wt, int[] val, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int W = 0; W <=W1 ; W++) {
                int nottake=dpp[i-1+1][W];
                int take=Integer.MIN_VALUE;
                if(W-wt[i]>=0)
                    take=val[i]+dpp[i+1][W-wt[i]];
                 dpp[i+1][W]=Math.max(take,nottake);
            }
        }
        return dpp[i1+1][W1];
    }
    private static int unBoundedKnapSack(int i, int W, int[] wt, int[] val, int[][] dpp) {
         if(i<0) return 0;
         if(dpp[i][W]!=0) return dpp[i][W];
         int nottake=unBoundedKnapSack(i-1,W,wt,val, dpp);
         int take=Integer.MIN_VALUE;
         if(W-wt[i]>=0)
         take=val[i]+unBoundedKnapSack(i,W-wt[i],wt,val, dpp);
        return dpp[i][W]=Math.max(take,nottake);
    }

    private static int unBoundedKnapSack(int i, int W, int[] wt, int[] val) {
        if(i<0) return 0;
        int nottake=unBoundedKnapSack(i-1,W,wt,val);
        int take=Integer.MIN_VALUE;
        if(W-wt[i]>=0)
            take=val[i]+unBoundedKnapSack(i,W-wt[i],wt,val);
        return Math.max(take,nottake);
    }
    private static int coinChange2BottomUpSpaceOptimized(int i1, int target1, int[] a) {
        int[] prev = new int[target1+1];
        int[] cur = new int[target1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                if(target==0)  cur[target]=1;
                else {
                    int nottake =prev[target];
                    int take = 0;
                    if (target - a[i] >= 0)
                        take = cur [target - a[i]];
                    cur[target] = take + nottake;
                }
            }prev=cur;
        } return cur[target1];
    }
    private static int coinChange2BottomUp(int i1, int target1, int[] a, int[][] dpp) {
        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                if(target==0)  dpp[i+1][target]=1;
                else {
                    int nottake = dpp[i - 1+1][target];
                    int take = 0;
                    if (target - a[i] >= 0)
                        take = dpp[i+1] [target - a[i]];
                    dpp[i+1][target] = take + nottake;
                }
            }
        } return dpp[i1+1][target1];
    }
    private static int coinChange2(int i, int target, int[] a, int[][] dpp) {
        if(target==0) return 1;
        if(i<0) return 0;
        if( dpp[i][target]!=0) return  dpp[i][target];
        int nottake=coinChange2(i-1,target,a, dpp);
        int take=0;
        if(target-a[i]>=0)
        take=coinChange2(i,target-a[i],a, dpp);
        return dpp[i][target]=take+nottake;
    }

    private static int coinChange2(int i, int target, int[] a) {
        if(target==0) return 1;
        if(i<0) return 0;
        int nottake=coinChange2(i-1,target,a);
        int take=0;
        if(target-a[i]>=0)
            take=coinChange2(i,target-a[i],a);
        return take+nottake;
    }

    //same as countSubsetWithDifferenceD - DP16
    private static int assignSigns(int i, int D, int[] a, int[][] dpp) {
        return countSubsetWithDifferenceD(i,D,a);
    }

    private static int coinBottomUPSpaceOptimized(int i1, int target1, int[] a, int[][] dpp) {
        int[] prev = new int[target1+1];
        int[]cur = new int[target1+1];

        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                if (i==0) {
                    if(target%a[i]==0) cur[target]= target/a[i];
                    else cur[target]= Integer.MAX_VALUE;
                } else {
                    int notTake=0; if(i-1>=0)notTake=prev[target];
                    int take= Integer.MAX_VALUE;
                    if(target-a[i]>=0)
                        take=1+cur[target-a[i]];
                    cur[target]=Math.min(notTake,take);
                }
            }prev=cur;
        }
        return prev[target1];
    }
    private static int coinBottomUP(int i1, int target1, int[] a, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                 if (i==0) {
                    if(target%a[i]==0) dpp[i][target]= target/a[i];
                    else dpp[i][target]= Integer.MAX_VALUE;
                } else {
                int notTake=0; if(i-1>=0)notTake=dpp[i-1][target];
                int take= Integer.MAX_VALUE;
                if(target-a[i]>=0)
                    take=1+dpp[i][target-a[i]];
                dpp[i][target]=Math.min(notTake,take);
                }
            }
        }
        return dpp[i1][target1];
    }

    private static int coin(int i, int target, int[] a, int[][] dpp) {
        if(target ==0) return 0;
        if(i<0) {
            return Integer.MAX_VALUE;
        }
        if(dpp[i][target]!=0) return dpp[i][target];
        int notTake=0+coin(i-1,target,a, dpp);
        int take= Integer.MAX_VALUE;
        if(target-a[i]>=0)
            take=1+coin(i,target-a[i],a, dpp);
        return dpp[i][target]=Math.min(notTake,take);
    }

    private static int coin(int i, int target, int[] a) {
        if(target ==0) return 0;
        if(i<0) {
            return Integer.MAX_VALUE;
        }
        int notTake=0+coin(i-1,target,a);
        int take= Integer.MAX_VALUE;
        if(target-a[i]>=0)
            take=1+coin(i,target-a[i],a);
        return Math.min(notTake,take);
    }
    private static int knapsackBottomUpSpaceOptimizedSingleArray(int i1, int W1, int[] val, int[] wt, int[][] dp) {
        int[] prev = new int[W1 + 1];
        for (int i = 0; i <= i1; i++) {
            for (int W = W1; W >= 0; W--) {
                int not_take = 0;
                if (i - 1 >= 0) not_take = prev[W];
                int take = Integer.MIN_VALUE;
                if (W - wt[i] >= 0) {
                    take = val[i];
                    if (i - 1 >= 0) take += prev[W - wt[i]];
                }
                prev[W] = Math.max(take, not_take);
            }
        }   return prev[W1];
    }
    private static int knapsackBottomUpSpaceOptimized(int i1, int W1, int[] val, int[] wt, int[][] dp) {
        int[] prev = new int[W1+1];
        for (int i = 0; i <=i1 ; i++) {
            int[] cur = new int[W1+1];
            for (int W = 0; W <=W1 ; W++) {
                int not_take = 0 ;if(i-1>=0) not_take=prev [W];
                int take = Integer.MIN_VALUE;
                if (W - wt[i] >= 0 ) {
                    take = val[i] ; if(i-1>=0)take+= prev [W - wt[i]];
                }
                cur[W]=Math.max(take, not_take);
            } prev=cur;
        }
        return prev[W1];
    }
    private static int knapsackBottomUp(int i1, int W1, int[] val, int[] wt, int[][] dp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int W = 0; W <=W1 ; W++) {
                int not_take = 0 ;if(i-1>=0) not_take=dp[i - 1] [W];
                int take = Integer.MIN_VALUE;
                if (W - wt[i] >= 0 ) {
                    take = val[i] ; if(i-1>=0)take+= dp[i - 1] [W - wt[i]];
                }
                 dp[i][W]=Math.max(take, not_take);
            }
        }
        return dp[i1][W1];
    }
    private static int knapsack(int i, int W, int[] val, int[] wt, int[][] dpp) {
        if (i < 0) return 0;
        int not_take = 0 + knapsack(i - 1, W, val, wt, dpp);
        if(dpp[i][W]!=0) return dpp[i][W];
        int take = Integer.MIN_VALUE;
        if (W - wt[i] >= 0) {
            take = val[i] + knapsack(i - 1, W - wt[i], val, wt, dpp);
        }
        return dpp[i][W]=Math.max(take, not_take);
    }

    private static int knapsack(int i, int W, int[] val, int[] wt) {
        if (i < 0) return 0;
        int not_take = 0 + knapsack(i - 1, W, val, wt);
        int take = Integer.MIN_VALUE;
        if (W - wt[i] >= 0) {
            take = val[i] + knapsack(i - 1, W - wt[i], val, wt);
        }
        return Math.max(take, not_take);
    }
    private static int countSubsetWithDifferenceD(int n, int D, int[] a) {
        //s1-s2=D
        //totsum-s2-s2=D
        //totsum-2s2=D or totsum-D=2s2 or s2=(totsum-D)/2
        // so check if s2 can be formed or not
        int totSum = Arrays.stream(a).sum();
        if(totSum-D<=0 || (totSum-D)%2!=0) return -1;
        return countSubsetWithSumBottomUPSpaceOptimzed(n,(totSum-D)/2,a);
    }

    private static int countSubsetWithSumBottomUPSpaceOptimzed(int i1, int target1, int[] a) {
        int[] prev = new int[target1+1];
        for (int i = 0; i <= i1; i++) {
            int[] cur = new int[target1+1];
            for (int target = 0; target <= target1; target++) {
                if (target == 0 ) cur[target] = 1;
                    //imp:missed this in base case
                else if(i==0 && a[i]==target) cur[target]=1;
                else if (i - 1 >= 0) {
                    int not_take = prev[target];
                    int take = 0;
                    if (target - a[i] >= 0) {
                        take = prev[ target - a[i]];
                    }
                    cur[target] = take + not_take;
                }
            } prev = cur;
        }
        return prev[target1];

    }
    //imp:missed this in base case
    /*      else if(i==0 && a[i]==target) dp[i][target]=1;*/
    private static int countSubsetWithSumBottomUP(int i1, int target1, int[] a, int[][] dp) {

        for (int i = 0; i <= i1; i++) {
            for (int target = 0; target <= target1; target++) {
                if (target == 0 ) dp[i][target] = 1;
                //imp:missed this in base case
                else if(i==0 && a[i]==target) dp[i][target]=1;
                else if (i - 1 >= 0) {
                    int not_take = dp[i - 1][target];
                    int take = 0;
                    if (target - a[i] >= 0) {
                        take = dp[i - 1][ target - a[i]];
                    }
                    dp[i][target] = take + not_take;
                }
            }
        }
        return dp[i1][target1];

    }
    private static int countSubsetWithSum(int i, int target, int[] a, int[][] dp) {
        if(target==0) return 1;
        if(i<0) return 0;
        if(dp[i][target]!=0) return dp[i][target];
        int not_take=countSubsetWithSum(i-1,target,a, dp);
        int take=0;
        if(target-a[i]>=0)
        take+=countSubsetWithSum(i-1,target-a[i],a, dp);
        return dp[i][target]=take+not_take;
    }

    /*//handle 0 case especially
        if(i==0 && target==0 & a[i]==0) return 2;*/
    private static int countSubsetWithSum(int i, int target,int[] a) {
        if(i<0) return 0;

        //handle 0 case especially
        if(i==0){
            if(target==0 && a[0]==0) return 2;
            if(target==0 || target == a[0]) return 1;
            return  0;
        };

        int not_take=countSubsetWithSum(i-1,target,a);
        int take=0;
        if(target-a[i]>=0)
            take+=countSubsetWithSum(i-1,target-a[i],a);
        return take+not_take;
    }

    private static int partitionWithMinDifference(int i1, int[] a) {

        int target1= Arrays.stream(a).sum();
        boolean prev[] = new boolean[target1+1];
        for (int i = 0; i <=i1 ; i++) {
            boolean cur[] = new boolean[target1+1];
            for (int target = 0; target <=target1 ; target++) {
                if(target == 0)cur[target]= true;
                else {
                    boolean take=false;
                    if(target-a[i]>=0 && i-1>=0)
                        take= prev[target-a[i]];
                    boolean not_take= false; if(i-1>=0) not_take=prev[ target];
                    cur[target]=take ||not_take;
                }
            }prev=cur;
        }

        int mini=Integer.MAX_VALUE;
        for (int s1 = 0; s1 <=target1  ; s1++) {
            if(prev[s1]){
                mini=Math.min(mini,Math.abs(target1-s1-s1));
            }
        }
        return mini;
    }

    private static boolean partitionEuqualSubset(int ind, int[] a) {
        int sum = Arrays.stream(a).sum();
        if(sum%2!=0) return false;
        return targetSubsetSpace(ind, sum/2,a);
    }

    //video - 14
    private static boolean targetSubsetSpace(int i1, int target1,int[] a) {
        boolean prev[] = new boolean[target1+1];
        boolean cur[] = new boolean[target1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                if(target == 0)cur[target]= true;
                else {
                    boolean take=false;
                    if(target-a[i]>=0 && i-1>=0)
                        take= prev[target-a[i]];
                    boolean not_take= false; if(i-1>=0) not_take=prev[ target];
                    cur[target]=take ||not_take;
                }
            }prev=cur;
        }
        return prev[target1];
    }

    private static boolean targetSubsetBottomUp(int i1, int[] a, int target1, boolean[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int target = 0; target <=target1 ; target++) {
                if(target == 0) dpp[i][target]= true;
                else {
                boolean take=false;
                if(target-a[i]>=0 && i-1>=0)
                    take= dpp[i-1][target-a[i]];
                boolean not_take= false; if(i-1>=0) not_take=dpp[i-1][ target];
                dpp[i][target]=take ||not_take;
                }
            }
        }
        return dpp[i1][target1];
    }
    private static boolean targetSubset(int i, int[] a, int target, Boolean[] dp) {
        if(target == 0) return true;
        if(i<0) return false;
        if(dp[i]!=null) return dp[i];
        boolean take=false;
        if(target-a[i]>=0)
            take= targetSubset(i-1,a,target-a[i], dp);
        boolean not_take= targetSubset(i-1,a, target, dp);
        return dp[i]=take ||not_take;
    }

    private static boolean targetSubset(int i, int[] a, int target) {
        if(target == 0) return true;
        if(i<0) return false;
        boolean take=false;
        if(target-a[i]>=0)
            take= targetSubset(i-1,a,target-a[i]);
        boolean not_take= targetSubset(i-1,a, target);
        return take ||not_take;
    }

    private static int cherryBottomUPSpaceOptimized(int i1, int j11, int j21, int[][] g, int[][][] dppp) {

        int m = g.length;
        int n=g[0].length;
        int prev[][] = new int[n][n];
        for (int i = m -1; i >=0 ; i--) {
            int cur[][] = new int[n][n];
            for (int j1 = n-1; j1 >=0 ; j1--) {
                for (int j2 = 0; j2 <=j21 ; j2++) {
                    int left = 0; if(i+1< m && j1-1>=0) left+=prev[j1 - 1][ j2];
                    int down = 0; if(i+1<m) down+=prev[ j1][ j2];
                    int right = 0; if(i+1<m && j1+1<n)right+=prev [j1 + 1][ j2];
                    int alice=Math.max(
                            Math.max(left,
                                    down
                            ), right);

                    int bob=Math.max(
                            Math.max(left,
                                    down
                            ), right);

                    int sum= g[i][j1] + g[i][j2];
                    if (j1 == j2) sum= g[i][j1];
                    cur[j1][j2]=sum + Math.max(alice, bob);
                }
            }prev=cur;
        }
        return prev[j11][j21];

    }
    private static int cherryBottomUP(int i1, int j11, int j21, int[][] g, int[][][] dppp) {

        int m = g.length;
        int n=g[0].length;
        for (int i = m -1; i >=0 ; i--) {
            for (int j1 = n-1; j1 >=0 ; j1--) {
                for (int j2 = 0; j2 <=j21 ; j2++) {
                    int left = 0; if(i+1< m && j1-1>=0) left+=dppp[i + 1][j1 - 1][ j2];
                    int down = 0; if(i+1<m) down+=dppp[i + 1][ j1][ j2];
                    int right = 0; if(i+1<m && j1+1<n)right+=dppp[i + 1] [j1 + 1][ j2];
                    int alice=Math.max(
                            Math.max(left,
                                    down
                            ), right);

                    int bob=Math.max(
                            Math.max(left,
                                    down
                            ), right);

                    int sum= g[i][j1] + g[i][j2];
                    if (j1 == j2) sum= g[i][j1];
                    dppp[i][j1][j2]=sum + Math.max(alice, bob);
                }
            }
        }
        return dppp[i1][j11][j21];

    }
    //doubt not working
    private static int cherryAnotherWay(int i, int j1, int j2, int[][] g) {
        if(i>=g.length || j1<0 || j2<0 ||j1>=g[0].length||j2>=g[0].length) return (int) -1e8;

        int ans=(int) -1e8;
        for (int dj1 = -1; dj1 <=1 ; dj1++) {
            for (int dj2 = -1; dj2 <=1 ; dj2++) {
                int curSum=g[i][j1]+g[i][j2];  if(j1==j2) curSum=g[i][j1];
                ans=Math.max(ans,curSum+cherryAnotherWay(i+1,j1+dj1,j2+dj2,g));
            }
        }
        return ans;
    }
    private static int cherry(int i, int j1, int j2, int[][] g, int[][][] dppp) {
        if(i>=g.length || j1<0 ||j1>=g[0].length||j2<0 ||j2>=g[0].length) return 0;

        if(dppp[i][j1][j2]!=0) return dppp[i][j1][j2];
        int alice=Math.max(
                Math.max(cherry(i+1,j1-1,j2, g, dppp),
                        cherry(i+1,j1,j2, g, dppp)
                ), cherry(i+1,j1+1,j2, g, dppp));

        int bob=Math.max(
                Math.max(cherry(i+1,j1-1,j2, g, dppp),
                        cherry(i+1,j1,j2, g, dppp)
                ), cherry(i+1,j1+1,j2, g, dppp));

        int sum= g[i][j1] + g[i][j2];
        if (j1 == j2) sum= g[i][j1];

        return dppp[i][j1][j2]=sum + Math.max(alice, bob);
    }
    private static int cherry(int i, int j1, int j2, int[][] g) {
        if(i>=g.length || j1<0 ||j1>=g[0].length||j2<0 ||j2>=g[0].length) return 0;

        int alice=Math.max(
                Math.max(cherry(i+1,j1-1,j2, g),
                        cherry(i+1,j1,j2, g)
                ), cherry(i+1,j1+1,j2, g));

        int bob=Math.max(
                Math.max(cherry(i+1,j1-1,j2, g),
                        cherry(i+1,j1,j2, g)
                ), cherry(i+1,j1+1,j2, g));

        int sum= g[i][j1] + g[i][j2];
        if (j1 == j2) sum= g[i][j1];

        return sum + Math.max(alice, bob);
    }
    private static int maxVariableFallingPathSumBottomUPSpaceOptimized(int i1, int j1, int maxCol, int[][] a, int[][] dpp) {
        int prev[] = new int[maxCol];
        for (int i = 0; i<=i1 ; i++) {
            int cur[] = new int[maxCol];
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 ) cur[j]= a[i][j];
                else {
                    int up=a[i][j]+ prev[j];
                    int ld=a[i][j]; if(j-1>=0) ld+=prev[j-1];
                    int rd=a[i][j]; if(j+1<maxCol) rd+= prev[j+1];
                    cur[j]=Math.max(up,Math.max(ld,rd));
                }
            }prev=cur;
        }
        return prev[j1];
    }
    private static int maxVariableFallingPathSumBottomUP(int i1, int j1, int maxCol, int[][] a, int[][] dpp) {

        for (int i = 0; i<=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 ) dpp[i][j]= a[i][j];
                else {
                int up=a[i][j]+ dpp[i-1][j];
                int ld=a[i][j]; if(j-1>=0) ld+= dpp[i-1][j-1];
                int rd=a[i][j]+ dpp[i-1][j+1];
                dpp[i][j]=Math.max(up,Math.max(ld,rd));
                }
            }
        }
        return dpp[i1][j1];
    }
    private static int maxVariableFallingPathSum(int i, int j, int maxCol, int[][] a, int[][] dpp) {
        if(j<0 || j>=maxCol) return Integer.MIN_VALUE;
        if(i==0 ) return a[i][j];
        if(dpp[i][j]!=0) return dpp[i][j];
        int up=a[i][j]+ maxVariableFallingPathSum(i-1,j, maxCol, a, dpp);
        int ld=a[i][j]+ maxVariableFallingPathSum(i-1,j-1, maxCol, a, dpp);
        int rd=a[i][j]+ maxVariableFallingPathSum(i-1,j+1, maxCol, a, dpp);
        return dpp[i][j]=Math.max(up,Math.max(ld,rd));
    }

    private static int maxVariableFallingPathSum(int i, int j, int maxCol, int[][] a) {
        if(j<0 || j>=maxCol) return Integer.MIN_VALUE;
        if(i==0 ) return a[i][j];
        int up=a[i][j]+ maxVariableFallingPathSum(i-1,j, maxCol, a);
        int ld=a[i][j]+ maxVariableFallingPathSum(i-1,j-1, maxCol, a);
        int rd=a[i][j]+ maxVariableFallingPathSum(i-1,j+1, maxCol, a);
        return Math.max(up,Math.max(ld,rd));
    }

    private static int triangelMinPathSumBottomUpSpaceOptimized(int i1, int j1, int row1, int[][] aa) {
        int prev[] = new int[row1+1];
        int cur[] = new int[row1+1];
        for (int i = row1; i >=0 ; i--) {
            for (int j = i ; j >=0 ; j--) {
                if(i==row1) prev[j]= aa[i][j];
                else {
                    int down=aa[i][j]+cur[j];
                    int diag=aa[i][j]+cur[j+1];
                    prev[j]=Math.min(down,diag);
                }
            }
            prev=cur;
        }
        return prev[j1];
    }
    private static int triangelMinPathSumBottomUp(int i1, int j1, int row1, int[][] aa, int[][] dpp) {

        for (int i = row1; i >=0 ; i--) {
            for (int j = i ; j >=0 ; j--) {
                if(i==row1) dpp[i][j]= aa[i][j];
                else {
                int down=aa[i][j]+dpp[i+1][j];
                int diag=aa[i][j]+dpp[i+1][j+1];
                dpp[i][j]=Math.min(down,diag);
                }
            }
        }
        return dpp[i1][j1];
    }
    private static int triangelMinPathSum(int i, int j, int row, int[][] aa, int[][] dpp) {
        if(i==row) return aa[i][j];
        if(dpp[i][j]!=0) return dpp[i][j];
        int down=aa[i][j]+triangelMinPathSum(i+1,j,row,aa, dpp);
        int diag=aa[i][j]+triangelMinPathSum(i+1,j+1,row,aa, dpp);
        return dpp[i][j]=Math.min(down,diag);
    }
    private static int triangelMinPathSum(int i, int j, int row, int[][] aa) {
        if(i==row) return aa[i][j];
        int down=aa[i][j]+triangelMinPathSum(i+1,j,row,aa);
        int diag=aa[i][j]+triangelMinPathSum(i+1,j+1,row,aa);
        return Math.min(down,diag);
    }

    private static int minPathSumDpBottomUPSpaceOptimized(int i1, int j1, int[][] aa) {
        int []prev=new int[j1+1];
        int []cur=new int[j1+1];
        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 && j==0) cur[j]= aa[i][j];
                else {
                    int up=aa[i][j]+ prev[j];
                    int left=aa[i][j]+ cur[j-1];
                    cur[j]=Math.min(up,left);
                }
            }
        }
        return prev[j1];
    }
    private static int minPathSumDpBottomUP(int i1, int j1, int[][] aa, int[][] dpp) {

        for (int i = 0; i <=i1 ; i++) {
            for (int j = 0; j <=j1 ; j++) {
                if(i==0 && j==0) dpp[i][j]= aa[i][j];
                else {
                int up=aa[i][j]+ dpp[i-1][j];
                int left=aa[i][j]+ dpp[i][j-1];
                dpp[i][j]=Math.min(up,left);
                }
            }
        }
        return dpp[i1][j1];
    }
    private static int minPathSumDp(int i, int j, int[][] aa, int[][] dpp) {
        if(i==0 && j==0) return aa[i][j];
        if(i<0 || j<0) return (int) 1e8;
        if(dpp[i][j]!=0)return dpp[i][j];
        int up=aa[i][j]+ minPathSumDp(i-1,j,aa, dpp);
        int left=aa[i][j]+ minPathSumDp(i,j-1,aa, dpp);
        return dpp[i][j]=Math.min(up,left);
    }

    private static int minPathSum(int i, int j, int[][] aa) {
        if(i==0 && j==0) return aa[i][j];
        if(i<0 || j<0) return (int) 1e8;
        int up=aa[i][j]+ minPathSum(i-1,j,aa);
        int left=aa[i][j]+ minPathSum(i,j-1,aa);
        return Math.min(up,left);
    }
    private static int gridUniquePathDpBottomUPSpaceOptimized(int m1, int n1, int[][] aa) {
        int prev[]=new int[n1+1];
        int cur[]=new int[n1+1];
        for (int m = 0; m <=m1 ; m++) {
            for (int n = 0; n <=n1 ; n++) {
                if(m==0 && n==0) cur[n]= 1;
                else if (aa[m][n]==-1) {
                    cur[0]= 0; break;
                } else {
                    int up= 0; if(m-1>=0)up+=prev[n];
                    int left=0 ;if(n-1>=0)left+= cur[n-1];
                    cur[n]=up+left;
                }
            } prev=cur;
        }
        return prev[n1];
    }
    private static int gridUniquePathDpBottomUP(int m1, int n1, int[][] dpp) {

        for (int m = 0; m <=m1 ; m++) {
            for (int n = 0; n <=n1 ; n++) {
                if(m==0 && n==0) dpp[m][n]= 1;
                else {
                int up= 0; if(m-1>=0)up+=dpp[m-1][n];
                int left=0 ;if(n-1>=0)left+= dpp[m][n-1];
                dpp[m][n]=up+left;
                }
            }
        }
        return dpp[m1][n1];
    }

    private static int gridUniquePathDp(int m, int n, int[][] dpp) {
        if(m==0 && n==0) return 1;
        if(m<0 || n<0) return 0;
        if(dpp[m][n]!=0) return dpp[m][n];
        int up= gridUniquePathDp(m-1,n, dpp);
        int left= gridUniquePathDp(m,n-1, dpp);
        return dpp[m][n]=up+left;
    }

    private static int gridUniquePath(int m, int n) {
        if(m==0 && n==0) return 1;
        if(m<0 || n<0) return 0;
        int up=gridUniquePath(m-1,n);
        int left=gridUniquePath(m,n-1);
        return up+left;
    }
    private static int stockMaxProfit1(int n, int[] a) {
        int min=a[0] , profit=0;
        for (int i = 0; i < n; i++) {
            profit=Math.max(profit,a[i]-min);
            min=Math.min(min,a[i]);
        }
        return profit;
    }

    private static int rotten(int[][] aa) {
        Queue<int[]> queue=new ArrayDeque<>();
        int n = aa.length;
        int m = aa[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
              if(aa[i][j]==2){
                  queue.add(new int[]{i,j,0});
              }
            }
        }

        int unitTime=0;
        boolean [][]vis=new boolean[n][m];
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int di = polled[0];
            int dj = polled[1];
            int time = polled[2];
            if(!vis[di][dj]){
                vis[di][dj]=true;
                visitAll4Corner(di+1,dj,n,m,vis,time,aa,queue);
                visitAll4Corner(di-1,dj,n,m,vis,time, aa, queue);
                visitAll4Corner(di,dj+1,n,m,vis,time, aa, queue);
                visitAll4Corner(di,dj-1,n,m,vis,time, aa, queue);
                unitTime=Math.max(unitTime,time);
            }
        }
        return unitTime;
    }

    private static void visitAll4Corner(int di, int dj, int n, int m, boolean[][] vis, int dist, int[][] aa, Queue<int[]> queue) {
        if (isValidCell(di, dj, n, m) && !vis[di][dj] && aa[di][dj]==1) {
            aa[di][dj]=2;
            queue.add(new int[]{di, dj, dist + 1});
        }
    }

    private static int ninjaBottomUpSpaceOptimized(int n1, int prevDay1, int[][] a) {
        int prev1[]=new int[a[0].length];
        int cur[]=new int[a[0].length];
        int maxi = 0;
        for (int n = 0; n <=n1 ; n++) {
            for (int prevDay = 0; prevDay <=prevDay1 ; prevDay++) {
                for (int day = 0; day < a[0].length; day++) {
                    if (day != prevDay) {
                        int max = a[n][day] ; if(n-1>=0) max+=prev1 [day];
                        maxi = cur[prevDay]= Math.max(maxi, max);
                    }
                    prev1=cur;
                }
            }
        }
        return prev1[n1];
    }
    private static int ninjaBottomUp(int n1, int prevDay1, int[][] a, int[][] dpp) {

        int maxi = 0;
        for (int n = 0; n <=n1 ; n++) {
            for (int prevDay = 0; prevDay <=prevDay1 ; prevDay++) {
                for (int day = 0; day < a[0].length; day++) {
                    if (day != prevDay) {
                        int max = a[n][day] ; if(n-1>=0) max+=dpp[n - 1] [day];
                        maxi = dpp[n][prevDay]= Math.max(maxi, max);
                    }
                }
            }
        }
        return maxi;
    }

    private static int ninjaDp(int n, int prevDay, int[][] a, int[][] dpp) {
        if (n < 0) return 0;
        if(dpp[n][prevDay]!=0) return dpp[n][prevDay];
        int maxi = 0;
        for (int day = 0; day < a[0].length; day++) {
            if (day != prevDay) {
                int max = a[n][day] + ninjaDp(n - 1, day, a, dpp);
                maxi = dpp[n][prevDay]= Math.max(maxi, max);
            }
        }
        return maxi;
    }
    private static int ninja(int n, int prevDay, int[][] a) {
        if (n < 0) return 0;
        int maxi = 0;
        for (int day = 0; day < a[0].length; day++) {
            if (day != prevDay) {
                int max = a[n][day] + ninja(n - 1, day, a);
                maxi = Math.max(maxi, max);
            }
        }
        return maxi;
    }

    private static int[] reArrangeBySignNotEqual(int n, int[] a) {
        List<Integer> pos=new ArrayList<>();
        List<Integer> neg=new ArrayList<>();
        for (int i = 0; i <n ; i++) {
            if(a[i]>0) pos.add(a[i]);
            else neg.add(a[i]);
        }
        if(pos.size()>neg.size()){
            for (int i = 0; i < neg.size(); i++) {
                a[2 * i] = pos.get(i);
                a[2 * i + 1] = neg.get(i);
            }
            for (int i = neg.size(); i <pos.size() ; i++) {
                a[i] = pos.get(i);
            }
        }
        else {
                for (int i = 0; i < pos.size(); i++) {
                    a[2 * i] = pos.get(i);
                    a[2 * i + 1] = neg.get(i);
                }
                for (int i = pos.size(); i <neg.size() ; i++) {
                    a[i] = neg.get(i);
                }
        }
        return a;
    }

    private static int[] reArrangeBySignEqual(int n, int[] a) {
        int ans[] = new int[n];
        int ipos = 0, ineg = 1;
        for (int i = 0; i < n; i++) {
            if (a[i] > 0) {
                ans[ipos] = a[i];
                ipos += 2;
            } else {
                ans[ineg] = a[i];
                ineg += 2;
            }
        }
        return ans;
    }

    private static int[] kadaneSumIndex(int n, int[] a) {
        int maxSum=Integer.MIN_VALUE , tempSum=0 ,start=-1, ansStart=-1,ansEnd=-1;
        for (int i = 0; i <n ; i++) {
            if(tempSum==0) start=i;
            tempSum+=a[i];
            if(tempSum>maxSum) {
                maxSum = tempSum;
                ansEnd=i;
            }
            if(tempSum<0)tempSum=0;
        }
        return new int[]{start,ansEnd};
    }
    private static int kadane(int n, int[] a) {
        int maxSum=Integer.MIN_VALUE , tempSum=0;
        for (int i = 0; i <n ; i++) {
            tempSum+=a[i];
            if(tempSum>maxSum) maxSum=tempSum;
            if(tempSum<0)tempSum=0;
        }
        return maxSum==Integer.MIN_VALUE?-1:maxSum;
    }

    private static int moorieVoting(int n, int[] a) {
        int count=0,ele=-1;
        for (int i = 0; i < n; i++) {
            if (count == 0) {
                ele = a[i];
                count++;
            } else if (a[i] == ele) count++;
            else count--;
        }
        count=0;
        for (int i = 0; i <n ; i++) {
            if(ele==a[i])count++;
        }
        return count>n/2?ele:-1;
        }
    private static int[] sor0sAnd1s(int n, int[] a) {
        int lo=0,mid=0,hi=n-1;
        while (mid<=hi){
            int val=a[mid];
            if(val==0){
                swap(lo,mid,a);lo++;mid++;
            } else if (val==1) {
                mid++;
            }
            //val is 2
            else {
                swap(mid,hi,a);
                hi--;
            }
        }
        return a;
    }

    private static int[] _2_sumUsing2Pointer(int n, int target, int[] a) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        int first=0,last=n-1 , tempSum=0;
        while (first < last) {
            tempSum = a[first] + a[last];
            if (tempSum == target) return new int[]{first, last};
            else if (tempSum > target) last--;
            else if (tempSum < target) {
                first++;
            }
        }
        return new int[]{-1,-1};
    }
    private static int[] _2_sum(int n, int target, int[] a) {

        //val,index
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < n; i++) {
             map.put(a[i],i);
        }

        for (int i = 0; i < n ; i++) {
            int key = target - a[i];
            if(map.containsKey(key)) return new int[]{i,map.get(key)};
        }
        return new int[]{-1,-1};
    }

    // only for positivies & 0
    private static int maxSubArraySumLenUsing2Pointer(int n, int requiredSum, int[] a) {
        int right=0,left=0,tempSum = 0,len = 0;
        while (left<n && right<n ){
            if(tempSum>requiredSum){
                tempSum-=a[left];
                left++;
            } if (tempSum==requiredSum) {
                len=Math.max(len,right-left);
               // tempSum-=a[left];left++;
                tempSum += a[right];
                right++;
            } else{
                tempSum += a[right];
                right++;
            }
        }
        return len;
    }

    //for negative , 0 and positivies
    private static int maxSubArraySumLen(int n, int k, int[] a) {
        //val,index
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, len = 0;
        for (int i = 0; i < n; i++) {
            sum += a[i];
            int key = sum - k;
            if(sum==k) len=i+1;
            else if (map.containsKey(key)) {
                len = Math.max(len, i - map.get(key));
            }
            map.putIfAbsent(sum,i);
        }

        return len;
    }

    private static int maxConsequetiveOne(int n, int[] a) {
        int count=0,max=0;
        for (int i = 0; i <n ; i++) {
            if(a[i]==0){
                max=Math.max(max,count);
                count=0;
            }
            else count++;
        }
        return max;
    }

    private static int missigNumber(int n, int[] a) {
        int xor1=0;
        for (int i = 0; i <n ; i++) {
            xor1^=a[i];
        }

        for (int i = 1; i <=n ; i++) {
            xor1^=i;
        }
        return xor1;
    }

    // element that appear once
    private static int missingNum(int n, int[] a) {
        int xor1=0 , xor2=0;
        for (int i = 0; i <n-1 ; i++) {
            xor1^=a[i];
            xor2^=(i+1);
        }
        xor2^=n;
        return xor1^xor2;
    }

    private static int houseRobber2(int n, int[] a) {
        int excludingLast = nonAdjBottomUPSpaceOptimized(n - 2, a);
         int b[]=Arrays.copyOfRange(a,1,n+1);
        int excludingFirst = nonAdjBottomUPSpaceOptimized(b.length-1, b);
        return Math.max(excludingFirst,excludingLast);
    }

    private static int nonAdjBottomUPSpaceOptimized(int ind1, int[] a) {
        int prev1 = 0,prev2 = 0,cur;
        for (int ind = 0; ind <=ind1 ; ind++) {
            if (ind < 0) return 0;
            int take = a[ind] ; if(ind-2>=0) take+=prev2;
            int not_take = 0 ; if(ind-1>=0)not_take+=prev1;
            cur=Math.max(take, not_take);
            prev2=prev1;
            prev1=cur;
        }
        return prev1;
    }
    private static int nonAdjBottomUP(int ind1, int[] a, int[] dp) {

        for (int ind = 0; ind <=ind1 ; ind++) {
            if (ind < 0) return 0;
            if(dp[ind]!=0) return dp[ind];
            int take = a[ind] ; if(ind-2>=0) take+=dp[ind - 2];
            int not_take = 0 ; if(ind-1>=0)not_take+=dp[ind - 1];
            dp[ind]=Math.max(take, not_take);
        }
        return dp[ind1];

    }
    private static int nonAdjDp(int ind, int[] a, int[] dp) {
        if (ind < 0) return 0;
        if(dp[ind]!=0) return dp[ind];
        int take = a[ind] + nonAdjDp(ind - 2, a, dp);
        int not_take = 0 + nonAdjDp(ind - 1, a, dp);
        return dp[ind]=Math.max(take, not_take);
    }
    private static int nonAdj(int ind, int[] a) {
        if (ind < 0) return 0;
        int take = a[ind] + nonAdj(ind - 2, a);
        int not_take = 0 + nonAdj(ind - 1, a);
        return Math.max(take, not_take);
    }

    private static int frogn(int ind, int[] a , int k1, int[][] dp2) {
        if (ind <= 0) return 0;
        if(dp2[ind][k1]!=0) return dp2[ind][k1];
        int ans = Integer.MAX_VALUE;
        for (int k = 1; k <= k1; k++) {
            int step2 = (int) 1e8;
            if (ind - k >= 0) step2 = Math.abs(a[ind] - a[ind - k]) + frogn(ind - k, a, k, dp2);
            ans = dp2[ind][k1]=Math.min(ans, step2);
        }
        return ans;
    }
    private static int frogdpBottomUpSpaceOptimized(int ind1, int[] a) {
        int prev1 = 0,prev2 = 0;
        for (int ind = 1; ind <=ind1 ; ind++) {
            int step1= (int) 1e8;
            if(ind-1>=0)step1=Math.abs(a[ind]-a[ind-1])+ prev1;
            int step2=(int) 1e8;
            if(ind-2>=0)step2=Math.abs(a[ind]-a[ind-2])+ prev2;
            int cur=Math.min(step1,step2);
            prev2=prev1;
            prev1=cur;
        }
        return prev1;
    }
    private static int frogdpBottomUp(int ind1, int[] a, int[] dp) {

        for (int ind = 1; ind <=ind1 ; ind++) {
            int step1= (int) 1e8;
            if(ind-1>=0)step1=Math.abs(a[ind]-a[ind-1])+ dp[ind-1];
            int step2=(int) 1e8;
            if(ind-2>=0)step2=Math.abs(a[ind]-a[ind-2])+ dp[ind-2];
            dp[ind]=Math.min(step1,step2);
        }
        return dp[ind1];
    }
    private static int frogdp(int ind, int[] a, int[] dp) {
        if(ind<=0) return 0;
        if(dp[ind]!=0) return dp[ind];
        int step1= (int) 1e8;
        if(ind-1>=0)step1=Math.abs(a[ind]-a[ind-1])+ frogdp(ind-1,a, dp);
        int step2=(int) 1e8;
        if(ind-2>=0)step2=Math.abs(a[ind]-a[ind-2])+ frogdp(ind-2,a, dp);

        return dp[ind]=Math.min(step1,step2);
    }

    private static int frog(int ind, int[] a) {
        if(ind<0) return 0;
        if(ind==1) return Math.abs(a[ind]-a[ind-1]);
        int step1= (int) 1e8;
        if(ind-1>=0)step1=Math.abs(a[ind]-a[ind-1])+ frog(ind-1,a);
        int step2=(int) 1e8;
                if(ind-2>=0)step2=Math.abs(a[ind]-a[ind-2])+ frog(ind-2,a);

        return Math.min(step1,step2);
    }

    static class Edge{
        int node;
        int weight;

        public Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }
    private static void graph() {

        List<List<Edge>> edgeGraph = new ArrayList<>();
        int V = 5;
        for (int i = 0; i <= V; i++) {
            edgeGraph.add(new ArrayList<>());
        }

        edgeGraph.get(0).addAll(List.of(new Edge(1,2),new Edge(2,1)));
        edgeGraph.get(1).addAll(List.of(new Edge(0,2),new Edge(2,1)));
        edgeGraph.get(2).addAll(List.of(new Edge(0,1),new Edge(1,1),new Edge(4,2),new Edge(3,2)));
        edgeGraph.get(3).addAll(List.of(new Edge(2,2),new Edge(4,1)));
        edgeGraph.get(4).addAll(List.of(new Edge(2,2),new Edge(3,1)));

        List<List<Integer>> graph = new ArrayList<>();
        int Vg = 13;
        for (int i = 0; i <= Vg; i++) {
            graph.add(new ArrayList<>());
        }

       /* graph.get(0).addAll(List.of(1,2,3));
        graph.get(1).addAll(List.of(0));
        graph.get(2).addAll(List.of(0,3,4,5));
        graph.get(3).addAll(List.of(0,2));
        graph.get(4).addAll(List.of(2,6));
        graph.get(5).addAll(List.of(2,6));
        graph.get(6).addAll(List.of(4,5));*/

        graph.get(1).addAll(List.of(2,4));
        graph.get(2).addAll(List.of(1,3));
        graph.get(3).addAll(List.of(2,4));
        graph.get(4).addAll(List.of(1,5));
        graph.get(5).addAll(List.of(6));
        graph.get(6).addAll(List.of(7,9));
        graph.get(7).addAll(List.of(6,8));
        graph.get(8).addAll(List.of(7,9,10));
        graph.get(9).addAll(List.of(6,8));
        graph.get(10).addAll(List.of(8,11,12));
        graph.get(11).addAll(List.of(10,12));
        graph.get(12).addAll(List.of(10,11));

        int[][] grid = {
                {1,0,1},
                {1,1,0},
                {1,0,0}
        };

        int[][] gl = {
                {0,0},
                {0,0},
                {1,1},
                {1,0},
                {0,1},
                {0,3},
                {1,3},
                {0,4},
                {3,2},
                {2,2},
                {1,2},
                {0,2},
        };

        int m = grid.length;
        int n = grid[0].length;
        boolean []vis= new boolean [V];
        boolean []pathvis= new boolean [V];

        String s[]={"hot","dot","dog","lot","log","cog"};
        int a[]={2,5,7};
        int maxStop=2;
        List<Edges> edgesList= new ArrayList<>(List.of(
                new Edges(0,1,0),
                new Edges(0,3,0),
                new Edges(0,2,0),
                new Edges(1,2,0),
                new Edges(2,3,0),
                new Edges(4,5,0),
                new Edges(5,6,0),
                new Edges(7,8,0)
        ));
        //

        List<List<String>> accounts = List.of(
                List.of("john", "j1@com", "j2@com", "j3@com"),
                List.of("john", "j4@com"),
                List.of("raj", "r1@com", "r2@com"),
                List.of("john", "j1@com", "j5@com"),
                List.of("raj", "r2@com", "r3@com"),
                List.of("mary", "m1@com")
        );

        System.out.println(numberOfIsland2_onlineQueries(4, 5, gl));

       String str="ab";

    }

    private static List<List<Integer>> articulationPoint(int vg, int startNode, List<List<Integer>> graph) {
        boolean vis[]=new boolean[vg];
        int tin[]=new int[vg];
        int low[]=new int[vg];
        List<List<Integer>> ans=new ArrayList<>();
        for (int node = startNode; node < vg; node++) {
            if (!vis[node]) {
                doDfsArticulationPoint(node,1,-1,vis, tin,low,graph,ans);
            }
        }
         // ans + graph.get(startNode).size()>1?1:0;
        return ans;
    }

    private static void doDfsArticulationPoint(int node, int insertionTime, int parent, boolean[] vis, int[] tin, int[] low, List<List<Integer>> graph, List<List<Integer>> ans) {
        vis[node]=true;
        tin[node]=insertionTime;
        low[node]=insertionTime;
        for (int child : graph.get(node)) {
            if (child == parent ) continue;
            if (!vis[child]) {
                doDfsArticulationPoint(child, insertionTime+1, node, vis, tin, low, graph, ans);
                low[node]=Math.min(low[node],low[child]);
                if(tin[node]<=low[child] && parent != -1) ans.add((List.of(node)));
            }
            else low[node]=Math.min(low[node],tin[child]);
        }
    }
    //startNode was 1 as graph started with 1
    private static List<List<Integer>> bridges(int vg, int startNode, List<List<Integer>> graph) {

        boolean vis[]=new boolean[vg];
        int tin[]=new int[vg];
        int low[]=new int[vg];
        List<List<Integer>> ans=new ArrayList<>();
        for (int i = startNode; i < vg; i++) {
            if (!vis[i]) {
                doDfsBridges(i,1,0,vis, tin,low,graph,ans);
            }
        }
        return ans;
    }

    private static void doDfsBridges(int node, int insertionTime, int parent, boolean[] vis, int[] tin, int[] low, List<List<Integer>> graph, List<List<Integer>> ans) {
        vis[node]=true;
        tin[node]=insertionTime;
        low[node]=insertionTime;
        for (int child : graph.get(node)) {
            if (child == parent ) continue;
            if (!vis[child]) {
                doDfsBridges(child, insertionTime+1, node, vis, tin, low, graph, ans);
                low[node]=Math.min(low[node],low[child]);
                if(tin[node]<low[child]) ans.add((List.of(node,child)));
            }
            else low[node]=Math.min(low[node],low[child]);
        }
    }
    private static List<List<Integer>> scc(int vg, List<List<Integer>> graph) {
        //do dfs & sort in their finished time

        boolean vis[]=new boolean[vg];
        Stack<Integer> finishingOrder=new Stack<>();
        for (int i = 0; i < vg; i++) {
            if (!vis[i]) {
                doDfsScc(i,vis,finishingOrder,graph);
            }
        }

        List<List<Integer>> radj=new ArrayList<>();
        for (int i = 0; i <vg ; i++) {
            radj.add(new ArrayList<>());
        }
        // reverse graph
        for(int u=0;u< graph.size();u++){
            for (int v: graph.get(u)){
                radj.get(v).add(u);
            }
        }

        // go dfs and add ans
        List<List<Integer>> ans=new ArrayList<>();
        vis =new boolean[vg];
        while (!finishingOrder.isEmpty()) {
            Stack<Integer> tempList=new Stack<>();
            Integer i = finishingOrder.pop();
            if (!vis[i]) {
                doDfsScc(i,vis,tempList,radj);
            }
            //System.out.println("tempList:"+tempList);
            if(!tempList.isEmpty())ans.add(tempList);
        }
        return ans;
    }

    private static void doDfsScc(int node, boolean[] vis, Stack<Integer> list, List<List<Integer>> graph) {
        vis[node]=true;
        for (int adj : graph.get(node)) {
            if (!vis[adj]) {
                doDfsScc(adj, vis, list, graph);
            }
        }
        list.add(node);
    }

    private static int mostStoneRemovedSameRowOrCol(int numberOfStones, int[][] stones) {

        int maxRow=0;
        int maxCol=0;
        for (int i = 0; i < numberOfStones; i++) {
            maxRow=Math.max(maxRow,stones[i][0]);
            maxCol=Math.max(maxCol,stones[i][1]);
        }

        DS disjoingSet=new DS(maxRow+maxCol+1);

        Set<Integer> allProcessedStone = new HashSet<>();
        for (int i = 0; i < stones.length; i++) {
            int rowNode = stones[i][0];
            int colNode = stones[i][1];
            disjoingSet.unionBySize(rowNode,maxRow+1+colNode);
            allProcessedStone.add(rowNode);
            allProcessedStone.add(maxRow+1+colNode);
        }

        int noOfComponenets = 0;
        for (int nodeNumber :allProcessedStone) {
            if (disjoingSet.getUltimateParent(nodeNumber) == nodeNumber) noOfComponenets++;
        }

      //  System.out.println("noOfComponenets:"+noOfComponenets);
        return numberOfStones - noOfComponenets;
    }

    private static int makingALargeIsland(int m, int n, int[][] grid) {
        DS disjoingSet=new DS(m*n);

        //make ds
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                  if(grid[i][j]==1){
                      addNodeToSet(i - 1, j, i, j, m, n, disjoingSet,grid);
                      addNodeToSet(i + 1, j, i, j, m, n, disjoingSet, grid);
                      addNodeToSet(i, j - 1, i, j, m, n, disjoingSet, grid);
                      addNodeToSet(i, j + 1, i, j, m, n, disjoingSet, grid);
                }
            }
        }

        int maxSize=0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set<Integer> allParent=new HashSet<>();
                    addAll4CornerParentToSet(i-1,j,m,n,allParent, disjoingSet,grid);
                    addAll4CornerParentToSet(i+1,j,m,n,allParent, disjoingSet, grid);
                    addAll4CornerParentToSet(i,j-1,m,n,allParent, disjoingSet, grid);
                    addAll4CornerParentToSet(i,j+1,m,n,allParent, disjoingSet, grid);
                    int setSum = 1 + setSum(allParent, disjoingSet);
                   // System.out.println("i:"+i+",j:"+j+",allParent"+allParent+",setSum:"+setSum);
                    maxSize=Math.max(maxSize, setSum);
                }
            }
        }

        //edge case : where whole grid is 1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                int nodeNumber = n*i+j;
                maxSize=Math.max(maxSize,disjoingSet.getSize(disjoingSet.getUltimateParent(nodeNumber)));
            }
        }

        return maxSize;
    }

    private static void addAll4CornerParentToSet(int i, int j, int m, int n, Set<Integer> allParent, DS disjoingSet, int[][] grid) {
        if(isValidCell(i,j,m,n) && grid[i][j] ==1){
            //nodeNumber = i*col+col
            allParent.add(disjoingSet.getUltimateParent(i*n+j));
        }
    }

    private static int setSum(Set<Integer> allParent, DS disjoingSet) {
        int sum=0;
        for (int parent:allParent) {
            sum+= disjoingSet.getSize(parent);
        }
        return sum;
    }

    private static void addNodeToSet(int i, int j, int i1, int j1, int m, int n, DS disjoingSet, int[][] grid) {
        if(isValidCell(i,j,m,n) && isValidCell(i1,j1,m,n)  && grid[i][j] ==1){

            int a =n*i+j;
            int b= n*i1+j1;
          //  System.out.println("a:"+a+","+b+",m:"+m+",i:"+i+",j:"+j);
            disjoingSet.unionBySize(b,a);
        }

    }

    //video - 51
    private static List<Integer> numberOfIsland2_onlineQueries(int m, int n, int[][] grid) {
        DS disjoingSet=new DS(m*n);
        int count=0;
        boolean[][] vis = new boolean[m][n];
        List<Integer> ans=new ArrayList<>();
        for (int[] a : grid) {
            int i = a[0];
            int j = a[1];
            if(vis[i][j]) {   ans.add(count); continue; }
            Set<Integer> allParent = new HashSet<>();

            getUltimateParent(i -1, j, m, n, allParent, disjoingSet, vis);
            getUltimateParent(i +1, j, m, n, allParent, disjoingSet, vis);
            getUltimateParent(i, j -1, m, n, allParent, disjoingSet, vis);
            getUltimateParent(i, j +1, m, n, allParent, disjoingSet, vis);
            count++;
            count -= allParent.size();
            vis[i][j] = true;
            ans.add(count);

            addToDisjointSet(i -1, j, i, j, m, n, disjoingSet, vis);
            addToDisjointSet(i +1, j, i, j, m, n, disjoingSet, vis);
            addToDisjointSet(i, j -1, i, j, m, n, disjoingSet, vis);
            addToDisjointSet(i, j +1, i, j, m, n, disjoingSet, vis);

        }
        return ans;
    }

    private static void addToDisjointSet(int i, int j, int i1, int j1, int m, int n, DS disjoingSet, boolean[][] grid) {
        if(isValidCell(i,j,m,n) && grid[i][j]){
            int a =n*i+j;
            int b= n*i1+j1;
            disjoingSet.unionBySize(a,b);
        }

    }
    private static void getUltimateParent(int i, int j, int m, int n, Set<Integer> allParent, DS disjoingSet, boolean[][] grid) {
        if(isValidCell(i,j,m,n) && grid[i][j]){
            int a =n*i+j;
            //  System.out.println("a:"+a+","+b+",m:"+m+",i:"+i+",j:"+j);
            allParent.add(disjoingSet.getUltimateParent(a));
        }

    }

    private static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<Integer, String> names=new HashMap<>();
        Map<String,Integer> emails=new HashMap<>();
        DS disjoingSet=new DS(accounts.size());
        for (int accountNumber=0 ;accountNumber <accounts.size();accountNumber++) {
            List<String> ithAccount = accounts.get(accountNumber);
            for (int node = 0; node < ithAccount.size(); node++) {
                String value =  ithAccount.get(node);
               if(node ==0){
                   names.put(accountNumber,value);
               }
               else {
                   if(emails.containsKey(value)){
                       disjoingSet.unionBySize(emails.get(value),accountNumber);
                   }
                   else emails.put(value,accountNumber);
               }
            }
        }
        Map<Integer, List<String>> listMap = emails.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));

        listMap.values().forEach(list -> list.sort(Comparator.naturalOrder()));

       // System.out.println("listMap"+listMap);
       // System.out.println("names"+names);

        List<List<String>> ans=new ArrayList<>(listMap.size());
        for(int i=0;i<listMap.size();i++) ans.add(new ArrayList<>());

        for(int node=0;node<listMap.size();node++){

            int ultimateParent = disjoingSet.getUltimateParent(node);
           // System.out.println("node"+node+",ultimateParent:"+ultimateParent);
            if(node == ultimateParent){
            ans.get(node).addAll(listMap.get(ultimateParent));
            String name = names.get(node);
            ans.get(node).add(0,name);
            }
            else {
                ans.get(node).clear();
            }
        }

       return ans;

    }

    private static int minOperationsToMakeGraphConnected(int nodes, List<Edges> edgesList) {
        DS disjoingSet=new DS(nodes);

        int noOfextraEdges=0;
        for (Edges edge:edgesList){
            if(disjoingSet.getUltimateParent(edge.u)==disjoingSet.getUltimateParent(edge.v)){
                noOfextraEdges++;
            }
            else disjoingSet.unionBySize(edge.u,edge.v);
        }
        int noOfComponents=0;
        for (int node = 0; node <=nodes ; node++) {
            if(disjoingSet.getUltimateParent(node)==node)noOfComponents++;
        }

        int requiredMinOfEdges=noOfComponents-1;
        System.out.println("noOfComponents:"+noOfComponents+",noOfextraEdges:"+noOfextraEdges);
        if(noOfextraEdges>=requiredMinOfEdges) return requiredMinOfEdges;
        return -1;
    }

    //video - 48
    private static int numberOfProvincesUsingDS(int totalNodes, int m, int n, int[][] grid) {

        DS disjoingSet=new DS(totalNodes);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j]==1){
                    disjoingSet.unionBySize(i,j);
                }
            }
        }
        int count=0;
        for (int i = 0; i < m ; i++) {
            if(disjoingSet.getUltimateParent(i)==i) count++;
        }
        return count;
    }


    private static List<List<Integer>> kruskal(int totalSize, List<Edges> edgesList) {
      //  Collections.sort(edgesList,Comparator.comparingInt(Edges::getWt));
        edgesList.sort(Comparator.comparingInt(Edges::getWt));
        DS disjoingSet=new DS(totalSize);
        List<List<Integer>> edges=new ArrayList<>();
        int wtSum=0;
        for (Edges edge :edgesList) {
            if(!disjoingSet.isInSameComponent(edge.u,edge.v)){
                wtSum+=edge.wt;
                edges.add(List.of(edge.u,edge.v));
                disjoingSet.unionBySize(edge.u,edge.v);
            }
        }
       // System.out.println("wtSum:"+wtSum);
        return edges;
    }

    private static void disJointSetImpl() {
        DS ds=new DS(7);
        ds.unionBySize(1,2);
        ds.unionBySize(2,3);
        ds.unionBySize(4,5);
        ds.unionBySize(6,7);
        ds.unionBySize(5,6);
        System.out.println(ds.isInSameComponent(3,7));
        ds.unionBySize(3,7);
        System.out.println(ds.isInSameComponent(3,7));
    }

    private static List<List<Integer>> prim(int v, List<List<Edge>> graph) {
        boolean vis[] = new boolean[v];
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        //dist,node,parent
        queue.add(new int[]{0, 0, -1});
        List<List<Integer>> edges = new ArrayList<>();
        int mstLenSum = 0;
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int dist = polled[0];
            int node = polled[1];
            int parent = polled[2];
            if(vis[node]) continue;
            if(parent != -1) edges.add(List.of(parent, node));
            mstLenSum += dist;
            vis[node] = true;
            for (Edge adj : graph.get(node)) {
                if (!vis[adj.node]) {
                    queue.add(new int[]{adj.weight, adj.node, node});
                }
            }
        }

        System.out.println("totSum:" + mstLenSum);
        return edges;

    }

    int findCity(int n, int m, int[][] edges, int distanceThreshold){

        int matrix[][] =new int[m][n];
        for(int []b:matrix)Arrays.fill(b,Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j]=edges[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            matrix[edges[i][0]][edges[i][1]]= edges[i][2];
            matrix[edges[i][1]][edges[i][0]]= edges[i][2];
        }

        for (int via = 0; via < edges.length-1; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(matrix[i][via] ==Integer.MAX_VALUE || matrix[via][j]==Integer.MAX_VALUE ) continue;
                    matrix[i][j]=Math.min(matrix[i][j],matrix[i][via]+matrix[via][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            matrix[i][i]=0;
        }
        int maxCount=Integer.MAX_VALUE, node=-1;
        for (int i = 0; i < n; i++) {
            int count=0;
            for (int j = 0; j < n; j++) {
               if(matrix[i][j]<=distanceThreshold)count++;
            }
            if(count<=maxCount){ maxCount=count; node=i; }
        }
        return node;
    }

    //go via each node
    // multipe source sorted path
    //  negative cycle if a[i][i] <0
    private static void floydwarshal(int m, int n, int[][] grid) {

        for (int via = 0; via < m; via++) {
            for (int i = 0; i <m ; i++) {
                for (int j = 0; j <n ; j++) {
                    grid[i][j]=Math.max(grid[i][j],grid[i][via]+grid[via][j]);
                }
            }
        }
        
    }

    // relaxtion of edges to totaledges-1 times
    private static int[] bellmanford(int totalNodes, List<Edges> edgesList) {

        int dist[]=new int[totalNodes];
        Arrays.fill(dist,(int)1e7);
        dist[0]=0;
        for (int i = 1; i < edgesList.size(); i++) {
            for (Edges edges:edgesList){
                if(dist[edges.u]!=(int)1e7 && dist[edges.u]+edges.wt< dist[edges.v]){
                    dist[edges.v] = dist[edges.u]+edges.wt;
                }
            }
        }

        //for negative cycle
        for (Edges edges:edgesList){
            if(dist[edges.u]!=(int)1e7 && dist[edges.u]+edges.wt< dist[edges.v]){
               return new int[]{-1};
            }
        }

        return dist;
    }

    static class Edges{
        int u;
        int v;

        public int getWt() {
            return wt;
        }

        int wt;

        public Edges(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }
    }
    private static int numberOfWaysToArriveAtDestination(int V, int start,int end, List<List<Edge>> graph) {
        Queue<int[]> queue=new ArrayDeque<>();
        //dist,node
        queue.add(new int[]{0,0});
        int dist[]=new int[V];
        int ways[]=new int[V];
        Arrays.fill(dist,(int)1e7);
        dist[start]=0;
        ways[0]=1;
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int distance=polled[0];
            int u=polled[1];
            for (Edge v:graph.get(u)){
                if(distance + v.weight < dist[v.node] ){
                    dist[v.node] = distance + v.weight;
                    ways[v.node]=ways[u];
                    queue.add(new int[]{distance + v.weight,v.node});
                }
                else if(distance + v.weight == dist[v.node]){
                    ways[v.node]+=ways[u];
                }
            }
        }
        System.out.println(Arrays.toString(ways));
        return ways[end];
    }

    private static int minMultiplicationToReachEnd(int start, int end, int mod, int[] a) {
        Queue<int[]> queue = new ArrayDeque<>();
        //stop,node,distance
        queue.add(new int[]{0, start});
        int dist[] = new int[mod];
        Arrays.fill(dist, (int) 1e7);
        dist[start] = 0;
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int steps = polled[0];
            int val = polled[1];

            for (int i = 0; i < a.length; i++) {
                int num = (val * a[i]) % mod;
                if (num == end) return steps + 1;
                if (steps + 1 < dist[num]) {
                    dist[num] = 1 + steps;
                    queue.add(new int[]{steps + 1, num});
                }
            }
        }

        return -1;

    }

    // allowed stops is already +1 , so i want atmost 1 stop so it will be 2
    private static int cheapestFlighWithKStop(int V,int start,int end,int allowedStops, List<List<Edge>> graph) {
        Queue<int[]> queue=new ArrayDeque<>();
        //stop,node,distance
        queue.add(new int[]{0,0,0});
        int dist[]=new int[V];
        Arrays.fill(dist,(int)1e7);
        dist[start]=0;
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int stop=polled[0];
            int u=polled[1];
            int cost= polled[2];
            if(stop > allowedStops+1) continue;
            if(u == end) continue;
            for (Edge v:graph.get(u)){
                if(cost + v.weight < dist[v.node] && stop<=allowedStops+1){
                    dist[v.node] = cost + v.weight;
                    queue.add(new int[]{stop+1,v.node,cost + v.weight});
                    System.out.println(Arrays.toString(dist));
                }
            }
        }

        return dist[end];
    }

    private static int pathWithMinEffort(int i1, int j1, int di, int dj, int m, int n, int[][] grid) {
        int dist[][]=new int[m][n];
        for(int b[]:dist) Arrays.fill(b,(int)1e5);
        dist[i1][j1]=0;
        Queue<int[]> queue=new PriorityQueue<>(Comparator.comparingInt(a->a[0]));
        queue.add(new int[]{0,0,0});

            while (!queue.isEmpty()){
                int[] polled = queue.poll();
                int prevEffort=polled[0];
                int i=polled[1];
                int j= polled[2];
                if(i==di && j==dj) return prevEffort;
                visitAll4CornerMinEffort(i-1,j,m,n, queue,dist ,i ,j,prevEffort,grid);
                visitAll4CornerMinEffort(i+1,j,m,n, queue, dist, i, j, prevEffort, grid);
                visitAll4CornerMinEffort(i,j-1,m,n, queue, dist, i, j, prevEffort, grid);
                visitAll4CornerMinEffort(i,j+1,m,n, queue, dist, i, j, prevEffort, grid);
            }
            return 0;
    }

    private static void visitAll4CornerMinEffort(int di, int dj, int m, int n, Queue<int[]> queue, int[][] dist, int i, int j, int prevDist, int[][] grid) {
        if (isValidCell(di, dj, m, n)) {
            int maxVal = Math.max(Math.abs(grid[i][j] - grid[di][dj]), prevDist);
            //dist array and queue 0th element will be same
            if (maxVal < dist[di][dj]) {
                dist[di][dj] = dist[i][j] + Math.abs(grid[i][j] - grid[di][dj]);
                queue.add(new int[]{maxVal, di, dj});
            }
        }
    }

    private static int sortestPath(int i1, int j1, int di, int dj, int n, int m, int[][] grid) {
        Queue<int[]> queue=new ArrayDeque<>();
        queue.add(new int[]{i1,j1});
        boolean vis[][]=new boolean[n][m];
        int dist[][]=new int[n][m];
        for(int a[]:dist) Arrays.fill(a,(int)1e7);
        dist[i1][j1]=0;
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int i=polled[0];
            int j= polled[1];
            if(i==di && j==dj) return dist[i][j];
            visitAll4CornerSP(i-1,j,n,m,vis, grid,queue,dist ,i ,j);
            visitAll4CornerSP(i+1,j,n,m,vis, grid,queue, dist, i, j);
            visitAll4CornerSP(i,j-1,n,m,vis, grid,queue, dist, i, j);
            visitAll4CornerSP(i,j+1,n,m,vis, grid,queue, dist, i, j);
        }
        return 0;
    }

    private static void visitAll4CornerSP(int di, int dj, int n, int m, boolean[][] vis, int[][] aa, Queue<int[]> queue, int[][] dist, int i, int j) {
        if (isValidCell(di, dj, n, m) && !vis[di][dj] && aa[di][dj]==1 && dist[i][j]+1 < dist[di][dj]) {
            vis[di][dj]=true;
            dist[di][dj]= dist[i][j]+1;
            queue.add(new int[]{di, dj});
        }
    }

    private static List<Integer> dijkstraPath(int startNode , int endNode, int V, List<List<Edge>> graph) {
        Queue<Edge> queue= new PriorityQueue<>(Comparator.comparingInt(x->x.weight));
        int dist[]=new int[V];
        int parent[]=new int[V];
        for (int i = startNode; i <V ; i++) {
            parent[i]=i;
        }
        queue.add(new Edge(startNode,0));
        Arrays.fill(dist, (int) 1e7);
        dist[startNode]=0;
        while (!queue.isEmpty()){
            Edge u = queue.poll();
            for (Edge v:graph.get(u.node)){
                if(dist[u.node]+v.weight < dist[v.node]){
                    dist[v.node]= dist[u.node]+v.weight;
                    queue.add(new Edge(v.node,dist[u.node]+v.weight));
                    parent[v.node]=u.node;
                }
            }
        }

        //System.out.println(Arrays.toString(parent));

        List<Integer> ans=new ArrayList<>();

        while (endNode!=parent[endNode]){
            ans.add(endNode);
            endNode = parent[endNode];
        }
        ans.add(startNode);
        //System.out.println("path:"+ans);
        return ans;
    }


    //not applicable for negative weight/cycle
    private static int[] dijkstra(int startNode , int V, List<List<Edge>> graph) {
        Queue<Edge> queue= new PriorityQueue<>(Comparator.comparingInt(x->x.weight));
        int dist[]=new int[V];
        queue.add(new Edge(startNode,0));
        Arrays.fill(dist, (int) 1e7);
        dist[startNode]=0;
        while (!queue.isEmpty()){
            Edge u = queue.poll();
            for (Edge v:graph.get(u.node)){
                if(dist[u.node]+v.weight < dist[v.node]){
                    dist[v.node]= dist[u.node]+v.weight;
                    queue.add(new Edge(v.node,dist[u.node]+v.weight));
                }
            }
        }
        return dist;
    }

    static class Ladder {String word;int size;

        public Ladder(String word, int size) {
            this.word = word;
            this.size = size;
        }
    }

    static class LadderList {List<String> word;int level;

        public LadderList(List<String> word, int level) {
            this.word = word;
            this.level = level;
        }

        @Override
        public String toString() {
            return "LadderList{" +
                    "word=" + word +
                    ", level=" + level +
                    '}';
        }

    }

    private static List<List<String>> wordLadderOptimized(String start, String end, String[] s) {
        Set<String> set = Arrays.stream(s).collect(Collectors.toSet());
        Queue<Ladder> queue=new ArrayDeque<>();
        queue.add(new Ladder(start,1));
        set.remove(start);
        Map<String,Integer> map =new HashMap<>();
        map.put(start,0);
        while (!queue.isEmpty()) {
            Ladder polled = queue.poll();
            if (polled.word.equals(end)) { continue;}
            String str = polled.word;
            for (int i = 0; i < str.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    if(str.charAt(i)!=c){
                        char[] chars = str.toCharArray();
                        chars[i]=c;
                        String strstring = String.valueOf(chars);
                        if (set.contains(strstring)) {
                            queue.add(new Ladder(strstring, polled.size + 1));
                            map.put(strstring, polled.size );
                            set.remove(strstring);
                        }
                    }
                }
            }
        }
      //  System.out.println(map);
        List<String> temp = new ArrayList<>();
        List<List<String>> ans = new ArrayList<>();
        temp.add(end);
        printMapUsingDfs(start,end,map, temp ,ans);
        return ans;
    }

    private static void printMapUsingDfs(String start, String end, Map<String, Integer> map, List<String> list, List<List<String>> ans) {
        //reached started from end
        if(start.equals(end)){
            List<String> list11 = new ArrayList<>(list);
            Collections.reverse(list11);
            ans.add(list11);
            return;
        }

        for (int i = 0; i < end.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (end.charAt(i) != c) {
                    char[] chars = end.toCharArray();
                    chars[i]=c;
                    String newStr = String.valueOf(chars);
                    //newStr +1 is currentLevel
                    if(map.containsKey(newStr) && map.get(newStr)+1== map.get(end)){
                        list.add(newStr);
                        printMapUsingDfs(start,newStr,map, list, ans);
                        list.remove(list.size()-1);
                    }
                }
            }
        }

    }

    private static List<List<String>> wordLadder2(String start, String end, String[] s) {
        Set<String> set = Arrays.stream(s).collect(Collectors.toSet());
        Queue<LadderList> queue = new ArrayDeque<>();
        List<String> list=new ArrayList<>();
        list.add(start);
        queue.add(new LadderList(list,1));
        set.remove(start);
        List<List<String>> ans = new ArrayList<>();
        int level=1;
        Set<String> to_be_deleted = new HashSet<>();
        while (!queue.isEmpty()) {
            LadderList polled = queue.poll();
           // System.out.println("set:" + set);
            String str = polled.word.get(polled.word.size() - 1);
            if (polled.level > level) {
                set.remove(to_be_deleted);
                to_be_deleted.clear();
                level=polled.level;
               // System.out.println(" removal after set:" + set);
            }
            if (str.equals(end)) {
                ans.add(polled.word);
                while (queue.peek().level<=polled.level){
                    ans.add(queue.poll().word);
                }
              break;
            }
            for (int i = 0; i < str.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    if (str.charAt(i) != c) {
                        char[] chars = str.toCharArray();
                        chars[i] = c;
                        String strstring = String.valueOf(chars);
                        if (set.contains(strstring)) {
                           // System.out.println("contains:" + strstring);
                            List<String> newList = new ArrayList<>(polled.word);
                            newList.add(strstring);
                         //   System.out.println("adding to queue:"+newList +" ,level:"+(polled.level+1));
                            queue.add(new LadderList(new ArrayList<>(newList), polled.level + 1));
                            to_be_deleted.add(strstring);
                        }
                    }
                }
            }
        }
        return ans;
    }

    private static int wordLadder1(String start, String end, String[] s) {
        Set<String> set = Arrays.stream(s).collect(Collectors.toSet());
        Queue<Ladder> queue=new ArrayDeque<>();
        queue.add(new Ladder(start,1));
        set.remove(start);
        while (!queue.isEmpty()) {
            Ladder polled = queue.poll();
            if (polled.word.equals(end)) return polled.size;
            String str = polled.word;
            for (int i = 0; i < str.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    if(str.charAt(i)!=c){
                        char[] chars = str.toCharArray();
                        chars[i]=c;
                        String strstring = String.valueOf(chars);
                        if (set.contains(strstring)) {
                            queue.add(new Ladder(strstring, polled.size + 1));
                            set.remove(strstring);
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static int[] shortestPathUDGFromSourceToAllNode(int V, int startNode, List<List<Integer>> graph) {
        Queue<Integer> queue =new ArrayDeque<>();
        queue.add(startNode);
        int []dist=new int[V];
        Arrays.fill(dist, (int) 1e7);
        dist[startNode]=0;
        while (!queue.isEmpty()){
            int u = queue.poll();
            for ( int v:graph.get(u)){
                if(dist[u]+1<dist[v]){
                    dist[v] =dist[u]+1;
                    queue.add(v);
                }
            }
        }
        return dist;
    }

    //shortest path without algo
    private static int[] shortestPathDGFromSourceToAllNode(int V, int startNode, List<List<Edge>> graph) {

        boolean vis[]=new boolean[V];
        Stack<Integer> stack=new Stack<>();
        for (int node = 0; node < V; node++) {
            if(!vis[node]){
                doDfs(node,vis,stack,graph);
            }
        }
        int []dist=new int[V];
        Arrays.fill(dist, (int) 1e7);
        dist[startNode]=0;
        while (!stack.isEmpty()){
            Integer u = stack.pop();
            for ( Edge v:graph.get(u)){
                if(dist[u] +  v.weight < dist[v.node]){
                    dist[v.node]= dist[u] +  v.weight;
                }
            }
        }
        return dist;
    }

    private static void doDfs(int node, boolean[] vis, Stack<Integer> stack, List<List<Edge>> graph) {
        vis[node]=true;
        for ( Edge child:graph.get(node)) {
            if(!vis[child.node]){
                doDfs(child.node,vis,stack,graph);
            }
        }
        stack.add(node);
    }

    private static List<Character> alienOrder(int v, String[] s) {
        List<List<Integer>> edges=new ArrayList<>();
        for (int i = 0; i < v; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i <= s.length-2; i++) {
            addEdges(s[i],s[i+1],edges);
        }
        return kahnTopoSort(v,edges).stream()
                .map(x ->  Character.valueOf((char) (x+'a')))
                .collect(Collectors.toList());

    }

    private static void addEdges(String s, String t, List<List<Integer>> edges) {

             for (int i = 0; i < s.length(); i++) {
                 if (s.charAt(i) != t.charAt(i)) {
                     edges.get(s.charAt(i) - 'a').add(t.charAt(i) - 'a');
                     break;
                 }
         }
    }

    // reverse edges and toposort will give all safe nodes
    private static List<Integer> eventualSafeStates(int V, List<List<Integer>> graph) {
        List<List<Integer>> revEdges=new ArrayList<>();
        for (int i = 0; i < V; i++) {
            revEdges.add(new ArrayList<>());
        }
        for (int u = 0; u < V ; u++) {
            for (int v:graph.get(u)){
                revEdges.get(v).add(u);
            }
        }
        return kahnTopoSort(V,revEdges);

    }

    private static List<Integer> courseScheduleOrder(int V, List<List<Integer>> graph) {
        return kahnTopoSort(V, graph);
    }
    private static boolean isCourseSchedulePossible(int V, int totalNumberOfNodes, List<List<Integer>> graph) {
        return kahnTopoSort(V, graph).size() == totalNumberOfNodes ? true : false;
    }

    //check for Cycle
    private static boolean isCycleInDirectedGraph(int V, int totalNumberOfNodes , List<List<Integer>> graph) {
        return kahnTopoSort(V, graph).size() != totalNumberOfNodes ? true : false;
    }

    //valid only for DAG
    private static List<Integer> kahnTopoSort(int v, List<List<Integer>> graph) {
        List<Integer> ans = new ArrayList<>();
        int[] indegree = new int[v];
        for (int i = 0; i < v; i++) {
            for (int child : graph.get(i)) {
                indegree[child] += 1;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; i++) {
            if (indegree[i] == 0) queue.add(i);
        }
        while (!queue.isEmpty()) {
            Integer polled = queue.poll();
            for (int child : graph.get(polled)) {
                indegree[child] -= 1;
                if (indegree[child] == 0) queue.add(child);
            }
           ans.add(polled);
        }
        return ans;
    }


    private static List<Integer> topoSort(int v, boolean[] vis, List<List<Integer>> graph) {
        List<Integer> ans=new ArrayList<>();
        Stack<Integer> stack=new Stack<>();
        for (int i = 0; i <v ; i++) {
            if(!vis[i]){
                doTopoSort(i,vis, stack,graph);
            }
        }
        while(!stack.empty()) ans.add(stack.pop());
        return ans;
    }

    private static void doTopoSort(int node, boolean[] vis, Stack<Integer> stack, List<List<Integer>> graph) {
        vis[node]=true;
        for(int child:graph.get(node)){
            if(!vis[child]){
                doTopoSort(child,vis, stack,graph);
            }
        }
        stack.add(node);
    }

    // node from which all path lead to teminal nodes
    private static Set<Integer> getSafeNodes(int v, boolean[] vis, boolean[] pathvis, List<List<Integer>> graph) {
        Set<Integer> set=new HashSet<>();
        for (int i = 0; i <v ; i++) {
            if(!vis[i]){
                checkSafeNodes(i,vis,pathvis,set,graph);
            }
        }
        return set;
    }

    // if call of a node is completed that is safe node
    private static boolean checkSafeNodes(int node, boolean[] vis, boolean[] pathvis, Set<Integer> set, List<List<Integer>> graph) {
        vis[node]=true;
        pathvis[node]=true;
        for (int child:graph.get(node)){
             if(vis[child] && pathvis[child]==true) return true;
             if(!vis[child]){
                if(checkSafeNodes(child,vis,pathvis,set, graph)) return true;
            }
        }
        set.add(node);
        pathvis[node]=false;
        return false;
    }

    private static boolean checkCycleInDirected(int v, boolean[] vis, boolean[] pathvis, List<List<Integer>> graph) {
        Set<Integer> set=new HashSet<>();
        for (int i = 0; i <v ; i++) {
            if(!vis[i]){
                if(isCycleInDirected(i,vis,pathvis,graph))return true;
            }
        }
        return false;
    }
    private static boolean isCycleInDirected(int node, boolean[] vis, boolean[] pathvis, List<List<Integer>> graph) {
        vis[node]=true;
        pathvis[node]=true;
        for (int child:graph.get(node)){
            if(vis[child] && pathvis[child]==true) return true;
            else if(!vis[child]){
                if(isCycleInDirected(child,vis,pathvis, graph)) return true;
            }
        }
        pathvis[node]=false;
        return false;
    }


    //if graph has mulitple componenet it will have multiple startNode
    // repeat for each node

    private static boolean checkPartitieGraphDfs(int startNode, int V, List<List<Integer>> graph) {
        int col[]= new int[V];
        Arrays.fill(col,-1);
        //parent,neighbour,0
        col[startNode]=0;
        for (int node = startNode; node < V; node++) {
            if (col[node] == -1) {
                if (doPartitieGraphDfs(node, 0, col, graph) == false) return false;
            }
        }
        return true;
    }

    private static boolean doPartitieGraphDfs(int parent, int pcol, int[] col, List<List<Integer>> graph) {

        col[parent] = pcol;
        for (int child : graph.get(parent)) {
            if (col[parent] != -1 && col[parent] == col[child]) return false;
            else if (col[child] == -1) {
                if (doPartitieGraphDfs(child, 1 - pcol, col, graph) == false) return false;
            }
        }
        return true;
    }

    //if graph has mulitple componenet it will have multiple startNode
    // repeat for each node
    private static boolean biPartitieGraph(int startNode, int V, List<List<Integer>> graph) {
        int col[]= new int[V];
        Arrays.fill(col,-1);
        //node
        Queue<int[]> queue =new ArrayDeque<>();
        queue.add(new int[]{startNode,-1});
        col[startNode]=0;
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int node= polled[0];
            for (int neighbour : graph.get(node)) {
                if (col[node]!=-1 && col[node] == col[neighbour]) return false;
                else if (col[neighbour] == -1) {
                    col[neighbour] = 1- col[node];
                    queue.add(new int[]{neighbour});
                }
            }
        }
        return true;
    }

    private static int numberOfDistinctIsland(int m, int n, int[][] grid) {
        boolean vis[][]=new boolean[m][n];
        Set<List<Integer>> shape=new HashSet<>();
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                if(!vis[i][j] && grid[i][j]==1 ){
                    List<Integer> temp=new ArrayList<>();
                    visitAll4CornerForShape(i,j,i,j,m,n,vis, grid ,temp);
                    shape.add(temp);
                }
            }
        }

        System.out.println(shape);
        return shape.size();
    }

    private static void visitAll4CornerForShape(int basei, int basej, int i, int j, int m, int n, boolean[][] vis, int[][] grid, List<Integer> temp) {
         if(isValidCell(i,j,m,n) && !vis[i][j] && grid[i][j]==1){
             vis[i][j]=true;
             temp.addAll(List.of(i-basei,j-basej));
             visitAll4CornerForShape(basei,basej,i+1,j,m,n,vis, grid ,temp);
             visitAll4CornerForShape(basei,basej,i-1,j,m,n,vis, grid ,temp);
             visitAll4CornerForShape(basei,basej,i,j+1,m,n,vis, grid ,temp);
             visitAll4CornerForShape(basei,basej,i,j-1,m,n,vis, grid ,temp);
         }
    }

    private static int numberOfEnclaves(int n, int m, int[][] grid) {
        Queue<int[]> queue=new ArrayDeque<>();
        boolean vis[][]=new boolean[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <m ; j++) {
                if((i==0 || i== n-1 || j==0 || j== m-1) && grid[i][j]==1) queue.add(new int[]{i,j});
            }
        }

        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int i =polled[0];
            int j =polled[1];
            if(!vis[i][j]){
                vis[i][j]=true;
                visitAll4CornerEnclaves(i+1,j,n,m,vis,grid,queue);
                visitAll4CornerEnclaves(i-1,j,n,m,vis,grid,queue);
                visitAll4CornerEnclaves(i,j+1,n,m,vis,grid,queue);
                visitAll4CornerEnclaves(i,j-1,n,m,vis,grid,queue);
            }
        }


        int count=0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(grid[i][j]==1 && !vis[i][j]){
                    count++;
                }
            }
        }
        return count;
    }

    private static void visitAll4CornerEnclaves(int di, int dj, int n, int m, boolean[][] vis, int [][] grid, Queue<int[]> queue) {
        if (isValidCell(di, dj, n, m) && !vis[di][dj] && grid[di][dj]==1) {
            queue.add(new int[]{di, dj});
        }
    }
    private static char[][] convertXToOs(int n, int m, char[][] grid) {
        Queue<int[]> queue=new ArrayDeque<>();

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <m ; j++) {
                if((i==0 || i== n-1 || j==0 || j== m-1) && grid[i][j]=='o') queue.add(new int[]{i,j});
            }
        }
        boolean vis[][]=new boolean[m][n];
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int i =polled[0];
            int j =polled[1];
            if(!vis[i][j]){
                vis[i][j]=true;
                visitAll4CornerXTos(i+1,j,n,m,vis,grid,queue);
                visitAll4CornerXTos(i-1,j,n,m,vis,grid,queue);
                visitAll4CornerXTos(i,j+1,n,m,vis,grid,queue);
                visitAll4CornerXTos(i,j-1,n,m,vis,grid,queue);
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <m ; j++) {
                if(!vis[i][j] && grid[i][j]=='o') grid[i][j]='x';
            }
        }

        return grid;
    }

    private static void visitAll4CornerXTos(int di, int dj, int n, int m, boolean[][] vis, char[][] grid, Queue<int[]> queue) {
        if (isValidCell(di, dj, n, m) && !vis[di][dj] && grid[di][dj]=='o') {
            queue.add(new int[]{di, dj});
        }
    }

    private static int[][] distanceNearestCellhaving1(int n, int m, int[][] graph) {
        Queue<int[]> queue=new ArrayDeque<>();
        boolean vis[][]=new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (graph[i][j] == 1) {
                    queue.add(new int[]{i, j, 0});
                    vis[i][j] = true;
                }
            }
        }
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            int i=polled[0];
            int j=polled[1];
            int dist=polled[2];
            graph[i][j]=dist;

                visitAll4CornerForNearest1s(i + 1, j, n, m, vis, dist, graph, queue);
                visitAll4CornerForNearest1s(i - 1, j, n, m, vis, dist, graph, queue);
                visitAll4CornerForNearest1s(i, j + 1, n, m, vis, dist, graph, queue);
                visitAll4CornerForNearest1s(i, j - 1, n, m, vis, dist, graph, queue);

        }
        return graph;
    }

    private static void visitAll4CornerForNearest1s(int di, int dj, int n, int m, boolean[][] vis, int dist, int[][] graph, Queue<int[]> queue) {
        if (isValidCell(di, dj, n, m) && !vis[di][dj] && graph[di][dj]==0) {
            vis[di][dj] = true;
            queue.add(new int[]{di, dj, dist + 1});
        }
    }

    private static boolean isCycleDfs(int startNode, List<List<Integer>> graph) {
        boolean []vis=new boolean[graph.size()];
        return doCheckDfsCycle(startNode,-1,vis,graph);
    }

    private static boolean doCheckDfsCycle(int startNode, int parent, boolean[] vis, List<List<Integer>> graph) {
        vis[startNode]=true;
        for(int adjacent:graph.get(startNode)){
            if(vis[adjacent] && adjacent!=parent) return true;
            else if(!vis[adjacent]){
                if(doCheckDfsCycle(adjacent,startNode,vis,graph)) return true;
            }
        }
        return false;
    }

    private static boolean isCycleBfs(int startNode, List<List<Integer>> graph) {
        //node,parent
        Queue<int[]> queue=new ArrayDeque<>();
        queue.add(new int[]{startNode,-1});
        boolean []vis=new boolean[graph.size()];
        vis[startNode]=true;
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int node = polled[0];
            int parent = polled[1];
            for (int neighbour : graph.get(node)) {
                if (vis[neighbour] && parent != neighbour) return true;
                else if (!vis[neighbour]) {
                    queue.add(new int[]{neighbour, node});
                    vis[neighbour]=true;
                }
            }
        }
        return false;
    }

    private static int[][] floodFill(int sr, int sc, int oldColor, int newColor, int n, int m, int[][] grid) {

        Queue<int []> queue=new ArrayDeque<>();
        queue.add(new int[]{sr,sc,oldColor});
        boolean [][]vis=new boolean[n][m];
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            if(!vis[polled[0]][polled[1]]){
            dfsFlood(polled[0],polled[1],polled[2],newColor,n,m,vis,grid);
                vis[polled[0]][polled[1]]=true;
            }
        }
        return grid;
    }

    private static void dfsFlood(int row, int col, int oldColor, int newColor, int n, int m, boolean[][] vis, int[][] grid) {
         if(row>=0 && row<n && col>=0 && col<m && !vis[row][col] && grid[row][col]==oldColor){
             grid[row][col]=newColor;
             vis[row][col]=true;
         }

        dfsFlood(row+1,col,oldColor,newColor,n,m,vis,grid);
        dfsFlood(row-1,col,oldColor,newColor,n,m,vis,grid);
        dfsFlood(row,col+1,oldColor,newColor,n,m,vis,grid);
        dfsFlood(row,col-1,oldColor,newColor,n,m,vis,grid);
    }

    private static int numberOfIsland(int m, int n, int[][] grid) {
        boolean vis[][]=new boolean[m][n];
        int count=0;
        for (int i = 0; i <m ; i++) {
            for (int j = 0; j <n ; j++) {
                if(grid[i][j]==1 && !vis[i][j]){
                    count++;
                    visitAll8Corner(i,j,m,n,vis,grid);
                    vis[i][j]=true;
                }
            }
        }
        return count;
    }

    private static void visitAll8Corner(int i, int j, int m, int n, boolean[][] vis, int[][] grid) {
        Queue<int[]> queue=new ArrayDeque<>();
        queue.add(new int[]{i,j});
        while (!queue.isEmpty()){
            int[] polled = queue.poll();
            for (int di = -1; di <=1 ; di++) {
                for (int dj = -1; dj <=1 ; dj++) {
                    if(di==dj)continue;
                    int newRow=polled[0]+di;
                    int newCol=polled[1]+dj;
                    if(isValidCell(newRow,newCol,m,n) && grid[newRow][newCol]==1 && !vis[newRow][newCol]){
                        queue.add(new int[]{newRow,newCol});
                        vis[newRow][newCol]=true;
                    }
                }
            }
        }
    }

    private static boolean isValidCell(int newRow, int newCol, int m, int n) {
        if(newRow>=0 && newRow<m && newCol>=0 && newCol<n) return true;
        return false;
    }

    private static int numberOfProvinces(int totNumberOfNodes, List<List<Integer>> graph) {
        // System.out.println("totNumberOfNodes:"+totNumberOfNodes);
        int provinces = 0;
        boolean vis[] = new boolean[totNumberOfNodes];
        for (int i = 1; i < totNumberOfNodes; i++) {
            if (!vis[i]) {
                graphBfs(i, vis ,graph);
                provinces++;
            }
        }
        return provinces;
    }

    private static void graphDfs(int startNode, int totalNodes, List<List<Integer>> graph) {
        boolean[] vis = new boolean[totalNodes];
        for (int i = startNode; i < totalNodes; i++) {
            if (!vis[i]) {
                doGraphDfs(startNode,vis,graph);
            }
        }
    }

    private static void doGraphDfs(int startNode, boolean[] vis, List<List<Integer>> graph) {
        vis[startNode]=true;
        System.out.print(startNode+" ");
        for (Integer neighbour:graph.get(startNode)){
            if(!vis[neighbour]){
                doGraphDfs(neighbour,vis,graph);
            }
        }
    }

    //graph starts
    private static void graphBfs(int startNode, boolean[] vis, List<List<Integer>> graph) {
        Queue<Integer> queue=new ArrayDeque<>();
        queue.add(startNode);
        vis[startNode]=true;
        while (!queue.isEmpty()){
            Integer polled = queue.poll();
            System.out.print(polled+" ");
            for (Integer neighbour:graph.get(polled)) {
                if (!vis[neighbour]) {
                    queue.add(neighbour);
                    vis[neighbour] = true;
                }
            }
        }
    }

    private static void tree() {
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(15);
        root.right = new TreeNode(40);


        root.left.left = new TreeNode(14);
        root.left.right = new TreeNode(18);


        root.right.left = new TreeNode(30);
        root.right.right = new TreeNode(60);

        int []ar={3,5,7,8,10,15,20,25};
        TreeNode[] violation=new TreeNode[3];
        TreeNode[] prev=new TreeNode[1];
        List<Integer> list =new ArrayList<>();
        getLeftView(list,1,root);
        System.out.println(list);

    }

    // {size,max_val,min_val}
    private static int[] largetBST(TreeNode root) {
        if(root ==null ) return new int[]{0,Integer.MIN_VALUE,Integer.MAX_VALUE};
      //  if(root.left ==null && root.right == null ) return new int[]{1,root.data,root.data};
        int[] left = largetBST(root.left);
        int[] right = largetBST(root.right);
        if(left[1]<root.data && root.data < right[2]){
            return new int[]{1+left[0]+right[0],Math.max(root.data,right[1]),Math.min(root.data,left[1])};
        }//carry max value and max as max and min as min so that all comparisons ruled out
        return new int[]{Math.max(left[0],right[0]),Integer.MAX_VALUE,Integer.MIN_VALUE};
    }

    private static void recCoverBSTNode(TreeNode[] violation, TreeNode[] prev, TreeNode root) {
        recCoverBST(violation,prev , root);
        if(violation[2]==null){
            swapNodeValue(violation[0],violation[1]);
        }
        else{
            swapNodeValue(violation[0],violation[2]);
        }
    }

    private static void swapNodeValue(TreeNode node1, TreeNode node2) {
        int temp= node1.data;
        node1.data=node2.data;
        node2.data=temp;
    }

    //inorder
    private static void recCoverBST(TreeNode[] violation, TreeNode[] prev, TreeNode root) {
        if (root == null) return;
        recCoverBST(violation, prev, root.left);
        if (prev[0] != null && prev[0].data > root.data) {
            if (violation[0] == null) {
                violation[0] = prev[0];
                violation[1] = root;
            } else if (violation[2] == null) {
                violation[2] = root;
            }
        }
        prev[0] = root;
        recCoverBST(violation, prev, root.right);
    }

    private static boolean twoSumBST(int val, TreeNode root) {
        BSTIterator startBstIterator = new BSTIterator(root,false);
        BSTIterator endBstIterator = new BSTIterator(root,true);
        int start = startBstIterator.next() ;
        int end = endBstIterator.next();
        while (start < end){
            int sum=start + end;
            System.out.println("start:"+start+",end:"+end);
            if(sum==val) return true;
            if(sum<val) {
                start=startBstIterator.next();
            }
            else {
                end=endBstIterator.next();
            }
        }
        return false;
    }

    static class BSTIterator{
        Stack<TreeNode> stack;
        boolean reverse;

        public BSTIterator(TreeNode root , boolean reverse) {
            this.stack = new Stack<>();
            this.reverse = reverse;
            pushAllToStack(root);
        }

        private void pushAllToStack(TreeNode root) {
            TreeNode temp = root;
            while (temp != null) {
                stack.add(temp);
                if (reverse) temp = temp.right;
                else temp = temp.left;
            }
        }

        public int next() {
            TreeNode pop = null;
            if (this.hasNext()) {
                pop = stack.pop();
                if (reverse) pushAllToStack(pop.left);
                else pushAllToStack(pop.right);
            }
            return pop == null ? -1 : pop.data;
        }

        public boolean hasNext(){
            return !stack.isEmpty();
        }
    }

    private static int bstSuccessorLoop(int val, TreeNode root) {

        int successor = 0;
        while (root != null) {
            if (val >= root.data) root = root.right;
            else {
                successor = root.data;
                root = root.left;
            }
        }
        return successor;
    }

    private static void bstSuccessor(int val, int[] successor, TreeNode root) {
        if (root == null) return ;

        if (root.data >= val && successor[0] > root.data) successor[0] = root.data;
        if (val > root.data)  bstSuccessor(val, successor, root.right);
         bstSuccessor(val, successor, root.left);
    }

    //{8,5,1,7,10,12}
    private static TreeNode bSTFromPreOrder(int[] i, int maxValue, int[] preOrder) {
        if (i[0] == preOrder.length || maxValue < preOrder[i[0]]) return null;
        TreeNode root = new TreeNode(preOrder[i[0]++]);
        root.left = bSTFromPreOrder(i, root.data, preOrder);
        root.right = bSTFromPreOrder(i ,maxValue, preOrder);
        return root;
    }

    private static TreeNode lcaBST(int p, int q, TreeNode root) {
        if(root==null) return null;
        // p,q both are smaller than root.data
        if(p< root.data && q < root.data) return lcaBST(p,q,root.left);
        //both are greater than root.data
        if(p > root.data && q > root.data) return lcaBST(p,q,root.right);
        return root;
    }

    private static boolean isBST(int minValue, TreeNode root, int maxValue) {
        if(root==null) return true;
        return minValue<root.data && root.data<maxValue &&
                isBST(minValue,root.left,root.data)
                && isBST(root.data,root.right,maxValue);
    }

    private static void nthSmallest(int requiredCount, int[] currentCount, TreeNode root) {
        if (root == null) return;
        nthSmallest(requiredCount, currentCount, root.left);
        currentCount[0]++;
        if (currentCount[0] == requiredCount) {
            System.out.print("root.data:" + root.data);
            return;
        }
        nthSmallest(requiredCount, currentCount, root.right);
    }

    private static TreeNode removeBST(int val, TreeNode parent, TreeNode root) {
        // root node deletion
        if (root.data == val) {
            root.left.right = root.right;
            return root.left;
        } else return removeBSTNode(val, parent, root);

    }

    private static TreeNode removeBSTNode(int val, TreeNode parent, TreeNode node) {
        if (node == null) return null;

        if (node.data == val) {
            // parent.left ==root
            if (node.left == null) {
                if (parent.left == node) parent.left = node.right;
                else parent.right = node.right;
            } else if (node.right == null) {
                if (parent.left == node) parent.left = node.left;
                else parent.right = node.left;
            } else {
                TreeNode temp = node.left;
                while (temp.right != null) {
                    temp = temp.right;
                }
                temp.right = node.right;
                if (parent.left == node) parent.left = node.left;
                else parent.right = node.left;
            }
        }
        if (val > node.data) return removeBSTNode(val, node, node.right);
        return  removeBSTNode(val, node, node.left);
    }

    private static void insertBST(int val, TreeNode root) {
        if (root == null) return;
        if ( root.left == null && root.right ==null) {
            if(val<root.data) root.left=new TreeNode(val);
            else root.right=new TreeNode(val);
            return;
        }
        if (val > root.data) {
            insertBST(val, root.right);
        } else {
            insertBST(val, root.left);
        }
    }
    private static int floorBSTLoop(int key, TreeNode root) {
        int ceil = -1;
        while (root != null) {
            if (root.data == key) {
                ceil = root.data; break;
            }
            if (key > root.data) {
                ceil = root.data;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return ceil;
    }
    private static void floorBST(int key, int[] ceil, TreeNode root) {
        if (root == null) return;
        if (key == root.data) {
            ceil[0] = root.data;
            return;
        }
        if(root.data<=key && root.data > ceil[0]) ceil[0] = root.data;
        if (key > root.data) {
            floorBST(key, ceil, root.right);
        } else {
            floorBST(key, ceil, root.left);
        }
    }
    private static int ceilBSTLoop(int key, TreeNode root) {
        int ceil = -1;
        while (root != null) {
            if (root.data == key) {
                ceil = root.data; break;
            }
            if (key > root.data) {
                root = root.right;
            } else {
                ceil = root.data;
                root = root.left;
            }
        }
        return ceil;
    }

    private static void ceilBST(int key, int[] ceil, TreeNode root) {
        if (root == null) return;
        if (key == root.data) {
            ceil[0] = root.data;
            return;
        }
        if(root.data>=key && root.data < ceil[0]) ceil[0] = root.data;
        if (key > root.data) {
            ceilBST(key, ceil, root.right);
        } else {
            ceilBST(key, ceil, root.left);
        }
    }

    private static TreeNode searchBST(int val, TreeNode root) {
        if(root == null) return null;
        if(val==root.data) return root;
        if(val > root.data) return searchBST(val,root.right);
        return searchBST(val,root.left);
    }

    private static void flattenBinaryTreeUsingMorrisTraversal(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode prev = cur.left;
                while (prev.right != null) {
                    prev = prev.right;
                }
                prev.right = cur.right;
                cur.right = cur.left;
                //note this
                cur.left=null;
            }
            cur = cur.right;
        }
    }

    private static void flattenBinaryTreeUsingStack(TreeNode root) {
        Stack<TreeNode> stack=new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()){
            TreeNode popped = stack.pop();
            if(popped.right!=null) stack.add(popped.right);
            if(popped.left!=null) stack.add(popped.left);

            if(!stack.isEmpty())popped.right = stack.peek();
            popped.left=null;
        }
    }

    private static void flattenBinaryTree(TreeNode node, TreeNode[] prev) {
        if(node==null ) return ;

        flattenBinaryTree(node.right, prev);
        flattenBinaryTree(node.left, prev);

        node.right=prev[0];
        prev[0]=node;
        node.left=null;
    }

    //single line change from moved adding to list in else block from if block
    private static List<Integer> morrisPreOrderTraversal(TreeNode root) {
        List<Integer> inOrder =new ArrayList<>();
        TreeNode cur=root;
        while (cur!=null){
            //case 1
            if(cur.left ==null){
                inOrder.add(cur.data);
                cur=cur.right;
            }
            else {
                TreeNode prev=cur.left;
                while (prev.right!=null || prev.right!=cur){
                    prev=prev.right;
                }
                if(prev.right ==null){
                    //preOrder
                    inOrder.add(cur.data);
                    prev.right=cur;
                    cur=cur.left;
                }
                else {
                    prev.right=null;
                    cur=cur.right;
                }
            }
        }
        return inOrder;
    }

    private static List<Integer> morrisInOrderTraversal(TreeNode root) {
        List<Integer> inOrder = new ArrayList<>();
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                inOrder.add(cur.data);
                cur = cur.right;
            } else {
                TreeNode prev = cur.left;
                while (prev.right != null || prev.right != cur) {
                    prev = prev.right;
                }
                if (prev.right == null) {
                    prev.right = cur;
                    cur = cur.left;
                } else {
                    prev.right = null;
                    //inOrder
                    inOrder.add(cur.data);
                    cur = cur.right;
                }
            }
        }
        return inOrder;
    }

    private static void serializeDeSerialize(TreeNode root) {
        StringBuffer serialize = serialize(root);
        // split on ,
        String[] s = Arrays.stream(String.valueOf(serialize).split(",")).toArray(String[]::new);
        TreeNode deserialize = deSerialize(0,s);
        System.out.println(isSameTree(deserialize, root));
    }

    private static TreeNode deSerialize(int ind, String[] s) {
        Queue<TreeNode> queue =new ArrayDeque<>();

        TreeNode root = new TreeNode(Integer.valueOf(s[ind++]));
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode polled = queue.poll();
            String leftVal = s[ind++];
            if (!leftVal.equals("#")) {
                polled.left = new TreeNode(Integer.valueOf(leftVal));
                queue.add(polled.left);
            }

            String rightVal = s[ind++];
            if (!rightVal.equals("#")) {
                polled.right = new TreeNode(Integer.valueOf(rightVal));
                queue.add(polled.right);
            }
        }
        return root;
    }

    private static StringBuffer serialize(TreeNode root) {
        Queue<TreeNode> queue=new ArrayDeque<>();
        queue.add(root);
        StringBuffer buffer=new StringBuffer();
        buffer.append(root.data + ",");
        while (!queue.isEmpty()){
             int size=queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode polled = queue.poll();
                if (polled.left != null) {
                    queue.add(polled.left);
                    buffer.append(polled.left.data + ",");
                } else buffer.append("#,");
                if (polled.right != null) {
                    queue.add(polled.right);
                    buffer.append(polled.right.data + ",");
                }
                else buffer.append("#,");
              //  System.out.print(polled.data+" ");
            }
           // System.out.println();
        }
       return buffer;
    }

    private static TreeNode constructBinaryTreeeFromPostAndInOrder(int[] inOrder, int inStart, int inEnd, int[] preOrder, int postStart, int postEnd) {
        //value,Index
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i <=inEnd ; i++) {
            map.put(inOrder[i],i);
        }
        return   constructBinaryTreeeFromPostAndInOrder(inOrder,inStart,inEnd,preOrder,postStart,postEnd,map);
    }

    private static TreeNode constructBinaryTreeeFromPostAndInOrder(int[] inOrder, int inStart, int inEnd, int[] postOrder, int postStart, int postEnd, Map<Integer, Integer> map) {
        if(postStart>postEnd || inStart > inEnd) return null;

        TreeNode root=new TreeNode(postOrder[postEnd]);
        Integer indexOfRoot = map.get(postOrder[postEnd]);
        int numsLeft= indexOfRoot - inStart;
        root.left= constructBinaryTreeeFromPostAndInOrder(inOrder,inStart,indexOfRoot-1,postOrder,postStart,postStart+numsLeft-1,map);
        root.right= constructBinaryTreeeFromPostAndInOrder(inOrder,indexOfRoot+1,inEnd,postOrder,postStart+numsLeft,postEnd-1,map);
        return root;

    }

    private static TreeNode constructBinaryTreeeFromPreAndInOrder(int[] inOrder, int inStart, int inEnd, int[] preOrder, int preStart, int preEnd) {
        //value,Index
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i <=inEnd ; i++) {
            map.put(inOrder[i],i);
        }
       return   constructBinaryTreeeFromPreAndInOrder(inOrder,inStart,inEnd,preOrder,preStart,preEnd,map);
    }

    private static TreeNode constructBinaryTreeeFromPreAndInOrder(int[] inOrder, int inStart, int inEnd, int[] preOrder, int preStart, int preEnd, Map<Integer, Integer> map) {
        if(preStart>preEnd || inStart > inEnd) return null;

        TreeNode root=new TreeNode(preOrder[preStart]);
        Integer indexOfRootInPreOrder = map.get(preOrder[preStart]);
        int numsLeft= indexOfRootInPreOrder - inStart;
        root.left= constructBinaryTreeeFromPreAndInOrder(inOrder,inStart,indexOfRootInPreOrder-1,preOrder,preStart+1,preStart+numsLeft,map);
        root.right= constructBinaryTreeeFromPreAndInOrder(inOrder,indexOfRootInPreOrder+1,inEnd,preOrder,preStart+1+numsLeft,preEnd,map);
        return root;

    }


    private static int totalNodeCompleteBinaryTree(TreeNode root) {
        if(root ==null) return 0;
        int lh= completeTreeKLeftHeight(root.left);
        int rh= completeTreeKRighttHeight(root.right);

        if(lh==rh)
           return (1<<lh)-1;
        return 1+totalNodeCompleteBinaryTree(root.left)+totalNodeCompleteBinaryTree(root.right);

    }

    private static int completeTreeKRighttHeight(TreeNode right) {
        int count=0;
        while (right!=null){
            count++; right=right.left;
        }
        return count;
    }

    private static int completeTreeKLeftHeight(TreeNode left) {
        int count=0;
        while (left!=null){
            count++; left=left.left;
        }
        return count;
    }

    private static int burnTime(TreeNode node, TreeNode root) {
        class TTreeNode {
            TreeNode root;
            int val;

            public TTreeNode(TreeNode root, int val) {
                this.root = root;
                this.val = val;
            }
        }
        Map<TreeNode , TreeNode> map=new HashMap<>();
        assignAllParent(map,root);
        Queue<TTreeNode> queue=new ArrayDeque<>();
        Set<TreeNode> visited=new HashSet<>();
        queue.add(new TTreeNode(node,0));
        int burnTime=0;
        while (!queue.isEmpty()) {
            TTreeNode polled = queue.poll();
            burnTime = Math.min(burnTime,polled.val);
            TreeNode polledRoot = polled.root;
            if (!visited.add(polledRoot)) continue;
            if (polledRoot.left != null && !visited.contains(polledRoot.left))
                queue.add(new TTreeNode(polledRoot.left, burnTime + 1));
            if (polledRoot.right != null && !visited.contains(polledRoot.right))
                queue.add(new TTreeNode(polledRoot.right, burnTime + 1));
            TreeNode parent = map.get(polled.root);
            if (parent != null && !visited.contains(parent)) queue.add(new TTreeNode(parent, burnTime + 1));
        }
       return burnTime;
    }

    private static void nodeAtDistanceK(TreeNode node, int k, TreeNode root) {
        class TTreeNode {
            TreeNode root;
            int val;

            public TTreeNode(TreeNode root, int val) {
                this.root = root;
                this.val = val;
            }
        }
        Map<TreeNode , TreeNode> map=new HashMap<>();
        assignAllParent(map,root);
        Queue<TTreeNode> queue=new ArrayDeque<>();
        Set<TreeNode> visited=new HashSet<>();
        queue.add(new TTreeNode(node,0));
        while (!queue.isEmpty()) {
            TTreeNode polled = queue.poll();
            int dist = polled.val;
            TreeNode polledRoot = polled.root;
            if (!visited.add(polledRoot)) continue;
            if (dist == k) {
                System.out.println(polled.root.data);
                while (!queue.isEmpty()) {
                    System.out.println(queue.poll().root.data + " ");
                }
                break;
            } else {
                if (polledRoot.left != null && !visited.contains(polledRoot.left))
                    queue.add(new TTreeNode(polledRoot.left, dist + 1));
                if (polledRoot.right != null && !visited.contains(polledRoot.right))
                    queue.add(new TTreeNode(polledRoot.right, dist + 1));
                TreeNode parent = map.get(polled.root);
                if (parent != null && !visited.contains(parent)) queue.add(new TTreeNode(parent, dist + 1));
            }
        }

    }

    private static void assignAllParent(Map<TreeNode, TreeNode> map, TreeNode root) {
        if(root ==null) return;
        if(root.left!=null) map.put(root.left,root);
        if(root.right!=null) map.put(root.right,root);
        assignAllParent(map,root.left);
        assignAllParent(map,root.right);
    }

    private static void childrenSumProperty(TreeNode root) {
        if(root ==null || root.left == null || root.right ==null) return ;
        // if sum of left and right is smaller than root
        if( root.data > root.left.data + root.right.data){
            root.left.data=root.data;
            root.right.data=root.data;
        }
        else root.data = root.left.data + root.right.data;

        childrenSumProperty(root.left);
        childrenSumProperty(root.right);

        root.data = root.left.data +root.right.data;
    }

    //max distance b/w 2 node on a level
    // used (polled.val*2)+1 -left to reduce size of left and right
    private static int maxWidth(TreeNode root) {
        class TTreeNode {
            TreeNode root;
            int val;

            public TTreeNode(TreeNode root, int val) {
                this.root = root;
                this.val = val;
            }
        }
        Queue<TTreeNode> queue= new ArrayDeque<>();
        queue.add(new TTreeNode(root,0));
        int maxWidth=0;
        //level order traversal
        while (!queue.isEmpty()){
            int size=queue.size(); int left=0 , right=0;
            for (int i = 0; i < size ; i++) {
                TTreeNode polled = queue.poll();
               // System.out.print(polled.root.data+" ");
                if(i==0)left= polled.val;
                TreeNode polledroot = polled.root;
                if(polledroot.left!=null)   queue.add(new TTreeNode(polledroot.left,(polled.val*2)+1 -left));
                if(polledroot.right!=null)  queue.add(new TTreeNode(polledroot.right,(polled.val*2)+2-left));
                right= polled.val;
            }
            maxWidth=Math.max(maxWidth,right-left+1);
        }
        return maxWidth;
    }

    private static TreeNode lca(TreeNode root, int p, int q) {
        if (root == null || root.data == p || root.data == q) return root;
        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    private static boolean printRootToNode(TreeNode root, int data, List<Integer> temp) {
        if(root == null) return false;
        temp.add(root.data);
        if(root.data ==data)  { System.out.println(temp); return true;}

        if(printRootToNode(root.left,data,temp) || printRootToNode(root.right,data,temp) )return true;
        temp.remove(temp.size()-1);

        return false;
    }

    private static boolean isMirrorOrSymmetric(TreeNode root) {
        if(root==null) return true;
        return checkMirror(root.left,root.right);
    }

    private static boolean checkMirror(TreeNode left, TreeNode right) {
        if(left == null || right ==null) return left==right;
        if(left.data!=right.data) return false;
        return checkMirror(left.left,right.right) && checkMirror(left.right,right.left);
    }

    // using   if(list.size() == level) list.add(root.data);
    private static void rightViewUsingList(int level, List<Integer> list, TreeNode root) {
        if(root ==null) return;
        if(list.size() == level) list.add(root.data);
        rightViewUsingList(level+1,list,root.right);
        rightViewUsingList(level+1,list,root.left);
    }
    private static void getLeftView(int level, Map<Integer, Integer> map, TreeNode root) {
        if(root ==null) return;
        map.putIfAbsent(level,root.data);
        getLeftView(level+1,map,root.left);
        getLeftView(level+1,map,root.right);
    }
    private static void getRightView(int level, Map<Integer, Integer> map, TreeNode root) {
        if(root ==null) return;
        map.putIfAbsent(level,root.data);
        getRightView(level+1,map,root.right);
        getRightView(level+1,map,root.left);
    }

    private static void bottomView(int vertical, Map<Integer, Integer> map, TreeNode root) {
        if(root ==null) return;
        map.put(vertical,root.data);
        bottomView(vertical-1,map,root.left);
        bottomView(vertical+1,map,root.right);
    }

    private static void topView(int vertical, Map<Integer, Integer> map, TreeNode root) {
        if(root ==null) return;
        map.putIfAbsent(vertical,root.data);
        topView(vertical-1,map,root.left);
        topView(vertical+1,map,root.right);
    }

    private static void verticalOrder(int vertical, Map<Integer, List<Integer>> map, TreeNode root) {
        if(root ==null) return;
        map.computeIfAbsent(vertical,k->new ArrayList<>()).add(root.data);
        verticalOrder(vertical-1,map,root.left);
        verticalOrder(vertical+1,map,root.right);

    }

    private static List<Integer> boundryTraverSal(TreeNode root) {
         List<Integer> ans=new ArrayList<>();
         ans.add(root.data);
         getLeftView(ans,1,root.left);
         getLeafNodes(ans ,root);
         getRightView(ans,1,root.right);
         System.out.println(ans);
         return ans;
    }

    private static void getRightView(List<Integer> ans, int level, TreeNode root) {
        if(root ==null ) return;
         if(level==ans.size())ans.add(root.data);
        getRightView(ans,level+1,root.right);
        getRightView(ans,level+1,root.left);

    }

    private static void getLeafNodes(List<Integer> ans, TreeNode root) {
        if(root ==null ) return;
        if(root.left ==null && root.right ==null) ans.add(root.data);
        getLeafNodes(ans,root.left);
        getLeafNodes(ans,root.right);
    }

    private static void getLeftView(List<Integer> ans, int level, TreeNode root) {
        if(root ==null) return;
        if(level==ans.size()) ans.add(root.data);
        getLeftView(ans,level+1,root.left);
        getLeftView(ans,level+1,root.right);
    }

    private static List<List<Integer>> zigZagTraversal(TreeNode root) {
         Queue<TreeNode> queue=new ArrayDeque<>();
         queue.add(root);
         // 0 means l to r , 1 mean r to l
         boolean left_to_rigth=true;
         List<List<Integer>> ans=new LinkedList<>();

         while (!queue.isEmpty()){
             int size=queue.size();
             List<Integer> temp=new LinkedList<>();
             for (int i = 0; i <size ; i++) {
                 TreeNode polled = queue.poll();
                 temp.add(polled.data);
                 if(polled.left!=null) queue.add(polled.left);
                 if(polled.right!=null) queue.add(polled.right);
             }
             if(left_to_rigth)ans.add(temp);
             else{
                 Collections.reverse(temp);
                 ans.add(temp);
             }
             left_to_rigth=!left_to_rigth;
         }
         return ans;
    }

    private static boolean isSameTree(TreeNode root1, TreeNode root2) {
        if(root1==null || root2==null) return root1==root2;
        return
                root1.data==root2.data && isSameTree(root1.left,root2.left)
                 && isSameTree(root1.right,root2.right);
    }

    private static int maxPathSum(int[] val, TreeNode root) {
        if(root ==null)return 0;
        int lh=Math.max(0,maxPathSum(val, root.left));
        int rh=Math.max(0,maxPathSum(val, root.right));
        val[0]=Math.max(val[0],root.data+lh+rh);
        return root.data+Math.max(lh,rh);
    }

    //longest path b/w 2 node , with/without root
    private static int diameter(int[] val, TreeNode root) {
        if(root ==null)return 0;
        int lh=diameter(val, root.left);
        int rh=diameter(val, root.right);
        val[0]=Math.max(val[0],lh+rh);
        return 1+Math.max(lh,rh);
    }

    private static boolean checkBalancedHeightTree(TreeNode root) {
        return isBalancedBinaryTree(root) == -1 ? false : true;
    }

    //height diff is max 1
    private static int isBalancedBinaryTree(TreeNode root) {
        if(root ==null ) return 0;

        int lh= isBalancedBinaryTree(root.left);
        if(lh==-1) return -1;

        int rh= isBalancedBinaryTree(root.right);
        if(rh==-1) return -1;

        if(Math.abs(lh-rh)>1) return -1;

        return 1+Math.max(lh,rh);

    }

    //Or, depth
    private static int treeHeight(TreeNode root) {
        if(root==null) return 0;
        return 1+Math.max(treeHeight(root.left),treeHeight(root.right));
    }

    private static void allIterativeTraversal(TreeNode root) {
        List<Integer> pre=new ArrayList<>();
        List<Integer> in=new ArrayList<>();
        List<Integer> post=new ArrayList<>();
        Stack<TreeNode[]> queue=new Stack<>();
        queue.add(new TreeNode[]{root,new TreeNode(1)});
        while (!queue.isEmpty()) {
            TreeNode[] polled = queue.pop();
            TreeNode val = polled[1];
            TreeNode node = polled[0];
            if (val.data == 1) {
                pre.add(node.data);
                queue.add(new TreeNode[]{node, new TreeNode(2)});
                if (node.left != null) queue.add(new TreeNode[]{node.left, new TreeNode(1)});
            } else if (val.data == 2) {
                in.add(node.data);
                queue.add(new TreeNode[]{node, new TreeNode(3)});
                if (node.right != null) queue.add(new TreeNode[]{node.right, new TreeNode(1)});
            } else {
                post.add(node.data);
            }
        }
            System.out.println(pre);
            System.out.println(in);
            System.out.println(post);
    }

    //TODO:doubt
    private static void postOrderIterative2(TreeNode root) {
        TreeNode cur=root ,temp;
        Stack<TreeNode> stack=new Stack<>();
        while (!stack.isEmpty() || cur != null) {
            if(cur!=null){
                stack.add(cur);
                cur=cur.left;
            }
            else {
                temp=stack.peek().right;
                if(temp==null){
                    //check if next 2 line are optional
                  temp=stack.pop();
                  System.out.print(temp.data+" ");
                    while (!stack.isEmpty() && stack.peek().right==temp){
                        temp=stack.pop();
                        System.out.print(temp.data+" ");
                    }
                }
                else cur=temp;
            }
        }
    }
    private static void postOrderIterative1(TreeNode root) {
        Stack<TreeNode> stack =new Stack<>();
        List<Integer> ans=new ArrayList<>();
        stack.add(root);
        while (!stack.isEmpty()){
            TreeNode popped = stack.pop();
            ans.add(popped.data);
            if(popped.left!=null)stack.add(popped.left);
            if(popped.right!=null)stack.add(popped.right);
        }
        Collections.reverse(ans);
        System.out.println(ans);
    }

    private static void preOrderIterative(TreeNode root) {
        Stack<TreeNode> stack=new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()){
            TreeNode popped = stack.pop();
            System.out.print(popped.data+" ");
            if(popped.right!=null) stack.add(popped.right);
            if(popped.left!=null) stack.add(popped.left);
        }
    }

    private static void levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue=new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            //System.out.print("current Size:"+size);
            for (int i = 0; i < size; i++) {
                TreeNode polled = queue.poll();
                System.out.print(" "+polled.data);
                if(polled.left!=null) queue.add(polled.left);
                if(polled.right!=null) queue.add(polled.right);
            }
            System.out.println(" ");
        }
    }

    private static void postOrderTraversal(TreeNode root) {
        if(root==null) return;
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.data+" ");
    }

    private static void inOrderTraversal(TreeNode root) {
        if(root ==null) return;
        inOrderTraversal(root.left);
        System.out.print(root.data+" ");
        inOrderTraversal(root.right);
    }

    // 1,2,4,5,6,3,7,8,9,10
    private static void preOrderTraversal(TreeNode root) {
        if(root ==null) return;
        System.out.print(root.data+" ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }

    private static void list() {

    /*    ListNode head = new ListNode(3);
        head.next = new ListNode(1);
        ListNode section = new ListNode(4);
        head.next.next = section;
        head.next.next.next = new ListNode(6);
        head.next.next.next.next = new ListNode(2);

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(4);
        head2.next.next.next = new ListNode(5);
        head2.next.next.next.next = section;

        printDLL(head);
        printDLL(head2);
        ListNode listNode = interSection(head, head2);
        printDLL(listNode);*/
    }
    private static void binarySearch() {
        int a[][] = {
                {4, 2, 5, 1, 4, 5},
                {2, 9, 3, 3, 3, 2},
                {1, 7, 6, 0, 1, 3},
                {3, 6, 2, 3, 7, 2}
        };
        int n = a.length;
        int k=4;
        System.out.println(Arrays.toString(peakIn2DArray(a)));

    }

    private static int[] peakIn2DArray(int[][] a) {
        int lo=0,hi=a[0].length;
        int col=a[0].length;
        while (lo<=hi){
            int mid=lo+hi>>1;
            int maxRowIndex= findMaxColIndex(mid,a);
            int left= mid-1>=0?a[maxRowIndex][mid-1]:-1;
            int right= mid+1<col?a[maxRowIndex][mid+1]:-1;
            if(a[maxRowIndex][mid]>left && a[maxRowIndex][mid]>right) return new int[]{maxRowIndex,mid};
            else if(a[maxRowIndex][mid]<right)lo=mid+1;
            else hi=mid-1;
        }
        return new int[]{-1,1};
    }

    private static int findMaxColIndex(int col, int[][] a) {
     int ans=0,index=-1;
        for (int i = 0; i <a.length ; i++) {
            if(a[i][col]>ans){
                ans=a[i][col];
                index=i;
            }
        }
        return index;
    }

    private static int rowWithMax1s(int n, int[][] a) {
        int maxLen = 0,index = 0;
        for (int i = 0; i <n ; i++) {
            int len = n - upperBound(a[i].length, 0, a[i]);
            if(maxLen<len){
                maxLen=len;
                index=i;
            }
           // System.out.println("temp:"+temp+",index:"+index);
        }
        return index;
    }

    private static int kthElement(int n1, int n2, int[] a, int[] b, int k) {
        //want array - a to be smaller
        //want array - a to be smaller
        if(n1>n2) return median(n2,n1,b,a);
        int lo = Math.max(0, k - n2), hi = Math.min(n1, k);
        int no_of_element_in_left_partition=k;
        while (lo<=hi){
            int mid=lo+hi>>1;
            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;
            if(mid-1>=0)l1=a[mid-1];   r1=a[mid];
            if(no_of_element_in_left_partition-(l1+1)-1>=0)l2=b[no_of_element_in_left_partition-(l1+1)-1];
            if(l2+1<n2)r2=b[l2+1];
            if (l1 <= r2 && l2 <= r1) {
                    return (int) (Math.max(l1, l2) / 2.0);
            }
            else if(l1>r2) hi=mid-1;
            else lo=mid+1;
        }
        return -1;
    }

    private static int median(int n1, int n2, int[] a, int[] b) {
        //want array - a to be smaller
        if(n1>n2) return median(n2,n1,b,a);
        int lo=0,hi=n1;
        int symmetry_point=(n1+n2+1)>>1;
        int total=n1+n2;
        //mid is partition at
        while (lo<=hi){
            int mid1=lo+hi>>1;
            int mid2=symmetry_point-mid1;
            int l1=Integer.MIN_VALUE , r1=Integer.MAX_VALUE;
            int l2=Integer.MIN_VALUE , r2=Integer.MAX_VALUE;
            if(mid1<n1)r1=a[mid1];
            if(mid2<n2)r2=b[mid2];
            if(mid1-1>=0)l1=a[mid1-1];
            if(mid2-1>=0)l2=b[mid2-1];
            if (l1 <= r2 && l2 <= r1) {
                if (total % 2 != 0)
                    return Math.max(l1, l2);
                else return (Math.max(l1, l2) + Math.min(r1, r2)) / 2;
            }
            else if(l1>r2) hi=mid1-1;
            else lo=mid1+1;
        }
          return -1;
    }

    private static double maxDistbwGasStationBinary(int n, int givenStation, int[] a) {
        double lo=0 , hi= Arrays.stream(a).max().getAsInt();
        while (hi-lo > Math.pow(10,-6)){
            double mid= (lo+ hi)/2.0;
            // need to increase distance so it is withing limit
            if(getCountOfGasStation(mid,n,a)>givenStation) lo=mid;
            else hi=mid;
        }
        return hi;
    }

    private static int getCountOfGasStation(double givenDiff, int n, int[] a) {
        int count=0;
        for (int i = 1; i <n ; i++) {
            int gap=a[i]-a[i-1];
            int numberInbwetween= (int) (gap/givenDiff);
            if(gap == givenDiff*numberInbwetween) numberInbwetween--;
            count+=numberInbwetween;
        }
        return count;
    }

    private static int maxDistbwGasStationUsingQueue(int n, int k1, int[] a) {
        int[] howMany=new int[n-1];
        //len,index
        Queue<int[]> queue=new PriorityQueue<>(Comparator.comparingInt((int[] b)->b[0]).reversed()
                .thenComparingInt(aa->aa[1]).reversed());

        for (int i = 1; i <n ; i++) {
            int len=a[i]-a[i-1];
            queue.add(new int[]{len,i-1});
        }

        for (int k = 1; k <=k1 ; k++) {
            int[] poll = queue.poll();
            int val=poll[0];
            int ind=poll[1];
            queue.add(new int[]{val/(howMany[ind]+1),ind});
            howMany[ind]+=1;
            //  System.out.println("k:"+k+" "+Arrays.toString(new_placed));
        }

        return queue.poll()[0];
    }
    private static int maxDistbwGasStation(int n, int k1, int[] a) {
        int[] howMany=new int[n-1];
        for (int k = 1; k <=k1 ; k++) {
            //find max gap where new station can be placed
            int index=-1,maxsegmentlen=-1;
            //1, 13, 17, 23
            for (int i = 1; i <n ; i++) {
                int len=a[i]-a[i-1];
                int totalSegment = howMany[i-1] + 1;
                int segmentlen=len/ totalSegment;
                if(segmentlen>maxsegmentlen){
                    maxsegmentlen=segmentlen;
                    index=i-1;
                }
            }
            howMany[index]+=1;
          //  System.out.println("k:"+k+" "+Arrays.toString(new_placed));
        }

        //calculate max difference
        int ans=-1;
        for (int i = 1; i <n ; i++) {
            int len=a[i]-a[i-1];
            int segment = howMany[i-1]+1;
            ans=Math.max(ans,len/segment);
        }
        return ans;
    }

    private static int bookAllocation(int n, int students, int[] a) {
        Arrays.sort(a);
        int lo= Arrays.stream(a).max().getAsInt(),hi= Arrays.stream(a).sum();
        while (lo<=hi){
            int mid=lo+hi>>1;
            if(numberOfStudentsRequired(mid,n, a)>students) lo=mid+1;
            else hi=mid-1;
        }
        return lo;
    }
    //25, 46, 28, 49, 24          4
    private static int numberOfStudentsRequired(int maxBooksPerStudent, int n, int[] a) {
        int studentRequired=1,prevSum=0;
        for (int i = 0; i <n ; i++) {
            if(prevSum+a[i]<=maxBooksPerStudent){
                prevSum+=a[i];
            }
            else { studentRequired++; prevSum=a[i];}
        }
        return studentRequired;
    }

    //doubt
    private static int agressiveCows(int n, int cows, int[] a) {
        //hi can be max-min
        int lo=1,hi= Arrays.stream(a).max().getAsInt();
        while (lo<=hi){
            int mid=lo+hi>>1;
            if(canPlaceCow(mid,n,cows,a))lo=mid+1;
              else hi=mid-1;
        }
        return hi;
    }

    //0, 3, 4, 7, 9, 10      4
    private static boolean canPlaceCow(int dist, int n, int cows, int[] a) {
        int tempcow=1 , prevCowIsAtDist=a[0];
        for (int i = 1; i <n ; i++) {
            //dist b/w 2 cows
            if(a[i] - prevCowIsAtDist >=dist){
                tempcow++; prevCowIsAtDist=a[i];
            }
        }
        return tempcow>=cows?true:false;
    }

      //doubt
      private static int missingNumber(int n, int k, int[] a) {
        int lo=0,hi=n-1;
        while (lo<=hi){
            int mid=lo+hi>>1;
            if(a[mid]-(mid+1)<=k) lo=mid+1;
            else hi=mid-1;
        }
        //a[hi]+more
        // a[hi]+ k-missing
        //a[hi] +k -(a[hi]-(hi+1))
        //a[hi] +k -(a[hi]- hi - 1)
        //a[hi] +k -a[hi]+ hi + 1)
        //a[hi] +k -a[hi]+ hi + 1) or  //a[hi] +k -a[hi]+ lo or lo+k // hi+1+k
        return lo+k;
    }

    //video 15
    //doubt
    private static int capToShipPackage(int n, int maxDays, int[] a) {
        int lo= Arrays.stream(a).max().getAsInt() , hi= Arrays.stream(a).sum();
        while (lo<=hi){
            int mid=lo+hi>>1;
            //increase weight size to put into ship if greater than requied than threshold
            if(daysReq(mid,n,a)>maxDays) lo=mid+1;
            else hi=mid-1;
        }
        return  lo;
    }

    static int daysReq(int cap, int n, int[] wt){
        int load = 0, days = 0;
        for (int i = 0; i <n ; i++) {
            if(load+wt[i]<=cap) load+=wt[i];
            // increase day and reset load to current load
            else {days++;load=wt[i]; }
        }
        //for leftover
        if(load>0)days++;
        return days;
    }
    private static int smallestDivisorGivenSum(int n, int threshold, int[] a) {
        if(threshold <n ) return -1;
        int lo=1, hi= Arrays.stream(a).max().getAsInt();
        while (lo<=hi){
            int mid=lo+hi>>1;
            int limit = getLimit(mid, n, a);
            //System.out.println("lo:"+lo+",hi:"+hi+",mid:"+mid+",limt:"+limit);
            if(limit <=threshold) hi=mid-1;
            else lo=mid+1;
        }
        return lo;
    }

    private static int getLimit(double mid, int n, int[] a) {
        int ans=0;
        for (int i = 0; i < n; i++) {
            ans += Math.ceil(a[i] / mid);
        }
        return ans;
    }

    private static int minDaysBonquet(int n, int m, int k, int[] a) {
        if (m * k > n) return -1;
        int lo= Arrays.stream(a).min().getAsInt() , hi= Arrays.stream(a).max().getAsInt();
        while (lo<=hi){
            int mid=lo+hi>>1;
            //if possible take min
            if(possible(mid,m,k,n,a)){
                hi=mid-1;
            }
            else lo=mid+1;
        }
        return lo;
    }

    static boolean possible(int day, int givenbucket, int eachSize, int n, int[] a){
        int totalBucket=0 , tempTotal = 0;
       //7, 7, 7, 7, 13, 11, 12, 7          2,3
        for (int i = 0; i < n ; i++) {
            if(day>=a[i]){
                tempTotal++;
            }
            else {
                totalBucket+=(tempTotal/eachSize);
                tempTotal=0;
            }
        }
        //to consider for balance totalBucket
        totalBucket+=(tempTotal/eachSize);
        return totalBucket>=givenbucket?true:false;
    }
    private static int cocoEatingBanana(int n, int maxAllowedHour, int[] a) {

        int lo=1,hi= Arrays.stream(a).max().getAsInt();
        while (lo<=hi){
            int mid=lo+hi>>1;
            //need min so go left
            if(totalHourWithCurrentMid(mid,n,a)<=maxAllowedHour){
                hi=mid-1;
            }
            else lo=mid+1;
        }
        return lo;
    }

    private static int totalHourWithCurrentMid(double mid, int n, int[] a) {
        int ans = 0;
        for (int i = 0; i <n ; i++) {
            ans+=Math.ceil(a[i]/mid);
        }
        return ans;
    }

    private static int nthRoot(int n , int root) {
        int lo=1,hi=n;
        while (lo<=hi){
            int mid=lo+hi>>1;
            double powVal = Math.pow(mid, root);
            if(powVal==n) return mid;
            if(powVal <n)
                lo=mid+1;
            else hi=mid-1;
        }
        return -1;
    }
    private static int peakElement(int n, int[] a) {
        int lo=1, hi=n-2;
        if(n==1) return a[0];
        if(a[0]>a[1])return a[0];
        if(a[n-1]>a[n-2])return a[n-1];
        while (lo<=hi) {

            int mid = lo + hi >> 1;
            if (a[mid - 1] < a[mid] && a[mid] > a[mid + 1]) return a[mid];
            else if (a[mid-1] < a[mid] && a[mid] < a[mid + 1]) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    //this is same as index of min element in rotated array
    private static int numberOfRotationInRotatedArray(int n, int[] a) {
            int lo=0, hi=n-1,ans=Integer.MAX_VALUE ,index=-1;
            while (lo<=hi) {

                //optimization , array is already sorted
                if(a[lo]<=a[hi]) return lo;
                int mid = lo + hi >> 1;
                //left is sorted
                if(a[lo]<=a[mid]){
                    if (a[lo] < ans) {
                        ans = a[lo];
                        index=lo;
                    }
                    lo = mid + 1;
                } else {
                    if (ans > a[mid + 1]) {
                        ans = a[mid + 1];
                        index=mid+1;
                    }
                    hi = mid - 1;
                }
            }
            return index;
    }

    // BS - 6
    private static int minInRotatedArray(int n, int[] a) {
        int lo=0, hi=n-1,ans=-1;
        while (lo<=hi) {

            //optimization , array/search space is alreday sorted
            if(a[lo]<=a[hi]) { ans=Math.min(a[lo],ans); break;}
            int mid = lo + hi >> 1;
            //left is sorted
            if(a[lo]<=a[mid]){
                ans=Math.min(ans,a[lo]);lo=mid+1;
            }
            else {
                ans=Math.min(ans,a[mid+1]); hi =mid-1;
            }
        }
        return ans;
    }

    //only additional condition are
    /*
    if (a[lo] == a[mid] && a[mid] == a[hi]) {

                lo = lo + 1;
                hi = hi - 1;
            }
     */
    private static int searchRotatedArrayWithDuplicate(int n, int target, int[] a) {
        int lo=0, hi=n-1;
        while (lo<=hi) {

            int mid = lo + hi >> 1;
            // System.out.println("lo:"+lo+",hi:"+hi+", index:"+mid+",mid:"+a[mid]);
            if (a[lo] == a[mid] && a[mid] == a[hi]) {
                lo = lo + 1;
                hi = hi - 1;
            } else if (a[mid] == target) return mid;
                //if left is sorted
            else if (a[lo] <= a[mid]) {
                if (target >= a[lo] && target < a[mid]) hi = mid - 1;
                else lo = mid + 1;
            }
            //right is sorted
            else {
                if (target > a[mid] && target <= a[hi]) lo = mid + 1;
                else hi = mid - 1;
            }
        }

        return -1;
    }

    private static int searchRotatedArray(int n, int target, int[] a) {
        int lo=0, hi=n-1;
        while (lo<=hi){

            int mid = lo + hi >> 1;
           // System.out.println("lo:"+lo+",hi:"+hi+", index:"+mid+",mid:"+a[mid]);
            if(a[mid]==target) return mid;
            //if left is sorted
            else if(a[lo]<=a[mid]){
                if(target>=a[lo] && target<a[mid]) hi=mid-1;
                else lo=mid+1;
            }
            //right is sorted
            else {
                if(target>a[mid] && target<=a[hi]) lo=mid+1;
                else hi=mid-1;
            }
        }

        return -1;
    }

    private static int dupLength(int n, int target, int[] a) {
        int lo=0,hi=n-1,first=-1;

        while (lo<=hi){
            int mid=lo+hi>>1;
            if(a[mid]==target){
                first=mid; hi=mid-1;
            }
            else if(target > a[mid]) lo=mid+1;
            else hi=mid-1;
        }
        if(first==-1) return -1;
        lo=0; hi=n-1;int second=-1;

        while (lo<=hi){
            int mid=lo+hi>>1;
            if(a[mid]==target){
                second=mid; lo=mid+1;
            }
            else if(target > a[mid]) lo=mid+1;
            else hi=mid-1;
        }
        return second - first +1;
    }

    private static int[] firstAndLastOccurance(int n, int target, int[] a) {
         int lo=0,hi=n-1,first=-1;

         while (lo<=hi){
             int mid=lo+hi>>1;
             if(a[mid]==target){
                 first=mid; hi=mid-1;
             }
            else if(target > a[mid]) lo=mid+1;
            else hi=mid-1;
         }
         if(first==-1) return new int[]{-1,-1};
        lo=0; hi=n-1;int second=-1;

        while (lo<=hi){
            int mid=lo+hi>>1;
            if(a[mid]==target){
                second=mid; lo=mid+1;
            }
            else if(target > a[mid]) lo=mid+1;
            else hi=mid-1;
        }
        return new int[]{first,second};
    }

    private static int[] firstAndLastOccuranceUsingLowerbound_UpperBound(int n, int target, int[] a) {
        int first = lowerBound(n, target, a);
        if (first ==n-1 || a[first] != target) return new int[]{-1, -1};
        return new int[]{first, upperBound(n, target, a) - 1};

    }

    // lower bound is smallest index such that a[i]>x
    private static int upperBound(int n, int x, int[] a) {
        int lo=0,hi=n-1 ,ans=n;
        while (lo<=hi){
            int mid=lo+hi>>1;
            if(a[mid]>x){
                hi=mid-1;
                ans=mid;
            }
            else {
                lo=mid+1;
            }
        }
        return ans;
    }
    // lower bound is smallest index such that a[i]>=x
    //lower bound is same is ceil value
    private static int lowerBound(int n, int x, int[] a) {
          int lo=0,hi=n-1 ,ans=n;
          while (lo<=hi){
              int mid=lo+hi>>1;
              if(a[mid]>=x){
                  hi=mid-1;
                  ans=mid;
              }
              else {
                  lo=mid+1;
              }
          }
          return ans;
    }
}