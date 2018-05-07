import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Server_RMI extends JFrame implements Runnable{

	int seconds;
	int speed;
	int flag;
	Calendar calend;
	JTextField tf;
	JButton bEdit;
	JButton bUpdate;
	
	public void setSpeed_view(int sp){
		speed = sp;
	}
	
	public void setSeconds_view(int sc){
		seconds = sc;
	}
	
	public int getSeconds_view(){
		return seconds;
	}

	int getSecondsFromCalendar(){
		calend = new GregorianCalendar();
		int	tmp = 0;
		tmp += (calend.get(Calendar.HOUR_OF_DAY) * 3600);
		tmp += (calend.get(Calendar.MINUTE) * 60);
		tmp += calend.get(Calendar.SECOND);
		return tmp;
	}
	
	int getHours(){
		return (seconds / 3600);
	}
	
	int getMinutes(){
		return ((seconds % 3600) / 60);
	}
	
	int getSeconds(){
		return (seconds % 60);
	}
	
	String getTimeText(){
		String ret = "";
		int actHours = getHours();
		int actMinutes = getMinutes();
		int actSeconds = getSeconds();
		if (actHours < 10)
			ret += "0";
		ret += actHours;
		ret += " : ";
		if (actMinutes < 10)
			ret += "0";
		ret += actMinutes;
		ret += " : ";
		if (actSeconds < 10)
			ret += "0";
		ret += actSeconds;
		return ret;
	}
	
	int valid(char digit){
		if ('0' <= digit && digit <= '9')
			return 1;
		return 0;
	}
	
	int validMiddle(String middle){
		if (middle.compareTo(" : ") == 0)
			return 1;
		return 0;
	}
	
	public void run(){
		while(true){
			int a = 0;
			a++;
			System.out.print(".");
			if (flag == 1){
				try{
					Thread.sleep(speed);
				}catch(InterruptedException e){
					return;
				}
				seconds++;
				seconds %= 86400;
				tf.setText(getTimeText());
			}
		}
	}
	
	Server_RMI(int sec){
		
		speed = 1000;
		flag = 1;
		if (sec == 0)
			seconds = getSecondsFromCalendar();
		else seconds = sec;
	
		tf = new JTextField(getTimeText());
		tf.setBounds(50 , 50, 300, 40);
		
		bEdit = new JButton("Editar hora");
		bEdit.setBounds(50, 100, 100, 50);
		
		bUpdate = new JButton("Actualizar hora");
		bUpdate.setBounds(250, 100, 100, 50);
		
		bEdit.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					flag = 0;
				}
			}
		);
		
		bUpdate.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String txt = tf.getText();
					int sintax = 1;
					if (txt.length() != 12)
						sintax = 0;
					if (sintax == 1){
						if (valid(txt.charAt(0)) < 1 || valid(txt.charAt(1)) < 1)
							sintax = 0;
						if (valid(txt.charAt(5)) < 1 || valid(txt.charAt(6)) < 1)
							sintax = 0;
						if (valid(txt.charAt(10)) < 1 || valid(txt.charAt(11)) < 1)
							sintax = 0;
						if (validMiddle(txt.substring(2, 5)) < 1)
							sintax = 0;
						if (validMiddle(txt.substring(7, 10)) < 1)
							sintax = 0;
						if (sintax == 1){
							
							int actHours = Integer.parseInt(txt.substring(0, 2));
							int actMinutes = Integer.parseInt(txt.substring(5, 7));
							int actSeconds = Integer.parseInt(txt.substring(10, 12));
							if (!(0 <= actHours && actHours <= 24))
								sintax = 0;
							if (!(0 <= actMinutes && actMinutes <= 60))
								sintax = 0;
							if (!(0 <= actSeconds && actSeconds <= 60))
								sintax = 0;
							if (sintax == 1){
								seconds = actHours * 3600 + actMinutes * 60 + actSeconds; 
								flag = 1;
							}
						}
					}
					if (sintax == 0){
						tf.setText("Formato invalido, HH : MM : SS");
					}
				}
			}
		);
		
		add(bEdit);
		add(bUpdate);
		add(tf);
		
		setSize(420, 250);
		setLayout(null);
		setVisible(true);
	}

}