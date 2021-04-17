import java.io.*;
import javax.sound.midi.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*; //事件
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

class Main extends JPanel implements KeyListener {
  static int state = 1, attr = 1,count = 1;
  //Kept track of which key is pressed
  private boolean[] keyDown = new boolean[250];
  //Holds which key plays what note
  private HashMap<Character, Integer> notes = new HashMap<Character, Integer>();

  //initiate synthesizer variables
  Synthesizer syn;
  MidiChannel[] midChannel;
  Instrument[] instrument;
  int instNum = 88; //change this to change instruments
  int midiChannel = 7;

  public Main() {
    this.setPreferredSize(new Dimension(800, 600));
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
			
			String command=e.getActionCommand();
			if(command.equals("Major C")) { 
				System.out.println("Major C");
				Main.state = 1;
			}
			else if(command.equals("Major D")) {
				System.out.println("Major D");
        Main.state = 2;
			}
			else if(command.equals("Major G")) {
				System.out.println("Major G");
        Main.state = 3;
  		}
      else if(command.equals("Major F")) {
				System.out.println("Major F");
        Main.state = 4;
			}
			else if(command.equals("Chord")) {
				System.out.println("Chord");
        Main.attr = 1;
  		}
      else if(command.equals("Jump")) {
				System.out.println("Jump");
        Main.attr = 2;
  		}
      else if(command.equals("Smooth")) {
				System.out.println("Smooth");
        Main.attr = 3;
  		}
		}
		
	}
  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    //Adds key to the keyDown hashmap if key is pressed
    if (keyDown[e.getKeyCode()]) {
      return;
    } else {
      //play a note if the key has an assigned note
      if(notes.containsKey(e.getKeyChar())){
try {
    PrintWriter writer = new PrintWriter(new FileOutputStream("d:\\output.txt"));
    writer.println(e.getKeyChar());
    writer.flush();
    writer.close();
    } 
catch (FileNotFoundException e) {
    e.printStackTrace();
}
        makeSound(notes.get(e.getKeyChar()), 550);
        writer.println(e.getKeyChar());
        System.out.println(notes.get(e.getKeyChar()));
        if(Main.attr == 1){  //chord
          switch(Main.state){
            case 1:
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
                System.out.println("go"); //la do me
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
                makeSound(notes.get('2'), 100); //do mi so
                makeSound(notes.get('6'), 100);
                makeSound(notes.get('9'), 100);
              }
              break;
            case 2:  //D
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
                System.out.println("go"); //re fa# la
                makeSound(notes.get('4'), 100);
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 51){ //re#
                System.out.println("go"); //si re# fa#
                makeSound(notes.get('1'), 100);
                makeSound(notes.get('5'), 100);
                makeSound(notes.get('8'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 52){ //mi
                makeSound(notes.get('b'), 100); //la do# mi
                makeSound(notes.get('3'), 100);
                makeSound(notes.get('6'), 100);
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
                makeSound(notes.get('4'), 100); //re fa# la
                makeSound(notes.get('8'), 100);
                makeSound(notes.get('-'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 63){ //re#
                makeSound(notes.get('Q'), 100); //si re# fa#
                makeSound(notes.get('E'), 100);
                makeSound(notes.get('T'), 100);
              }
              else if(notes.get(e.getKeyChar()) == 64){ //mi
                makeSound(notes.get('-'), 100); //la do# mi
                makeSound(notes.get('W'), 100);
                makeSound(notes.get('D'), 100);
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
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('k'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('f'), 100);
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
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('7'), 100);
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
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('2'), 100);
                }
                Main.count++;
              }
              else if(Main.count == 3){
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('g'), 100);
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('c'), 100);
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('b'), 100);
                  makeSound(notes.get('2'), 100);
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
                  makeSound(notes.get('1'), 100);
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('5'), 100);
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
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
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('0'), 100);
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('-'), 100);
                  makeSound(notes.get('A'), 100);
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
                  makeSound(notes.get('Q'), 100);
                  makeSound(notes.get('S'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('E'), 100);
                  makeSound(notes.get('T'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('6'), 100);
                  makeSound(notes.get('9'), 100);
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
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('k'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('f'), 100);
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
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('7'), 100);
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
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('7'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('2'), 100);
                }
                Main.count++;
              }
              else if(Main.count == 3){
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('g'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('c'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('b'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  makeSound(notes.get('3'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('5'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('0'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  makeSound(notes.get('Q'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  makeSound(notes.get('W'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('E'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('6'), 100);
                }
                count++;
              }
              else if(Main.count == 5){
                if(notes.get(e.getKeyChar()) == 43){
                  makeSound(notes.get('d'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 44){
                  makeSound(notes.get('1'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 45){
                  makeSound(notes.get('2'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 46){
                  makeSound(notes.get('a'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 47){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 48){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 49){
                  makeSound(notes.get('6'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 50){
                  makeSound(notes.get('4'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 51){
                  makeSound(notes.get('8'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 52){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 53){
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 54){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 55){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 56){
                  makeSound(notes.get('Q'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 57){
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 58){
                  makeSound(notes.get('='), 100);
                }
                else if(notes.get(e.getKeyChar()) == 59){
                  makeSound(notes.get('S'), 100);
                } 
                else if(notes.get(e.getKeyChar()) == 60){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 61){
                  makeSound(notes.get('D'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 62){
                  makeSound(notes.get('S'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 63){
                  makeSound(notes.get('T'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 64){
                  makeSound(notes.get('9'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 65){
                  makeSound(notes.get('A'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 66){
                  makeSound(notes.get('-'), 100);
                }
                else if(notes.get(e.getKeyChar()) == 67){
                  makeSound(notes.get('9'), 100);
                }
                count++;
              }
            break;
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
      if(Main.attr == 1){
        switch(Main.state){
          case 1:
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
              stopSound(notes.get('2'));
              stopSound(notes.get('6'));
              stopSound(notes.get('9'));
            }
            break;

          case 2:
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
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 51){
              stopSound(notes.get('1'));
              stopSound(notes.get('5'));
              stopSound(notes.get('8'));
            }
            else if(notes.get(e.getKeyChar()) == 52){
              stopSound(notes.get('b'));
              stopSound(notes.get('3'));
              stopSound(notes.get('6'));
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
              stopSound(notes.get('4'));
              stopSound(notes.get('8'));
              stopSound(notes.get('-'));
            }
            else if(notes.get(e.getKeyChar()) == 63){
              stopSound(notes.get('Q'));
              stopSound(notes.get('E'));
              stopSound(notes.get('T'));
            }
            else if(notes.get(e.getKeyChar()) == 64){
              stopSound(notes.get('-'));
              stopSound(notes.get('W'));
              stopSound(notes.get('D'));
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
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
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
                stopSound(notes.get('d'));
              }
              else if(notes.get(e.getKeyChar()) == 44){
                stopSound(notes.get('c'));
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 45){
                stopSound(notes.get('b'));
                stopSound(notes.get('2'));
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
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                stopSound(notes.get('1'));
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('5'));
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
              }
              else if(notes.get(e.getKeyChar()) == 54){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 55){
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 56){
                stopSound(notes.get('0'));
                stopSound(notes.get('Q'));
              }
              else if(notes.get(e.getKeyChar()) == 57){
                stopSound(notes.get('-'));
                stopSound(notes.get('A'));
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
                stopSound(notes.get('Q'));
                stopSound(notes.get('S'));
              }
              else if(notes.get(e.getKeyChar()) == 63){
                stopSound(notes.get('E'));
                stopSound(notes.get('T'));
              }
              else if(notes.get(e.getKeyChar()) == 64){
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
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
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                stopSound(notes.get('4'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('1'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('b'));
                }
              else if(notes.get(e.getKeyChar()) == 53){
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
                stopSound(notes.get('6'));
                stopSound(notes.get('9'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
                stopSound(notes.get('8'));
                stopSound(notes.get('-'));
              }
              else if(notes.get(e.getKeyChar()) == 51){
                stopSound(notes.get('5'));
                stopSound(notes.get('8'));
              }
              else if(notes.get(e.getKeyChar()) == 52){
                stopSound(notes.get('3'));
                stopSound(notes.get('6'));
              }
              else if(notes.get(e.getKeyChar()) == 53){
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
                stopSound(notes.get('2'));
              }
              else if(notes.get(e.getKeyChar()) == 49){
                stopSound(notes.get('b'));
              }
              else if(notes.get(e.getKeyChar()) == 50){
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

  public void paintComponent(Graphics g) {
    //Clear the window
    g.clearRect(0, 0, getWidth(), getHeight());
    
    boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    /*if(!caps){
      g.drawString("Turn Capslock on!", 50, getHeight() / 3);
    }
    */

    //Draw keys that are held down
    g.drawString("The key pressed is " + getKeyDown(), 50, getHeight() / 2);
    
    g.drawString("Keys:"+notes.keySet().toString(), 50, getHeight() * 2 / 3);
    repaint();
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



  }

	
	
  public static void main(String[] args) {
    System.out.print("start");
    JFrame f = new JFrame("KeyTone");
    f.getContentPane().add(new Main());
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
    JMenu diner1 = new JMenu("Rhythm"); 
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
		bar.add(diner); //加到diner中
    bar.add(diner1); 
 	  //f.addMouseListener(new MyMouseEvent()); //將滑鼠偵測加到frame中
		//f.setLayout(null); //設定frame的layout
		f.setJMenuBar(bar);  
  
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.pack();
    f.setVisible(true);
  }
}
