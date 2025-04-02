import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //coloca la apariencia default del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //crea el login utilizando el FormFactory
            FormFactory formFactory = FormFactory.getInstance();
            LoginForm loginForm = formFactory.createLoginForm();
            loginForm.setVisible(true);
        });
    }
}