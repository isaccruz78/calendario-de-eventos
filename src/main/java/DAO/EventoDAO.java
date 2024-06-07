package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Evento;

public class EventoDAO {

	private static final String drive = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://localhost:5432/Calendario_Eventos";
	private static final String usuario = "postgres";
	private static final String senha = "postgres";

	public Connection getEventoDAO() throws SQLException {
		try {
			Class.forName(drive);
			Connection connection = DriverManager.getConnection(url, usuario, senha);
			return connection;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public List<Evento> ConsultarTodosEventos() {
		List<Evento> eventos = new ArrayList<>();

		String comando_sql = "SELECT * FROM Eventos";

		try (Connection com = getEventoDAO(); PreparedStatement ps = com.prepareStatement(comando_sql)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				String local = rs.getString("local");
				String data = rs.getString("data");
				String horario = rs.getString("horario");

				eventos.add(new Evento(id, nome, descricao, local, data, horario));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return eventos;
	}

	public void PublicarEvento(Evento evento) {
		String comando_sql = "INSERT INTO Eventos (nome, descricao, local, data, horario) VALUES (?, ?, ?, ?, ?)";

		try (Connection com = getEventoDAO(); PreparedStatement ps = com.prepareStatement(comando_sql)) {
			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getDescricao());
			ps.setString(3, evento.getLocal());
			ps.setString(4, evento.getData());
			ps.setString(5, evento.getHorario());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AtualizarEvento(Evento evento) {
		String comando_sql = "UPDATE Eventos SET nome = ?, descricao = ?, local = ?, data = ?, horario = ? WHERE id = ?";

		try (Connection com = getEventoDAO(); PreparedStatement ps = com.prepareStatement(comando_sql)) {
			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getDescricao());
			ps.setString(3, evento.getLocal());
			ps.setString(4, evento.getData());
			ps.setString(5, evento.getHorario());
			ps.setInt(6, evento.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ExcluirEvento(int id) {
		String comando_sql = "DELETE FROM Eventos WHERE id = ?";

		try (Connection com = getEventoDAO(); PreparedStatement ps = com.prepareStatement(comando_sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}