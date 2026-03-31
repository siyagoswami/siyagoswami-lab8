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
    
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner keyboard = new Scanner(System.in); 
        Scanner fileScanner = null; 

        while(fileScanner == null) {
            try {
                fileScanner = new Scanner(new File(path)); 
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Enter another file name: "); 
                path = keyboard.nextLine(); 
            }
        }

        StringBuffer contents = new StringBuffer(); 
        while(fileScanner.hasNextLine()) {
            contents.append(fileScanner.nextLine()); 
            if(fileScanner.hasNextLine()) {
                contents.append("\n"); 
            }
        }

        fileScanner.close();
        if(contents.length() == 0) {
            throw new EmptyFileException(path); 
        }

        return contents; 
    }

    public static void main(String [] args) {

    }
}