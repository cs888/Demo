package cs.rec;

public class FenwickTree {
    int fen[];

    //fen tree is 1 based
    void update(int i, int diff) {
        while (i < fen.length) {
            fen[i] += diff;
            i += (i & (-i));
        }
    }

    int sum(int i) {
        int tempSum = 0;
        while (i > 0) {
            tempSum += fen[i];
            i -= (i & (-i));
        }
        return tempSum;
    }

    int findLowerBound(int k) {
        int n = fen.length;
        int cur = 0, prevSum = 0;
        for (int i = (int) Math.log(n); i >= 0; i--) {
            if (fen[cur + (1 << i)] + prevSum < k) {
                cur += (1 << i);
                prevSum += fen[cur];
            }
        }
        return cur + 1;
    }
}



