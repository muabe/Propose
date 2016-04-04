package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-31
 */
public class Flinger extends DirectionAdapter {

    @Override
    public boolean left(Point point, boolean isChangeDirection) {
        return false;
    }

    @Override
    public boolean right(Point point, boolean isChangeDirection) {
        return false;
    }

    @Override
    public boolean up(Point point, boolean isChangeDirection) {
        return false;
    }

    @Override
    public boolean down(Point point, boolean isChangeDirection) {
        return false;
    }

}
