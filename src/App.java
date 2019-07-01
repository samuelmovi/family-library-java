import controller.Controller;


public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		print("[#] Starting app...");
		Controller c = new Controller();
		c.initController();
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

}
