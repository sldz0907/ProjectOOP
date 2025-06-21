package Entities;

public class EntityActions {
    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int DEAD = 3; 
        public static final int[] ATTACK = {4,5,6,7};
        public static final int HURT = 8; 

        public static int getSpriteAmount(int player_action) {
            if (player_action == IDLE)
                return 5;
            else if (player_action == RUN)
                return 8;
            else if (player_action == JUMP)
                return 8;
            else if (player_action == DEAD)
                return 5;
            else if (player_action == ATTACK[0])
                return 6;
            else if (player_action == ATTACK[1])
                return 3;
            else if (player_action == ATTACK[2])
                return 3;
            else if (player_action == ATTACK[3])
                return 10;
            else if (player_action == HURT)
                return 2;

            return 0;
        }
}

    public static class EnemyConstants{
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int RUN = 2;
        public static final int HURT = 3;
        public static final int DEAD = 4;
        public static final int ATTACK = 5;

        public static int getSpriteAmount(int enemyState) {
            switch (enemyState) {
                case IDLE:
                    return 7;
                case DEAD:
                    return 4;          
                case RUN:
                    return 8;
                case WALK:
                    return 7;
                case ATTACK:
                    return 4;
                case HURT:
                    return 2;
                         
                default:
                    break;
            }
            return 0;
        }
    }

    public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
}
