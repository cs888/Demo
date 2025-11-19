package cs.rec;

public class FenwickTree {
    int fen[];

    //fen tree is 1 based
    void update(int i, int diff) {
        int N = fen.length;
        while (i < N) {
            fen[i] += diff;
            i += (i & (-i));
        }
    }

    int sum(int i) {
        int S = 0;
        while (i > 0) {
            S += fen[i];
            i -= (i & (-i));
        }
        return S;
    }

    int rangeSum(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    //first index till which sum is K or >k
    //Or,index till which prefix sum is lowerBound
    //binary lifting
    int findLowerBound(int k) {
        int n = fen.length;
        int index = 0, prevSum = 0;
        double value_n_base_2 = Math.log(n) / Math.log(2);
        for (int i = (int) value_n_base_2; i >= 0; i--) {
            //1 << i is power 2 raise to i or Math.pow(2,i)
            int power_2_raise_i = 1 << i;
            if (power_2_raise_i<n && fen[index + power_2_raise_i] + prevSum <= k) {
                index += power_2_raise_i;
                prevSum += fen[index];
            }
        }
        return index + 1;
    }
}



