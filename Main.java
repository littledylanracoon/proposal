import java.io.*;
import javax.sound.midi.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*; //事件
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

class Main extends JPanel implements KeyListener {
  static int state = 1, attr = 1,count = 1, mode = 1;
  //Kept track of which key is pressed
  private boolean[] keyDown = new boolean[250];
  //Holds which key plays what note
  private HashMap<Character, Integer> notes = new HashMap<Character, Integer>();
  static JLabel[] mouse_label = new JLabel[37];
  static JLabel[] noteboard = new JLabel[22];
  static JLabel[] whitered = new JLabel[22];
  static JLabel[] noteblack = new JLabel[15];
  static JLabel[] blackred = new JLabel[15];
  TimeUnit time = TimeUnit.MILLISECONDS;
  //static JLabel[][] mouse_label = new JLabel[37][10];
  static final int position[][] = new int[37][2];
  static final int record[][] = new int[100][2];
  static final int blank[] = new int[100];
  //static final int notepos[][] = new int[22][2];
  //initiate synthesizer variables
  Synthesizer syn;
  MidiChannel[] midChannel;
  Instrument[] instrument;
  static int countnote = 0;
  int instNum = 88; //change this to change instruments
  //static int temp = 0;
  int midiChannel = 7;
  long keyPressedMillis;
  long keyPressedMillis1;
  int keyPressLength;
  int keyPressLength1;
  static JLabel fortone;
  static JLabel forrhythm;
  static JLabel lb;
  static JLabel formode;
  public Main() {
    this.setPreferredSize(new Dimension(1000, 4000));
    addKeyListener(this);

    setKeys(notes);

    try {
      syn = MidiSystem.getSynthesizer();
      syn.open();
      midChannel = syn.getChannels();
      midChannel[midiChannel].programChange(instNum);
      instrument = syn.getAvailableInstruments();
      syn.loadInstrument(instrument[50]);
      //makeSound(notes.get('a'), 100);
      //makeSound(notes.get('b'), 100);
      //makeSound(notes.get('2'), 100);
    } catch (MidiUnavailableException ex) {
      ex.printStackTrace();
    }
  }

  public void addNotify() {
    super.addNotify();
    requestFocus();
  }
  
  public static class MyBtnListener implements ActionListener{ //private class，Player專用的ButtonListener
		
		public void actionPerformed(ActionEvent e) {// ActionListener包含的函式 拿來自己定義
			
			String command = e.getActionCommand();
			if(command.equals("Major C")) { 
        Main.fortone.setText("TONE: Major C");
				System.out.println("Major C");
        Main.count = 1;
				Main.state = 1;
			}
			else if(command.equals("Major D")) {
        Main.fortone.setText("TONE: Major D");
				System.out.println("Major D");
        //lb.setVisible(false);
        Main.count = 1;
        Main.state = 2;
			}
			else if(command.equals("Major G")) {
        Main.fortone.setText("TONE: Major G");
				System.out.println("Major G");
        Main.count = 1;
        Main.state = 3;
  		}
      else if(command.equals("Major F")) {
        Main.fortone.setText("TONE: Major F");
				System.out.println("Major F");
        Main.count = 1;
        Main.state = 4;
			}
			else if(command.equals("Chord")) {
        Main.forrhythm.setText("RHYTHM: Chord");
				System.out.println("Chord");
        Main.attr = 1;
        Main.count = 1;
  		}
      else if(command.equals("Jump")) {
        Main.forrhythm.setText("RHYTHM: Jump");
				System.out.println("Jump");
        Main.attr = 2;
        Main.count = 1;
  		}
      else if(command.equals("Smooth")) {
        Main.forrhythm.setText("RHYTHM: Smooth");
				System.out.println("Smooth");
        Main.attr = 3;
        Main.count = 1;
  		}
      else if(command.equals("Normal")) {
        Main.formode.setText("MODE: Normal");
				System.out.println("normal");
        Main.mode = 1;
        Main.count = 1;
  		}
      else if(command.equals("Accompaniment")) {
        Main.formode.setText("MODE: Accompaniment");
				System.out.println("Accompaniment");
        Main.mode = 2;
        Main.count = 1;
  		}
      else if(command.equals("Stop Record")) 
      	System.out.println(Main.countnote);
      
      else if(command.equals("Play")){ 
				System.out.println("Play");
        for(int i = 0; i < Main.countnote; i++){
          makeSound(record[i][0], 550);
          try{
            time.sleep(record[i][1]);      
          }
          catch (InterruptedException e1) {
            System.out.println("Interrupted while Sleeping");
          }
          stopSound(record[i][0]);
          try{
            time.sleep(blank[i]);      
          }
          catch (InterruptedException e2) {
            System.out.println("Interrupted while Sleeping");
          }
        }
      }

		}
		
	}
  
  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    //Adds key to the keyDown hashmap if key is pressed
    if (keyDown[e.getKeyCode()]) {
      return;
    } 
    else {
      //play a note if the key has an assigned note
      if(notes.containsKey(e.getKeyChar())){
      //FileOutputStream(File file, boolean append);
      /*  try {
            //FileOutputStream fos=new FileOutputStream(new File("abc.txt"),true);
            PrintWriter Writer = new PrintWriter(new FileOutputStream("output.txt", true));
            Writer.println(e.getKeyChar());
            Writer.flush();
            Writer.close();
            } 
        catch (FileNotFoundException f) {
            f.printStackTrace();
        }
        */
        makeSound(notes.get(e.getKeyChar()), 550);
        keyPressedMillis = System.currentTimeMillis(); 
        if(countnote != 0){
          blank[countnote - 1] = (int)(System.currentTimeMillis() - keyPressedMillis1);
        }

        record[countnote][0] = notes.get(e.getKeyChar());
        if(notes.get(e.getKeyChar()) == 36){ 
          noteboard[0].setVisible(false);
          whitered[0].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 37){ 
          noteblack[0].setVisible(false);
          blackred[0].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 38){ 
          noteboard[1].setVisible(false);
          whitered[1].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 39){ 
          noteblack[1].setVisible(false);
          blackred[1].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 40){ 
          noteboard[2].setVisible(false);
          whitered[2].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 41){ 
          noteboard[3].setVisible(false);
          whitered[3].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 42){ 
          noteblack[2].setVisible(false);
          blackred[2].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 43){ 
          noteboard[4].setVisible(false);
          whitered[4].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 44){ 
          noteblack[3].setVisible(false);
          blackred[3].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 45){ 
          noteboard[5].setVisible(false);
          whitered[5].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 46){ 
          noteblack[4].setVisible(false);
          blackred[4].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 47){ 
          noteboard[6].setVisible(false);
          whitered[6].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 48){ 
          noteboard[7].setVisible(false);
          whitered[7].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 49){ 
          noteblack[5].setVisible(false);
          blackred[5].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 50){ 
          noteboard[8].setVisible(false);
          whitered[8].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 51){ 
          noteblack[6].setVisible(false);
          blackred[6].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 52){ 
          noteboard[9].setVisible(false);
          whitered[9].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 53){ 
          noteboard[10].setVisible(false);
          whitered[10].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 54){ 
          noteblack[7].setVisible(false);
          blackred[7].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 55){ 
          noteboard[11].setVisible(false);
          whitered[11].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 56){ 
          noteblack[8].setVisible(false);
          blackred[8].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 57){ 
          noteboard[12].setVisible(false);
          whitered[12].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 58){ 
          noteblack[9].setVisible(false);
          blackred[9].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 59){ 
          noteboard[13].setVisible(false);
          whitered[13].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 60){ 
          noteboard[14].setVisible(false);
          whitered[14].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 61){ 
          noteblack[10].setVisible(false);
          blackred[10].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 62){ 
          noteboard[15].setVisible(false);
          whitered[15].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 63){ 
          noteblack[11].setVisible(false);
          blackred[11].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 64){ 
          noteboard[16].setVisible(false);
          whitered[16].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 65){ 
          noteboard[17].setVisible(false);
          whitered[17].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 66){ 
          noteblack[12].setVisible(false);
          blackred[12].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 67){ 
          noteboard[18].setVisible(false);
          whitered[18].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 68){ 
          noteblack[13].setVisible(false);
          blackred[13].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 69){ 
          noteboard[19].setVisible(false);
          whitered[19].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 70){ 
          noteblack[14].setVisible(false);
          blackred[14].setVisible(true);   
        }
        else if(notes.get(e.getKeyChar()) == 71){ 
          noteboard[20].setVisible(false);
          whitered[20].setVisible(true);
        }
        else if(notes.get(e.getKeyChar()) == 72){ 
          noteboard[21].setVisible(false);
          whitered[21].setVisible(true);
        }
        
        System.out.println(notes.get(e.getKeyChar()));
        if(Main.mode == 2){
        if(Main.attr == 1){  //chord
          switch(Main.state){
            case 1: //Major C
              if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                makeSound(notes.get('i'), 100);
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('b'), 100); 
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100); 
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                makeSound(notes.get('i'), 100);
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 43){ //so
                mouse_label[7].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                makeSound(notes.get('g'), 100);
                makeSound(notes.get('d'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 44){ //so#
                mouse_label[8].setVisible(true); 
                System.out.println("go"); //me so# si
                makeSound(notes.get('g'), 100); 
                makeSound(notes.get('c'), 100);
                makeSound(notes.get('1'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 45){ //la
                mouse_label[9].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 46){ //la#
                mouse_label[10].setVisible(true); 
                System.out.println("go"); //re# go la#
                makeSound(notes.get('h'), 100); 
                makeSound(notes.get('d'), 100);
                makeSound(notes.get('a'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 47){ //si
                mouse_label[11].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 48){ //do 
                mouse_label[12].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('2'), 100); 
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 49){ //do#
                mouse_label[13].setVisible(true); 
                System.out.println("go"); //la do me
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 50){ //re
                mouse_label[14].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100);
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 51){ //re#
                mouse_label[15].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 52){ //mi
                mouse_label[16].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 53){ //fa
                mouse_label[17].setVisible(true);
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 54){ //fa#
                mouse_label[18].setVisible(true); 
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 55){ //so
                mouse_label[19].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 56){ //so#
                mouse_label[20].setVisible(true); 
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 57){ //la
                mouse_label[21].setVisible(true); 
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 58){ //la#
                mouse_label[22].setVisible(true);   
                makeSound(notes.get('5'), 100); //re# so si
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 59){ //si
                mouse_label[23].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 60){ //do
                mouse_label[24].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 61){ //do#
                mouse_label[25].setVisible(true); 
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 62){ //re
                mouse_label[26].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 63){ //re#
                mouse_label[27].setVisible(true);
                makeSound(notes.get('Q'), 100); //si re# fa#
                makeSound(notes.get('E'), 100);
                makeSound(notes.get('T'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 64){ //mi
                mouse_label[28].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 65){ //fa
                mouse_label[29].setVisible(true); 
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 66){ //fa#
                mouse_label[30].setVisible(true); 
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 67){ //so
                mouse_label[31].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('5'), 100); //re# so si
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              break;
            case 2:  //D
            if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                makeSound(notes.get('i'), 100);
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('b'), 100); 
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100); 
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('b'), 100); 
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 43){ //so
                mouse_label[7].setVisible(true);
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 44){ //so#
                mouse_label[8].setVisible(true);
                System.out.println("go"); //me so# si
                makeSound(notes.get('g'), 100); 
                makeSound(notes.get('c'), 100);
                makeSound(notes.get('1'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 45){ //la
                mouse_label[9].setVisible(true);
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 46){ //la#
                mouse_label[10].setVisible(true);
                System.out.println("go"); //re# go la#
                makeSound(notes.get('h'), 100); 
                makeSound(notes.get('d'), 100);
                makeSound(notes.get('a'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 47){ //si
                mouse_label[11].setVisible(true);
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 48){ //do 
                mouse_label[12].setVisible(true);
                System.out.println("go"); //do mi so
                makeSound(notes.get('2'), 100); 
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 49){ //do#
                mouse_label[13].setVisible(true);
                System.out.println("go"); //la do# me
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 50){ //re
                mouse_label[14].setVisible(true);
                System.out.println("go"); //re fa# la
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 51){ //re#
                mouse_label[15].setVisible(true);
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 52){ //mi
                mouse_label[16].setVisible(true);
                makeSound(notes.get('b'), 100); //la do# mi
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 53){ //fa
                mouse_label[17].setVisible(true);
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 54){ //fa#
                mouse_label[18].setVisible(true);
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 55){ //so
                mouse_label[19].setVisible(true);
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 56){ //so#
                mouse_label[20].setVisible(true);
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 57){ //la
                mouse_label[21].setVisible(true);
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 58){ //la#
                mouse_label[22].setVisible(true);
                makeSound(notes.get('5'), 100); //re# so si
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 59){ //si
                mouse_label[23].setVisible(true);
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 60){ //do
                mouse_label[24].setVisible(true);
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 61){ //do#
                mouse_label[25].setVisible(true);
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 62){ //re
                mouse_label[26].setVisible(true);
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 63){ //re#
                mouse_label[27].setVisible(true);
                makeSound(notes.get('Q'), 100); //si re# fa#
                makeSound(notes.get('E'), 100);
                makeSound(notes.get('T'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 64){ //mi
                mouse_label[28].setVisible(true);
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 65){ //fa
                mouse_label[29].setVisible(true);
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 66){ //fa#
                mouse_label[30].setVisible(true);
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 67){ //so
                mouse_label[31].setVisible(true);
                makeSound(notes.get('9'), 100); //do si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('5'), 100); //re# so si
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);
              }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              break;
            case 3:  //G
              if(notes.get(e.getKeyChar()) == 43){ //so
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 44){ //so#
                System.out.println("go"); //me so# si
                makeSound(notes.get('g'), 100); 
                makeSound(notes.get('c'), 100);
                makeSound(notes.get('1'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 45){ //la
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 46){ //la#
                System.out.println("go"); //re# go la#
                makeSound(notes.get('h'), 100); 
                makeSound(notes.get('d'), 100);
                makeSound(notes.get('a'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 47){ //si
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 48){ //do 
                System.out.println("go"); //do mi so
                makeSound(notes.get('2'), 100); 
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 49){ //do#
                System.out.println("go"); //la do# me
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 50){ //re
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100);
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 51){ //re#
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 52){ //mi
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 53){ //fa
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 54){ //fa#
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 55){ //so
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 56){ //so#
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 57){ //la
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 58){ //la#
                makeSound(notes.get('5'), 100); //re# so si
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 59){ //si
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 60){ //do
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 61){ //do#
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 62){ //re
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 63){ //re#
                makeSound(notes.get('Q'), 100); //si re# fa#
                makeSound(notes.get('E'), 100);
                makeSound(notes.get('T'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 64){ //mi
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 65){ //fa
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 66){ //fa#
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 67){ //so
                makeSound(notes.get('9'), 100); //do si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              break;
            case 4: //F
              if(notes.get(e.getKeyChar()) == 43){ //so
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                makeSound(notes.get('g'), 100);
                makeSound(notes.get('d'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 44){ //so#
                System.out.println("go"); //me so# si
                makeSound(notes.get('g'), 100); 
                makeSound(notes.get('c'), 100);
                makeSound(notes.get('1'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 45){ //la
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 46){ //la#
                System.out.println("go"); //la# re fa
                makeSound(notes.get('a'), 100); 
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('7'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 47){ //si
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 48){ //do 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 49){ //do#
                System.out.println("go"); //la do me
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 50){ //re
                System.out.println("go"); //la# re fa
                makeSound(notes.get('a'), 100);
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('7'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 51){ //re#
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 52){ //mi
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 53){ //fa
                makeSound(notes.get('a'), 100); //la# re fa
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('7'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 54){ //fa#
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 55){ //so
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 56){ //so#
                makeSound(notes.get('6'), 100); //mi so# si
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 57){ //la
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 58){ //la#
                makeSound(notes.get('a'), 100); //la# re fa
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('7'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 59){ //si
                makeSound(notes.get('9'), 100); //so si re
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 60){ //do
                makeSound(notes.get('7'), 100); //fa la do
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 61){ //do#
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 62){ //re
                makeSound(notes.get('a'), 100); //la# re fa
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('7'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 63){ //re#
                makeSound(notes.get('Q'), 100); //si re# fa#
                makeSound(notes.get('E'), 100);
                makeSound(notes.get('T'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 64){ //mi
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 65){ //fa
                makeSound(notes.get('='), 100); //la# re fa
                makeSound(notes.get('S'), 100);
                makeSound(notes.get('F'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 66){ //fa#
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 67){ //so
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              break;
          }
        }
        else if(Main.attr == 2){  //jump
          //if()
          switch(Main.state){
            case 1:  //C
              if(Main.count == 1){
              if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
               }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('b'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                }
                else if(notes.get(e.getKeyChar()) == 43){
                  mouse_label[7].setVisible(true);
                  makeSound(notes.get('k'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  mouse_label[8].setVisible(true);
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  mouse_label[9].setVisible(true);
                  makeSound(notes.get('f'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  mouse_label[10].setVisible(true);
                  makeSound(notes.get('h'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  mouse_label[11].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  mouse_label[12].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  mouse_label[13].setVisible(true);
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  mouse_label[14].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  mouse_label[15].setVisible(true);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  mouse_label[16].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  mouse_label[17].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  mouse_label[18].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  mouse_label[19].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  mouse_label[20].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  mouse_label[21].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  mouse_label[22].setVisible(true);
                  makeSound(notes.get('5'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  mouse_label[23].setVisible(true);
                  makeSound(notes.get('9'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  mouse_label[24].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  mouse_label[25].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  mouse_label[26].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  mouse_label[27].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  mouse_label[28].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  mouse_label[29].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  mouse_label[30].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  mouse_label[31].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('6'), 100); //mi so# si
                }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('7'), 100); //fa la do
                }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('5'), 100); //re# so si
                }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                }
                Main.count++;
              }
              else if(Main.count == 3){
                if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('i'), 100);
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('i'), 100);
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('b'), 100);
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('e'), 100);
                makeSound(notes.get('b'), 100);
              }
                else if(notes.get(e.getKeyChar()) == 43){
                  mouse_label[7].setVisible(true);
                  makeSound(notes.get('g'), 100);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  mouse_label[8].setVisible(true);
                  makeSound(notes.get('c'), 100);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  mouse_label[9].setVisible(true);
                  makeSound(notes.get('b'), 100);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  mouse_label[10].setVisible(true);
                  makeSound(notes.get('d'), 100);
                  makeSound(notes.get('a'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  mouse_label[11].setVisible(true);
                  makeSound(notes.get('1'), 100);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  mouse_label[12].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  mouse_label[13].setVisible(true);
                  makeSound(notes.get('3'), 100);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  mouse_label[14].setVisible(true);
                  makeSound(notes.get('1'), 100);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  mouse_label[15].setVisible(true);
                  makeSound(notes.get('5'), 100);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  mouse_label[16].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  mouse_label[17].setVisible(true);
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  mouse_label[18].setVisible(true);
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  mouse_label[19].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  mouse_label[20].setVisible(true);
                  makeSound(notes.get('0'), 100);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  mouse_label[21].setVisible(true);
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  mouse_label[22].setVisible(true);
                  makeSound(notes.get('9'), 100);
                  makeSound(notes.get('='), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  mouse_label[23].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  mouse_label[24].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  mouse_label[25].setVisible(true);
                  makeSound(notes.get('W'), 100);
                  makeSound(notes.get('D'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  mouse_label[26].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  mouse_label[27].setVisible(true);
                  makeSound(notes.get('E'), 100);
                  makeSound(notes.get('T'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  mouse_label[28].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  mouse_label[29].setVisible(true);
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  mouse_label[30].setVisible(true);
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  mouse_label[31].setVisible(true);
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('0'), 100);
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('-'), 100);
                makeSound(notes.get('A'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('9'), 100);
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('Q'), 100);
                makeSound(notes.get('S'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
                count++;
              }
            break;
            case 2:  //D
              if(Main.count == 1){
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('i'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  makeSound(notes.get('h'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  makeSound(notes.get('5'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  makeSound(notes.get('9'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('9'), 100);
                }
                Main.count++;
              }
              else if(Main.count == 3){
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('1'), 100);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('c'), 100);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('e'), 100);
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  makeSound(notes.get('d'), 100);
                  makeSound(notes.get('a'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  makeSound(notes.get('1'), 100);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  makeSound(notes.get('3'), 100);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('5'), 100);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('3'), 100);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('0'), 100);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  makeSound(notes.get('9'), 100);
                  makeSound(notes.get('='), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  makeSound(notes.get('W'), 100);
                  makeSound(notes.get('D'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('E'), 100);
                  makeSound(notes.get('T'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('W'), 100);
                  makeSound(notes.get('D'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('8'), 100);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                }
                count++;
              }
            break;
          }
        }
        else if(Main.attr == 3){  //smooth
          //if()
          switch(Main.state){
            case 1:  //C
              System.out.println(Main.state);
              if(Main.count == 1){
                System.out.println(notes.get(e.getKeyChar()));
              if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('b'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('d'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('k'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('f'), 100); 
                }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('i'), 100); 
                }
                else if(notes.get(e.getKeyChar()) == 43){
                  mouse_label[7].setVisible(true);
                  makeSound(notes.get('k'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  mouse_label[8].setVisible(true);
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  mouse_label[9].setVisible(true);
                  makeSound(notes.get('f'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  mouse_label[10].setVisible(true);
                  makeSound(notes.get('h'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  mouse_label[11].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  mouse_label[12].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  mouse_label[13].setVisible(true);
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  mouse_label[14].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  mouse_label[15].setVisible(true);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  mouse_label[16].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  mouse_label[17].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  mouse_label[18].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  mouse_label[19].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  mouse_label[20].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  mouse_label[21].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  mouse_label[22].setVisible(true);
                  makeSound(notes.get('5'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  mouse_label[23].setVisible(true);
                  makeSound(notes.get('9'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  mouse_label[24].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  mouse_label[25].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  mouse_label[26].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  mouse_label[27].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  mouse_label[28].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  mouse_label[29].setVisible(true);
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  mouse_label[30].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  mouse_label[31].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('6'), 100); //mi so# si
                }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('7'), 100); //fa la do
                }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('5'), 100); //re# so si
                }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('9'), 100); //so si re
                }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('2'), 100); //do mi so
                }
                Main.count++;
              }
              else if(Main.count == 3){
                if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('i'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('3'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('b'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('5'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('i'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('b'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('e'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 43){
                  mouse_label[7].setVisible(true);
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  mouse_label[8].setVisible(true);
                  makeSound(notes.get('c'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  mouse_label[9].setVisible(true);
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  mouse_label[10].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  mouse_label[11].setVisible(true);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  mouse_label[12].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  mouse_label[13].setVisible(true);
                  makeSound(notes.get('3'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  mouse_label[14].setVisible(true);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  mouse_label[15].setVisible(true);
                  makeSound(notes.get('5'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  mouse_label[16].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  mouse_label[17].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  mouse_label[18].setVisible(true);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  mouse_label[19].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  mouse_label[20].setVisible(true);
                  makeSound(notes.get('0'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  mouse_label[21].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  mouse_label[22].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  mouse_label[23].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  mouse_label[24].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  mouse_label[25].setVisible(true);
                  makeSound(notes.get('W'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  mouse_label[26].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  mouse_label[27].setVisible(true);
                  makeSound(notes.get('E'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  mouse_label[28].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  mouse_label[29].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  mouse_label[30].setVisible(true);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  mouse_label[31].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('0'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('-'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('9'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('Q'), 100);
                }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('6'), 100);
                }
                count++;
              }
              else if(Main.count == 5){
                if(notes.get(e.getKeyChar()) == 36){ //do
                mouse_label[0].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 37){ //do#
                mouse_label[1].setVisible(true); 
                System.out.println("go"); //la do# mi
                makeSound(notes.get('6'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 38){ //re
                mouse_label[2].setVisible(true); 
                System.out.println("go"); //so si re
                makeSound(notes.get('4'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 39){ //re#
                mouse_label[3].setVisible(true); 
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 40){ //mi
                mouse_label[4].setVisible(true); 
                System.out.println("go"); //do mi so
                makeSound(notes.get('g'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 41){ //fa
                mouse_label[5].setVisible(true); 
                System.out.println("go"); //fa la do
                makeSound(notes.get('2'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 42){ //fa#
                mouse_label[6].setVisible(true); 
                System.out.println("go"); //re fa# la
                makeSound(notes.get('b'), 100);
              }
               else if(notes.get(e.getKeyChar()) == 43){
                 mouse_label[7].setVisible(true);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  mouse_label[8].setVisible(true);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  mouse_label[9].setVisible(true);
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  mouse_label[10].setVisible(true);
                  makeSound(notes.get('a'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  mouse_label[11].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  mouse_label[12].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  mouse_label[13].setVisible(true);
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  mouse_label[14].setVisible(true);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  mouse_label[15].setVisible(true);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  mouse_label[16].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  mouse_label[17].setVisible(true);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  mouse_label[18].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  mouse_label[19].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  mouse_label[20].setVisible(true);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  mouse_label[21].setVisible(true);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  mouse_label[22].setVisible(true);
                  makeSound(notes.get('='), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  mouse_label[23].setVisible(true);
                  makeSound(notes.get('S'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  mouse_label[24].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  mouse_label[25].setVisible(true);
                  makeSound(notes.get('D'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  mouse_label[26].setVisible(true);
                  makeSound(notes.get('S'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  mouse_label[27].setVisible(true);
                  makeSound(notes.get('T'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  mouse_label[28].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  mouse_label[29].setVisible(true);
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  mouse_label[30].setVisible(true);
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  mouse_label[31].setVisible(true);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 68){ //so#
                mouse_label[32].setVisible(true); 
                makeSound(notes.get('Q'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 69){ //la
                mouse_label[33].setVisible(true); 
                makeSound(notes.get('A'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 70){ //la#
                mouse_label[34].setVisible(true);   
                makeSound(notes.get('='), 100);

              }
              else if(notes.get(e.getKeyChar()) == 71){ //si
                mouse_label[35].setVisible(true); 
                makeSound(notes.get('S'), 100);

              }
              else if(notes.get(e.getKeyChar()) == 72){ //do
                mouse_label[36].setVisible(true); 
                makeSound(notes.get('9'), 100);
              }
                count++;
              }
            break;
          }
        }
        }
      }
      keyDown[e.getKeyCode()] = true;
    }
  }

  public void keyReleased(KeyEvent e) {
    //Remove the key from the keyDown hashmap if key is released
    keyDown[e.getKeyCode()] = false;
    //If the key played a note, stop that note
    if (notes.containsKey(e.getKeyChar())){
      stopSound(notes.get(e.getKeyChar()));
      keyPressedMillis1 = System.currentTimeMillis();
      keyPressLength = (int)(System.currentTimeMillis() - keyPressedMillis);        
      record[countnote][1] = keyPressLength;
      System.out.println(record[countnote][1]);          
      countnote++;
      if(notes.get(e.getKeyChar()) == 36){ 
          noteboard[0].setVisible(true);
          whitered[0].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 37){ 
          noteblack[0].setVisible(true);
          blackred[0].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 38){ 
          noteboard[1].setVisible(true);
          whitered[1].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 39){ 
          noteblack[1].setVisible(true);
          blackred[1].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 40){ 
          noteboard[2].setVisible(true);
          whitered[2].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 41){ 
          noteboard[3].setVisible(true);
          whitered[3].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 42){ 
          noteblack[2].setVisible(true);
          blackred[2].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 43){ 
          noteboard[4].setVisible(true);
          whitered[4].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 44){ 
          noteblack[3].setVisible(true);
          blackred[3].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 45){ 
          noteboard[5].setVisible(true);
          whitered[5].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 46){ 
          noteblack[4].setVisible(true);
          blackred[4].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 47){ 
          noteboard[6].setVisible(true);
          whitered[6].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 48){ 
          noteboard[7].setVisible(true);
          whitered[7].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 49){ 
          noteblack[5].setVisible(true);
          blackred[5].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 50){ 
          noteboard[8].setVisible(true);
          whitered[8].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 51){ 
          noteblack[6].setVisible(true);
          blackred[6].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 52){ 
          noteboard[9].setVisible(true);
          whitered[9].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 53){ 
          noteboard[10].setVisible(true);
          whitered[10].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 54){ 
          noteblack[7].setVisible(true);
          blackred[7].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 55){ 
          noteboard[11].setVisible(true);
          whitered[11].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 56){ 
          noteblack[8].setVisible(true);
          blackred[8].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 57){ 
          noteboard[12].setVisible(true);
          whitered[12].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 58){ 
          noteblack[9].setVisible(true);
          blackred[9].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 59){ 
          noteboard[13].setVisible(true);
          whitered[13].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 60){ 
          noteboard[14].setVisible(true);
          whitered[14].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 61){ 
          noteblack[10].setVisible(true);
          blackred[10].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 62){ 
          noteboard[15].setVisible(true);
          whitered[15].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 63){ 
          noteblack[11].setVisible(true);
          blackred[11].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 64){ 
          noteboard[16].setVisible(true);
          whitered[16].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 65){ 
          noteboard[17].setVisible(true);
          whitered[17].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 66){ 
          noteblack[12].setVisible(true);
          blackred[12].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 67){ 
          noteboard[18].setVisible(true);
          whitered[18].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 68){ 
          noteblack[13].setVisible(true);
          blackred[13].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 69){ 
          noteboard[19].setVisible(true);
          whitered[19].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 70){ 
          noteblack[14].setVisible(true);
          blackred[14].setVisible(false);   
        }
        else if(notes.get(e.getKeyChar()) == 71){ 
          noteboard[20].setVisible(true);
          whitered[20].setVisible(false);
        }
        else if(notes.get(e.getKeyChar()) == 72){ 
          noteboard[21].setVisible(true);
          whitered[21].setVisible(false);
        }
      if(Main.mode == 2){
      if(Main.attr == 1){
        switch(Main.state){
          case 1:
            if(notes.get(e.getKeyChar()) == 36){
              mouse_label[0].setVisible(false); 
              noteboard[0].setVisible(true);
              blackred[0].setVisible(false);
              stopSound(notes.get('k'));
              stopSound(notes.get('i'));
              stopSound(notes.get('g'));
            }
            else if(notes.get(e.getKeyChar()) == 37){
              mouse_label[1].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 38){
              mouse_label[2].setVisible(false); 
              stopSound(notes.get('d'));
              stopSound(notes.get('b'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 39){
              mouse_label[3].setVisible(false); 
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 40){
              mouse_label[4].setVisible(false); 
              stopSound(notes.get('k'));
              stopSound(notes.get('i'));
              stopSound(notes.get('g'));
            }
            else if(notes.get(e.getKeyChar()) == 41){
              mouse_label[5].setVisible(false); 
              stopSound(notes.get('f'));
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 42){
              mouse_label[6].setVisible(false); 
              stopSound(notes.get('i'));
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
            else if(notes.get(e.getKeyChar()) == 43){
              mouse_label[7].setVisible(false); 
              stopSound(notes.get('k'));
              stopSound(notes.get('g'));
              stopSound(notes.get('d'));
            }
            else if(notes.get(e.getKeyChar()) == 44){
              mouse_label[8].setVisible(false); 
              stopSound(notes.get('g'));
              stopSound(notes.get('c'));
              stopSound(notes.get('1'));
            }
            else if(notes.get(e.getKeyChar()) == 45){
              mouse_label[9].setVisible(false); 
              stopSound(notes.get('f'));
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 46){
              mouse_label[10].setVisible(false); 
              stopSound(notes.get('h'));
              stopSound(notes.get('d'));
              stopSound(notes.get('a'));
            }
            else if(notes.get(e.getKeyChar()) == 47){
              mouse_label[11].setVisible(false); 
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }        
            else if(notes.get(e.getKeyChar()) == 48){
              mouse_label[12].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 49){
              mouse_label[13].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 50){
              mouse_label[14].setVisible(false); 
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 51){
              mouse_label[15].setVisible(false); 
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 52){
              mouse_label[16].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 53){
              mouse_label[17].setVisible(false); 
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 54){
              mouse_label[18].setVisible(false); 
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 55){
              mouse_label[19].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 56){
              mouse_label[20].setVisible(false); 
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 57){
              mouse_label[21].setVisible(false); 
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 58){
              mouse_label[22].setVisible(false); 
              stopSound(notes.get('5'));
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 59){
              mouse_label[23].setVisible(false); 
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 60){
              mouse_label[24].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 61){
              mouse_label[25].setVisible(false); 
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
            }
            else if(notes.get(e.getKeyChar()) == 62){
              mouse_label[26].setVisible(false); 
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 63){
              mouse_label[27].setVisible(false); 
              stopSound(notes.get('Q'));
              stopSound(notes.get('E'));
              stopSound(notes.get('T'));
            }
            else if(notes.get(e.getKeyChar()) == 64){
              mouse_label[28].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 65){
              mouse_label[29].setVisible(false); 
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 66){
              mouse_label[30].setVisible(false); 
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 67){
              mouse_label[31].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 68){
              mouse_label[32].setVisible(false); 
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 69){
              mouse_label[33].setVisible(false); 
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 70){
              mouse_label[34].setVisible(false); 
              stopSound(notes.get('5'));
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 71){
              mouse_label[35].setVisible(false); 
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 72){
              mouse_label[36].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            break;

          case 2:
          if(notes.get(e.getKeyChar()) == 36){
              mouse_label[0].setVisible(false); 
              stopSound(notes.get('k'));
              stopSound(notes.get('i'));
              stopSound(notes.get('g'));
            }
            else if(notes.get(e.getKeyChar()) == 37){
              mouse_label[1].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 38){
              mouse_label[2].setVisible(false); 
              stopSound(notes.get('i'));
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
            else if(notes.get(e.getKeyChar()) == 39){
              mouse_label[3].setVisible(false); 
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 40){
              mouse_label[4].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 41){
              mouse_label[5].setVisible(false); 
              stopSound(notes.get('f'));
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 42){
              mouse_label[6].setVisible(false); 
              stopSound(notes.get('i'));
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
            else if(notes.get(e.getKeyChar()) == 43){
              mouse_label[7].setVisible(false);
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 44){
              mouse_label[8].setVisible(false);
              stopSound(notes.get('g'));
              stopSound(notes.get('c'));
              stopSound(notes.get('1'));
            }
            else if(notes.get(e.getKeyChar()) == 45){
              mouse_label[9].setVisible(false);
              stopSound(notes.get('i'));
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
            else if(notes.get(e.getKeyChar()) == 46){
              mouse_label[10].setVisible(false);
              stopSound(notes.get('h'));
              stopSound(notes.get('d'));
              stopSound(notes.get('a'));
            }
            else if(notes.get(e.getKeyChar()) == 47){
              mouse_label[11].setVisible(false);
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }        
            else if(notes.get(e.getKeyChar()) == 48){
              mouse_label[12].setVisible(false);
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 49){
              mouse_label[13].setVisible(false);
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 50){
              mouse_label[14].setVisible(false);
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 51){
              mouse_label[15].setVisible(false);
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 52){
              mouse_label[16].setVisible(false);
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 53){
              mouse_label[17].setVisible(false);
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 54){
              mouse_label[18].setVisible(false);
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 55){
              mouse_label[19].setVisible(false);
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 56){
              mouse_label[20].setVisible(false);
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 57){
              mouse_label[21].setVisible(false);
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 58){
              mouse_label[22].setVisible(false);
              stopSound(notes.get('5'));
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 59){
              mouse_label[23].setVisible(false);
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 60){
              mouse_label[24].setVisible(false);
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 61){
              mouse_label[25].setVisible(false);
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
            }
            else if(notes.get(e.getKeyChar()) == 62){
              mouse_label[26].setVisible(false);
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 63){
              mouse_label[27].setVisible(false);
              stopSound(notes.get('Q'));
              stopSound(notes.get('E'));
              stopSound(notes.get('T'));
            }
            else if(notes.get(e.getKeyChar()) == 64){
              mouse_label[28].setVisible(false);
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
            }
            else if(notes.get(e.getKeyChar()) == 65){
              mouse_label[29].setVisible(false);
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 66){
              mouse_label[30].setVisible(false);
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 67){
              mouse_label[31].setVisible(false);
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 68){
              mouse_label[32].setVisible(false); 
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 69){
              mouse_label[33].setVisible(false); 
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 70){
              mouse_label[34].setVisible(false); 
              stopSound(notes.get('5'));
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 71){
              mouse_label[35].setVisible(false); 
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 72){
              mouse_label[36].setVisible(false); 
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            break;
          case 3: //G
            if(notes.get(e.getKeyChar()) == 43){
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 44){
              stopSound(notes.get('g'));
              stopSound(notes.get('c'));
              stopSound(notes.get('1'));
            }
            else if(notes.get(e.getKeyChar()) == 45){
              stopSound(notes.get('i'));
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
            else if(notes.get(e.getKeyChar()) == 46){
              stopSound(notes.get('h'));
              stopSound(notes.get('d'));
              stopSound(notes.get('a'));
            }
            else if(notes.get(e.getKeyChar()) == 47){
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }        
            else if(notes.get(e.getKeyChar()) == 48){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 49){
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 50){
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 51){
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 52){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 53){
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 54){
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 55){
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 56){
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 57){
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 58){
              stopSound(notes.get('5'));
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 59){
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 60){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 61){
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
            }
            else if(notes.get(e.getKeyChar()) == 62){
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 63){
              stopSound(notes.get('Q'));
              stopSound(notes.get('E'));
              stopSound(notes.get('T'));
            }
            else if(notes.get(e.getKeyChar()) == 64){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 65){
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 66){
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 67){
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            break;  
          case 4: //F
            if(notes.get(e.getKeyChar()) == 43){
              stopSound(notes.get('k'));
              stopSound(notes.get('g'));
              stopSound(notes.get('d'));
            }
            else if(notes.get(e.getKeyChar()) == 44){
              stopSound(notes.get('g'));
              stopSound(notes.get('c'));
              stopSound(notes.get('1'));
            }
            else if(notes.get(e.getKeyChar()) == 45){
              stopSound(notes.get('f'));
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 46){
              stopSound(notes.get('a'));
              stopSound(notes.get('4'));
              stopSound(notes.get('7'));
            }
            else if(notes.get(e.getKeyChar()) == 47){
              stopSound(notes.get('d'));
              stopSound(notes.get('1'));
              stopSound(notes.get('4'));
            }        
            else if(notes.get(e.getKeyChar()) == 48){
              stopSound(notes.get('f'));
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 49){
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 50){
              stopSound(notes.get('a'));
              stopSound(notes.get('4'));
              stopSound(notes.get('7'));
            }
            else if(notes.get(e.getKeyChar()) == 51){
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 52){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 53){
              stopSound(notes.get('a'));
              stopSound(notes.get('4'));
              stopSound(notes.get('7'));
            }
            else if(notes.get(e.getKeyChar()) == 54){
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 55){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 56){
              stopSound(notes.get('6'));
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
            else if(notes.get(e.getKeyChar()) == 57){
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 58){
              stopSound(notes.get('a'));
              stopSound(notes.get('4'));
              stopSound(notes.get('7'));
            }
            else if(notes.get(e.getKeyChar()) == 59){
              stopSound(notes.get('9'));
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 60){
              stopSound(notes.get('7'));
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 61){
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
            }
            else if(notes.get(e.getKeyChar()) == 62){
              stopSound(notes.get('a'));
              stopSound(notes.get('4'));
              stopSound(notes.get('7'));
            }
            else if(notes.get(e.getKeyChar()) == 63){
              stopSound(notes.get('Q'));
              stopSound(notes.get('E'));
              stopSound(notes.get('T'));
            }
            else if(notes.get(e.getKeyChar()) == 64){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            else if(notes.get(e.getKeyChar()) == 65){
              stopSound(notes.get('='));
              stopSound(notes.get('S'));
              stopSound(notes.get('F'));
            }
            else if(notes.get(e.getKeyChar()) == 66){
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 67){
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            break;  
        }
      }
      else if(Main.attr == 2){
        switch(Main.state){
          case 1:  //C
            if(Main.count == 2){
              if(notes.get(e.getKeyChar()) == 36){
              mouse_label[0].setVisible(false); 
              stopSound(notes.get('k'));
              }
            else if(notes.get(e.getKeyChar()) == 37){
              mouse_label[1].setVisible(false); 
              stopSound(notes.get('b'));
              }
            else if(notes.get(e.getKeyChar()) == 38){
              mouse_label[2].setVisible(false); 
              stopSound(notes.get('d'));
              }
            else if(notes.get(e.getKeyChar()) == 39){
              mouse_label[3].setVisible(false); 
              stopSound(notes.get('1'));
              }
            else if(notes.get(e.getKeyChar()) == 40){
              mouse_label[4].setVisible(false); 
              stopSound(notes.get('k'));
              }
            else if(notes.get(e.getKeyChar()) == 41){
              mouse_label[5].setVisible(false); 
              stopSound(notes.get('f'));
              }
            else if(notes.get(e.getKeyChar()) == 42){
              mouse_label[6].setVisible(false); 
              stopSound(notes.get('i'));
              }
              else if(notes.get(e.getKeyChar()) == 43){
                mouse_label[7].setVisible(false); 
                stopSound(notes.get('k'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                mouse_label[8].setVisible(false); 
                stopSound(notes.get('g'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                mouse_label[9].setVisible(false); 
                stopSound(notes.get('f'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                mouse_label[10].setVisible(false);
                stopSound(notes.get('h'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                mouse_label[11].setVisible(false);
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                mouse_label[13].setVisible(false);
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                mouse_label[14].setVisible(false);
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                mouse_label[15].setVisible(false);
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                mouse_label[16].setVisible(false);
                stopSound(notes.get('2'));
                }
              else if(notes.get(e.getKeyChar()) == 53){
                mouse_label[17].setVisible(false);
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                mouse_label[18].setVisible(false);
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                mouse_label[19].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                mouse_label[20].setVisible(false);
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                mouse_label[21].setVisible(false);
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                mouse_label[22].setVisible(false);
                stopSound(notes.get('5'));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                mouse_label[24].setVisible(false);
                stopSound(notes.get('9'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                mouse_label[25].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                mouse_label[26].setVisible(false);
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                mouse_label[27].setVisible(false);
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                mouse_label[28].setVisible(false);
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                mouse_label[29].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                mouse_label[30].setVisible(false);
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                mouse_label[31].setVisible(false);
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                mouse_label[32].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 68){
              mouse_label[32].setVisible(false); 
              stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 69){
              mouse_label[33].setVisible(false); 
              stopSound(notes.get('7'));
              }
            else if(notes.get(e.getKeyChar()) == 70){
              mouse_label[34].setVisible(false); 
              stopSound(notes.get('5'));
              }
            else if(notes.get(e.getKeyChar()) == 71){
              mouse_label[35].setVisible(false); 
              stopSound(notes.get('9'));
              }
            else if(notes.get(e.getKeyChar()) == 72){
              mouse_label[36].setVisible(false); 
              stopSound(notes.get('2'));
              }
            
              Main.count++;
            }
            else if(Main.count == 4){
                if(notes.get(e.getKeyChar()) == 36){
              mouse_label[0].setVisible(false); 
              stopSound(notes.get('i'));
              stopSound(notes.get('g'));
            }
            else if(notes.get(e.getKeyChar()) == 37){
              mouse_label[1].setVisible(false); 
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
            }
            else if(notes.get(e.getKeyChar()) == 38){
              mouse_label[2].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('4'));
            }
            else if(notes.get(e.getKeyChar()) == 39){
              mouse_label[3].setVisible(false); 
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 40){
              mouse_label[4].setVisible(false); 
              stopSound(notes.get('i'));
              stopSound(notes.get('g'));
            }
            else if(notes.get(e.getKeyChar()) == 41){
              mouse_label[5].setVisible(false); 
              stopSound(notes.get('b'));
              stopSound(notes.get('2'));
            }
            else if(notes.get(e.getKeyChar()) == 42){
              mouse_label[6].setVisible(false); 
              stopSound(notes.get('e'));
              stopSound(notes.get('b'));
            }
              else if(notes.get(e.getKeyChar()) == 43){
                mouse_label[7].setVisible(false);
                stopSound(notes.get('g'));
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                mouse_label[8].setVisible(false);
                stopSound(notes.get('c'));
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                mouse_label[9].setVisible(false);
                stopSound(notes.get('b'));
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                mouse_label[10].setVisible(false);
                stopSound(notes.get('d'));
                stopSound(notes.get('a'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                mouse_label[11].setVisible(false);
                stopSound(notes.get('1'));
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                mouse_label[13].setVisible(false);
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                mouse_label[14].setVisible(false);
                stopSound(notes.get('1'));
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                mouse_label[15].setVisible(false);
                stopSound(notes.get('5'));
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                mouse_label[16].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
                mouse_label[17].setVisible(false);
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                mouse_label[18].setVisible(false);
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                mouse_label[19].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                mouse_label[20].setVisible(false);
                stopSound(notes.get('0'));
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                mouse_label[21].setVisible(false);
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                mouse_label[22].setVisible(false);
                stopSound(notes.get('9'));
                stopSound(notes.get('='));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                mouse_label[23].setVisible(false);
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                mouse_label[24].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                mouse_label[25].setVisible(false);
                stopSound(notes.get('W'));
                stopSound(notes.get('D'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                mouse_label[26].setVisible(false);
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                mouse_label[27].setVisible(false);
                stopSound(notes.get('E'));
                stopSound(notes.get('T'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                mouse_label[28].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                mouse_label[29].setVisible(false);
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                mouse_label[30].setVisible(false);
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                mouse_label[31].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 68){
              mouse_label[32].setVisible(false); 
              stopSound(notes.get('0'));
              stopSound(notes.get('Q'));
            }
              else if(notes.get(e.getKeyChar()) == 69){
              mouse_label[33].setVisible(false); 
              stopSound(notes.get('-'));
              stopSound(notes.get('A'));
            }
            else if(notes.get(e.getKeyChar()) == 70){
              mouse_label[34].setVisible(false); 
              stopSound(notes.get('9'));
              stopSound(notes.get('='));
            }
            else if(notes.get(e.getKeyChar()) == 71){
              mouse_label[35].setVisible(false); 
              stopSound(notes.get('Q'));
              stopSound(notes.get('S'));
            }
            else if(notes.get(e.getKeyChar()) == 72){
              mouse_label[36].setVisible(false); 
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
                count = 1;
            }
          break;
          case 2:  //D
            if(Main.count == 2){
              if(notes.get(e.getKeyChar()) == 43){
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('g'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('i'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                stopSound(notes.get('h'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                mouse_label[13].setVisible(false);
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                mouse_label[14].setVisible(false);
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                mouse_label[15].setVisible(false);
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                mouse_label[16].setVisible(false);
                stopSound(notes.get('b'));
                }
              else if(notes.get(e.getKeyChar()) == 53){
                mouse_label[17].setVisible(false);
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                stopSound(notes.get('5'));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                stopSound(notes.get('9'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                stopSound(notes.get('9'));
              }
              Main.count++;
            }
            else if(Main.count == 4){
              if(notes.get(e.getKeyChar()) == 43){
                stopSound(notes.get('1'));
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('c'));
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('e'));
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                stopSound(notes.get('d'));
                stopSound(notes.get('a'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                stopSound(notes.get('1'));
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                mouse_label[13].setVisible(false);
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                mouse_label[14].setVisible(false);
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('5'));
                mouse_label[15].setVisible(false);
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                mouse_label[16].setVisible(false);
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
                mouse_label[17].setVisible(false);
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('0'));
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                stopSound(notes.get('9'));
                stopSound(notes.get('='));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                stopSound(notes.get('W'));
                stopSound(notes.get('D'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('E'));
                stopSound(notes.get('T'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('W'));
                stopSound(notes.get('D'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              }
                count = 1;
            }
          break;
        }
      }
      else if(Main.attr == 3){
        switch(Main.state){
           case 1:  //C
            if(Main.count == 2){
              if(notes.get(e.getKeyChar()) == 43){
                stopSound(notes.get('k'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('g'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('f'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                stopSound(notes.get('h'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                mouse_label[13].setVisible(false);
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                mouse_label[12].setVisible(false);
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('2'));
                }
              else if(notes.get(e.getKeyChar()) == 53){
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                stopSound(notes.get('5'));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                stopSound(notes.get('9'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                stopSound(notes.get('7'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                stopSound(notes.get('2'));
              }
              Main.count++;
            }
            else if(Main.count == 4){
              if(notes.get(e.getKeyChar()) == 43){
                stopSound(notes.get('g'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('c'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('3'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('5'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('0'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                stopSound(notes.get('Q'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                stopSound(notes.get('W'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('E'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                stopSound(notes.get('6'));
              }
              count++;
            }
            else if(Main.count == 6){
              if(notes.get(e.getKeyChar()) == 43){
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 46){
                stopSound(notes.get('a'));
              }
              else if(notes.get(e.getKeyChar()) == 47){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 48){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 58){
                stopSound(notes.get('='));
              }
              else if(notes.get(e.getKeyChar()) == 59){
                stopSound(notes.get('S'));
              } 
              else if(notes.get(e.getKeyChar()) == 60){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 61){
                stopSound(notes.get('D'));
              }
              else if(notes.get(e.getKeyChar()) == 62){
                stopSound(notes.get('S'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('T'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 65){
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 66){
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 67){
                stopSound(notes.get('9'));
              }
              count = 1;
            }
            break;
          }
        }
      }
    }
  }

  
  public String getKeyDown() {
    //System.out.println("start play");
    //this function returns the keys that are currently pressed
    String keys = "";
    for (int i = 0; i < keyDown.length; i++) {
      //System.out.print("start going loop");
      //System.out.println(i);
      if (keyDown[i]) {
        keys += KeyEvent.getKeyText(i);
        //System.out.print(i);
        //System.out.print("  ");
        //System.out.print(keys);
      }
    }
    System.out.println(keys);
    return keys;
  }
  public void makeSound(int noteNumber, int velocity) {
    //Plays a note with specified note number
    this.midChannel[midiChannel].noteOn(noteNumber, velocity);
  }

  public void stopSound(int noteNumber) {
    //Stops specified note number
    this.midChannel[midiChannel].noteOff(noteNumber);
  }

  public void setKeys(HashMap<Character, Integer> map) {
    // Maps notes to keys
    //map.put('A', 48);
    //map.put('f', 29);
    map.put('k', 24);
    map.put('j', 25);
    map.put('i', 26);
    map.put('h', 27);
    map.put('g', 28);
    map.put('f', 29);
    map.put('e', 30);
    map.put('d', 31);
    map.put('c', 32);
    map.put('b', 33);
    map.put('a', 34);
    map.put('1', 35);
    map.put('2', 36);
    map.put('3', 37);
    map.put('4', 38);
    map.put('5', 39);
    map.put('6', 40);
    map.put('7', 41);
    map.put('8', 42);
    map.put('9', 43);
    map.put('0', 44);
    map.put('-', 45);
    map.put('=', 46);   
    map.put('Q', 47); 
    map.put('A', 48);
    map.put('W', 49);
    map.put('S', 50);
    map.put('E', 51);
    map.put('D', 52);
    map.put('F', 53);
    map.put('T', 54);
    map.put('G', 55);   
    map.put('Y', 56);
    map.put('H', 57);
    map.put('U', 58);
    map.put('J', 59);
    map.put('K', 60);
    map.put('O', 61);
    map.put('L', 62);
    map.put('P', 63);
    map.put(';', 64);
    map.put('.', 65);
    map.put('[', 66);
    map.put(']', 67);
    map.put('Z', 68);
    map.put('X', 69);
    map.put('C', 70);
    map.put('V', 71);
    map.put('B', 72);
  }

  @Override
  public void paintComponent(Graphics g) {
    System.out.print("start going loop");
    //Clear the window
    //g.clearRect(0, 0, getWidth(), getHeight());
    super.paintComponent(g);
    //boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    /*if(!caps){
      g.drawString("Turn Capslock on!", 50, getHeight() / 3);
    }
    */

    //Draw keys that are held down
    //g.drawString("The key pressed is " + getKeyDown(), 50, getHeight() / 2);
    
    //g.drawString("Keys:"+notes.keySet().toString(), 50, getHeight() * 2 / 3);
    //repaint();
    Polygon p = new Polygon();
      p.addPoint((int) (200), (int) (200));
      p.addPoint((int) (200), (int) (350));
      p.addPoint((int) (400), (int) (350));
      p.addPoint((int) (400), (int) (250));
      p.addPoint((int) (350), (int) (250));
      p.addPoint((int) (350), (int) (200));
      g.setColor(Color.green);
      g.fillPolygon(p);
      g.drawPolygon(p);
  }
	
  public static void main(String[] args) {
    System.out.print("start");
    JFrame f = new JFrame("KeyTone");
    //f.setSize(20, 20);
    f.getContentPane().add(new Main());
    JLabel label = new JLabel("Keyboard For Music Accompaniment",JLabel.LEFT);
    label.setVerticalAlignment(JLabel.TOP);
    label.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
    label.setBounds(10,10,1000,400); //position length width
    label.setForeground(new Color(120, 90, 40));
    label.setBackground(new Color(100, 20, 70));
    formode = new JLabel("MODE: Normal",JLabel.LEFT);
    formode.setVerticalAlignment(JLabel.TOP);
    formode.setFont(new Font("Times New Roman", Font.BOLD, 25));
    formode.setBounds(10,70,500,400);
    fortone = new JLabel("TONE: Major C",JLabel.LEFT);
    fortone.setVerticalAlignment(JLabel.TOP);
    fortone.setFont(new Font("Times New Roman", Font.BOLD, 25));  
    fortone.setBounds(10,120,500,400);
    forrhythm = new JLabel("RHYTHM: Chord",JLabel.LEFT);
    forrhythm.setVerticalAlignment(JLabel.TOP);
    forrhythm.setFont(new Font("Times New Roman", Font.BOLD, 25));
    forrhythm.setBounds(10,170,500,400);
    //label.setPreferredSize(new Dimension(250, 100));
    ImageIcon icon1 = new ImageIcon("background.png"); //插入圖片
    JLabel background = new JLabel(icon1); //新增label放圖片
    //background.setLayout(null); 
    //background.setBounds(0,0,1000,1000); //position length width
    background.setLayout(new BorderLayout()); 
    //background.setBounds(0, 0, icon1.getIconWidth(), icon1.getIconHeight()); //設定label的位置、大小，label大小為圖片的大小
    //JLabel lbb = new JLabel(new ImageIcon("Test.png")); first
    //lb.setLayout(new BorderLayout()); 
    //lbb.setLocation(250, 200);
    //lbb.setSize(600,500);
    //lb.setBackground(Color.red);
    //f.add(lbb);
    lb = new JLabel(new ImageIcon("pianodisplay.png"));
    //lb = new JLabel(new ImageIcon("board.png"));
    lb.setLayout(new BorderLayout()); 
    lb.setLocation(0, 0);
    lb.setSize(1000,470); 
    //lb.setBackground(Color.red);
    //f.add(lb);
    JLabel lb1 = new JLabel(new ImageIcon("board1.png"));
    lb1.setLayout(new BorderLayout()); 
    lb1.setLocation(197, 188);
    lb1.setSize(150,430);
    //f.add(lb1);
    //JLabel lb2 = new JLabel(new ImageIcon("board.png"));
    //lb.setLayout(new BorderLayout()); 
    //lb2.setLocation(550, 200);
    //lb2.setSize(90,270);
    //lb.setBackground(Color.red);
    //f.add(lb2);
    //JLabel lb3 = new JLabel(new ImageIcon("board.png"));
    //lb.setLayout(new BorderLayout()); 
    //lb3.setLocation(700, 200);
    //lb3.setSize(90,290);
    //lb.setBackground(Color.red);
    //f.add(lb3);
    
    //lb.setPreferredSize(new Dimension(1800, 1500));
    JMenu mode = new JMenu("MODE"); 
  JMenuItem normal = new JMenuItem("Normal"); //新增三個menu中的類別的名
  JMenuItem accompaniment = new JMenuItem("Accompaniment");
    normal.addActionListener(new MyBtnListener());
    accompaniment.addActionListener(new MyBtnListener());
    mode.add(normal); //將三個item加到diner中
  mode.add(accompaniment);
    JMenu diner = new JMenu("TONE"); 
  JMenuItem toneC = new JMenuItem("Major C"); //新增三個menu中的類別的名
  JMenuItem toneD = new JMenuItem("Major D");
    JMenuItem toneF = new JMenuItem("Major F");
  JMenuItem toneG = new JMenuItem("Major G");
    toneC.addActionListener(new MyBtnListener());
    toneD.addActionListener(new MyBtnListener());
    toneF.addActionListener(new MyBtnListener());
    toneG.addActionListener(new MyBtnListener());
   diner.add(toneC); //將三個item加到diner中
  diner.add(toneD);
    diner.add(toneF);
  diner.add(toneG);
    JMenu diner1 = new JMenu("RHYTHM"); 
  JMenuItem Chord = new JMenuItem("Chord"); //新增三個menu中的類別的名
  JMenuItem Jump = new JMenuItem("Jump");
  JMenuItem Smooth = new JMenuItem("Smooth");
    Chord.addActionListener(new MyBtnListener());
    Jump.addActionListener(new MyBtnListener());
    Smooth.addActionListener(new MyBtnListener());
   diner1.add(Chord); //將三個item加到diner中
  diner1.add(Jump);
  diner1.add(Smooth);
    JMenuBar bar = new JMenuBar(); //要求新的MenuBar
    bar.add(mode);
  bar.add(diner); //加到diner中
    bar.add(diner1); 
    //f.addMouseListener(new MyMouseEvent()); //將滑鼠偵測加到frame中
  //f.setLayout(null); //設定frame的layout
  
  //ImageIcon icon1 = new ImageIcon("background.png"); //插入圖片
  //JLabel background = new JLabel(icon1); //新增label放圖片
    ImageIcon image2 = new ImageIcon("board1.png");
    //image.setImage(image.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
    for(int i=0; i<15; i++) { //把老鼠圖片預備好
    JLabel note2 = new JLabel(image2); 
    noteblack[i] = note2;//新增label1放圖片
    if(i % 5 == 0)
      noteblack[i].setLocation(197 + 455 * (i / 5), 188);
    else if(i % 5 == 1)
      noteblack[i].setLocation(262 + 455 * (i / 5), 188);
    else if(i % 5 == 2)
      noteblack[i].setLocation(392 + 455 * (i / 5), 188);
    else if(i % 5 == 3)
      noteblack[i].setLocation(457 + 455 * (i / 5), 188);
    else
      noteblack[i].setLocation(522 + 455 * (i / 5), 188);
    noteblack[i].setSize(150,430);
    noteblack[i].setVisible(true);
    f.add(noteblack[i]);
    }
    ImageIcon image4 = new ImageIcon("red1.png");
    //image.setImage(image.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
    for(int i=0; i<15; i++) { //把老鼠圖片預備好
    JLabel note5 = new JLabel(image4); 
    blackred[i] = note5;//新增label1放圖片
    if(i % 5 == 0)
      blackred[i].setLocation(197 + 455 * (i / 5), 188);
    else if(i % 5 == 1)
      blackred[i].setLocation(262 + 455 * (i / 5), 188);
    else if(i % 5 == 2)
      blackred[i].setLocation(392 + 455 * (i / 5), 188);
    else if(i % 5 == 3)
      blackred[i].setLocation(457 + 455 * (i / 5), 188);
    else
      blackred[i].setLocation(522 + 455 * (i / 5), 188);
    blackred[i].setSize(150,430);
    blackred[i].setVisible(false);
    f.add(blackred[i]);
    }
    
    ImageIcon image1 = new ImageIcon("board.png");
    //image.setImage(image.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
    for(int i=0; i<22; i++) { //把老鼠圖片預備好
    JLabel note1 = new JLabel(image1); 
    noteboard[i] = note1;//新增label1放圖片
    noteboard[i].setLocation(165 + 65 * i, 230);
    noteboard[i].setSize(150,430);
    noteboard[i].setVisible(true);
    f.add(noteboard[i]);
    }
    ImageIcon image3 = new ImageIcon("red.png");
    //image.setImage(image.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
    for(int i=0; i<22; i++) { //把老鼠圖片預備好
    JLabel note2 = new JLabel(image3); 
    whitered[i] = note2;//新增label1放圖片
    whitered[i].setLocation(165 + 65 * i, 230);
    whitered[i].setSize(150,430);
    whitered[i].setVisible(false);
    f.add(whitered[i]);
    }
  position[0][0] = 340;
  position[0][1] = 230;
  position[1][0] = 365;
  position[1][1] = 230;
  position[2][0] = 390;
  position[2][1] = 230;
  position[3][0] = 415;
  position[3][1] = 230;
  position[4][0] = 440;
  position[4][1] = 230; //1set
  position[5][0] = 475;
  position[5][1] = 230;
  position[6][0] = 500;
  position[6][1] = 230;
  position[7][0] = 525;
  position[7][1] = 230;
  position[8][0] = 550;
  position[8][1] = 230;
  position[9][0] = 570;
  position[9][1] = 230;
  position[10][0] = 595;
  position[10][1] = 230;
  position[11][0] = 620;
  position[11][1] = 230; //2set
  position[12][0] = 655;
  position[12][1] = 230;
  position[13][0] = 680;
  position[13][1] = 230;
  position[14][0] = 705;
  position[14][1] = 230;
  position[15][0] = 730;
  position[15][1] = 230;
  position[16][0] = 755;
  position[16][1] = 230; //1set
    position[17][0] = 790;
  position[17][1] = 230;
    position[18][0] = 820;
  position[18][1] = 230;
    position[19][0] = 845;
  position[19][1] = 230;
    position[20][0] = 865;
  position[20][1] = 230;
    position[21][0] = 890;
  position[21][1] = 230;
    position[22][0] = 915;
  position[22][1] = 230;
    position[23][0] = 940; //2set
  position[23][1] = 230;
    position[24][0] = 975;
  position[24][1] = 230;
    position[25][0] = 1000;
  position[25][1] = 230;
    position[26][0] = 1025;
  position[26][1] = 230;
    position[27][0] = 1045;
  position[27][1] = 230;
    position[28][0] = 1075; //1set
  position[28][1] = 230;
    position[29][0] = 1105;
  position[29][1] = 230;
    position[30][0] = 1135;
  position[30][1] = 230;
    position[31][0] = 1160;
  position[31][1] = 230;
    position[32][0] = 1185;
  position[32][1] = 230;
    position[33][0] = 1205;
  position[33][1] = 230;
    position[34][0] = 1230;
  position[34][1] = 230;
    position[35][0] = 1255;
  position[35][1] = 230;
    position[36][0] = 1290;
  position[36][1] = 230;
    //ImageIcon icon1 = new ImageIcon("background.png"); //插入圖片
  //JLabel background = new JLabel(icon1); //新增label放圖片
    ImageIcon image = new ImageIcon("note.png");
    image.setImage(image.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
    for(int i=0; i<37; i++) { //把老鼠圖片預備好
      JLabel note = new JLabel(image); 
    mouse_label[i] = note;//新增label1放圖片
    mouse_label[i].setLocation(position[i][0]-44, position[i][1]-147);
    mouse_label[i].setSize(300,300);
    mouse_label[i].setVisible(false);
    f.add(mouse_label[i]);
    }
    JButton b = new JButton("Stop Record");  
    b.setBounds(50,100,95,30);  
    b.addActionListener(new MyBtnListener());
    f.add(b);
    JButton b1 = new JButton("Play");  
    b1.setBounds(150,200,95,30);  
    b1.addActionListener(new MyBtnListener());
    f.add(b1);
    f.setJMenuBar(bar);  
    f.add(label);
    f.add(formode);
    f.add(fortone);
    f.add(forrhythm);
    //background.add(lb); //piano
    f.add(background);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //f.setSize(20, 20);
    f.pack();
    f.setVisible(true);
  }
}
