package com.ebibli.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe des Exceptions Fonctionnelles
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunctionalException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;


    // ==================== Constructeurs ====================
    /**
     * Constructeur.
     *
     * @param pMessage -
     */
    public FunctionalException(String pMessage) {
        super(pMessage);
    }

    /**
     * Constructeur.
     *
     * @param pCause -
     */
    public FunctionalException(Throwable pCause) {
        super(pCause);
    }

    /**
     * Constructeur.
     *
     * @param pMessage -
     * @param pCause -
     */
    public FunctionalException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
