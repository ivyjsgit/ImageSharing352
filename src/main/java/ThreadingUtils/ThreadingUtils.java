package ThreadingUtils;

import java.util.Set;

public class ThreadingUtils {
    public static Set<Thread> getRunningThreads(){
        return Thread.getAllStackTraces().keySet();
    }
    public static void killNamedThread(String name, Set<Thread> runningThreads){
        for(Thread iteratingThread:runningThreads){
            if(iteratingThread.getName().equals(name)){
                iteratingThread.stop();
            }
        }
    }
}
