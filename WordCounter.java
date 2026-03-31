import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner; 
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 

public class WordCounter {
    public static int processText(StringBuffer text, String stopword) 
        throws InvalidStopwordException, TooSmallText {
            Pattern regex = Pattern.compile("[a-zA-Z0-9']+"); 
            Matcher matcher = regex.matcher(text); 

            int count = 0; 
            boolean foundStopword = false; 

            while(matcher.find()) {
                String word = matcher.group(); 
                count++; 

                if(stopword != null && word.equals(stopword)) {
                    foundStopword = true; 
                    break; 
                }
            }

            if(stopword != null && !foundStopword) {
                throw new InvalidStopwordException("Stopword not found: " + stopword); 
            }

            if(count < 5) {
                throw new TooSmallText("TooSmallText"); 
            }

            return count; 

        }
    
    public static StringBuffer

    public static void main(String [] args) {

    }
}