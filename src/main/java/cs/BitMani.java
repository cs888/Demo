package cs;

import java.util.ArrayList;
import java.util.List;

public class BitMani {
    public static void main(String[] args) {
        int n=5,i=2;
        int a[]={2,4,2,14,3,7,7,3};
        System.out.println(DividewithoutMulDivOp(22,3));
    }

    private static double DividewithoutMulDivOp(int dividend, int divisor) {
        int ans = 0;
        boolean negative = false;
        if (dividend < 0 && divisor >= 0) negative = true;
        if (dividend > 0 && divisor < 0) negative = true;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while (dividend >= divisor) {
            int temp = 0;
            while (divisor<<(temp+1) <= dividend) {
                temp++;
            }
            ans += 1 << temp;
            dividend = dividend - (divisor * (1 << temp));
        }

        if (negative) return -1 * ans;
        return ans;
    }

    private static int xorOfNumberINRange(int i , int j) {
        return xorOfNumber(i-1) - xorOfNumber(j);
    }

    private static int xorOfNumber(int n) {

        if(n%4==1) return 1;
        else if (n%4==2) { return n+1;
        }
        else if (n%4==3) { return 0;
        }
        else return n;
    }

    private static int[] singleNumber3(int n, int[] a) {
        int xorr = 0, ones = 0, twos = 0;
        for (int i = 0; i < n; i++) {
            xorr ^= a[i];
        }
        int rightMost = (xorr & (xorr - 1)) & xorr;
        for (int i = 0; i < n; i++) {
            if ((a[i] & rightMost) == 0) ones ^= a[i];
            else twos ^= a[i];
        }
        return new int[]{ones, twos};
    }

    private static void singleNumber2(int n, int[] a) {
        int ones=0 ,twos=0;
        for (int i = 0; i <n ; i++) {
            ones^=a[i]&~twos;
            twos^=a[i]&~ones;
        }
        System.out.println(ones);
    }

    private static void singleNumber(int n, int[] a) {
        int ans=0;
        for (int i = 0; i <32 ; i++) {
            int count=0;
            for (int j = 0; j < n; j++) {
                if((a[j]&(1<<i))!=0){
                    count++;
                }
            }
            if(count%3==1){
               ans |= 1<<i;
            }
        }
        System.out.println(ans);
    }

    private static void powerSet(int n, int[] a) {
        List<List<Integer>> list =new ArrayList<>();
        for (int i = 0; i < (1<<n); i++) {
            List<Integer> temp =new ArrayList<>();
            for (int bit = 0; bit < n; bit++) {
                if(((i>>bit)&1)==1) temp.add(a[bit]);
            } list.add(temp);
        }
        System.out.println(list);
    }

    private static void numberOfBitChangesToConvert(int start, int end) {
        System.out.println(countNumberOfSetBit3(start^end));
    }

    private static int countNumberOfSetBit3(int n) {
        int count=0;
        while (n!=0){
           n=n&(n-1);
            count++;
        }
       // System.out.println(count);
        return count;
    }

    //n&1 1 if odd , else 0 if even
    // n&1 is odd check
    private static void countNumberOfSetBit2(int n) {
        int count=0;
        while (n>0){
            count+=n&1;
            n=n>>1;
        }
        System.out.println(count);
    }
    private static void countNumberOfSetBit(int n) {
        int count=0;
        while (n>0){
            if(n%2==1) count++;
            n/=2;
        }
        System.out.println(count);
    }
    private static void removeRighMostSetBit(int n) {
        System.out.println(n&(n-1));
    }

    private static void toggleIthBit(int n, int i) {
        System.out.println(n^(1<<i));
    }
    private static void clearIthBit(int n, int i) {
        System.out.println(n&~(1<<i));
    }

    // will get same number if already set
    // otherwise , return with adding 2^i
    private static void setIthBit(int n, int i) {
        System.out.println( (n|(1<<i)));;
    }

    private static boolean checkIthBitIsSetOrNot2(int n , int i) {
        return ((n>>i)&1)==1 ? true:false;
    }
    private static boolean checkIthBitIsSetOrNot1(int n , int i) {
        return (n&(1<<i))!=0 ? true:false;
    }

}
