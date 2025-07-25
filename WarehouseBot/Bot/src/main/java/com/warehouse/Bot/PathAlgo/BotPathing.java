package com.warehouse.Bot.PathAlgo;

import com.warehouse.Bot.objects.Cell;

import java.util.LinkedList;

public class BotPathing {
    private static LinkedList<Integer> NN(Cell[] arr) {
        LinkedList<Integer> res = new LinkedList<>();
        res.add(0);
        int[] flag = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            flag[i] = 0;
        }
        int all_active = 0;
        while (all_active < arr.length) {
            int nmin = -1;
            int nminInd = 0;
            for (int i = 1; i < arr.length; i++) {
                if (res.getLast() != i && flag[i] == 0) {
                    if (arr[res.getLast()].getDist(arr[i]) < nmin || nmin == -1 ) {
                        nmin = arr[res.getLast()].getDist(arr[i]);
                        nminInd = i;
                    }
                }
            }


            flag[nminInd] = 1;
            all_active++;
            res.add(nminInd);
        }

        return res;
    }
    private static int pathCost(LinkedList<Integer> path, Cell[] cells){
        if(path.isEmpty()){
            return -1;
        }
        int sum =0;
        for (int i = 0; i <path.size()-1 ; i++) {
            sum+=cells[path.get(i)].getDist(cells[path.get(i+1)]);
        }
        return sum;
    }
    private static LinkedList<Integer> opSwap(LinkedList<Integer> l,int i, int j){
        LinkedList<Integer> res = new LinkedList<>();
        for (int k = 0; k < i+1; k++) {
            res.add(l.get(k));
        }
        for (int k = j; k >i ; k--) {
            res.add(l.get(k));
        }
        for (int k = j+1; k < l.size(); k++) {
            res.add(l.get(k));
        }
        return res;
    }
    private static LinkedList<Integer> op2(LinkedList<Integer> init, Cell[] cells){

        int oldRouteCost = pathCost(init,cells);
        LinkedList<Integer> newRoute = op2Helper(init,cells,oldRouteCost);
        int newRouteCost = pathCost(newRoute,cells);
        while(newRouteCost>oldRouteCost){
            init = newRoute;
            oldRouteCost = newRouteCost;
            newRoute = op2Helper(init,cells,oldRouteCost);
            newRouteCost = pathCost(newRoute,cells);

        }
        return newRoute;

    }
    private static LinkedList<Integer> op2Helper(LinkedList<Integer> oldRoute,Cell[] cells,int oldCost){
        LinkedList<Integer> newRoute = new LinkedList<>();
        for (int i = 1; i <cells.length-1 ; i++) {
            for (int j = i+1; j < cells.length ; j++) {
                newRoute = opSwap(oldRoute,i,j);
                int newCost = pathCost(newRoute,cells);
                if(oldCost>newCost){
                    return newRoute;
                }
            }
        }
        return oldRoute;
    }
    public static String PlanRoute(Cell[] locations){
        LinkedList<Integer> greedyRoute = NN(locations);
        LinkedList<Integer> approximateOptimalRoute = op2(greedyRoute,locations);
        StringBuilder route =
                new StringBuilder(
                        String.format("(%d,%d)",locations[approximateOptimalRoute.get(0)].getX()
                                ,locations[approximateOptimalRoute.get(0)].getY()));

        for (int i = 0; i < approximateOptimalRoute.size()-1; i++) {
            int X = locations[approximateOptimalRoute.get(i)].getX();
            int Y = locations[approximateOptimalRoute.get(i)].getY();

            while(X!=locations[approximateOptimalRoute.get(i+1)].getX()){
                if(X>locations[approximateOptimalRoute.get(i+1)].getX()){
                    X--;

                    route.append("-");
                    route.append(String.format("(%d,%d)",X,Y));
                }
                else{
                    X++;
                    route.append("-");
                    route.append(String.format("(%d,%d)",X,Y));
                }
            }
            while(Y!=locations[approximateOptimalRoute.get(i+1)].getY()){
                if(Y>locations[approximateOptimalRoute.get(i+1)].getY()){
                    Y--;
                    route.append("-");
                    route.append(String.format("(%d,%d)",X,Y));
                }
                else{
                    Y++;
                    route.append("-");
                    route.append(String.format("(%d,%d)",X,Y));
                }
            }
        }
        return route.toString();
    }


}
