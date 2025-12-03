package cs;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
   // You have a job that may depend on another Job before running it. Print dependency order if there is no cycle, if cycle then raise an exception
        //   j1    j3 j4 j2 j5
        //J1 -> [J2]
        //J2 -> [J3, J4]
        //J3 -> []
        //J4 -> [J5]
        //J5 -> []
        List<List<Integer>> graphNodes = new ArrayList<>();

        for (int i = 0; i <5 ; i++) {
            graphNodes.add(i,new ArrayList<>());
        }

        graphNodes.get(0).addAll(Arrays.asList(1));
        graphNodes.get(1).addAll(List.of(2,3));
        graphNodes.get(3).addAll(List.of(4));

        List<Integer> list=new ArrayList<>(Collections.nCopies(10,-1));
        //list.add(2,10);
        System.out.println(list);

        System.out.println(printJobOrder(graphNodes,5));

    }

    private static List<Integer> printJobOrder(List<List<Integer>> graphNodes , int totalNumberOfNodes) {
        // using kahn
        // get nodes whose indegree is 0 and add to queue

        int[] inOrder=new int[totalNumberOfNodes];

 // u->v
        for (int i = 0; i < totalNumberOfNodes ; i++) {
             for (Integer adjList:graphNodes.get(i)){
                 inOrder[adjList]++;
             }
        }

        Queue<Integer> queue=new ArrayDeque<>();
        for (int i = 0; i < totalNumberOfNodes ; i++) {
            if(inOrder[i]==0) queue.add(i);
        }

        List<Integer> ans=new ArrayList<>();
        while (!queue.isEmpty()){
            Integer popped = queue.poll();
            ans.add(popped);
            for (Integer node:graphNodes.get(popped)){
                inOrder[node]--;
                if(inOrder[node]==0) queue.add(node);
            }
        }

        if(ans.size()==totalNumberOfNodes) return ans;
        // throw exception
        return Collections.singletonList(-1);
    }

}
