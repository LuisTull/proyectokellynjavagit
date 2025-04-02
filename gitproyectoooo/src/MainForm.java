import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    private JTable tblUsuarios;
    private DefaultTableModel tableModel;
    private JButton btnNuevo;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCerrarSesion;
    private UserDAO userDAO;
    private User currentUser;

    public MainForm(User user) {
        this.currentUser = user;
        userDAO = new UserDAO();
        initComponents();
        loadUserData();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        //ma comentario para mi persona, gracias (me duele el caco) -------------
        //panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        //titulo
        JPanel titlePanel = new JPanel(new BorderLayout());

        //titulo central
        JLabel lblTitle = new JLabel("Clientes Registrados", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        //informacion del usuario (nombre del usuario conectado)
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblUserInfo = new JLabel("Usuario: " + currentUser.getNombre() + " " + currentUser.getApellido());
        lblUserInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        userInfoPanel.add(lblUserInfo);
        titlePanel.add(userInfoPanel, BorderLayout.EAST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        //tablas
        JPanel tablePanel = new JPanel(new BorderLayout());

        //definir tablas
        String[] columns = {"ID", "Nombre", "Apellido", "Teléfono", "Correo Electrónico", "Usuario"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        tblUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblUsuarios);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        //botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnNuevo = new JButton("NUEVO");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");
        btnCerrarSesion = new JButton("CERRAR SESIÓN");

        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCerrarSesion);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        //listenesr
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserEditForm(null);
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedUser();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedUser();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void loadUserData() {
        //limpiar datos de la tabla
        tableModel.setRowCount(0);

        //listar todos los usuarios
        List<User> users = userDAO.getAllUsers();

        //añadir usuarios
        for (User user : users) {
            Object[] rowData = {
                    user.getId(),
                    user.getNombre(),
                    user.getApellido(),
                    user.getTelefono(),
                    user.getEmail(),
                    user.getNombreUsuario()
            };
            tableModel.addRow(rowData);
        }
    }

    private void updateSelectedUser() {
        int selectedRow = tblUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para actualizar.",
                    "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        //busca usuario
        List<User> users = userDAO.getAllUsers();
        User selectedUser = null;

        for (User user : users) {
            if (user.getId() == userId) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser != null) {
            openUserEditForm(selectedUser);
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = tblUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para eliminar.",
                    "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 5);

        //pa no borra el usuario mio y que no se me eplote por octagesima cuarta vez
        if (username.equals(currentUser.getNombreUsuario())) {
            JOptionPane.showMessageDialog(this, "No puede eliminar su propio usuario mientras está conectado.",
                    "Operación No Permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //confirmar borrado (usuario)
        int option = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar el usuario seleccionado?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito.",
                        "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openUserEditForm(User user) {
        UserEditForm userEditForm = new UserEditForm(this, user);
        userEditForm.setVisible(true);
    }

    private void logout() {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        this.dispose();
    }

    //llama el formulario de editar despues de guardartodo (lo puse pegao porque me taba dando un error que no entendi, gracias)
    public void refreshData() {
        loadUserData();
    }
}