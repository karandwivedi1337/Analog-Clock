import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;

/*
<applet code="clock" width=375 height=200>
</applet>
*/

public class clock extends Applet implements ActionListener,Runnable{

Label l1,l2,l3;
TextField t1,t2,t3;
Button b;

double x1,y1,x2,y2,x3,y3,r=50;
int hr=0,min=0,sec=0;
double hrrad=0,minrad=0,secrad=0;
boolean hrset=false, minset=false,secset=false;
String am_pm="";
Thread t;

public void init(){

l1=new Label("Hr:");
l2=new Label("Min:");
l3=new Label("Sec:");

t1=new TextField(5);
t2=new TextField(5);
t3=new TextField(5);
b=new Button("Show");
add(l1);
add(t1);
add(l2);
add(t2);
add(l3);
add(t3);
add(b);

b.addActionListener(this);

}


public void start(){

}


public void actionPerformed(ActionEvent ae){

Calendar calender = new GregorianCalendar();


if(t1.getText().equals("")){
hr=calender.get(Calendar.HOUR); hrset=true;}

if(t2.getText().equals("")){
min=calender.get(Calendar.MINUTE); minset=true;}

if(t3.getText().equals("")){
sec=calender.get(Calendar.SECOND); secset=true;}


if(calender.get(Calendar.AM_PM) == 0) am_pm = "AM";
else am_pm = "PM";

if(hrset==false) hr=Integer.parseInt(t1.getText());
if(minset==false) min=Integer.parseInt(t2.getText());
if(secset==false) sec=Integer.parseInt(t3.getText());


t=new Thread(this);
t.start();

}

public void run(){

for(;;){

double secangle=(sec*6)%360;
double minangle=(min*6)%360 + secangle/60;
double hrangle=((hr%12)*30) + minangle/12;


minrad=((Math.PI)*(minangle-90))/180;
hrrad=((Math.PI)*(hrangle-90))/180;
secrad=((Math.PI)*(secangle-90))/180;

//System.out.println(secrad + " " + minrad + "  " + hrrad);

x1=(r-1)*Math.cos(secrad);
y1=(r-1)*Math.sin(secrad);

x2=(r-5)*Math.cos(minrad);
y2=(r-5)*Math.sin(minrad);

x3=(r-9)*Math.cos(hrrad);
y3=(r-9)*Math.sin(hrrad);

try{
t.sleep(1000);

int total=hr*3600+min*60+sec;
++total;

hr=total/3600;
min=(total%3600)/60;
sec=(total%3600)%60;

}

catch(InterruptedException e){}

repaint();

}

}//run close


public void paint(Graphics g){

showStatus("Time is: " + hr + ":" + min + ":" + sec + " " +am_pm);

g.drawOval(130,80,100,100);

//center=180,130

double th=0;

for(int i=1;i<=12;i++){

double p1=(r-2)*Math.cos(((Math.PI)*(th-90))/180);
double q1=(r-2)*Math.sin(((Math.PI)*(th-90))/180);

double p2=(r+2)*Math.cos(((Math.PI)*(th-90))/180);
double q2=(r+2)*Math.sin(((Math.PI)*(th-90))/180);

th+=30;
g.drawLine(180+(int)p1,130+(int)q1,180+(int)p2,130+(int)q2);
}

g.drawLine(180,130,180+(int)x1,130+(int)y1); //sec hand
g.drawLine(180,130,180+(int)x2,130+(int)y2); //min hand
g.drawLine(180,130,180+(int)x3,130+(int)y3); //hr hand


}

}


