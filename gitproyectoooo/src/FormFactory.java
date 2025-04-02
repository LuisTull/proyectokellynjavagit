//patron de diseño fabrica (Factory)
public class FormFactory {
    //y la inmstancia singleton
    private static FormFactory instance;

    private FormFactory() {
    }
    
    public static FormFactory getInstance() {
        if (instance == null) {
            instance = new FormFactory();
        }
        return instance;
    }
    
    //crea los formularios
    public LoginForm createLoginForm() {
        return new LoginForm();
    }
    public RegisterForm createRegisterForm(LoginForm loginForm) {
        return new RegisterForm(loginForm);
    }
    public MainForm createMainForm(User user) {
        return new MainForm(user);
    }
    public UserEditForm createUserEditForm(MainForm mainForm, User user) {
        return new UserEditForm(mainForm, user);
    }
}