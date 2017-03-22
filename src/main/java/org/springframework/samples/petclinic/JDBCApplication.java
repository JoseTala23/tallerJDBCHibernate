package org.springframework.samples.petclinic;


import java.sql.*;
import java.util.Scanner;
import java.util.Date;

import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;


public class JDBCApplication {
	static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("-------- Test de conexión con MySQL ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "root", "Everis2017");
			if (connection != null)
				System.out.println("Conexión establecida");

			//inserta(connection);
			
			crearOwnersAndMascota(connection);
			
			listadoPets(connection);
		
			boorarMascota(connection);
			
			//BusquedaPorNombreApellidos(connection);
			
			//String sql = "update owners set city = 'Sevilla' where first_name = 'Jose Manuel'";

			//statement.executeUpdate(sql);

			//listadoClientes(connection);
			
			

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			try {
				if (statement != null)
					connection.close();
			} catch (SQLException se) {

			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	private static void boorarMascota(Connection connection) {
		
		
		String delete = "Delete from pets where name='dama'";
		
		
		
	}

	private static void crearOwnersAndMascota(Connection connection) throws SQLException {
		System.out.println("Introduce tu nombre");
		String nombre = teclado.nextLine();
		System.out.println("Introduce tu apellido");
		String apellido = teclado.nextLine();
		System.out.println("Introduce tu direccion");
		String direccion = teclado.nextLine();
		System.out.println("Introduce tu ciudad");
		String ciudad = teclado.nextLine();
		System.out.println("Intrpduce tu telefono");
		String telf = teclado.nextLine();
		
		Owner o = new Owner(direccion,ciudad,telf);
		o.setFirstName(nombre);
		o.setLastName(apellido);
		
		Date d = new Date();
		PetType petType = new PetType();
		Pet pet = new Pet(d,petType,o);
		
		pet.setName(solicitarNombrePet());

		String consultaInsert = "INSERT INTO owners (first_name,last_name,address,city,telephone)" +
				"VALUES (?,?,?,?,?);";
		PreparedStatement pState = connection.prepareStatement(consultaInsert);
		
		pState.setString(1, o.getFirstName());
		pState.setString(2, o.getLastName());
		pState.setString(3, o.getAddress());
		pState.setString(4, o.getCity());
		pState.setString(5, o.getTelephone());
		
		pState.executeUpdate();
		
		String consulPet = "INSERT INTO pets (name,birth_date,type_id,owner_id)" +
				"VALUES (?,?,?,?)";
		pState = connection.prepareStatement(consulPet);
		
		pState.setString(1, pet.getName());
		pState.setString(2,String.valueOf(java.time.LocalDate.now()));
		pState.setInt(3, 3);
		pState.setInt(4, 16);
					
		pState.executeUpdate();
	}

	private static String solicitarNombrePet() {
		
		String nombre;
		
		System.out.println("Introduce tu nombre de mascota");
		nombre = teclado.nextLine();
		
		return nombre;
	}

	private static void listadoPets(Connection connection) {


		Statement statement;
		try {
			statement = connection.createStatement();
		
		String sql = "Select * from pets";
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			// Obtener Campos
			int id = rs.getInt("id");
			String nombre = rs.getString("name");
			String apellidos = rs.getString("birth_date");
			String direccion = rs.getString("type_id");
			String ciudad = rs.getString("owner_id");
			

			// Trato resultado
			System.out.print("Id: " + id + ", Nombre: " + nombre + ", Cumpleaños: " + apellidos + ", Tipo: "
					+ direccion + ", Owners: " + ciudad);
			System.out.println("\n");
		}
		rs.close();
		statement.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void inserta(Connection connection) throws SQLException {
		System.out.println("Introduce tu nombre");
		String nombre = teclado.nextLine();
		System.out.println("Introduce tu apellido");
		String apellido = teclado.nextLine();
		System.out.println("Introduce tu direccion");
		String direccion = teclado.nextLine();
		System.out.println("Introduce tu ciudad");
		String ciudad = teclado.nextLine();
		System.out.println("Intrpduce tu telefono");
		String telf = teclado.nextLine();
		
		String consultaInsert = "INSERT INTO owners (first_name,last_name,address,city,telephone)" +
				"VALUES (?,?,?,?,?);";
		PreparedStatement pState = connection.prepareStatement(consultaInsert);
		
		pState.setString(1, nombre);
		pState.setString(2, apellido);
		pState.setString(3, direccion);
		pState.setString(4, ciudad);
		pState.setString(5, telf);
		pState.executeUpdate();
	}

	private static void BusquedaPorNombreApellidos(Connection connection) throws SQLException {
		String valorBuscar;
		
		
		System.out.println("Introduce un nombre o un apellido");
		valorBuscar = teclado.nextLine();
		
		String sql = "Select * from owners where first_name like ? or last_name like ?";
		PreparedStatement pState = connection.prepareStatement(sql);
		
		pState.setString(1, valorBuscar);
		pState.setString(2, valorBuscar);
		ResultSet resu = pState.executeQuery();
		
			while (resu.next()) {
				// Obtener Campos
				int id = resu.getInt("id");
				String nombre = resu.getString("first_name");
				String apellidos = resu.getString("last_name");
				String direccion = resu.getString("address");
				String ciudad = resu.getString("city");
				String telf = resu.getString("telephone");

				// Trato resultado
				System.out.print("Id: " + id + ", Nombre: " + nombre + ", Apellidos: " + apellidos + ", direccion: "
						+ direccion + ", Ciudad: " + ciudad + ", Telefono: " + telf);
				System.out.println("\n");
			}
			
		
		resu.close();
	}

	private static void listadoClientes(Connection connection) throws SQLException {
		Statement statement;
		statement = connection.createStatement();
		String sql = "Select * from owners";
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			// Obtener Campos
			int id = rs.getInt("id");
			String nombre = rs.getString("first_name");
			String apellidos = rs.getString("last_name");
			String direccion = rs.getString("address");
			String ciudad = rs.getString("city");
			String telf = rs.getString("telephone");

			// Trato resultado
			System.out.print("Id: " + id + ", Nombre: " + nombre + ", Apellidos: " + apellidos + ", direccion: "
					+ direccion + ", Ciudad: " + ciudad + ", Telefono: " + telf);
			System.out.println("\n");
		}
		rs.close();
		statement.close();

		// Statement st = connection.createStatement();
		// String sql2 = "INSERT INTO owners
		// (first_name,last_name,address,city,telephone)" +
		// "VALUES ('Jose Manuel', 'Talaveron Perez', 'C/Albacete 35',
		// 'Arahal','666666666')";
		// st.executeUpdate(sql2);
		// st.close();

	}

}
