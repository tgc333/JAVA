package Software;
import java.util.*;

class Queen{
	int x, y;
	public Queen() {}
	public Queen(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}
}	// ���� ��ǥ�� ������ �ִ� class

public class java1 {
	Stack<Queen> s = new Stack<Queen>();
	Stack<Queen> sol = new Stack<Queen>();
	
	public void boardset(int b[][], int n) {
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				b[i][j] = 0;
	}  // �Է¹��� ũ�⸸ŭ�� ü�����带 2���� �迭�� �����ϰ�
		//ü�������� ��� ��ǥ�� 0���� �ʱ�ȭ
	
	public boolean boardcheck(int b[][], int n, int x, int y) {
		boolean check = true;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(x>=n || y>=n) {
					check = false;
					break;
				}
				if(b[x][i]==1) 
					check = false;
				if(b[j][y]==1) 
					check = false;
				if(i-j==x-y && b[i][j] == 1) 
					check = false;
				if(i+j==x+y && b[i][j] == 1) 
					check = false;
				if(!check)
					break;
			}
		}
		return check;
	}	//ü������� ��ǥ�� �Է� �ް� �� ��ǥ�� �����¿�, �밢����
		//�ٸ� ���� ��ġ���ִ��� �˻�, �Է¹��� ��ǥ�� ���� ��ġ�� �� ������
		//true ��ȯ
	
	public void start(int n) {			
		int x=0;
		int y=0;	// �ʱ� x,y ��ǥ�� 0,0
		Queen q;
		Queen temp;
		int board[][] = new int[n][n];
		boardset(board,n);
		long startMS = System.currentTimeMillis();
		while(true) {
			if(boardcheck(board,n,x,y)) {
				board[x][y] = 1;
				s.push(new Queen(x,y));
				y++;
				x=0;
			}	// ���� ��ġ�ߴٸ� �ش� ��ǥ�� 1 �Է�
				// ���� ���ÿ� push�ϰ� ��ǥ �缳��
			else {
				x++;
			}	// ���� ��ġ�� �� ���ٸ� ���������� ��ĭ �̵�
			if(x>=n) {
				q = (Queen)s.pop();
				y--;
				x = q.x+1;
				board[q.x][q.y] = 0;
			}	// ��ǥ�� ���忡�� ����ٸ� �� ��
				// �� ���� ��ġ �� �� ����
				// ���ÿ� �ִ� ���� �ϳ� pop �ϰ� 
				// pop�� ���� ��ǥ���� 
				// ���������� ��ĭ�̵�
			
			if(y==n) {
				for(int i=0;i<n;i++)
					sol.push(new Queen(s.get(i).x,s.get(i).y));
				temp = s.pop();
				board[temp.x][temp.y] = 0;
				x = temp.x+1;
				y--;
			}	// ü�����忡 n���� ���� ��ġ���� �� y�� n�� �ȴ�.
				// �� ��� ��� �ظ� �����ϴ� ���ÿ� �ظ� 
				// �Űܴ�� ���� �ظ� Ž���Ѵ�.
			if(x==n && y==0)
				break;
		}	// ��� �ظ� Ž������ ��� x=n , y=0 �� �ȴ�.
		long endMS = System.currentTimeMillis();
		System.out.printf("%,d msec", (endMS - startMS));
	}			// �� �� �ݺ����� Ż���Ѵ�.
}
