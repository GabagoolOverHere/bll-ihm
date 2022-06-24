package eu.unareil.dal.jdbc;

import eu.unareil.bo.Auteur;
import eu.unareil.bo.CartePostale;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteursJDBC implements DAO<Auteur> {
    private static final String SQL_INSERT = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE auteur SET nom=? prenom=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM auteur WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM auteur";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM auteur WHERE id=?";

    private static final String SQL_SELECT_ALL_POSTCARDS = """
            SELECT
                p.refProd,
                p.marque,
                p.libelle,
                p.qteStock,
                p.prixUnitaire
            FROM
                produit p
            JOIN auteur_cartePostale ac ON
                p.refProd = ac.refCartePostale
            JOIN auteur a ON
                a.id = ac.refAuteur
            WHERE a.id=?
        """;

    @Override
    public long insert(Auteur data) throws DALException {
        PreparedStatement pstmt = null;
        Connection cnx = JdbcTools.getConnection();
        long id = 0;
        try {
            pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, data.getNom());
            pstmt.setString(2, data.getPrenom());

            int nbRow = pstmt.executeUpdate();
            if (nbRow == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    data.setId(rs.getLong(1));
                    id = rs.getLong(1);
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
    public void delete(Auteur data) throws DALException {
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
    public void update(Auteur data) throws DALException {
        PreparedStatement pstmt = null;

        long id = data.getId();
        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, data.getNom());
            pstmt.setString(2, data.getPrenom());
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
    public Auteur selectById(long id) throws DALException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        Auteur el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                el = new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3));
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
    public List<Auteur> selectAll() throws DALException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Auteur> lesElements = new ArrayList<>();
        Auteur el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                el = new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3), getCartePostales(rs.getLong(1)));
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

    private List<CartePostale> getCartePostales(long id) throws DALException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        Auteur el = null;
        List<CartePostale> list = new ArrayList<>();

        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_SELECT_ALL_POSTCARDS);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                list.add(new CartePostale(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getLong(4),
                    rs.getFloat(5)
                ));
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
        return list;
    }
}
