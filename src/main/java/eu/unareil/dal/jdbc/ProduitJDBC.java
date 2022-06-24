package eu.unareil.dal.jdbc;

import eu.unareil.bo.*;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class ProduitJDBC implements DAO<Produit> {
    private static final String SQL_INSERT = """
         INSERT
            INTO
            produit (
                marque,
                libelle,
                qteStock,
                prixUnitaire,
                type,
                dateLimiteConso,
                poids,
                parfum,
                temperatureConservation,
                couleur,
                typeMine,
                typeCartePostale
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    private static final String SQL_UPDATE = """
            UPDATE
                produit
            SET
                marque =?,
                libelle =?,
                qteStock =?,
                prixUnitaire =?,
                type =?,
                dateLimiteConso =?,
                poids =?,
                parfum =?,
                temperatureConservation =?,
                couleur =?,
                typeMine =?,
                typeCartePostale =?
            WHERE
                refProd =?;
        """;
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM produit";
    private static final String SQL_SELECT_ALL_AUTHORS = """
            SELECT
                *
            FROM
                auteur a
            JOIN auteur_cartePostale acp on
                a.id = acp.refAuteur
            JOIN produit p on
                p.refProd = acp.refCartePostale
            WHERE
                p.refProd =?;
        """;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit WHERE refProd=?";

    @Override
    public long insert(Produit data) throws DALException {
        AuteursJDBC auteursJDBC = new AuteursJDBC();
        AuteurCartePostaleJDBC auteurCartePostaleJDBC = new AuteurCartePostaleJDBC();
        PreparedStatement pstmt = null;
        Connection cnx = JdbcTools.getConnection();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long id = 0;
        try {
            pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, data.getMarque());
            pstmt.setString(2, data.getLibelle());
            pstmt.setLong(3, data.getQteStock());
            pstmt.setDouble(4, data.getPrixUnitaire());
            pstmt.setNull(5, 0);
            pstmt.setNull(6, 0);
            pstmt.setNull(7, 0);
            pstmt.setNull(8, 0);
            pstmt.setNull(9, 0);
            pstmt.setNull(10, 0);
            pstmt.setNull(11, 0);
            pstmt.setNull(12, 0);

            if (data instanceof Pain) {
                pstmt.setString(5, "Pain");
                pstmt.setString(6, simpleDateFormat.format(Date.from(Instant.now().plus(2, ChronoUnit.DAYS))));
                pstmt.setInt(7, ((Pain) data).getPoids());
            } else if (data instanceof Glace) {
                pstmt.setString(5, "Glace");
                pstmt.setDate(6, Date.valueOf(((Glace) data).getDateLimiteConso()));
                pstmt.setString(8, ((Glace) data).getParfum());
                pstmt.setInt(9, ((Glace) data).getTemperatureConservation());
            } else if (data instanceof Stylo) {
                pstmt.setString(5, "Stylo");
                pstmt.setString(10, ((Stylo) data).getCouleur());
                pstmt.setString(11, ((Stylo) data).getTypeMine());
            } else if (data instanceof CartePostale) {
                pstmt.setString(5, "CartePostale");
            }
            int nbRow = pstmt.executeUpdate();
            if (nbRow == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    data.setRefProd(rs.getLong(1));
                    id = rs.getLong(1);
                    if (data instanceof CartePostale) {
                        for (Auteur a : ((CartePostale) data).getLesAuteurs()) {
                            if (auteursJDBC.selectById(a.getId()) == null) {
                                long auteurId = auteursJDBC.insert(a);
                                auteurCartePostaleJDBC.insert(new AuteurCartePostale(auteurId, rs.getLong(1)));
                            } else {
                                Auteur auteur = auteursJDBC.selectById(a.getId());
                                auteurCartePostaleJDBC.update(new AuteurCartePostale(auteur.getId(), rs.getLong(1)));
                            }
                        }
                    }
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
    public void delete(Produit data) throws DALException {
        PreparedStatement pstmt = null;

        long id = data.getRefProd();
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
    public void update(Produit data) throws DALException {
        PreparedStatement pstmt = null;
        AuteursJDBC auteursJDBC = new AuteursJDBC();
        AuteurCartePostaleJDBC auteurCartePostaleJDBC = new AuteurCartePostaleJDBC();

        long id = data.getRefProd();
        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_UPDATE);
            pstmt.setString(1, data.getMarque());
            pstmt.setString(2, data.getLibelle());
            pstmt.setLong(3, data.getQteStock());
            pstmt.setDouble(4, data.getPrixUnitaire());
            pstmt.setNull(5, 0);
            pstmt.setNull(6, 0);
            pstmt.setNull(7, 0);
            pstmt.setNull(8, 0);
            pstmt.setNull(9, 0);
            pstmt.setNull(10, 0);
            pstmt.setNull(11, 0);
            pstmt.setNull(12, 0);
            pstmt.setLong(13, id);

            if (data instanceof Pain) {
                pstmt.setString(5, "Pain");
                pstmt.setInt(7, ((Pain) data).getPoids());
            } else if (data instanceof Glace) {
                pstmt.setString(5, "Glace");
                pstmt.setString(8, ((Glace) data).getParfum());
                pstmt.setInt(9, ((Glace) data).getTemperatureConservation());
            } else if (data instanceof Stylo) {
                pstmt.setString(5, "Stylo");
                pstmt.setString(10, ((Stylo) data).getCouleur());
                pstmt.setString(11, ((Stylo) data).getTypeMine());
            } else if (data instanceof CartePostale) {
                pstmt.setString(5, "CartePostale");
            }
            pstmt.executeUpdate();
            if (data instanceof CartePostale) {
                for (Auteur a : ((CartePostale) data).getLesAuteurs()) {
                    if (auteursJDBC.selectById(a.getId()) == null) {
                        long auteurId = auteursJDBC.insert(a);
                        auteurCartePostaleJDBC.insert(new AuteurCartePostale(auteurId, id));
                    } else {
                        Auteur auteur = auteursJDBC.selectById(a.getId());
                        auteurCartePostaleJDBC.update(new AuteurCartePostale(auteur.getId(), id));
                    }
                }
            }
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
    public Produit selectById(long id) throws DALException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        Produit el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                switch (rs.getString(6)) {
                    case "Glace" -> el = new Glace(
                        rs.getLong(1),
                        rs.getDate(7).toLocalDate(),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        rs.getString(9),
                        rs.getInt(10)
                    );
                    case "Stylo" -> el = new Stylo(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        rs.getString(11),
                        rs.getString(12)
                    );
                    case "Pain" -> el = new Pain(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(8),
                        rs.getInt(5),
                        rs.getFloat(4)
                    );
                    case "Carte Postale" -> el = new CartePostale(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        getAuteurs(id),
                        TypeCartePostale.Paysage
                    );
                }
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
    public List<Produit> selectAll() throws DALException {
        Statement stmt = null;
        ResultSet rs;
        List<Produit> lesElements = new ArrayList<>();
        Produit el = null;

        Connection cnx = JdbcTools.getConnection();
        try {
            stmt = cnx.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {
                long id = rs.getLong(1);
                switch (rs.getString(6)) {
                    case "Glace" -> el = new Glace(
                        rs.getLong(1),
                        rs.getDate(7).toLocalDate(),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        rs.getString(9),
                        rs.getInt(10)
                    );
                    case "Stylo" -> el = new Stylo(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        rs.getString(11),
                        rs.getString(12)
                    );
                    case "Pain" -> el = new Pain(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(8),
                        rs.getInt(5),
                        rs.getFloat(4)
                    );
                    case "Carte Postale" -> el = new CartePostale(
                        rs.getLong(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(5),
                        rs.getFloat(4),
                        getAuteurs(id),
                        TypeCartePostale.Paysage
                    );
                }
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

    private List<Auteur> getAuteurs(long id) throws DALException {
        PreparedStatement pstmt = null;

        Connection cnx = JdbcTools.getConnection();
        ResultSet rs;
        List<Auteur> auteurs = new ArrayList<>();
        Auteur el;

        try {
            pstmt = cnx.prepareStatement(SQL_SELECT_ALL_AUTHORS);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                el = new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3));
                auteurs.add(el);
            }

        } catch (SQLException e) {
            throw new DALException("erreur du select all", e.getCause());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DALException("erreur du select all au niveau du close- ", e.getCause());
            }
        }
        return auteurs;
    }
}
