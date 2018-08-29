import java.util.*;

public class vigenere_mapped
{
    public static HashMap dictionary_charToNum;
    public static HashMap dictionary_numToChar;

    public static void main(String[] args)
    {
        String plaintext;
        String key;
        if (args.length == 2)
        {
            plaintext = args[0].toUpperCase();
            key = args[1].toUpperCase();
        }
        else
        {
            Scanner keyboard = new Scanner(System.in);
            
            plaintext = keyboard.nextLine().toUpperCase();
            key = keyboard.nextLine().toUpperCase();
        }
        
        initializeDictionaries();

        // String ciphertext = encrypt(plaintext, key);
        String ciphertext = stackEncrypt(plaintext, key);
        // String message = decrypt(ciphertext, key);
        String message = stackDecrypt(ciphertext, key);

        if (message.equals(plaintext))
        {
            System.out.println("It works! Your message is:\n" + message);
        }
        else
        {
            System.out.println("Error!\nPlaintext: " + plaintext + "\nMessage: " + message);
        }
    }

    public static void initializeDictionaries()
    {
        dictionary_charToNum = new HashMap<Character, Integer>();
        dictionary_numToChar = new HashMap<Integer, Character>();
        for (int i = 0; i < 26; i++)
        {
            dictionary_charToNum.put((char)('A' + i), i);
            dictionary_numToChar.put(i, (char)('A' + i));
        }
        // Special Characters
        dictionary_charToNum.put('.', 26);
        dictionary_numToChar.put(26, '.');
        dictionary_charToNum.put('!', 27);
        dictionary_numToChar.put(27, '!');
        dictionary_charToNum.put('?', 28);
        dictionary_numToChar.put(28, '?');
        dictionary_charToNum.put(' ', 29);
        dictionary_numToChar.put(29, ' ');
    }

    public static void initializeDictionaries(char[] letters)
    {
        dictionary_charToNum = new HashMap<Character, Integer>();
        dictionary_numToChar = new HashMap<Integer, Character>();
        for (int i = 0; i < letters.length; i++)
        {
            dictionary_numToChar.put(i, letters[i]);
            dictionary_charToNum.put(letters[i], i);
        }
    }

    public static String stackEncrypt(String plaintext, String key)
    {
        return encrypt(encrypt(encrypt(plaintext, key), key), key);
    }

    public static String stackDecrypt(String ciphertext, String key)
    {
        return decrypt(decrypt(decrypt(ciphertext, key), key), key);
    }

    public static String makeKeyText(String key, int length)
    {
        StringBuilder keytextBuilder = new StringBuilder();
        int keyindex = 0;
        for (int i = 0; i < length; i++)
        {
            keytextBuilder.append(key.charAt(keyindex++));
            if (keyindex == key.length())
                keyindex = 0;
        }

        return keytextBuilder.toString();
    }

    public static String buildText(String text, String key, boolean operator)
    {
        text = text.toUpperCase();
        String keytext = makeKeyText(key, text.length());
        StringBuilder textBuilder = new StringBuilder();

        int size = dictionary_charToNum.size();
        System.out.println("size = " + size);
        Scanner x = new Scanner(System.in);
        x.nextLine();
        int textlength = text.length();

        for (int i = 0; i < textlength; i++)
        {
            int textInt = (int)dictionary_charToNum.get(text.charAt(i));
            int keyInt = (int)dictionary_charToNum.get(keytext.charAt(i));

            int temp = -1;
            if (operator)
            {
                temp = (textInt + keyInt) % size;
            }
            else
            {
                temp = (textInt - keyInt) % size;
                if (temp < 0)
                {
                    temp += size;
                }
            }

            char encryptedChar = (char)dictionary_numToChar.get(temp);

            System.out.println(text.charAt(i) + " " + encryptedChar);

            textBuilder.append(encryptedChar);
        }

        System.out.println();
        
        return textBuilder.toString();
    }

    public static String encrypt(String plaintext, String key)
    {
        return buildText(plaintext, key, true);
    }

    public static String decrypt(String ciphertext, String key)
    {
        return buildText(ciphertext, key, false);
    }
}
