package client.signin;

public class NwbClientSignInTest {

	public static void main(String args[]) {

		NwbClientSignIn signIn = new NwbClientSignIn();
		signIn.setVisible(true);
		while (signIn.isVisible() == true)
			;
		NwbClientConnect frame = new NwbClientConnect();
		frame.setVisible(true);

	}
}
