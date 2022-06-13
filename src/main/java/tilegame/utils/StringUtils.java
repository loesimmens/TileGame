package tilegame.utils;

public class StringUtils {

    private StringUtils(){}

    //cuts a string into multiple lines so that it fits the maximum tokens per line, returns the string with line
    // breaks inserted
    public static String cutTextToFitLine(String text, int maxTokensPerLine) {
        return cutTextToFitLineRecursively(text, maxTokensPerLine);
    }

    //cuts a string into multiple lines so that it fits the maximum tokens per line, returns the string with line
    // breaks inserted
    public static String cutTextToFitLineRecursively(String text, int maxTokensPerLine) {
        if(text.length() < maxTokensPerLine)
            return text;

        int cutOffPoint = moveCutOffPointIfNecessary(text, maxTokensPerLine);

        StringBuilder line = new StringBuilder(text.substring(0, cutOffPoint));
        String nextLine = cutTextToFitLineRecursively(text.substring(cutOffPoint + 1), maxTokensPerLine);

        return line.append("\n").append(nextLine).toString();
    }

    private static int moveCutOffPointIfNecessary(String text, int cutOffPoint) {
        while(text.charAt(cutOffPoint) != ' ') {
            if(text.charAt(cutOffPoint + 1) == ' ') {
                cutOffPoint++;
            } else {
                cutOffPoint--;
            }
        }
        return cutOffPoint;
    }
}
