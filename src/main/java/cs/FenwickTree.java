package cs;

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

    // sum from 1 to index i
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

    //first/min index till which sum is >=k
    //Or,index till which prefix sum is lowerBound
    //binary lifting
    int findLowerBound(int k) {
        int n = fen.length;
        int curIndex = 0, prevSum = 0;
        double value_n_base_2 = Math.log(n) / Math.log(2);
        for (int i = (int) value_n_base_2; i >= 0; i--) {
            if (prevSum + fen[curIndex + (1 << i)] < k) {
                curIndex += 1 << i;
                prevSum += fen[curIndex];
            }
        }
        return curIndex + 1;
    }
}



