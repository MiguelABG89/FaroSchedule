import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private final static int NUMERO_HILOS=2;
    private final static String MORSE1="·-";
    private final static String MORSE2="-···";
    private final static String IDENTIFICADOR1="A";
    private final static String IDENTIFICADOR2="B";
    class Radiofaro implements Runnable {
        private String identificador;
        private String morse;
        private boolean colision = false;

        public Radiofaro(String identificador, String morse) {
            this.identificador = identificador;
            this.morse = morse;
        }

        @Override
        public void run() {
            Calendar l_Calendario = new GregorianCalendar();
            String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                    + ":" + l_Calendario.get(Calendar.SECOND);

            System.out.println(l_Tiempo + " > " + identificador + " " + morse + " > " );

        }

    }


    public static void  main(String[] args) throws InterruptedException {
        Calendar l_Calendario = new GregorianCalendar();
        String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                + ":" + l_Calendario.get(Calendar.SECOND);

        System.out.println( l_Tiempo+ " > Inicio aplicación.");

        Radiofaro radiofaroA = new Main().new Radiofaro(IDENTIFICADOR1, MORSE1);
        Radiofaro radiofaroB = new Main().new Radiofaro(IDENTIFICADOR2, MORSE2);

        ScheduledExecutorService l_Scheduler = Executors.newScheduledThreadPool(1);

        l_Scheduler.scheduleWithFixedDelay(radiofaroA,0,3,TimeUnit.SECONDS);
        l_Scheduler.scheduleWithFixedDelay(radiofaroB,3,2,TimeUnit.SECONDS);

        l_Scheduler.awaitTermination(10,TimeUnit.SECONDS);

        l_Scheduler.shutdown();

    }



}

