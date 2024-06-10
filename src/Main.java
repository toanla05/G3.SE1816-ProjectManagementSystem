public class Main {
	public static void main (String[] args) {
		User user = new User();
		ProjectManagement manager = new ProjectManagement(user);	

		manager.createProject();
	}
}
