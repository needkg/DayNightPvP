package me.needkg.daynightpvp.feature.gui.menus;

public enum MenuSize {
    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private final int size;

    MenuSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
