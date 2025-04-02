import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserEditForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JPasswordField txtConfirmarContraseña;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private UserDAO userDAO;
    private MainForm parentForm;
    private User user;
    private boolean isNewUser;
    
    public UserEditForm(MainForm parentForm, User user) {
        this.parentForm = parentForm;
        this.user = user;
        this.isNewUser = (user == null);
        userDAO = new UserDAO();
        initComponents();
        if (!isNewUser) {
            loadUserData();
        }
    }
    
    private void initComponents() {
        setTitle(isNewUser ? "Nuevo Usuario" : "Actualizar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        //ete e el ultimo de mi comentario mio mio personale
        //titulo
        JLabel lblTitle = new JLabel(isNewUser ? "NUEVO USUARIO" : "ACTUALIZAR USUARIO");
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
        
        //apelliod
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
        
        //telefonmo
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
        
        btnGuardar = new JButton("Guardar");
        buttonPanel.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);
        

        add(panel);
        
        //listeners
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void loadUserData() {
        if (user != null) {
            txtNombre.setText(user.getNombre());
            txtApellido.setText(user.getApellido());
            txtUsuario.setText(user.getNombreUsuario());
            txtContraseña.setText(user.getContraseña());
            txtConfirmarContraseña.setText(user.getContraseña());
            txtTelefono.setText(user.getTelefono());
            txtEmail.setText(user.getEmail());
            
            //para que no se pueda editar el nombre de usuario
            txtUsuario.setEditable(false);
        }
    }
    
    private void saveUser() {
        //toma los valores de las casillas
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword());
        String confirmarContraseña = new String(txtConfirmarContraseña.getPassword());
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        
        //valida quetodo este lleno (decir t0d0 e el problema, aunque no causa errores no me gusta como se ve)
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el apellido.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Debe confirmar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número de teléfono.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un correo electrónico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //valida las contraseñas
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //verifica el nombre de usuario
        if (isNewUser && userDAO.usernameExists(usuario)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe. Por favor, elija otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success;
        
        if (isNewUser) {
            //se crea el usuario (solo si es uno nuevo
            User newUser = new User(nombre, apellido, usuario, contraseña, telefono, email);
            success = userDAO.createUser(newUser);
        } else {
            //se actualiza el usuario
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setContraseña(contraseña);
            user.setTelefono(telefono);
            user.setEmail(email);
            success = userDAO.updateUser(user);
        }
        //mensajes de verificacion
        if (success) {
            JOptionPane.showMessageDialog(this, 
                isNewUser ? "Usuario creado con éxito." : "Usuario actualizado con éxito.", 
                "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            parentForm.refreshData(); //refresca el form principal
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                isNewUser ? "Error al crear el usuario." : "Error al actualizar el usuario.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}