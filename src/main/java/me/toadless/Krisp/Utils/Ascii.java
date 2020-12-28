package me.toadless.Krisp.Utils;

public class Ascii {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    public static void printVainity() {
        String vanity = "g      .r    _  __     _    g      __ _ _\n" +
                "g      /\\\\ r| |/ /_ __(_)___ _ __g \\ \\ \\ \\\n" +
                "g     ( ( )r|  < | '__| / __| '_ \\g \\ \\ \\ \\\n" +
                "g      \\\\/ r| . \\| |  | \\__ \\ |_) |g ) ) ) )\n" +
                "g       '  r|_|\\_\\_|  |_|___/ .__/g / / / /\n" +
                "     d=====================r| |d===g/_/_/_/ \n" +
                "g                          r|_|\nd";
        String green = vanity.replace("g", ANSI_GREEN);
        String red = green.replace("r", ANSI_RED);
        String VanityFinish = red.replace("d", ANSI_RESET);
        System.out.println(VanityFinish);
    }
}
