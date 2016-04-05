/***********************************************
 * main() for Listener class                   *
 *                                             *
 * Bill Nicholson                              *
 ***********************************************/
package listenerPackage;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//for (int i = 0; i < 100; i++) {
		//	((Listener)(new Listener(100+i,"Hoosier"))).start();
		//}
		Listener listener = new Listener(100, "Hoosier");
		listener.start();
	}

}
