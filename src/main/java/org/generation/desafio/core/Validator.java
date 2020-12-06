package org.generation.desafio.core;

import java.util.regex.Pattern;

/**
 * Classe que concentra as validações do sistema
 */
public class Validator {

    //https://android.googlesource.com/platform/frameworks/base/+/81aa097/core/java/android/util/Patterns.java
    private static final Pattern EMAIL_ADDRESS =
            Pattern.compile("[a-zA-Z0-9\\.\\_\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(?:\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})");

    /**
     * Valida se o e-mail está dentro dos padrões de formato de email por exemplo: usuario@dominio.com
     * @param email email a ser validado
     * @return  true para e-mail válido
     *          false para e-mail inválido
     */
    public static boolean isEMail(String email) {
        return !"".equals(email) && EMAIL_ADDRESS.matcher(email).matches();
    }

}
