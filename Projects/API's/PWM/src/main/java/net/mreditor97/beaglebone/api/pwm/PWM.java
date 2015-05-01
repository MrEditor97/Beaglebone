package net.mreditor97.beaglebone.api.pwm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Samuel J. Hughes (MrEditor97)
 */
public class PWM {
    
    private static final Logger log = LogManager.getLogger(PWM.class);
    private final String controller;
    
    public PWM(String controller) {
        this.controller = controller;
    }
    
    public int getDuty() {
        try {
            FileInputStream reader = new FileInputStream("/sys/class/pwm/pwm" + controller + "/duty_ns");
            return reader.read();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        } catch (IOException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        }
        return -1;
    }
    
    public int getPeriod() {
        try {
            FileInputStream reader = new FileInputStream("/sys/class/pwm/pwm" + controller + "/period_ns");
            return reader.read();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        } catch (IOException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        }
        return -1;
    }
    
    public int getPolarity() {
        try {
            FileInputStream reader = new FileInputStream("/sys/class/pwm/pwm" + controller + "/polarity");
            return reader.read();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        } catch (IOException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        }
        return -1;
    }
    
    public boolean isRunning() {
        try {
            FileInputStream reader = new FileInputStream("/sys/class/pwm/pwm" + controller + "/duty_ns");
            return reader.read() != 0;
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        } catch (IOException ex) {
            log.warn("An error occured when attempting to read from PWM Controller " + controller + ": " + ex);
            System.exit(1);
        }
        return false;
    }
    
    public void setDuty(String duty) {
        try {
            PrintWriter writer = new PrintWriter("/sys/class/pwm/pwm" + controller + "/duty_ns");
            writer.print(duty);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to write to the PWM Controller " + controller + ": " + ex);
        }
    }
    
    public void setDuty(int duty) {
        setDuty(Integer.toString(duty));
    }
    
    public void setPeriod(String period) {        
        try {
            PrintWriter writer = new PrintWriter("/sys/class/pwm/pwm" + controller + "/period_ns");
            writer.print(period);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to write to the PWM Controller " + controller + ": " + ex);
        }
    }
    
    public void setPeriod(int period) {
        setPeriod(Integer.toString(period));
    }
    
    public void setPolarity(String polarity) {
        setRun(false);

        try {
            PrintWriter writer = new PrintWriter("/sys/class/pwm/pwm" + controller + "/polarity");
            writer.print(polarity);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to write to the PWM Controller " + controller + ": " + ex);
        }
        
        setRun(true);
    }
    
    public void setPolarity(int polarity) {
        setPolarity(Integer.toString(polarity));
    }
    
    public void setRun(boolean run) {
        int running = (run) ? 1 : 0;
        try {
            PrintWriter writer = new PrintWriter("/sys/class/pwm/pwm" + controller + "/run");
            writer.print(running);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            log.warn("An error occured when attempting to write to the PWM Controller " + controller + ": " + ex);
        }
    }
    
    public void create() {
        if (!exists() == true) {
            try {
                PrintWriter writer = new PrintWriter("/sys/class/pwm/export");
                writer.print(controller);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException ex) {
                log.warn("An error occured when attempting to export the PWM Controller " + controller + ": " + ex);
            }
        }
    }
    
    public boolean exists() {
        File file = new File("/sys/class/pwm/pwm" + controller);
        
        //log.debug("Checking whether PWM Controller " + controller + " already exists");
        
        return file.exists();
    }
    
}
