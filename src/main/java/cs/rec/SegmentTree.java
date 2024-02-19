package cs.rec;

public class SegmentTree {

    public static void main(String[] args) {
        int a[] = {4, 5, 2, 1, 9, 3, 2};
        int n = a.length;
        int seg[] = new int[4 * n];
        buildSegmentTree(0, 0, n - 1, seg, a);
        //now do n number of queries
    }

    static int  segmentTreeRangeSumQuery(int i, int low, int high, int[] seg, int l, int r, int val, int[] lazy) {
        //do pending update if any index
        if(lazy[i]!=0){
            seg[i] += (high - low + 1) * lazy[i];
            if (low != high) {
                lazy[2 * i + 1] += lazy[i];
                lazy[2 * i + 2] += lazy[i];
            }
            lazy[i]=0;
        }

        //outside
        if (r < low || l > high) return 0;
        else if (l >= low && r <= high) {
            return seg[i];
        }
        int mid = low + high >> 1;
        return  segmentTreeRangeSumQuery(2 * i + 1, low, mid, seg, l, r, val, lazy)+
        segmentTreeRangeSumQuery(2 * i + 2, mid + 1, high, seg, l, r, val, lazy);

    }


    //rangeUpdate
    static void segmentTreeRangeUpdate(int i, int low, int high, int[] seg, int l, int r, int val, int[] lazy) {
        //do pending update if any index
        if(lazy[i]!=0){
            seg[i] += (high - low + 1) * lazy[i];
            if (low != high) {
                lazy[2 * i + 1] += lazy[i];
                lazy[2 * i + 2] += lazy[i];
            }
            lazy[i]=0;
        }

        //outside
        if (r < low || l > high) return;
        else if (l >= low && r <= high) {
            seg[i] += (high - low + 1) * val;
            if (low != high) {
                lazy[2 * i + 1] += val;
                lazy[2 * i + 2] += val;
            }
            return;
        }
        int mid = low + high >> 1;
        segmentTreeRangeUpdate(2 * i + 1, low, mid, seg, l, r, val, lazy);
        segmentTreeRangeUpdate(2 * i + 2, mid + 1, high, seg, l, r, val, lazy);

        seg[i] = seg[2 * i + 1] + seg[2 * i + 2];
    }

    //point update
    static void segmentTreePointUpdate(int i, int low, int high, int[] seg, int a[], int node, int val) {
        if (low == high) {
            seg[low] += val;
            return;
        }
        int mid = low + high >> 1;
        if (node <= mid && node >= low)
            segmentTreePointUpdate(2 * i + 1, low, mid, seg, a, node, val);
        else segmentTreePointUpdate(2 * i + 2, mid + 1, high, seg, a, node, val);
        seg[i] = seg[2 * i + 1] + seg[2 * i + 2];
    }

    static void buildSegmentTree(int i, int low, int high, int[] seg, int a[]) {
        if (low == high) {
            seg[low] = a[low];
            return;
        }
        int mid = low + high / 2;
        buildSegmentTree(2 * i + 1, low, mid, seg, a);
        buildSegmentTree(2 * i + 2, mid + 1, high, seg, a);
        seg[i] = Math.max(seg[2 * i + 1], seg[2 * i + 2]);
    }

    int search(int i, int low, int high, int l, int r, int[] seg) {
        //within
        if (low >= l && high <= r) {
            return seg[i];
        }
        //out of Range
        else if (low > r || high < l) return Integer.MIN_VALUE;

        //overlap
        int mid = low + high / 2;
        return Math.max(search(2 * i + 1, low, mid, l, r, seg),
                search(2 * i + 2, mid + 1, high, l, r, seg));
    }
}



