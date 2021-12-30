/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-12
*/

package org.jyotisa.app;

import org.swisseph.app.SweRuntimeException;

/**
 * @author  Yura Krymlov
 * @version 1.1, 2019-12
 */
public class KundaliRuntimeException extends SweRuntimeException {
    private static final long serialVersionUID = 7492777826497156552L;

    /**
     * Constructs a new Jyotisa runtime exception with the specified detail message.
     */
    public KundaliRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new Jyotisa runtime exception with the specified detail message and cause.
     */
    public KundaliRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new Jyotisa runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     */
    public KundaliRuntimeException(Throwable cause) {
        super(cause);
    }
}
