import java.io.FileInputStream;
import java.util.Scanner;
public class ReadFileTest {
public static void main(String[] args) {
try{
Scanner scanner = new Scanner(new FileInputStream("output.txt"));
while(scanner.hasNextLine()){
String aline = scanner.nextLine();
System.out.println(aline);
}
}catch(Exception e){
e.printStackTrace();
}
}
}