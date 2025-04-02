import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    //crear usuarios
    public boolean createUser(User user) {
        String query = "INSERT INTO usuarios (Nombre, Apellido, NombreUsuario, Contraseña, Telefono, Email) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
                       
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellido());
            statement.setString(3, user.getNombreUsuario());
            statement.setString(4, user.getContraseña());
            statement.setString(5, user.getTelefono());
            statement.setString(6, user.getEmail());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error creando usuario: " + e.getMessage());
            return false;
        }
    }
    
    //revisa si el nombre de usuario existe
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE NombreUsuario = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, username);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error verificando nombre: " + e.getMessage());
        }
        
        return false;
    }
    
    //verificar usuarios
    public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM usuarios WHERE NombreUsuario = ? AND Contraseña = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    String nombreUsuario = resultSet.getString("NombreUsuario");
                    String contraseña = resultSet.getString("Contraseña");
                    String telefono = resultSet.getString("Telefono");
                    String email = resultSet.getString("Email");
                    
                    return new User(id, nombre, apellido, nombreUsuario, contraseña, telefono, email);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error verificando usuario: " + e.getMessage());
        }
        
        return null;
    }
    
    //listar usuarios
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String nombre = resultSet.getString("Nombre");
                String apellido = resultSet.getString("Apellido");
                String nombreUsuario = resultSet.getString("NombreUsuario");
                String contraseña = resultSet.getString("Contraseña");
                String telefono = resultSet.getString("Telefono");
                String email = resultSet.getString("Email");
                
                User user = new User(id, nombre, apellido, nombreUsuario, contraseña, telefono, email);
                users.add(user);
            }
            
        } catch (SQLException e) {
            System.out.println("Error listando todos los usuarios: " + e.getMessage());
        }
        
        return users;
    }
    
    //actualiza los usuarios
    public boolean updateUser(User user) {
        String query = "UPDATE usuarios SET Nombre = ?, Apellido = ?, NombreUsuario = ?, " +
                       "Contraseña = ?, Telefono = ?, Email = ? WHERE Id = ?";
                       
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellido());
            statement.setString(3, user.getNombreUsuario());
            statement.setString(4, user.getContraseña());
            statement.setString(5, user.getTelefono());
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getId());
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }
    
    //borrar usuario
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM usuarios WHERE Id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setInt(1, userId);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error borrando usuario: " + e.getMessage());
            return false;
        }
    }
}