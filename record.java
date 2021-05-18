import javax.sound.midi.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

class Main extends JPanel implements KeyListener {
  //keep track of which key is pressed
  private boolean[] keyDown = new boolean[250];
  //hold which key plays what note
  private HashMap<Character, Integer> notes = new HashMap<Character, Integer>(); //save key-value

  //initiate synthesizer variables
  Synthesizer syn;
  MidiChannel[] midChannel;
  Instrument[] instrument;
  long keyPressedMillis;
  long keyPressLength;
  int instNum = 88; //change this to change instruments
  int midiChannel = 7;

  public Main() {
    this.setPreferredSize(new Dimension(700, 700));
    addKeyListener(this);
    setKeys(notes);
    long timeToSleep = 250;
    TimeUnit time = TimeUnit.MILLISECONDS;
    try {
      syn = MidiSystem.getSynthesizer();
      syn.open();
      midChannel = syn.getChannels();
      midChannel[midiChannel].programChange(instNum);
      instrument = syn.getAvailableInstruments();
      syn.loadInstrument(instrument[50]);
      makeSound(notes.get('A'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('A'));
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      makeSound(notes.get('G'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('G')); 
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      makeSound(notes.get('A'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('A')); 
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      makeSound(notes.get('S'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('S'));
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      makeSound(notes.get('D'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('D')); 
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      makeSound(notes.get('A'), 550);
      try{
        time.sleep(timeToSleep);      
      }
      catch (InterruptedException e) {
        System.out.println("Interrupted while Sleeping");
      }
      stopSound(notes.get('A')); 
       
    } catch (MidiUnavailableException ex) {
      ex.printStackTrace();
    }
  }

  public void addNotify() {
    super.addNotify();
    requestFocus();  //將滑鼠游標設定在某個物件
  }

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    //Adds key to the keyDown hashmap if key is pressed
    if (keyDown[e.getKeyCode()]) {  //getKeyCode:取得鍵盤按鍵的整數值 鍵盤上每一個按鈕都有對應碼(Code),可用來查知用戶按了什麼鍵， 如[Shift]鍵code為16
      return;
    } else {
      //play a note if the key has an assigned note
      if (notes.containsKey(e.getKeyChar())) //測試是否存在於此映射要測試的鍵
        makeSound(notes.get(e.getKeyChar()), 550);
      keyDown[e.getKeyCode()] = true;
      //int codigo = e.getKeyCode();
      keyPressedMillis = System.currentTimeMillis();           
      //System.out.println(keyPressedMillis);          
    }
  }

  public void keyReleased(KeyEvent e) {
    //Remove the key from the keyDown hashmap if key is released
    keyDown[e.getKeyCode()] = false;
    //If the key played a note, stop that note
    if (notes.containsKey(e.getKeyChar()))
      stopSound(notes.get(e.getKeyChar()));
    keyPressLength = System.currentTimeMillis() - keyPressedMillis;        
    //System.out.println(System.currentTimeMillis());                 
    System.out.println(keyPressLength);          
  }

  public void paintComponent(Graphics g) {
    //Clear the window
    g.clearRect(0, 0, getWidth(), getHeight());
    Font f2 = new Font("Helvetica", Font.BOLD,20);
    g.setFont(f2);
    boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK); //scroll lock
    if(!caps){
      g.drawString("Remember to Turn Capslock on!", 80, getHeight() / 3);
    }
    
    //Draw keys that are held down
    g.drawString("The key pressed is " + getKeyDown(), 80, getHeight() / 2);
    
    g.drawString("Keys:"+notes.keySet().toString(), 80, getHeight() * 2 / 3);
    repaint();
  }

  public String getKeyDown() {
    //this function returns the keys that are currently pressed
    String keys = "";
    for (int i = 0; i < keyDown.length; i++) {
      if (keyDown[i]) {
        keys += KeyEvent.getKeyText(i);
      }
    }

    return keys;
  }
  public void makeSound(int noteNumber, int velocity) { //noteNumber:MIDI音符編號 從0到127（60=中C） velocity:按下按鍵的速度

    //Plays a note with specified note number
    this.midChannel[midiChannel].noteOn(noteNumber, velocity); 
  }

  public void stopSound(int noteNumber) {
    //Stops specified note number
    this.midChannel[midiChannel].noteOff(noteNumber); 
  }

  public void setKeys(HashMap<Character, Integer> map) {
    // Maps notes to keys
    map.put('A', 48);
    map.put('W', 49);
    map.put('S', 50);
    map.put('E', 51);
    map.put('D', 52);
    map.put('R', 53);
    map.put('F', 54);
    map.put('G', 55);   //middle C
    map.put('Y', 56);
    map.put('H', 57);
    map.put('U', 58);
    map.put('J', 59);
    map.put('K', 60);
    map.put('O', 61);
    map.put('L', 62);
    map.put('P', 63);
    map.put(';', 64);
    map.put('[', 65);

  }

  public static void main(String[] args) {
    JFrame f = new JFrame("Keyboard Piano");
    f.getContentPane().add(new Main()); //獲得JFrame的內容面板,再對其加入元件
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //使用者單擊視窗的關閉按鈕時程式執行的操作
    f.pack(); //將視窗中的各個元件，自動依版面配置作適當的排列
    f.setVisible(true);
  }

}
