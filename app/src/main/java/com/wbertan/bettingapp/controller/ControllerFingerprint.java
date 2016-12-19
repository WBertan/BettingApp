package com.wbertan.bettingapp.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;

import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by william.bertan on 18/12/2016.
 * Following this tutorial: http://www.techotopia.com/index.php/An_Android_Fingerprint_Authentication_Tutorial
 */
@TargetApi(Build.VERSION_CODES.M)
public class ControllerFingerprint {
    private static final String KEY_NAME = "betting_app_key";

    private KeyStore mKeyStore;
    private Cipher mCipher;

    public void validateFingerprint(Context aContext, ICallback<Boolean> aCallback) {
        KeyguardManager keyguardManager = (KeyguardManager) aContext.getSystemService(Context.KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) aContext.getSystemService(Context.FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {
            aCallback.onError(new CallbackError(-1, "Lock screen security not enabled in Settings"));
            return;
        }
        if (ActivityCompat.checkSelfPermission(aContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            aCallback.onError(new CallbackError(-1, "Fingerprint authentication permission not enabled"));
            return;
        }
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            aCallback.onError(new CallbackError(-1, "Register at least one fingerprint in Settings"));
            return;
        }

        generateKey();

        if (cipherInit()) {
            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(mCipher);
            FingerprintHandler helper = new FingerprintHandler(aCallback);
            helper.startAuth(aContext, fingerprintManager, cryptoObject);
        }
    }

    private boolean cipherInit() {
        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                                      + KeyProperties.BLOCK_MODE_CBC + "/"
                                      + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException
                | CertificateException
                | UnrecoverableKeyException
                | IOException
                | NoSuchAlgorithmException
                | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private void generateKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException
                | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            mKeyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                                     .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                                     .setUserAuthenticationRequired(true)
                                                     .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                                     .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
        private ICallback<Boolean> mCallback;
        private FingerprintHandler(ICallback<Boolean> aCallback) {
            mCallback = aCallback;
        }

        void startAuth(Context aContext, FingerprintManager aManager, FingerprintManager.CryptoObject aCryptoObject) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (ActivityCompat.checkSelfPermission(aContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            aManager.authenticate(aCryptoObject, cancellationSignal, 0, this, null);
        }

        @Override
        public void onAuthenticationError(int aErrorCode, CharSequence aErrorString) {
            super.onAuthenticationError(aErrorCode, aErrorString);
            mCallback.onError(new CallbackError(aErrorCode, "Authentication error.\n" + aErrorString));
        }

        @Override
        public void onAuthenticationHelp(int aHelpMessageId, CharSequence aHelpString) {
            super.onAuthenticationHelp(aHelpMessageId, aHelpString);
            mCallback.onError(new CallbackError(-1, "Authentication help.\n" + aHelpString));
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            mCallback.onSuccess(false);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult aResult) {
            super.onAuthenticationSucceeded(aResult);
            mCallback.onSuccess(true);
        }
    }
}
