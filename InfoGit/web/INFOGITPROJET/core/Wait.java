package core;

public class Wait extends Thread {

    final public static int TIME = 100;

    public void run() {
        System.out.print( "Processing" );

        while ( true ) {
            try {
                Thread.sleep( TIME );
            } catch ( InterruptedException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.print( " ." );
            // System.out.flush();
        }
    }
}