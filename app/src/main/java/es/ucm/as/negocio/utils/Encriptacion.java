package es.ucm.as.negocio.utils;

import se.simbio.encryption.Encryption;

public class Encriptacion {
    private static final String KEY = "$3creTKeeeyYAsAs";
    private static final String SALT = "3st@EzUn@S@lt";
    private static final byte[] IV = {-21, 58, 41, 124, -17, -19, 47, -35, 115, 120, -41, -7, 127, 103, -91, 8};

    public static String encrypt(String string) {
        Encryption encryption = Encryption.getDefault(KEY,SALT, IV);
        return encryption.encryptOrNull(string);
    }

    public static String decrypt(String string) {
        Encryption encryption = Encryption.getDefault(KEY, SALT, IV);
        String ret = encryption.decryptOrNull(string);
        return ret;
    }
}
