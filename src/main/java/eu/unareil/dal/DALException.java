package eu.unareil.dal;

public class DALException extends Exception {
    public DALException() {
    }

    public DALException(String message) {
        super("Erreur DAL: " + message);
    }

    public DALException(String message, Throwable cause) {
        super("Erreur DAL: " + message, cause);
    }
}
