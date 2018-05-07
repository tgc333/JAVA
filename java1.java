package Software;
import java.util.*;

class Queen{
	int x, y;
	public Queen() {}
	public Queen(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}
}	// 퀸의 좌표를 가지고 있는 class

public class java1 {
	Stack<Queen> s = new Stack<Queen>();
	Stack<Queen> sol = new Stack<Queen>();
	
	public void boardset(int b[][], int n) {
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				b[i][j] = 0;
	}  // 입력받은 크기만큼의 체스보드를 2차원 배열로 생성하고
		//체스보드의 모든 좌표를 0으로 초기화
	
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
	}	//체스보드와 좌표를 입력 받고 그 좌표의 상하좌우, 대각선에
		//다른 퀸이 배치되있는지 검사, 입력받은 좌표에 퀸을 배치할 수 있으면
		//true 반환
	
	public void start(int n) {			
		int x=0;
		int y=0;	// 초기 x,y 좌표는 0,0
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
			}	// 퀸을 배치했다면 해당 좌표에 1 입력
				// 퀸을 스택에 push하고 좌표 재설정
			else {
				x++;
			}	// 퀸을 배치할 수 없다면 오른쪽으로 한칸 이동
			if(x>=n) {
				q = (Queen)s.pop();
				y--;
				x = q.x+1;
				board[q.x][q.y] = 0;
			}	// 좌표가 보드에서 벗어났다면 그 열
				// 에 퀸을 배치 할 수 없음
				// 스택에 있는 퀸을 하나 pop 하고 
				// pop한 퀸의 좌표에서 
				// 오른쪽으로 한칸이동
			
			if(y==n) {
				for(int i=0;i<n;i++)
					sol.push(new Queen(s.get(i).x,s.get(i).y));
				temp = s.pop();
				board[temp.x][temp.y] = 0;
				x = temp.x+1;
				y--;
			}	// 체스보드에 n개의 퀸을 배치했을 때 y가 n이 된다.
				// 그 경우 모든 해를 저장하는 스택에 해를 
				// 옮겨담고 다음 해를 탐색한다.
			if(x==n && y==0)
				break;
		}	// 모든 해를 탐색했을 경우 x=n , y=0 이 된다.
		long endMS = System.currentTimeMillis();
		System.out.printf("%,d msec", (endMS - startMS));
	}			// 이 때 반복문을 탈출한다.
}
