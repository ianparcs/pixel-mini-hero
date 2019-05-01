package ph.sparcsky.miniheroes;

public interface Constant {

    String MENU_BG = "map/menu.tmx";
    String LEVEL_1 = "map/level1.tmx";
    String PLAYER = "atlas/player.atlas";

    float PIXEL_SIZE = 8f;

    interface Font {
        String YOSTER = "font/yoster.ttf";
        String MAIN = "title.ttf";
        String SMALL = "small.ttf";
    }

    interface Anim {
        String HERO_BOW_ATTACK = "basic_bow_attack";
        String HERO_AXE_ATTACK = "basic_axe_attack";
        String HERO_SWORD_ATTACK = "basic_sword_attack";
        String HERO_PIQUE_ATTACK = "basic_pique_attack";
        String HERO_JUMP  = "basic_jump";
        String HERO_RUN  = "basic_run";
        String HERO_IDLE = "basic_idle";
    }

    interface Math {
        float GRAVITY = -9.8f;
        float TIME_STEP = 1f / 60f;
        float SCALE = 1f / 8;
    }

}
