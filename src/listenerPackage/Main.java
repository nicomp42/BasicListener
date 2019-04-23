/***********************************************
 * main() for Listener class                   *
 *                                             *
 * Bill Nicholson                              *
 * nicholdw@ucmail.uc.edu                      *
 ***********************************************/
package listenerPackage;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Listener listener = new Listener(100, "Hoosier");
		listener.start();
	}

}
