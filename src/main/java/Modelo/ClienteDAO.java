package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;

import Controlador.ConexionSQL;

/**
 * @author Lenovo
 *
 */
public class ClienteDAO {
	
	ConexionSQL conn = new ConexionSQL();
	Connection  conect = conn.hacerConexion();
	PreparedStatement re = null;
	ResultSet resultado = null;
	
	/**
	 * metodo crear cliente
	 * @param cliente
	 * @return bool
	 */
	public boolean CrearCliente(ClienteDTO cliente)  {
		boolean bool = false;
		
		String ingresar = "insert into clientes values (?,?,?,?,?)";
		try {
			re = conect.prepareStatement(ingresar);
			re.setString(1, cliente.getCedula_cliente());
			re.setString(4, cliente.getNombre_cliente());
			re.setString(3, cliente.getEmail_cliente());
		
			re.setString(2, cliente.getDireccion_cliente());
			re.setString(5, cliente.getTelefono_cliente());
		//carga de información en data base//
		bool = re.executeUpdate()>0;
		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al insertar el usuario"+e);
		}
		
		return bool;
	}
	
	
	public ClienteDTO consultarCliente(String cedula) {
		ClienteDTO cliente = null;
		
		String buscar = "select * from clientes where cedula_cliente=?";
			try {
				re= conect.prepareStatement(buscar);
				re.setString(1, cedula);
				resultado = re.executeQuery();
				while(resultado.next()) {
					cliente = new ClienteDTO(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5));
				}
				
			} catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Error al consultar el usuario"+e);
				
			}
			return cliente;
		
	}
	
	/**
	 * 
	 * @param cliente
	 * @return resultado
	 */
	
	public boolean actualizarUsuario(ClienteDTO cliente) {
		
		boolean resultado = false;
		
		String actualizar = "update clientes set direccion_cliente=?, email_cliente=?, nombre_cliente=?, telefo_cliente=? where cedula_clientes=?";
		
		try {
			re = conect.prepareStatement(actualizar);
			re.setString(1,cliente.getDireccion_cliente() );
			re.setString(2, cliente.getEmail_cliente());
			re.setString(3, cliente.getNombre_cliente());
			re.setString(4, cliente.getTelefono_cliente());
			re.setString(5, cliente.getCedula_cliente());
			resultado= re.executeUpdate()> 0;
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Error al actualizar el cliente"+ e);
		}
		
		
		return resultado;
	}
	
	public boolean eliminarUsuario(String cedula) {
		boolean resultado = false;
		
		String eliminar = "delete from clientes where cedula_clientes=? ";
		
		try {
			re=conect.prepareStatement(eliminar);
			re.setString(1, cedula);
			resultado = re.executeUpdate()>0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar el cliente"+e);
		}
		
		
		return resultado;
	}

}
