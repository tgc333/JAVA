package Software;

import java.io.*;
import java.util.*;

public class java2
{
	static int queensCount;
	static int finalQueensCount = 0;

	public static void main(String []args)
	{
		long startMS = System.currentTimeMillis();
		queensCount = 14;
		int board[] = new int[queensCount + 1];
		countQueens(1, board);
		System.out.printf("finalQueensCount = %d°³\n", finalQueensCount);
		long endMS = System.currentTimeMillis();
		System.out.printf("%,d msec", endMS - startMS);
	}

	static void countQueens(int column, int board[])
	{
		for (int row=1; row<=queensCount; row++)
		{
			board[column] = row;
			if(checkSafe(row, column, board))
			{
				if(column == queensCount)
					finalQueensCount++;
				else
					countQueens(column+1, board);
			}
		}
	}

	static boolean checkSafe(int row, int column, int board[])
	{
		for (int i=1; i<column; i++)
			if (board[column-i] == row || board[column-i] == row-i || board[column-i] == row+i)
				return false;
		return true;
	}
} 

