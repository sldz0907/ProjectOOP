package Physic;

import Entities.Entity;
import Entities.Player;

public class Gravity {
    public float GravityConst = 0.06f;
    public boolean inAir =  false;
    public float airSpeed;
    public float fallSpeedAfterCollision = 0.5f;
    public Gravity()
    {
        airSpeed = 0f;
    }
    public Gravity(float airSpeed)
    {
        this.airSpeed = airSpeed;
    }
    private void resetInAir(){
        airSpeed = 0;
        inAir = false;
    }
    public void applyGravity(Entity aObject, int[][] lvData) // tác động với vector di chuyển Y, VECTOR VẬT LÝ LUÔN PHẢI ĐƯỢC THAO TÁC CUỐI CÙNG
    {
        if (aObject instanceof Player)
        {
            if (!inAir && !EntititesCollision.IsEntityOnFloor(((Player)aObject).getPlayerHitBox(), lvData))
                inAir = true;
            if (inAir)
            {
                if(EntititesCollision.CanMoveHere(aObject.getX(),aObject.getY() + airSpeed + ((Player)aObject).moveY, ((Player)aObject).getPlayerHitBox().width, ((Player)aObject).getPlayerHitBox().height, lvData)){
                    ((Player)aObject).moveY += airSpeed;
                    airSpeed += GravityConst;
                }
                else
                {
                    ((Player)aObject).moveY = EntititesCollision.GetEntityYPosUnderRoofOrAboveFloor(((Player)aObject).getPlayerHitBox(), airSpeed) - aObject.getY();
                    if (airSpeed > 0)
                    {
                        resetInAir();
                    }else{
                        airSpeed = fallSpeedAfterCollision;
                    }
                }
            }
        } 
}

    public void resetGravity() {
        resetInAir();
    }
}
