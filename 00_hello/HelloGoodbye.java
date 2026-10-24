public class HelloGoodbye {
    /**
     * Takes two names as command-line arguments and prints hello and goodbye
     * messages.
     */
    public static void main(String[] args) {
        String firstName = args[0];
        String secondName = args[1];
        String hello = "Hello " + firstName + " and " + secondName + ".";
        String goodbye = "Goodbye " + secondName + " and " + firstName + ".";
        System.out.println(hello);
        System.out.println(goodbye);
    }
}
