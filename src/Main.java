import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "Faro Schedule"                                                                                                              *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM                                                                                                *
 **********************************************************************************************************************************************
 *   @author Miguel Ángel Brand Gaviria                                                                                                       *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *        - Lanza una tarea con retraso inicial de 2 segundos, y la repite cada 3 segundos durante 10 segundos.                                                          *
 *        - Ejemplo básico de scheduledThreadPoolExecutor, Executors y Runnable().                                                            *
 *        - Una tarea se ejecuta cada 3 segundos con una pausa inicial de 2 segundos mostrando la hora del sistema.                           *
 **********************************************************************************************************************************************/

public class Main {
    private final static int NUMERO_HILOS=4;
    private final static int PAUSAINICIAL1=0;
    private final static int PAUSAINICIAL2=3;
    private final static int TIEMPOEJECUCION=30;
    private final static int DELAY1=3;
    private final static int DELAY2=2;
    private final static String MORSE1="·-";
    private final static String MORSE2="-···";
    private final static String IDENTIFICADOR1="A";
    private final static String IDENTIFICADOR2="B";

    //Clase runnable donde se imprime el radiofaro
    class Radiofaro implements Runnable {
        //definicion de los atributos y el constructor con sus respectivos parametros, de la clase Radiofaro
        private String a_Identificador;
        private String a_Morse;
        private Ejecucion a_Buzon;

        public Radiofaro(String p_Identificador, String p_Morse,Ejecucion p_Buzon) {
            this.a_Identificador = p_Identificador;
            this.a_Morse = p_Morse;
            this.a_Buzon=p_Buzon;
        }
        //Metodo run
        @Override
        public void run() {
            //Creacion de las variables que se van a usar en el metodo
            Calendar l_Calendario = new GregorianCalendar();
            String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                    + ":" + l_Calendario.get(Calendar.SECOND);
            String l_Colision;

            //if para comprobar si coinciden en la ejecucion la tarea 1 y la tarea 2
            if (a_Buzon.a_Estado) l_Colision="Colisionan";
            else l_Colision="no colisionan";


            a_Buzon.a_Estado=true;

            System.out.println(l_Tiempo + " > " + a_Identificador + " " + a_Morse + " > " +l_Colision);

            //delay entre tareas
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            a_Buzon.a_Estado=false;
        }

    }

    public static void  main(String[] args) throws InterruptedException {
        //creacion de las variables usadas para el programa
        Calendar l_Calendario = new GregorianCalendar();
        Ejecucion l_Buzon = new Main().new Ejecucion();
        //sintaxis de para la impresion de la HH:MM:SS
        String l_Tiempo=l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE)
                + ":" + l_Calendario.get(Calendar.SECOND);

        System.out.println( l_Tiempo+ " > Inicio aplicación.");
        //creacion de los objetos runnable y sus respectivos parametros
        Radiofaro radiofaroA = new Main().new Radiofaro(IDENTIFICADOR1, MORSE1,l_Buzon);
        Radiofaro radiofaroB = new Main().new Radiofaro(IDENTIFICADOR2, MORSE2,l_Buzon);
        //creacion del objeto Schedule
        ScheduledExecutorService l_Scheduler = Executors.newScheduledThreadPool(NUMERO_HILOS);
        //Inicializacion del objeto Schedule para cada tarea
        l_Scheduler.scheduleWithFixedDelay(radiofaroA,PAUSAINICIAL1,DELAY1,TimeUnit.SECONDS);
        l_Scheduler.scheduleWithFixedDelay(radiofaroB,PAUSAINICIAL2,DELAY2,TimeUnit.SECONDS);
        //Definicion del tiempo que va a durar la ejecucion
        l_Scheduler.awaitTermination(TIEMPOEJECUCION,TimeUnit.SECONDS);
        //Finalizacion del objeto schedule
        l_Scheduler.shutdown();

    }


    class Ejecucion{
        //objeto Buzon para almacenar el estado de ejecucion
        public boolean a_Estado;
        public Ejecucion() {
            a_Estado =false;
        }

    }
}

