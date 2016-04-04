package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-31
 */
abstract class DirectionAdapter{

    public interface DirectionObserver {
        boolean plus(Point point, boolean isChangeDirection);
        boolean minus(Point point, boolean isChangeDirection);
    }

    public abstract boolean left(Point point, boolean isChangeDirection);
    public abstract boolean right(Point point, boolean isChangeDirection);
    public abstract boolean up(Point point, boolean isChangeDirection);
    public abstract boolean down(Point point, boolean isChangeDirection);

    private DirectionObserver horizontal, vertical;

    public DirectionAdapter(){
        horizontal = new DirectionObserver() {
            @Override
            public boolean plus(Point point, boolean isChangeDirection) {
                return right(point, isChangeDirection);
            }

            @Override
            public boolean minus(Point point, boolean isChangeDirection) {
                return left(point, isChangeDirection);
            }
        };

        vertical = new DirectionObserver() {
            @Override
            public boolean plus(Point point, boolean isChangeDirection) {
                return down(point, isChangeDirection);
            }

            @Override
            public boolean minus(Point point, boolean isChangeDirection) {
                return up(point, isChangeDirection);
            }
        };
    }

    public DirectionObserver getHorizontal(){
        return horizontal;
    }

    public DirectionObserver getVertical(){
        return vertical;
    }
}
