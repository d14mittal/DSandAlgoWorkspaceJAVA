package novChallenge;

import java.util.Scanner;


class SegmentTree {
	private class Node {
		int data;
		int si;
		int ei;
		Node left;
		Node right;
	}
 
	private Node root;
	private int size;
 
	
	public SegmentTree(int[] arr) {
		root = construct(arr, 0, arr.length - 1);
	}
 
	private Node construct(int[] arr, int si, int ei) {
		if (si == ei) {
			Node base = new Node();
			this.size++;
 
			base.data = arr[si];
			base.si = base.ei = si;
			return base;
		}
		int mid = (si + ei) / 2;
 
		Node node = new Node();
		this.size++;
		node.si = si;
		node.ei = ei;
 
		node.left = construct(arr, si, mid);
		node.right = construct(arr, mid + 1, ei);
 
		node.data = Math.max(node.left.data, node.right.data);
 
		return node;
	}
 
	public int query(int qsi, int qei) {
		return query(root, qsi, qei);
 
	}
 
	private int query(Node node, int qsi, int qei) {
		// node entirely in the query
		if (node.si >= qsi && node.ei <= qei) {
			return node.data;
		}
		// node entirely out of query
		else if (node.ei < qsi || node.si > qei) {
			return Integer.MIN_VALUE;
		}
		// overlap
		else {
			int lsc = query(node.left, qsi, qei);
			int rsc = query(node.right, qsi, qei);
			return Math.max(lsc, rsc);
		}
 
	}
 
	public void update(int idx, int data) {
		update(root, idx, data);
	}
 
	private void update(Node node, int idx, int data) {
 
		if (node.si == idx && node.ei == idx) {
			node.data = data;
			return;
		}
 
		int mid = (node.si + node.ei) / 2;
		if (idx > mid) {
			update(node.right, idx, data);
		} else {
			update(node.left, idx, data);
		}
 
		node.data = Math.max(node.left.data, node.right.data);
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] arr = new int[n];
		int q = in.nextInt();
		int L = in.nextInt();
		int R = in.nextInt();
		SegmentTree tree = new SegmentTree(arr);
		// tree.display();
		while (q-- > 0) {
			int type = in.nextInt();
			if (type == 1) {
				int oi = in.nextInt();
				int nv = in.nextInt();
				arr[oi - 1] = nv;
				tree.update(oi - 1, nv);
				// tree.display();
 
			} else if (type == 2) {
				int count=0;
				int l = in.nextInt();
				int r = in.nextInt();
				int max=Integer.MIN_VALUE;
				for(int i=l-1;i<r;i++){
					
					for(int j=i;j<r;j++){
						if (i == j) {
							max = arr[i];
						} else if (i != j) {
							max = tree.query(i, j);
						}
						if (max >= L && max <= R) {
							count++;
						}
					}
				}
				System.out.println(count);
			}
		}
	}
}