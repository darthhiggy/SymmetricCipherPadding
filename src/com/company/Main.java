package com.company;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Main
{

    public static void main(String[] args) throws Exception
    {
        byte[]        input = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        byte[]        keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };


        // SecretKeySpec class provides a simple mechanism for converting byte data into secret key suitable for
        // passing to a cipher object's init method
        SecretKeySpec key = new SecretKeySpec (keyBytes, "AES");

        // patterns to create and use Cipher object
        // You create one using Cipher.getInstance()
        // initialize it with the mode you want using Cipher.init()
        // feed the input data in while while collecting the output at the sametime using Cipher.update()
        // finish off the process with Cipher.doFinal()

        // cipher name: AlogrithmName/Mode/TypeofPadding  | BC = Bouncing Cassle
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");

        //Padding: DES has a block size of 8 bytes,
        // aes has a block size of 16 bytes
        // thruth is for most of us, the data we wish to encrypt
        // is not a alays going to be a multiple of the block size of the encryption mechanism we want to use

        // it is best to print the bytes you are interested in using hex, nicely mpas to two digits a byte
        System.out.println("input: " + CryptoUtils.toHex(input));


        // encryption pass
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

        // cipher.updata will encrypt the input into cipherText as output and passing the # of bytes encrypted into
        // ctlength
        // regardless of how many bytes get output during an update, you will only know how many bytes have been
        // written to the output array if you keep track of retrun value
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);

        // finish off encryption
        ctLength += cipher.doFinal(cipherText, ctLength);


        System.out.println("cipher: " + CryptoUtils.toHex(cipherText) + " bytes: " + ctLength);

        //decryption pass
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plainText = new byte[cipher.getOutputSize(ctLength)];

        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        ptLength += cipher.doFinal(plainText, ptLength);
        System.out.println("plaint text: " + CryptoUtils.toHex(plainText, ptLength) + " bytes: " + ptLength);
    }
}
