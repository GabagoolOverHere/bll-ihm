package eu.unareil.dal.jdbc;

import eu.unareil.bo.AuteurCartePostale;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteurCartePostaleJDBC implements DAO<AuteurCartePostale> {

    private static final String SQL_INSERT = "INSERT INTO auteur_cartePostale (refAuteur, refCartePostale) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE auteur_cartePostale SET refAuteur=?, refCartePostale=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM auteur_cartePostale WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM auteur_cartePostale";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM auteur_cartePostale WHERE id=?";

    @Override
    public long insert(AuteurCartePostale data) throws DALException {
        PreparedStatement pstmt = null;
        Connection cnx = JdbcTools.getConnection();
        long id = 0;
        try {
            pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, data.getRefAuteur());
            pstmt.setLong(2, data.getRefCartePostale());

            int nbRow = pstmt.executeUpdate();
            if (nbRow == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    data.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new DALException("erreur du insert - data=" + data, e.getCause());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du insert au niveau du close- data=" + data, e.getCause());
            }
        }

        return id;
    }

    @Override
    public void delete(AuteurCartePostale data) throws DALException {
        PreparedStatement pstmt = null;

        long id = data.getId();
        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_DELETE);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("erreur du delete - data=" + data, e.getCause());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du delete au niveau du close- data=" + data, e.getCause());
            }
        }
    }

    @Override
    public void update(AuteurCartePostale data) throws DALException {
        PreparedStatement pstmt = null;

        long id = data.getId();
        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_UPDATE);
            pstmt.setLong(1, data.getRefAuteur());
            pstmt.setLong(2, data.getRefCartePostale());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("erreur du update - data=" + data, e.getCause());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du update au niveau du close- data=" + data, e.getCause());
            }
        }
    }

    @Override
    public AuteurCartePostale selectById(long id) throws DALException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        AuteurCartePostale el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                el = new AuteurCartePostale(rs.getLong(1), rs.getInt(2), rs.getInt(3));
            }

        } catch (SQLException e) {
            throw new DALException("erreur du select by id - id=" + id, e.getCause());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du select by id au niveau du close- id=" + id, e.getCause());
            }
        }
        return el;
    }

    @Override
    public List<AuteurCartePostale> selectAll() throws DALException {
        Statement stmt = null;
        ResultSet rs = null;
        List<AuteurCartePostale> lesElements = new ArrayList<>();
        AuteurCartePostale el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                el = new AuteurCartePostale(rs.getLong(1), rs.getInt(2), rs.getInt(3));
                lesElements.add(el);
            }

        } catch (SQLException e) {
            throw new DALException("erreur du select all", e.getCause());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du select all au niveau du close- ", e.getCause());
            }
        }
        return lesElements;
    }
}
