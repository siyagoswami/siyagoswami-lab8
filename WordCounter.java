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

            int totalCount = 0; 
            int countThroughStopword = 0;
            boolean foundStopword = false; 

            while(matcher.find()) {
                String word = matcher.group(); 
                totalCount++; 

                if(!foundStopword) {
                    countThroughStopword++; 
                }

                if(stopword != null && word.equals(stopword)) {
                    foundStopword = true; 
                }
            }

            if(totalCount < 5) {
                throw new TooSmallText(totalCount); 
            }

            if(stopword != null && !foundStopword) {
                throw new InvalidStopwordException(stopword); 
            }

            return countThroughStopword; 

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
        Scanner keyboard = new Scanner(System.in); 
        System.out.println("Enter 1 to process a file or 2 to process text:"); 
        String choice = keyboard.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Invalid option. Please enter 1 or 2:"); 
            choice = keyboard.nextLine(); 
        }

        String stopword = null; 
        if(args.length > 1) {
            stopword = args[1];
        }

        StringBuffer text; 
        if(choice.equals("1")) {
            try {
                text = processFile(args[0]); 
            } catch (EmptyFileException e) {
                text = new StringBuffer(""); 
            }
        } else {
            text = new StringBuffer(args[0]);
        }

        try {
            int count = processText(text, stopword); 
            System.out.println("Found " + count + " words."); 
        } catch (TooSmallText e) { 
            System.out.println(e); 
        } catch (InvalidStopwordException e) {
            System.out.println("Invalid stopword. Please enter another stopword:");
            String newStopword = keyboard.nextLine(); 
            try {
                int count = processText(text, stopword); 
                System.out.println("Found " + count + " words."); 
            } catch (TooSmallText e2) {
                System.out.println(e2); 
            } catch (InvalidStopwordException e2) {
                System.out.println(e2);
            }
        }
    }
}