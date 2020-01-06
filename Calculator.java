import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Calculator extends Frame implements ActionListener
{
	Button b[]=new Button[24];
	TextField tf=new TextField();
	StringBuffer sb=new StringBuffer();
	String name[]={"%","ce","c","<-","1/x","x*x","x^1/2","/","7","8","9","*","4","5","6","-","1","2","3","+","+/-","0",".","="};
	Calculator(String s)
	{
		super(s);
		tf.setBounds(70,0,280,100);
		setSize(500,500);
		setLayout(null);
		setVisible(true);
		add(tf);
		addWindowListener(new Windowcloseevent());
		int x=0,y=150,width=70,height=40,k=0,st=0;
		for(int j=0;j<6;j++)
		{
		for(int i=0;i<4;i++)
		{
			b[k++]=new Button(name[st++]);
			b[k-1].setBounds(x=x+width,y,width,height);
			b[k-1].addActionListener(this);
			add(b[k-1]);
			
		}
		y=y+40;
		x=0;
 		}
	}
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<b.length;i++)
		{
			if(e.getSource()==b[i])
			{
				switch(name[i])
				{
					case "<-":
						if(sb.length()>0)
							sb=sb.delete(sb.length()-1,sb.length());
						break;
					case "c":
						sb=new StringBuffer();
						Calculation.top=-1;
						break;
					case "1/x":
						if(Calculation.top==-1)
							sb=new StringBuffer(String.valueOf((1/Double.parseDouble(sb.toString()))));
						else
						{
							sb=new StringBuffer(String.valueOf((1/Calculation.arr[Calculation.top])));
							Calculation.arr[Calculation.top]=Double.parseDouble(sb.toString());
						}
						break;
					case "x*x":
						if(Calculation.top==-1)
							sb=new StringBuffer(String.valueOf(Double.parseDouble(sb.toString())*Double.parseDouble(sb.toString())));
						else
						{
							sb=new StringBuffer(String.valueOf((Calculation.arr[Calculation.top]*Calculation.arr[Calculation.top])));
							Calculation.arr[Calculation.top]=Double.parseDouble(sb.toString());

						}
						break;
					case "x^1/2":
						if(Calculation.top==-1)
							sb=new StringBuffer(String.valueOf(Math.pow(Double.parseDouble(sb.toString()),.5)));
						else
						{
							sb=new StringBuffer(String.valueOf(Math.pow(Calculation.arr[Calculation.top],.5)));
							Calculation.arr[Calculation.top]=Double.parseDouble(sb.toString());
						}
						break;


							
					case "=":
						StringTokenizer ob=new StringTokenizer(sb.toString(),"-+*/");
						double ans=Calculation.solution(sb.toString().toCharArray(),ob);
						sb=new StringBuffer(String.valueOf(ans));
						Calculation.opt=-1;
						break;		
					default:
						sb.append(name[i]);

				}
				tf.setText(sb.toString());

			}
		}
	}
	public static void main(String... s)
	{
		new Calculator("calculator");
	}
}
class Windowcloseevent extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
}





class Calculation
{
static double arr[]=new double[1000];
static char op[]=new char[1000];
static int top=-1;
static int opt=-1;
public static double solution(char exp[],StringTokenizer ob)
{
	int flag=0;
	for(int i=0;i<exp.length;i++)
	{
		if(!(42<=(int)exp[i] && (int)exp[i]<=47) && flag!=1)     //3*4*5-5-6-4/3

		{
			flag=1;
			while(ob.hasMoreTokens())
			{
			if(opt!=-1 && op[opt]=='-')
				arr[++top]=-(Double.parseDouble(ob.nextToken()));
			else
			{
			//	String ss=ob.nextToken();
			//	System.out.println(ss);
			arr[++top]=Double.parseDouble(ob.nextToken());
			}
			break;
			}
			
		}
		else if((42<=(int)exp[i] &&(int)exp[i]<=47)&&exp[i]!='.')
		{
			flag=0;
			while(true)
			{
			if(opt==-1)
			{
				op[++opt]=exp[i];
				break;
			}
			else
			{
				
											//8.0*2/2
				if(precedence(exp[i])>precedence(op[opt]))  
				{
					op[++opt]=exp[i];
					break;
				}
				else
				{
					System.out.println("top:"+top+"    "+arr[top]);
					calculation(op[opt--]);
					System.out.println("top:"+top+" "+arr[top]);
				}
				
			}
			}
		}

	}
	if(top==0)
	{
		return arr[top];
	}
	else
	{
		for(int i=0;i<=opt;i++)
		{
		//	System.out.println(arr[top]);
			calculation(op[opt-i]);
		}
		return arr[top];
	}

}
public static void calculation(char c)
{
	double sum,diff,mult,div;
	int a,b;
	double x,y;
	switch(c)
	{
		case '+':
			sum=arr[top]+arr[--top];
			arr[top]=sum;
			break;
		case '-':
			a=top;
			diff=arr[--top]+arr[a];
			arr[top]=diff;
			break;
		case '*':
				x=arr[top];
				y=arr[--top];
				System.out.println(x+" "+y);
			mult=x*y;

			arr[top]=mult;
			break;
		case '/':
			a=top;
			div=arr[--top]/arr[a];
			arr[top]=div;
			break;
	}
}
			
public static int precedence(char c)
{
	if(c=='-' || c=='+')
		return 0;
	else if(c=='*' || c=='/')
		return 1;
	else
		return -1;
}
} 






