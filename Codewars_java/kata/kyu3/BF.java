package kata.kyu3;

import java.util.*;
import java.awt.Point;
import java.util.stream.*;


public class BF {

    final static private int[]   BOATS = {0,4,3,2,1};
    final static private Point[] MOVES = {new Point(0,1), new Point(1,0)};

    private int[][]    field;
    private Set<Point> posSet;
    private int[]      boats = Arrays.copyOf(BOATS,BOATS.length);

    public BF(int[][] fld) {
        field  = Arrays.stream(fld).map(a->Arrays.copyOf(a,10)).toArray(int[][]::new);
        posSet = IntStream.range(0,10)
                .mapToObj( i -> IntStream.range(0,10).filter(j->fld[i][j]==1).mapToObj(j -> new Point(i,j)))
                .reduce(Stream.of(), Stream::concat)
                .collect(Collectors.toSet());
    }

    public boolean validate() { return posSet.size() == 20 && dfs(); }

    private static int comparePos(Point a, Point b) { return a.x != b.x ? Integer.compare(a.x,b.x) : Integer.compare(a.y,b.y); }

    private void setup(Set<Point> boat, boolean settingUp) {
        if (settingUp) posSet.removeAll(boat);
        else           posSet.addAll(boat);
        boats[boat.size()] +=  settingUp ? -1:1;
        boat.forEach( p -> field[p.x][p.y] = settingUp ? 0:1 );
    }

    private boolean dfs() {
        final Point p = posSet.stream().min(BF::comparePos).get();

        for (final Point move: MOVES) {

            int maxSize = 0, x=0, y=0;
            do {maxSize++;
                x = p.x + move.x * maxSize;
                y = p.y + move.y * maxSize;
            } while (x<10 && y<10 && maxSize<4 && field[x][y]==1);

            for (int s=maxSize ; s>0 ; s--) {
                if (boats[s]<=0) continue;

                Set<Point> boat = IntStream.range(0,s)
                        .mapToObj( n -> new Point(p.x+move.x*n, p.y+move.y*n) )
                        .collect(Collectors.toSet());
                setup(boat, true);
                boolean isGood = posSet.isEmpty() || dfs();
                if (isGood) return true;
                setup(boat, false);
            }
        }
        return false;
    }
}