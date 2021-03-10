package com.yh.demo.design;

/**
 * 责任链模式
 *
 * @author yanghan
 * @date 2021/3/10
 */
public class ChainOfResponsibilityPattern {
    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();

        System.out.println("Standard");
        loggerChain.logMessage(AbstractLogger.INFO, "INFO level information.");

        System.out.println("Standard");
        loggerChain.logMessage(AbstractLogger.DEBUG, "DEBUG level information.");

        System.out.println("Standard");
        loggerChain.logMessage(AbstractLogger.ERROR, "ERROR level information.");
    }
}

class FileLogger extends AbstractLogger {

    public FileLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File::Logger: " + message);
    }
}

class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}

class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Console::Logger: " + message);
    }
}

abstract class AbstractLogger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;

    /** 责任链中的下一个元素 */
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    /**
     * 父类通用接口，传递请求
     * @param level
     * @param message
     */
    public void logMessage(int level, String message) {
        // 先执行下个节点，即先进后出
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
        // 后执行下个节点，即先进先出
//        if (this.level <= level) {
//            write(message);
//        }
    }

    /**
     * 实际操作接口
     * @param message
     */
    abstract protected void write(String message);

}