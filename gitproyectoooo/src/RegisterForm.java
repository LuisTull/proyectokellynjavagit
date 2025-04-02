import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JPasswordField txtConfirmarContraseña;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private UserDAO userDAO;
    private LoginForm loginForm;
    
    public RegisterForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        userDAO = new UserDAO();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        //comentario pa mi aqui tambien porque no soy loco pa sabe que e cada cosa to el tiempo, aunque eto tan facile :D
        //titulo
        JLabel lblTitle = new JLabel("REGISTRO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);
        
        //nombre
        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblNombre, gbc);
        
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNombre, gbc);
        
        //apellido
        JLabel lblApellido = new JLabel("Apellido:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(lblApellido, gbc);
        
        txtApellido = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtApellido, gbc);
        
        //nombre de usuario
        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtUsuario, gbc);
        
        //contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblContraseña, gbc);
        
        txtContraseña = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(txtContraseña, gbc);
        
        //confirmar contraseña
        JLabel lblConfirmarContraseña = new JLabel("Confirmar Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblConfirmarContraseña, gbc);
        
        txtConfirmarContraseña = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(txtConfirmarContraseña, gbc);
        
        //teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(lblTelefono, gbc);
        
        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(txtTelefono, gbc);
        
        //email
        JLabel lblEmail = new JLabel("Correo Electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(txtEmail, gbc);
        
        //panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnRegistrar = new JButton("Registrar");
        buttonPanel.add(btnRegistrar);
        
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        add(panel);
        
        //listeners
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
    }
    
    private void registerUser() {
        //toma los valores de las casillas
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword());
        String confirmarContraseña = new String(txtConfirmarContraseña.getPassword());
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        
        //valida que esten llenas las casillas
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su apellido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (confirmarContraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe confirmar su contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su número de teléfono.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su correo electrónico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //valida que sea la misma contraseña
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //verifica el nombre de usuario
        if (userDAO.usernameExists(usuario)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe. Por favor, elija otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //se crea el usuario
        User newUser = new User(nombre, apellido, usuario, contraseña, telefono, email);
        
        //guarda los usuarios en la base de datos (gracias señor)
        if (userDAO.createUser(newUser)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cancel(); //y otra vez para el login
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //el nombre lo dice, pero, cancelar la operacion
    private void cancel() {
        loginForm.setVisible(true);
        this.dispose();
    }
}