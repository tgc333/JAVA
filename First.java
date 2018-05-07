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
	JButton solution = new JButton("�� ���ϱ�");
	JLabel time = new JLabel("�ɸ� �ð� : ");
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
		JButton manual = new JButton("����");
		manual.addActionListener(new manualListener());
		menu.add(manual);
		menu.add(new JLabel("ũ���Է� : "));
		menu.add(input);
		
		JButton create = new JButton("�������");
		create.addActionListener(new createListener());	
		menu.add(create);
		
		JButton pre = new JButton("���� ��");
		pre.addActionListener(new preListener());
		menu.add(pre);
		
		menu.add(page);

		JButton next = new JButton("���� ��");
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
				size = Integer.parseInt(input.getText());	//����ũ���Է¹���
				if(size<4) {
					JOptionPane.showMessageDialog(null, "4 �̻��� �ڿ��� �Է¸� �Է��ϼ���.", "�Է� ����", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "4 �̻��� �ڿ��� �Է¸� �Է��ϼ���.", "�Է� ����", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int line=0;
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			b.setText(input.getText());		//�� �κ��� ������
								//������ ���ϴµ� �������� ã�� ���߽��ϴ�.
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					p = new JPanel[size][size]; // �������
					line++;		//���带 �׸��� �ݺ���
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
			j.start(size);	//�˰��� ����
			long et = System.currentTimeMillis();
			long pt = et-st;	// �ð� ����
			time.setText("�ɸ��ð� : " + pt +"msec");
			pagen=1;
			Board.repaint();
			Board.removeAll();
			Board.setLayout(new GridLayout(size,size));
			p = new JPanel[size][size];
			while(j.sol.size()!=0)
				vs.add(j.sol.pop());	//������ ���ͷ� �ű�
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
					}// ù��° �� ���
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
		JOptionPane.showMessageDialog(null, "������ ũ�⸦ �Է� �� �Ŀ� ���� ���� ��ư�� ������ ���尡 �����ȴ�.\n "
				+ "�� �Ŀ� �� ���ϱ� ��ư�� ������ ��� �ذ� �������� ���� ��, ���� �ط� �ٸ� �ظ� �� �� �ִ�.\n"
				+ "������ ũ�Ⱑ 10 �̻��̸� �ظ� ���ϴ� �ð��� ���� �ɸ���.", "����", JOptionPane.QUESTION_MESSAGE);
		
	}
}

