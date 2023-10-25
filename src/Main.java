import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private final static int NUMERO_HILOS=4;
    private final static String MORSE1="·-";
    private final static String MORSE2="-···";
    private final static String IDENTIFICADOR1="A";
    private final static String IDENTIFICADOR2="B";
    class Radiofaro implements Runnable {
        private String a_Identificador;
        private String a_Morse;
        private Ejecucion a_Buzon;

        public Radiofaro(String p_Identificador, String p_Morse,Ejecucion p_Buzon) {
            this.a_Identificador = p_Identificador;
            this.a_Morse = p_Morse;
            this.a_Buzon=p_Buzon;
        }

        @Override
        public void run() {
            Calendar l_Calendario = new GregorianCalendar();
            String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                    + ":" + l_Calendario.get(Calendar.SECOND);
            String l_Colision;

            if (a_Buzon.a_Estado) l_Colision="Colisionan";
            else l_Colision="no colisionan";


            a_Buzon.a_Estado=true;

            System.out.println(l_Tiempo + " > " + a_Identificador + " " + a_Morse + " > " +l_Colision+ " "+ a_Buzon.isA_Estado());
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            a_Buzon.a_Estado=false;
        }

    }

    public static void  main(String[] args) throws InterruptedException {
        Calendar l_Calendario = new GregorianCalendar();
        Ejecucion l_Buzon = new Main().new Ejecucion();

        String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                + ":" + l_Calendario.get(Calendar.SECOND);

        System.out.println( l_Tiempo+ " > Inicio aplicación.");

        Radiofaro radiofaroA = new Main().new Radiofaro(IDENTIFICADOR1, MORSE1,l_Buzon);
        Radiofaro radiofaroB = new Main().new Radiofaro(IDENTIFICADOR2, MORSE2,l_Buzon);

        ScheduledExecutorService l_Scheduler = Executors.newScheduledThreadPool(NUMERO_HILOS);

        l_Scheduler.scheduleWithFixedDelay(radiofaroA,0,3,TimeUnit.SECONDS);
        l_Scheduler.scheduleWithFixedDelay(radiofaroB,3,2,TimeUnit.SECONDS);

        l_Scheduler.awaitTermination(30,TimeUnit.SECONDS);

        l_Scheduler.shutdown();

    }


    class Ejecucion{
        public boolean a_Estado;
        public Ejecucion() {
            a_Estado =false;
        }

        public boolean isA_Estado() {
            return a_Estado;
        }

        public void setA_Estado(boolean a_Estado) {
            this.a_Estado = a_Estado;
        }

    }
}

