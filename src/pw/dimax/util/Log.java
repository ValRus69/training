package pw.dimax.util;

public final class Log {

    private static int runtimeCounter = 0;

    static void print(String s) {
        System.out.println(s);
    }

    static void runtime(String s) {
        System.out.println("=== Runtime (" + ++runtimeCounter + ") ===");
        System.out.println(s);
        System.out.println("=== end " + runtimeCounter + " ===");
    }

    static void exception(Exception e) {
        System.out.println("=== Exception ===");
        System.out.println("Cosed by: " + Thread.currentThread().getStackTrace()[2]);
        System.out.println(e.getMessage());
        //e.printStackTrace();
        System.out.println("=== end exception ===");
    }
}
