// Gateway defines a Conditional Critical Region for the closed variable
// which is checked by a process. 
// If its value is closed (open) the process can continiue. Otherwise the 
// process must be stopped

package playground;

public class Gateway {
    
    private boolean closed=false;

    public synchronized void look(){
        while(closed){
            try{wait();} catch(InterruptedException ie){}
        }
    }
    public synchronized void open(){
        closed=false;
        notifyAll();
    }
    public synchronized void close(){
        closed=true;
    }
    public synchronized boolean isClosed(){
        return closed;
    }
}
