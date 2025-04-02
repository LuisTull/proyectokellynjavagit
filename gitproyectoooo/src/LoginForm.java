import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnEntrar;
    private JButton btnRegistrarse;
    private UserDAO userDAO;
    private FormFactory formFactory;

    public LoginForm() {
        userDAO = new UserDAO();
        formFactory = FormFactory.getInstance();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //comentarios para no perderme --------------------------------------------------

        //titulo
        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        //label nombre de usuario
        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblUsuario, gbc);

        //campo nombre de usuario
        txtUsuario = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtUsuario, gbc);

        //label contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(lblContraseña, gbc);

        //campo contraseña
        txtContraseña = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtContraseña, gbc);

        //boton login
        btnEntrar = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnEntrar, gbc);

        //boton registrarse
        btnRegistrarse = new JButton("Registrarse");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnRegistrarse, gbc);

        add(panel);

        //actualiza los listeners para utilizar el factory
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterForm();
            }
        });
    }

    private void login() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtContraseña.getPassword());

        //valida campos vacios
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse.",
                    "Error de Login", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //verificar usuario
        User user = userDAO.authenticateUser(username, password);
        if (user != null) {
            //abre el formulario principal usando factory
            MainForm mainForm = formFactory.createMainForm(user);
            mainForm.setVisible(true);
            this.dispose(); // Close login form
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos.",
                    "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterForm() {
        //crea formulario de registro usando factory
        RegisterForm registerForm = formFactory.createRegisterForm(this);
        registerForm.setVisible(true);
        this.setVisible(false);
    }
}