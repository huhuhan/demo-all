package com.yh.demo.design;

/**
 * 模板模式
 *
 * @author yanghan
 * @date 2021/3/8
 */
public class TemplatePattern {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();

        System.out.println();
        game = new Football();
        game.play();
    }
}


/**
 * 抽象父类，通用方法定义逻辑执行的流程，每个接口为模板接口
 */
abstract class Game {
    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    /** final修饰，不可变 */
    public final void play() {

        if (this.isSupported()) {
            //初始化游戏
            initialize();

            //开始游戏
            startPlay();

            //结束游戏
            endPlay();
        } else {
            System.out.println("游戏不支持！");
        }
    }

    abstract boolean isSupported();
}

class Cricket extends Game {
    @Override
    boolean isSupported() {
        return true;
    }

    @Override
    void endPlay() {
        System.out.println("Cricket Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }
}

class Football extends Game {
    @Override
    boolean isSupported() {
        return false;
    }

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }
}