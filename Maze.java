/* @author emirhanylmzz */

import java.util.*;

public class Maze {
	public int row = 10, col = 10;
	public Map<Integer, Cell> map = new HashMap<>();
	public class Cell{
		int data;
		Cell parent;
		int left, right, top, bottom, x, y;
		int rank;
	}
	public Cell makeSet(int x, int y, int data) {
		Cell c = new Cell();
		c.bottom = 1;
		c.left = 1;
		c.right = 1;
		c.top = 1;
		c.rank = 0;
		c.parent = c;
		c.data = data;
		c.x = x;
		c.y = y;
		map.put(data, c);
		return c;
	}
	public int findSet(int data) {
		return findSet(map.get(data)).data;
	}
	public Cell findSet(Cell c) {
		if(c.parent == c) {
			return c.parent;
		}
		c.parent = findSet(c.parent);
		return c.parent;
	}
	public boolean union(int data1, int data2) {
		Cell c1 = map.get(data1);
		Cell c2 = map.get(data2);
		
		Cell parent1 = findSet(c1);
		Cell parent2 = findSet(c2);
		
		if(parent1.data == parent2.data) {
			return false;
		}
		else if(parent1.rank == parent2.rank) {
			parent2.rank = parent2.rank + 1;
			parent1.parent = parent2;
		}
		else if(parent1.rank > parent2.rank) {
			parent2.parent = parent1;
		}
		else {
			parent1.parent = parent2;
		}
		return true;
	}
	public boolean wayCheck(int data, int way) {
		Cell c = map.get(data);
		if(way == 0) {
			if (c.x == 0) {
				return false;
			}
		}
		else if(way == 1) {
			if(c.x == row - 1) {
				return false;
			}
		}
		else if(way == 2) {
			if(c.y == col - 1) {
				return false;
			}
		}
		else if(way == 3) {
			if(c.y == 0) {
				return false;
			}
		}
		return true;
	}
	public int random_path(int data) {
		Cell c = map.get(data);
		Random generator = new Random();
		int r = generator.nextInt(4);
		Cell c2 = new Cell();
		if (r == 0) {
			if(wayCheck(data, r)) {
				c2 = map.get(data - row);
				if(findSet(c.data) != findSet(c2.data)) {
					union(data, data - row);
					c.top = 0;
					c2.bottom = 0;
				}
			}
		}
		else if(r == 1) {
			if(wayCheck(data, r)) {
				c2 = map.get(data + row);
				if(findSet(c.data) != findSet(c2.data)) {
					union(data, data + row);
					c.bottom = 0;
					c2.top = 0;
				}
			}
		}
		else if(r == 2) {
			if(wayCheck(data, r)) {
				c2 = map.get(data + 1);
				if(findSet(c.data) != findSet(c2.data)) {
					union(data, data + 1);
					c.right = 0;
					c2.left = 0;
				}
			}
		}
		else if(r == 3) {
			if(wayCheck(data, r)) {
				c2 = map.get(data - 1);
				if(findSet(c) != findSet(c2)) {
					union(data, data - 1);
					c.left = 0;
					c2.right = 0;
				}
			}
		}
		return c2.data;
	}
	//This method taken from someone else.
	//printMaze fonksiyonu başkasından alınmıştır.
	public void printMaze(int n, int m) {
		for(int i = 0; i < n; ++i) {						
			for(int j = 0; j < m; ++j) {
				if(map.get(i*n+j).left == 1)
					System.out.print("|");
				else
					System.out.print(" ");
				if(map.get(i*n+j).bottom == 1)
					System.out.print("_");
				else
					System.out.print(" ");
				/*if(map.get(i*n+j).top == 1)
					System.out.print("_");
				else
					System.out.print(" ");*/
				if(map.get(i*n+j).right == 1) {
					System.out.print("|");
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maze a = new Maze();
		int d = 0;
		for(int i = 0; i  < a.row; ++i) {
			for(int j = 0; j < a.col; ++j) {
				a.makeSet(i, j, d);
				d++;
			}
		}
		int i = 0;
		while(1 == 1) {
			if(a.map.get(i).x == a.col - 1 && a.map.get(i).y == a.row - 1) {
				break;
			}
			i = a.random_path(i);
		}
		a.map.get(i).bottom = 0;
		
		
		System.out.println(i);
		a.printMaze(a.row, a.col);
	}
}
