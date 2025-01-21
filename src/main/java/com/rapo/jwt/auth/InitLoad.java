package com.rapo.jwt.auth;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class InitLoad {

    @Getter
    public static final PrivateKey privateKey;

    @Getter
    public static final PublicKey publicKey;

    static {
        try {
            privateKey = getPrivateKeyFromPEM("private.pem");
            publicKey = getPublicKeyFromPEM("public.pem");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load keys: " + e.getMessage());
        }
    }

    private static PrivateKey getPrivateKeyFromPEM(String filename) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File file = ResourceUtils.getFile("classpath:" + filename);
        String key = new String(Files.readAllBytes(file.toPath()));
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    private static PublicKey getPublicKeyFromPEM(String filename) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File file = ResourceUtils.getFile("classpath:" + filename);
        String key = new String(Files.readAllBytes(file.toPath()));
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}
