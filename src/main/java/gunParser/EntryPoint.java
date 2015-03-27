package gunParser;


public class EntryPoint
{

    public static void main(String[] args)
    {
        System.out.println("Started");
        MainExecutor.instance.run();
        if(System.console() != null){
            System.out.println("Successfully finished. Press Enter to exit...");
            System.console().readLine();
        }
    }
}
