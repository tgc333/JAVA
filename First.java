package Software;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class First extends JFrame {
	java1 j = new java1();
	int size;
	int pagen=1;
	int solcount;
	JPanel[][] p;
	Vector<Queen> vs = new Vector<Queen>();
	Container c = getContentPane();
	JTextField input = new JTextField(2);
	JLabel page = new JLabel("page : 0/0");
	JButton solution = new JButton("해 구하기");
	JLabel time = new JLabel("걸린 시간 : ");
	JPanel Board = new JPanel();
	ImageIcon white = new ImageIcon("C:\\Users\\Secret\\eclipse-workspace\\First\\white.jpg");
	ImageIcon black = new ImageIcon("C:\\Users\\Secret\\eclipse-workspace\\First\\black.jpg");
	ImageIcon queen = new ImageIcon("C:\\Users\\Secret\\eclipse-workspace\\First\\queen.jpg");
	
	First(){
		setTitle("N-Queens Problem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		c.requestFocus();
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout());
		c.add(menu, BorderLayout.NORTH);
		c.add(Board, BorderLayout.CENTER);
		JButton manual = new JButton("설명서");
		manual.addActionListener(new manualListener());
		menu.add(manual);
		menu.add(new JLabel("크기입력 : "));
		menu.add(input);
		
		JButton create = new JButton("보드생성");
		create.addActionListener(new createListener());	
		menu.add(create);
		
		JButton pre = new JButton("이전 해");
		pre.addActionListener(new preListener());
		menu.add(pre);
		
		menu.add(page);

		JButton next = new JButton("다음 해");
		next.addActionListener(new nextListener());
		menu.add(next);

		solution.addActionListener(new solutionListener());
		menu.add(solution);
		menu.add(time);
		
		setSize(900,900);
		setVisible(true);	
	}

	class createListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			try {
				size = Integer.parseInt(input.getText());	//보드크기입력받음
				if(size<4) {
					JOptionPane.showMessageDialog(null, "4 이상의 자연수 입력를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "4 이상의 자연수 입력를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int line=0;
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			b.setText(input.getText());		//이 부분이 없으면
								//동작을 안하는데 문제점을 찾지 못했습니다.
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					p = new JPanel[size][size]; // 보드생성
					line++;		//보드를 그리는 반복문
					if(line%2==0) {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(black.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					else {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(white.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					Board.add(p[i][j]);
				}
				if(size%2==0)
					line++;
			}
			c.add(Board, BorderLayout.CENTER);
		}
	}
	
	class solutionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			vs.clear();
			long st = System.currentTimeMillis();
			j.start(size);	//알고리즘 실행
			long et = System.currentTimeMillis();
			long pt = et-st;	// 시간 측정
			time.setText("걸린시간 : " + pt +"msec");
			pagen=1;
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			p = new JPanel[size][size];
			while(j.sol.size()!=0)
				vs.add(j.sol.pop());	//스택을 벡터로 옮김
			solcount = vs.size()/size;
			page.setText("page : "+pagen+"/"+solcount);
			solution.setText(input.getText());
			int line = 0;
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					line++;
					if(line%2==0) {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(black.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					else {
						p[i][j] = new JPanel() {
								public void paintComponent(Graphics g) {
									super.paintComponent(g);
									g.drawImage(white.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
								}
							};
					}
				}
				if(size%2==0)
					line++;
			}
			for(int i=0;i<size;i++) {
				p[vs.elementAt(i).x][vs.elementAt(i).y] = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(queen.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
					}// 첫번째 해 출력
				};
			}
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					Board.add(p[i][j]);
				}
			}
			c.add(Board, BorderLayout.CENTER);
		}
	}
	
	class nextListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(pagen>=solcount)
				pagen=1;
			else
				pagen++;
			page.setText("page : "+pagen+"/"+solcount);
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			p = new JPanel[size][size];
			
			solcount = vs.size()/size;
			page.setText("page : "+pagen+"/"+solcount);
			
			int line = 0;
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					line++;
					if(line%2==0) {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(black.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					else {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(white.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					Board.add(p[i][j]);
				}
				if(size%2==0)
					line++;
			}
			Board.removeAll();
			for(int i=0;i<size;i++) {
				p[vs.elementAt((pagen-1)*size+i).x][vs.elementAt((pagen-1)*size+i).y] = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(queen.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
					}
				};
			}
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					Board.add(p[i][j]);
				}
			}
			c.add(Board, BorderLayout.CENTER);
		}
	}
	
	class preListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(pagen==1)
				pagen=solcount;
			else
				pagen--;
			page.setText("page : "+pagen+"/"+solcount);
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			p = new JPanel[size][size];
			
			solcount = vs.size()/size;
			page.setText("page : "+pagen+"/"+solcount);
			
			int line = 0;
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					line++;
					if(line%2==0) {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(black.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					else {
						p[i][j] = new JPanel() {
							public void paintComponent(Graphics g) {
								super.paintComponent(g);
								g.drawImage(white.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
							}
						};
					}
					Board.add(p[i][j]);
				}
				if(size%2==0)
					line++;
			}
			Board.removeAll();
			for(int i=0;i<size;i++) {
				p[vs.elementAt((pagen-1)*size+i).x][vs.elementAt((pagen-1)*size+i).y] = new JPanel() {
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(queen.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
					}
				};
			}
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					Board.add(p[i][j]);
				}
			}
			c.add(Board, BorderLayout.CENTER);
		}
	}

	public static void main(String[] args) {
		First gui = new First();
	}
}

class manualListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		JOptionPane.showMessageDialog(null, "보드의 크기를 입력 한 후에 보드 생성 버튼을 누르면 보드가 생성된다.\n "
				+ "그 후에 해 구하기 버튼을 누르면 모든 해가 구해지고 이전 해, 다음 해로 다른 해를 볼 수 있다.\n"
				+ "보드의 크기가 10 이상이면 해를 구하는 시간이 오래 걸린다.", "설명서", JOptionPane.QUESTION_MESSAGE);
		
	}
}

