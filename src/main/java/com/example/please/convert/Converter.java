package com.example.please.convert;

public class Converter {

    private static char [] lettersInRussian = {
            'й','ц','у','к','е','н','г','ш','щ','з','х','ъ',
            'ф','ы','в','а','п','р','о','л','д','ж','э',
            'я','ч','с','м','и','т','ь','б','ю',
            'Й','Ц','У','К','Е','Н','Г','Ш','Щ','З','Х','Ъ',
            'Ф','Ы','В','А','П','Р','О','Л','Д','Ж','Э',
            'Я','Ч','С','М','И','Т','Ь','Б','Ю',
            '1','2','3','4','5','6','7','8','9','0'
    };

    private static char [] lettersInEnglish = {
            'q','w','e','r','t','y','u','i','o','p','[',']',
            'a','s','d','f','g','h','j','k','l',';','\'',
            'z','x','c','v','b','n','m',',','.',
            'Q','W','E','R','T','Y','U','I','O','P','{','}',
            'A','S','D','F','G','H','J','K','L',':','"',
            'Z','X','C','V','B','N','M','<','>',
            '1','2','3','4','5','6','7','8','9','0'
    };


    public static String convertPassword(String password){
        
        char [] inRussian = password.toCharArray();
        char [] inEnglish = new char[inRussian.length];

        for (int i = 0; i < lettersInEnglish.length; i++) {
            for (int j = 0; j < inRussian.length; j++) {
                if (inRussian[j] == lettersInRussian[i]){
                    inEnglish[j] = lettersInEnglish[i];
                }
            }
        }

        return String.valueOf(inEnglish);
    }

}
